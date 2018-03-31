package com.watshoulditake.waltermao.coursesapp.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.database.CoursesDao;
import com.watshoulditake.waltermao.coursesapp.loaders.CourseListLoader;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;
import com.watshoulditake.waltermao.coursesapp.ui.base.BaseCourseListenerListFragment;

import org.json.JSONException;

import java.util.List;

public class PrereqsFragment extends BaseCourseListenerListFragment {

    private static final String LOG_TAG = PrereqsFragment.class.getSimpleName();

    @Override
    public String getListDescription() {
        return getString(R.string.prereqs_list_description, getCourseCode());
    }

    @Override
    public Loader<List<CourseSummary>> getDataLoader() {
        return new PrereqListLoader(getContext(), getCourseCode());
    }

    private static class PrereqListLoader extends CourseListLoader {

        private String mCourseCode;

        public PrereqListLoader(Context context, String courseCode) {
            super(context);
            mCourseCode = courseCode;
        }

        @Nullable
        @Override
        public List<CourseSummary> loadInBackground() {
            try {
                return CoursesDao.getInstance(getContext()).getPrerequitesList(mCourseCode);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
