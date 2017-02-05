package com.linyuzai.banner.indicator;

import com.linyuzai.banner.ViewHolder;

/**
 * Created by Administrator on 2017/2/4 0004.
 *
 * @author Linyuzai
 */

public interface OnIndicatorClickListener<VH extends ViewHolder> {
    /**
     * 点击导航栏的响应，在导航栏更新之后调用，在OnIndicatorChangeCallback之后调用
     *
     * @param holder   导航栏item的ViewHolder
     * @param position 导航栏item的position
     */
    void onIndicatorClick(VH holder, int position);
}
