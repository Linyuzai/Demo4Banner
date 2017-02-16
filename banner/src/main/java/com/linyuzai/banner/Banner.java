package com.linyuzai.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.linyuzai.banner.indicator.Indicator;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * 支持循环播放，自动播放
 * <p>
 * Created by Administrator on 2017/1/5 0005.
 *
 * @author Linyuzai
 */

public class Banner extends ViewPager implements IBanner {
    private static final String TAG = "Banner";
    private static final boolean DEBUG = false;

    private static final int DEFAULT_DURATION = 250;
    public static final int NO_ADAPTER = -1;

    private Indicator mIndicator;

    private int mBannerInterval = 20 * DEFAULT_DURATION;//自动播放时的时间间隔
    private int mManualDuration = DEFAULT_DURATION;//手动滑动Banner时翻页时间
    private int mAutoDuration = 3 * DEFAULT_DURATION;//自动滚动时的翻页时间

    private int mCurrentPosition;//当前position，支持BannerAdapter2

    private boolean isStationary;//是否禁止滑动

    private boolean isLoop;//是否循环
    private boolean isManual;//当前是否是手动触摸
    private boolean isAutoScroll;//是否自动播放

    private BannerScroller mScroller;
    private BannerHandler mHandler;

    private OnBannerChangeListener mBannerChangeListener;
    private OnBannerItemClickListener mBannerItemClickListener;

    /**
     * 自动播放的消息循环
     * <p>
     * 官方提倡正确使用Handler的姿势
     */
    private static class BannerHandler extends Handler {
        private final WeakReference<Banner> mBanner;

        private BannerHandler(Banner banner) {
            this.mBanner = new WeakReference<>(banner);
        }

        @Override
        public void handleMessage(Message msg) {
            Banner banner = mBanner.get();
            if (banner != null && !banner.isManual && banner.isAutoScroll) {
                banner.autoScroll(banner.getCurrentItem() + 1);
            }
        }
    }

    /**
     * 用来替换ViewPager中的Scroller
     */
    private class BannerScroller extends Scroller {

        private int duration;//可以随时更改翻页的时间

        private BannerScroller(Context context, int duration) {
            super(context);
            this.duration = duration;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, duration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, this.duration);
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }

