package com.watshoulditake.waltermao.coursesapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.adapters.BaseListAdapter;
import com.watshoulditake.waltermao.coursesapp.loaders.CourseScheduleLoader;
import com.watshoulditake.waltermao.coursesapp.model.CourseSchedule;
import com.watshoulditake.waltermao.coursesapp.ui.base.BaseCourseListenerFragment;

import java.util.ArrayList;
import java.util.List;


public class CourseScheduleFragment extends BaseCourseListenerFragment<List<CourseSchedule>> {

    private RecyclerView mRecyclerView;
    private ScheduleListAdapter mAdapter;
    private TextView mTopText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_list, container, false);
    }

    @Override
    public void updateUI() {
        mTopText.setText(getString(R.string.schedule_for_course, getKey().getCourseCode()));
        boolean showViews = getData() != null && getData().size() != 0;
        if (showViews) {
            if (mAdapter == null || mRecyclerView.getAdapter() != mAdapter) {
                mAdapter = new ScheduleListAdapter(getData());
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.setData(getData());
                mAdapter.notifyDataSetChanged();
            }
        }
        setViewsVisibility(showViews);
    }

    @Override
    public void initialiseViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                ((LinearLayoutManager) mRecyclerView.getLayoutManager()).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mAdapter = new ScheduleListAdapter(new ArrayList<CourseSchedule>());
        mRecyclerView.setAdapter(mAdapter);
        mTopText = view.findViewById(R.id.list_description);
        ((TextView) view.findViewById(R.id.empty_text)).setText(
                getString(R.string.no_schedule_found, getKey().getCourseCode()));
    }

    @Override
    public Loader<List<CourseSchedule>> getDataLoader() {
        return new CourseScheduleLoader(getContext(), getKey().getSubject(), getKey().getCatalogNumber());
    }

    private void setViewsVisibility(boolean showViews) {
        mRecyclerView.setVisibility(showViews ? View.VISIBLE : View.GONE);
        mTopText.setVisibility(showViews ? View.VISIBLE : View.GONE);
        getView().findViewById(R.id.empty_text).setVisibility(showViews ? View.GONE : View.VISIBLE);
    }

    class ScheduleListAdapter extends BaseListAdapter<CourseSchedule> {

        public ScheduleListAdapter(List<CourseSchedule> data) {
            super(data);
        }

        @NonNull
        @Override
        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new ScheduleViewHolder(inflater, parent);
        }

        class ScheduleViewHolder extends BaseListAdapter<CourseSchedule>.BaseViewHolder {

            private TextView mSectionText;
            private TextView mTimeText;
            private TextView mInstructorText;
            private TextView mWeekdaysText;
            private TextView mSeatsText;
            private TextView mLocationText;

            ScheduleViewHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.list_item_course_schedule, parent, false));
                mSectionText = itemView.findViewById(R.id.section);
                mTimeText = itemView.findViewById(R.id.time);
                mInstructorText = itemView.findViewById(R.id.instructor);
                mWeekdaysText = itemView.findViewById(R.id.weekdays);
                mSeatsText = itemView.findViewById(R.id.seats);
                mLocationText = itemView.findViewById(R.id.location);
            }

            @Override
            public void bind(@NonNull CourseSchedule item) {
                mSectionText.setText(item.getSection() != null ? item.getSection() : "");
                mTimeText.setText(item.getStartTime() != null && item.getEndTime() != null ?
                        getString(R.string.dash_binary_separator_format, item.getStartTime(), item.getEndTime())
                        : "");
                mInstructorText.setText(item.getInstructors() != null && item.getInstructors().size() > 0 ?
                        item.getInstructors().get(0) : "");
                mWeekdaysText.setText(item.getWeekDays() != null ? item.getWeekDays() : "");
                mSeatsText.setText(getString(R.string.number_spots_format, item.getOccupied(), item.getCapacity()));
                mLocationText.setText(item.getClassLocation() != null &&
                        item.getClassLocation().getBuilding() != null &&
                        item.getClassLocation().getRoom() != null ?
                        getString(R.string.dash_binary_separator_format,
                                item.getClassLocation().getBuilding() + item.getClassLocation().getRoom(),
                                item.getCampus()) :
                        "");
            }
        }
    }

}
