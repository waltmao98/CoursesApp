package com.watshoulditake.waltermao.coursesapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.adapters.BaseListAdapter;
import com.watshoulditake.waltermao.coursesapp.adapters.CourseSummariesAdapter;
import com.watshoulditake.waltermao.coursesapp.loaders.CourseSubjectLoader;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;
import com.watshoulditake.waltermao.coursesapp.model.SubjectMapping;

import java.util.List;

public class SubjectListFragment extends BaseListFragment<SubjectMapping, CourseSummary> {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text_and_list, container, false);
    }

    @Override
    Loader<List<CourseSummary>> getDataLoader() {
        return new CourseSubjectLoader(getContext(), getKey().getSubjectCode());
    }

    @Override
    BaseListAdapter createAdapter(List<CourseSummary> data) {
        return new CourseSummariesAdapter(data, getContext());
    }

    @Override
    void onListItemClick(int position) {
        CourseDetailPagerFragment fragment = CourseDetailPagerFragment.createFragment(getData().get(position));
        startFragment(fragment, null);
    }

}
