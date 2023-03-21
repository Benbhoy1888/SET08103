package com.napier.sem;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * A Class to run project world reports application
 * Reports generated are saved in reports directory
 *
 * To run locally, now just need to use green run arrow next to main method (no need to rebuild)
 */
public class App
{
    /**
     * Main Method, program starts here
     * @param args
     */
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        if(args.length < 1){
            a.connect("localhost:33060", 0);
        }else{
            a.connect(args[0], Integer.parseInt(args[1]));
        }

        System.out.println("Generating Reports...");

        // Country reports --- vvv ---------------------------------------------------------------------

        // Extract country information for:
        // world report
        ArrayList<Country> worldCountries = a.getAllCountries("w", "");
        // continent report
        ArrayList<Country> continentCountries = a.getAllCountries("c", "Oceania");
        //region report
        ArrayList<Country> regionCountries = a.getAllCountries("r", "Western Europe");
        //
        ArrayList<Population> populationRegion = a.getPopulationByRegion();

        // Generates country reports and outputs to markdown file for:
        // all countries in world
        a.outputCountryReport(worldCountries, -1, "allWorldCountries");
        // all countries in continent (in this case, continent = 'Oceania')
        a.outputCountryReport(continentCountries, -1, "allCountriesContinent");
        // all countries in region (in this case, region = 'Western Europe')
        a.outputCountryReport(regionCountries, -1, "allCountriesRegion");
        // top n populated countries in world (in this case, n = 5)
        a.outputCountryReport(worldCountries, 5, "top5_worldCountries");
        // top n populated countries in continent (in this case, n = 8, continent = 'Oceania')
        a.outputCountryReport(continentCountries, 8, "top8_continentCountries");
        // top n populated countries in region (in this case, n = 3, region = 'Western Europe')
        a.outputCountryReport(regionCountries, 3, "top3_regionCountries");

        // Cities reports --- vvv ----------------------------------------------------------------------

        // Capital City reports --- vvv ----------------------------------------------------------------

        // Urbanisation reports --- vvv ----------------------------------------------------------------

        // TotalPopulation reports --- vvv -------------------------------------------------------------

        // Language reports --- vvv --------------------------------------------------------------------

        // ---------------------------------------------------------------------------------------------

        System.out.println("Program finished\n");

