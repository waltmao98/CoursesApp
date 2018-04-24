package com.watshoulditake.waltermao.coursesapp.ui;

import android.support.v4.content.Loader;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.adapters.BaseListAdapter;
import com.watshoulditake.waltermao.coursesapp.adapters.CourseSummariesAdapter;
import com.watshoulditake.waltermao.coursesapp.loaders.SearchTermLoader;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;
import com.watshoulditake.waltermao.coursesapp.ui.base.BaseListSearchViewFragment;
import com.watshoulditake.waltermao.coursesapp.utils.ParcelableString;

import java.util.List;

public class SearchResultsFragment extends BaseListSearchViewFragment<ParcelableString, CourseSummary> {

    @Override
    public Loader<List<CourseSummary>> getDataLoader() {
        return new SearchTermLoader(getContext(), getKey().getString());
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
        return getString(R.string.search_results_text, getKey().getString());
    }

    @Override
    public String getTitle() {
        return getString(R.string.search_results_title);
    }


}
