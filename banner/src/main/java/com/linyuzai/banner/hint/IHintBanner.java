package com.linyuzai.banner.hint;

import com.linyuzai.banner.Banner;
import com.linyuzai.banner.IBanner;

/**
 * Created by Administrator on 2017/1/11 0011.
 *
 * @author Linyuzai
 */

public interface IHintBanner extends IBanner {
    /**
     * 设置HintView
     *
     * @param creator HintView的创建接口
     */
    void setHintView(HintViewCreator creator);

    /**
     * 获得里面的Banner
     *
     * @return 当前的Banner
     */
    Banner getBanner();

    /**
     * 获得HintBannerChangeListener
     *
     * @return HintBannerChangeListener
     */
    OnHintBannerChangeListener getOnHintBannerChangeListener();

    /**
     * 设置HintBannerChangeListener
     *
     * @param listener 需要设置的HintBannerChangeListener
     */
    void setOnHintBannerChangeListener(OnHintBannerChangeListener listener);
}
