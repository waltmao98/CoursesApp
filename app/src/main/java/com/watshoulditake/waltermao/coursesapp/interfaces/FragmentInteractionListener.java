package com.watshoulditake.waltermao.coursesapp.interfaces;

import android.support.v4.app.Fragment;

public interface FragmentInteractionListener {

    /**
     * Replaces the current fragment with childFragment
     *
     * @param childFragment replaces current fragment with this
     * @param tag           tag for fragment transaction
     */
    void startFragment(Fragment childFragment, String tag);
}
