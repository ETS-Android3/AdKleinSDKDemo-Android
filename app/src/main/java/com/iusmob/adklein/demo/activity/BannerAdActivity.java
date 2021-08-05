package com.iusmob.adklein.demo.activity;

import android.graphics.Point;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.iusmob.adklein.ad.AdKleinBannerAd;
import com.iusmob.adklein.ad.AdKleinBannerAdListener;
import com.iusmob.adklein.ad.AdKleinError;
import com.iusmob.adklein.demo.DemoConstants;
import com.iusmob.adklein.demo.R;
import com.iusmob.adklein.demo.utils.ToastUtils;

public class BannerAdActivity extends AppCompatActivity {

    private AdKleinBannerAd kleinBanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        FrameLayout adContainer = findViewById(R.id.ad_container);
        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        float width = screenSize.x;
        kleinBanner = new AdKleinBannerAd(
                this,
                DemoConstants.BANNER_ID,
                adContainer,
                new AdKleinBannerAdListener() {
                    @Override
                    public void onAdClose() {
                        ToastUtils.toast(BannerAdActivity.this, "banner ad closed",
                                Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onError(AdKleinError adKleinError) {
                        ToastUtils.toast(BannerAdActivity.this,
                                String.format("error %d, %s", adKleinError.getErrorCode(),
                                        adKleinError.getErrorMsg()),
                                Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onAdLoaded() {
                        ToastUtils.toast(BannerAdActivity.this, "banner ad loaded",
                                Toast.LENGTH_SHORT);
                        kleinBanner.show();
                    }

                    @Override
                    public void onAdShow() {
                        ToastUtils.toast(BannerAdActivity.this, "banner ad shown",
                                Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onAdClicked() {
                        ToastUtils.toast(BannerAdActivity.this, "banner ad clicked",
                                Toast.LENGTH_SHORT);
                    }
                },
                15000, width, 0.0F);
        kleinBanner.load();
    }
}
