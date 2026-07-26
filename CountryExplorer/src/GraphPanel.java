import javax.swing.*;
import java.awt.*;

public class GraphPanel extends JPanel {

    // Unit 1: Object fields
    // These fields store the two countries currently being compared
    private Country country1;
    private Country country2;

    // Unit 6: 2D Graphics
    // This constructor sets a preferred size for the custom drawing panel
    public GraphPanel() {
        setPreferredSize(new Dimension(600, 250));
    }

    // Unit 1 and Unit 9:
    // This method updates the two countries being displayed
    // and then repaints the panel so the graphics refresh
    public void setCountries(Country country1, Country country2) {
        this.country1 = country1;
        this.country2 = country2;
        repaint();
    }

    // Unit 6: 2D Graphics
    // This method draws a simple population bar chart for the two selected countries
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Unit 3: Condition
        // If no countries have been selected yet, display an instruction message
        if (country1 == null || country2 == null) {
            g.drawString("Compare two countries to see the population graph.", 20, 35);
            return;
        }

        long pop1 = country1.getPopulation();
        long pop2 = country2.getPopulation();

        long maxPopulation = Math.max(pop1, pop2);

        // Unit 3: Condition
        // Avoid division by zero if population data is missing
        if (maxPopulation == 0) {
            return;
        }

        int panelWidth = getWidth();
        int barMaxWidth = panelWidth - 200;

        // Scale each bar relative to the larger population
        int bar1Width = (int) ((double) pop1 / maxPopulation * barMaxWidth);
        int bar2Width = (int) ((double) pop2 / maxPopulation * barMaxWidth);

        int y1 = 80;
        int y2 = 170;
        int barHeight = 30;

        // Draw labels and bars for both countries
        g.drawString(country1.getName() + " (" + pop1 + ")", 20, y1 - 10);
        g.fillRect(20, y1, bar1Width, barHeight);

        g.drawString(country2.getName() + " (" + pop2 + ")", 20, y2 - 10);
        g.fillRect(20, y2, bar2Width, barHeight);

        g.drawString("Population Comparison", 20, 35);
    }
}