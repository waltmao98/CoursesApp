package com.watshoulditake.waltermao.coursesapp.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.adapters.BaseListAdapter;
import com.watshoulditake.waltermao.coursesapp.loaders.SubjectsLoader;
import com.watshoulditake.waltermao.coursesapp.model.SubjectMapping;

import java.util.List;

public class HomeFragment extends BaseListFragment<Parcelable, SubjectMapping> {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text_and_list, container, false);
    }

    @Override
    SubjectsListAdapter createAdapter(List<SubjectMapping> data) {
        return new SubjectsListAdapter(data);
    }

    @Override
    void onListItemClick(int position) {
        BaseFragment fragment = BaseFragment.createFragment(
                new SubjectListFragment(),
                getData().get(position));
        startFragment(fragment, null);
    }

    @Override
    Loader<List<SubjectMapping>> getDataLoader() {
        return new SubjectsLoader(getContext());
    }

    class SubjectsListAdapter extends BaseListAdapter<SubjectMapping> {

        SubjectsListAdapter(List<SubjectMapping> data) {
            super(data);
        }

        @NonNull
        @Override
        public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new SubjectViewHolder(inflater, parent);
        }

        class SubjectViewHolder extends BaseListAdapter<SubjectMapping>.BaseViewHolder {

            private TextView mSubjectCodeText;
            private TextView mSubjectNameText;

            SubjectViewHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.list_item_course_summary, parent, false));
                mSubjectCodeText = itemView.findViewById(R.id.course_code);
                mSubjectNameText = itemView.findViewById(R.id.course_title);
            }

            @Override
            public void bind(SubjectMapping mapping) {
                mSubjectCodeText.setText(mapping.getSubjectCode());
                mSubjectNameText.setText(mapping.getSubjectName());
            }
        }

    }

}
