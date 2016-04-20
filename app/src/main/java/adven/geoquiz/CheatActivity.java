package adven.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import static adven.geoquiz.R.string.false_btn;
import static adven.geoquiz.R.string.true_btn;

@EActivity(R.layout.activity_cheat)
public class CheatActivity extends AppCompatActivity implements CheatContract.View{
    public static final String EXTRA_ANSWER = "adven.geoquiz.is_true_answer";
    public static final String EXTRA_ANSWER_SHOWN = "adven.geoquiz.answer_shown";

    @ViewById(R.id.answerTextView) TextView mAnswerTextView;
    @InstanceState boolean mIsAnswerShown;
    @Extra(EXTRA_ANSWER) boolean answer;

    private CheatPresenter mActionListener;

    @AfterViews
    void setUp() {
        mActionListener = new CheatPresenter(this);
        if (mIsAnswerShown) {
            setAnswerShownResult();
        }
    }

    @Click
    void showAnswerBtn() {
        mActionListener.showAnswer(answer);
    }

    @Override
    public void showAnswer(boolean answer) {
        mAnswerTextView.setText(answer? true_btn : false_btn);
        setAnswerShownResult();
    }

    private void setAnswerShownResult() {
        mIsAnswerShown = true;
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, mIsAnswerShown);
        setResult(RESULT_OK, data);
    }
}