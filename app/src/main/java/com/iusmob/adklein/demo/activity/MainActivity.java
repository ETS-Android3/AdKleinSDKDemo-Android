package com.iusmob.adklein.demo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.iusmob.adklein.ad.AdKleinSDK;
import com.iusmob.adklein.demo.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView) findViewById(R.id.tvVersion)).setText("V" + AdKleinSDK.getVersionName());

        findViewById(R.id.btnSplashAd).setOnClickListener(this);
        findViewById(R.id.btnBannerAd).setOnClickListener(this);
        findViewById(R.id.btnRewardVodeoAd).setOnClickListener(this);
        findViewById(R.id.btnFullScreenVideoAd).setOnClickListener(this);
        findViewById(R.id.btnInterstitialAd).setOnClickListener(this);
        findViewById(R.id.btnNativePage).setOnClickListener(this);
        findViewById(R.id.btnDrawAd).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSplashAd:
                Intent intent = new Intent(this, SplashAdActivity.class);
                intent.putExtra("SplashAd", true);
                startActivity(intent);
                break;
            case R.id.btnNativePage:
                startActivity(NativeActivity.class);
                break;
            case R.id.btnBannerAd:
                startActivity(BannerAdActivity.class);
                break;
            case R.id.btnRewardVodeoAd:
                startActivity(RewardVideoAdActivity.class);
                break;
            case R.id.btnFullScreenVideoAd:
                startActivity(FullScreenVideoAdActivity.class);
                break;
            case R.id.btnInterstitialAd:
                startActivity(InterstitialAdActivity.class);
                break;
            case R.id.btnDrawAd:
                startActivity(DrawAdActivity.class);
                break;
            default:
                break;
        }
    }

    private void startActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
