package com.watshoulditake.waltermao.coursesapp.loaders;

import android.content.Context;

import com.watshoulditake.waltermao.coursesapp.database.CoursesDao;
import com.watshoulditake.waltermao.coursesapp.model.CourseSummary;

import org.jetbrains.annotations.Nullable;

import java.util.List;


/**
 * Loads a list of courses
 */
public class GetCoursesLoader extends BaseCourseLoader<List<CourseSummary>> {

    @Nullable
    private String mSubject;

    /**
     * @param subject if non-null, loads a list of this subject. Else loads all courses
     */
    public GetCoursesLoader(Context context, @Nullable String subject) {
        super(context);
        mSubject = subject;
    }

    @Override
    public List<CourseSummary> loadInBackground() {
        if(mSubject == null) {
            return CoursesDao.getInstance(getContext()).getAllCourses();
        } else {
            return CoursesDao.getInstance(getContext()).querySubject(mSubject);
        }
    }

}
