package com.linyuzai.banner.indicator;

import com.linyuzai.banner.ViewHolder;

/**
 * Created by Administrator on 2017/2/4 0004.
 *
 * @author Linyuzai
 */

public interface OnIndicatorChangeCallback<VH extends ViewHolder> {
    /**
     * 指示器更新回调
     *
     * @param position 更新的位置
     * @return true--拦截导航栏的更新操作，false--不拦截
     */
    boolean interceptBeforeChange(int position);

    /**
     * 还原上一个导航栏item
     *
     * @param holder 导航栏item的ViewHolder
     */
    void onIndicatorRestore(VH holder);

    /**
     * 更新某个位置的导航栏item
     *
     * @param holder 导航栏item的ViewHolder
     */
    void onIndicatorChange(VH holder);
}
