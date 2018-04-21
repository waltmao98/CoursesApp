package com.watshoulditake.waltermao.coursesapp.model;

import java.util.List;

public class CourseSchedule {

    private String mCourseCode;

    private String mSection;

    private String mCampus;

    private int mCapacity;

    private int mOccupied;

    private String mStartTime;

    private String mEndTime;

    private String mWeekDays;

    private ClassLocation mClassLocation;

    private List<String> mInstructors;

    private int mTerm;

    public String getCourseCode() {
        return mCourseCode;
    }

    public void setCourseCode(String courseCode) {
        mCourseCode = courseCode;
    }

    public String getSection() {
        return mSection;
    }

    public void setSection(String section) {
        mSection = section;
    }

    public String getCampus() {
        return mCampus;
    }

    public void setCampus(String campus) {
        mCampus = campus;
    }

    public int getCapacity() {
        return mCapacity;
    }

    public void setCapacity(int capacity) {
        mCapacity = capacity;
    }

    public int getOccupied() {
        return mOccupied;
    }

    public void setOccupied(int occupied) {
        mOccupied = occupied;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public String getWeekDays() {
        return mWeekDays;
    }

    public void setWeekDays(String weekDays) {
        mWeekDays = weekDays;
    }

    public ClassLocation getClassLocation() {
        return mClassLocation;
    }

    public void setClassLocation(ClassLocation classLocation) {
        mClassLocation = classLocation;
    }

    public List<String> getInstructors() {
        return mInstructors;
    }

    public void setInstructors(List<String> instructors) {
        mInstructors = instructors;
    }

    public int getTerm() {
        return mTerm;
    }

    public void setTerm(int term) {
        mTerm = term;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CourseSchedule &&
                ((CourseSchedule) obj).getCourseCode().equals(this.getCourseCode()) &&
                ((CourseSchedule) obj).getSection().equals(this.getSection());
    }
}
