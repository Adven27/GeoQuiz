package adven.geoquiz;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import adven.geoquiz.services.QuestionsService;
import adven.geoquiz.services.model.Question;

public class QuizActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "index";
    private Button mTrueBtn;
    private Button mFalseBtn;
    private ImageButton mNextBtn;
    private ImageButton mPrevBtn;
    private TextView mQuestionTextView;

    private final QuestionsService mQuestionsService = new QuestionsService();
    private int mCurrentQuestionIndex = 1;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, mCurrentQuestionIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mCurrentQuestionIndex = savedInstanceState.getInt(KEY_INDEX);
        }

        setContentView(R.layout.activity_quiz);

        setUpQuestionTextView();
        setUpQuizButtons();
        setUpToolbar();
        setUpFloatingActionButton();
    }

    private void setUpQuestionTextView() {
        mQuestionTextView = (TextView) findViewById(R.id.question_txt);
        updateQuestion(mCurrentQuestionIndex);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });
    }

    private void setUpQuizButtons() {
        mTrueBtn = (Button) findViewById(R.id.true_btn);
        mFalseBtn = (Button) findViewById(R.id.false_btn);
        mNextBtn = (ImageButton) findViewById(R.id.next_btn);
        mPrevBtn = (ImageButton) findViewById(R.id.prev_btn);

        mTrueBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });
        mPrevBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                prevQuestion();
            }
        });
    }

    private void prevQuestion() {
        updateQuestion(mCurrentQuestionIndex - 1);
    }

    private void nextQuestion() {
        updateQuestion(mCurrentQuestionIndex + 1);
    }

    private void updateQuestion(int i) {
        Question newQuestion = mQuestionsService.getQuestion(i);
        if (newQuestion != null) {
            mCurrentQuestionIndex = i;
            mQuestionTextView.setText(newQuestion.getTxt());
        }
    }

    private void checkAnswer(boolean isCorrect) {
        if (isCorrect == mQuestionsService.getQuestion(mCurrentQuestionIndex).isCorrect()) {
            showCorrectToast();
        } else {
            showIncorrectToast();
        }
    }

    private void showIncorrectToast() {
        Toast.makeText(this, R.string.toast_incorrect, Toast.LENGTH_SHORT).show();
    }

    private void showCorrectToast() {
        Toast.makeText(this, R.string.toast_correct, Toast.LENGTH_SHORT).show();
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
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
}
