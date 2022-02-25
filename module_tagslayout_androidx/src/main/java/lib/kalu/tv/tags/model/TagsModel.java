package lib.kalu.tv.tags.model;


import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Keep;

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
