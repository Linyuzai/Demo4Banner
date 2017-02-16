package com.linyuzai.demo4banner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button mBannerButton;
    Button mBanner2Button;
    Button mHintBannerButton;
    Button mHintBanner2Button;
    Button mIndicatorBannerButton;
    Button mIndicatorBanner2Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBannerButton = (Button) findViewById(R.id.bt_banner);
        mBanner2Button = (Button) findViewById(R.id.bt_banner2);
        mHintBannerButton = (Button) findViewById(R.id.bt_hint_banner);
        mHintBanner2Button = (Button) findViewById(R.id.bt_hint_banner2);
        mIndicatorBannerButton = (Button) findViewById(R.id.bt_indicator_banner);
        mIndicatorBanner2Button = (Button) findViewById(R.id.bt_indicator_banner2);

        mBannerButton.setOnClickListener(this);
        mBanner2Button.setOnClickListener(this);
        mHintBannerButton.setOnClickListener(this);
        mHintBanner2Button.setOnClickListener(this);
        mIndicatorBannerButton.setOnClickListener(this);
        mIndicatorBanner2Button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_banner:
                startActivity(new Intent(MainActivity.this, BannerActivity.class));
                break;
            case R.id.bt_banner2:
                startActivity(new Intent(MainActivity.this, Banner2Activity.class));
                break;
            case R.id.bt_hint_banner:
                startActivity(new Intent(MainActivity.this, HintBannerActivity.class));
                break;
            case R.id.bt_hint_banner2:
                startActivity(new Intent(MainActivity.this, HintBanner2Activity.class));
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
