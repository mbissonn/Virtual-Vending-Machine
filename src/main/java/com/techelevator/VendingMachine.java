package com.techelevator;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;

public class VendingMachine {
    protected int currentBalance = 0;
    protected int totalSales = 0;
    protected Map<Item, Integer> currentInventory = new HashMap<>();
    protected Map<String, Item> purchaseMap = new HashMap<>();
    protected File inventoryFile = new File("vendingmachine.csv");
    protected File logFile = new File("Log.txt");
    protected File salesReport = new File("salesreport.txt");
    protected Scanner userInput = new Scanner(System.in);
    protected String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm:ss a"));

    public VendingMachine() {
        try {
            Scanner inventoryScanner = new Scanner(inventoryFile);
            while (inventoryScanner.hasNextLine()) {
                String currentLine = inventoryScanner.nextLine();
                String[] currentItem = currentLine.split("\\|");
                if (currentItem[3].equals("Candy")) {
                    Candy newCandy = new Candy(currentItem[0], currentItem[1], (int) (Double.parseDouble(currentItem[2]) * 100));
                    currentInventory.put(newCandy, 5);
                    purchaseMap.put(newCandy.itemLocation, newCandy);
                }
                if (currentItem[3].equals("Chip")) {
                    Chip newChip = new Chip(currentItem[0], currentItem[1], (int) (Double.parseDouble(currentItem[2]) * 100));
                    currentInventory.put(newChip, 5);
                    purchaseMap.put(newChip.itemLocation, newChip);
                }
                if (currentItem[3].equals("Drink")) {
                    Drink newDrink = new Drink(currentItem[0], currentItem[1], (int) (Double.parseDouble(currentItem[2]) * 100));
                    currentInventory.put(newDrink, 5);
                    purchaseMap.put(newDrink.itemLocation, newDrink);
                }
                if (currentItem[3].equals("Gum")) {
                    Gum newGum = new Gum(currentItem[0], currentItem[1], (int) (Double.parseDouble(currentItem[2]) * 100));
                    currentInventory.put(newGum, 5);
                    purchaseMap.put(newGum.itemLocation, newGum);
                }
            }
            if (!logFile.exists()) {
                logFile.createNewFile();
            } else {
                logFile.delete();
                logFile.createNewFile();
            }
            if (!salesReport.exists()) {
                salesReport.createNewFile();
            } else {
                salesReport.delete();
                salesReport.createNewFile();
            }

            Debug debug = new Debug("9Z", "Debug", 0);
            purchaseMap.put(debug.itemLocation, debug);

        } catch (IOException e) {
            System.out.println("Sorry, there are currently no items in stock.");
        }
    }

    public boolean mainMenu() {
        System.out.println("(1) Display Vending Machine Items \n(2) Purchase \n(3) Exit");
        String userInputString = userInput.nextLine();
        switch (userInputString) {
            case "1":
                System.out.println(displayVendingMachineItems());
                mainMenu();
            case "2":
                purchaseMenu();
            case "3":
                System.exit(1);
            case "4":
                salesReporter();
                mainMenu();
            default:
                System.out.println("Please choose option (1), option (2), or option (3).");
                mainMenu();
        }
        return true;
    }

    public boolean purchaseMenu() {

        System.out.println("Current Money Provided: $" + currentBalance / 100.0 + " \n\n(1) Feed Money \n(2) Select Product \n(3) Finish Transaction");
        String userInputString = userInput.nextLine();
        switch (userInputString) {
            case "1":
                feedMoney();
                purchaseMenu();
            case "2":
                selectProduct();
                purchaseMenu();
            case "3":
                System.out.println(finishTransaction());
                mainMenu();
            default:
                System.out.println("Please choose option (1), option (2), or option (3).");
                purchaseMenu();
        }
        return true;
    }

    public String displayVendingMachineItems() {
        String displayString = "";
        String tempLineString = "";
        List<String> tempDisplayList = new ArrayList<>();
        for (Item item : currentInventory.keySet()) {
            tempLineString = item.itemLocation + " " + item.itemName + " $" + item.itemPrice / 100.0 + ", " + currentInventory.get(item) + " are in stock" + "\n";
            tempDisplayList.add(tempLineString);
        }
        Collections.sort(tempDisplayList);
        for (String line : tempDisplayList) {
            displayString += line;
        }
        return displayString;
    }

