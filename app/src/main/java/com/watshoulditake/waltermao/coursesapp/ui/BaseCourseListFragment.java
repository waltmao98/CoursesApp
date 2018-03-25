package com.watshoulditake.waltermao.coursesapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.loaders.BaseLoader;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

import java.util.List;


public abstract class BaseCourseListFragment extends BaseCourseFragment {

    private static final int COURSE_LIST_LOADER = 1239;

    RecyclerView mRecyclerView;
    List<CourseSummary> mData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_course_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    void updateData() {
        getLoaderManager().initLoader(COURSE_LIST_LOADER,null,new CourseListLoaderCallbacks());
    }

    @Override
    void updateUI() {
        // TODO: set recyclerview data
    }

    abstract void setListDescription();

    abstract List<CourseSummary> getListData();

    /////////////////////////////// INNER CLASSES //////////////////////////////

    private class CourseListLoader extends BaseLoader<List<CourseSummary>> {

        public CourseListLoader(Context context) {
            super(context);
        }

        @Nullable
        @Override
        public List<CourseSummary> loadInBackground() {
            return getListData();
        }
    }

    private class CourseListLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<CourseSummary>> {
        @NonNull
        @Override
        public Loader<List<CourseSummary>> onCreateLoader(int id, Bundle args) {
            return new CourseListLoader(getContext());
        }

        @Override
        public void onLoadFinished(@NonNull Loader<List<CourseSummary>> loader, List<CourseSummary> data) {
            mData = data;
            updateUI();
        }

        @Override
        public void onLoaderReset(@NonNull Loader<List<CourseSummary>> loader) {

        }
    }

}
