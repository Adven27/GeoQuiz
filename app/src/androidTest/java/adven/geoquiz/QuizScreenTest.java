package adven.geoquiz;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import adven.geoquiz.services.Injection;
import adven.geoquiz.services.QuestionsService;
import adven.geoquiz.services.model.Question;
import adven.geoquiz.util.EspressoIdlingResource;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class QuizScreenTest extends ApplicationTest {
    public static final Question QUESTION_TRUE = new Question("True question?", true);
    public static final Question QUESTION_FALSE = new Question("False question?", false);
    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     *
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    @SuppressWarnings("unchecked")
    public ActivityTestRule<QuizActivity> mActivityTestRule = new ActivityTestRule(QuizActivity_.class) {
        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
            Injection.bind(QuestionsService.class, new QuestionsService() {
                @Override
                public Question getQuestion(int index) {
                    return Arrays.asList(QUESTION_TRUE, QUESTION_FALSE).get(index);
                }
            });
        }
    };
    private IdlingResource flagIdlingResource = EspressoIdlingResource.getFlagIdlingResource();
    //    private GenymotionManager genymotion = GenymotionManager.getGenymotionManager(mActivityTestRule.getActivity());

    @Before
    public void setUp() throws Exception {
        Espresso.registerIdlingResources(flagIdlingResource);
        //EspressoIdlingResource.idle();
    }

    @After
    public void unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(flagIdlingResource);
    }

    @Test
    public void shouldShowQuestion() throws Exception {
        onView(withId(R.id.question_txt)).check(matches(withText(QUESTION_TRUE.getTxt())));
    }

    @Test
    public void shouldIndicateThatAnswerIsRight() throws Exception {
        onView(withId(R.id.trueBtn)).perform(click());
        checkToast(R.string.toast_correct);
    }

    @Test
    public void shouldIndicateThatAnswerIsWrong() throws Exception {
        onView(withId(R.id.falseBtn)).perform(click());
        checkToast(R.string.toast_incorrect);
    }

    @Test
    public void shouldShowCheatScreen() throws Exception {
        onView(withId(R.id.cheatBtn)).perform(click());
        onView(withId(R.id.showAnswerBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowJudgmentWhenCheated() throws Exception {
        onView(withId(R.id.cheatBtn)).perform(click());
        onView(withId(R.id.showAnswerBtn)).check(matches(isDisplayed())).perform(click());
        pressBack();
        onView(withId(R.id.trueBtn)).perform(click());
        checkToast(R.string.toast_judgment);
    }

    @Test
    public void shouldShowJudgmentWhenCheatedEvenAfterQuizScreenRotation() throws Exception {
        onView(withId(R.id.cheatBtn)).perform(click());
        onView(withId(R.id.showAnswerBtn)).check(matches(isDisplayed())).perform(click());
        pressBack();
        rotateScreen();
        onView(withId(R.id.trueBtn)).perform(click());
        checkToast(R.string.toast_judgment);
    }

    @Test
    public void shouldShowJudgmentWhenCheatedEvenAfterForwardBackQuestionChanging() throws Exception {
        onView(withId(R.id.cheatBtn)).perform(click());
        onView(withId(R.id.showAnswerBtn)).check(matches(isDisplayed())).perform(click());
        pressBack();

        onView(withId(R.id.nextBtn)).perform(click());
        onView(withId(R.id.prevBtn)).perform(click());

        onView(withId(R.id.trueBtn)).perform(click());
        checkToast(R.string.toast_judgment);
    }

    @Test
    public void shouldShowJudgmentWhenCheatedEvenAfterCheatScreenRotation() throws Exception {
        onView(withId(R.id.cheatBtn)).perform(click());
        onView(withId(R.id.showAnswerBtn)).check(matches(isDisplayed())).perform(click());

        rotateScreen();
        pressBack();

        onView(withId(R.id.trueBtn)).perform(click());
        checkToast(R.string.toast_judgment);
    }

    private void checkToast(int toastTxt) {
        onView(withText(toastTxt)).inRoot(isToast()).check(matches(isDisplayed()));
        EspressoIdlingResource.busy();
    }

    private void rotateScreen() {
        Context context = InstrumentationRegistry.getTargetContext();
        int o = context.getResources().getConfiguration().orientation;
        mActivityTestRule.getActivity().setRequestedOrientation(
            o == ORIENTATION_PORTRAIT ? SCREEN_ORIENTATION_LANDSCAPE : SCREEN_ORIENTATION_PORTRAIT);
    }
}