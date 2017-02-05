package com.linyuzai.banner.creator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.linyuzai.banner.hint.HintViewCreator;

/**
 * Created by Administrator on 2017/2/4 0004.
 *
 * @author Linyuzai
 */

public abstract class ColorHintViewCreator implements HintViewCreator {
    @Override
    public View getHintView(ViewGroup parent) {
        int margin = getSpacing() / 2;
        View view;
        if (isRound()) {
            view = new RoundView(parent.getContext(), getViewWidth(), getViewHeight());
        } else {
            view = new View(parent.getContext());
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getViewWidth(), getViewHeight());
        params.leftMargin = margin;
        params.rightMargin = margin;
        view.setLayoutParams(params);
        return view;
    }

    @Override
    public void onHintActive(View hint) {
        if (isRound()) {
            if (hint instanceof RoundView)
                ((RoundView) hint).updateColor(getHintActiveColor());
        } else
            hint.setBackgroundColor(getHintActiveColor());
    }

    @Override
    public void onHintReset(View hint) {
        if (isRound()) {
            if (hint instanceof RoundView)
                ((RoundView) hint).updateColor(getHintResetColor());
        } else
            hint.setBackgroundColor(getHintResetColor());
    }

    @Override
    public int getMarginBottom() {
        return 0;
    }

    /**
     * @return HintView item之间的间隔
     */
    public int getSpacing() {
        return 0;
    }

    /**
     * @return 选中时的颜色
     */
    public abstract int getHintActiveColor();

    /**
     * @return 默认的颜色
     */
    public abstract int getHintResetColor();

    /**
     * @return 是否是圆的
     */
    public abstract boolean isRound();

    /**
     * @return HintView item高度，默认wrap content
     */
    public abstract int getViewHeight();

    /**
     * @return HintView item宽度，默认wrap content
     */
    public abstract int getViewWidth();

    /**
     * 圆形View
     */
    public static class RoundView extends View {
        private Paint paint = new Paint();
        private float cx;
        private float cy;
        private float radius;

        public RoundView(Context context, int width, int height) {
            super(context);
            cx = width * 0.5f;
            cy = height * 0.5f;
            radius = Math.min(cx, cy);
            init();
        }

        public RoundView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawCircle(cx, cy, radius, paint);
        }

        /**
         * 改变颜色
         *
         * @param color 需要变成的颜色
         */
        public void updateColor(int color) {
            paint.setColor(color);
            invalidate();
        }
    }
}
