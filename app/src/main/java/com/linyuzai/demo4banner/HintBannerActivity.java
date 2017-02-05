package com.linyuzai.demo4banner;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.linyuzai.banner.adapter.ImageBannerAdapter;
import com.linyuzai.banner.creator.ColorHintViewCreator;
import com.linyuzai.banner.creator.DrawableHintViewCreator;
import com.linyuzai.banner.hint.HintBanner;

public class HintBannerActivity extends AppCompatActivity {

    HintBanner hintBanner1, hintBanner2;
    Drawable jj1, jj2, jj3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint_banner);

        hintBanner1 = (HintBanner) findViewById(R.id.hint_banner1);
        hintBanner2 = (HintBanner) findViewById(R.id.hint_banner2);

        jj1 = getResources().getDrawable(R.mipmap.jj01);
        jj2 = getResources().getDrawable(R.mipmap.jj02);
        jj3 = getResources().getDrawable(R.mipmap.jj03);

        hintBanner1.setAdapter(new ImageBannerAdapter() {
            @Override
            public void onImageViewCreated(ImageView view) {
                view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }

            @Override
            public void onBindImage(ImageView image, int position) {
                switch (position) {
                    case 0:
                        image.setImageDrawable(jj1);
                        break;
                    case 1:
                        image.setImageDrawable(jj2);
                        break;
                    case 2:
                        image.setImageDrawable(jj3);
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
        hintBanner1.setHintView(new ColorHintViewCreator() {
            @Override
            public int getHintActiveColor() {
                return Color.WHITE;
            }

            @Override
            public int getHintResetColor() {
                return Color.BLACK;
            }

            @Override
            public boolean isRound() {
                return true;
            }

            @Override
            public int getViewHeight() {
                return 5;
            }

            @Override
            public int getViewWidth() {
                return 5;
            }

            @Override
            public int getMarginBottom() {
                return 10;
            }

            @Override
            public int getSpacing() {
                return 5;
            }
        });
        hintBanner1.startAutoScroll(2000);
        hintBanner2.setHintView(new DrawableHintViewCreator() {
            @Override
            public Drawable getHintActiveDrawable() {
                return getResources().getDrawable(R.mipmap.point_big);
            }

            @Override
            public Drawable getHintResetDrawable() {
                return getResources().getDrawable(R.mipmap.point_small);
            }

            @Override
            public int getMarginBottom() {
                return 10;
            }

            @Override
            public int getDrawableHeight() {
                return 25;
            }

            @Override
            public int getDrawableWidth() {
                return 25;
            }
        });
        hintBanner2.setAdapter(new ImageBannerAdapter() {
            @Override
            public void onImageViewCreated(ImageView view) {
                view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }

            @Override
            public void onBindImage(ImageView image, int position) {
                switch (position) {
                    case 0:
                        image.setImageDrawable(jj1);
                        break;
                    case 1:
                        image.setImageDrawable(jj2);
                        break;
                    case 2:
                        image.setImageDrawable(jj3);
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

        hintBanner2.startAutoScroll(2000);
    }
}
