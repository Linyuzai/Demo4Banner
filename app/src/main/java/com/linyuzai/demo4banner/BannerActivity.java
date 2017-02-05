package com.linyuzai.demo4banner;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linyuzai.banner.Banner;
import com.linyuzai.banner.BannerAdapter;
import com.linyuzai.banner.ViewHolder;
import com.linyuzai.banner.adapter.ImageBannerAdapter;

public class BannerActivity extends AppCompatActivity {

    Banner banner1, banner2;
    Drawable hz1, hz2, hz3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        banner1 = (Banner) findViewById(R.id.banner1);
        banner2 = (Banner) findViewById(R.id.banner2);

        hz1 = getResources().getDrawable(R.mipmap.hz01);
        hz2 = getResources().getDrawable(R.mipmap.hz02);
        hz3 = getResources().getDrawable(R.mipmap.hz03);

        class MyViewHolder extends ViewHolder {
            ImageView image;
            TextView text;

            MyViewHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.image);
                text = (TextView) itemView.findViewById(R.id.text_position);
            }
        }
        banner1.setAdapter(new BannerAdapter<MyViewHolder>() {

            @Override
            public int getBannerCount() {
                return 3;
            }

            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_item, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                //holder.text.setText(String.valueOf(position));
                switch (position) {
                    case 0:
                        holder.image.setImageDrawable(hz1);
                        break;
                    case 1:
                        holder.image.setImageDrawable(hz2);
                        break;
                    case 2:
                        holder.image.setImageDrawable(hz3);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public boolean isLoop() {
                return true;
            }
        });
        banner1.startAutoScroll(2000);

        banner2.setAdapter(new ImageBannerAdapter() {
            @Override
            public void onImageViewCreated(ImageView view) {
                view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }

            @Override
            public void onBindImage(ImageView image, int position) {
                switch (position) {
                    case 0:
                        image.setImageDrawable(hz1);
                        break;
                    case 1:
                        image.setImageDrawable(hz2);
                        break;
                    case 2:
                        image.setImageDrawable(hz3);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public int getBannerCount() {
                return 3;
            }

            @Override
            public boolean isLoop() {
                return true;
            }
        });
        banner2.startAutoScroll(2000);
    }
}
