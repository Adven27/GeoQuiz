package adven.geoquiz;

public class CheatPresenter implements CheatContract.UserActionsListener{
    private final CheatContract.View view;

    public CheatPresenter(CheatContract.View view) {
        this.view = view;
    }

    @Override
    public void showAnswer(boolean answer) {
        view.showAnswer(answer);
    }
}
