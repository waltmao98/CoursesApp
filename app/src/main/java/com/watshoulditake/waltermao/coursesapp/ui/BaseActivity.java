package com.watshoulditake.waltermao.coursesapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.interfaces.FragmentInteractionListener;

public class BaseActivity extends AppCompatActivity implements FragmentInteractionListener {

    private static final String LOG_TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FragmentManager fm = getSupportFragmentManager();

        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                getSupportActionBar().setDisplayHomeAsUpEnabled(fm.getBackStackEntryCount() > 0);
            }
        });

        if (savedInstanceState == null && fm.findFragmentById(R.id.fragment_container) == null) {
            fm.beginTransaction().
                    add(R.id.fragment_container, new HomeFragment()).
                    commit();
        }
    }

    @Override
    public void startFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().
                setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).
                replace(R.id.fragment_container, fragment).
                addToBackStack(null).
                commit();
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }
}