    /**
     * 实现OnPageChangeListener
     */
    private class OnPageChangeListenerImpl implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int modifyPosition = getModifyPosition(position);
            if (DEBUG)
                Log.d(TAG, "onPageScrolled:" + position + ",positionOffset:" + positionOffset
                        + ",positionOffsetPixels:" + positionOffsetPixels + ",modifyPosition:" + modifyPosition);
            if (mBannerChangeListener != null && !isIgnorePosition(position))
                mBannerChangeListener.onBannerScrolled(modifyPosition, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            int modifyPosition = getModifyPosition(position);
            if (DEBUG)
                Log.d(TAG, "onPageSelected:" + position + ",modifyPosition:" + modifyPosition);
            mCurrentPosition = position;
            if (isLoop && isBannerAdapter()) {
                if (position == 0)
                    resetPosition(0);
                if (position == Integer.MAX_VALUE - 1)
                    resetPosition(modifyPosition);
            }
            //如果绑定的indicator进行联动
            boolean isIgnore = isIgnorePosition(position);
            if (mIndicator != null && !isIgnore)
                mIndicator.setCurrentIndicator(modifyPosition);
            if (mBannerChangeListener != null && !isIgnore)
                mBannerChangeListener.onBannerSelected(modifyPosition);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (DEBUG)
                Log.d(TAG, "onPageScrollStateChanged:" + state);
            //完成翻页时，发送自动翻页消息
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (isBannerAdapter2()) {
                    if (mCurrentPosition == 0)
                        setCurrentItem(((BannerAdapter2) getAdapter()).getBannerCount(), false);
                    else if (mCurrentPosition == ((BannerAdapter2) getAdapter()).getBannerCount() + 1)
                        setCurrentItem(1, false);
                }
                sendToScroll();
            }
            if (mBannerChangeListener != null)
                mBannerChangeListener.onBannerScrollStateChanged(state);
        }
    }

    public Banner(Context context) {
        super(context);
        initBanner(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBanner(context, attrs);
    }

    private void initBanner(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Banner);
            mBannerInterval = a.getInt(R.styleable.Banner_banner_interval, mBannerInterval);
            mManualDuration = a.getInt(R.styleable.Banner_manual_duration, mManualDuration);
            mAutoDuration = a.getInt(R.styleable.Banner_auto_duration, mAutoDuration);
            isStationary = a.getBoolean(R.styleable.Banner_stationary, isStationary);
            a.recycle();
        }
        addOnPageChangeListener(new OnPageChangeListenerImpl());
        mScroller = new BannerScroller(context, mAutoDuration);
        mHandler = new BannerHandler(this);
        replaceScroller();
    }

    /**
     * 替换ViewPager的Scroller
     */
    private void replaceScroller() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(this, this.mScroller);
        } catch (Exception e) {
            Log.w(TAG, "replaceScroller is failed", e);
        }
    }

    private void sendToScroll() {
        mHandler.removeMessages(0);//移除所有消息
        isManual = false;//非手动
        //发送间隔mBannerInterval的翻页命令
        if (isAutoScroll)
            startAutoScroll(mBannerInterval);
    }

    /**
     * 指定当前的position不会触发OnPageChangeListener
     *
     * @param index position
     */
    private void setCurrentPosition(int index) {
        if (DEBUG)
            Log.d(TAG, "setCurrentPosition---->index:" + index);
        try {
            Field field = ViewPager.class.getDeclaredField("mCurItem");
            field.setAccessible(true);
            field.set(this, index);
        } catch (Exception e) {
            Log.w(TAG, "setCurrentPosition is failed", e);
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
        if (!smoothScroll) {
            sendToScroll();
        }
    }

    @Override
    public void updateBannerAfterDataSetChanged() {
        if (isBannerAdapter2())
            setCurrentItem(1, false);
    }

    /**
     * 得到相对应的position
     *
     * @param position 实际的position
     * @return 相对应的position
     */
    private int getModifyPosition(int position) {
        int modifyPosition = position;
        //如果可循环并且Adapter为BannerAdapter，计算相对应的position
        if (isLoop) {
            if (getAdapter() instanceof BannerAdapter)
                modifyPosition = position % ((BannerAdapter) getAdapter()).getBannerCount();
            if (getAdapter() instanceof BannerAdapter2) {
                if (position == 0)
                    modifyPosition = ((BannerAdapter2) getAdapter()).getBannerCount();
                else if (position == ((BannerAdapter2) getAdapter()).getBannerCount() + 1)
                    modifyPosition = 1;
                modifyPosition--;
            }
        }
        return modifyPosition;
    }

    private boolean isIgnorePosition(int position) {
        return isBannerAdapter2() && (position == 0 || position == getAdapter().getCount() - 1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //如果高度包含内容，测量每个child的高度选择最大的作为Banner高度
        if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            int height = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0,
                        MeasureSpec.UNSPECIFIED);
                child.measure(widthMeasureSpec, newHeightMeasureSpec);
                int h = child.getMeasuredHeight();
                if (h > height)
                    height = h;
            }
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                InterceptAutoScroll();
                if (DEBUG)
                    Log.d(TAG, "onInterceptTouchEvent:ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                sendToScroll();
                if (DEBUG)
                    Log.d(TAG, "onInterceptTouchEvent:ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                sendToScroll();
                if (DEBUG)
                    Log.d(TAG, "onInterceptTouchEvent:ACTION_CANCEL");
                break;
        }
        /*//打断自动播放
        if (!isStationary && ev.getAction() == MotionEvent.ACTION_DOWN) {
            isManual = true;//手动
            mScroller.setDuration(mManualDuration);//改变翻页的时间
        }*/
        return !isStationary && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !isStationary && super.onTouchEvent(ev);
    }

    /**
     * 自动翻页
     *
     * @param nextPosition 翻页的position
     */
    protected void autoScroll(int nextPosition) {
        mScroller.setDuration(mAutoDuration);//设置自动翻页的时间
        /*int modifyPosition = banner.mCurrentPosition % banner.mBannerAdapter.getBannerCount();
        int jumpPosition = modifyPosition + Integer.MAX_VALUE / 2 - banner.mOffsetPosition;
        if (DEBUG)
            Log.d(TAG, "currentPosition:" + banner.mCurrentPosition + ",modifyPosition:" + modifyPosition
                    + ",jumpPosition:" + jumpPosition);
        if (jumpPosition != banner.mCurrentPosition)
            banner.setCurrentItem(jumpPosition, false);*/
        setCurrentItem(nextPosition);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        stopAutoScroll();
        super.setAdapter(adapter);
        initLoopIfNeed();
        setOnBannerItemClickListener(mBannerItemClickListener);
    }

    /**
     * @return 是否禁止滑动
     */
    public boolean isStationary() {
        return isStationary;
    }

    /**
     * 设置禁止滑动
     *
     * @param stationary 是否禁止滑动
     */
    public void setStationary(boolean stationary) {
        isStationary = stationary;
    }

    @Override
    public void startAutoScroll() {
        startAutoScroll(0);
    }

    @Override
    public void startAutoScroll(long delay) {
        if (isStationary)
            return;
        isAutoScroll = true;
        mHandler.sendEmptyMessageDelayed(0, delay);
    }

    @Override
    public void stopAutoScroll() {
        isAutoScroll = false;
        mHandler.removeMessages(0);
    }

    @Override
    public void bindIndicator(Indicator indicator) {
        mIndicator = indicator;
        //不支持可循环的Banner
        if (isLoop)
            throw new RuntimeException("Indicator is not support loop banner");
        int bannerCount = getCompatibleCount();
        int indicatorCount = mIndicator.getCount();
        if (bannerCount == Banner.NO_ADAPTER || indicatorCount == Banner.NO_ADAPTER)
            throw new RuntimeException("Banner and Indicator need setAdapter before bindIndicator");
        if (indicatorCount != bannerCount)
            throw new RuntimeException("The count of banner is "
                    + bannerCount + " and the count of indicator is "
                    + indicatorCount + ",which is different");
    }

    @Override
    public int getCompatibleCount() {
        PagerAdapter adapter = getAdapter();
        if (adapter == null)
            return NO_ADAPTER;
        if (adapter instanceof BannerAdapter)
            return ((BannerAdapter) adapter).getBannerCount();
        else if (adapter instanceof BannerAdapter2)
            return ((BannerAdapter2) adapter).getBannerCount();
        else
            return adapter.getCount();
    }

    @Override
    public int getBannerInterval() {
        return mBannerInterval;
    }

    @Override
    public void setBannerInterval(int interval) {
        this.mBannerInterval = interval;
    }

    private void initLoopIfNeed() {
        if (getAdapter() instanceof BannerAdapter) {
            if (isLoop = ((BannerAdapter) getAdapter()).isLoop()) {
                int mOffsetPosition = Integer.MAX_VALUE / 2 % ((BannerAdapter) getAdapter()).getBannerCount();
                setCurrentPosition(Integer.MAX_VALUE / 2 - mOffsetPosition);
            }
        } else if (getAdapter() instanceof BannerAdapter2) {
            if (isLoop = ((BannerAdapter2) getAdapter()).isLoop()) {
                setCurrentItem(1, false);
            }
        }
    }

    private void InterceptAutoScroll() {
        isManual = true;//手动
        mScroller.setDuration(mManualDuration);//改变翻页的时间
    }

    /**
     * 重定位循环位置
     */
    @Deprecated
    private void resetPosition(int position) {
        if (isLoop) {
            int mOffsetPosition = Integer.MAX_VALUE / 2 % ((BannerAdapter) getAdapter()).getBannerCount();
            setCurrentPosition(Integer.MAX_VALUE / 2 - mOffsetPosition + position);
            setCurrentItem(Integer.MAX_VALUE / 2 - mOffsetPosition + position, false);
            sendToScroll();
        }
    }

    @Override
    public int getManualDuration() {
        return mManualDuration;
    }

    @Override
    public void setManualDuration(int duration) {
        this.mManualDuration = duration;
    }

    @Override
    public int getAutoDuration() {
        return mAutoDuration;
    }

    @Override
    public void setAutoDuration(int duration) {
        this.mAutoDuration = duration;
        mScroller.setDuration(duration);
    }

    @Override
    public void setOnBannerItemClickListener(OnBannerItemClickListener onBannerItemClickListener) {
        mBannerItemClickListener = onBannerItemClickListener;
        if (isBannerAdapter())
            ((BannerAdapter) getAdapter()).mBannerItemClickListener = mBannerItemClickListener;
        else if (isBannerAdapter2())
            ((BannerAdapter2) getAdapter()).mBannerItemClickListener = mBannerItemClickListener;
    }

    @Override
    public OnBannerItemClickListener getOnBannerItemClickListener() {
        return mBannerItemClickListener;
    }

    /**
     * 得到OnBannerChangeListener
     *
     * @return BannerChangeListener
     */
    public OnBannerChangeListener getOnBannerChangeListener() {
        return mBannerChangeListener;
    }

    /**
     * 设置OnBannerChangeListener
     *
     * @param listener BannerChangeListener
     */
    public void setOnBannerChangeListener(OnBannerChangeListener listener) {
        this.mBannerChangeListener = listener;
    }

    private boolean isBannerAdapter() {
        return getAdapter() != null && getAdapter() instanceof BannerAdapter;
    }

    private boolean isBannerAdapter2() {
        return getAdapter() != null && getAdapter() instanceof BannerAdapter2;
    }
}
