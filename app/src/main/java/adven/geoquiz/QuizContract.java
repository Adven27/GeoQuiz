package adven.geoquiz;

import adven.geoquiz.services.model.Question;

public interface QuizContract {

    interface View {
        void showCheckResult(boolean isCorrect);
        void showQuestion(Question question);
        void showCheatScreen(boolean answer);
    }

    interface UserActionsListener {
        void getQuestion(int index);
        void checkAnswer(int index, boolean isYes);
        void showCheatScreen(int question);
    }
}
