package com.techelevator;

public abstract class Item {
    protected String itemLocation;
    protected String itemName;
    protected String dispenseMessage;
    protected int itemPrice;
    protected int stockValue;

    public Item(String itemLocation, String itemName, int itemPrice) {
        this.itemLocation = itemLocation;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDispenseMessage() {
        return dispenseMessage;
    }

    public void setDispenseMessage(String dispenseMessage) {
        this.dispenseMessage = dispenseMessage;
    }

    public int getItemPrice() {
        return itemPrice;
    }
}
