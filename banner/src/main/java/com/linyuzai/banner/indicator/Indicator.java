package com.linyuzai.banner.indicator;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.linyuzai.banner.Banner;
import com.linyuzai.banner.BannerAdapter;
import com.linyuzai.banner.R;
import com.linyuzai.banner.ViewHolder;
import com.linyuzai.banner.ViewUtils;

/**
 * Created by Administrator on 2017/2/4 0004.
 *
 * @author Linyuzai
 */

public class Indicator extends HorizontalScrollView {
    private static final String TAG = "Indicator";
    private static final boolean DEBUG = false;

    private RelativeLayout mGroup;//HorizontalScrollView的直接子View
    private CursorView mCursor;//可滑动的指示器
    private LinearLayout mIndicatorGroup;//导航栏布局
    private int mCurrentPosition;//当前位置
    private int mIndicatorWidth;//导航栏宽度
    private int mIndicatorHeight;//导航栏高度

    private float mCursorWidth;//指示器宽度
    private IndicatorAttributes[] mIndicators;//导航栏属性，用来更新item位置
    private IndicatorAdapter mAdapter;

    private Banner mBanner;
    private boolean isBannerAnim = true;//Banner切换动画，需要绑定Banner
    private boolean isIndicatorAnim = true;//导航栏item切换动画
    private boolean isCursorAnim = true;//指示器切换动画，需要设置指示器

    private OnIndicatorChangeCallback mOnIndicatorChangeCallback;
    private OnIndicatorClickListener mOnIndicatorClickListener;

    public Indicator(Context context) {
        super(context);
        init(context, null);
    }

