package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

public class VendingMachineTest {

    private VendingMachine vendingMachine;
    private String displayTest;
    private Scanner testScanner = new Scanner("[int]");

    @Before
    public void Init() {

        this.vendingMachine = new VendingMachine();
    }

    @Test
    public void VendingMachine_Happy_Path_Main_Menu_1_Returns_All_Items_Quantity_5() {
        String expected = "A1 Potato Crisps $3.05, 5 are in stock\n" +
                "A2 Stackers $1.45, 5 are in stock\n" +
                "A3 Grain Waves $2.75, 5 are in stock\n" +
                "A4 Cloud Popcorn $3.65, 5 are in stock\n" +
                "B1 Moonpie $1.8, 5 are in stock\n" +
                "B2 Cowtales $1.5, 5 are in stock\n" +
                "B3 Wonka Bar $1.5, 5 are in stock\n" +
                "B4 Crunchie $1.75, 5 are in stock\n" +
                "C1 Cola $1.25, 5 are in stock\n" +
                "C2 Dr. Salt $1.5, 5 are in stock\n" +
                "C3 Mountain Melter $1.5, 5 are in stock\n" +
                "C4 Heavy $1.5, 5 are in stock\n" +
                "D1 U-Chews $0.85, 5 are in stock\n" +
                "D2 Little League Chew $0.95, 5 are in stock\n" +
                "D3 Chiclets $0.75, 5 are in stock\n" +
                "D4 Triplemint $0.75, 5 are in stock";

        String actual = vendingMachine.displayVendingMachineItems();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void VendingMachine_Happy_Path_Main_Menu_1_Returns_All_Items_Quantity_4_After_Dispensing_One_Of_Each() {
        for(Item item : vendingMachine.currentInventory.keySet()) {
            vendingMachine.dispenseProduct(item);
        }
        String expected = "A1 Potato Crisps $3.05, 4 are in stock\n" +
                "A2 Stackers $1.45, 4 are in stock\n" +
                "A3 Grain Waves $2.75, 4 are in stock\n" +
                "A4 Cloud Popcorn $3.65, 4 are in stock\n" +
                "B1 Moonpie $1.8, 4 are in stock\n" +
                "B2 Cowtales $1.5, 4 are in stock\n" +
                "B3 Wonka Bar $1.5, 4 are in stock\n" +
                "B4 Crunchie $1.75, 4 are in stock\n" +
                "C1 Cola $1.25, 4 are in stock\n" +
                "C2 Dr. Salt $1.5, 4 are in stock\n" +
                "C3 Mountain Melter $1.5, 4 are in stock\n" +
                "C4 Heavy $1.5, 4 are in stock\n" +
                "D1 U-Chews $0.85, 4 are in stock\n" +
                "D2 Little League Chew $0.95, 4 are in stock\n" +
                "D3 Chiclets $0.75, 4 are in stock\n" +
                "D4 Triplemint $0.75, 4 are in stock";

        String actual = vendingMachine.displayVendingMachineItems();

    }

    @Test
    public void VendingMachine_Happy_Path_Main_Menu_2_Takes_You_To_Purchase_Menu() {

        String actual = vendingMachine.displayVendingMachineItems();

        Assert.assertEquals(expected, actual);
    }
}
