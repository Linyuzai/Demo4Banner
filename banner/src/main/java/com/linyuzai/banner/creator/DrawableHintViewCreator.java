package com.linyuzai.banner.creator;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.linyuzai.banner.ViewLocation;
import com.linyuzai.banner.hint.HintViewCreator;

/**
 * Created by Administrator on 2017/2/4 0004.
 *
 * @author Linyuzai
 */

public abstract class DrawableHintViewCreator implements HintViewCreator {
    private Drawable mActiveDrawable;//选中时的drawable
    private Drawable mResetDrawable;//默认的drawable

    @Override
    public View getHintView(ViewGroup parent) {
        ImageView image = new ImageView(parent.getContext());
        int margin = getSpacing() / 2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getDrawableWidth(), getDrawableHeight());
        params.leftMargin = margin;
        params.rightMargin = margin;
        image.setLayoutParams(params);
        image.setScaleType(getImageScaleType());
        return image;
    }

    @Override
    public void onHintActive(View hint) {
        ImageView image = (ImageView) hint;
        if (mActiveDrawable == null)
            mActiveDrawable = getHintActiveDrawable();
        image.setImageDrawable(mActiveDrawable);
    }

    @Override
    public void onHintReset(View hint) {
        ImageView image = (ImageView) hint;
        if (mResetDrawable == null)
            mResetDrawable = getHintResetDrawable();
        image.setImageDrawable(mResetDrawable);
    }

    @Override
    public ViewLocation getViewLocation() {
        return ViewLocation.getDefaultViewLocation();
    }

    /**
     * @return 选中时的Drawable
     */
    public abstract Drawable getHintActiveDrawable();

    /**
     * @return 默认的Drawable
     */
    public abstract Drawable getHintResetDrawable();

    /**
     * @return HintView item之间的间隔
     */
    public int getSpacing() {
        return 0;
    }

    /**
     * @return HintView item高度，默认wrap content
     */
    public int getDrawableHeight() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    /**
     * @return HintView item宽度，默认wrap content
     */
    public int getDrawableWidth() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    /**
     * @return 显示的ScaleType
     */
    public ImageView.ScaleType getImageScaleType() {
        return ImageView.ScaleType.CENTER_INSIDE;
    }
}
