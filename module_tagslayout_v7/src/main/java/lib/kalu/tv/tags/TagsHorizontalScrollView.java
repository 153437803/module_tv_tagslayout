package lib.kalu.tv.tags;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import java.util.List;

import lib.kalu.tv.tags.model.TagsModel;

@Keep
class TagsHorizontalScrollView extends HorizontalScrollView {
    public TagsHorizontalScrollView(Context context) {
        super(context);
        init();
    }

    public TagsHorizontalScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TagsHorizontalScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TagsHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private final void init() {
        setFillViewport(true);
        setFocusable(false);
        setFocusableInTouchMode(false);
    }

    /********************/

    protected final void update(@NonNull String key, @NonNull List<TagsModel> list, @NonNull int textSize, @NonNull int paddingLeft, @NonNull int paddingRight) {

        if (null == key || key.length() == 0 || null == list || list.size() == 0)
            return;
        TagsLinearLayoutChild layout = new TagsLinearLayoutChild(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);

        layout.update(key, list, textSize, paddingLeft, paddingRight);
        addView(layout);
    }

    protected final void callback() {
        try {
            ((TagsLayout) getParent()).callback();
        } catch (Exception e) {
        }
    }

    protected final String[] searchTags() {
        int select = -1;
        TagsLinearLayoutChild layout = (TagsLinearLayoutChild) getChildAt(0);
        int count = layout.getChildCount();
        for (int i = 0; i < count; i++) {
            TagsTextView child = (TagsTextView) layout.getChildAt(i);
            if (child.isHightlight()) {
                select = i;
                break;
            }
        }
        if (select == -1) {
            select = 0;
        }
        TagsTextView child = (TagsTextView) layout.getChildAt(select);
        CharSequence hint = child.getHint();
        CharSequence text = child.getText();
        return new String[]{String.valueOf(hint), String.valueOf(text)};
    }
}
