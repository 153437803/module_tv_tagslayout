package lib.kalu.tv.tags.model;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Keep;

@Keep
public interface TagsModel {

    int initId();

    String initText();

    @ColorInt
    int initTextColorFocus();

    @ColorInt
    int initTextColorSelect();

    @ColorInt
    int initTextColorDetault();

    @DrawableRes
    int initBackgroundResourceFocus();

    @DrawableRes
    int initBackgroundResourceSelect();

    @DrawableRes
    int initBackgroundResourceDefault();
}
