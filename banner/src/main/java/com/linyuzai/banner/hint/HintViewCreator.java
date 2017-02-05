package com.linyuzai.banner.hint;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/2/4 0004.
 *
 * @author Linyuzai
 */

public interface HintViewCreator {
    /**
     * @param parent 父控件
     * @return HintView实例
     */
    View getHintView(ViewGroup parent);

    /**
     * 更新HintView的item
     *
     * @param hint 需要更新的HintView
     */
    void onHintActive(View hint);

    /**
     * 还原HintView的item
     *
     * @param hint 需要还原的HintView
     */
    void onHintReset(View hint);

    /**
     * @return 到底部的距离
     */
    int getMarginBottom();
}
