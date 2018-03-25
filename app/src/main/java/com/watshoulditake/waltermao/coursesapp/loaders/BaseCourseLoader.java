package com.watshoulditake.waltermao.coursesapp.loaders;

import android.content.Context;

/**
 * Created by waltermao on 2018-03-24.
 */

public abstract class BaseCourseLoader<T> extends android.support.v4.content.AsyncTaskLoader<T> {

    public BaseCourseLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
