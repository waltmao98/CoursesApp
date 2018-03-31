package com.watshoulditake.waltermao.coursesapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class SubjectListFragment extends BaseFragment<SubjectMapping, List<CourseSummary>> {

    private TextView mDescriptionText;
    private RecyclerView mRecyclerView;
    private CourseSummariesAdapter mAdapter;

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.action_bar_list_menu, menu);
    }

    void updateUI() {
        setTitle(getString(R.string.subject_list_title, getKey().getSubjectCode()));
        boolean showViews = getData() != null && getData().size() != 0;
        if (showViews) {
            if (mAdapter == null || mRecyclerView.getAdapter() != mAdapter) {
                mAdapter = new CourseSummariesAdapter(getData(), getContext());
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setData(getData());
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
                        BaseFragment fragment = BaseFragment.createFragment(
                                new CourseDetailPagerFragment(), getData().get(position));
                        startFragment(fragment, null);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
        mDescriptionText.setText(getString(R.string.subject_list_description, getKey().getSubjectName()));
    }

    @Override
    Loader<List<CourseSummary>> getDataLoader() {
        return new CourseSubjectLoader(getContext(), getKey().getSubjectCode());
    }

    void setViewsVisibility(boolean shown) {
        mRecyclerView.setVisibility(shown ? View.VISIBLE : View.GONE);
        mDescriptionText.setVisibility(shown ? View.VISIBLE : View.GONE);
        getView().findViewById(R.id.empty_text).setVisibility(shown ? View.GONE : View.VISIBLE);
    }
}
