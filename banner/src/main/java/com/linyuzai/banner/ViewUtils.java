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
}
