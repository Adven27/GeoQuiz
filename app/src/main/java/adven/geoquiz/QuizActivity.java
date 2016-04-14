package adven.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import adven.geoquiz.services.Injection;
import adven.geoquiz.services.QuestionsService;
import adven.geoquiz.services.model.Question;

import static adven.geoquiz.R.string.toast_correct;
import static adven.geoquiz.R.string.toast_incorrect;
import static android.widget.Toast.LENGTH_SHORT;

public class QuizActivity extends AppCompatActivity implements QuizContract.View {

    private static final String KEY_INDEX = "index";
    private Button mCheatBtn;
    private Button mTrueBtn;
    private Button mFalseBtn;
    private ImageButton mNextBtn;
    private ImageButton mPrevBtn;

    private TextView mQuestionTextView;
    private int mCurrentQuestionIndex = 1;
    private QuizPresenter mActionListener;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, mCurrentQuestionIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionListener = new QuizPresenter(Injection.provide(QuestionsService.class), this);

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
        mActionListener.getQuestion(mCurrentQuestionIndex);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mActionListener.getQuestion(mCurrentQuestionIndex + 1);
            }
        });
    }

    private void setUpQuizButtons() {
        mCheatBtn = (Button) findViewById(R.id.cheat_btn);
        mTrueBtn = (Button) findViewById(R.id.true_btn);
        mFalseBtn = (Button) findViewById(R.id.false_btn);
        mNextBtn = (ImageButton) findViewById(R.id.next_btn);
        mPrevBtn = (ImageButton) findViewById(R.id.prev_btn);

        mTrueBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mActionListener.checkAnswer(mCurrentQuestionIndex, true);
            }
        });
        mFalseBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mActionListener.checkAnswer(mCurrentQuestionIndex, false);
            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mActionListener.getQuestion(mCurrentQuestionIndex + 1);
            }
        });
        mPrevBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mActionListener.getQuestion(mCurrentQuestionIndex - 1);
            }
        });

        mCheatBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               mActionListener.showCheatScreen(mCurrentQuestionIndex);
            }
        });
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
    public void showCheckResult(boolean isCorrect) {
        Toast.makeText(this, isCorrect? toast_correct : toast_incorrect, LENGTH_SHORT).show();
    }

    @Override
    public void showQuestion(Question q) {
       mQuestionTextView.setText( q != null ? q.getTxt() : "No question" );
    }

    @Override
    public void showCheatScreen(boolean answer) {
        Intent i = new Intent(QuizActivity.this, CheatActivity.class);
        startActivity(i);
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