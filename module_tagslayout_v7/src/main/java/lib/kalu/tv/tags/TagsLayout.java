package lib.kalu.tv.tags;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.kalu.tv.R;
import lib.kalu.tv.tags.listener.OnTagsChangeListener;
import lib.kalu.tv.tags.model.TagsModel;

@Keep
public class TagsLayout extends LinearLayout {

    private int mItemHeight = 0;
    private int mItemPaddingTop = 0;
    private int mItemPaddingBottom = 0;

    private int mTextSize = 0;
    private int mTextPaddingLeft = 0;
    private int mTextPaddingRight = 0;

    private int mUnderlinePaddingLeft = 0;
    private int mUnderlinePaddingRight = 0;
    private int mUnderlineHeight = 0;
    @ColorInt
    private int mUnderlineColor = Color.TRANSPARENT;

    public TagsLayout(Context context) {
        super(context);
        init(null);
    }

    public TagsLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TagsLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        TagsUtil.logE("dispatchDraw =>");
        if (mUnderlineColor != Color.TRANSPARENT && mUnderlineHeight > 0) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setDither(true);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(mUnderlineColor);
            paint.setStrokeWidth(mUnderlineHeight);
            Rect rectF = new Rect();
            int width = getWidth();
            int height = getHeight();
            rectF.left = mUnderlinePaddingLeft;
            rectF.right = width - mUnderlinePaddingRight;
            rectF.top = height - mUnderlineHeight / 2;
            rectF.bottom = height;
            canvas.drawRect(rectF, paint);
            TagsUtil.logE("dispatchDraw[drawRect] => left = " + rectF.left + ", top = " + rectF.top + ", right = " + rectF.right + ", bottom = " + rectF.bottom + ", height = " + height + ", width = " + width + ", lineHeight = " + mUnderlineHeight + ", lineColor = " + mUnderlineColor);
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        TagsUtil.logE("drawChild => child = " + child);
        return super.drawChild(canvas, child, drawingTime);
    }

    private final void init(@Nullable AttributeSet attrs) {
        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(LinearLayout.VERTICAL);
        setFocusable(false);
        setFocusableInTouchMode(false);
        TypedArray attributes = null;
        try {
            attributes = getContext().obtainStyledAttributes(attrs, R.styleable.TagsLayout);
            mItemHeight = attributes.getDimensionPixelOffset(R.styleable.TagsLayout_tl_item_height, 0);
            mItemPaddingTop = attributes.getDimensionPixelOffset(R.styleable.TagsLayout_tl_item_padding_top, 0);
            mItemPaddingBottom = attributes.getDimensionPixelOffset(R.styleable.TagsLayout_tl_item_padding_bottom, 0);
            mTextSize = attributes.getDimensionPixelOffset(R.styleable.TagsLayout_tl_text_size, 0);
            mTextPaddingLeft = attributes.getDimensionPixelOffset(R.styleable.TagsLayout_tl_text_padding_left, 0);
            mTextPaddingRight = attributes.getDimensionPixelOffset(R.styleable.TagsLayout_tl_text_padding_right, 0);
            mUnderlineHeight = attributes.getDimensionPixelOffset(R.styleable.TagsLayout_tl_underline_height, 0);
            mUnderlinePaddingLeft = attributes.getDimensionPixelOffset(R.styleable.TagsLayout_tl_underline_padding_left, 0);
            mUnderlinePaddingRight = attributes.getDimensionPixelOffset(R.styleable.TagsLayout_tl_underline_padding_right, 0);
            mUnderlineColor = attributes.getColor(R.styleable.TagsLayout_tl_underline_color, Color.TRANSPARENT);
        } catch (Exception e) {
        }
        if (null != attributes) {
            attributes.recycle();
        }
        if (mTextSize <= 0) {
            mTextSize = getResources().getDimensionPixelOffset(R.dimen.module_tagslayout_text_size_default);
        }
        if (mItemHeight <= 0) {
            mItemHeight = getResources().getDimensionPixelOffset(R.dimen.module_tagslayout_item_height_default);
        }
        if (mTextPaddingLeft <= 0) {
            mTextPaddingLeft = getResources().getDimensionPixelOffset(R.dimen.module_tagslayout_text_padding_left_default);
        }
        if (mTextPaddingRight <= 0) {
            mTextPaddingRight = getResources().getDimensionPixelOffset(R.dimen.module_tagslayout_text_padding_right_default);
        }
        if (mItemPaddingTop <= 0) {
            mItemPaddingTop = getResources().getDimensionPixelOffset(R.dimen.module_tagslayout_item_padding_top_default);
        }
        if (mItemPaddingBottom <= 0) {
            mItemPaddingBottom = getResources().getDimensionPixelOffset(R.dimen.module_tagslayout_item_padding_bottom_default);
        }
    }

    /*************/

    private final void add(@NonNull String key, @NonNull List<TagsModel> list) {
        TagsHorizontalScrollView child = new TagsHorizontalScrollView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, mItemHeight);
        child.setPadding(0, mItemPaddingTop, 0, mItemPaddingBottom);
        child.setLayoutParams(params);

        child.update(key, list, mTextSize, mTextPaddingLeft, mTextPaddingRight);
        addView(child);
    }

    @Keep
    public final void update(@NonNull Map<String, List<TagsModel>> map) {

        if (null == map || map.size() == 0)
            return;

        for (String key : map.keySet()) {
            if (null == key || key.length() == 0)
                continue;
            List<TagsModel> list = map.get(key);
            if (null == list || list.size() == 0)
                continue;
            add(key, list);
        }
    }

    @Keep
    public final Map<String, String> getTags() {
        HashMap<String, String> map = new HashMap<>();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            TagsHorizontalScrollView layout = (TagsHorizontalScrollView) getChildAt(i);
            String[] strings = layout.searchTags();
            map.put(strings[0], strings[1]);
        }
        return map;
    }

