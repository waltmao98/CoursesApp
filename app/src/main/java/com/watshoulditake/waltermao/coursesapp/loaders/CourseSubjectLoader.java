package com.watshoulditake.waltermao.coursesapp.loaders;

import android.content.Context;
import android.support.annotation.Nullable;

import com.watshoulditake.waltermao.coursesapp.database.CoursesDao;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

import java.util.List;

public class CourseSubjectLoader extends BaseLoader<List<CourseSummary>> {

    private String mSubjectCode;

    public CourseSubjectLoader(Context context, String subjectCode) {
        super(context);
        mSubjectCode = subjectCode;
    }

    @Nullable
    @Override
    public List<CourseSummary> loadInBackground() {
        return CoursesDao.getInstance(getContext()).querySubject(mSubjectCode);
    }
}
