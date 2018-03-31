package com.watshoulditake.waltermao.coursesapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.adapters.CourseSummariesAdapter;
import com.watshoulditake.waltermao.coursesapp.listeners.RecyclerItemClickListener;
import com.watshoulditake.waltermao.coursesapp.loaders.CourseSubjectLoader;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;
import com.watshoulditake.waltermao.coursesapp.model.SubjectMapping;

import java.util.ArrayList;
import java.util.List;

public class SubjectListFragment extends BaseFragment {

    private static final String LOG_TAG = SubjectListFragment.class.getSimpleName();
    private static final String SUBJECT_MAPPING_EXTRA = "subject_mapping";
    private static final int SUBJECT_LIST_LOADER_ID = 4729;

    private SubjectMapping mSubject;
    private List<CourseSummary> mCourseSummaries;
    private TextView mDescriptionText;
    private RecyclerView mRecyclerView;
    private CourseSummariesAdapter mAdapter;

    public static SubjectListFragment createFragment(SubjectMapping subjectMapping) {
        SubjectListFragment fragment = new SubjectListFragment();
        Bundle args = new Bundle();
        args.putParcelable(SUBJECT_MAPPING_EXTRA, subjectMapping);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text_and_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSubject = getArguments().getParcelable(SUBJECT_MAPPING_EXTRA);
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
                        BaseCourseListenerFragment fragment = BaseCourseListenerFragment.createFragment(
                                new CourseDetailPagerFragment(), mCourseSummaries.get(position));
                        startFragment(fragment, null);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );

        mDescriptionText.setText(getString(R.string.subject_list_description, mSubject.getSubjectName()));
        Log.d(LOG_TAG, mSubject.getSubjectCode() + " is being displayed");
        getLoaderManager().restartLoader(SUBJECT_LIST_LOADER_ID, null, new CourseSubjectListLoaderCallbacks());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.action_bar_list_menu, menu);
    }

    private void updateUI() {
        setTitle(getString(R.string.subject_list_title, mSubject.getSubjectCode()));
        boolean showViews = mCourseSummaries != null && mCourseSummaries.size() != 0;
        if (showViews) {
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

    void setViewsVisibility(boolean shown) {
        mRecyclerView.setVisibility(shown ? View.VISIBLE : View.GONE);
        mDescriptionText.setVisibility(shown ? View.VISIBLE : View.GONE);
        getView().findViewById(R.id.empty_text).setVisibility(shown ? View.GONE : View.VISIBLE);
    }

    private class CourseSubjectListLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<CourseSummary>> {
        @NonNull
        @Override
        public Loader<List<CourseSummary>> onCreateLoader(int id, Bundle args) {
            return new CourseSubjectLoader(getContext(), mSubject.getSubjectCode());
        }

        @Override
        public void onLoadFinished(@NonNull Loader<List<CourseSummary>> loader, List<CourseSummary> data) {
            mCourseSummaries = data;
            if (data != null && data.size() > 0) {
                for (int i = 0; i < data.size(); ++i) {
                    if (data.get(i) == null) {
                        Log.d(LOG_TAG, "data.get(" + i + ") is null");
                    }
                }
            }
            updateUI();
        }

        @Override
        public void onLoaderReset(@NonNull Loader<List<CourseSummary>> loader) {

        }
    }
}
