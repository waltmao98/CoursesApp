package com.watshoulditake.waltermao.coursesapp.model;

import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import com.watshoulditake.waltermao.coursesapp.utils.TextUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Course implements BaseColumns {

    @NotNull
    private String mCourseCode; // CS246

    @Nullable
    private String mTitle; // Object Oriented Software Development

    @NotNull
    private String mSubject; // CS

    @NotNull
    private String mCatalogNumber; // 246

    @Nullable
    private Double mUnits; // 0.5

    @Nullable
    private String mDescription;

    @Nullable
    private List<String> mInstructions;

    @Nullable
    private List<String> mPrereqsList;

    @Nullable
    private String mPrereqsString;

    @Nullable
    private String mAntiReqs;

    @Nullable
    private List<String> mFutureCourses;

    @Nullable
    private List<String> mTermsOffered;

    @Nullable
    private String mNotes;

    @Nullable
    private Boolean mIsOnline;

    @Nullable
    private String mURL;

    @Nullable
    private Boolean mIsFavourite;

    @Override
    public boolean equals(Object other) {
        return other instanceof Course && mCourseCode.equals(((Course) other).getCourseCode());
    }

    // basic getters and setters

    @NonNull
    public String getCourseCode() {
        return mCourseCode;
    }

    public void setCourseCode(@NonNull String courseCode) {
        mCourseCode = courseCode;
    }

    public String getTitle() {
        return mTitle != null ? mTitle : "";
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @NonNull
    public String getSubject() {
        return mSubject;
    }

    public void setSubject(@NonNull String subject) {
        mSubject = subject;
    }

    @NonNull
    public String getCatalogNumber() {
        return mCatalogNumber;
    }

    public void setCatalogNumber(@NonNull String catalogNumber) {
        mCatalogNumber = catalogNumber;
    }

    public Double getUnits() {
        return mUnits;
    }

    public void setUnits(double units) {
        mUnits = units;
    }

    public String getDescription() {
        return mDescription != null ? mDescription : "";
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public List<String> getInstructions() {
        return mInstructions != null ? mInstructions : new ArrayList<String>();
    }

    public void setInstructions(List<String> instructions) {
        mInstructions = instructions;
    }

    public String getInstructionsJSONString() {
        return new JSONArray(mInstructions).toString();
    }

    public String getInstructionsString() {
        return TextUtils.getCommaSeparatedList(mInstructions);
    }

    public List<String> getPrereqs() {
        return mPrereqsList != null ? mPrereqsList : new ArrayList<String>();
    }

    public String getPrereqsJSONString() {
        return new JSONArray(mPrereqsList).toString();
    }

    public void setPrereqsList(List<String> prerequisites) {
        mPrereqsList = prerequisites;
    }

    public String getPrereqsString() {
        return mPrereqsString != null ? mPrereqsString : "";
    }

    public void setPrereqsString(String prereqsString) {
        this.mPrereqsString = prereqsString;
    }

    public String getAntiRequisites() {
        return mAntiReqs != null ? mAntiReqs : "";
    }

    public void setAntiRequisites(String antiRequisites) {
        mAntiReqs = antiRequisites;
    }

    public List<String> getFutureCourses() {
        return mFutureCourses != null ? mFutureCourses : new ArrayList<String>();
    }

    public void setFutureCourses(List<String> futureCourses) {
        mFutureCourses = futureCourses;
    }

    public String getFutureCoursesJSONString() {
        return new JSONArray(mFutureCourses).toString();
    }

    public List<String> getTermsOffered() {
        return mTermsOffered != null ? mTermsOffered : new ArrayList<String>();
    }

    public void setTermsOffered(List<String> termsOffered) {
        mTermsOffered = termsOffered;
    }

    public String getTermsOfferedString() {
        return TextUtils.getCommaSeparatedList(mTermsOffered);
    }

    public String getTermsOfferedJSONString() {
        return new JSONArray(mTermsOffered).toString();
    }

    public String getNotes() {
        return mNotes != null ? mNotes : "";
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public Boolean isOnline() {
        return mIsOnline;
    }

    public void setIsOnline(boolean online) {
        mIsOnline = online;
    }

    public String getURL() {
        return mURL != null ? mURL : "";
    }

    public void setURL(String URL) {
        mURL = URL;
    }

    public void setIsFavourite(boolean isFavourite) {
        mIsFavourite = isFavourite;
    }

    public boolean isFavourite() {
        return mIsFavourite == null ? false : mIsFavourite;
    }
}
