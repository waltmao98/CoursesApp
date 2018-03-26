package com.watshoulditake.waltermao.coursesapp.utils;

import java.util.List;

public class TextUtils {

    public static <T> String getCommaSeparatedList(List<T> list) {
        if (list == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size() - 1; ++i) {
            result.append(list.get(i)).append(", ");
        }
        result.append(list.get(list.size() - 1));
        return result.toString();
    }

    public static boolean isNullorEmpty(String str) {
        return str == null || str.isEmpty();
    }

}
