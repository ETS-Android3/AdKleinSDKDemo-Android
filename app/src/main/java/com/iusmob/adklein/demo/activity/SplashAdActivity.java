package com.iusmob.adklein.demo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.iusmob.adklein.ad.AdKleinError;
import com.iusmob.adklein.ad.AdKleinSplashAd;
import com.iusmob.adklein.ad.AdKleinSplashAdListener;
import com.iusmob.adklein.demo.DemoConstants;
import com.iusmob.adklein.demo.R;
import com.iusmob.adklein.demo.utils.SPUtil;
import com.iusmob.adklein.demo.utils.ToastUtils;
import com.iusmob.adklein.demo.widget.PrivacyPolicyDialog;

import java.util.ArrayList;
import java.util.List;

public class SplashAdActivity extends Activity implements View.OnClickListener {

    private static final String AGREE_PRIVACY_POLICY = "AGREE_PRIVACY_POLICY";
    /**
     * 根据实际情况申请
     */
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private List<String> permissionList = new ArrayList<>();

    private TextView tvSkip;

    private AdKleinSplashAd kleinSplash;
    private ConstraintLayout clButton;
    private SwitchCompat switchJump;
    private FrameLayout adContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_ad);
        tvSkip = findViewById(R.id.tv_skip);
        adContainer = findViewById(R.id.ad_container);
        clButton = findViewById(R.id.cl_button);
        switchJump = findViewById(R.id.switch_jump);
        findViewById(R.id.splash_ad).setOnClickListener(this);
        boolean splashAd = getIntent().getBooleanExtra("SplashAd", false);
        if (splashAd) {
            clButton.setVisibility(View.VISIBLE);
        } else {
            checkPrivacyPolicy();
        }
    }

    /**
     * 检查隐私政策
     */
    private void checkPrivacyPolicy() {
        boolean agreePrivacyPolicy = SPUtil.getBoolean(this, AGREE_PRIVACY_POLICY);
        if (agreePrivacyPolicy) {
            loadSplashAd();
        } else {
            showPrivacyPolicyDialog();
        }
    }

    /**
     * 展示隐私政策弹框
     */
    private void showPrivacyPolicyDialog() {
        PrivacyPolicyDialog privacyPolicyDialog = new PrivacyPolicyDialog(this);
        privacyPolicyDialog.setCallback(new PrivacyPolicyDialog.OnResultCallback() {
            @Override
            public void onConfirm() {
                SPUtil.putBoolean(SplashAdActivity.this, AGREE_PRIVACY_POLICY, true);
                loadSplashAd();
            }

            @Override
            public void onCancel() {
                jumpMain();
            }
        });
        privacyPolicyDialog.setCancelable(false);
        privacyPolicyDialog.setCanceledOnTouchOutside(false);
        privacyPolicyDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.splash_ad:
                clButton.setVisibility(View.GONE);
                loadSplashAd();
                break;
        }
    }

    private void loadSplashAd() {
        // 6.0及以上获取没有申请的权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : PERMISSIONS) {
                int checkSelfPermission = ContextCompat.checkSelfPermission(this, permission);
                if (PackageManager.PERMISSION_GRANTED == checkSelfPermission) {
                    continue;
                }
                permissionList.add(permission);
            }
        }

        //展示底部view
        findViewById(R.id.relativeLayout).setVisibility(View.VISIBLE);
        // ，创建开屏广告实例第一个参数可以是Activity或Fragment，第二个参数是广告容器（请保证容器不会拦截点击、触摸等事件）
        kleinSplash = new AdKleinSplashAd(this, adContainer,
                DemoConstants.SPLASH_ID, new AdKleinSplashAdListener() {
            @Override
            public void onAdClosed() {
                ToastUtils.toast(SplashAdActivity.this, "splash onAdClosed", Toast.LENGTH_SHORT);
                jumpMain();
            }

            @Override
            public void onAdSkip() {
                //广告跳过回调，不一定准确
                ToastUtils.toast(SplashAdActivity.this, "splash onAdSkip", Toast.LENGTH_SHORT);
            }

            @Override
            public void onAdTick(int millisUntilFinished) {
                // 如果没有设置自定义跳过按钮不会回调该方法
                Log.d("SplashAdActivity", "倒计时剩余时长" + millisUntilFinished);
                tvSkip.setText(String.format(getString(R.string.splash_skip_text),
                        millisUntilFinished));
            }

            @Override
            public void onError(AdKleinError adKleinError) {
                ToastUtils.toast(SplashAdActivity.this,
                        "splash onError " + adKleinError.getErrorCode() + " " + adKleinError.getErrorMsg(),
                        Toast.LENGTH_SHORT);
                jumpMain();
            }

            @Override
            public void onAdLoaded() {
                ToastUtils.toast(SplashAdActivity.this, "splash onAdLoaded", Toast.LENGTH_SHORT);
                if (switchJump.isChecked()) {
                    tvSkip.setVisibility(View.VISIBLE);
                }
                kleinSplash.show();
            }

            @Override
            public void onAdShow() {
                ToastUtils.toast(SplashAdActivity.this, "splash onAdShow", Toast.LENGTH_SHORT);
            }

            @Override
            public void onAdClicked() {
                ToastUtils.toast(SplashAdActivity.this, "splash onAdClicked", Toast.LENGTH_SHORT);
            }
        });
        //设置应用热启动（从后台切换到APP内）时展示开屏广告，可以在管理后台进行配置。
        kleinSplash.setClassAddress("com.iusmob.adklein.demo.activity.SplashAdActivity");

        if (switchJump.isChecked()) {
            // 设置自定义跳过按钮和倒计时时长（非必传，倒计时时长范围[3000,5000]建议不要传入倒计时时长）
            kleinSplash.setSkipView(tvSkip, 5000);
        }

        if (!permissionList.isEmpty()) {
            // 存在未申请的权限则先申请
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[0]), 1);
        } else {
            kleinSplash.load();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode) {
            // 加载开屏广告，参数为广告位ID，同一个KleinSplash只有一次loadAd有效
            kleinSplash.load();
        }
    }

    @Override
    public void onBackPressed() {
        // 取消返回事件，增加开屏曝光率
    }

    /**
     * 跳转到主界面
     */
    private void jumpMain() {
        if (!this.isTaskRoot()) {
            finish();
            return;
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
