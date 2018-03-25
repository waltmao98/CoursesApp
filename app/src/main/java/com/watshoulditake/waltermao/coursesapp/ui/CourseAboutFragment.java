package com.watshoulditake.waltermao.coursesapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.loaders.GetCourseAboutLoader;
import com.watshoulditake.waltermao.coursesapp.model.Course;

public class CourseAboutFragment extends BaseCourseFragment {

    private static final int GET_COURSE_ABOUT_LOADER_ID = 532;
    private Course mCourse;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_about, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDescription = view.findViewById(R.id.description);
        mPrereqs = view.findViewById(R.id.prereqs);
        mTermsOffered = view.findViewById(R.id.terms_offered);
        mUnits = view.findViewById(R.id.units);
        mIsOnline = view.findViewById(R.id.is_online);
        mInstructions = view.findViewById(R.id.instructions);
        mAntiReqs = view.findViewById(R.id.antirequisites);
        mWebUrl = view.findViewById(R.id.web_url);

        getLoaderManager().initLoader(GET_COURSE_ABOUT_LOADER_ID, getArguments(), new LoaderManager.LoaderCallbacks<Course>() {
            @Override
            public Loader<Course> onCreateLoader(int id, Bundle args) {
                return new GetCourseAboutLoader(getContext(), getCourseCode());
            }

            @Override
            public void onLoadFinished(Loader<Course> loader, Course data) {
                mCourse = data;
                updateUI();
            }

            @Override
            public void onLoaderReset(Loader<Course> loader) {

            }
        });
    }

    @Override
    public void updateUI() {
        mDescription.setText(mCourse.getDescription());
        mPrereqs.setText(mCourse.getPrereqsString());
        mAntiReqs.setText(mCourse.getAntiRequisites());
        mTermsOffered.setText(mCourse.getTermsOfferedString());
        mUnits.setText(String.valueOf(mCourse.getUnits()));
        mIsOnline.setText(mCourse.isOnline() ? "Yes" : "No");
        mInstructions.setText(mCourse.getInstructionsString());
        mWebUrl.setText(mCourse.getURL());
    }

}
