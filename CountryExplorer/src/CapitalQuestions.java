public class CapitalQuestions extends QuizQuestions {

    // Unit 2: Inheritance
    // This subclass passes its data to the parent QuizQuestion constructor
    public CapitalQuestions(String questionText, String[] options, String correctAnswer) {
        super(questionText, options, correctAnswer);
    }

    // Unit 7 and Unit 8:
    // This overridden method identifies the type of quiz question
    @Override
    public String getQuestionType() {
        return "Capital";
    }
}