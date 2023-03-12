package com.napier.sem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class to display menu and parse choices for user
 * Program flow is directed via parseChoice() method
 */
public class Menu {

    /**
     * Constructor for Menu
     */
    public Menu() {
        this.displayMenu();
        this.parseChoice();
    }

    /**
     * Prints menu to console for user to view
     */
    private void displayMenu() {
        System.out.println("\n\n------------------- MAIN MENU -------------------\n");
        System.out.println("1     ->    Countries Report");
        System.out.println("2     ->    All Cities Report");
        System.out.println("3     ->    Capital Cities Report");
        System.out.println("4     ->    Urbanisation Report");
        System.out.println("5     ->    Total Population");
        System.out.println("6     ->    Speakers of Common Languages Report");
        System.out.println("Q|E   ->    Quit / Exit\n");
    }

    /**
     * Method to parse user menu choice and direct program flow
     */
    private void parseChoice() {
        boolean validChoice = false;

        // Loops until user enters a valid input
        do {
            // Gets user input
            String choice = getInput().toUpperCase();

            switch (choice) {
                case "1":
                    System.out.println("1 selected");
                    validChoice = true;
                    break;
                case "2":
                    System.out.println("2 selected");
                    validChoice = true;
                    break;
                case "3":
                    System.out.println("3 selected");
                    validChoice = true;
                    break;
                case "4":
                    System.out.println("4 selected");
                    validChoice = true;
                    break;
                case "5":
                    System.out.println("5 selected");
                    validChoice = true;
                    break;
                case "6":
                    System.out.println("6 selected");
                    validChoice = true;
                    break;
                case "Q":
                    System.out.println("Quitting...");
                    validChoice = true;
                    break;
                case "E":
                    System.out.println("Exiting...");
                    validChoice = true;
                    break;
                default:
                    System.out.println("Input not valid, please try again");
                    break;
            }
        } while (!validChoice);
    }

    /**
     * Get input from user for menu choice
     */
    private String getInput() {

        String input = null;
        try {
            // Create BufferedReader object
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter selection: ");

            // Gets user input as String
            input = reader.readLine();

            System.out.println();
        } catch (Exception e) {
            System.out.println(e);
            input = "";
        }
        return input;
    }
}
