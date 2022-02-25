package lib.kalu.tv.tags;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.List;

import lib.kalu.tv.tags.model.TagsModel;

@Keep
class TagsLinearLayoutChild extends LinearLayout {

    public TagsLinearLayoutChild(Context context) {
        super(context);
        init();
    }

    public TagsLinearLayoutChild(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TagsLinearLayoutChild(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TagsLinearLayoutChild(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private final void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // repeat
        if (event.getRepeatCount() > 0) {
            return true;
        }
        // up come
        else if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            HorizontalScrollView parent = (HorizontalScrollView) getParent();
            LinearLayout root = (LinearLayout) parent.getParent();
            int index = root.indexOfChild(parent);
            Log.e("TagsLinearLayoutChild", "dispatchKeyEvent[up come] => index = " + index);
            resetFocus();
            return true;
        }
        // down come
        else if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            HorizontalScrollView parent = (HorizontalScrollView) getParent();
            LinearLayout root = (LinearLayout) parent.getParent();
            int index = root.indexOfChild(parent);
            Log.e("TagsLinearLayoutChild", "dispatchKeyEvent[down come] => index = " + index);
            resetFocus();
            return true;
        }
        // right come
        else if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
            View focus = findFocus();
            View next = FocusFinder.getInstance().findNextFocus(this, focus, View.FOCUS_RIGHT);
            if (null == next && focus instanceof TagsLinearLayoutChild) {
                Log.e("TagsLinearLayoutChild", "dispatchKeyEvent[right come] => focus = " + focus + ", next = " + next);
                resetFocus();
                return true;
            }
        }
        // left come
        else if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
            Log.e("TagsLinearLayoutChild", "dispatchKeyEvent[left come] => action = " + event.getAction() + ", code = " + event.getKeyCode());
        }
        // right move
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
            View focus = findFocus();
            if (null != focus && focus instanceof TagsTextView) {
                int index = indexOfChild(focus);
                int count = getChildCount();
                if (index + 1 == count) {
                    clearFocus();
                    ((TagsTextView) focus).hightlight();
                    Log.e("TagsLinearLayoutChild", "dispatchKeyEvent[right leave] => focus = " + focus + ", index = " + index + ", count = " + count);
                } else {
                    Log.e("TagsLinearLayoutChild", "dispatchKeyEvent[right move] => focus = " + focus + ", index = " + index + ", count = " + count);
                    focus.clearFocus();
                    nextFocus(index + 1);
                }
            }
            return true;
        }
        // left move
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
            View focus = findFocus();
            Log.e("TagsLinearLayoutChild", "dispatchKeyEvent[left] => focus = " + focus);
            if (null != focus && focus instanceof TagsTextView) {
                int index = indexOfChild(focus);
                int count = getChildCount();
                if (index <= 0) {
                    clearFocus();
                    ((TagsTextView) focus).hightlight();
                    Log.e("TagsLinearLayoutChild", "dispatchKeyEvent[left leave] => focus = " + focus + ", index = " + index + ", count = " + count);
                } else {
                    Log.e("TagsLinearLayoutChild", "dispatchKeyEvent[left move] => focus = " + focus + ", index = " + index + ", count = " + count);
                    focus.clearFocus();
                    nextFocus(index - 1);
                }
            }
            return true;
        }
        // up
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            HorizontalScrollView parent = (HorizontalScrollView) getParent();
            TagsLayout root = (TagsLayout) parent.getParent();
            int index = root.indexOfChild(parent);
            if (index > 0) {
                Log.e("TagsLinearLayoutChild", "dispatchKeyEvent[up come] => index = " + (index - 1));
                ((TagsLinearLayoutChild) ((ViewGroup) root.getChildAt(index - 1)).getChildAt(0)).resetFocus();
                return true;
            } else {
                Log.e("TagsLinearLayoutChild", "dispatchKeyEvent[up leave] => index = " + index);
            }
        }
        // down leave
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            HorizontalScrollView parent = (HorizontalScrollView) getParent();
            LinearLayout root = (LinearLayout) parent.getParent();
            int index = root.indexOfChild(parent);
            int count = root.getChildCount();
            if (index + 1 < count) {
                Log.e("TagsLinearLayoutChild", "dispatchKeyEvent[down come] => index = " + (index + 1));
                ((TagsLinearLayoutChild) ((ViewGroup) root.getChildAt(index + 1)).getChildAt(0)).resetFocus();
                return true;
            } else {
                Log.e("TagsLinearLayoutChild", "dispatchKeyEvent[down leave] => index = " + index);
            }
        } else {
            View focus = findFocus();
            Log.e("TagsLinearLayoutChild", "dispatchKeyEvent[null] => focus = " + focus);
        }
        return super.dispatchKeyEvent(event);
    }

    protected final View searchFocus() {

        try {
            int index = -1;
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                if (null != child && child instanceof TagsTextView) {
                    TagsTextView textView = (TagsTextView) child;
                    boolean hightlight = textView.isHightlight();
                    Log.e("TagsLinearLayoutChild", "searchFocus[" + i + "] => i = " + i + ", hightlight = " + hightlight + ", text = " + textView.getText());
                    if (hightlight) {
                        index = i;
                        break;
                    }
                }
            }

            if (index == -1) {
                index = 0;
            }

            Log.e("TagsLinearLayoutChild", "reset => index = " + index);
            View child = getChildAt(index);
            return child;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * FOCUS_BLOCK_DESCENDANTS: 拦截焦点, 直接自己尝试获取焦点
     * FOCUS_BEFORE_DESCENDANTS: 首先自己尝试获取焦点, 如果自己不能获取焦点, 则尝试让子控件获取焦点
     * FOCUS_AFTER_DESCENDANTS: 首先尝试把焦点给子控件, 如果所有子控件都不要, 则自己尝试获取焦点
     */
    protected final void nextFocus(@NonNull int index) {
        int count = getChildCount();
        if (index + 1 > count)
            return;
        View focus = getChildAt(index);
        if (focus instanceof TagsTextView) {
            TagsTextView textView = (TagsTextView) focus;
            Log.e("TagsLinearLayoutChild", "nextFocus => focus = " + focus + ", text = " + textView.getText() + ", index = " + index);
            // 首先自己尝试获取焦点, 如果自己不能获取焦点, 则尝试让子控件获取焦点
            setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
            textView.requestFocus();
            setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        }
    }

    /**
     * FOCUS_BLOCK_DESCENDANTS: 拦截焦点, 直接自己尝试获取焦点
     * FOCUS_BEFORE_DESCENDANTS: 首先自己尝试获取焦点, 如果自己不能获取焦点, 则尝试让子控件获取焦点
     * FOCUS_AFTER_DESCENDANTS: 首先尝试把焦点给子控件, 如果所有子控件都不要, 则自己尝试获取焦点
     */
    protected final void resetFocus() {

        View focus = searchFocus();
        Log.e("TagsLinearLayoutChild", "nextFocus => focus = " + focus);
        if (null == focus)
            return;

        if (focus instanceof TagsTextView) {
            TagsTextView textView = (TagsTextView) focus;
            Log.e("TagsLinearLayoutChild", "nextFocus => focus = " + focus + ", text = " + textView.getText());
            // 首先自己尝试获取焦点, 如果自己不能获取焦点, 则尝试让子控件获取焦点
            setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
            textView.requestFocus();
            setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        }
    }

    /*************/

    protected final void update(@NonNull String key, @NonNull List<TagsModel> list, @NonNull int textSize, @NonNull int paddingLeft, @NonNull int paddingRight) {

        if (null == key || key.length() == 0 || null == list || list.size() == 0)
            return;

        Log.e("TagsLinearLayoutChild", "**********************");
        int size = list.size();
        for (int i = 0; i < size; i++) {

            TagsModel temp = list.get(i);
            if (null == temp)
                continue;

            String initText = temp.initText();
            Log.e("TagsLinearLayoutChild", "update => initText = " + initText);
            if (null == initText || initText.length() == 0)
                continue;

            TagsTextView child = new TagsTextView(getContext());
            child.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

            child.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            child.setPadding(paddingLeft, 0, paddingRight, 0);

            child.setTextColorDefault(temp.initTextColorDetault());
            child.setTextColorSelect(temp.initTextColorSelect());
            child.setTextColorFocus(temp.initTextColorFocus());

            child.setBackgroundResourceDefault(temp.initBackgroundResourceDefault());
            child.setBackgroundResourceSelect(temp.initBackgroundResourceSelect());
            child.setBackgroundResourceFocus(temp.initBackgroundResourceFocus());

            child.setSelected(false);
            child.setText(initText, i == 0);
            child.setHint(key);
            addView(child);
        }
        Log.e("TagsLinearLayoutChild", "**********************");
    }

    protected final void callback() {
        try {
            ((TagsHorizontalScrollView) getParent()).callback();
        } catch (Exception e) {
        }
    }
}
