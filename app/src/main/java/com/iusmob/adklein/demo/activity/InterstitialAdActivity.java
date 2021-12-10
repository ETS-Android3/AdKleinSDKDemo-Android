package com.iusmob.adklein.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.iusmob.adklein.ad.AdKleinError;
import com.iusmob.adklein.ad.AdKleinInterstitialAd;
import com.iusmob.adklein.ad.AdKleinInterstitialAdListener;
import com.iusmob.adklein.demo.DemoConstants;
import com.iusmob.adklein.demo.R;
import com.iusmob.adklein.demo.utils.ToastUtils;
import com.iusmob.adklein.demo.utils.UIUtils;

public class InterstitialAdActivity extends AppCompatActivity implements View.OnClickListener {

    private AdKleinInterstitialAd kleinInterstitial;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_video);

        initListener();
        initAd();
    }

    private void initListener() {
        Button btnLoadAd = findViewById(R.id.btnLoadAd);
        Button btnShowAd = findViewById(R.id.btnShowAd);

        btnLoadAd.setText("获取插屏广告");
        btnShowAd.setText("展示插屏广告");

        btnLoadAd.setOnClickListener(this);
        btnShowAd.setOnClickListener(this);
    }

    private void initAd() {
        kleinInterstitial = new AdKleinInterstitialAd(
                this, DemoConstants.INTERSTITIAL_ID,
                new AdKleinInterstitialAdListener() {
                    /**
                     * 广告关闭回调
                     * */
                    @Override
                    public void onAdClosed() {
                        ToastUtils.toast(InterstitialAdActivity.this, "interstitial onAdClosed",
                                Toast.LENGTH_SHORT);
                    }

                    /**
                     * 广告渲染失败
                     *
                     * @param adKleinError         错误描述
                     */
                    @Override
                    public void onRenderFail(AdKleinError adKleinError) {
                        ToastUtils.toast(InterstitialAdActivity.this, "interstitial onRenderFail "
                                        + adKleinError.getErrorCode() + " " + adKleinError.getErrorMsg()
                                , Toast.LENGTH_SHORT);
                    }

                    /**
                     * 广告渲染成功
                     */
                    @Override
                    public void onRenderSuccess() {
                        ToastUtils.toast(InterstitialAdActivity.this, "interstitial " +
                                "onRenderSuccess", Toast.LENGTH_SHORT);
                    }

                    /**
                     * 广告加载失败
                     * @param adKleinError 错误描述
                     * */
                    @Override
                    public void onError(AdKleinError adKleinError) {
                        ToastUtils.toast(InterstitialAdActivity.this,
                                "interstitial onError " + adKleinError.getErrorCode() + " " + adKleinError.getErrorMsg(), Toast.LENGTH_SHORT);
                    }

                    /**
                     * 广告拉取成功
                     * */
                    @Override
                    public void onAdLoaded() {
                        ToastUtils.toast(InterstitialAdActivity.this, "interstitial onAdLoaded",
                                Toast.LENGTH_SHORT);
                    }

                    /**
                     * 广告展示成功
                     * */
                    @Override
                    public void onAdShow() {
                        ToastUtils.toast(InterstitialAdActivity.this, "interstitial onAdShow",
                                Toast.LENGTH_SHORT);
                    }

                    /**
                     * 广告点击回调
                     * */
                    @Override
                    public void onAdClicked() {
                        ToastUtils.toast(InterstitialAdActivity.this, "interstitial onAdClicked",
                                Toast.LENGTH_SHORT);
                    }
                }, (int) (UIUtils.getScreenWidthDp(this) * 3 / 4), UIUtils.getScreenWidthDp(this)
        );
        //设置视频音量，true为
        kleinInterstitial.setVolumeOn(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoadAd:
                kleinInterstitial.load();
                break;
            case R.id.btnShowAd:
                kleinInterstitial.show();
                break;
            default:
                break;
        }
    }

}
