package com.linyuzai.demo4banner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button mBannerButton;
    Button mHintBannerButton;
    Button mIndicatorBannerButton;
    Button mIndicatorBanner2Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBannerButton = (Button) findViewById(R.id.bt_banner);
        mHintBannerButton = (Button) findViewById(R.id.bt_hint_banner);
        mIndicatorBannerButton = (Button) findViewById(R.id.bt_indicator_banner);
        mIndicatorBanner2Button = (Button) findViewById(R.id.bt_indicator_banner2);

        mBannerButton.setOnClickListener(this);
        mHintBannerButton.setOnClickListener(this);
        mIndicatorBannerButton.setOnClickListener(this);
        mIndicatorBanner2Button.setOnClickListener(this);

        /*indicator = (Indicator) findViewById(R.id.indicator);
        indicator.setAdapter(new TextIndicatorAdapter() {
            @Override
            public void onBindText(TextView text, int position) {

            }

            @Override
            public int getIndicatorCount() {
                return 0;
            }

        });
        indicator.setAdapter(new BaseIndicatorAdapter() {
            @Override
            public int getIndicatorCount() {
                return 20;
            }

            @Override
            public View getIndicatorView(ViewGroup parent, int position) {
                TextView text = new TextView(parent.getContext());
                text.setText(position > 9 ? position + "" : "0" + position);
                text.setPadding(35, 15, 35, 15);
                return text;
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
                return Color.BLACK;
            }

            @Override
            public int getMarginBottom() {
                return 0;
            }

            @Override
            public float getScale() {
                return 0.9f;
            }
        });



        indicator.bindBanner(banner);
        banner.bindIndicator(indicator);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_banner:
                startActivity(new Intent(MainActivity.this, BannerActivity.class));
                break;
            case R.id.bt_hint_banner:
                startActivity(new Intent(MainActivity.this, HintBannerActivity.class));
                break;
            case R.id.bt_indicator_banner:
                startActivity(new Intent(MainActivity.this, IndicatorBannerActivity.class));
                break;
            case R.id.bt_indicator_banner2:
                startActivity(new Intent(MainActivity.this, IndicatorBanner2Activity.class));
                break;
        }
    }
}
