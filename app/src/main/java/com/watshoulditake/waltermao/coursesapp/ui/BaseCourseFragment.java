package com.watshoulditake.waltermao.coursesapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

public abstract class BaseCourseFragment extends Fragment {

    static final String COURSE_SUMMARY_ARG = "course_code";

    /**
     * @param fragment the instance of subclass of BaseCourseFragment to be created
     * @param courseSummary the course summary that the created fragment should be associated with
     * @return concrete instance of a subclass of BaseCourseFragment with course summary argument
     */
    public static BaseCourseFragment createFragment(BaseCourseFragment fragment, CourseSummary courseSummary) {
        Bundle args = new Bundle();
        args.putParcelable(COURSE_SUMMARY_ARG,courseSummary);
        fragment.setArguments(args);
        return fragment;
    }

}
