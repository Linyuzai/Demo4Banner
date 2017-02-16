package com.linyuzai.demo4banner;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.linyuzai.banner.Banner;
import com.linyuzai.banner.BannerAdapter2;
import com.linyuzai.banner.OnBannerChangeListener;
import com.linyuzai.banner.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class Banner2Activity extends AppCompatActivity {

    Button button1, button2;
    Banner banner;
    Drawable hz1, hz2, hz3, jj1, jj2, jj3;
    List<Drawable> drawables;
    BannerAdapter2 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner2);

        banner = (Banner) findViewById(R.id.banner);
        button1 = (Button) findViewById(R.id.bt_add);
        button2 = (Button) findViewById(R.id.bt_remove);

        hz1 = getResources().getDrawable(R.mipmap.hz01);
        hz2 = getResources().getDrawable(R.mipmap.hz02);
        hz3 = getResources().getDrawable(R.mipmap.hz03);

        drawables = new ArrayList<>();
        drawables.add(hz1);
        drawables.add(hz2);
        drawables.add(hz3);

        jj1 = getResources().getDrawable(R.mipmap.jj01);
        jj2 = getResources().getDrawable(R.mipmap.jj02);
        jj3 = getResources().getDrawable(R.mipmap.jj03);

        class MyViewHolder extends ViewHolder {
            ImageView image;
            TextView text;

            MyViewHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.image);
                text = (TextView) itemView.findViewById(R.id.text_position);
            }
        }

        banner.setAdapter(adapter = new BannerAdapter2<MyViewHolder>() {
            @Override
            public int getBannerCount() {
                return drawables.size();
            }

            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_item, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                holder.image.setImageDrawable(drawables.get(position));
            }

            @Override
            public boolean isLoop() {
                return true;
            }
        });

        banner.setOnBannerChangeListener(new OnBannerChangeListener() {
            @Override
            public void onBannerScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onBannerSelected(int position) {
                //Log.i("qwert", "onBannerSelected:" + position);
            }

            @Override
            public void onBannerScrollStateChanged(int state) {

            }
        });

        banner.startAutoScroll(2000);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawables.add(jj1);
                adapter.notifyDataSetChanged();
                banner.updateBannerAfterDataSetChanged();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawables.remove(0);
                adapter.notifyDataSetChanged();
                banner.updateBannerAfterDataSetChanged();
            }
        });
    }
}
