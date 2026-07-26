public class RegionQuestions extends QuizQuestions {

    // Unit 2: Inheritance
    // This subclass uses the parent QuizQuestion constructor to store same question data
    public RegionQuestions(String questionText, String[] options, String correctAnswer) {
        super(questionText, options, correctAnswer);
    }

    // Unit 7 and Unit 8:
    // This overridden method returns the specific type of this quiz question
    @Override
    public String getQuestionType() {
        return "Region";
    }
}