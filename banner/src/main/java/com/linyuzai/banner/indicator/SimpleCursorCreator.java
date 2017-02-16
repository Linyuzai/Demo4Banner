package com.linyuzai.banner.indicator;

import android.graphics.Paint;
import android.view.Gravity;

import com.linyuzai.banner.ViewLocation;

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
    public ViewLocation getViewLocation() {
        return ViewLocation.getDefaultViewLocation();
    }
}
