package com.watshoulditake.waltermao.coursesapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;

import com.watshoulditake.waltermao.coursesapp.interfaces.FragmentInteractionListener;

/**
 * Base fragment for all fragments that display data from the database
 *
 * @param <K> typeof key for identifying this and for retrieving data (eg. String for course code)
 * @param <D> typeof value this fragment will display (eg. List of CourseSummary)
 */
public abstract class BaseFragment<K extends Parcelable, D> extends Fragment {

    private static final int DATA_LOADER_ID = -9138;
    private static final String KEY_ARG = "key_arg";

    private FragmentInteractionListener mListener;
    private K mKey;
    private D mData;

    public static <K extends Parcelable> BaseFragment createFragment(BaseFragment fragment, K key) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_ARG, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof FragmentInteractionListener) {
            mListener = (FragmentInteractionListener) getActivity();
        } else {
            throw new RuntimeException("Host activity must implement FragmentInteractionListener");
        }
    }

    /**
     * 1. get key from arguments, if it exists
     * 2. initialise views, which gets references to each view
     * 3. updateData(), which will eventually call updateUI()
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mKey = getArguments().getParcelable(KEY_ARG);
        }
        initialiseViews(view);
        updateData();
    }

    void startFragment(Fragment fragment, String tag) {
        mListener.startFragment(fragment, tag);
    }

    void setTitle(String title) {
        mListener.setTitle(title);
    }

    void setKey(K key) {
        mKey = key;
    }

    K getKey() {
        return mKey;
    }

    void setData(D data) {
        mData = data;
    }

    D getData() {
        return mData;
    }

    /**
     * Re-fetches data associated with the current course summary
     */
    void updateData() {
        getLoaderManager().restartLoader(DATA_LOADER_ID, null, new DataLoaderCallbacks());
    }

    /**
     * Called once the data in updateData() has been fetched - somewhere in the method chain of
     * a call to updateData(). Default behaviour is to call in Loader.OnLoadFinished
     */
    abstract void updateUI();

    /**
     * Get access to all views via rootView.findViewById
     */
    abstract void initialiseViews(View view);

    /**
     * @return data loader that loads the fragment's data from database
     */
    abstract Loader<D> getDataLoader();

    /**
     * (Re)Loads data and then calls updateUI();
     */
    private class DataLoaderCallbacks implements LoaderManager.LoaderCallbacks<D> {
        @NonNull
        @Override
        public Loader<D> onCreateLoader(int id, Bundle args) {
            return getDataLoader();
        }

        @Override
        public void onLoadFinished(@NonNull Loader<D> loader, D data) {
            mData = data;
            updateUI();
        }

        @Override
        public void onLoaderReset(@NonNull Loader<D> loader) {

        }
    }
}
