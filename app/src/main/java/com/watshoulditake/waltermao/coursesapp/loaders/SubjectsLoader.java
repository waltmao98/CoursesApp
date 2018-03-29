package com.watshoulditake.waltermao.coursesapp.loaders;

import android.content.Context;
import android.support.annotation.Nullable;

import com.watshoulditake.waltermao.coursesapp.database.CoursesDao;

import java.util.Map;

public class SubjectsLoader extends BaseLoader<Map<String, String>> {
    public SubjectsLoader(Context context) {
        super(context);
    }

    @Nullable
    @Override
    public Map<String, String> loadInBackground() {
        return CoursesDao.getInstance(getContext()).getSubjectMapping();
    }
}
