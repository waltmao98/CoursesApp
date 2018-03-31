package com.watshoulditake.waltermao.coursesapp.ui;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.adapters.BaseListAdapter;
import com.watshoulditake.waltermao.coursesapp.loaders.SubjectsLoader;
import com.watshoulditake.waltermao.coursesapp.model.SubjectMapping;
import com.watshoulditake.waltermao.coursesapp.ui.base.BaseDataFragment;
import com.watshoulditake.waltermao.coursesapp.ui.base.BaseListFragment;

import java.util.List;

public class HomeFragment extends BaseListFragment<Parcelable, SubjectMapping> {

    @Override
    public SubjectsListAdapter createAdapter(List<SubjectMapping> data) {
        return new SubjectsListAdapter(data);
    }

    @Override
    public void onListItemClick(int position) {
        BaseDataFragment fragment = BaseDataFragment.createFragment(
                new SubjectListFragment(),
                getData().get(position));
        startFragment(fragment, null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_home).setVisible(false);
    }

    @Override
    public String getListDescription() {
        return getString(R.string.home_message);
    }

    @Override
    public String getTitle() {
        return getString(R.string.app_name);
    }

    @Override
    public Loader<List<SubjectMapping>> getDataLoader() {
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
