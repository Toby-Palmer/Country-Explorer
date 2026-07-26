public class Country {

    // Unit 3: Encapsulation
    // These fields are private so the data is protected and accessed through methods
    private String name;
    private String capital;
    private String region;
    private String subregion;
    private double area;
    private long population;

    // Unit 1: Constructor
    // This is used to create a Country object with all of its key details
    public Country(String name, String capital, String region, String subregion, double area, long population) {
        this.name = name;
        this.capital = capital;
        this.region = region;
        this.subregion = subregion;
        this.area = area;
        this.population = population;
    }

    // Unit 1 and Unit 3:
    // Getter methods allow access to the private variables
    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public long getPopulation() {
        return population;
    }

    public double getArea() {
        return area;
    }

    // Unit 1: Method overriding
    // This will convert the Country object into a readable string for display in the GUI
    @Override
    public String toString() {
        return "Name: " + name +
                "\nCapital: " + capital +
                "\nRegion: " + region +
                "\nSubregion: " + subregion +
                "\nPopulation: " + population +
                "\nArea: " + area;
    }
}