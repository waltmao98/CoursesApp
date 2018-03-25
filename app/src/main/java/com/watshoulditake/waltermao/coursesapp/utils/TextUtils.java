package com.watshoulditake.waltermao.coursesapp.utils;

import java.util.List;

public class TextUtils {

    public static <T> String getCommaSeparatedList(List<T> list) {
        if(list == null) {
            return "";
        }
        String result = "";
        for(int i = 0; i < list.size()-1; ++i) {
            result += list.get(i) + ", ";
        }
        result += list.get(list.size()-1);
        return result;
    }

}
