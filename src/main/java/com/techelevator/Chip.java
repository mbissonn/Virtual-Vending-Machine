package com.techelevator;

public class Chip extends Item {
    public Chip(String itemLocation, String itemName, int itemPrice) {
        super(itemLocation, itemName, itemPrice);
        super.setDispenseMessage("Crunch Crunch, Yum!");
    }
}
