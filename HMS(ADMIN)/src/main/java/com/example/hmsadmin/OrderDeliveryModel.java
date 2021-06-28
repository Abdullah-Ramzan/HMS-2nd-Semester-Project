package com.example.hmsadmin;

public class OrderDeliveryModel
{
    private String Address;
    private String City;
    private String PhoneNumber;

    public OrderDeliveryModel() {
    }

    public OrderDeliveryModel(String address, String city, String phoneNumber) {
        Address = address;
        City = city;
        PhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
