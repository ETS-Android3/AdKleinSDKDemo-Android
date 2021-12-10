package com.iusmob.adklein.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.iusmob.adklein.ad.AdKleinError;
import com.iusmob.adklein.ad.AdKleinFullscreenVideoAd;
import com.iusmob.adklein.ad.AdKleinFullscreenVideoAdListener;
import com.iusmob.adklein.demo.DemoConstants;
import com.iusmob.adklein.demo.R;
import com.iusmob.adklein.demo.utils.ToastUtils;

public class FullScreenVideoAdActivity extends AppCompatActivity implements View.OnClickListener {

    AdKleinFullscreenVideoAd fullscreenVideo;

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

        btnLoadAd.setText("获取全屏视频广告");
        btnShowAd.setText("展示全屏视频广告");

        btnLoadAd.setOnClickListener(this);
        btnShowAd.setOnClickListener(this);
    }

    private void initAd() {
        fullscreenVideo = new AdKleinFullscreenVideoAd(
                this, DemoConstants.FULLSCREEN_ID, 1, false,
                new AdKleinFullscreenVideoAdListener() {
                    /**
                     * 广告关闭回调
                     * */
                    @Override
                    public void onAdClose() {
                        ToastUtils.toast(FullScreenVideoAdActivity.this, "fullscreen onAdClose",
                                Toast.LENGTH_SHORT);
                    }

                    /**
                     * 视频播放完毕回调
                     * */
                    @Override
                    public void onVideoComplete() {
                        ToastUtils.toast(FullScreenVideoAdActivity.this, "fullscreen " +
                                "onVideoComplete", Toast.LENGTH_SHORT);
                    }

                    /**
                     * 视频点击跳过回调
                     * */
                    @Override
                    public void onSkippedVideo() {
                        ToastUtils.toast(FullScreenVideoAdActivity.this, "fullscreen " +
                                "onSkippedVideo", Toast.LENGTH_SHORT);
                    }

                    /**
                     * 广告加载失败
                     * @param adKleinError 错误描述
                     * */
                    @Override
                    public void onError(AdKleinError adKleinError) {
                        ToastUtils.toast(FullScreenVideoAdActivity.this,
                                "fullscreen onError " + adKleinError.getErrorCode() + " " + adKleinError.getErrorMsg(), Toast.LENGTH_SHORT);
                    }

                    /**
                     * 广告拉取成功
                     * */
                    @Override
                    public void onAdLoaded() {
                        ToastUtils.toast(FullScreenVideoAdActivity.this, "fullscreen onAdLoaded",
                                Toast.LENGTH_SHORT);
                    }

                    /**
                     * 广告展示成功
                     * */
                    @Override
                    public void onAdShow() {
                        ToastUtils.toast(FullScreenVideoAdActivity.this, "fullscreen onAdShow",
                                Toast.LENGTH_SHORT);
                    }

                    /**
                     * 广告点击回调
                     * */
                    @Override
                    public void onAdClicked() {
                        ToastUtils.toast(FullScreenVideoAdActivity.this, "fullscreen onAdClicked"
                                , Toast.LENGTH_SHORT);
                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoadAd:
                fullscreenVideo.load();
                break;
            case R.id.btnShowAd:
                fullscreenVideo.show();
                break;
            default:
                break;
        }
    }

}
