package adven.geoquiz;

import android.support.annotation.NonNull;
import adven.geoquiz.services.model.Question;

public interface QuizContract {

    interface View {
        void showCheckResult(boolean isCorrect);
        void showQuestion(Question question);
    }

    interface UserActionsListener {
        void getQuestion(int index);
        void checkAnswer(@NonNull Question question, boolean isYes);
    }
}
