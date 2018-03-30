package com.watshoulditake.waltermao.coursesapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.interfaces.FragmentInteractionListener;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

import java.util.List;

public class BaseActivity extends AppCompatActivity implements FragmentInteractionListener {

    private static final String LOG_TAG = BaseActivity.class.getSimpleName();

    private List<CourseSummary> summaries; // testing

    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new HomeFragment())
                .commit();
    }

    void setSearchViewVisibility(boolean shown) {
        mSearchView.setVisibility(shown ? View.VISIBLE : View.GONE);
    }

    @Override
    public void startFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().
                setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit).
                replace(R.id.fragment_container, fragment).
                addToBackStack(null).
                commit();
    }

}
