package com.watshoulditake.waltermao.coursesapp.loaders;

import android.content.Context;

import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

import java.util.List;

public abstract class CourseListLoader extends BaseLoader<List<CourseSummary>> {

    public CourseListLoader(Context context) {
        super(context);
    }
}
