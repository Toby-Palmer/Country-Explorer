import java.util.HashMap;
import java.util.Map;

public class CountryController {

    // Unit 1: Object field
    // This object is for communicating with the RestCountries API
    private RestCountriesWrapper api;

    // Unit 8: Map
    // A HashMap is used to store previously searched countries so they can be reused quickly
    private Map<String, Country> countryCache;

    // Unit 1: Constructor
    // This sets up the API wrapper and the countryCache when a CountryController object is created
    public CountryController() {
        api = new RestCountriesWrapper();
        countryCache = new HashMap<>();
    }

    // Unit 1 and Unit 8:
    // This method searches for a country by name
    // It first checks the HashMap before calling the API
    public Country getCountryByName(String countryName) {
        try {
            String key = countryName.toLowerCase();

            // Unit 3: Condition
            // If the country has already been searched for, return it from the cache
            if (countryCache.containsKey(key)) {
                return countryCache.get(key);
            }

            String json = api.getCountryByName(countryName, "name,capital,region,subregion,area,population");

            // Unit 3: Condition
            // If the API returns no data, return null
            if (json == null || json.isEmpty()) {
                return null;
            }

            // get the required values from the JSON response
            String name = extractValue(json, "\"common\":\"", "\"");
            String capital = extractValue(json, "\"capital\":[\"", "\"");
            String region = extractValue(json, "\"region\":\"", "\"");
            String subregion = extractValue(json, "\"subregion\":\"", "\"");
            String areaString = extractValue(json, "\"area\":", ",");
            String populationString = extractValue(json, "\"population\":", "}");

            double area = Double.parseDouble(areaString);
            long population = Long.parseLong(populationString);

            // Unit 1: Object creation
            // Create a new Country object using the extracted values
            Country country = new Country(name, capital, region, subregion, area, population);

            // Unit 8: Map
            // Store the country in the cache for future uses
            countryCache.put(key, country);

            return country;

        } catch (Exception e) {
            return null;
        }
    }

    // Unit 10: Recursion
    // This public method starts the recursive calculation of total population for all favourite countries
    public long getTotalPopulationOfFavourites(String[] favouriteNames) {
        return getTotalPopulationRecursive(favouriteNames, 0);
    }
    // Unit 10: Recursion
    // Base case: if the index reaches the end of the array, return 0
    // Recursive case: add the current country's population and call the method again for the next index.
    private long getTotalPopulationRecursive(String[] favouriteNames, int index) {
        if (index >= favouriteNames.length) {
            return 0;
        }

        Country country = getCountryByName(favouriteNames[index]);

        long currentPopulation = 0;
        if (country != null) {
            currentPopulation = country.getPopulation();
        }

        return currentPopulation + getTotalPopulationRecursive(favouriteNames, index + 1);
    }

    // Unit 1: Helper method
    // This method extracts a value from a string using a start marker and end marker
    // It's used to pull specific pieces of information out of the API JSON response
    private String extractValue(String text, String start, String end) {
        int startIndex = text.indexOf(start);

        if (startIndex == -1) {
            return "";
        }

        startIndex += start.length();
        int endIndex = text.indexOf(end, startIndex);

        if (endIndex == -1) {
            return "";
        }

        return text.substring(startIndex, endIndex);
    }

    // Unit 1 and Unit 3:
    // This method compares two countries and returns a readable summary
    // It uses If statements to decide which country is larger by population and area
    public String compareCountries(String countryName1, String countryName2) {
        Country country1 = getCountryByName(countryName1);
        Country country2 = getCountryByName(countryName2);

        if (country1 == null || country2 == null) {
            return "One or both countries could not be found.";
        }

        String result = "Comparing " + country1.getName() + " and " + country2.getName() + "\n\n";

        result += country1.getName() + " capital: " + country1.getCapital() + "\n";
        result += country2.getName() + " capital: " + country2.getCapital() + "\n\n";

        result += country1.getName() + " population: " + country1.getPopulation() + "\n";
        result += country2.getName() + " population: " + country2.getPopulation() + "\n";

        if (country1.getPopulation() > country2.getPopulation()) {
            result += country1.getName() + " has the larger population.\n\n";
        } else if (country2.getPopulation() > country1.getPopulation()) {
            result += country2.getName() + " has the larger population.\n\n";
        } else {
            result += "Both countries have the same population.\n\n";
        }

        result += country1.getName() + " area: " + country1.getArea() + "\n";
        result += country2.getName() + " area: " + country2.getArea() + "\n";

        if (country1.getArea() > country2.getArea()) {
            result += country1.getName() + " has the larger area.\n\n";
        } else if (country2.getArea() > country1.getArea()) {
            result += country2.getName() + " has the larger area.\n\n";
        } else {
            result += "Both countries have the same area.\n\n";
        }

        result += country1.getName() + " region: " + country1.getRegion() + "\n";
        result += country2.getName() + " region: " + country2.getRegion() + "\n";

        if (country1.getRegion().equalsIgnoreCase(country2.getRegion())) {
            result += "Both countries are in the same region.";
        } else {
            result += "The countries are in different regions.";
        }

        return result;
    }

    // Unit 4: Array
    // This returns a fixed array of country names used by the quiz system
    public String[] getSampleCountryNames() {
        return new String[] {
                "France", "Japan", "Brazil", "Germany",
                "Canada", "India", "Italy", "Spain",
                "Australia", "Mexico"
        };
    }
}