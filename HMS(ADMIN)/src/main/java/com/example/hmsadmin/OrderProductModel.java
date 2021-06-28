package com.example.hmsadmin;

public class OrderProductModel
{
    private String SpecialInstruction;
    private String ItemName;
    private String Price;
    private String Quantity;
    private String OrderID;

    public OrderProductModel() {
    }

    public OrderProductModel(String specialInstruction, String itemName, String price, String quantity, String orderID) {
        SpecialInstruction = specialInstruction;
        ItemName = itemName;
        Price = price;
        Quantity = quantity;
        OrderID = orderID;
    }

    public String getSpecialInstruction() {
        return SpecialInstruction;
    }

    public void setSpecialInstruction(String specialInstruction) {
        SpecialInstruction = specialInstruction;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }
}
