package adven.geoquiz;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import adven.geoquiz.services.Injection;
import adven.geoquiz.services.QuestionsService;
import adven.geoquiz.services.model.Question;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class QuizScreenTest extends ApplicationTest {
    public static final Question QUESTION = new Question("My QUESTIONNNN!!!", true);
    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     *
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    @SuppressWarnings("unchecked")
    public ActivityTestRule<QuizActivity> mActivityTestRule = new ActivityTestRule(QuizActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
            Injection.bind(QuestionsService.class, new QuestionsService() {
                @Override
                public Question getQuestion(int index) {
                    return QUESTION;
                }
            });
        }
    };

    @Test
    public void questionShouldBeVisible() throws Exception {
        onView(withId(R.id.question_txt)).check(matches(withText(QUESTION.getTxt())));
    }

    @Test
    public void shouldCanAnswerRight() throws Exception {
        onView(withId(R.id.true_btn)).perform(click());
        onView(withText(R.string.toast_correct)).inRoot(isToast()).check(matches(isDisplayed()));
    }

    @Test
    public void shouldCanAnswerWrong() throws Exception {
        onView(withId(R.id.false_btn)).perform(click());
        onView(withText(R.string.toast_incorrect)).inRoot(isToast()).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowCheatScreen() throws Exception {
        onView(withId(R.id.cheat_btn)).perform(click());
        onView(withId(R.id.showAnswerBtn)).check(matches(isDisplayed()));
    }
}