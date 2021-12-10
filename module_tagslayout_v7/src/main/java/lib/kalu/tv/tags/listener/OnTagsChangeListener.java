package lib.kalu.tv.tags.listener;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;

import java.util.Map;

@Keep
public interface OnTagsChangeListener {

    void onChange(@NonNull Map<String, String> map);
}
