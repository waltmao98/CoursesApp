package com.watshoulditake.waltermao.coursesapp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.CallSuper;

import com.watshoulditake.waltermao.coursesapp.interfaces.ChangeTabEventListener;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

/**
 * Base class of all fragments that are associated with a single course.
 * Child classes have the following common traits:
 * - a necessary CourseSummary instance to identify which course to associate with
 * - a way to broadcast a change in the current course to other child classes
 * - a way to receive broadcasts from other child classes
 *
 * @param <D> typeof value this fragment will display (eg. List of CourseSummary)
 */
public abstract class BaseCourseListenerFragment<D> extends BaseDataFragment<CourseSummary, D> {

    static final String COURSE_SUMMARY_ARG = "course_summary_arg";
    static final String COURSE_CHANGED_ACTION = "com.watshoulditake.waltermao.coursesapp.broadcast.COURSE_CHANGED_ACTION";

    private CourseChangedReceiver mReceiver;
    ChangeTabEventListener mChangeTabEventListener;

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
        return getKey();
    }

    String getCourseCode() {
        return getKey().getCourseCode();
    }

    void setChangeTabEventListener(ChangeTabEventListener listener) {
        mChangeTabEventListener = listener;
    }

    @CallSuper
    void receiveCourseChangedBroadcast(CourseSummary summary) {
        setKey(summary);
        updateData();
    }

    private class CourseChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            CourseSummary courseSummary = intent.getParcelableExtra(COURSE_SUMMARY_ARG);
            receiveCourseChangedBroadcast(courseSummary);
        }
    }

}
