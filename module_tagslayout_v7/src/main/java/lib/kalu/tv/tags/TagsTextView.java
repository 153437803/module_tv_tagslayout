package lib.kalu.tv.tags;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
class TagsTextView extends TextView {

    @ColorInt
    private int mTextColorFocus = Color.WHITE;
    @ColorInt
    private int mTextColorSelect = Color.RED;
    @ColorInt
    private int mTextColorDefault = Color.GRAY;

    @DrawableRes
    private int mBackgroundResourceFocus = R.drawable.module_tagslayout_ic_shape_background_focus;
    @DrawableRes
    private int mBackgroundResourceSelect = R.drawable.module_tagslayout_ic_shape_background_select;
    @DrawableRes
    private int mBackgroundResourceDefault = R.drawable.module_tagslayout_ic_shape_background_default;

    public TagsTextView(Context context) {
        super(context);
        init();
    }

    public TagsTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TagsTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TagsTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        CharSequence text = getText();
        if (null == text) {
            text = "";
        }
        int width = (int) getPaint().measureText(String.valueOf(text));
        if (width > 0) {
            width += getPaddingLeft();
            width += getPaddingRight();
        }
        setMeasuredDimension(width, height);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {

//        int start = -1;
//        if (start == -1 && isSelected() && focused && (direction == View.FOCUS_UP || direction == View.FOCUS_DOWN)) {
//            TagsLinearLayoutChild layout = (TagsLinearLayoutChild) getParent();
//            int count = layout.getChildCount();
//            for (int i = 0; i < count; i++) {
//                TagsTextView textView = (TagsTextView) layout.getChildAt(i);
//                if (textView.getPaint().getColor() == Color.parseColor("#ff673c")) {
//                    start = i;
//                    break;
//                }
//            }
//        }

//        if (start > 0) {
//            final int index = start;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    TagsLinearLayoutChild layout = (TagsLinearLayoutChild) getParent();
//                    TagsTextView textView = (TagsTextView) layout.getChildAt(index);
//                    textView.setTextColor(Color.parseColor("#ffffff"));
//                    textView.setBackgroundResource(R.drawable.ten_tag_hot_search_bg_focus);
//                    textView.requestFocus();
//                }
//            }, 40);
//            return;
//        }

        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        TagsUtil.logE("onFocusChanged => isSelected = " + isSelected() + ", focus = " + focused + ", direction = " + direction + ", start = " + -1 + ", text = " + getText());
        if (isSelected() && !focused) {
            setTextColor(mTextColorSelect);
            setBackgroundResource(mBackgroundResourceSelect);
        } else if (focused) {

            // 监听
            try {
                ((TagsLinearLayoutChild) getParent()).callback();
            } catch (Exception e) {
            }

            setTextColor(mTextColorFocus);
            setBackgroundResource(mBackgroundResourceFocus);
        } else {
            setTextColor(mTextColorDefault);
            setBackgroundResource(mBackgroundResourceDefault);
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Log.e("TagsTextView", "onKeyDown => keyCode = " + keyCode + ", action = " + event.getAction());
//        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_DPAD_UP) {
//            mAutoCall = false;
//            setSelected(true);
//        } else {
//            setSelected(false);
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
//        View focus = findFocus();
//        Log.e("TagsTextView111", "dispatchKeyEvent => focus = " + focus);
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void clearFocus() {
        setSelected(false);
        super.clearFocus();
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        setSelected(true);
        return super.requestFocus(direction, previouslyFocusedRect);
    }

    protected final void setText(@NonNull String text, boolean select) {
        super.setText(text);
        setTextColor(select ? mTextColorSelect : mTextColorDefault);
    }

    public final void hightlight() {
//        setSelected(true);
        setTextColor(mTextColorSelect);
        setBackgroundResource(mBackgroundResourceSelect);
    }

    protected final boolean isHightlight() {
        return hasFocus() || isSelected();
    }

//    protected final void forceFocus() {
//        mUpdate = true;
//        setSelected(true);
//        requestFocus();
//    }
//
//    protected final void forceClear() {
//        mUpdate = true;
//        setSelected(false);
//        clearFocus();
//    }

//    protected final void callback() {
//        // 监听
//        try {
//            ((TagsLinearLayoutChild) getParent()).callback();
//        } catch (Exception e) {
//        }
//    }

    private final void init() {
        setGravity(Gravity.CENTER);
        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setBackgroundColor(Color.TRANSPARENT);
    }

    /*************************/


    public void setTextColorFocus(int mTextColorFocus) {
        this.mTextColorFocus = mTextColorFocus;
    }

    public void setTextColorSelect(int mTextColorSelect) {
        this.mTextColorSelect = mTextColorSelect;
    }

    public void setTextColorDefault(int mTextColorDefault) {
        this.mTextColorDefault = mTextColorDefault;
    }

    public void setBackgroundResourceFocus(int mBackgroundResourceFocus) {
        this.mBackgroundResourceFocus = mBackgroundResourceFocus;
    }

    public void setBackgroundResourceSelect(int mBackgroundResourceSelect) {
        this.mBackgroundResourceSelect = mBackgroundResourceSelect;
    }

    public void setBackgroundResourceDefault(int mBackgroundResourceDefault) {
        this.mBackgroundResourceDefault = mBackgroundResourceDefault;
    }
}
