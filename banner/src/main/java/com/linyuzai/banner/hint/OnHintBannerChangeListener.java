package com.linyuzai.banner.hint;

/**
 * Created by Administrator on 2017/1/11 0011.
 *
 * @author Linyuzai
 * @see com.linyuzai.banner.OnBannerChangeListener
 */

public interface OnHintBannerChangeListener {
    void onHintBannerScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onHintBannerSelected(int position);

    void onHintBannerScrollStateChanged(int state);
}
