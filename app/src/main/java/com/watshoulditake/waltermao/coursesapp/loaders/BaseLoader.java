package com.watshoulditake.waltermao.coursesapp.loaders;

import android.content.Context;

public abstract class BaseLoader<T> extends android.support.v4.content.AsyncTaskLoader<T> {

    public BaseLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
