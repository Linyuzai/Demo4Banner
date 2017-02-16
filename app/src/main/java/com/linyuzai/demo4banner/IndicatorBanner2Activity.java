package com.linyuzai.demo4banner;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linyuzai.banner.Banner;
import com.linyuzai.banner.ViewHolder;
import com.linyuzai.banner.ViewLocation;
import com.linyuzai.banner.indicator.SimpleCursorCreator;
import com.linyuzai.banner.indicator.BaseIndicatorAdapter;
import com.linyuzai.banner.indicator.Indicator;
import com.linyuzai.banner.indicator.OnIndicatorChangeCallback;

import java.util.ArrayList;
import java.util.List;

public class IndicatorBanner2Activity extends AppCompatActivity {
    String[] TITLE = {"妖精的尾巴", "夏目友人帐", "命运石之门"};
    int[] drawableIds;
    Indicator indicator;
    Banner banner;
    List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator_banner2);

        indicator = (Indicator) findViewById(R.id.indicator2);
        banner = (Banner) findViewById(R.id.indicator_banner2);

        drawableIds = new int[TITLE.length];
        drawableIds[0] = R.mipmap.yw;
        drawableIds[1] = R.mipmap.xm;
        drawableIds[2] = R.mipmap.sg;

        fragments = new ArrayList<>();
        for (int drawableId : drawableIds)
            fragments.add(MyFragment.newInstance(drawableId));

        class MyViewHolder extends ViewHolder {
            TextView text;

            MyViewHolder(View itemView) {
                super(itemView);
                text = (TextView) itemView.findViewById(R.id.indicator_text);
            }
        }

        indicator.setAdapter(new BaseIndicatorAdapter<MyViewHolder>() {
            @Override
            public int getIndicatorCount() {
                return TITLE.length;
            }

            @Override
            public MyViewHolder onCreateIndicatorViewHolder(ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.indicator_item, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindIndicatorViewHolder(MyViewHolder holder, int position) {
                holder.text.setText(TITLE[position]);
                holder.text.setTextColor(Color.GRAY);
            }

            @Override
            public boolean isFitScreenWidth() {
                return true;
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

            @Override
            public ViewLocation getViewLocation() {
                ViewLocation location = ViewLocation.getDefaultViewLocation();
                location.setVerticalGravity(ViewLocation.VerticalGravity.TOP);
                return location;
            }

            @Override
            public float getScale() {
                return 0.6f;
            }
        });

        indicator.setOnIndicatorChangeCallback(new OnIndicatorChangeCallback<MyViewHolder>() {
            @Override
            public boolean interceptBeforeChange(int position) {
                return false;
            }

            @Override
            public void onIndicatorRestore(MyViewHolder holder) {
                holder.text.setTextColor(Color.GRAY);
            }

            @Override
            public void onIndicatorChange(MyViewHolder holder) {
                holder.text.setTextColor(Color.BLUE);
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
