package com.linyuzai.banner;

import static com.linyuzai.banner.ViewLocation.HorizontalGravity.CENTER;
import static com.linyuzai.banner.ViewLocation.VerticalGravity.BOTTOM;

/**
 * Created by Administrator on 2017/2/14 0014.
 *
 * @author Linyuzai
 */
public class ViewLocation {
    public enum HorizontalGravity {
        CENTER, RIGHT, LEFT
    }

    public enum VerticalGravity {
        CENTER, TOP, BOTTOM
    }

    /*public static final int CENTER = Gravity.CENTER;
    public static final int RIGHT = Gravity.RIGHT;
    public static final int LEFT = Gravity.LEFT;
    public static final int TOP = Gravity.TOP;
    public static final int BOTTOM = Gravity.BOTTOM;*/

    private HorizontalGravity horizontalGravity = CENTER;
    private VerticalGravity verticalGravity = BOTTOM;

    private int marginTop;
    private int marginBottom;
    private int marginLeft;
    private int marginRight;

    private ViewLocation() {
    }

    public static ViewLocation getDefaultViewLocation() {
        return new ViewLocation();
    }

    public HorizontalGravity getHorizontalGravity() {
        return horizontalGravity;
    }

    public void setHorizontalGravity(HorizontalGravity horizontalGravity) {
        this.horizontalGravity = horizontalGravity;
    }

    public VerticalGravity getVerticalGravity() {
        return verticalGravity;
    }

    public void setVerticalGravity(VerticalGravity verticalGravity) {
        this.verticalGravity = verticalGravity;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }
}
