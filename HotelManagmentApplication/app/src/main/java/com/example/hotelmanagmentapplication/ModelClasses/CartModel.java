package com.example.hotelmanagmentapplication.ModelClasses;

public class CartModel
{
    private String CategoryName;
    private String ItemName;
    private int Quantity;
    private String SpecialInstruction;
    private int Price;
    private String date,time,mPRID,Address,PhoneNumber,City;

    public CartModel(String categoryName, String itemName, int quantity, String specialInstruction, int price, String date, String time, String mPRID, String address, String phoneNumber, String city) {
        CategoryName = categoryName;
        ItemName = itemName;
        Quantity = quantity;
        SpecialInstruction = specialInstruction;
        Price = price;
        this.date = date;
        this.time = time;
        this.mPRID = mPRID;
        Address = address;
        PhoneNumber = phoneNumber;
        City = city;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getmPRID() {
        return mPRID;
    }

    public void setmPRID(String mPRID) {
        this.mPRID = mPRID;
    }

    public CartModel(String categoryName, String itemName, int quantity, String specialInstruction, int price, String date, String time, String mPRID) {
        CategoryName = categoryName;
        ItemName = itemName;
        Quantity = quantity;
        SpecialInstruction = specialInstruction;
        Price = price;
        this.date = date;
        this.time = time;
        this.mPRID = mPRID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public CartModel(String categoryName, String itemName, int quantity, String specialInstruction, int price, String date, String time) {
        CategoryName = categoryName;
        ItemName = itemName;
        Quantity = quantity;
        SpecialInstruction = specialInstruction;
        Price = price;
        this.date = date;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CartModel() {
    }

    public CartModel(String categoryName, String itemName, int quantity, String specialInstruction, int price) {
        CategoryName = categoryName;
        ItemName = itemName;
        Quantity = quantity;
        SpecialInstruction = specialInstruction;
        Price = price;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getSpecialInstruction() {
        return SpecialInstruction;
    }

    public void setSpecialInstruction(String specialInstruction) {
        SpecialInstruction = specialInstruction;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
