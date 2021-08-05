package com.iusmob.adklein.demo.activity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.iusmob.adklein.ad.AdKleinError;
import com.iusmob.adklein.ad.AdKleinNativeExpressAd;
import com.iusmob.adklein.ad.AdKleinNativeExpressAdData;
import com.iusmob.adklein.ad.AdKleinNativeExpressAdListener;
import com.iusmob.adklein.demo.DemoConstants;
import com.iusmob.adklein.demo.R;
import com.iusmob.adklein.demo.utils.ToastUtils;

import java.util.List;

public class NativeExpressAdActivity extends AppCompatActivity implements View.OnClickListener,
        AdKleinNativeExpressAdListener {

    private FrameLayout adContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_express);
        adContainer = findViewById(R.id.ad_container);
        findViewById(R.id.express_ad).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.express_ad:
                adContainer.removeAllViews();
                loadExpressAd();
                break;
        }
    }

    private float getScreenWidth() {
        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        return (float) screenSize.x;
    }

    private AdKleinNativeExpressAd expressAd;

    private void loadExpressAd() {
        expressAd = new AdKleinNativeExpressAd(
                this,
                DemoConstants.FEED_EXPRESS_ID,
                this,
                getScreenWidth(), 0.0F
        );
        //设置华为广告为测试环境
        expressAd.setDev(true);
        expressAd.setAdPlayWithMute(true);
        expressAd.load(1);
    }

    @Override
    public void onAdLoaded(List<AdKleinNativeExpressAdData> list) {
        ToastUtils.toast(NativeExpressAdActivity.this, "native express ad loaded",
                Toast.LENGTH_SHORT);
        if (list.size() > 0) {
            adContainer.removeAllViews();
            adContainer.addView(list.get(0).getView());
            list.get(0).render();
        }
    }

    @Override
    public void onRenderFail(AdKleinNativeExpressAdData kleinNativeExpressAd,
                             AdKleinError adKleinError) {
        ToastUtils.toast(NativeExpressAdActivity.this, "native express ad render fail" +
                        " " + adKleinError.getErrorMsg() + " " + adKleinError.getErrorCode(),
                Toast.LENGTH_SHORT);
    }

    @Override
    public void onAdClose(AdKleinNativeExpressAdData kleinNativeExpressAd) {
        ToastUtils.toast(NativeExpressAdActivity.this, "native express ad close", Toast.LENGTH_SHORT);
        adContainer.removeAllViews();
    }

    @Override
    public void onError(AdKleinError adKleinError) {
        ToastUtils.toast(NativeExpressAdActivity.this, "native express ad error" +
                adKleinError.getErrorCode() + " " + adKleinError.getErrorMsg(), Toast.LENGTH_SHORT);
    }

    @Override
    public void onAdShow() {
        ToastUtils.toast(NativeExpressAdActivity.this, "native express ad show", Toast.LENGTH_SHORT);
    }

    @Override
    public void onAdClicked() {
        ToastUtils.toast(NativeExpressAdActivity.this, "native express ad clicked",
                Toast.LENGTH_SHORT);
    }

}
