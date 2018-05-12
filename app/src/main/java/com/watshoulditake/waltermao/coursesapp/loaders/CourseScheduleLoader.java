package com.watshoulditake.waltermao.coursesapp.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
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
            JSONObject responseJSON = new JSONObject(responseJSONString != null ? responseJSONString : "");
            return convertJSONToSchedulesList(responseJSON);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error in parsing course schedule json", e);
            return null;
        }
    }

    private List<CourseSchedule> convertJSONToSchedulesList(@NonNull JSONObject responseJSON) throws JSONException {
        if (!responseJSON.has(JSONKeys.DATA)) {
            throw new JSONException("Response JSON is missing data");
        }
        JSONArray courseSchedulesJSON = responseJSON.optJSONArray("data");
        List<CourseSchedule> courseSchedules = new ArrayList<>();
        for (int i = 0; i < courseSchedulesJSON.length(); ++i) {
            CourseSchedule schedule = convertJSONToSchedule((courseSchedulesJSON.optJSONObject(i)));
            courseSchedules.add(schedule);
        }
        return courseSchedules;
    }

    private CourseSchedule convertJSONToSchedule(JSONObject courseScheduleJSON) throws JSONException {
        CourseSchedule schedule = new CourseSchedule();
        schedule.setCourseCode(courseScheduleJSON.optString(JSONKeys.SUBJECT)
                + courseScheduleJSON.optString(JSONKeys.CATALOG_NUMBER));
        if (!isNullLiteral(courseScheduleJSON, JSONKeys.SECTION)) {
            schedule.setSection(courseScheduleJSON.optString(JSONKeys.SECTION));
        }
        if (!isNullLiteral(courseScheduleJSON, JSONKeys.CAMPUS)) {
            schedule.setCampus(courseScheduleJSON.optString(JSONKeys.CAMPUS));
        }
        schedule.setCapacity(courseScheduleJSON.optInt(JSONKeys.CAPACITY));
        schedule.setOccupied(courseScheduleJSON.optInt(JSONKeys.OCCUPIED));
        schedule.setTerm(courseScheduleJSON.optInt(JSONKeys.TERM));

        JSONObject classJSON = courseScheduleJSON.optJSONArray(JSONKeys.CLASSES).optJSONObject(0);

        // date
        JSONObject dateJSON = classJSON.optJSONObject(JSONKeys.DATE);
        if (!isNullLiteral(dateJSON, JSONKeys.START_TIME)) {
            schedule.setStartTime(dateJSON.optString(JSONKeys.START_TIME));
        }
        if (!isNullLiteral(dateJSON, JSONKeys.END_TIME)) {
            schedule.setEndTime(dateJSON.optString(JSONKeys.END_TIME));
        }
        if (!isNullLiteral(dateJSON, JSONKeys.WEEKDAYS)) {
            schedule.setWeekDays(dateJSON.optString(JSONKeys.WEEKDAYS));
        }

        // location
        JSONObject locationJSON = classJSON.optJSONObject(JSONKeys.LOCATION);
        ClassLocation classLocation = new ClassLocation();
        if (!isNullLiteral(locationJSON, JSONKeys.BUILDING)) {
            classLocation.setBuilding(locationJSON.optString(JSONKeys.BUILDING));
        }
        if (!isNullLiteral(locationJSON, JSONKeys.ROOM)) {
            classLocation.setRoom(locationJSON.optString(JSONKeys.ROOM));
        }
        schedule.setClassLocation(classLocation);

        JSONArray instructorsJSON = classJSON.optJSONArray(JSONKeys.INSTRUCTORS);

        List<String> instructors = CourseJSONUtils.JSONArrayToList(instructorsJSON);
        schedule.setInstructors(instructors);

        return schedule;
    }

    /**
     * UW API returns JSON object with "null" as keys. This method exists to check for this obscurity
     *
     * @return true if the literal string "null" is the value for the given key, else false
     */
    private boolean isNullLiteral(JSONObject jsonObject, String key) {
        return jsonObject.optString(key).equals("null");
    }
}
