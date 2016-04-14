package adven.geoquiz;

public interface CheatContract {

    interface View {
        void showAnswer(boolean answer);
    }

    interface UserActionsListener {
        void showAnswer(boolean answer);
    }
}
