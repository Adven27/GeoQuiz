package adven.geoquiz.services.model;

import java.util.Objects;

public class Question {
    private final int txt;
    private final boolean isCorrect;

    public Question(int txt, boolean isCorrect) {
        this.txt = txt;
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return "Question{txt=" + txt + ", isCorrect=" + isCorrect + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return txt == question.txt && isCorrect == question.isCorrect;
    }

    @Override
    public int hashCode() {
        return Objects.hash(txt, isCorrect);
    }

    public int getTxt() {
        return txt;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
