package com.watshoulditake.waltermao.coursesapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.loaders.SubjectsLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private static final int SUBJECT_LOADER_ID = 93935;

    private List<SubjectMapping> mSubjectMappings;
    private RecyclerView mRecyclerView;
    private SubjectsListAdapter mAdapter;
    private TextView mHomeText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                ((LinearLayoutManager) mRecyclerView.getLayoutManager()).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(new SubjectsListAdapter(new ArrayList<SubjectMapping>()));

        mHomeText = view.findViewById(R.id.list_description);

        getLoaderManager().initLoader(SUBJECT_LOADER_ID, null, new SubjectsLoaderCallBacks());
    }


    private class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mSubjectCodeText;
        private TextView mSubjectNameText;

        public SubjectViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_course_summary, parent, false));
            mSubjectCodeText = itemView.findViewById(R.id.course_code);
            mSubjectNameText = itemView.findViewById(R.id.course_title);
            itemView.setOnClickListener(this);
        }

        public void bind(SubjectMapping mapping) {
            mSubjectCodeText.setText(mapping.getSubjectCode());
            mSubjectNameText.setText(mapping.getSubjectName());
        }

        @Override
        public void onClick(View view) {

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
            holder.bind(mSubjectMappings.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    private class SubjectsLoaderCallBacks implements LoaderManager.LoaderCallbacks<Map<String, String>> {

        @NonNull
        @Override
        public Loader<Map<String, String>> onCreateLoader(int id, @Nullable Bundle args) {
            return new SubjectsLoader(getContext());
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Map<String, String>> loader, Map<String, String> data) {
            initializeSubjectMapping(data);

            boolean showViews = data != null && data.size() != 0;
            if (showViews) {
                mHomeText.setText(R.string.home_message);
                if (mAdapter == null || mRecyclerView.getAdapter() == null) {
                    mAdapter = new SubjectsListAdapter(mSubjectMappings);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.setData(mSubjectMappings);
                    mAdapter.notifyDataSetChanged();
                }
            }
            setViewsVisibility(showViews);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Map<String, String>> loader) {

        }
    }

    private void setViewsVisibility(boolean showViews) {
        mRecyclerView.setVisibility(showViews ? View.VISIBLE : View.GONE);
        mHomeText.setVisibility(showViews ? View.VISIBLE : View.GONE);
        getView().findViewById(R.id.empty_text).setVisibility(showViews ? View.GONE : View.VISIBLE);
    }

    private void initializeSubjectMapping(Map<String, String> mappings) {
        mSubjectMappings = new ArrayList<>();
        for (Map.Entry<String, String> mapping : mappings.entrySet()) {
            mSubjectMappings.add(new SubjectMapping(mapping));
        }
    }

    public class SubjectMapping {
        private String mSubjectCode;
        private String mSubjectName;

        public SubjectMapping(Map.Entry<String, String> mapping) {
            mSubjectCode = mapping.getKey();
            mSubjectName = mapping.getValue();
        }

        public String getSubjectCode() {
            return mSubjectCode;
        }

        public String getSubjectName() {
            return mSubjectName;
        }
    }
}
