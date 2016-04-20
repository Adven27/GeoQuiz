package adven.geoquiz;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CheatScreenTest extends ApplicationTest {

    @Rule
    public ActivityTestRule<CheatActivity_> mCheatActivityTestRule =
            new ActivityTestRule<>(CheatActivity_.class, true /* Initial touch mode  */, false /* Lazily launch activity */);

    @Before
    public void intentWithStubbedNoteId() {
        Intent startIntent = new Intent();
        startIntent.putExtra(CheatActivity.EXTRA_ANSWER, true);
        mCheatActivityTestRule.launchActivity(startIntent);
    }

    @Test
    public void shouldShowOnlyWarning() throws Exception {
        onView(withText(R.string.warning_txt)).check(matches(isDisplayed()));
        onView(withId(R.id.answerTextView)).check(matches(withText("")));
    }

    @Test
    public void shouldShowAnswer() throws Exception {
        onView(withId(R.id.showAnswerBtn)).perform(click());
        onView(withId(R.id.answerTextView)).check(matches(withText(R.string.true_btn)));
    }
}