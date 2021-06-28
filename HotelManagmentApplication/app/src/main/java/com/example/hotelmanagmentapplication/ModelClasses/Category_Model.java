package com.example.hotelmanagmentapplication.ModelClasses;

public class Category_Model
{
    String category_Name;
    String category_Pic;

    public Category_Model() {
    }

    public Category_Model(String category_Name) {
        this.category_Name = category_Name;
    }

    public Category_Model(String category_Name, String category_Pic) {
        this.category_Name = category_Name;
        this.category_Pic = category_Pic;
    }

    public String getCategory_Name() {
        return category_Name;
    }

    public void setCategory_Name(String category_Name) {
        this.category_Name = category_Name;
    }

    public String getCategory_Pic() {
        return category_Pic;
    }

    public void setCategory_Pic(String category_Pic) {
        this.category_Pic = category_Pic;
    }


}
