package com.example.hotelmanagmentapplication.ModelClasses;

public class UploadImage
{
    private String mImageUrl;
    private String mName;

    public UploadImage()
    {}

    public UploadImage(String mImageUrl, String mName)
    {
        if(mName.trim().equals(""))
        {
            mName = "No Name";
        }
        this.mImageUrl = mImageUrl;
        this.mName = mName;
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
}
