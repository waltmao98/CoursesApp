package com.watshoulditake.waltermao.coursesapp.loaders;

import android.content.Context;
import android.support.annotation.Nullable;

import com.watshoulditake.waltermao.coursesapp.database.CoursesDao;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

import java.util.List;

/**
 * Created by waltermao on 2018-03-31.
 */

public class SearchTermLoader extends BaseLoader<List<CourseSummary>> {

    private String mSearchTerm;

    public SearchTermLoader(Context context, String searchTerm) {
        super(context);
        mSearchTerm = searchTerm;
    }

    @Nullable
    @Override
    public List<CourseSummary> loadInBackground() {
        return CoursesDao.getInstance(getContext()).querySearchTerm(mSearchTerm);
    }
}
