package com.watshoulditake.waltermao.coursesapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.WorkerThread;

import com.watshoulditake.waltermao.coursesapp.model.Course;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;
import com.watshoulditake.waltermao.coursesapp.model.SubjectMapping;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for database
 */
@WorkerThread
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

    public List<CourseSummary> getPrerequitesList(String courseCode) throws JSONException {
        return getJSONCourseSummaryList(courseCode, CoursesDBSchema.Cols.PREREQS_LIST);
    }

    public List<CourseSummary> getFutureCourses(String courseCode) throws JSONException {
        return getJSONCourseSummaryList(courseCode, CoursesDBSchema.Cols.FUTURE_COURSES_LIST);
    }

    public List<CourseSummary> querySubject(String subject) {
        CourseCursorWrapper cursorWrapper = mDbHelper.queryCourses(null,
                CoursesDBSchema.Cols.SUBJECT + "= ?",
                new String[]{subject},
                null, null,
                CoursesDBSchema.Cols.CATOLOG_NUMBER + " ASC");
        return cursorWrapper.getCourseSummaries();
    }

    public Course getCourseDetail(String courseCode) {
        CourseCursorWrapper cursorWrapper = mDbHelper.queryCourses(null,
                CoursesDBSchema.Cols.COURSE_CODE + "= ?",
                new String[]{courseCode},
                null, null, null);
        return cursorWrapper.getCourseDetails();
    }

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

    public List<CourseSummary> querySearchTerm(String searchTerm) {
        String sqlSearchTerm = "%" + searchTerm + "%";
        String orderBy = CoursesDBSchema.Cols.COURSE_CODE + " = \"" + searchTerm + "\" DESC," +
                CoursesDBSchema.Cols.COURSE_CODE + " LIKE \"%" + searchTerm + "%\" DESC," +
                CoursesDBSchema.Cols.TITLE + " = \"" + searchTerm + "\" DESC," +
                CoursesDBSchema.Cols.TITLE + " LIKE \"%" + searchTerm + "%\" DESC," +
                CoursesDBSchema.Cols.DESCRIPTION + " = \"" + searchTerm + "\" DESC," +
                CoursesDBSchema.Cols.DESCRIPTION + " LIKE \"%" + searchTerm + "%\" DESC";

        CourseCursorWrapper cursorWrapper = mDbHelper.queryCourses(null,
                CoursesDBSchema.Cols.COURSE_CODE + " LIKE ? COLLATE NOCASE" +
                        " OR " + CoursesDBSchema.Cols.COURSE_CODE + " LIKE ? COLLATE NOCASE" +
                        " OR " + CoursesDBSchema.Cols.TITLE + " LIKE ? COLLATE NOCASE" +
                        " OR " + CoursesDBSchema.Cols.DESCRIPTION + " LIKE ? COLLATE NOCASE",
                new String[]{sqlSearchTerm, sqlSearchTerm.replaceAll("\\s+", ""), sqlSearchTerm, sqlSearchTerm},
                null, null, orderBy);
        return cursorWrapper.getCourseSummaries();
    }

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
    private List<CourseSummary> getJSONCourseSummaryList(String courseCode, String column) throws JSONException {
        CourseCursorWrapper cursorWrapper = mDbHelper.queryCourses(
                new String[]{column},
                CoursesDBSchema.Cols.COURSE_CODE + "=?",
                new String[]{courseCode},
                null,
                null,
                null);
        List<String> prereqCourseCodes = cursorWrapper.getCourseCodeStringsFromJSON();
        if (prereqCourseCodes == null) {
            return new ArrayList<>();
        }

        List<CourseSummary> prereqs = new ArrayList<>();
        for (String prereqCourseCode : prereqCourseCodes) {
            CourseSummary prereqSummary = getSingleCourseSummary(prereqCourseCode);
            if (prereqSummary != null) {
                prereqs.add(prereqSummary);
            }
        }
        return prereqs;
    }

    /**
     * @return map of subject code to subject full name
     */
    public List<SubjectMapping> getSubjectMapping() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(SubjectDBSchema.TABLE_NAME,
                new String[]{SubjectDBSchema.Cols.SUBJECT_CODE, SubjectDBSchema.Cols.SUBJECT_NAME},
                null, null, null, null,
                SubjectDBSchema.Cols.SUBJECT_CODE + " ASC");
        List<SubjectMapping> subjectMappings = new ArrayList<>();
        while (cursor.moveToNext()) {
            String subjectCode = cursor.getString(cursor.getColumnIndex(SubjectDBSchema.Cols.SUBJECT_CODE));
            String subjectName = cursor.getString(cursor.getColumnIndex(SubjectDBSchema.Cols.SUBJECT_NAME));
            subjectMappings.add(new SubjectMapping(subjectCode, subjectName));
        }
        cursor.close();
        return subjectMappings;
    }

}
