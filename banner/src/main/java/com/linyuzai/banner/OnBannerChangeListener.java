package com.linyuzai.banner;

/**
 * Created by Administrator on 2017/1/5 0005.
 *
 * @author Linyuzai
 * @see android.support.v4.view.ViewPager.OnPageChangeListener
 */

public interface OnBannerChangeListener {
    void onBannerScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onBannerSelected(int position);

    void onBannerScrollStateChanged(int state);
}
