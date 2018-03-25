package com.watshoulditake.waltermao.coursesapp.observers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

public abstract class CourseChangedObserver extends Fragment{



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private static class CourseChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    abstract void receiveCourseChangedBroadcast(CourseSummary summary);

    abstract void sendCourseChangedBroadcast();

}
