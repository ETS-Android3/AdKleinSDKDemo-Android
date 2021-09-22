package com.iusmob.adklein.demo;

import android.app.Application;

import com.iusmob.adklein.ad.AdKleinInitConfig;
import com.iusmob.adklein.ad.AdKleinSDK;
import com.tencent.bugly.crashreport.CrashReport;


public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 添加bugly初始化（该初始化与广告SDK无关，广告SDK中不包含bugly相关内容，仅供Demo错误信息收集使用）
        CrashReport.initCrashReport(getApplicationContext(), "7dc75070a3", true);

        AdKleinSDK.init(this, new AdKleinInitConfig.Builder()
                .mediaId(DemoConstants.MEDIA_ID)
                .deBug(BuildConfig.DEBUG)
                .build());
    }
}
