package com.watshoulditake.waltermao.coursesapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.database.CoursesDao;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

import java.util.List;

public class BaseActivity extends AppCompatActivity {

    private static final String LOG_TAG = BaseActivity.class.getSimpleName();

    private List<CourseSummary> summaries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        summaries = CoursesDao.getInstance(this).querySubject("CS");
        startFragment();

    }

    private void startFragment() {
        FragmentManager fm = getSupportFragmentManager();
        if(fm.findFragmentById(R.id.fragment_container) == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_container,CourseDetailFragment.createFragment(summaries.get(5)))
                    .commit();
        }
    }

}
