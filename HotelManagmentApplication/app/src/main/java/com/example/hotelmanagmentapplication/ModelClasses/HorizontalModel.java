package com.example.hotelmanagmentapplication.ModelClasses;

public class HorizontalModel
{
    private  String mSelectedItem;
    private String mCategoryName;
    private String mImageUrl;
    private String mName;
   private String mText1;
   private String mText2;
   private String mPID;

    public String getmPID() {
        return mPID;
    }

    public HorizontalModel(String mSelectedItem, String mCategoryName, String mImageUrl, String mName, String mText1, String mText2, String mPID) {
        this.mSelectedItem = mSelectedItem;
        this.mCategoryName = mCategoryName;
        this.mImageUrl = mImageUrl;
        this.mName = mName;
        this.mText1 = mText1;
        this.mText2 = mText2;
        this.mPID = mPID;
    }

    public void setmPID(String mPID) {
        this.mPID = mPID;
    }

    public HorizontalModel() {
    }

    public HorizontalModel(String mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }

    public HorizontalModel(String mCategoryName, String mImageUrl, String mName, String mText1, String mText2) {
        this.mCategoryName = mCategoryName;
        this.mImageUrl = mImageUrl;
        if(mName.trim().equals(""))
        {
            mName = "No Name";
        }
        this.mName = mName;
        this.mText1 = mText1;
        this.mText2 = mText2;
    }


    public String getmSelectedItem() {
        return mSelectedItem;
    }

    public void setmSelectedItem(String mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }

    public HorizontalModel(String mImageUrl, String mName, String mText1, String mText2)
    {
        if(mName.trim().equals(""))
        {
            mName = "No Name";
        }
        this.mImageUrl = mImageUrl;
        this.mName = mName;
        this.mText1 = mText1;
        this.mText2 = mText2;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }

    public void setmCategoryName(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }


    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }


}
