package com.linyuzai.banner;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/2/5 0005.
 *
 * @author Linyuzai
 */

public class ViewUtils {

    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;

    /**
     * 测量View
     *
     * @param view 被测量的View
     */
    public static void measure(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
    }

    /**
     * 用于得到屏幕宽高
     *
     * @param context View或Activity或Fragment的context
     * @return DisplayMetrics
     */
    public static DisplayMetrics getMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(metric);
        return metric;
    }

    /**
     * new一个LinearLayout的LayoutParams
     *
     * @return LinearLayout.LayoutParams
     */
    public static LinearLayout.LayoutParams newLinearLayoutParams() {
        return new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
    }

    /**
     * new一个RelativeLayout的LayoutParams
     *
     * @return RelativeLayout.LayoutParams
     */
    public static RelativeLayout.LayoutParams newRelativeLayoutParams() {
        return new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
    }

    public static RelativeLayout.LayoutParams getVerticalRelativeLayoutParamsFromViewLocation(RelativeLayout.LayoutParams params,
                                                                                              ViewLocation location) {
        return getRelativeLayoutParamsFromViewLocation(params, location, false, true);
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutParamsFromViewLocation(RelativeLayout.LayoutParams params,
                                                                                      ViewLocation location) {
        return getRelativeLayoutParamsFromViewLocation(params, location, true, true);
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutParamsFromViewLocation(RelativeLayout.LayoutParams params,
                                                                                      ViewLocation location, boolean useHorizontal, boolean useVertical) {
        if (useHorizontal)
            switch (location.getHorizontalGravity()) {
                case CENTER:
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    break;
                case RIGHT:
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    params.rightMargin = location.getMarginRight();
                    break;
                case LEFT:
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    params.leftMargin = location.getMarginLeft();
                    break;
            }
        if (useVertical)
            switch (location.getVerticalGravity()) {
                case CENTER:
                    params.addRule(RelativeLayout.CENTER_VERTICAL);
                    break;
                case TOP:
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    params.topMargin = location.getMarginTop();
                    break;
                case BOTTOM:
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    params.bottomMargin = location.getMarginBottom();
                    break;
            }
        return params;
    }
}
