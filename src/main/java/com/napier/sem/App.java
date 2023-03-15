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

        ///////////////////// NO LONGER USED, keep here for now //////////////////
        // Creates menu for user - parses choice(s) and creates relevant report(s)
        //a.createMenu();


        // Country reports

        // Extract country information for world report
        ArrayList<Country> worldCountries = a.getAllCountries("w", "");
        // Extract country information for continent report
        ArrayList<Country> continentCountries = a.getAllCountries("c", "Oceania");
        //Extract country information for region report
        ArrayList<Country> regionCountries = a.getAllCountries("r", "Western Europe");

        // Print report for all countries in the world
        System.out.println("Countries report (all, world):");
        a.printCountries(worldCountries, -1);
        // Print report for all countries in continent
        System.out.println("Countries report (all, continent = 'Oceania'):");
        a.printCountries(continentCountries, -1);
        // Print report for all countries in region
        System.out.println("Countries report (all, region = 'Western Europe'):");
        a.printCountries(regionCountries, -1);
        // Print report for top 5 most populated countries in world
        System.out.println("Countries report (n = 5, world):");
        a.printCountries(worldCountries, 5);
        // Print report for top 8 most populated countries in continent
        System.out.println("Countries report (n = 8, continent = 'Oceania'):");
        a.printCountries(continentCountries, 8);
        // Print report for top 3 most populated countries in region
        System.out.println("Countries report (n = 3, region = 'Western Europe'):");
        a.printCountries(regionCountries, 3);


        // Disconnect from database
        a.disconnect();
    }

    /**
     * Prints a list of countries
     * @param countries The list of countries to print
     * @param displayN number to display, -1 displays all
     */
    public void printCountries(ArrayList<Country> countries, int displayN)
    {
        // checks list exists
        if(countries != null) {

            // checks list is not empty
            if(countries.size()<1){
                System.out.println("No countries found\n");
                return;
            }

            // Print header
            System.out.println(String.format("%-4s %-53s %-15s %-27s %-12s %-36s", "Code", "Name", "Continent", "Region", "Population", "Capital"));

            // sets displayN to total number of countries in ArrayList if either disiplayN is set to -1 (display all)
            // or displayN is greater than the number of countries
            if(displayN>countries.size() || displayN==-1){
                displayN = countries.size();
            }

            // Loop over all countries in the list
            for (int i=0; i<displayN;i++) {
                Country country;
                country = countries.get(i);
                String country_string =
                        String.format("%-4s %-53s %-15s %-27s %-12s %-36s",
                                country.code, country.name, country.continent, country.region, country.population, country.capital);
                System.out.println(country_string);
            }
            System.out.println("");
        }
    }

    /**
     * Gets all countries (defaults to world, pass "c" (continent) or "r" (region) to get relevant report)
     * 'choice' is only used if continent or region is specified as reportType
     * @param reportType should be "w", "c" or "r", "" can also be used to get world
     * @param choice if selecting a continent or region, this should be specified here - ignored if report type is "w"
     * @return A list of all countries, or null if there is an error
     */
    public ArrayList<Country> getAllCountries(String reportType, String choice)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Checks report type valid and correctly sets formatting
            if(reportType.toUpperCase().equals("C")){
                reportType = "Continent";
            } else if (reportType.toUpperCase().equals("R")){
                reportType = "Region";
            } else if (reportType.toUpperCase().equals("W") || reportType.equals("")){
                reportType = "World";
            } else {
                System.out.println("Countries report type not valid\n");
                return null;
            }

            // Create string for SQL statement
            String strSelect =
                    "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name as Capital\n"
                           + "FROM country\n"
                           + "LEFT JOIN city on country.Capital = city.ID\n";
            // Sets where clause for continent or region
            if(!(reportType.equals("World"))){
                strSelect += " WHERE " + reportType + " = '" + choice + "'\n";
            }
            // Orders by largest population to smallest
            strSelect += " ORDER BY Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information from result set
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next())
            {
                Country country = new Country();
                country.code = rset.getString("Code");
                country.name = rset.getString("Name");
                country.continent = rset.getString("Continent");
                country.region = rset.getString("Region");
                country.population = rset.getInt("Population");
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