    public int feedMoney() {
        int feedAmount = 0;
        boolean feedAmountIsNumber = false;
        do {
            System.out.println("How much money do you wish to feed? (please use whole dollar amounts)");
            try {
                feedAmount = (int) (Double.parseDouble(userInput.nextLine()) * 100);
                feedAmountIsNumber = true;
            } catch (NumberFormatException e) {
            }
            if (feedAmount < 0 || (feedAmount % 100) != 0 || !feedAmountIsNumber) {
                System.out.println("Please enter a valid whole dollar amount.");
            }
        } while (feedAmount < 0 || (feedAmount % 100) != 0 || !feedAmountIsNumber);
        currentBalance += feedAmount;

        try (PrintWriter logWriter = new PrintWriter(new FileWriter(logFile, true))) {
            logWriter.println(currentDate + " FEED MONEY: $" + feedAmount / 100.0 + " $" + currentBalance / 100.0);
        } catch (IOException e) {
            System.out.println("Log file not found.");
        }
        return currentBalance;
    }

    public boolean selectProduct() {
        String userInputString = "";
        do {
            System.out.println(displayVendingMachineItems() + "\nPlease select a product.");
            userInputString = userInput.nextLine();
            if (!purchaseMap.containsKey(userInputString)) {
                System.out.println("Please input a valid item location.");
            }
        } while (!purchaseMap.containsKey(userInputString));

        Item selectedItem = purchaseMap.get(userInputString);

        if (currentInventory.get(selectedItem) < 1) {
            System.out.println("Sorry, " + selectedItem.itemName + " is out of stock.");
            return false;
        }

        if (selectedItem.itemPrice > currentBalance) {
            System.out.println("Sorry, you do not have enough money to purchase that item.");
            return false;
        }

        dispenseProduct(selectedItem);
        return true;
    }

    public boolean dispenseProduct(Item selectedItem) {
        currentBalance -= selectedItem.itemPrice;
        currentInventory.put(selectedItem, currentInventory.get(selectedItem) - 1);
        try (PrintWriter logWriter = new PrintWriter(new FileWriter(logFile, true))) {
            logWriter.println(currentDate + " " + selectedItem.itemName + " " + selectedItem.itemLocation + " $" + selectedItem.itemPrice / 100.0 + " $" + currentBalance);
        } catch (IOException e) {
            System.out.println("Log file not found.");
        }
        System.out.println(selectedItem.itemName + " $" + selectedItem.itemPrice / 100.0 + " $" + currentBalance / 100.0);
        System.out.println(selectedItem.dispenseMessage);
        return true;
    }

    public String finishTransaction() {
        int quarters = 0;
        int dimes = 0;
        int nickels = 0;
        int pennies = 0;

        if (currentBalance == 0) {
            return "You have no change.";
        }

        try (PrintWriter logWriter = new PrintWriter(new FileWriter(logFile, true))) {
            logWriter.println(currentDate + " GIVE CHANGE: $" + currentBalance / 100.0 + " $0.00");
        } catch (IOException e) {
            System.out.println("Log file not found.");
        }

        String changeMessage = "Your change is:";
        quarters = currentBalance / 25;
        currentBalance %= 25;
        dimes = currentBalance / 10;
        currentBalance %= 10;
        nickels = currentBalance / 5;
        pennies = currentBalance % 5;
        currentBalance = 0;

        changeMessage = quarters > 0 ? changeMessage + " " + quarters + " quarters" : changeMessage;
        changeMessage = dimes > 0 ? changeMessage + " " + dimes + " dimes" : changeMessage;
        changeMessage = nickels > 0 ? changeMessage + " " + nickels + " nickels" : changeMessage;
        changeMessage = pennies > 0 ? changeMessage + " " + pennies + " pennies" : changeMessage;

        return changeMessage;
    }

    public boolean salesReporter() {
        try (PrintWriter salesReportWriter = new PrintWriter(new FileWriter(salesReport, true))) {
            for (Item item : currentInventory.keySet()) {
                salesReportWriter.println(item.itemName + "|" + (5 - currentInventory.get(item)));
                totalSales += (item.itemPrice) * (5 - currentInventory.get(item));
            }
            salesReportWriter.println("\n\n **TOTAL SALES ** $" + totalSales / 100.0);
        } catch (IOException e) {
            System.out.println("Sales report file not found.");
        }
        return true;
    }

}


