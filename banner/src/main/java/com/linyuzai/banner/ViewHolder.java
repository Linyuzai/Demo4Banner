package com.linyuzai.banner;

import android.view.View;

/**
 * 使用setTag()来保存ViewHolder
 * <p>
 * Created by Administrator on 2017/1/11 0011.
 *
 * @author Linyuzai
 */

public class ViewHolder {
    public final View itemView;

    public ViewHolder(View itemView) {
        this.itemView = itemView;
        this.itemView.setTag(this);
    }
}
