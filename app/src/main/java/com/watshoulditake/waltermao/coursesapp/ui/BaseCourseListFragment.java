package com.watshoulditake.waltermao.coursesapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.adapters.CourseSummariesAdapter;
import com.watshoulditake.waltermao.coursesapp.listeners.RecyclerItemClickListener;
import com.watshoulditake.waltermao.coursesapp.loaders.CourseListLoader;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseCourseListFragment extends BaseCourseListenerFragment {

    private static final int COURSE_LIST_LOADER = 1239;

    private RecyclerView mRecyclerView;
    private List<CourseSummary> mCourseSummaries;
    private CourseSummariesAdapter mAdapter;
    private TextView mDescriptionText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_course_list, container, false);
    }

    @Override
    void updateData() {
        getLoaderManager().restartLoader(COURSE_LIST_LOADER, null, new CourseListLoaderCallbacks());
    }

    @Override
    void updateUI() {
        boolean showViews = mCourseSummaries != null && mCourseSummaries.size() != 0;
        if (showViews) {
            mDescriptionText.setText(getListDescription());
            if (mAdapter == null || mRecyclerView.getAdapter() != mAdapter) {
                mAdapter = new CourseSummariesAdapter(mCourseSummaries, getContext());
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setData(mCourseSummaries);
                mAdapter.notifyDataSetChanged();
            }
        }
        setViewsVisibility(showViews);
    }

    @Override
    void initialiseViews(View view) {
        mDescriptionText = view.findViewById(R.id.list_description);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                ((LinearLayoutManager) mRecyclerView.getLayoutManager()).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mAdapter = new CourseSummariesAdapter(new ArrayList<CourseSummary>(), getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        sendCourseChangedBroadcast(mCourseSummaries.get(position));
                        if (mChangeTabEventListener != null) {
                            mChangeTabEventListener.changeToPage(CourseDetailPagerFragment.ABOUT_PAGE_POSITION);
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

    void sendCourseChangedBroadcast(CourseSummary courseSummary) {
        Intent intent = new Intent();
        intent.setAction(BaseCourseListenerFragment.COURSE_CHANGED_ACTION);
        intent.putExtra(COURSE_SUMMARY_ARG, courseSummary);
        getContext().sendBroadcast(intent);
    }

    void setViewsVisibility(boolean shown) {
        mRecyclerView.setVisibility(shown ? View.VISIBLE : View.GONE);
        mDescriptionText.setVisibility(shown ? View.VISIBLE : View.GONE);
        getView().findViewById(R.id.empty_text).setVisibility(shown ? View.GONE : View.VISIBLE);
    }

    abstract String getListDescription();

    abstract CourseListLoader getListDataLoader();

    /////////////////////////////// INNER CLASSES //////////////////////////////

    private class CourseListLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<CourseSummary>> {
        @NonNull
        @Override
        public Loader<List<CourseSummary>> onCreateLoader(int id, Bundle args) {
            return getListDataLoader();
        }

        @Override
        public void onLoadFinished(@NonNull Loader<List<CourseSummary>> loader, List<CourseSummary> data) {
            mCourseSummaries = data;
            updateUI();
        }

        @Override
        public void onLoaderReset(@NonNull Loader<List<CourseSummary>> loader) {

        }
    }
}
