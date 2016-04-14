package adven.geoquiz;

import android.support.test.espresso.Root;

import adven.geoquiz.custom.ToastMatcher;

public class ApplicationTest {

    public static Matcher<Root> isToast() {
        return new ToastMatcher();
    }

}