package com.watshoulditake.waltermao.coursesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CourseSummary implements Parcelable {

    private String mCourseCode;
    private String mTitle;
    private String mSubject;
    private String mCatalogNumber;

    public CourseSummary() {
    }

    protected CourseSummary(Parcel in) {
        mCourseCode = in.readString();
        mTitle = in.readString();
        mSubject = in.readString();
        mCatalogNumber = in.readString();
    }

    public String getCourseCode() {
        return mCourseCode;
    }

    public void setCourseCode(String courseCode) {
        mCourseCode = courseCode;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public String getCatalogNumber() {
        return mCatalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        mCatalogNumber = catalogNumber;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CourseSummary && mCourseCode.equals(((CourseSummary) other).getCourseCode());
    }

    ///////////////////////// PARCELABLE //////////////////////////////
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mCourseCode);
        parcel.writeString(mTitle);
        parcel.writeString(mSubject);
        parcel.writeString(mCatalogNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CourseSummary> CREATOR = new Creator<CourseSummary>() {
        @Override
        public CourseSummary createFromParcel(Parcel in) {
            return new CourseSummary(in);
        }

        @Override
        public CourseSummary[] newArray(int size) {
            return new CourseSummary[size];
        }
    };
}

