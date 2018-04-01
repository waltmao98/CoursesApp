package com.watshoulditake.waltermao.coursesapp.ui;

import android.support.v4.content.Loader;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.adapters.BaseListAdapter;
import com.watshoulditake.waltermao.coursesapp.adapters.CourseSummariesAdapter;
import com.watshoulditake.waltermao.coursesapp.loaders.CourseSubjectLoader;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;
import com.watshoulditake.waltermao.coursesapp.model.SubjectMapping;
import com.watshoulditake.waltermao.coursesapp.ui.base.BaseListFragment;

import java.util.List;

public class SubjectListFragment extends BaseListFragment<SubjectMapping, CourseSummary> {

    @Override
    public Loader<List<CourseSummary>> getDataLoader() {
        return new CourseSubjectLoader(getContext(), getKey().getSubjectCode());
    }

    @Override
    public BaseListAdapter createAdapter(List<CourseSummary> data) {
        return new CourseSummariesAdapter(data, getContext());
    }

    @Override
    public void onListItemClick(int position) {
        CourseDetailPagerFragment fragment = CourseDetailPagerFragment.createFragment(getData().get(position));
        startFragment(fragment, null);
    }

    @Override
    public String getListDescription() {
        return getString(R.string.subject_list_description, getKey().getSubjectCode());
    }

    @Override
    public String getTitle() {
        return getKey().getSubjectCode();
    }

}
