package com.linyuzai.banner.hint;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.linyuzai.banner.Banner;
import com.linyuzai.banner.BannerAdapter;
import com.linyuzai.banner.OnBannerChangeListener;
import com.linyuzai.banner.R;
import com.linyuzai.banner.ViewUtils;
import com.linyuzai.banner.indicator.Indicator;

/**
 * Created by Administrator on 2017/1/11 0011.
 *
 * @author Linyuzai
 */

public class HintBanner extends RelativeLayout implements IHintBanner {
    private static final String TAG = "HintBanner";
    private static final boolean DEBUG = false;

    private LinearLayout mHintGroup;
    private HintViewCreator mCreator;
    private Banner mBanner;
    private boolean isHintEnable;

    private int mCurrentPosition;

    private OnHintBannerChangeListener mHintBannerChangeListener;

    public HintBanner(Context context) {
        super(context);
        init(context, null);
    }

    public HintBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HintBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HintBanner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mBanner = new Banner(context);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HintBanner);
            mBanner.setBannerInterval(a.getInt(R.styleable.HintBanner_hint_banner_interval, mBanner.getBannerInterval()));
            mBanner.setManualDuration(a.getInt(R.styleable.HintBanner_hint_manual_duration, mBanner.getManualDuration()));
            mBanner.setAutoDuration(a.getInt(R.styleable.HintBanner_hint_auto_duration, mBanner.getAutoDuration()));
            a.recycle();
        }
        mBanner.setId(R.id.banner_id);
        addView(mBanner);
        mBanner.setOnBannerChangeListener(new OnBannerChangeListenerImpl());
    }

    private void addHintView() {
        mHintGroup = new LinearLayout(getContext());
        mHintGroup.setOrientation(LinearLayout.HORIZONTAL);
        int count = getCompatibleCount();
        for (int position = 0; position < count; position++) {
            View hint = mCreator.getHintView(mHintGroup);
            mHintGroup.addView(hint);
            if (position == 0)
                mCreator.onHintActive(hint);
            else
                mCreator.onHintReset(hint);
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null && layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            ViewUtils.measure(this);
            layoutParams.height = getMeasuredHeight();
            setLayoutParams(layoutParams);
        }
        LayoutParams params = ViewUtils.newRelativeLayoutParams();
        params.bottomMargin = mCreator.getMarginBottom();
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mHintGroup.setLayoutParams(params);
        addView(mHintGroup);
    }

    @Override
    public void setHintView(HintViewCreator creator) {
        mCreator = creator;
        if (creator == null) {
            isHintEnable = false;
            if (mHintGroup != null) {
                removeView(mHintGroup);
                mHintGroup = null;
            }
            return;
        }
        if (isHintEnable) {
            removeView(mHintGroup);
            addHintView();
        } else {
            isHintEnable = true;
            if (getCompatibleCount() != Banner.NO_ADAPTER) {
                addHintView();
            }
        }
    }

    @Override
    public Banner getBanner() {
        return mBanner;
    }

    @Override
    public void startAutoScroll() {
        mBanner.startAutoScroll();
    }

    @Override
    public void startAutoScroll(long delay) {
        mBanner.startAutoScroll(delay);
    }

    @Override
    public void stopAutoScroll() {
        mBanner.stopAutoScroll();
    }

    @Override
    public void bindIndicator(Indicator indicator) {
        mBanner.bindIndicator(indicator);
    }

    @Override
    public int getCompatibleCount() {
        return mBanner.getCompatibleCount();
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        mBanner.setAdapter(adapter);
        if (isHintEnable && mHintGroup == null)
            addHintView();
    }

    @Override
    public PagerAdapter getAdapter() {
        return mBanner.getAdapter();
    }

    @Override
    public int getBannerInterval() {
        return mBanner.getBannerInterval();
    }

    @Override
    public void setBannerInterval(int interval) {
        mBanner.setBannerInterval(interval);
    }

    @Override
    public int getManualDuration() {
        return mBanner.getManualDuration();
    }

    @Override
    public void setManualDuration(int duration) {
        mBanner.setManualDuration(duration);
    }

    @Override
    public int getAutoDuration() {
        return mBanner.getAutoDuration();
    }

    @Override
    public void setAutoDuration(int duration) {
        mBanner.setAutoDuration(duration);
    }

    @Override
    public OnHintBannerChangeListener getOnHintBannerChangeListener() {
        return mHintBannerChangeListener;
    }

    @Override
    public void setOnHintBannerChangeListener(OnHintBannerChangeListener listener) {
        this.mHintBannerChangeListener = listener;
    }

    private class OnBannerChangeListenerImpl implements OnBannerChangeListener {

        @Override
        public void onBannerScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mHintBannerChangeListener != null)
                mHintBannerChangeListener.onHintBannerScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onBannerSelected(int position) {
            if (mHintGroup != null) {
                mCreator.onHintReset(mHintGroup.getChildAt(mCurrentPosition));
                mCreator.onHintActive(mHintGroup.getChildAt(position));
            }
            mCurrentPosition = position;
            if (mHintBannerChangeListener != null)
                mHintBannerChangeListener.onHintBannerSelected(position);
        }

        @Override
        public void onBannerScrollStateChanged(int state) {
            if (mHintBannerChangeListener != null)
                mHintBannerChangeListener.onHintBannerScrollStateChanged(state);
        }
    }
}
