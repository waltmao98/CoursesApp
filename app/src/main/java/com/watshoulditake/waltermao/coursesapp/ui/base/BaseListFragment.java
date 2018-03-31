package com.watshoulditake.waltermao.coursesapp.ui.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.adapters.BaseListAdapter;
import com.watshoulditake.waltermao.coursesapp.listeners.RecyclerItemClickListener;
import com.watshoulditake.waltermao.coursesapp.ui.SearchResultsFragment;
import com.watshoulditake.waltermao.coursesapp.utils.ParcelableString;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for general list fragment
 *
 * @param <K> {@inheritDoc}
 * @param <D> List of this type
 */
public abstract class BaseListFragment
        <K extends Parcelable, D extends Parcelable>
        extends BaseDataFragment<K, List<D>> {

    private RecyclerView mRecyclerView;
    private BaseListAdapter mAdapter;
    private TextView mTopText;
    private SearchView mSearchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text_and_list,container,false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem menuItemSearchView = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) menuItemSearchView.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchResultsFragment fragment = BaseDataFragment.createFragment(new SearchResultsFragment(), new ParcelableString(query));
                startFragment(fragment,null);
                return false;
            }
        });
    }

    @Override
    public void updateUI() {
        setTitle(getTitle());
        boolean showViews = getData() != null && getData().size() != 0;
        if (showViews) {
            mTopText.setText(getListDescription());
            if (mAdapter == null || mRecyclerView.getAdapter() != mAdapter) {
                mAdapter = createAdapter(getData());
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
        mAdapter = createAdapter(new ArrayList<D>());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        onListItemClick(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );
        mTopText = view.findViewById(R.id.list_description);
    }

    private void setViewsVisibility(boolean showViews) {
        mRecyclerView.setVisibility(showViews ? View.VISIBLE : View.GONE);
        mTopText.setVisibility(showViews ? View.VISIBLE : View.GONE);
        getView().findViewById(R.id.empty_text).setVisibility(showViews ? View.GONE : View.VISIBLE);
    }

    public abstract BaseListAdapter createAdapter(List<D> data);

    public abstract void onListItemClick(int position);

    public abstract String getListDescription();

    public abstract String getTitle();
}


