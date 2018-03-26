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
import com.watshoulditake.waltermao.coursesapp.loaders.CourseListLoader;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

import java.util.List;


public abstract class BaseCourseListFragment extends BaseCourseFragment {

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
        if (mCourseSummaries.size() != 0) {
            mDescriptionText.setText(getListDescription());
            if (mAdapter == null || mRecyclerView.getAdapter() == null) {
                mAdapter = new CourseSummariesAdapter(mCourseSummaries);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setData(mCourseSummaries);
                mAdapter.notifyDataSetChanged();
            }
        } else {
            mDescriptionText.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            getView().findViewById(R.id.empty_text).setVisibility(View.VISIBLE);
        }
    }

    @Override
    void initialiseViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                ((LinearLayoutManager) mRecyclerView.getLayoutManager()).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mDescriptionText = view.findViewById(R.id.list_description);
    }

    void sendCourseChangedBroadcast(CourseSummary courseSummary) {
        Intent intent = new Intent();
        intent.setAction(BaseCourseFragment.COURSE_CHANGED_ACTION);
        intent.putExtra(COURSE_SUMMARY_ARG, courseSummary);
        getContext().sendBroadcast(intent);

        if (mChangeTabEventListener != null) {
            mChangeTabEventListener.changeToPage(CourseDetailPagerFragment.ABOUT_PAGE_POSITION);
        }
    }

    abstract String getListDescription();

    abstract CourseListLoader getListDataLoader();

    /////////////////////////////// INNER CLASSES //////////////////////////////

    private class CourseSummaryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CourseSummary mCourseSummary;
        private TextView mCourseCodeText;
        private TextView mTitleText;

        public CourseSummaryHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_course_summary, parent, false));
            mCourseCodeText = itemView.findViewById(R.id.course_code);
            mTitleText = itemView.findViewById(R.id.course_title);
            itemView.setOnClickListener(this);
        }

        public void bind(CourseSummary courseSummary) {
            mCourseSummary = courseSummary;
            mCourseCodeText.setText(courseSummary.getCourseCode());
            mTitleText.setText(courseSummary.getTitle());
        }

        @Override
        public void onClick(View view) {
            sendCourseChangedBroadcast(mCourseSummary);
        }
    }

    private class CourseSummariesAdapter extends RecyclerView.Adapter<CourseSummaryHolder> {

        private List<CourseSummary> mData;

        public CourseSummariesAdapter(List<CourseSummary> data) {
            mData = data;
        }

        public void setData(List<CourseSummary> data) {
            mData = data;
        }

        @NonNull
        @Override
        public CourseSummaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new CourseSummaryHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CourseSummaryHolder holder, int position) {
            holder.bind(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

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
