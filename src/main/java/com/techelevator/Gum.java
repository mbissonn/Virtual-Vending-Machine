package com.techelevator;

public class Gum extends Item{
    public Gum(String itemLocation, String itemName, int itemPrice) {
        super(itemLocation, itemName, itemPrice);
        super.setDispenseMessage("Chew Chew, Yum!");
    }
}
