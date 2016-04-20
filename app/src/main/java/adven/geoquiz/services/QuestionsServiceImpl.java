package adven.geoquiz.services;

import java.util.HashMap;
import java.util.Map;

import adven.geoquiz.services.model.Question;

public class QuestionsServiceImpl implements QuestionsService {
    private final Map<Integer, Question> questionsRepo = new HashMap<>();
    public QuestionsServiceImpl() {
        questionsRepo.put(0, new Question("true", true));
        questionsRepo.put(1, new Question("false", false));
    }

    @Override
    public Question getQuestion(int index) {
        return questionsRepo.get(index);
    }
}
