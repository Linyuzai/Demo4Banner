package com.linyuzai.banner.indicator;

import com.linyuzai.banner.ViewHolder;

/**
 * Created by Administrator on 2017/2/5 0005.
 *
 * @author Linyuzai
 */

public abstract class BaseIndicatorAdapter<VH extends ViewHolder> implements IndicatorAdapter<VH> {
    @Override
    public boolean isFitScreenWidth() {
        return false;
    }
}
