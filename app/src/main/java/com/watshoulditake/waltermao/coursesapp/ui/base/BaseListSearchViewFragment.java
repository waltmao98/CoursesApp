package com.watshoulditake.waltermao.coursesapp.ui.base;

import android.os.Parcelable;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.ui.SearchResultsFragment;
import com.watshoulditake.waltermao.coursesapp.utils.ParcelableString;
import com.watshoulditake.waltermao.coursesapp.utils.ViewUtils;

/**
 * Base class for general list fragments that have a searchview in app bar
 *
 * @param <K> {@inheritDoc}
 * @param <D> List of this type
 */
public abstract class BaseListSearchViewFragment<K extends Parcelable, D extends Parcelable> extends BaseListFragment<K, D> {

    private SearchView mSearchView;

    @Override
    public void onPause() {
        super.onPause();
        ViewUtils.hideSoftKeyboard(mSearchView, getContext());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem menuItemSearchView = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) menuItemSearchView.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchResultsFragment fragment = BaseDataFragment.createFragment(new SearchResultsFragment(), new ParcelableString(query));
                startFragment(fragment, null);
                return false;
            }
        });

        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if (!queryTextFocused) {
                    menuItemSearchView.collapseActionView();
                    mSearchView.setQuery("", false);
                }
            }
        });
    }

}
