package com.watshoulditake.waltermao.coursesapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

import java.lang.ref.WeakReference;
import java.util.List;

public class CourseSummariesAdapter extends BaseListAdapter<CourseSummary> {

    private WeakReference<Context> mContextReference;

    public CourseSummariesAdapter(List<CourseSummary> data, Context context) {
        super(data);
        mContextReference = new WeakReference<>(context);
    }

    @NonNull
    @Override
    public CourseSummaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContextReference.get());
        return new CourseSummaryHolder(inflater, parent);
    }

    class CourseSummaryHolder extends BaseViewHolder {

        private TextView mCourseCodeText;
        private TextView mTitleText;

        public CourseSummaryHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_course_summary, parent, false));
            mCourseCodeText = itemView.findViewById(R.id.course_code);
            mTitleText = itemView.findViewById(R.id.course_title);
        }

        @Override
        public void bind(CourseSummary courseSummary) {
            mCourseCodeText.setText(courseSummary.getCourseCode());
            mTitleText.setText(courseSummary.getTitle());
        }

    }

}
