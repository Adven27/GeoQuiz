package adven.geoquiz.util;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.view.View;
import android.widget.Toast;

public final class ToastManager {
    private static final View.OnAttachStateChangeListener listener = new View.OnAttachStateChangeListener() {
        @Override
        public void onViewAttachedToWindow(final View v) {}

        @Override
        @VisibleForTesting
        public void onViewDetachedFromWindow(final View v) {
           EspressoIdlingResource.idle();
        }
    };

    private ToastManager() { }

    public static Toast makeText(final Context context, final CharSequence text, final int duration) {
        Toast t = Toast.makeText(context, text, duration);
        t.getView().addOnAttachStateChangeListener(listener);
        return t;
    }

    public static Toast makeText(final Context context, final int resId, final int duration) {
        Toast t = Toast.makeText(context, resId, duration);
        t.getView().addOnAttachStateChangeListener(listener);
        return t;
    }
}