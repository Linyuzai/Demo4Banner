package com.linyuzai.banner.indicator;

import android.graphics.Paint;
import android.view.Gravity;

/**
 * Created by Administrator on 2017/2/4 0004.
 *
 * @author Linyuzai
 */

public abstract class SimpleCursorCreator implements CursorCreator {

    @Override
    public float getScale() {
        return 1.0f;
    }

    @Override
    public Paint.Cap getStyle() {
        return Paint.Cap.SQUARE;
    }

    @Override
    public int getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public int getMarginTop() {
        return 0;
    }

    @Override
    public int getMarginBottom() {
        return 0;
    }
}
