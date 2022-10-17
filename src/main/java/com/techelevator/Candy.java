package com.techelevator;

public class Candy extends Item {
    public Candy(String itemLocation, String itemName, int itemPrice) {
        super(itemLocation, itemName, itemPrice);
        super.setDispenseMessage("Munch Munch, Yum!");
    }
}