//    @Keep
//    public final int getSelect() {
//        int select = -1;
//        try {
//            int count1 = getChildCount();
//            for (int i = 0; i < count1; i++) {
//                TagsLinearLayoutChild linearLayoutChild = (TagsLinearLayoutChild) ((ViewGroup) getChildAt(i)).getChildAt(0);
//                int count2 = linearLayoutChild.getChildCount();
//                for (int j = 0; j < count2; j++) {
//                    View child = linearLayoutChild.getChildAt(j);
//                    if (child.hasFocus()) {
//                        select = i;
//                        break;
//                    }
//                }
//                if (select != -1)
//                    break;
//            }
//        } catch (Exception e) {
//        }
//        return select;
//    }

    @Keep
    public final boolean isFirst(@NonNull View view) {
        boolean status = false;
        if (null != view && view instanceof TagsTextView) {
            try {
                int count = getChildCount();
                for (int i = 0; i < count; i++) {
                    TagsLinearLayoutChild layout = (TagsLinearLayoutChild) ((TagsHorizontalScrollView) getChildAt(i)).getChildAt(0);
                    int index = layout.indexOfChild(view);
                    if (index == 0) {
                        status = true;
                        break;
                    }
                }
            } catch (Exception e) {
            }
        }
        Log.e("TagsLinearLayoutChild", "isFirst => status = " + status);
        return status;
    }

    @Keep
    public final boolean isLast(@NonNull View view) {
        boolean status = false;
        if (null != view && view instanceof TagsTextView) {
            try {
                int count = getChildCount();
                for (int i = 0; i < count; i++) {
                    TagsLinearLayoutChild layout = (TagsLinearLayoutChild) ((TagsHorizontalScrollView) getChildAt(i)).getChildAt(0);
                    int index = layout.indexOfChild(view);
                    int childCount = layout.getChildCount();
                    if (index + 1 == childCount) {
                        status = true;
                        break;
                    }
                }
            } catch (Exception e) {
            }
        }
        Log.e("TagsLinearLayoutChild", "isLast => status = " + status);
        return status;
    }

    protected final void callback() {
        if (null == onTagsChangeListener)
            return;
        try {
            Map<String, String> map = getTags();
            onTagsChangeListener.onChange(map);
        } catch (Exception e) {
        }
    }

    /*************************/

    private OnTagsChangeListener onTagsChangeListener;

    public final void setOnTagsChangeListener(@NonNull OnTagsChangeListener listener) {
        this.onTagsChangeListener = listener;
    }

    /*************************/
}
