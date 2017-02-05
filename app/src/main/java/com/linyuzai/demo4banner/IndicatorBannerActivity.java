package com.linyuzai.demo4banner;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linyuzai.banner.Banner;
import com.linyuzai.banner.ViewHolder;
import com.linyuzai.banner.adapter.TextIndicatorAdapter;
import com.linyuzai.banner.indicator.SimpleCursorCreator;
import com.linyuzai.banner.indicator.Indicator;
import com.linyuzai.banner.indicator.OnIndicatorChangeCallback;

import java.util.ArrayList;
import java.util.List;

public class IndicatorBannerActivity extends AppCompatActivity {
    String[] TITLE = {"死神", "火影忍者", "海贼王", "家庭教师", "妖精的尾巴", "钢之炼金术师", "夏目友人帐", "命运石之门", "名侦探柯南", "野良神"};
    int[] drawableIds;
    Indicator indicator;
    Banner banner;
    List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator_banner);

        indicator = (Indicator) findViewById(R.id.indicator);
        banner = (Banner) findViewById(R.id.indicator_banner);

        drawableIds = new int[TITLE.length];
        drawableIds[0] = R.mipmap.ss;
        drawableIds[1] = R.mipmap.hy;
        drawableIds[2] = R.mipmap.hz;
        drawableIds[3] = R.mipmap.jj;
        drawableIds[4] = R.mipmap.yw;
        drawableIds[5] = R.mipmap.gl;
        drawableIds[6] = R.mipmap.xm;
        drawableIds[7] = R.mipmap.sg;
        drawableIds[8] = R.mipmap.kn;
        drawableIds[9] = R.mipmap.yl;

        fragments = new ArrayList<>();
        for (int drawableId : drawableIds)
            fragments.add(MyFragment.newInstance(drawableId));

        indicator.setAdapter(new TextIndicatorAdapter() {
            @Override
            public void onBindText(TextView text, int position) {
                ViewGroup.LayoutParams params = text.getLayoutParams();
                params.width = 100;
                params.height = 50;
                text.setLayoutParams(params);
                text.setText(TITLE[position]);
                text.setTextColor(Color.GRAY);
            }

            @Override
            public int getIndicatorCount() {
                return drawableIds.length;
            }
        });
        indicator.setCursor(new SimpleCursorCreator() {
            @Override
            public float getHeight() {
                return 3;
            }

            @Override
            public int getColor() {
                return Color.BLUE;
            }
        });
        indicator.setOnIndicatorChangeCallback(new OnIndicatorChangeCallback() {
            @Override
            public boolean interceptBeforeChange(int position) {
                return false;
            }

            @Override
            public void onIndicatorRestore(ViewHolder holder) {
                ((TextView) holder.itemView).setTextColor(Color.GRAY);
            }

            @Override
            public void onIndicatorChange(ViewHolder holder) {
                ((TextView) holder.itemView).setTextColor(Color.BLUE);
            }
        });

        banner.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        indicator.bindBanner(banner);
        banner.bindIndicator(indicator);
    }
}
