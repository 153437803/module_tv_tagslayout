package lib.kalu.tv.tags;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class TagsUtil {

    private static final String TAG = "module_tagslayout";

    public static final void logE(@NonNull String message) {
        logE(message, null);
    }

    public static final void logE(@NonNull String message, @Nullable Throwable tr) {

        if (null == message || message.length() == 0)
            return;

        if (null == tr) {
            Log.e(TAG, message);
        } else {
            Log.e(TAG, message, tr);
        }
    }
}
