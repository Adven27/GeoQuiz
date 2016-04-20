package adven.geoquiz;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;

import adven.geoquiz.QuizContract.UserActionsListener.QuestionNotFoundException;
import adven.geoquiz.services.Injection;
import adven.geoquiz.services.QuestionsService;
import adven.geoquiz.services.model.Question;
import adven.geoquiz.util.ToastManager;

import static adven.geoquiz.R.string.toast_correct;
import static adven.geoquiz.R.string.toast_incorrect;
import static adven.geoquiz.R.string.toast_judgment;
import static android.widget.Toast.LENGTH_SHORT;

@EActivity(R.layout.activity_quiz)
public class QuizActivity extends AppCompatActivity implements QuizContract.View {
    public static final int REQ_CODE_CHEAT = 0;

    @ViewById(R.id.question_txt) TextView mQuestionTextView;
    @ViewById(R.id.toolbar) Toolbar toolbar;

    @InstanceState int mCurrentQuestionIndex = 0;
    @InstanceState HashMap<Integer, Boolean> mIsCheater = new HashMap<>();

    private QuizPresenter mActionListener;

    @AfterViews
    void setUp() {
        mActionListener = new QuizPresenter(Injection.provide(QuestionsService.class), this);
        mActionListener.getQuestion(mCurrentQuestionIndex);
        setSupportActionBar(toolbar);
    }

    @Click(R.id.question_txt)
    void onQuestionTextClick(){
        mActionListener.getQuestion(mCurrentQuestionIndex + 1);
    }

    @Click
    void cheatBtn() {
        mActionListener.showCheatScreen(mCurrentQuestionIndex);
    }

    @Click
    void nextBtn() {
        try {
            mActionListener.getQuestion(++mCurrentQuestionIndex);
        } catch (QuestionNotFoundException e) {
            mCurrentQuestionIndex--;
        }
    }

    @Click
    void prevBtn() {
        try {
            mActionListener.getQuestion(--mCurrentQuestionIndex);
        } catch (QuestionNotFoundException e) {
            mCurrentQuestionIndex++;
        }
    }

    @Click
    void falseBtn() {
        mActionListener.checkAnswer(mCurrentQuestionIndex, false);
    }

    @Click
    void trueBtn() {
        mActionListener.checkAnswer(mCurrentQuestionIndex, true);
    }

    @Click
    void fab(View view){
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @OnActivityResult(REQ_CODE_CHEAT)
    protected void onResult(int resultCode, @OnActivityResult.Extra(CheatActivity.EXTRA_ANSWER_SHOWN) boolean wasAnswerShown) {
        if (resultCode != RESULT_OK) {
            return;
        }
        mIsCheater.put(mCurrentQuestionIndex, wasAnswerShown);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showCheckResult(boolean isCorrect) {
        Boolean isCheater = mIsCheater.get(mCurrentQuestionIndex);
        ToastManager.makeText(this, isCheater != null && isCheater ? toast_judgment :
                isCorrect ? toast_correct : toast_incorrect, LENGTH_SHORT).show();
    }

    @Override
    public void showQuestion(Question q) {
        mQuestionTextView.setText(q.getTxt());
    }

    @Override
    public void showCheatScreen(boolean answer) {
        CheatActivity_.intent(this).answer(answer).startForResult(REQ_CODE_CHEAT);
    }
}