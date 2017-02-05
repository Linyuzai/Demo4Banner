package com.linyuzai.banner.indicator;

import android.graphics.Paint;

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
     * 位置
     *
     * @return Gravity.TOP或者Gravity.BOTTOM
     */
    int getGravity();

    /**
     * @return 距离TOP的距离
     */
    int getMarginTop();

    /**
     * @return 距离BOTTOM的距离
     */
    int getMarginBottom();
}