        // Disconnect from database
        a.disconnect();
    }

    // Cities reports --- vvv ----------------------------------------------------------------------

    // Capital City reports --- vvv ----------------------------------------------------------------

    // Urbanisation reports --- vvv ----------------------------------------------------------------

    // Language reports --- vvv --------------------------------------------------------------------

    // Total Population --- vvv --------------------------------------------------------------------
    /**
     * Gets total population information from database
     * @param reportType
     * @param choice specifies which continent, region, country, distric or city as per reportType
     * @return A TotalPopulation object, or null if there is an error
     */
    public TotalPopulation getTotalPopulation(String reportType, String choice) {
        // use report types "con" - continent, "cou" - country, "ci" - city, rest use 1st letter
        // if reportType is empty, should generate world report
        // store reportType name in object
        // get population in thousands to 2 decimal places
        System.out.println("Report type = " + reportType);
        return null;
    }

    /**
     * Outputs to Markdown
     * Filename and extension is automatically generated based on reportType
     * @param population A TotalPopulation object
     * @param reportType Used to generate correct filename
     */
    public void outputTotalPopulationReport(TotalPopulation population) {
        // use report types "con" - continent, "cou" - country, "ci" - city, rest use 1st letter
        // population column in thousands, reflect in header
    }

    // Country reports --- vvv ---------------------------------------------------------------------
    /**
     * Outputs to Markdown
     * Extension is automatically added
     * @param countries The list of countries to print
     * @param displayN number to display, -1 displays all
     * @param filename markdown output filename
     */
    public void outputCountryReport(ArrayList<Country> countries, int displayN, String filename) {
        if(filename.equals("")){
            return;
        }

        // Check countries is not null
        if (countries == null || countries.size()<1) {
            System.out.println("No countries");
            return;
        }

        // sets displayN to total number of countries in ArrayList if either disiplayN is set to -1 (display all)
        // or displayN is greater than the number of countries
        if(displayN>countries.size() || displayN<0){
            displayN = countries.size();
        }

        StringBuilder sb = new StringBuilder();
        // Print header
        sb.append("|Code |Name |Continent | Region | Population | Capital |\r\n");
        sb.append("| :--- | :--- | :--- | :--- | ---: | :--- |\r\n");

        // Loop over all countries in the list
        for (int i=0; i<displayN;i++) {
            Country country;
            country = countries.get(i);
            if(country == null) continue;
            sb.append(("| " + country.code + " | " +
                        country.name + " | " +
                        country.continent + " | " +
                        country.region + " | " +
                        country.population + " | " +
                        country.capital + " |\r\n"));
        }

        try {
            File directory = new File("./reports");
            if(!directory.exists()){
                directory.mkdir();
            }
            new File("./reports/country_reports").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter("./reports/country_reports/" + filename + ".md"));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all countries information from database (defaults to world, pass "c" (continent)
     * or "r" (region) to get relevant report)
     * 'choice' is only used if continent or region is specified as reportType
     * @param reportType should be "w", "c" or "r", "" can also be used to get world
     * @param choice if selecting a continent or region, this should be specified here - ignored if report type is "w"
     * @return A list of all countries, or null if there is an error
     */
    public ArrayList<Country> getAllCountries(String reportType, String choice) {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Checks report type valid and correctly sets formatting
            if(reportType.toUpperCase().equals("W") || reportType.equals("")) {
                reportType = "World";
            } else if (choice.equals("")){
                System.out.println("No choice provided when report type is not W or ''");
                return null;
            } else if(reportType.toUpperCase().equals("C")){
                reportType = "Continent";
            } else if (reportType.toUpperCase().equals("R")){
                reportType = "Region";
            } else {
                System.out.println("Countries report type not valid");
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

    public ArrayList<Country> getPopulationByRegion() {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();


            // Create string for SQL statement
            String strSelect =
                    "SELECT world.country.Region AS region, SUM(world.country.Population) as total_population,\n" +
                            "       (SELECT SUM(world.city.Population)\n" +
                            "        FROM world.city\n" +
                            "        JOIN world.country c on c.Code = city.CountryCode\n" +
                            "        WHERE world.c.Region = world.country.Region) as urban_population,\n" +
                            "    SUM(world.country.Population) -   (SELECT SUM(world.city.Population)\n" +
                            "                                       FROM world.city\n" +
                            "                                       JOIN world.country c on c.Code = city.CountryCode\n" +
                            "                                       WHERE world.c.Region = world.country.Region) as rural_population,\n" +
                            "\n" +
                            "                                       (SELECT SUM(world.city.Population)\n" +
                            "                                            FROM world.city\n" +
                            "                                                     JOIN world.country c on c.world.Code = city.CountryCode\n" +
                            "                                            WHERE world.c.Region = world.country.Region) / (SUM(world.country.Population )\n" +
                            "                                                ) * 100 as urban_percentage\n" +
                            "FROM world.country\n" +
                            "GROUP BY world.country.Region";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information from result set
            ArrayList<Population> population = new ArrayList<Country>();
            while (rset.next())
            {
                Population pop= new Population();
                pop.name = rset.getString(1);
                pop.totalPopulation = rset.getLong(2);
                pop.urbanPopulation = rset.getLong(3);
                pop.ruralPopulation = rset.getLong(4);
                pop.percentageUrban = rset.getLong(5);
                population.add(pop);
            }
            return pop;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country population details\n");
            return null;
        }
    }

    /**
     * Prints a list of countries
     * ///////////////////// NO LONGER USED, keep here for now //////////////////
     *
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

    // Menu --- vvv --------------------------------------------------------------------------------
    /**
     * Creates a menu for user
     * Parses choice(s) and creates relevant report(s)
     */
    private void createMenu() {
        Menu m = new Menu();
    }

    // Database --- vvv ----------------------------------------------------------------------------
    /**
     * Connection to MySQL database
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database
     */
    public void connect(String location, int delay)
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
                // Wait a bit for db to start
                Thread.sleep(delay); // Should be 30000 for pushing to GitHub, 0 when db up and running and testing locally
                // Connect to database
                //docker use db:3306
                //local use localhost:30060
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?useSSL=false", "root", "example");
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