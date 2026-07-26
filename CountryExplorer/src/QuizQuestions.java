public abstract class QuizQuestions {

    // Unit 3: Encapsulation
    // These private fields store the shared data for every quiz question
    private String questionText;
    private String[] options;
    private String correctAnswer;

    // Unit 1: Constructor
    // This constructor is used by all quiz question subclasses
    public QuizQuestions(String questionText, String[] options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    // Unit 1 and Unit 3:
    // Getter methods are used to access to the question data
    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    // Unit 1:
    // This method checks whether the user's answer is the correct answer
    public boolean isCorrect(String chosenAnswer) {
        return correctAnswer.equalsIgnoreCase(chosenAnswer);
    }

    // Unit 7: Abstraction
    // Each subclass must provide its own question type
    public abstract String getQuestionType();
}