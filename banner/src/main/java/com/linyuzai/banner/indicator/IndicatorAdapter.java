package com.linyuzai.banner.indicator;

import android.view.ViewGroup;

import com.linyuzai.banner.ViewHolder;

/**
 * Created by Administrator on 2017/2/4 0004.
 *
 * @author Linyuzai
 */

public interface IndicatorAdapter<VH extends ViewHolder> {
    /**
     * 设置导航栏的item数量
     *
     * @return item数量
     */
    int getIndicatorCount();

    //View getIndicatorView(ViewGroup parent, int position);

    /**
     * 创建导航栏ViewHolder
     *
     * @param parent 父控件
     * @return ViewHolder
     */
    VH onCreateIndicatorViewHolder(ViewGroup parent);

    /**
     * 绑定导航栏数据
     *
     * @param holder   导航栏ViewHolder
     * @param position item的position
     */
    void onBindIndicatorViewHolder(VH holder, int position);

    /**
     * 适配屏幕
     *
     * @return true导航栏和屏幕一样宽，不可滚动
     */
    boolean isFitScreenWidth();
}
