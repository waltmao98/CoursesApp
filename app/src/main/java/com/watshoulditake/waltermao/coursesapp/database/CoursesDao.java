package com.watshoulditake.waltermao.coursesapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.WorkerThread;

import com.watshoulditake.waltermao.coursesapp.model.Course;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Data access object for database
 */
public class CoursesDao {

    private static CoursesDao sCoursesDao;
    private AppDatabase mDbHelper;

    private CoursesDao(Context context) {
        mDbHelper = new AppDatabase(context.getApplicationContext());
    }

    public static CoursesDao getInstance(Context context) {
        if (sCoursesDao == null) {
            sCoursesDao = new CoursesDao(context);
        }
        return sCoursesDao;
    }

    @WorkerThread
    public List<CourseSummary> getPrerequitesList(String courseCode) throws JSONException {
        return getJSONCourseSummaryList(courseCode, CoursesDBSchema.Cols.PREREQS_LIST);
    }

    @WorkerThread
    public List<CourseSummary> getFutureCourses(String courseCode) throws JSONException {
        return getJSONCourseSummaryList(courseCode, CoursesDBSchema.Cols.FUTURE_COURSES_LIST);
    }

    @WorkerThread
    public List<CourseSummary> querySubject(String subject) {
        CourseCursorWrapper cursorWrapper = mDbHelper.queryCourses(null,
                CoursesDBSchema.Cols.SUBJECT + "= ?",
                new String[]{subject},
                null, null, null);
        return cursorWrapper.getCourseSummaries();
    }

    @WorkerThread
    public Course getCourseDetail(String courseCode) {
        CourseCursorWrapper cursorWrapper = mDbHelper.queryCourses(null,
                CoursesDBSchema.Cols.COURSE_CODE + "= ?",
                new String[]{courseCode},
                null, null, null);
        return cursorWrapper.getCourseDetails();
    }

    @WorkerThread
    public CourseSummary getSingleCourseSummary(String courseCode) {
        CourseCursorWrapper cursorWrapper = mDbHelper.queryCourses(
                CoursesDBSchema.Cols.COURSE_SUMMARY_COLS,
                CoursesDBSchema.Cols.COURSE_CODE + "=?",
                new String[]{courseCode},
                null,
                null,
                null
        );
        return cursorWrapper.getSingleCourseSummary();
    }

    @WorkerThread
    public List<CourseSummary> querySearchTerm(String searchTerm) {
        String sqlSearchTerm = "%" + searchTerm + "%";
        CourseCursorWrapper cursorWrapper = mDbHelper.queryCourses(null,
                CoursesDBSchema.Cols.COURSE_CODE + " LIKE ? COLLATE NOCASE" +
                        " OR " + CoursesDBSchema.Cols.TITLE + " LIKE ? COLLATE NOCASE" +
                        " OR " + CoursesDBSchema.Cols.DESCRIPTION + " LIKE ? COLLATE NOCASE",
                new String[]{sqlSearchTerm, sqlSearchTerm, sqlSearchTerm},
                null, null, null);
        return cursorWrapper.getCourseSummaries();
    }

    @WorkerThread
    public List<CourseSummary> getAllCourses() {
        CourseCursorWrapper cursorWrapper = mDbHelper.queryCourses(
                null, null, null, CoursesDBSchema.Cols.SUBJECT, null, null);
        return cursorWrapper.getCourseSummaries();
    }

    /**
     * @param courseCode course code of the course to look for
     * @param column     column that contains a json array string
     * @return course summaries list of the courses in the json array string
     * @throws JSONException if given column is not a json array string
     */
    @WorkerThread
    private List<CourseSummary> getJSONCourseSummaryList(String courseCode, String column) throws JSONException {
        CourseCursorWrapper cursorWrapper = mDbHelper.queryCourses(
                new String[]{column},
                CoursesDBSchema.Cols.COURSE_CODE + "=?",
                new String[]{courseCode},
                null,
                null,
                null);
        List<String> prereqCourseCodes = cursorWrapper.getCourseCodeStringsFromJSON();

        List<CourseSummary> prereqs = new ArrayList<>();
        for (String prereqCourseCode : prereqCourseCodes) {
            prereqs.add(getSingleCourseSummary(prereqCourseCode));
        }
        return prereqs;
    }

    /**
     * @return map of subject code to subject full name
     */
    @WorkerThread
    public Map<String, String> getSubjectMapping() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(SubjectDBSchema.TABLE_NAME,
                new String[]{SubjectDBSchema.Cols.SUBJECT_CODE, SubjectDBSchema.Cols.SUBJECT_NAME},
                null, null, null, null,
                SubjectDBSchema.Cols.SUBJECT_CODE + " ASC");
        Map<String, String> subjectsMap = new LinkedHashMap<>();
        while (cursor.moveToNext()) {
            String subjectCode = cursor.getString(cursor.getColumnIndex(SubjectDBSchema.Cols.SUBJECT_CODE));
            String subjectName = cursor.getString(cursor.getColumnIndex(SubjectDBSchema.Cols.SUBJECT_NAME));
            subjectsMap.put(subjectCode, subjectName);
        }
        cursor.close();
        return subjectsMap;
    }

}
