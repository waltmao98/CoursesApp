package com.watshoulditake.waltermao.coursesapp.utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for handling conversions between Java and JSON
 */
public class CourseJSONUtils {
    /**
     * @param jsonArrayString json array string representation of data
     * @return ArrayList representation of {@code jsonArrayString} data
     * @throws JSONException
     */
    public static <T> List<T> JSONArrayToList(String jsonArrayString) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonArrayString);
        List<T> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); ++i) {
            list.add((T) jsonArray.get(i));
        }
        return list;
    }
}
