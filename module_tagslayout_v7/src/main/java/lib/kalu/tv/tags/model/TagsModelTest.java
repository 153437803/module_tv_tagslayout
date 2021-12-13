package lib.kalu.tv.tags.model;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Keep;

import lib.kalu.tv.tags.R;

@Keep
public abstract class TagsModelTest implements TagsModel {

    @Override
    public int initTextColorFocus() {
        return Color.RED;
    }

    @Override
    public int initTextColorSelect() {
        return Color.BLACK;
    }

    @Override
    public int initTextColorDetault() {
        return Color.GRAY;
    }

    @Override
    public int initBackgroundResourceFocus() {
        return R.drawable.module_tagslayout_ic_shape_background_focus;
    }

    @Override
    public int initBackgroundResourceSelect() {
        return R.drawable.module_tagslayout_ic_shape_background_select;
    }

    @Override
    public int initBackgroundResourceDefault() {
        return R.drawable.module_tagslayout_ic_shape_background_default;
    }
}
