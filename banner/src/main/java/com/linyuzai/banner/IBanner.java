package com.linyuzai.banner;

import android.support.v4.view.PagerAdapter;

import com.linyuzai.banner.indicator.Indicator;

/**
 * Created by Administrator on 2017/1/11 0011.
 *
 * @author Linyuza
 */

public interface IBanner {
    /**
     * 开始自动轮播
     */
    void startAutoScroll();

    /**
     * 延迟自动轮播
     *
     * @param delay 延迟的时间
     */
    void startAutoScroll(long delay);

    /**
     * 停止自动轮播
     */
    void stopAutoScroll();

    /**
     * 绑定indicator
     *
     * @param indicator 需要绑定的indicator
     */
    void bindIndicator(Indicator indicator);

    /**
     * 获取item的数量
     *
     * @return item的数量，没有adapter返回Banner.NO_ADAPTER
     */
    int getCompatibleCount();

    /**
     * 设置adapter
     *
     * @param adapter 需要设置的adapter
     */
    void setAdapter(PagerAdapter adapter);

    /**
     * 获得当前的adapter
     *
     * @return 当前的adapter
     */
    PagerAdapter getAdapter();

    /**
     * 获得自动轮播的时间间隔
     *
     * @return 时间间隔
     */
    int getBannerInterval();

    /**
     * 设置自动轮播的时间间隔
     *
     * @param mBannerInterval 需要设置的时间间隔
     */
    void setBannerInterval(int mBannerInterval);

    /**
     * 获得手动滑动的翻页时间
     *
     * @return 手动翻页时间
     */
    int getManualDuration();

    /**
     * 设置手动滑动的翻页时间
     *
     * @param mManualDuration 需要设置的翻页时间
     */
    void setManualDuration(int mManualDuration);

    /**
     * 获得自动轮播的翻页时间
     *
     * @return 自动翻页时间
     */
    int getAutoDuration();

    /**
     * 设置自动轮播的翻页时间
     *
     * @param mAutoDuration 需要设置的翻页时间
     */
    void setAutoDuration(int mAutoDuration);
}
