package com.watshoulditake.waltermao.coursesapp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.View;

import com.watshoulditake.waltermao.coursesapp.interfaces.ChangeTabEventListener;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

/**
 * Base class of all fragments that are associated with a single course.
 * Child classes have the following common traits:
 * - a necessary CourseSummary instance to identify which course to associate with
 * - a way to broadcast a change in the current course to other child classes
 * - a way to receive broadcasts from other child classes
 */
public abstract class BaseCourseListenerFragment extends BaseFragment {

    static final String COURSE_SUMMARY_ARG = "course_summary_arg";
    static final String COURSE_CHANGED_ACTION = "com.watshoulditake.waltermao.coursesapp.broadcast.COURSE_CHANGED_ACTION";

    private CourseChangedReceiver mReceiver;
    private CourseSummary mCourseSummary;
    ChangeTabEventListener mChangeTabEventListener;

    /**
     * @param fragment      the instance of subclass of BaseCourseListenerFragment to be created
     * @param courseSummary the course summary that the created fragment should be associated with
     * @return concrete instance of a subclass of BaseCourseListenerFragment with course summary argument
     */
    public static BaseCourseListenerFragment createFragment(BaseCourseListenerFragment fragment, CourseSummary courseSummary) {
        Bundle args = new Bundle();
        args.putParcelable(COURSE_SUMMARY_ARG, courseSummary);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCourseSummary = getArguments().getParcelable(COURSE_SUMMARY_ARG);
        initialiseViews(view);
        updateData();
    }

    @Override
    public void onStart() {
        super.onStart();
        mReceiver = new CourseChangedReceiver();
        IntentFilter filter = new IntentFilter(COURSE_CHANGED_ACTION);
        getContext().registerReceiver(mReceiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        getContext().unregisterReceiver(mReceiver);
    }

    CourseSummary getCourseSummary() {
        return mCourseSummary;
    }

    String getCourseCode() {
        return mCourseSummary.getCourseCode();
    }

    void setChangeTabEventListener(ChangeTabEventListener listener) {
        mChangeTabEventListener = listener;
    }

    @CallSuper
    void receiveCourseChangedBroadcast(CourseSummary summary) {
        mCourseSummary = summary;
        updateData();
    }

    /**
     * Re-fetches data associated with the current course summary
     */
    abstract void updateData();

    /**
     * Should be called once the data in updateData() has been fetched - somewhere in the method chain of
     * a call to updateData()
     */
    abstract void updateUI();

    /**
     * Get access to all views via rootView.findViewById
     */
    abstract void initialiseViews(View view);

    private class CourseChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            CourseSummary courseSummary = intent.getParcelableExtra(COURSE_SUMMARY_ARG);
            receiveCourseChangedBroadcast(courseSummary);
        }
    }

}