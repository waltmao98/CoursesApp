package com.watshoulditake.waltermao.coursesapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.interfaces.ChangeTabEventListener;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

import java.lang.ref.WeakReference;

public class CourseDetailPagerFragment extends BaseCourseListenerFragment implements ChangeTabEventListener {

    public static int ABOUT_PAGE_POSITION = 0;

    private ViewPager mViewPager;
    private CoursePagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;
    private TextView mCourseCodeText;
    private TextView mTitleText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    void updateData() {
        updateUI();
    }

    // not used since updateData() is overridden to directly call updateUI()
    // this fragment doesn't have any inherent data
    @Override
    Loader getDataLoader() {
        return null;
    }

    @Override
    void updateUI() {
        setTitle(getCourseCode());
        mCourseCodeText.setText(getCourseCode());
        mTitleText.setText(getCourseSummary().getTitle());
    }

    @Override
    void initialiseViews(View view) {
        mCourseCodeText = view.findViewById(R.id.course_code);
        mTitleText = view.findViewById(R.id.course_title);

        mViewPager = view.findViewById(R.id.view_pager);
        mPagerAdapter = new CoursePagerAdapter(getCourseSummary(), getChildFragmentManager(), getContext());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount() - 1);
        mTabLayout = view.findViewById(R.id.sliding_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void changeToPage(int page) {
        if (page >= 0 && page < mPagerAdapter.getCount()) {
            mViewPager.setCurrentItem(page);
        }
    }

    @Override
    void receiveCourseChangedBroadcast(CourseSummary summary) {
        super.receiveCourseChangedBroadcast(summary);
    }

    private class CoursePagerAdapter extends FragmentPagerAdapter {

        private static final int NUM_ITEMS = 4;

        private CourseSummary mCourseSummary;
        private WeakReference<Context> mContextReference;

        CoursePagerAdapter(CourseSummary courseSummary, FragmentManager fm, Context context) {
            super(fm);
            mCourseSummary = courseSummary;
            mContextReference = new WeakReference<>(context);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return BaseFragment.createFragment(new CourseAboutFragment(), mCourseSummary);
                case 1:
                    PrereqsFragment prereqsFragment = new PrereqsFragment();
                    prereqsFragment.setChangeTabEventListener(CourseDetailPagerFragment.this);
                    return BaseFragment.createFragment(prereqsFragment, mCourseSummary);
                case 2:
                    FutureCoursesFragment futureCoursesFragment = new FutureCoursesFragment();
                    futureCoursesFragment.setChangeTabEventListener(CourseDetailPagerFragment.this);
                    return BaseFragment.createFragment(futureCoursesFragment, mCourseSummary);
                case 3:
                    return new CourseScheduleFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return mContextReference.get().getString(R.string.about_tab_title);
                case 1:
                    return mContextReference.get().getString(R.string.prereqs);
                case 2:
                    return mContextReference.get().getString(R.string.future_courses);
                case 3:
                    return mContextReference.get().getString(R.string.schedule_tab_title);
                default:
                    return null;
            }
        }
    }
}
