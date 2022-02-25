package lib.kalu.tv.tags.listener;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import java.util.Map;

@Keep
public interface OnTagsChangeListener {

    void onChange(@NonNull Map<String, String> map);
}
