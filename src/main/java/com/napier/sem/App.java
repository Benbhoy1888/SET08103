package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

/**
 * A Class to run project world reports application
 */
public class App
{
    /* Ensure set to false before pushing to GitHub or for running via docker-compose.
       If setting to true and testing locally, start db first before running app. Will ONLY be able to run via App main()
     */
    private Boolean test_on_localhost = false; // if changing, make sure to package and rebuild images

    /**
     * Main Method, program starts here
     * @param args
     */
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // Extract country information
        ArrayList<Country> countries = a.getAllCountries("", "");
        a.printCountries(countries);

        // Creates menu for user - parses choice(s) and creates relevant report(s)
        //a.createMenu();

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Prints a list of countries.
     * @param countries The list of countries to print.
     */
    public void printCountries(ArrayList<Country> countries)
    {
        // Print header
        System.out.println(String.format("%-3s %-52s %-10s %-15s %-26s %-1s", "Code", "Name", "Population", "Continent", "Region", "Capital"));
        // Loop over all employees in the list
        for (Country country : countries)
        {
            String country_string =
                    String.format("%-3s %-52s %-10s %-15s %-26s %-1s",
                            country.code, country.name, country.population, country.continent, country.region, country.capital);
            System.out.println(country_string);
        }
        System.out.println("");
    }

    /**
     * Gets all countries (default world, pass cont or region to get relevant report).
     * @return A list of all countries, or null if there is an error.
     */
    public ArrayList<Country> getAllCountries(String cont, String region)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Code, Name, Population, Continent, Region, Capital "
                            + "FROM country"
                           /* if (!cont.equals("")){
                                + "WHERE Continent = " + cont;
                            } else if (!region.equals("")){
                                + "WHERE Region = " + region;
                            }*/
                            + " ORDER BY Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next())
            {
                Country country = new Country();
                country.code = rset.getString("Code");
                country.name = rset.getString("Name");
                country.population = rset.getInt("Population");
                country.continent = rset.getString("Continent");
                country.region = rset.getString("Region");
                country.capital = rset.getString("Capital");
                countries.add(country);
            }
            return countries;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details\n");
            return null;
        }
    }

    /**
     * Creates a menu for user
     * Parses choice(s) and creates relevant report(s)
     */
    private void createMenu() {
        Menu m = new Menu();
    }

    /**
     * Connection to MySQL database
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                int delay = 30000;
                String port = "db:3306";
                if (test_on_localhost) {
                    delay = 0;
                    port = "localhost:33060";
                }
                // Wait a bit for db to start
                Thread.sleep(delay); // Change delay to 30000 before pushing to GitHub, set to 0 when db up and running and testing locally
                // Connect to database
                //docker use db:3306
                //local use localhost:30060
                con = DriverManager.getConnection("jdbc:mysql://" + port + "/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected\n");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database
     */
    public void disconnect()
    {
        // Lets user know program is in process of exiting
        System.out.println("Disconnecting from database...");

        if (con != null)
        {
            try
            {
                // Close connection
                con.close();

                System.out.println("Successfully disconnected");
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }
}