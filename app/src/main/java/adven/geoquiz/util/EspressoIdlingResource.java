package adven.geoquiz.util;

import android.support.test.espresso.IdlingResource;

public class EspressoIdlingResource {

    private static final String RESOURCE = "GLOBAL";

    private static SimpleCountingIdlingResource mCountingIdlingResource = new SimpleCountingIdlingResource(RESOURCE);
    private static SimpleFlagIdlingResource mFlagIdlingResource = new SimpleFlagIdlingResource(RESOURCE);

    public static void increment() {
        mCountingIdlingResource.increment();
    }

    public static void decrement() {
        mCountingIdlingResource.decrement();
    }

    public static void idle() {
        mFlagIdlingResource.idle();
    }

    public static void busy() {
        mFlagIdlingResource.busy();
    }

    public static IdlingResource getIdlingResource() {
        return mCountingIdlingResource;
    }

    public static IdlingResource getFlagIdlingResource() {
        return mFlagIdlingResource;
    }
}