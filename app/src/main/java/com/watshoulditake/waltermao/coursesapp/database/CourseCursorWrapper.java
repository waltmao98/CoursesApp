package com.watshoulditake.waltermao.coursesapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.watshoulditake.waltermao.coursesapp.model.Course;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;
import com.watshoulditake.waltermao.coursesapp.utils.CourseJSONUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Cursor wrapper that converts a cursor into course model objects
 */
public class CourseCursorWrapper extends CursorWrapper {

    public CourseCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * Converts the single json array string item in this cursor to Java list.
     * Requires: cursor contains a single item (single row and single column) that is a json array string
     * @return Java list representation of the JSON array in this cursor
     * @throws JSONException if the queried value is not a JSONArray string
     * @throws RuntimeException if query returns more than 1 column
     */
    public List<CourseSummary> getCourseSummariesFromJSON() throws JSONException {
        if(getColumnCount() > 1) {
            throw new RuntimeException("Invalid format: cannot call getCourseSummariesFromJSON() on a cursor with more than 1 columns (there are " + getColumnCount() + " columns");
        }

        if(moveToNext()) {
            String jsonCourseSummary = getString(0);
            return CourseJSONUtils.JSONArrayToList(jsonCourseSummary);
        } else {
            return null;
        }
    }

    /**
     * @return list of course summaries for every course in COURSES table
     */
    public List<CourseSummary> getCourseSummaries() {
        List<CourseSummary> courseSummaries = new ArrayList<>();
        while(moveToNext()) {
            courseSummaries.add(convertCursorToCourseSummary(this));
        }
        return courseSummaries;
    }

    /**
     * @return course details of the single course in this cursor
     * @throws RuntimeException if called on a cursor with > 1 row
     */
    public Course getCourseDetails() throws RuntimeException {
        if(getCount() > 1) {
            throw new RuntimeException("Cannot call CourseCursorWrapper.getCourseDetails() on a list of courses. Please use CourseCursorWrapper.getCourseSummaries() instead");
        }
        if(!moveToNext()) {
            return null;
        }
        return convertCursorToCourse(this);
    }

    private static CourseSummary convertCursorToCourseSummary(Cursor cursor) {
        CourseSummary summary = new CourseSummary();
        summary.setCourseCode(cursor.getString(cursor.getColumnIndex(DBSchema.Cols.COURSE_CODE)));
        summary.setSubject(cursor.getString(cursor.getColumnIndex(DBSchema.Cols.SUBJECT)));
        summary.setTitle(cursor.getString(cursor.getColumnIndex(DBSchema.Cols.TITLE)));
        summary.setCatalogNumber(cursor.getString(cursor.getColumnIndex(DBSchema.Cols.CATOLOG_NUMBER)));
        return summary;
    }
    
    private static Course convertCursorToCourse(Cursor cursor) {
        Course course = new Course();
        course.setCourseCode(cursor.getString(cursor.getColumnIndex(DBSchema.Cols.COURSE_CODE)));
        course.setTitle(cursor.getString(cursor.getColumnIndex(DBSchema.Cols.TITLE)));
        course.setSubject(cursor.getString(cursor.getColumnIndex(DBSchema.Cols.SUBJECT)));
        course.setCatalogNumber(cursor.getString(cursor.getColumnIndex(DBSchema.Cols.CATOLOG_NUMBER)));
        course.setUnits(cursor.getDouble(cursor.getColumnIndex(DBSchema.Cols.UNITS)));
        course.setDescription(cursor.getString(cursor.getColumnIndex(DBSchema.Cols.DESCRIPTION)));
        course.setPrereqsString(cursor.getString(cursor.getColumnIndex(DBSchema.Cols.PREREQS_STRING)));
        course.setAntiRequisites(cursor.getString(cursor.getColumnIndex(DBSchema.Cols.ANTIREQS)));
        course.setNotes(cursor.getString(cursor.getColumnIndex(DBSchema.Cols.NOTES)));
        course.setIsOnline(cursor.getInt(cursor.getColumnIndex(DBSchema.Cols.IS_ONLINE)) == 1);
        course.setURL(cursor.getString(cursor.getColumnIndex(DBSchema.Cols.URL)));
        course.setIsFavourite(cursor.getInt(cursor.getColumnIndex(DBSchema.Cols.FAVOURITE)) == 1);

        try {
            List<String> instructions = CourseJSONUtils.JSONArrayToList(cursor.getString(cursor.getColumnIndex(DBSchema.Cols.INSTRUCTIONS)));
            List<String> prereqsList = CourseJSONUtils.JSONArrayToList(cursor.getString(cursor.getColumnIndex(DBSchema.Cols.PREREQS_LIST)));
            List<String> futureCoursesList = CourseJSONUtils.JSONArrayToList(cursor.getString(cursor.getColumnIndex(DBSchema.Cols.FUTURE_COURSES_LIST)));
            List<String> termsOffered = CourseJSONUtils.JSONArrayToList(cursor.getString(cursor.getColumnIndex(DBSchema.Cols.TERMS_OFFERED)));

            course.setInstructions(instructions);
            course.setPrereqsList(prereqsList);
            course.setFutureCourses(futureCoursesList);
            course.setTermsOffered(termsOffered);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return course;
    }

}
