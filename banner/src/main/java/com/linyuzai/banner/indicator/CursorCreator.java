package com.linyuzai.banner.indicator;

import android.graphics.Paint;

import com.linyuzai.banner.ViewLocation;

/**
 * Created by Administrator on 2017/2/4 0004.
 *
 * @author Linyuzai
 */

public interface CursorCreator {
    /**
     * @return 指示器高度
     */
    float getHeight();

    /**
     * @return 指示器颜色
     */
    int getColor();

    /**
     * @return 宽度缩放比例
     */
    float getScale();

    /**
     * 样式
     *
     * @return 画笔Cap
     */
    Paint.Cap getStyle();

    /**
     * 设置Cursor的位置
     *
     * @return ViewLocation
     */
    ViewLocation getViewLocation();
}
