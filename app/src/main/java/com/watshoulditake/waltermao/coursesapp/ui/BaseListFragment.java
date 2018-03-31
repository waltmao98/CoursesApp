package com.watshoulditake.waltermao.coursesapp.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.adapters.BaseListAdapter;
import com.watshoulditake.waltermao.coursesapp.listeners.RecyclerItemClickListener;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.action_bar_list_menu, menu);

        MenuItem menuItemSearchView = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) menuItemSearchView.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });
    }

    @Override
    void updateUI() {
        setTitle(getString(R.string.app_name));
        boolean showViews = getData() != null && getData().size() != 0;
        if (showViews) {
            mTopText.setText(R.string.home_message);
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
    void initialiseViews(View view) {
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

    abstract BaseListAdapter createAdapter(List<D> data);

    abstract void onListItemClick(int position);

}


