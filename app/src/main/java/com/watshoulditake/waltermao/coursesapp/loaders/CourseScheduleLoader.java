package com.watshoulditake.waltermao.coursesapp.loaders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.watshoulditake.waltermao.coursesapp.model.ClassLocation;
import com.watshoulditake.waltermao.coursesapp.model.CourseSchedule;
import com.watshoulditake.waltermao.coursesapp.utils.CourseJSONUtils;
import com.watshoulditake.waltermao.coursesapp.utils.JSONKeys;
import com.watshoulditake.waltermao.coursesapp.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CourseScheduleLoader extends BaseLoader<List<CourseSchedule>> {

    private static final String LOG_TAG = CourseScheduleLoader.class.getSimpleName();

    public static final String COURSE_SCHEDULE_BASE_URL = NetworkUtils.UW_API_BASE_COURSE_URL;

    private String mSubject;
    private String mCatalogNumber;

    public CourseScheduleLoader(Context context, String subject, String catalogNumber) {
        super(context);
        mSubject = subject;
        mCatalogNumber = catalogNumber;
    }

    @Nullable
    @Override
    public List<CourseSchedule> loadInBackground() {
        String requestUrl = COURSE_SCHEDULE_BASE_URL +
                "/" + mSubject +
                "/" + mCatalogNumber +
                "/schedule.json" +
                "?key=" + NetworkUtils.UW_API_KEY;
        String responseJSONString = NetworkUtils.executeRequest(requestUrl);
        try {
            JSONObject responseJSON = new JSONObject(responseJSONString);
            return convertJSONToSchedulesList(responseJSON);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error in parsing course schedule json", e);
            return null;
        }
    }

    private List<CourseSchedule> convertJSONToSchedulesList(JSONObject responseJSON) throws JSONException {
        if (!responseJSON.has(JSONKeys.DATA)) {
            throw new JSONException("Response JSON is missing data");
        }
        JSONArray courseSchedulesJSON = responseJSON.getJSONArray("data");
        List<CourseSchedule> courseSchedules = new ArrayList<>();
        for (int i = 0; i < courseSchedulesJSON.length(); ++i) {
            CourseSchedule schedule = convertJSONToSchedule((courseSchedulesJSON.getJSONObject(i)));
            courseSchedules.add(schedule);
        }
        return courseSchedules;
    }

    private CourseSchedule convertJSONToSchedule(JSONObject courseScheduleJSON) throws JSONException {
        CourseSchedule schedule = new CourseSchedule();
        schedule.setCourseCode(courseScheduleJSON.getString(JSONKeys.SUBJECT)
                + courseScheduleJSON.getString(JSONKeys.CATALOG_NUMBER));
        schedule.setSection(courseScheduleJSON.getString(JSONKeys.SECTION));
        schedule.setCampus(courseScheduleJSON.getString(JSONKeys.CAMPUS));
        schedule.setCapacity(courseScheduleJSON.getInt(JSONKeys.CAPACITY));
        schedule.setOccupied(courseScheduleJSON.getInt(JSONKeys.OCCUPIED));
        schedule.setTerm(courseScheduleJSON.getInt(JSONKeys.TERM));

        JSONObject classJSON = courseScheduleJSON.getJSONArray(JSONKeys.CLASSES).getJSONObject(0);

        JSONObject dateJSON = classJSON.getJSONObject(JSONKeys.DATE);
        schedule.setStartTime(dateJSON.getString(JSONKeys.START_TIME));
        schedule.setEndTime(dateJSON.getString(JSONKeys.END_TIME));
        schedule.setWeekDays(dateJSON.getString(JSONKeys.WEEKDAYS));

        JSONObject locationJSON = classJSON.getJSONObject(JSONKeys.LOCATION);
        ClassLocation classLocation = new ClassLocation();
        classLocation.setBuilding(locationJSON.getString(JSONKeys.BUILDING));
        classLocation.setRoom(locationJSON.getString(JSONKeys.ROOM));
        schedule.setClassLocation(classLocation);

        JSONArray instructorsJSON = classJSON.getJSONArray(JSONKeys.INSTRUCTORS);

        List<String> instructors = CourseJSONUtils.JSONArrayToList(instructorsJSON);
        schedule.setInstructors(instructors);

        return schedule;
    }
}
