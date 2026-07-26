import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GUI extends JFrame {

    // Unit 1: Object fields
    // the manager objects will handle the programs logic and file storage for favourites
    private CountryController manager;
    private FavouritesFile favouritesFile;

    // the search tab components
    private JTextField searchField;
    private JButton searchButton;
    private JTextArea searchResultArea;
    private JButton addFavouriteButton;
    private JTextArea favouritesArea;
    private JButton loadFavouritesButton;
    private JButton totalPopulationButton;
    private JLabel totalPopulationLabel;
    private Country lastSearchedCountry;

    // the Compare tab components
    private JTextField country1Field;
    private JTextField country2Field;
    private JButton compareButton;
    private JTextArea compareResultArea;
    private GraphPanel graphPanel;

    // Quiz tab components
    // Unit 4: Array
    // a button array is used to store the four multiple-choice option
    private JLabel quizQuestionLabel;
    private JButton[] optionButtons;
    private JButton nextQuestionButton;
    private JLabel scoreLabel;

    // Unit 7 and Unit 8:
    // The current question is stored as the abstract parent type
    // This allows different subclasses such as CapitalQuestion and RegionQuestion to be used polymorphically
    private QuizQuestions currentQuestion;
    private int score = 0;
    private int questionCount = 0;

    // Unit 1: Constructor
    // This creates the main GUI window and adds the three tabs
    public GUI() {
        manager = new CountryController();
        favouritesFile = new FavouritesFile();

        setTitle("World Explorer");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Search Country", createSearchPanel());
        tabbedPane.addTab("Compare Countries", createComparePanel());
        tabbedPane.addTab("Quiz", createQuizPanel());

        add(tabbedPane);
        setVisible(true);
    }

    // Unit 9: GUI construction
    // This method creates the search tab where users can search for countries,save favourites, load favourites, and calculate total favourite population

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Enter country name:");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addFavouriteButton = new JButton("Add to Favourites");
        loadFavouritesButton = new JButton("Load Favourites");
        totalPopulationButton = new JButton("Total Favourite Population");

        buttonPanel.add(addFavouriteButton);
        buttonPanel.add(loadFavouritesButton);
        buttonPanel.add(totalPopulationButton);

        topPanel.add(searchPanel);
        topPanel.add(buttonPanel);

        searchResultArea = new JTextArea();
        searchResultArea.setEditable(false);
        searchResultArea.setLineWrap(true);
        searchResultArea.setWrapStyleWord(true);
        searchResultArea.setBorder(new TitledBorder("Country Details"));

        favouritesArea = new JTextArea();
        favouritesArea.setEditable(false);
        favouritesArea.setLineWrap(true);
        favouritesArea.setWrapStyleWord(true);
        favouritesArea.setBorder(new TitledBorder("Favourite Countries"));

        JScrollPane resultScrollPane = new JScrollPane(searchResultArea);
        JScrollPane favouritesScrollPane = new JScrollPane(favouritesArea);

        totalPopulationLabel = new JLabel("Total population: ");

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(favouritesScrollPane, BorderLayout.CENTER);
        rightPanel.add(totalPopulationLabel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.add(resultScrollPane);
        centerPanel.add(rightPanel);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);

        // Unit 9: GUI events
        // These button listeners connect user actions to program methods
        searchButton.addActionListener(e -> searchCountry());
        addFavouriteButton.addActionListener(e -> addFavourite());
        loadFavouritesButton.addActionListener(e -> loadFavourites());
        totalPopulationButton.addActionListener(e -> showTotalFavouritePopulation());

        return panel;
    }

    // Unit 5 and Unit 9:
    // This method saves the most recently searched country to the favourites file
    private void addFavourite() {
        if (lastSearchedCountry == null) {
            searchResultArea.setText("Search for a valid country first.");
            return;
        }

        favouritesFile.saveFavourite(lastSearchedCountry.getName());
        searchResultArea.append("\n\nAdded to favourites: " + lastSearchedCountry.getName());
    }

    // Unit 5, Unit 7 and Unit 9:
    // This method loads favourite countries from the file and displays them in the GUI
    private void loadFavourites() {
        favouritesArea.setText("");

        for (String favourite : favouritesFile.loadFavourites()) {
            favouritesArea.append(favourite + "\n");
        }
    }

    // Unit 9: GUI construction
    // This method creates the compare tab where users can compare two countries and view a graphical population comparison
    private JPanel createComparePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel();
        JLabel country1Label = new JLabel("Country 1:");
        country1Field = new JTextField(12);
        JLabel country2Label = new JLabel("Country 2:");
        country2Field = new JTextField(12);
        compareButton = new JButton("Compare");

        topPanel.add(country1Label);
        topPanel.add(country1Field);
        topPanel.add(country2Label);
        topPanel.add(country2Field);
        topPanel.add(compareButton);

        compareResultArea = new JTextArea();
        compareResultArea.setEditable(false);
        compareResultArea.setLineWrap(true);
        compareResultArea.setWrapStyleWord(true);
        compareResultArea.setBorder(new TitledBorder("Comparison Result"));

        JScrollPane textScrollPane = new JScrollPane(compareResultArea);

        graphPanel = new GraphPanel();
        graphPanel.setBorder(new TitledBorder("Population Graph"));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(textScrollPane, BorderLayout.CENTER);
        centerPanel.add(graphPanel, BorderLayout.SOUTH);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);

        compareButton.addActionListener(e -> compareCountries());

        return panel;
    }

    // Unit 10: Recursion usage
    // this method loads favourites, converts them into an array and displays the recursively calculated total population
    private void showTotalFavouritePopulation() {
        java.util.ArrayList<String> favourites = favouritesFile.loadFavourites();

        if (favourites.isEmpty()) {
            totalPopulationLabel.setText("Total population: No favourites saved.");
            return;
        }

        String[] favouriteArray = favourites.toArray(new String[0]);
        long totalPopulation = manager.getTotalPopulationOfFavourites(favouriteArray);

        totalPopulationLabel.setText("Total population: " + String.format("%,d", totalPopulation));
    }

    // Unit 9:
    // this method handles the search button event and displays the selected country's details
    private void searchCountry() {
        String countryName = searchField.getText().trim();

        if (countryName.isEmpty()) {
            searchResultArea.setText("Please enter a country name.");
            lastSearchedCountry = null;
            return;
        }

        Country country = manager.getCountryByName(countryName);

        if (country == null) {
            searchResultArea.setText("Country not found.");
            lastSearchedCountry = null;
        } else {
            searchResultArea.setText(country.toString());
            lastSearchedCountry = country;
        }
    }

    // Unit 9:
    // This method handles comparison input and updates both the text result and the custom graphics panel

    private void compareCountries() {
        String country1Name = country1Field.getText().trim();
        String country2Name = country2Field.getText().trim();

        if (country1Name.isEmpty() || country2Name.isEmpty()) {
            compareResultArea.setText("Please enter both country names.");
            return;
        }

        Country country1 = manager.getCountryByName(country1Name);
        Country country2 = manager.getCountryByName(country2Name);

        if (country1 == null || country2 == null) {
            compareResultArea.setText("One or both countries could not be found.");
            graphPanel.setCountries(null, null);
            return;
        }

        String result = manager.compareCountries(country1Name, country2Name);
        compareResultArea.setText(result);

        graphPanel.setCountries(country1, country2);
    }

    // Unit 9: GUI construction
    // This method creates the quiz tab with a question label, four option buttons and a score display
    private JPanel createQuizPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        quizQuestionLabel = new JLabel("Press Next Question to start the quiz.", SwingConstants.CENTER);
        quizQuestionLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        optionButtons = new JButton[4];

        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new JButton("Option " + (i + 1));
            int index = i;
            optionButtons[i].addActionListener(e -> checkQuizAnswer(optionButtons[index].getText()));
            optionsPanel.add(optionButtons[i]);
        }

        JPanel bottomPanel = new JPanel();
        nextQuestionButton = new JButton("Next Question");
        scoreLabel = new JLabel("Score: 0 / 0");

        bottomPanel.add(nextQuestionButton);
        bottomPanel.add(scoreLabel);

        panel.add(quizQuestionLabel, BorderLayout.NORTH);
        panel.add(optionsPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        nextQuestionButton.addActionListener(e -> loadNextQuestion());

        return panel;
    }

    // Unit 4, Unit 7, Unit 8 and Unit 9:
    // This method creates a new quiz question by choosing a random country,  generating four options, and then creating either a CapitalQuestion or RegionQuestion object
    private void loadNextQuestion() {
        String[] countryNames = manager.getSampleCountryNames();

        int correctIndex = (int) (Math.random() * countryNames.length);
        String correctCountryName = countryNames[correctIndex];

        Country correctCountry = manager.getCountryByName(correctCountryName);

        if (correctCountry == null) {
            quizQuestionLabel.setText("Could not load question. Try again.");
            return;
        }

        String[] options = new String[4];
        options[0] = correctCountryName;

        int filled = 1;
        while (filled < 4) {
            int randomIndex = (int) (Math.random() * countryNames.length);
            String randomCountry = countryNames[randomIndex];

            boolean alreadyUsed = false;
            for (int i = 0; i < filled; i++) {
                if (options[i].equalsIgnoreCase(randomCountry)) {
                    alreadyUsed = true;
                    break;
                }
            }

            if (!alreadyUsed) {
                options[filled] = randomCountry;
                filled++;
            }
        }

        shuffleArray(options);

        int questionType = (int) (Math.random() * 2);

        if (questionType == 0) {
            if (correctCountry.getCapital() == null || correctCountry.getCapital().isEmpty()) {
                quizQuestionLabel.setText("Could not load capital question. Try again.");
                return;
            }

            currentQuestion = new CapitalQuestions(
                    "Which country has the capital " + correctCountry.getCapital() + "?",
                    options,
                    correctCountryName
            );
        } else {
            if (correctCountry.getRegion() == null || correctCountry.getRegion().isEmpty()) {
                quizQuestionLabel.setText("Could not load region question. Try again.");
                return;
            }

            currentQuestion = new RegionQuestions(
                    "Which country is in the region " + correctCountry.getRegion() + "?",
                    options,
                    correctCountryName
            );
        }

        quizQuestionLabel.setText(currentQuestion.getQuestionText());

        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(currentQuestion.getOptions()[i]);
            optionButtons[i].setEnabled(true);
        }
    }

    // Unit 8 and Unit 9:
    // This method checks the user's chosen answer and updates the score
    private void checkQuizAnswer(String chosenAnswer) {
        if (currentQuestion == null) {
            return;
        }

        questionCount++;

        if (currentQuestion.isCorrect(chosenAnswer)) {
            score++;
            quizQuestionLabel.setText("Correct! " + currentQuestion.getQuestionType() +
                    " question: " + currentQuestion.getCorrectAnswer() + " was the right answer.");
        } else {
            quizQuestionLabel.setText("Wrong! " + currentQuestion.getQuestionType() +
                    " question. The correct answer was " + currentQuestion.getCorrectAnswer() + ".");
        }

        scoreLabel.setText("Score: " + score + " / " + questionCount);

        for (JButton button : optionButtons) {
            button.setEnabled(false);
        }
    }

    // Unit 4: Array handling
    // This method shuffles the answer options so the correct answer is not always in the same position
    private void shuffleArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            int randomIndex = (int) (Math.random() * array.length);

            String temp = array[i];
            array[i] = array[randomIndex];
            array[randomIndex] = temp;
        }
    }
}