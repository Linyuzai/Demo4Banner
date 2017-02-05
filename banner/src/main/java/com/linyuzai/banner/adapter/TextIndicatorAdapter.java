package com.linyuzai.banner.adapter;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linyuzai.banner.ViewHolder;
import com.linyuzai.banner.indicator.BaseIndicatorAdapter;


/**
 * Created by Administrator on 2017/2/4 0004.
 *
 * @author Linyuzai
 */

public abstract class TextIndicatorAdapter extends BaseIndicatorAdapter {

    @Override
    public ViewHolder onCreateIndicatorViewHolder(ViewGroup parent) {
        TextView indicatorView = new TextView(parent.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        indicatorView.setLayoutParams(params);
        indicatorView.setGravity(Gravity.CENTER);
        return new ViewHolder(indicatorView);
    }

    @Override
    public void onBindIndicatorViewHolder(ViewHolder holder, int position) {
        onBindText((TextView) holder.itemView, position);
    }

    /**
     * 绑定数据
     *
     * @param text     需要绑定数据的TextView
     * @param position 导航栏的item的position
     */
    public abstract void onBindText(TextView text, int position);
}
