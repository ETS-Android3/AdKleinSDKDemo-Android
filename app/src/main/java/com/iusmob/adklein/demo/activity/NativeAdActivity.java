package com.iusmob.adklein.demo.activity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.iusmob.adklein.ad.AdKleinError;
import com.iusmob.adklein.ad.AdKleinNativeAd;
import com.iusmob.adklein.ad.AdKleinNativeAdData;
import com.iusmob.adklein.ad.AdKleinNativeAdListener;
import com.iusmob.adklein.demo.DemoConstants;
import com.iusmob.adklein.demo.R;
import com.iusmob.adklein.demo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class NativeAdActivity extends AppCompatActivity implements View.OnClickListener,
        AdKleinNativeAdListener {

    private FrameLayout adContainer;
    AdKleinNativeAdData kleinNativeAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        adContainer = findViewById(R.id.ad_container);
        findViewById(R.id.native_ad).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.native_ad:
                adContainer.removeAllViews();
                loadNativeAd();
                break;
        }
    }

    private float getScreenWidth() {
        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        return (float) screenSize.x;
    }

    private AdKleinNativeAd nativeAd;

    private void loadNativeAd() {
        nativeAd = new AdKleinNativeAd(
                this,
                DemoConstants.FEED_NATIVE_ID,
                this,
                getScreenWidth(), 0.0F
        );
        nativeAd.load(1);
    }

    /**
     * 广告拉取成功
     * 获取到广告view后，需要根据data数据进行自渲染
     *
     * @param list 广告加载器对象集合
     */
    @Override
    public void onAdLoaded(List<AdKleinNativeAdData> list) {
        ToastUtils.toast(NativeAdActivity.this, "native ad loaded",
                Toast.LENGTH_SHORT);
        showNativeAd(list.get(0));
    }

    /**
     * 广告关闭回调
     *
     * @param kleinNativeAd 广告数据对象
     */
    @Override
    public void onAdClose(AdKleinNativeAdData kleinNativeAd) {
        ToastUtils.toast(NativeAdActivity.this, "native ad close", Toast.LENGTH_SHORT);
        if (this.kleinNativeAd == kleinNativeAd) {
            adContainer.removeAllViews();
            this.kleinNativeAd = null;
        }
    }

    /**
     * 广告加载失败
     *
     * @param adKleinError 错误描述
     */
    @Override
    public void onError(AdKleinError adKleinError) {
        ToastUtils.toast(NativeAdActivity.this, "native ad error" +
                adKleinError.getErrorCode() + " " + adKleinError.getErrorMsg(), Toast.LENGTH_SHORT);
    }

    /**
     * 广告展示成功
     */
    @Override
    public void onAdShow() {
        ToastUtils.toast(NativeAdActivity.this, "native ad show", Toast.LENGTH_SHORT);
    }

    /**
     * 广告点击回调
     */
    @Override
    public void onAdClicked() {
        ToastUtils.toast(NativeAdActivity.this, "native ad clicked",
                Toast.LENGTH_SHORT);
    }

    private void showNativeAd(AdKleinNativeAdData adKleinNativeAd) {
        this.kleinNativeAd = adKleinNativeAd;
        switch (adKleinNativeAd.getType()) {
            case IMAGE_SINGLE_SMALL:
                renderSmallImage(adKleinNativeAd);
                break;
            case IMAGE_SINGLE_LARGE:
                renderLargeImage(adKleinNativeAd);
                break;
            case IMAGE_THREE_SMALL:
                renderThreeImages(adKleinNativeAd);
                break;
            case VIDEO:
                loadVideo(adKleinNativeAd);
                break;
        }
    }

    private void renderSmallImage(AdKleinNativeAdData kleinNativeAd) {
        View adView = getLayoutInflater().inflate(R.layout.item_native_small_image, adContainer,
                false);
        TextView title = adView.findViewById(R.id.ad_title);
        TextView desc = adView.findViewById(R.id.ad_desc);
        ImageView image = adView.findViewById(R.id.image);
        ImageView ivClose = adView.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(v -> {
            kleinNativeAd.destroy(kleinNativeAd);
        });
        title.setText(kleinNativeAd.getTitle());
        desc.setText(kleinNativeAd.getDesc());
        Glide.with(adView).load(kleinNativeAd.getImages().get(0)).into(image);
        // 绑定view
        List<View> clickViews = new ArrayList<>();
        clickViews.add(adView);
        kleinNativeAd.bindView(kleinNativeAd, adContainer, adView, null, clickViews);
    }

    private void renderLargeImage(AdKleinNativeAdData kleinNativeAd) {
        View adView = getLayoutInflater().inflate(R.layout.item_native_large_image, adContainer,
                false);
        TextView title = adView.findViewById(R.id.ad_title);
        TextView desc = adView.findViewById(R.id.ad_desc);
        ImageView image = adView.findViewById(R.id.image);
        ImageView ivClose = adView.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(v -> {
            kleinNativeAd.destroy(kleinNativeAd);
        });
        title.setText(kleinNativeAd.getTitle());
        desc.setText(kleinNativeAd.getDesc());
        Glide.with(adView).load(kleinNativeAd.getImages().get(0)).into(image);
        // 绑定view
        List<View> clickViews = new ArrayList<>();
        clickViews.add(adView);
        kleinNativeAd.bindView(kleinNativeAd, adContainer, adView, null, clickViews);
    }

    private void renderThreeImages(AdKleinNativeAdData kleinNativeAd) {
        View adView = getLayoutInflater().inflate(R.layout.item_native_three_image, adContainer,
                false);
        TextView title = adView.findViewById(R.id.ad_title);
        TextView desc = adView.findViewById(R.id.ad_desc);
        ImageView image1 = adView.findViewById(R.id.image1);
        ImageView image2 = adView.findViewById(R.id.image2);
        ImageView image3 = adView.findViewById(R.id.image3);
        ImageView ivClose = adView.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(v -> {
            kleinNativeAd.destroy(kleinNativeAd);
        });
        title.setText(kleinNativeAd.getTitle());
        desc.setText(kleinNativeAd.getDesc());
        Glide.with(adView).load(kleinNativeAd.getImages().get(0)).into(image1);
        Glide.with(adView).load(kleinNativeAd.getImages().get(1)).into(image2);
        Glide.with(adView).load(kleinNativeAd.getImages().get(2)).into(image3);
        // 绑定view
        List<View> clickViews = new ArrayList<>();
        clickViews.add(adView);
        kleinNativeAd.bindView(kleinNativeAd, adContainer, adView, null, clickViews);
    }

    private void loadVideo(AdKleinNativeAdData kleinNativeAd) {
        View adView = getLayoutInflater().inflate(R.layout.item_native_video, adContainer, false);
        TextView title = adView.findViewById(R.id.ad_title);
        TextView desc = adView.findViewById(R.id.ad_desc);
        ImageView ivClose = adView.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(v -> {
            kleinNativeAd.destroy(kleinNativeAd);
        });
        title.setText(kleinNativeAd.getTitle());
        desc.setText(kleinNativeAd.getDesc());
        // MediaView container
        ViewGroup video_wrapper = adView.findViewById(R.id.video_wrapper);
        // 绑定view
        List<View> clickViews = new ArrayList<>();
        clickViews.add(adView);
        kleinNativeAd.bindView(kleinNativeAd, adContainer, adView, video_wrapper, clickViews);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (kleinNativeAd != null) {
            adContainer.removeAllViews();
            kleinNativeAd.destroy(kleinNativeAd);
            kleinNativeAd = null;
        }
    }
}
