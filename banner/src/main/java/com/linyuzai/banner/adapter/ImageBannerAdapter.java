package com.linyuzai.banner.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.linyuzai.banner.BannerAdapter;
import com.linyuzai.banner.ViewHolder;

/**
 * Created by Administrator on 2017/1/11 0011.
 *
 * @author Linyuzai
 */

public abstract class ImageBannerAdapter extends BannerAdapter {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        ImageView view = new ImageView(parent.getContext());
        onImageViewCreated(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        onBindImage((ImageView) holder.itemView, position);
    }

    /**
     * 用来设置ImageView的属性
     *
     * @param view 被创建的ImageView
     */
    public abstract void onImageViewCreated(ImageView view);

    /**
     * 绑定数据
     *
     * @param image    需要绑定数据的ImageView
     * @param position HintView item的position
     */
    public abstract void onBindImage(ImageView image, int position);
}
