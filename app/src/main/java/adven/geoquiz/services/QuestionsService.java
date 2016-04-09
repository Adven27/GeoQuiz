package adven.geoquiz.services;

import java.util.HashMap;
import java.util.Map;

import adven.geoquiz.R;
import adven.geoquiz.services.model.Question;

public class QuestionsService {
    private final Map<Integer, Question> questionsRepo = new HashMap<>();
    public QuestionsService() {
        questionsRepo.put(1, new Question(R.string.q_true, true));
        questionsRepo.put(2, new Question(R.string.q_false, false));
    }

    public Question getQuestion(int index) {
        return questionsRepo.get(index);
    }
}
