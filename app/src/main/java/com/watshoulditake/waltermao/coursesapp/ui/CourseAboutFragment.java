package com.watshoulditake.waltermao.coursesapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.loaders.CourseAboutLoader;
import com.watshoulditake.waltermao.coursesapp.model.Course;
import com.watshoulditake.waltermao.coursesapp.ui.base.BaseCourseListenerFragment;
import com.watshoulditake.waltermao.coursesapp.utils.TextUtils;

public class CourseAboutFragment extends BaseCourseListenerFragment<Course> {

    private TextView mCourseTitle;
    private TextView mDescription;
    private TextView mPrereqs;
    private TextView mAntiReqs;
    private TextView mTermsOffered;
    private TextView mUnits;
    private TextView mIsOnline;
    private TextView mInstructions;
    private TextView mWebUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_about, container, false);
    }

    @Override
    public void updateUI() {
        mCourseTitle.setText(TextUtils.isNullorEmpty(getData().getTitle()) ? "–" : getData().getTitle());
        mDescription.setText(TextUtils.isNullorEmpty(getData().getDescription()) ? "–" : getData().getDescription());
        mPrereqs.setText(TextUtils.isNullorEmpty(getData().getPrereqsString()) ? "–" : getData().getPrereqsString());
        mAntiReqs.setText(TextUtils.isNullorEmpty(getData().getAntiRequisites()) ? "–" : getData().getAntiRequisites());
        mTermsOffered.setText(TextUtils.isNullorEmpty(getData().getTermsOfferedString()) ? "–" : getData().getTermsOfferedString());
        mUnits.setText(getData().getUnits() == null ? "–" : String.valueOf(getData().getUnits()));
        mIsOnline.setText(getData().isOnline() != null ? (getData().isOnline() ? "Yes" : "No") : "–");
        mInstructions.setText(TextUtils.isNullorEmpty(getData().getInstructionsString()) ? "–" : getData().getInstructionsString());
        mWebUrl.setText(TextUtils.isNullorEmpty(getData().getURL()) ? "–" : getData().getURL());
    }

    @Override
    public void initialiseViews(View view) {
        mCourseTitle = view.findViewById(R.id.course_title);
        mDescription = view.findViewById(R.id.description);
        mPrereqs = view.findViewById(R.id.prereqs);
        mTermsOffered = view.findViewById(R.id.terms_offered);
        mUnits = view.findViewById(R.id.units);
        mIsOnline = view.findViewById(R.id.is_online);
        mInstructions = view.findViewById(R.id.instructions);
        mAntiReqs = view.findViewById(R.id.antirequisites);
        mWebUrl = view.findViewById(R.id.web_url);
    }

    @Override
    public Loader<Course> getDataLoader() {
        return new CourseAboutLoader(getContext(), getCourseCode());
    }

}
