package com.watshoulditake.waltermao.coursesapp.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
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
import com.watshoulditake.waltermao.coursesapp.listeners.RecyclerItemClickListener;
import com.watshoulditake.waltermao.coursesapp.loaders.SubjectsLoader;
import com.watshoulditake.waltermao.coursesapp.model.SubjectMapping;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment<Parcelable, List<SubjectMapping>> {

    private RecyclerView mRecyclerView;
    private SubjectsListAdapter mAdapter;
    private TextView mHomeText;
    private SearchView mSearchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text_and_list, container, false);
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
            mHomeText.setText(R.string.home_message);
            if (mAdapter == null || mRecyclerView.getAdapter() != mAdapter) {
                mAdapter = new SubjectsListAdapter(getData());
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
        mRecyclerView.setAdapter(new SubjectsListAdapter(new ArrayList<SubjectMapping>()));
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        BaseFragment fragment = BaseFragment.createFragment(
                                new SubjectListFragment(),
                                getData().get(position));
                        startFragment(fragment, null);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );
        mHomeText = view.findViewById(R.id.list_description);
    }

    @Override
    Loader<List<SubjectMapping>> getDataLoader() {
        return new SubjectsLoader(getContext());
    }

    private void setViewsVisibility(boolean showViews) {
        mRecyclerView.setVisibility(showViews ? View.VISIBLE : View.GONE);
        mHomeText.setVisibility(showViews ? View.VISIBLE : View.GONE);
        getView().findViewById(R.id.empty_text).setVisibility(showViews ? View.GONE : View.VISIBLE);
    }

    private class SubjectViewHolder extends RecyclerView.ViewHolder {

        private TextView mSubjectCodeText;
        private TextView mSubjectNameText;

        public SubjectViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_course_summary, parent, false));
            mSubjectCodeText = itemView.findViewById(R.id.course_code);
            mSubjectNameText = itemView.findViewById(R.id.course_title);
        }

        public void bind(SubjectMapping mapping) {
            mSubjectCodeText.setText(mapping.getSubjectCode());
            mSubjectNameText.setText(mapping.getSubjectName());
        }

    }

    private class SubjectsListAdapter extends RecyclerView.Adapter<SubjectViewHolder> {

        private List<SubjectMapping> mData;

        public SubjectsListAdapter(List<SubjectMapping> data) {
            mData = data;
        }

        public void setData(List<SubjectMapping> data) {
            mData = data;
        }

        @NonNull
        @Override
        public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new SubjectViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
            holder.bind(getData().get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}
