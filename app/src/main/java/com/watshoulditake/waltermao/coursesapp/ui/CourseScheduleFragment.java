package com.watshoulditake.waltermao.coursesapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.loaders.CourseScheduleLoader;
import com.watshoulditake.waltermao.coursesapp.model.CourseSchedule;
import com.watshoulditake.waltermao.coursesapp.ui.base.BaseCourseListenerFragment;

import java.util.List;


public class CourseScheduleFragment extends BaseCourseListenerFragment<List<CourseSchedule>> {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_course_list, container, false);
    }

    @Override
    public void updateUI() {

    }

    @Override
    public void initialiseViews(View view) {

    }

    @Override
    public Loader<List<CourseSchedule>> getDataLoader() {
        return new CourseScheduleLoader(getContext(), getKey().getSubject(), getKey().getCatalogNumber());
    }

}
