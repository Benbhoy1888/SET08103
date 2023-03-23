package com.napier.sem;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;


/**
 * A Class to run project world reports application
 * Reports generated are saved in reports directory
 * To run locally, now just need to use green run arrow next to main method (no need to rebuild)
 */
public class App
{
    /**
     * Main Method, program starts here
     */
    public static void main(String[] args) {
        // Create new Application
        App a = new App();

        // Connect to database
        if (args.length < 1) {
            a.connect("localhost:33060", 0);
        } else {
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




        // Cities reports --- vvv ----------------------------------------------------------------------
        ArrayList<City> worldCities= a.getAllCities("w", "");

        ArrayList<City> continentCities = a.getAllCities("c", "Oceania");

        ArrayList<City> regionCities = a.getAllCities("r", "Western Europe");

        ArrayList<City> countryCities = a.getAllCities("co", "United Kingdom");

        ArrayList<City> districtCities = a.getAllCities("d", "Kabol");

        a.outputCityReport(worldCities, -1, "allWorldCities");
        a.outputCityReport(continentCities, -1, "allCitiesContinent");
        a.outputCityReport(regionCities, -1, "allCitiesRegion");
        a.outputCityReport(countryCities, -1, "allCitiesCountry");
        a.outputCityReport(districtCities, -1, "allCitiesDistrict");

        a.outputCityReport(worldCities, 5, "top5_worldCities");
        a.outputCityReport(continentCities, 8, "top8_continentCities");
        a.outputCityReport(regionCities, 3, "top3_regionCities");
        a.outputCityReport(countryCities, 5, "top5_countryCities");
        a.outputCityReport(regionCities, 1, "top1_districtCities");

        // Capital City reports --- vvv ----------------------------------------------------------------

        // Urbanisation reports --- vvv ----------------------------------------------------------------

        // urban report - Continent
        ArrayList<Population> urbanPopulationContinent = a.getTotalUrbanRuralPopulation("Continent");
        // urban report - Region
        ArrayList<Population> urbanPopulationRegion = a.getTotalUrbanRuralPopulation("Region");
        // urban report - Continent
        ArrayList<Population> urbanPopulationCountry = a.getTotalUrbanRuralPopulation("Country");
        // urban report - world
        ArrayList<Population> urbanPopulationWorld = a.getTotalUrbanRuralPopulationWorld();
        // produce urban population report by continent
        a.outputUrbanPopulationReport(urbanPopulationContinent, "Urban_Continent");
        // produce urban population report by region
        a.outputUrbanPopulationReport(urbanPopulationRegion, "Urban_Region");
        // produce urban population report by country
        a.outputUrbanPopulationReport(urbanPopulationCountry, "Urban_Country");
        // produce urban population report for world
        a.outputUrbanPopulationReport(urbanPopulationWorld,"Urban_World");



        // TotalPopulation reports --- vvv -------------------------------------------------------------

        // Language reports --- vvv --------------------------------------------------------------------

        // ---------------------------------------------------------------------------------------------

        System.out.println("Program finished\n");

        // Disconnect from database
        a.disconnect();
    }

    // Cities reports --- vvv ----------------------------------------------------------------------
    public void outputCityReport(ArrayList<City> cities, int displayN, String filename) {
        if(filename.equals("")){
            return;
        }

        // Check countries is not null
        if (cities == null || cities.size()<1) {
            System.out.println("No capitals");
            return;
        }

        // sets displayN to total number of countries in ArrayList if either disiplayN is set to -1 (display all)
        // or displayN is greater than the number of countries
        if(displayN>cities.size() || displayN<0){
            displayN = cities.size();
        }

        StringBuilder sb = new StringBuilder();
        // Print header
        sb.append("|Name | Country | District| Population| \r\n");
        sb.append("| :--- | :--- | ---: |\r\n");

        // Loop over all countries in the list
        for (int i=0; i<displayN;i++) {
            City city;
            city = cities.get(i);
            if(city == null) continue;
            sb.append(("| " + city.name + " | " +
                    city.country + " | " +
                    city.district + " | " +
                    city.population + " |\r\n"));
        }

        try {
            File directory = new File("./reports");
            if(!directory.exists()){
                directory.mkdir();
            }
            new File("./reports/city_reports").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter("./reports/city_reports/" + filename + ".md"));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<City> getAllCities(String reportType, String choice) {
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
            } else if (reportType.toUpperCase().equals("R")) {
                reportType = "Region";
            }    else if (reportType.toUpperCase().equals("CO")){
                    reportType = "Country";
            }    else if (reportType.toUpperCase().equals("D")){
                reportType = "District";
            } else {
                System.out.println("Cities report type not valid");
                return null;
            }


            // Create string for SQL statement
            String strSelect =
                    "SELECT world.city.Name, world.city.Country, world.city.District, world.city.Population\n"
                            + "FROM city\n";

            // Sets where clause for continent or region
            if(!(reportType.equals("World"))){
                strSelect += " WHERE " + reportType + " = '" + choice + "'\n";
            }
            // Orders by largest population to smallest
            strSelect += " ORDER BY Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information from result set
            ArrayList<City> cities = new ArrayList<City>();
            while (rset.next())
            {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
            return cities;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details\n");
            return null;
        }
    }
    // Capital City reports --- vvv ----------------------------------------------------------------

    // Urbanisation reports --- vvv ----------------------------------------------------------------

    /**
     * This method creates SQL query to return either urban continent, urban region or urban country data depending on report type
     * @param reportType "Continent" (urban continent report), "Region" (urban region report), "Country" (urban country report)
     * @return population array containing return of SQL statment
     */
    public ArrayList<Population> getTotalUrbanRuralPopulation(String reportType) {

        String strSelect="";

        try
        {
            // if country report, chose column "Name"
            if(reportType=="Country"){
                reportType="Name";
            }

            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            strSelect =
                    "SELECT world.country." + reportType + " AS " + reportType + ", SUM(world.country.Population) as total_population,\n" +
                            "       (SELECT SUM(world.city.Population)\n" +
                            "        FROM world.city\n" +
                            "        JOIN world.country c on world.c.Code = world.city.CountryCode\n" +
                            "        WHERE world.c." + reportType + " = world.country." + reportType + ") as urban_population,\n" +
                            "    SUM(world.country.Population) - (SELECT SUM(world.city.Population)\n" +
                            "                                       FROM world.city\n" +
                            "                                       JOIN world.country c on world.c.Code = world.city.CountryCode\n" +
                            "                                       WHERE world.c." + reportType + " = world.country. " + reportType + ") as rural_population,\n" +
                            "\n" +
                            "                                       (SELECT SUM(world.city.Population)\n" +
                            "                                            FROM world.city\n" +
                            "                                                     JOIN world.country c on world.c.Code = world.city.CountryCode\n" +
                            "                                            WHERE world.c. " + reportType + " = world.country. " + reportType + ") / (SUM(world.country.Population )\n" +
                            "                                                ) * 100 as urban_percentage\n" +
                            "FROM world.country\n" +
                            "GROUP BY world.country." + reportType;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract urban population information from result set
            ArrayList<Population> population = new ArrayList<Population>();
            // Loop though query return and store values in population array
            while (rset.next())
            {
                Population pop = new Population();
                pop.Name = rset.getString(1);
                pop.totalPopulation = rset.getLong(2);
                pop.cityPopulation = rset.getLong(3);
                pop.nonCityPopulation = rset.getLong(4);
                population.add(pop);
            }
            return population;
        }
        catch (Exception e)
        {
            // capture SQL query error(s)
            System.out.println(e.getMessage());
            System.out.println("Failed to get country population details\n");
            return null;
        }
    }

    /**
     * This method creates SQL query to return either global urban population
     * @return global urban population return from sql query
     */
    public ArrayList<Population> getTotalUrbanRuralPopulationWorld() {

        String strSelect="";

        try {

            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            strSelect ="SELECT SUM(world.country.Population) as total_population,\n" +
                    "       (SELECT(SUM(world.city.Population))\n" +
                    "        FROM world.city) as city_population,\n" +
                    "       (SELECT(SUM(world.country.Population)) -\n" +
                    "                (SELECT(SUM(world.city.Population))\n" +
                    "                 FROM world.city)\n" +
                    "                            FROM world.country) as non_city_population\n" +
                    " FROM world.country";



            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information from result set
            ArrayList<Population> population = new ArrayList<Population>();
            while (rset.next())
            {
                Population pop = new Population();
                pop.totalPopulation = rset.getLong(1);
                pop.cityPopulation = rset.getLong(2);
                pop.nonCityPopulation = rset.getLong(3);
                population.add(pop);
            }
            return population;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get world population details\n");
            return null;
        }
    }



    /**
     * This method creates reports for urban continent, urban regional and urban country
     * @param population array
     * @param filename name of markdown report file
     */
    public void outputUrbanPopulationReport(ArrayList<Population> population, String filename) {

        // return from method if no report filename provided
        if(filename.equals("")){
            return;
        }

        // Check urban populations is not null
        if (population == null || population.size()<1) {
            System.out.println("No urban population");
            return;
        }

        // build report header
        StringBuilder sb = new StringBuilder();
        // Print header
        sb.append("|Name |Total Population |Population living in cities |Population not living in cities | \r\n");
        sb.append("| :--- | ---: | ---: | ---: |\r\n");

        // Loop over all rows in the list
        int rowCount = population.size();
        for (int i= 0; i<rowCount; i++){
            Population pop;
            pop = population.get(i);
            if(pop == null) continue;
            sb.append("| " + pop.Name + " | " +
                    pop.totalPopulation + " | " +
                    pop.cityPopulation + " | " +
                    pop.nonCityPopulation + " |\r\n");

        }

        try {
            // create directory and file for the report
            File directory = new File("./reports");
            if(!directory.exists()){
                directory.mkdir();
            }
            new File("./reports/urban_reports").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter("./reports/urban_reports/" + filename + ".md"));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates reports for world urban
     *      * @param population array
     *      * @param filename name of markdown report file
     */
    public void getTotalUrbanRuralPopulationWorld(ArrayList<Population> population, String filename) {

        // return from method if no report filename provided
        if(filename.equals("")){
            return;
        }

        // Check urban populations is not null
        if (population == null || population.size()<1) {
            System.out.println("No urban world population");
            return;
        }

        // build report header
        StringBuilder sb = new StringBuilder();
        // Print header
        sb.append("|Total Population |Population living in cities |Population not living in cities | \r\n");
        sb.append("| ---: | ---: | ---: |\r\n");

        // Loop over all rows in the list
        int rowCount = population.size();
        for (int i= 0; i<rowCount; i++){
            Population pop;
            pop = population.get(i);
            if(pop == null) continue;
            sb.append("| " + pop.totalPopulation + " | " +
                    pop.cityPopulation + " | " +
                    pop.nonCityPopulation  + " |\r\n");


        }

        try {
            // create directory and file for the report
            File directory = new File("./reports");
            if(!directory.exists()){
                directory.mkdir();
            }
            new File("./reports/urban_reports").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter("./reports/urban_reports/" + filename + ".md"));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



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
        return null;
    }

    /**
     * Outputs to Markdown
     * Filename and extension is automatically generated based on reportType
     * @param population A TotalPopulation object
    // * @param reportType Used to generate correct filename
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
     * Gets connection
     * @return con
     */
    public Connection getConnection() {
        return con;
    }

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
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=true&useSSL=false", "root", "example");
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