    public Indicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Indicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Indicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Indicator);
            isBannerAnim = a.getBoolean(R.styleable.Indicator_banner_anim, isBannerAnim);
            isIndicatorAnim = a.getBoolean(R.styleable.Indicator_indicator_anim, isIndicatorAnim);
            isCursorAnim = a.getBoolean(R.styleable.Indicator_cursor_anim, isCursorAnim);
            a.recycle();
        }
        setHorizontalScrollBarEnabled(false);
        //导航栏布局
        mIndicatorGroup = new LinearLayout(context);
        mIndicatorGroup.setOrientation(LinearLayout.HORIZONTAL);
        mIndicatorGroup.setGravity(Gravity.CENTER_VERTICAL);

        mGroup = new RelativeLayout(context);
        mGroup.addView(mIndicatorGroup);

        addView(mGroup);
    }

    /**
     * 设置adapter
     *
     * @param adapter 需要设置的adapter
     */
    @SuppressWarnings("unchecked")
    public void setAdapter(IndicatorAdapter adapter) {
        if (mAdapter == adapter)
            return;
        //boolean isAdapterNull = (mAdapter == null);
        mAdapter = adapter;
        mIndicators = new IndicatorAttributes[adapter.getIndicatorCount()];
        LinearLayout.LayoutParams params = ViewUtils.newLinearLayoutParams();
        int childWidth = ViewUtils.WRAP_CONTENT;
        //如果宽度适配屏幕，即设置导航栏宽度等于屏幕宽度，不可滚动
        if (adapter.isFitScreenWidth()) {
            //获得屏幕宽高DisplayMetrics
            DisplayMetrics metric = ViewUtils.getMetrics(getContext());
            //ViewGroup.LayoutParams params1 = getLayoutParams();
            //ViewGroup.LayoutParams params2 = mGroup.getLayoutParams();
            ViewGroup.LayoutParams params3 = mIndicatorGroup.getLayoutParams();
            //params1.width = ViewGroup.LayoutParams.MATCH_PARENT;
            //params2.width = ViewGroup.LayoutParams.MATCH_PARENT;
            //params3.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params3.width = metric.widthPixels;//导航栏宽度等于屏幕宽度
            //setLayoutParams(params1);
            //mGroup.setLayoutParams(params2);
            mIndicatorGroup.setLayoutParams(params3);
            //setFillViewport(true);
            childWidth = metric.widthPixels / adapter.getIndicatorCount();//计算导航栏item宽度
        }
        mIndicatorGroup.removeAllViews();
        for (int position = 0; position < adapter.getIndicatorCount(); position++) {
            ViewHolder holder = adapter.onCreateIndicatorViewHolder(mIndicatorGroup);
            adapter.onBindIndicatorViewHolder(holder, position);
            View child = holder.itemView;
            if (position == 0) {
                /*int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                child.measure(width, height);*/
                if (child.getLayoutParams() == null)
                    child.setLayoutParams(params);
                ViewGroup.LayoutParams childLayoutParams = child.getLayoutParams();
                ViewUtils.measure(child);
                mIndicatorHeight = child.getMeasuredHeight();//获得测量高度
                mCursorWidth = child.getMeasuredWidth();//获得测量宽度
                if (DEBUG)
                    Log.d(TAG, "mCursorWidth:" + mCursorWidth + ",mIndicatorHeight:" + mIndicatorHeight);
                if (DEBUG)
                    Log.d(TAG, "childLayoutParams.width:" + childLayoutParams.width
                            + ",childLayoutParams.height:" + childLayoutParams.height);
                if (childLayoutParams.width > 0)
                    mCursorWidth = childLayoutParams.width;//如果自定义了具体宽度，则按照具体宽度
                if (childLayoutParams.height > 0) {
                    mIndicatorHeight = childLayoutParams.height;//如果自定义了具体高度，则按照具体高度
                    ViewGroup.LayoutParams indicatorParams = getLayoutParams();
                    indicatorParams.height = mIndicatorHeight;
                    setLayoutParams(indicatorParams);
                } else if (childLayoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    //如果是高度为WRAP_CONTENT，则设置为测量高度
                    ViewGroup.LayoutParams indicatorParams = getLayoutParams();
                    indicatorParams.height = mIndicatorHeight;
                    setLayoutParams(indicatorParams);
                }
                if (DEBUG)
                    Log.d(TAG, "mCursorWidth:" + mCursorWidth + ",mIndicatorHeight" + mIndicatorHeight);
            }
            //如果适配屏幕宽度，设置导航栏item的宽度
            if (adapter.isFitScreenWidth()) {
                LinearLayout.LayoutParams widthParams = (LinearLayout.LayoutParams) child.getLayoutParams();
                //weightParams.width = 0;
                //weightParams.weight = 1;
                widthParams.width = childWidth;
                if (DEBUG)
                    Log.d(TAG, "widthParams.width:" + widthParams.width);
                child.setLayoutParams(widthParams);
                mCursorWidth = childWidth;//更新指示器宽度
            }
            child.setOnClickListener(new OnClickListenerImpl(position));
            mIndicatorGroup.addView(child);
            mIndicators[position] = new IndicatorAttributes();
        }
    }

    @Deprecated
    private void resetWidth(View view, int width) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            ViewGroup.LayoutParams params = view.getLayoutParams();
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                resetWidth(child, width);
            }
            if (params != null) {
                params.width = width;
                view.setLayoutParams(params);
            }
        }
    }

    /**
     * 获取adapter
     *
     * @return 当前的adapter
     */
    public IndicatorAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * 设置指示器
     *
     * @param creator 指示器的创建接口
     */
    public void setCursor(CursorCreator creator) {
        //去掉指示器
        if (creator == null) {
            if (mCursor != null) {
                mGroup.removeView(mCursor);
                mCursor = null;
            }
            return;
        }
        if (DEBUG)
            Log.d(TAG, "mCursor:" + mCursor);
        //添加或更新指示器
        if (mCursor == null) {
            mCursor = new CursorView(getContext());
            setupCursor(creator);
            mGroup.addView(mCursor);
        } else {
            setupCursor(creator);
            mCursor.invalidate();
        }
    }

    /**
     * 设置指示器参数
     *
     * @param creator 指示器参数接口
     */
    private void setupCursor(CursorCreator creator) {
        mCursor.width = mCursorWidth;
        mCursor.height = creator.getHeight();
        mCursor.color = creator.getColor();
        mCursor.scale = creator.getScale();
        mCursor.style = creator.getStyle();
        mCursor.setupPaint();
        /*FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);*/
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mCursor.getLayoutParams();
        if (params == null)
            params = new RelativeLayout.LayoutParams((int) mCursor.width, (int) mCursor.height);
        if (DEBUG)
            Log.d(TAG, "cursor.width:" + mCursor.width + ",cursor.height:" + mCursor.height);
        params.topMargin = creator.getMarginTop();
        params.bottomMargin = creator.getMarginBottom();
        //设置为底部
        if (creator.getGravity() == Gravity.BOTTOM) {
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            //params.topMargin = (int) (mIndicatorHeight - params.bottomMargin - mCursor.height);
            //params.bottomMargin = 0;
            if (DEBUG)
                Log.d(TAG, "getHeight:" + getHeight());
        }
        mCursor.setLayoutParams(params);
    }

    /**
     * 绑定Banner'
     *
     * @param banner 需要绑定的Banner
     */
    public void bindBanner(Banner banner) {
        mBanner = banner;
        int bannerCount = mBanner.getCompatibleCount();
        int indicatorCount = getCount();
        if (bannerCount == Banner.NO_ADAPTER || indicatorCount == Banner.NO_ADAPTER)
            throw new RuntimeException("Banner and Indicator need setAdapter before bindBanner");
        if (indicatorCount != bannerCount)
            throw new RuntimeException("The count of banner is "
                    + bannerCount + " and the count of indicator is "
                    + indicatorCount + ",which is different");
        if (banner.getAdapter() instanceof BannerAdapter && ((BannerAdapter) banner.getAdapter()).isLoop())
            throw new RuntimeException("Indicator is not support loop banner");
    }

    /**
     * 获得导航栏item个数
     *
     * @return 导航栏个数，未设置adapter返回Banner.NO_ADAPTER
     */
    public int getCount() {
        if (mAdapter == null)
            return Banner.NO_ADAPTER;
        return mAdapter.getIndicatorCount();
    }

    /**
     * Banner切换是否动画
     *
     * @return 是否动画
     */
    public boolean isBannerAnim() {
        return isBannerAnim;
    }

    /**
     * 设置Banner切换是否动画，需要bindBanner()后生效
     *
     * @param bannerAnim 是否动画
     */
    public void setBannerAnim(boolean bannerAnim) {
        isBannerAnim = bannerAnim;
    }

    /**
     * 导航栏切换是否动画
     *
     * @return 是否动画
     */
    public boolean isIndicatorAnim() {
        return isIndicatorAnim;
    }

    /**
     * 设置导航栏切换是否动画
     *
     * @param indicatorAnim 是否动画
     */
    public void setIndicatorAnim(boolean indicatorAnim) {
        isIndicatorAnim = indicatorAnim;
    }

    /**
     * 指示器切换是否动画
     *
     * @return 是否动画
     */
    public boolean isCursorAnim() {
        return isCursorAnim;
    }

    /**
     * 指示器切换是否动画，setCursor()后生效
     *
     * @param cursorAnim 是否动画
     */
    public void setCursorAnim(boolean cursorAnim) {
        isCursorAnim = cursorAnim;
    }

    /**
     * 设置导航栏的position
     *
     * @param position 需要切换的position
     */
    public void setCurrentIndicator(int position) {
        if (mOnIndicatorChangeCallback != null) {
            //返回false拦截
            if (mOnIndicatorChangeCallback.interceptBeforeChange(position))
                return;
            //将上一个位置的view还原
            restoreIndicator(mCurrentPosition);
        }
        //如果设置了指示器，更新指示器位置
        if (mCursor != null) {
            //动画
            if (isCursorAnim) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(mCursor, "translationX",
                        mIndicators[mCurrentPosition].left, mIndicators[position].left);
                animator.setDuration(200).start();
            } else
                mCursor.setTranslationX(mIndicators[position].left);
        }
        //动画改变导航栏item位置或直接切换
        if (isIndicatorAnim)
            smoothScrollTo(mIndicators[position].left - (mIndicatorWidth - mIndicators[position].width) / 2, 0);
        else
            scrollTo(mIndicators[position].left - (mIndicatorWidth - mIndicators[position].width) / 2, 0);
        mCurrentPosition = position;//记录当前position
        //设置当前position的view为选中状态
        changeIndicator(position);
    }

    /**
     * 获得IndicatorChangeCallback
     *
     * @return IndicatorChangeCallback
     */
    public OnIndicatorChangeCallback getOnIndicatorChangeCallback() {
        return mOnIndicatorChangeCallback;
    }

    /**
     * 设置IndicatorChangeCallback
     *
     * @param onIndicatorChangeCallback 需要设置的IndicatorChangeCallback
     */
    public void setOnIndicatorChangeCallback(OnIndicatorChangeCallback onIndicatorChangeCallback) {
        mOnIndicatorChangeCallback = onIndicatorChangeCallback;
        changeIndicator(0);
    }

    /**
     * 更新导航栏
     *
     * @param position 更新的位置
     */
    @SuppressWarnings("unchecked")
    public void changeIndicator(int position) {
        if (mOnIndicatorChangeCallback != null)
            mOnIndicatorChangeCallback.onIndicatorChange((ViewHolder) mIndicatorGroup.getChildAt(position).getTag());
    }

    /**
     * 还原导航栏
     *
     * @param position 还原的位置
     */
    @SuppressWarnings("unchecked")
    public void restoreIndicator(int position) {
        if (mOnIndicatorChangeCallback != null)
            mOnIndicatorChangeCallback.onIndicatorRestore((ViewHolder) mIndicatorGroup.getChildAt(position).getTag());
    }

    /**
     * 获得IndicatorClickListener
     *
     * @return IndicatorClickListener
     */
    public OnIndicatorClickListener getOnIndicatorClickListener() {
        return mOnIndicatorClickListener;
    }

    /**
     * 设置IndicatorClickListener
     *
     * @param onIndicatorClickListener 需要设置的IndicatorClickListener
     */
    public void setOnIndicatorClickListener(OnIndicatorClickListener onIndicatorClickListener) {
        mOnIndicatorClickListener = onIndicatorClickListener;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //记录所有导航栏item的位置和宽度
        for (int i = 0; i < mIndicatorGroup.getChildCount(); i++) {
            View child = mIndicatorGroup.getChildAt(i);
            mIndicators[i].left = child.getLeft();
            mIndicators[i].width = child.getWidth();
            if (DEBUG)
                Log.d(TAG, i + "-->left:" + child.getLeft() + ",width:" + child.getWidth());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mIndicatorWidth = w;
        if (DEBUG)
            Log.d(TAG, "mIndicatorWidth:" + mIndicatorWidth);
    }

    /**
     * 保存导航栏的属性
     */
    class IndicatorAttributes {
        int left;//起始位置
        int width;//宽度
        //ObjectAnimator animator;
    }

    class OnClickListenerImpl implements OnClickListener {
        private int position;

        OnClickListenerImpl(int position) {
            this.position = position;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onClick(View v) {
            //Indicator.this.setCurrentIndicator(position);会调用mBanner.setCurrentItem(position, isBannerAnim);
            if (mBanner != null)
                mBanner.setCurrentItem(position, isBannerAnim);
            else
                Indicator.this.setCurrentIndicator(position);
            if (mOnIndicatorClickListener != null)
                mOnIndicatorClickListener.onIndicatorClick((ViewHolder) v.getTag(), position);
        }
    }

    /**
     * 自定义指示器View
     */
    class CursorView extends View {
        Paint paint = new Paint();
        float scale = 1.0f;
        float width;
        float height;
        int color;
        Paint.Cap style;

        public CursorView(Context context) {
            super(context);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
        }

        void setupPaint() {
            paint.setColor(color);
            paint.setStrokeCap(style);
            paint.setStrokeWidth(height);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            float radius = paint.getStrokeWidth() * 0.5f;
            float startX = width * (1.0f - scale) * 0.5f + radius;
            float stopX = width - startX;
            float startY = radius;
            float stopY = radius;
            if (DEBUG)
                Log.d(TAG, "startX:" + startX + ",startY:" + startY + ",stopX:" + stopX + ",stopY:" + stopY);
            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }
    }
}
