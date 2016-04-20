package adven.geoquiz.util;

import android.support.annotation.NonNull;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.google.common.base.Preconditions.checkNotNull;

public class SimpleFlagIdlingResource implements IdlingResource {
    private final String resource;

    // written from main thread, read from any thread.
    private volatile ResourceCallback resourceCallback;

    private final AtomicBoolean isIdle = new AtomicBoolean(true);

    public SimpleFlagIdlingResource(String resource) {
        this.resource = checkNotNull(resource);
    }

    @Override
    public String getName() {
        return resource;
    }

    @Override
    public boolean isIdleNow() {
        return isIdle.get();
    }

    @Override
    public void registerIdleTransitionCallback(@NonNull ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    public void idle() {
        isIdle.getAndSet(true);
        resourceCallback.onTransitionToIdle();
    }

    public void busy() {
        isIdle.getAndSet(false);
    }
}
