package adven.geoquiz;

import adven.geoquiz.services.QuestionsService;
import adven.geoquiz.services.model.Question;

public class QuizPresenter implements QuizContract.UserActionsListener {
    private final QuestionsService questionsService;
    private final QuizContract.View view;

    public QuizPresenter(QuestionsService questionsService, QuizContract.View view) {
        this.questionsService = questionsService;
        this.view = view;
    }

    @Override
    public void getQuestion(int index) {
        Question q = questionsService.getQuestion(index);
        if (q != null) {
            view.showQuestion(q);
            return;
        }
        throw new QuestionNotFoundException("Question with index " + index + " not found.");
    }

    @Override
    public void checkAnswer(int index, boolean isYes) {
        Question q = questionsService.getQuestion(index);
        view.showCheckResult(q.isCorrect() == isYes);
    }

    @Override
    public void showCheatScreen(int index) {
        view.showCheatScreen(questionsService.getQuestion(index).isCorrect());
    }
}