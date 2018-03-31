package com.watshoulditake.waltermao.coursesapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseCourseListenerListFragment extends BaseCourseListenerFragment<List<CourseSummary>> {

    private RecyclerView mRecyclerView;
    private CourseSummariesAdapter mAdapter;
    private TextView mDescriptionText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_course_list, container, false);
    }

    @Override
    void updateUI() {
        boolean showViews = getData() != null && getData().size() != 0;
        if (showViews) {
            mDescriptionText.setText(getListDescription());
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
                        sendCourseChangedBroadcast(getData().get(position));
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

}
