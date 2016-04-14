package adven.geoquiz;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import adven.geoquiz.util.EspressoIdlingResource;

import static adven.geoquiz.R.string.false_btn;
import static adven.geoquiz.R.string.true_btn;

public class CheatActivity extends AppCompatActivity implements CheatContract.View{

    public static final String EXTRA_ANSWER = "adven.geoquiz.is_true_answer";
    private TextView mAnswerTextView;
    private CheatPresenter mActionListener;
    private Button mShowAnswerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionListener = new CheatPresenter(this);

        setContentView(R.layout.activity_cheat);
        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);

        mShowAnswerBtn = (Button) findViewById(R.id.showAnswerBtn);
        mShowAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionListener.showAnswer(getIntent().getBooleanExtra(EXTRA_ANSWER, false));
            }
        });


    }

    @Override
    public void showAnswer(boolean answer) {
        mAnswerTextView.setText(answer? true_btn : false_btn);
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
