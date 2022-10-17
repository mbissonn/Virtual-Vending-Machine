package com.techelevator;

public class Drink extends Item{
    public Drink(String itemLocation, String itemName, int itemPrice) {
        super(itemLocation, itemName, itemPrice);
        super.setDispenseMessage("Glug Glug, Yum!");
    }
}
