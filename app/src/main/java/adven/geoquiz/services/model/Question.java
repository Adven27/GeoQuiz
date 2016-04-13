package adven.geoquiz.services.model;

public class Question {
    private final String txt;
    private final boolean isCorrect;

    public Question(String txt, boolean isCorrect) {
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

    public String getTxt() {
        return txt;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
