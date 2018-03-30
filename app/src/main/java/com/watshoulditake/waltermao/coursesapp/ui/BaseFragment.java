package com.watshoulditake.waltermao.coursesapp.ui;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.watshoulditake.waltermao.coursesapp.interfaces.FragmentInteractionListener;

public abstract class BaseFragment extends Fragment {
    private FragmentInteractionListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof FragmentInteractionListener) {
            mListener = (FragmentInteractionListener) getActivity();
        } else {
            throw new RuntimeException("Host activity must implement FragmentInteractionListener");
        }
    }

    void startFragment(Fragment fragment, String tag) {
        mListener.startFragment(fragment, tag);
    }

    void setTitle(String title) {
        mListener.setTitle(title);
    }
}
