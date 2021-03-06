package com.watshoulditake.waltermao.coursesapp.loaders;

import android.content.Context;

import com.watshoulditake.waltermao.coursesapp.database.CoursesDao;
import com.watshoulditake.waltermao.coursesapp.model.Course;

/**
 * Loads a course detail given a course code
 */
public class CourseAboutLoader extends BaseLoader<Course> {

    private String mCourseCode;

    /**
     * @param courseCode course code of the course to load
     */
    public CourseAboutLoader(Context context, String courseCode) {
        super(context);
        mCourseCode = courseCode;
    }

    @Override
    public Course loadInBackground() {
        return CoursesDao.getInstance(getContext()).getCourseDetail(mCourseCode);
    }
}
