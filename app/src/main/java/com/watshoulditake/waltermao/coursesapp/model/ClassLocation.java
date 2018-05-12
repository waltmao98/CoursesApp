package com.watshoulditake.waltermao.coursesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ClassLocation implements Parcelable {

    private String mBuilding;

    private String mRoom;

    public String getBuilding() {
        return mBuilding;
    }

    public void setBuilding(String building) {
        mBuilding = building;
    }

    public String getRoom() {
        return mRoom;
    }

    public void setRoom(String room) {
        mRoom = room;
    }

    public ClassLocation() {

    }

    protected ClassLocation(Parcel in) {
        mBuilding = in.readString();
        mRoom = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBuilding);
        dest.writeString(mRoom);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClassLocation> CREATOR = new Creator<ClassLocation>() {
        @Override
        public ClassLocation createFromParcel(Parcel in) {
            return new ClassLocation(in);
        }

        @Override
        public ClassLocation[] newArray(int size) {
            return new ClassLocation[size];
        }
    };

}
