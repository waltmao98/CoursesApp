package com.watshoulditake.waltermao.coursesapp.loaders;

import android.content.Context;
import android.support.annotation.Nullable;

import com.watshoulditake.waltermao.coursesapp.database.CoursesDao;
import com.watshoulditake.waltermao.coursesapp.model.SubjectMapping;

import java.util.List;

public class SubjectsLoader extends BaseLoader<List<SubjectMapping>> {
    public SubjectsLoader(Context context) {
        super(context);
    }

    @Nullable
    @Override
    public List<SubjectMapping> loadInBackground() {
        return CoursesDao.getInstance(getContext()).getSubjectMapping();
    }
}
