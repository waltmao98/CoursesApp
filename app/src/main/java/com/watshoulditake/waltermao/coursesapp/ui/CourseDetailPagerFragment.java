package com.watshoulditake.waltermao.coursesapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.watshoulditake.waltermao.coursesapp.R;
import com.watshoulditake.waltermao.coursesapp.interfaces.ChangeTabEventListener;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;
import com.watshoulditake.waltermao.coursesapp.ui.base.BaseDataFragment;
import com.watshoulditake.waltermao.coursesapp.ui.base.BaseFragment;

import java.lang.ref.WeakReference;

public class CourseDetailPagerFragment extends BaseFragment implements ChangeTabEventListener {

    public static int ABOUT_PAGE_POSITION = 0;
    private static final String COURSE_SUMMARY_ARG = "course_summary_arg";

    private CourseSummary mCourseSummary;
    private ViewPager mViewPager;
    private CoursePagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;

    public static CourseDetailPagerFragment createFragment(CourseSummary courseSummary) {
        CourseDetailPagerFragment fragment = new CourseDetailPagerFragment();
        Bundle args = new Bundle();
        args.putParcelable(COURSE_SUMMARY_ARG,courseSummary);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCourseSummary = getArguments().getParcelable(COURSE_SUMMARY_ARG);

        mViewPager = view.findViewById(R.id.view_pager);
        mPagerAdapter = new CoursePagerAdapter(mCourseSummary, getChildFragmentManager(), getContext());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount() - 1);
        mTabLayout = view.findViewById(R.id.sliding_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        setTitle(mCourseSummary.getCourseCode());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_search).setVisible(false);
    }

    @Override
    public void changeToPage(int page) {
        if (page >= 0 && page < mPagerAdapter.getCount()) {
            mViewPager.setCurrentItem(page);
        }
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
                    return BaseDataFragment.createFragment(new CourseAboutFragment(), mCourseSummary);
                case 1:
                    PrereqsFragment prereqsFragment = new PrereqsFragment();
                    prereqsFragment.setChangeTabEventListener(CourseDetailPagerFragment.this);
                    return BaseDataFragment.createFragment(prereqsFragment, mCourseSummary);
                case 2:
                    FutureCoursesFragment futureCoursesFragment = new FutureCoursesFragment();
                    futureCoursesFragment.setChangeTabEventListener(CourseDetailPagerFragment.this);
                    return BaseDataFragment.createFragment(futureCoursesFragment, mCourseSummary);
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
