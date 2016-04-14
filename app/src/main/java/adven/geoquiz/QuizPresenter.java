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
        view.showQuestion(questionsService.getQuestion(index));
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