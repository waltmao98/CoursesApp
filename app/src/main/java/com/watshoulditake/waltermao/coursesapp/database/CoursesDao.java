package com.watshoulditake.waltermao.coursesapp.database;

import android.content.Context;
import android.support.annotation.WorkerThread;

import com.watshoulditake.waltermao.coursesapp.model.Course;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

import org.json.JSONException;

import java.util.List;

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
        return getJSONCourseSummaryList(courseCode, DBSchema.Cols.PREREQS_LIST);
    }

    @WorkerThread
    public List<CourseSummary> getFutureCourses(String courseCode) throws JSONException {
        return getJSONCourseSummaryList(courseCode, DBSchema.Cols.FUTURE_COURSES_LIST);
    }

    @WorkerThread
    public List<CourseSummary> querySubject(String subject) {
        CourseCursorWrapper cursorWrapper = mDbHelper.queryCourses(null,
                DBSchema.Cols.SUBJECT + "= ?",
                new String[]{subject},
                null, null, null);
        return cursorWrapper.getCourseSummaries();
    }

    @WorkerThread
    public Course getCourseDetail(String courseCode) {
        CourseCursorWrapper cursorWrapper = mDbHelper.queryCourses(null,
                DBSchema.Cols.COURSE_CODE + "= ?",
                new String[]{courseCode},
                null, null, null);
        return cursorWrapper.getCourseDetails();
    }

    @WorkerThread
    public List<CourseSummary> querySearchTerm(String searchTerm) {
        String sqlSearchTerm = "%" + searchTerm + "%";
        CourseCursorWrapper cursorWrapper = mDbHelper.queryCourses(null,
                DBSchema.Cols.COURSE_CODE + " LIKE ? COLLATE NOCASE" +
                        " OR " + DBSchema.Cols.TITLE + " LIKE ? COLLATE NOCASE" +
                        " OR " + DBSchema.Cols.DESCRIPTION + " LIKE ? COLLATE NOCASE",
                new String[]{sqlSearchTerm, sqlSearchTerm, sqlSearchTerm},
                null, null, null);
        return cursorWrapper.getCourseSummaries();
    }

    @WorkerThread
    public List<CourseSummary> getAllCourses() {
        CourseCursorWrapper cursorWrapper = mDbHelper.queryCourses(
                null, null, null, DBSchema.Cols.SUBJECT, null, null);
        return cursorWrapper.getCourseSummaries();
    }

    @WorkerThread
    private List<CourseSummary> getJSONCourseSummaryList(String courseCode, String column) throws JSONException {
        CourseCursorWrapper cursorWrapper = mDbHelper.queryCourses(
                new String[]{column},
                DBSchema.Cols.COURSE_CODE + "=?",
                new String[]{courseCode},
                null,
                null,
                null);
        return cursorWrapper.getCourseSummariesFromJSON();
    }

}
