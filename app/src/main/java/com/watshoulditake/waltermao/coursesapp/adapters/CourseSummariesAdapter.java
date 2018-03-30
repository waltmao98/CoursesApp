package com.watshoulditake.waltermao.coursesapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

import java.lang.ref.WeakReference;
import java.util.List;

public class CourseSummariesAdapter extends RecyclerView.Adapter<CourseSummariesAdapter.CourseSummaryHolder> {

    private static final String LOG_TAG = CourseSummariesAdapter.class.getSimpleName();

    private List<CourseSummary> mData;
    private WeakReference<Context> mContextReference;

    public CourseSummariesAdapter(List<CourseSummary> data, Context context) {
        mData = data;
        mContextReference = new WeakReference<>(context);
    }

    public void setData(List<CourseSummary> data) {
        mData = data;
    }

    @NonNull
    @Override
    public CourseSummaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContextReference.get());
        return new CourseSummaryHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseSummaryHolder holder, int position) {
        if (mData.get(position) == null) {
            Log.e(LOG_TAG, "item is null");
        }
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class CourseSummaryHolder extends RecyclerView.ViewHolder {

        private TextView mCourseCodeText;
        private TextView mTitleText;

        public CourseSummaryHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_course_summary, parent, false));
            mCourseCodeText = itemView.findViewById(R.id.course_code);
            mTitleText = itemView.findViewById(R.id.course_title);
        }

        public void bind(CourseSummary courseSummary) {
            mCourseCodeText.setText(courseSummary.getCourseCode());
            mTitleText.setText(courseSummary.getTitle());
        }

    }

}
