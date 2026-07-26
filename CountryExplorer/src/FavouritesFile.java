import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class FavouritesFile {

    // Unit 5: File I/O
    // This constant stores the name of the text file used to save favourite countries
    private static final String FILE = "favourites.txt";

    // Unit 5 and Unit 8:
    // This method saves a country to the file
    // A Set is used so duplicate country names are not stored
    public void saveFavourite(String countryName) {
        Set<String> favourites = loadFavouriteSet();
        favourites.add(countryName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE))) {
            for (String favourite : favourites) {
                writer.write(favourite);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving favourite.");
        }
    }

    // Unit 7: List
    // This method returns the favourites as an ArrayList so they can be displayed easily in the GUI
    public ArrayList<String> loadFavourites() {
        return new ArrayList<>(loadFavouriteSet());
    }

    // Unit 5 and Unit 8:
    // This method loads favourite countries from the text file into a LinkedHashSet
    // It's used so values stay unique and keep their insertion order
    public Set<String> loadFavouriteSet() {
        Set<String> favourites = new LinkedHashSet<>();

        File file = new File(FILE);

        // Unit 3: Condition
        // If the file does not exist yet, return an empty set
        if (!file.exists()) {
            return favourites;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;

            // Unit 3: Loop
            // Read the file line by line and add each country to the set
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    favourites.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading favourites.");
        }

        return favourites;
    }
}