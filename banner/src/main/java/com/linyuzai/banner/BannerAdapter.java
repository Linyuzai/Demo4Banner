package com.linyuzai.banner;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;


/**
 * 提供循环播放功能的adapter
 * <p>
 * Created by Administrator on 2017/1/5 0005.
 *
 * @author Linyuzai
 */

public abstract class BannerAdapter<VH extends ViewHolder> extends PagerAdapter {
    private static final String TAG = "BannerAdapter";
    private static final boolean DEBUG = false;

    private LinkedList<ViewHolder> mRecyclerViewHolders = new LinkedList<>();//可重复使用的view

    OnBannerItemClickListener mBannerItemClickListener;

    @Override
    public int getCount() {
        //如果可循环数量的Integer.MAX_VALUE
        return isLoop() ? Integer.MAX_VALUE : getBannerCount();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewHolder holder = null;
        //如果有可以复用的，则从中取得
        if (mRecyclerViewHolders.size() > 0) {
            holder = mRecyclerViewHolders.getFirst();
            mRecyclerViewHolders.removeFirst();
        }
        //获得相对应的position
        int modifyPosition = getBannerCount() == 0 ? 0 : position % getBannerCount();
        if (DEBUG)
            Log.d(TAG, "instantiateItem->position:" + position + ",modifyPosition:" + modifyPosition);
        //如果holder为空则创建一个
        if (holder == null)
            holder = onCreateViewHolder(container);
        holder.itemView.setOnClickListener(new OnClickListenerImpl(position));
        //绑定数据
        onBindViewHolder((VH) holder, modifyPosition);
        //View view = getBannerView(convertView, container, modifyPosition);
        if (holder.itemView.getParent() == null)
            container.addView(holder.itemView);
        return holder.itemView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (DEBUG)
            Log.d(TAG, "destroyItem->position:" + position);
        View recyclerView = (View) object;
        container.removeView(recyclerView);
        ViewHolder holder = (ViewHolder) recyclerView.getTag();
        //移除后添加到复用池
        if (holder != null)
            mRecyclerViewHolders.addFirst(holder);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * i设置tem的数量
     *
     * @return item的数量
     */
    public abstract int getBannerCount();

    //public abstract View getBannerView(View convertView, ViewGroup parent, int position);

    /**
     * 创建一个ViewHolder
     *
     * @param parent 父控件
     * @return ViewHolder
     */
    public abstract VH onCreateViewHolder(ViewGroup parent);

    /**
     * 绑定数据
     *
     * @param holder   创建或复用的ViewHolder
     * @param position 位置position
     */
    public abstract void onBindViewHolder(VH holder, int position);

    /**
     * 是否循环
     *
     * @return true可循环，false不可循环
     */
    public boolean isLoop() {
        return false;
    }

    private class OnClickListenerImpl implements View.OnClickListener {
        private int position;

        OnClickListenerImpl(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (mBannerItemClickListener != null)
                mBannerItemClickListener.onBannerItemClick((ViewHolder) v.getTag(), position);
        }
    }
}
