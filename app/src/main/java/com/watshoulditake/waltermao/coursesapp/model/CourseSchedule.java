package com.watshoulditake.waltermao.coursesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CourseSchedule implements Parcelable {

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

    public CourseSchedule() {

    }

    /////////////////////////// PARCELABLE /////////////////////////

    protected CourseSchedule(Parcel in) {
        mCourseCode = in.readString();
        mSection = in.readString();
        mCampus = in.readString();
        mCapacity = in.readInt();
        mOccupied = in.readInt();
        mStartTime = in.readString();
        mEndTime = in.readString();
        mWeekDays = in.readString();
        mInstructors = in.createStringArrayList();
        mTerm = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCourseCode);
        dest.writeString(mSection);
        dest.writeString(mCampus);
        dest.writeInt(mCapacity);
        dest.writeInt(mOccupied);
        dest.writeString(mStartTime);
        dest.writeString(mEndTime);
        dest.writeString(mWeekDays);
        dest.writeStringList(mInstructors);
        dest.writeInt(mTerm);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CourseSchedule> CREATOR = new Creator<CourseSchedule>() {
        @Override
        public CourseSchedule createFromParcel(Parcel in) {
            return new CourseSchedule(in);
        }

        @Override
        public CourseSchedule[] newArray(int size) {
            return new CourseSchedule[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CourseSchedule &&
                ((CourseSchedule) obj).getCourseCode().equals(this.getCourseCode()) &&
                ((CourseSchedule) obj).getSection().equals(this.getSection());
    }
}
