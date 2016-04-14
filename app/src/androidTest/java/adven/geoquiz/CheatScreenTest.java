package adven.geoquiz;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
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
    public ActivityTestRule<CheatActivity> mCheatActivityTestRule =
            new ActivityTestRule<>(CheatActivity.class, true /* Initial touch mode  */, false /* Lazily launch activity */);

    @Before
    public void intentWithStubbedNoteId() {
        Intent startIntent = new Intent();
        startIntent.putExtra(CheatActivity.EXTRA_ANSWER, true);
        mCheatActivityTestRule.launchActivity(startIntent);

        registerIdlingResource();
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

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    public void unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(
                mCheatActivityTestRule.getActivity().getCountingIdlingResource());
    }

    /**
     * Convenience method to register an IdlingResources with Espresso. IdlingResource resource is
     * a great way to tell Espresso when your app is in an idle state. This helps Espresso to
     * synchronize your test actions, which makes tests significantly more reliable.
     */
    private void registerIdlingResource() {
        Espresso.registerIdlingResources(
                mCheatActivityTestRule.getActivity().getCountingIdlingResource());
    }
}