package com.watshoulditake.waltermao.coursesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SubjectMapping implements Parcelable {
    private String mSubjectCode;
    private String mSubjectName;

    public SubjectMapping(String subjectCode, String subjectName) {
        mSubjectCode = subjectCode;
        mSubjectName = subjectName;
    }

    public String getSubjectCode() {
        return mSubjectCode;
    }

    public String getSubjectName() {
        return mSubjectName;
    }


    //////////////////////////// PARCELABLE ////////////////////////////////

    private SubjectMapping(Parcel in) {
        mSubjectCode = in.readString();
        mSubjectName = in.readString();
    }

    public static final Creator<SubjectMapping> CREATOR = new Creator<SubjectMapping>() {
        @Override
        public SubjectMapping createFromParcel(Parcel in) {
            return new SubjectMapping(in);
        }

        @Override
        public SubjectMapping[] newArray(int size) {
            return new SubjectMapping[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mSubjectCode);
        parcel.writeString(mSubjectName);
    }
}
