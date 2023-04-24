package com.napier.sem;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

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
        a.outputCountryReport(regionCountries, 3, "top3_regionCountries");

        // Cities reports --- vvv ----------------------------------------------------------------------
        ArrayList<City> worldCities= a.getAllCities("w", "");

        ArrayList<City> continentCities = a.getAllCities("c", "Oceania");

        ArrayList<City> regionCities = a.getAllCities("r", "Western Europe");

        ArrayList<City> countryCities = a.getAllCities("co", "United Kingdom");

        ArrayList<City> districtCities = a.getAllCities("d", "Kabol");

        // Generates reports and outputs to markdown file for:
        // world
        a.outputCityReport(worldCities, -1, "allWorldCities");
        // continent
        a.outputCityReport(continentCities, -1, "allCitiesContinent");
        // region
        a.outputCityReport(regionCities, -1, "allCitiesRegion");
        // country
        a.outputCityReport(countryCities, -1, "allCitiesCountry");
        // district
        a.outputCityReport(districtCities, -1, "allCitiesDistrict");

        // top n in world
        a.outputCityReport(worldCities, 5, "top5_worldCities");
        // top n in continent
        a.outputCityReport(continentCities, 8, "top8_continentCities");
        // top n in region
        a.outputCityReport(regionCities, 3, "top3_regionCities");
        // top n in country
        a.outputCityReport(countryCities, 5, "top5_countryCities");
        // top n in district
        a.outputCityReport(regionCities, 1, "top1_districtCities");

        // Capital City reports --- vvv ----------------------------------------------------------------



      //  public void outputCapitalCityReport(ArrayList<Capital> capitalCities, int displayN, String filename) {
           // if(filename.equals("")){
            //    return;
           // }
      // Check countries is not null
      //  if (CapitalCities == null || CapitalCities.size()<1) {
        // System.out.println("No capitals");
         //return;
        //}



        // Urbanisation reports --- vvv ----------------------------------------------------------------

        ArrayList<Urbanisation> urbanPopulationContinent = a.getTotalUrbanRuralPopulation("Con");
        // urban report - Region
        ArrayList<Urbanisation> urbanPopulationRegion = a.getTotalUrbanRuralPopulation("Reg");
        // urban report - Continent
        ArrayList<Urbanisation> urbanPopulationCountry = a.getTotalUrbanRuralPopulation("Cou");

        // produce urban population report by continent
        a.outputUrbanPopulationReport(urbanPopulationContinent, "Urban_Continent");
        // produce urban population report by region
        a.outputUrbanPopulationReport(urbanPopulationRegion, "Urban_Region");
        // produce urban population report by country
        a.outputUrbanPopulationReport(urbanPopulationCountry, "Urban_Country");

        // TotalPopulation reports --- vvv -------------------------------------------------------------

        // Extract population information for:
        // use report types "con" - continent, "cou" - country, "ci" - city, rest use 1st letter
        // world report
        TotalPopulation worldPopulation = a.getTotalPopulation("w", "");
        // continent report
        TotalPopulation continentPopulation = a.getTotalPopulation("con", "Oceania");
        // region report
        TotalPopulation regionPopulation  = a.getTotalPopulation("r", "Western Europe");
        // country report
        TotalPopulation countryPopulation = a.getTotalPopulation("cou", "Argentina");
        // district report
        TotalPopulation districtPopulation = a.getTotalPopulation("d", "Port-Louis");
        // city report
        TotalPopulation cityPopulation = a.getTotalPopulation("ci", "Buenos Aires");

        // Generates reports and outputs to markdown file for:
        // world
        a.outputTotalPopulationReport(worldPopulation);
        // continent
        a.outputTotalPopulationReport(continentPopulation);
        // region
        a.outputTotalPopulationReport(regionPopulation);
        // country
        a.outputTotalPopulationReport(countryPopulation);
        // district
        a.outputTotalPopulationReport(districtPopulation);
        // city
        a.outputTotalPopulationReport(cityPopulation);



        // Language reports --- vvv --------------------------------------------------------------------
        ArrayList<Language> languageArrayList = a.getLanguageReport();
        a.outputLanguageReport(languageArrayList, "languages_report");

        // ---------------------------------------------------------------------------------------------

        System.out.println("Program finished\n");

        // Disconnect from database
        a.disconnect();
    }




    // Cities reports methods --- vvv ----------------------------------------------------------------------

    /**
     * Outputs to Markdown
     * Extension is automatically added
     * @param cities The list of cities to print
     * @param displayN number to display, -1 displays all
     * @param filename markdown output filename
     */
    public void outputCityReport(ArrayList<City> cities, int displayN, String filename) {
        if(filename.equals("")){
            return;
        }

        // Check cities is not null
        if (cities == null || cities.size()<1) {
            System.out.println("No cities");
            return;
        }

        // sets displayN to total number of cities in ArrayList if either disiplayN is set to -1 (display all)
        // or displayN is greater than the number of cities
        if(displayN>cities.size() || displayN<0){
            displayN = cities.size();
        }

        StringBuilder sb = new StringBuilder();
        // Print header
        sb.append("|Name | Country | District| Population| \r\n");
        sb.append("| :--- | :--- | ---: | ---: |\r\n");

        // Loop over all cities in the list
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

    /**
     * Gets all cities information from database (defaults to world, pass "c" (continent)
     * or "r" (region) or "co" (country) or "d" (district) to get relevant report)
     * 'choice' is only used if continent or region is specified as reportType
     * @param reportType should be "w", "c" or "r", "" can also be used to get world
     * @param choice if selecting a continent, region, country or district this should be specified here - ignored if report type is "w"
     * @return A list of all cities, or null if there is an error
     */
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
                reportType = "country.Continent";
            } else if (reportType.toUpperCase().equals("R")) {
                reportType = "country.Region";
            }    else if (reportType.toUpperCase().equals("CO")){
                    reportType = "country.Name";
            }    else if (reportType.toUpperCase().equals("D")){
                reportType = "city.District";
            } else {
                System.out.println("Cities report type not valid");
                return null;

            }


            // Create string for SQL statement
            String strSelect =
                    "SELECT world.city.Name, world.country.Name AS Country, world.city.District, world.city.Population\n"
                            + "FROM world.city\n"
                            + "LEFT JOIN world.country on world.city.CountryCode = country.Code\n";



            // Sets where clause for continent or region
            if(!(reportType.equals("World"))){
                strSelect += " WHERE " + reportType + " = '" + choice + "'\n";
            }
            // Orders by largest population to smallest
            strSelect += " ORDER BY city.Population DESC";
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
    // Capital City reports methods --- vvv ----------------------------------------------------------------

    public void outputCapitalCitiesReport(
            ArrayList<Capital> capitalCities, int displayN, String filename) {

        StringBuilder sb = new StringBuilder();
        //Println header
        sb.append("|Name | Country | Population| \r\n");
        sb.append("| :--- | :--- | ---: |\r\n");

        /** Loop over all countries in the list*/
        for (int i=0; i<displayN;i++) {
         Capital capital;
            capital = capitalCities.get(i);
         if(capital== null) continue;
         sb.append(("| " + capital.name + " | " +
         capital.country + " | " +
         capital.population + " |\r\n"));
         }

        try {
         File directory = new File("./reports");
          if(!directory.exists()){
           directory.mkdir();
          }
         new File("./reports/capital_reports").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter("./reports/capitalCity_reports/" + filename + ".md"));
         writer.write(sb.toString());
         writer.close();
        } catch (IOException e) {
              e.printStackTrace();
        }
    }

        public ArrayList<Capital> getAllcapitalCities(String reportType, String choice) throws SQLException {
            Statement stmt;
            try {
                /** Creating an SQL statement*/
                stmt = con.createStatement();

                // Checks report type valid and correctly sets formatting
                if (reportType.equalsIgnoreCase("W") || reportType.equals("")){
                    reportType = "World";
                } else if (choice.equals("")) {
                    System.out.println("No choice provided when report type is not W or ''");
                    return null;
                } else if (reportType.equalsIgnoreCase("C")) {
                    reportType = "Continent";
                } else if (reportType.equalsIgnoreCase("R")) {
                    reportType = "Region";
                } else if (reportType.equalsIgnoreCase("CO")) {
                    reportType = "Country";
                } else {
                    System.out.println("Capital Cities report type not valid");
                    return null;
                }




            /** Create string for SQL statement*/
            String strSelect =
                    "SELECT world.country.Name AS country, world.city.name AS capital, world.city.Population as population\n"
                            + "FROM world.country\n" +
                            "JOIN world.country on world.city.ID  = world.city.ID;/n";
            /** Sets where clause for continent or region*/
            if (!(reportType.equals("World"))) {
                strSelect += " WHERE " + reportType + " = '" + choice + "'\n";
            }
            /** Orders by largest population to smallest*/
            strSelect += " ORDER BY Population DESC";
            /** Execute SQL statement*/
            ResultSet rset = stmt.executeQuery(strSelect);
            /** Extract country information from result set*/
            ArrayList<Capital> capitalCities = new ArrayList<Capital>();
            while (rset.next()) {
                Capital capital = new Capital();
                capital.name = rset.getString("Name");
                capital.country = rset.getString("Country");
                //  capital.region = rset.getString("Region");
                capital.population = rset.getInt("Population");
                capitalCities.add(capital);
            }
            return capitalCities;
        }
            catch(Exception e)
        {
            System.out.println(e.getMessage());
         System.out.println("Failed to get capital city details\n");
            return null;
        }
    }

    public ArrayList<Capital> getAllCapitalCites(String reportType, String choice) {
        return null;
    }






    // Urbanisation reports methods--- vvv ----------------------------------------------------------------

    // Language reports methods--- vvv --------------------------------------------------------------------

    // Urbanisation reports --- vvv ----------------------------------------------------------------

    /**
     * This method creates SQL query to return urban/rural population by either continent, region, or country
     * @param reportType "Con" (urban continent report), "Reg" (urban region report), "Cou" (urban country report)
     * @return population array containing return of SQL statement
     */
    public ArrayList<Urbanisation> getTotalUrbanRuralPopulation(String reportType) {

        try
        {
            // Checks report type valid and correctly sets formatting

            if(reportType.toUpperCase().equals("CON")) {
                reportType="Continent";
            }
            else if(reportType.toUpperCase().equals("REG")) {
                reportType="Region";
            }
            else if(reportType.toUpperCase().equals("COU")) {
                reportType="Name";
            }
            else{
                System.out.println("reportType not recognised");
                return null;
            }

            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT world.country." + reportType + " AS " + reportType + ", SUM(world.country.Population) as total_population,\n" +
                            "       (SELECT SUM(world.city.Population)\n" +
                            "        FROM world.city\n" +
                            "        JOIN world.country c on c.Code = world.city.CountryCode\n" +
                            "        WHERE c." + reportType + " = world.country." + reportType + ") as urban_population,\n" +
                            "    SUM(world.country.Population) - (SELECT SUM(world.city.Population)\n" +
                            "                                       FROM world.city\n" +
                            "                                       JOIN world.country c on c.Code = world.city.CountryCode\n" +
                            "                                       WHERE c." + reportType + " = world.country. " + reportType + ") as rural_population,\n" +
                            "\n" +
                            "                                       (SELECT SUM(world.city.Population)\n" +
                            "                                            FROM world.city\n" +
                            "                                                     JOIN world.country c on c.Code = world.city.CountryCode\n" +
                            "                                            WHERE c. " + reportType + " = world.country. " + reportType + ") / (SUM(world.country.Population )\n" +
                            "                                                ) * 100 as urban_percentage\n" +
                            "FROM world.country\n" +
                            "GROUP BY world.country." + reportType;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract urban population information from result set
            ArrayList<Urbanisation> urban = new ArrayList<Urbanisation>();
            // Loop though query return and store values in population array
            while (rset.next())
            {
                Urbanisation urb = new Urbanisation();
                urb.Name = rset.getString(1);
                urb.totalPopulation = rset.getLong(2);
                urb.cityPopulation = rset.getLong(3);

                // Only display city population by percentage if line does not return 0 for total population
                if(urb.totalPopulation>0) {
                    long totalPopulation  = rset.getLong(2);
                    long cityPopulation = rset.getLong(3);
                    double cityPercentage = (double)cityPopulation / (double)totalPopulation * 100;
                    urb.cityPopulationPercentage = (double)Math.round(cityPercentage);
                }
                else {
                    // display non-city population as 0.0 if total population = 0
                    urb.cityPopulationPercentage  = 0.0;
                }
                urb.nonCityPopulation = rset.getLong(4);
                // Only display non-city population by percentage if line does not return 0 for total population
                if(urb.totalPopulation>0) {
                    long totalPopulation  = rset.getLong(2);
                    long nonCityPopulation = rset.getLong(4);
                    double nonCityPercentage = (double)nonCityPopulation / (double)totalPopulation * 100;
                    urb.nonCityPopulationPercentage = (double)Math.round(nonCityPercentage);
                }
                else{
                    // display non-city population as 0.0 if total population = 0
                    urb.nonCityPopulationPercentage = 0.0;
                }
                urban.add(urb);
            }
            return urban;
        }
        catch (Exception e)
        {
            // capture SQL query error(s)
            System.out.println(e.getMessage());
            System.out.println("Failed to get urban population details\n");
            System.out.println(reportType);
            return null;
        }
    }

    /**
     * This method reads from array and stores data into markdown report file
     * @param urban array
     * @param filename name of markdown report file
     */
    public void outputUrbanPopulationReport(ArrayList<Urbanisation> urban, String filename) {

        // return from method if no report filename provided
        if(filename.equals("")){
            return;
        }

        // Check urban populations is not null
        if (urban == null || urban.size()<1) {
            System.out.println("No urban population");
            return;
        }

        // build report header
        StringBuilder sb = new StringBuilder();
        // Print header
        sb.append("|Name |Total Population |Population living in cities |Percent| Population not living in cities | Percentage | \r\n");
        sb.append("| :--- | ---: | ---: | ------------------------------: | ---: | -------------------------------: |\r\n");

        // Loop over all rows in the list
        int rowCount = urban.size();
        for (int i= 0; i<rowCount; i++){
            Urbanisation urb;
            urb = urban.get(i);
            if(urb == null) continue;
            sb.append(("| " + urb.Name + " | " +
                    urb.totalPopulation + " | " +
                    urb.cityPopulation + " | " +
                    urb.cityPopulationPercentage + " | " +
                    urb.nonCityPopulation + " | " +
                    urb.nonCityPopulationPercentage  + " |\r\n"));
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





    // Total Population methods--- vvv --------------------------------------------------------------------

    /**
     * Gets language information from database
     * @return A Language object, or null if there is an error
     */
    public Language getLanguage() {
        return null;
    }

    /**
     * Outputs to Markdown
     */
    public void outputLanguageReport(Language language) {
    }

    // Total Population methods --- vvv --------------------------------------------------------------------

    /**
     * Gets total population information from database
     * Use report types "con" - Continent, "cou" - Country, "ci" - City, the rest use 1st letter
     * If reportType is empty, generates world report
     * Population is given in thousands to 2 decimal places
     * @param reportType
     * @param choice specifies which continent, region, country, district or city as per reportType
     * @return A TotalPopulation object, or null if there is an error
     */
    public TotalPopulation getTotalPopulation(String reportType, String choice) {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Checks report type valid and correctly sets formatting
            // use report types "con" - continent, "cou" - country, "ci" - city, rest use 1st letter
            if(reportType.toUpperCase().equals("W") || reportType.equals("")) {
                reportType = "World";
            } else if (choice.equals("")){
                System.out.println("No choice provided when report type is not W or ''");
                return null;
            } else if(reportType.toUpperCase().equals("CON")){
                reportType = "Continent";
            } else if (reportType.toUpperCase().equals("R")){
                reportType = "Region";
            } else if (reportType.toUpperCase().equals("COU")){
                reportType = "Country";
            } else if (reportType.toUpperCase().equals("D")){
                reportType = "District";
            } else if (reportType.toUpperCase().equals("CI")){
                reportType = "City";
            } else {
                System.out.println("Total Population report type not valid");
                return null;
            }

            // Create string for SQL statement
            String strSelect = "SELECT ROUND(SUM(Population)/1000,2) AS 'Population'";

            if((reportType.equals("District")) || (reportType.equals("Region")) || (reportType.equals("Continent"))) {
                strSelect += ", " + reportType + " AS Name\n";
            } else if (!(reportType.equals("World"))) {
                strSelect += ", Name\n";
            } else {
                strSelect += "\n";
            }

            if((reportType.equals("District")) || (reportType.equals("City"))) {
                strSelect += "FROM city\n";
            } else {
                strSelect += "FROM country\n";
            }

            if((reportType.equals(("Country"))) || (reportType.equals("City"))) {
                strSelect += "WHERE Name = '" + choice + "'\n";
            } else if (!(reportType.equals("World"))) {
                strSelect += "WHERE " + reportType + " = '" + choice + "'\n";
            }

            strSelect += "GROUP BY 'Population'\n";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information from result set
            TotalPopulation population = new TotalPopulation();
            while (rset.next())
            {
                population.reportType = reportType;
                population.population = rset.getDouble("Population");

                if(reportType.equals("World")) {
                    population.name = "World";
                } else {
                    population.name = rset.getString("Name");
                }
            }
            return population;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population details\n");
            return null;
        }
    }

    /**
     * Outputs to Markdown
     * Filename and extension is automatically generated based on reportType
     * @param population A TotalPopulation object
     */
    public void outputTotalPopulationReport(TotalPopulation population) {
        // use report types "con" - continent, "cou" - country, "ci" - city, rest use 1st letter
        // population column in thousands, reflect in header

        // Check population is not null
        if (population == null) {
            System.out.println("No TotalPopulation object provided for outputting Total Population Report");
            return;
        }

        StringBuilder sb = new StringBuilder();
        // Print header
        sb.append("|Name | Population in Thousands|\r\n");
        sb.append("| :--- | ---: |\r\n");


        sb.append(("| " + population.name + " | " +
        population.population + " K |\r\n"));


        try {
            File directory = new File("./reports");
            if(!directory.exists()){
                directory.mkdir();
            }
            new File("./reports/total_population_reports").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter("./reports/total_population_reports/" + population.reportType + ".md"));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Country reports methods --- vvv ---------------------------------------------------------------------
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
                           + "JOIN city on country.Capital = city.ID\n";
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

    // Language

    // Language Reports --- vvv -------------------------------------------------------------------

    /**
     * This method creates SQL query to return the number of people and percentage of those globally who speak
     * Chinese, English, Hindi, Spanish and Arabic.
     * @return language array containing return of SQL statement
     */
    public ArrayList<Language> getLanguageReport() {

        try{
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect="SELECT Language, `Number of People`, ROUND((`Number of People`/`Total Population`)*100, 2) AS \"World Percentage\"\n" +
                    "FROM (SELECT world.countrylanguage.Language, ROUND(SUM(world.country.Population*(world.countrylanguage.Percentage/100)),0) AS 'Number of People',\n" +
                    "             (SELECT SUM(Population) FROM country) AS 'Total Population'\n" +
                    "      FROM world.country\n" +
                    "               JOIN world.countrylanguage ON countrylanguage.CountryCode = country.Code\n" +
                    "      WHERE Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic')\n" +
                    "      GROUP BY Language) AS lang";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract language information from result set
            ArrayList<Language> languages= new ArrayList<Language>();
            // Loop though query return and store values in language array
            while (rset.next())
            {
                Language lan = new Language();
                lan.languageName = rset.getString(1);
                lan.population = rset.getLong(2);
                lan.percentage = rset.getDouble(3);
                languages.add(lan);
            }
            return languages;
        }
        catch(Exception e) {
            // capture SQL query error(s)
            System.out.println(e.getMessage());
            System.out.println("Failed to get language details\n");
            return null;

        }
    }

    /**
     * This method reads from array and stores data into markdown report file
     * @param languages array
     * @param filename name of markdown report file
     */
    public void outputLanguageReport(ArrayList<Language> languages, String filename) {

        // return from method if no report filename provided
        if(filename.equals("")){
            return;
        }

        // Check language is not null
        if (languages == null || languages.size()<1) {
            System.out.println("No Languages");
            return;
        }

        // build report header
        StringBuilder sb = new StringBuilder();
        // Print header
        sb.append("|Language | Number of people | World Percentage| \r\n");
        sb.append("| :--- | ---: | ---: |\r\n");

        // Loop over all rows in the list
        int rowCount = languages.size();
        for (int i= 0; i<rowCount; i++){
            Language lan;
            lan = languages.get(i);
            if(lan == null) continue;
            sb.append(("| " + lan.languageName + " | " +
                    lan.population + " | " +
                    lan.percentage + " |\r\n"));
        }

        try {
            // create directory and file for the report
            File directory = new File("./reports");
            if(!directory.exists()){
                directory.mkdir();
            }
            new File("./reports/languages_report").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter("./reports/languages_report/" + filename + ".md"));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
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