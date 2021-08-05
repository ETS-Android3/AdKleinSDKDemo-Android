# AdKleinSDK Android SDK——接入文档

## 概述

尊敬的开发者朋友，欢迎您使用AdKlein广告聚合SDK。通过本文档，您可以快速完成多平台广告SDK的集成。

**此版本sdk不适用于中国以外的安卓商店/渠道。开发者如果需要上架Google Play，建议不集成AdKlein广告聚合SDK，单独做一个渠道包给Google Play用。**

### AdKlein广告聚合SDK组成结构

AdKlein广告聚合SDK主要由**AdKlein核心SDK**（简称AdKleinSDK）和**一个或多个**三方广告平台SDK构成。

### 三方广告平台名称概述

| Name      | 平台名称   | 平台别称 |
| --------- | -------- | -------- |
| mobius    | 莫比乌斯   | 莫比乌斯 |
| gdt       | 广点通   | 优量汇   |
| toutiao   | 头条     | 穿山甲   |
| baidu     | 百度     | 百度   |
| admob     | AdMob    | 谷歌   |
| smaato    | Smaato   | 誉广   |
| hwpps    | 华为广告联盟   | 华为广告联盟   |
| mimo    | 米盟   | 米盟   |

### 必填包容量
| Name      |  大小   |
| --------- | -------- |
| mobius基础包      |  0.26M  |
| oaid     |  2.18M  |

### 三方广告平台适配器+三方广告sdk总容量
| Name      |  大小   |
| --------- | -------- |
| gdt       |  1.43M  |
| toutiao   |  2.73M  |
| mobiusAd  |  0.143M |
| baidu     |  1.04M  |
|  admob    |  1.20M  |
|  smaato   |  1.25M  |
|  hwpps    |  1.35M  |
|  mimo     |  0.60M  |

## 支持的广告类型

<table>
  <tr>
    <th style="width:150px">类型</th>
    <th>简介</th>
    <th>适用场景</th>
  </tr>
  <tr>
    <td><a href="#ad_splash">开屏广告</a></td>
    <td>开屏广告以APP启动作为曝光时机的模板广告，需要将开屏广告视图添加到承载的广告容器中，提供5s可感知广告展示</td>
    <td>APP启动界面常会使用开屏广告</td>
  </tr>
  <tr>
      <td><a href="#ad_native">信息流广告</a></td>
      <td>信息流广告集合原生自渲染广告和模板广告两种，可以通过后台配置和SDK相关方法判断进行不同的渲染，以满足不同的样式需求</td>
      <td>信息流列表，轮播控件，固定位置都是较为适合</td>
    </tr>
  <tr>
    <td><a href="#ad_banner">Banner广告</a></td>
    <td>Banner广告是横向贯穿整个可视页面的模板广告，需要将Banner广告视图添加到承载的广告容器中</td>
    <td>任意界面的固定位置，不建议放在RecyclerView、List这种滚动布局中当item</td>
  </tr>
   <tr>
    <td><a href="#ad_reward_video">激励视频广告</a></td>
    <td>将短视频融入到APP场景当中，用户观看短视频广告后可以给予一些应用内奖励</td>
    <td>常出现在游戏的复活、任务等位置，或者网服类APP的一些增值服务场景</td>
  </tr>
   <tr>
    <td><a href="#ad_fullscreen_video">全屏视频广告</a></td>
    <td>类似激励视频，与激励视频不同的是，全屏视频广告在观看一定时长后即可跳过广告，无需全部观看完成，有视频跳过回调，但是没有激励回调</td>
    <td>常出现在游戏的复活、任务等位置，或者网服类APP的一些增值服务场景</td>
  </tr>
   <tr>
    <td><a href="#ad_interstitial">插屏广告</a></td>
    <td>插屏广告是移动广告的一种常见形式，在应用流程中弹出，当应用展示插屏广告时，用户可以选择点击广告，访问其目标网址，也可以将其关闭并返回应用</td>
    <td>在应用执行流程的自然停顿点，适合投放这类广告</td>
  </tr>
  <tr>
    <td><a href="#ad_draw">Draw广告</a></td>
    <td>Draw广告是竖屏视频列表中插入的一种竖屏信息流⼴告</td>
    <td>该⼴告样式适⽤于全屏的竖屏视频中使⽤</td>
  </tr>
</table>


## Demo及SDK下载链接

在管理后台的接入中心下载

## SDK接入流程

### 添加SDK到工程中

接入环境：**Android Studio**。

### 添加仓库地址

在项目的build.gradle文件中引入如下配置：

```java
allprojects {
    repositories {
        google()
        jcenter()
        //smaato广告平台仓库,如不接入可以不添加
        maven { url "https://s3.amazonaws.com/smaato-sdk-releases/" }
        //华为广告联盟仓库,如不接入可以不添加
        maven { url 'http://developer.huawei.com/repo/' }
    }
}
```

#### 添加AdKleinSDK和需要的AdapterSdk

将广告所需要的依赖集成进去，AdapterSdk可根据接入平台情况进行选择接入。

```java
dependencies {
    // 至少接入一个平台，但不推荐全部接入。如果不清楚需要哪些平台可以咨询媒介。

    // OAID(必须)
    implementation(name: 'msa_mdid_1.0.23', ext: 'aar')

    // 莫比乌斯SDK(必须)
	implementation 'com.squareup.okhttp3:okhttp:3.12.1'
    implementation(name: 'adKlein_core_3.0.1', ext: 'aar')

    // 头条，推荐接入
    implementation(name: 'adKlein_adapter_toutiao_2.0.1', ext: 'aar')
    implementation(name: 'open_ad_sdk_3.1.0.3', ext: 'aar')

    // 广点通，推荐接入
    implementation(name: 'adKlein_adapter_gdt_2.0.1', ext: 'aar')
    implementation(name: 'GDTSDK.unionNormal.4.362.1232', ext: 'aar')

    // 莫比乌斯，推荐接入(还需要gson和glide支持)
    implementation(name: 'mobius_ad_2.0.0', ext: 'aar')

    // 百度AdapterSDK，可选的
    implementation(name: 'adKlein_adapter_bd_2.0.1', ext: 'aar')
    implementation(name: 'Baidu_MobAds_SDK', ext: 'aar')

    // AdMobAdapterSdk，可选的（海外市场）
    implementation(name: 'adKlein_adapter_am_2.0.0', ext: 'aar')
    implementation 'com.google.android.gms:play-services-ads:19.7.0'

    // SmaatoAdapterSdk，可选的（海外市场）
    implementation(name: 'adKlein_adapter_sm_2.0.1', ext: 'aar')
    implementation 'com.smaato.android.sdk:smaato-sdk:21.5.7'
    implementation 'com.smaato.android.sdk:smaato-sdk-banner:21.5.7'
    implementation 'com.smaato.android.sdk:smaato-sdk-interstitial:21.5.7'
    implementation 'com.smaato.android.sdk:smaato-sdk-rewarded-ads:21.5.7'
    implementation 'com.smaato.android.sdk:smaato-sdk-native:21.5.7'

    //华为AdapterSDK，可选的(还需要glide支持)
    implementation(name: 'adKlein_adapter_hw_2.0.0', ext: 'aar')
    implementation 'com.huawei.hms:ads-extra:13.4.34.300'
    implementation(name: 'ads-banner-inner-13.4.40.302', ext: 'aar')
    implementation(name: 'ads-base-inner-13.4.40.302', ext: 'aar')
    implementation(name: 'ads-instream-inner-13.4.40.302', ext: 'aar')
    implementation(name: 'ads-interstitial-inner-13.4.40.302', ext: 'aar')
    implementation(name: 'ads-lang-inner-13.4.40.302', ext: 'aar')
    implementation(name: 'ads-native-inner-13.4.40.302', ext: 'aar')
    implementation(name: 'ads-reward-inner-13.4.40.302', ext: 'aar')
    implementation(name: 'ads-splash-inner-13.4.40.302', ext: 'aar')
    implementation(name: 'ads-template-inner-13.4.40.302', ext: 'aar')

    //米盟AdapterSDK，可选的（还需要gson和glide支持）
    implementation(name: 'adKlein_adapter_mimo_2.0.1', ext: 'aar')
    implementation(name: 'mimo-ad-sdk', ext: 'aar')
}
```

#### 注意事项

支持主流架构，x86架构暂不支持

```java
ndk {
    // 设置支持的SO库架构，暂不支持x86
    abiFilters 'armeabi-v7a' // 'armeabi', 'arm64-v8a'
}
```

**AdKleinSDK默认已经集成了三方的广告SDK**，如果因为项目中也使用了相同的三方广告SDK而发生冲突，可通过以下方法尝试避免或解决；

1.移除己方使用的三方广告SDK和相关配置；

2.激励、全屏视频、插屏等广告对象一次成功拉取的广告数据只允许展示一次，还需展示请再次加载广告。

* 如果要对接芒果SDK，需要导入java8配置
```java
 compileOptions {
     sourceCompatibility JavaVersion.VERSION_1_8
     targetCompatibility JavaVersion.VERSION_1_8
 }
```
* 如对接华为广告联盟，激励视频要提前预加载，并且播放完成后需要预加载下一个激励视频；banner广告使用场景是程序页面的顶部或者底部；

### OAID支持

Android10之后IMEI等数据无法获取，这对广告投放将产生一定影响，所以移动安全联盟(MSA)提出OAID来代替IMEI参与广告投放决策，OAID的支持会在一定程度上影响广告收益；**OAID是必须集成项，没有集成将会抛出异常提醒开发者**，OAID集成并不繁琐，SDK中已经进行了OAID的封装，只需以下几步即可完成OAID的支持；

1.导入安全联盟的OAID支持库 **msa_mdid_1.0.23.aar**，可在Demo的libs目录下找到，**强烈建议使用和Demo中一样版本的OAID库**；

2.将Demo中assets文件夹下的**supplierconfig.json**文件复制到自己的assets目录下并按照**supplierconfig.json**文件中的说明进行OAID的 **AppId** 配置，**supplierconfig.json**文件名不可修改；

3.添加以下混淆配置；

```java
-keep class XI.CA.XI.**{*;}
-keep class XI.K0.XI.**{*;}
-keep class XI.XI.K0.**{*;}
-keep class XI.vs.K0.**{*;}
-keep class XI.xo.XI.XI.**{*;}
-keep class com.asus.msa.SupplementaryDID.**{*;}
-keep class com.asus.msa.sdid.**{*;}
-keep class com.bun.lib.**{*;}
-keep class com.bun.miitmdid.**{*;}
-keep class com.huawei.hms.ads.identifier.**{*;}
-keep class com.samsung.android.deviceidservice.**{*;}
-keep class org.json.**{*;}
-keep public class com.netease.nis.sdkwrapper.Utils {public <methods>;}
```

**需要更多帮助可参考目录下《移动智能终端补充设备标识体系统一调用SDK开发者说明文档》；**

### 权限申请

使用SDK时可能需要以下权限，为了保证使用广告的正确，请在6.0及以上的手机中使用SDK前及时申请。

```java
<!-- 广告必须的权限，AAR中已经内置 -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<!-- 如果有视频相关的广告播放请务必添加 -->
<uses-permission android:name="android.permission.WAKE_LOCK" />
```

### 兼容配置

#### FileProvider配置

1.适配Android 7.0以及以上，请在AndroidManifest中添加如下代码（3.x.x以前版本暂时可不添加此FileProvider）：

* 非androidx

```java
<provider
    android:name="android.support.v4.content.FileProvider"
    android:authorities="${applicationId}.fileprovider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/adsaas_file_paths" />
</provider>
```

* androidx

```java
<provider
    android:name="androidx.core.content.FileProvider"
    android:authorities="${applicationId}.fileprovider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/adsaas_file_paths" />
</provider>
```

如果出现FileProvider冲突的问题，可以自己实现一个FileProvider的子类，然后使用子类即可解决冲突。

2.在res/xml目录下(如果xml目录不存在需要手动创建)，新建xml文件adklein_file_paths，在该文件中加入如下配置，如果存在相同android:authorities的provider，请将paths标签中的路劲配置到自己的xml文件中：

```java
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <external-path name="external_path" path="." />
    <external-files-path name="external_files_path" path="." />
</paths>
```

**为了适配下载和安装相关功能，在工程中引用的包 com.android.support:support-v4:x.x.x请使用26.0.0及以上版本。**

#### 媒体配置

需要在 AndroidManifest.xml 添加谷歌Admob ca-app-pub配置，**如果没有接入Admob则不用进行此配置**

```java
<application>

   <meta-data
            android:name="com.google.android.gms.ads.APPLICATION_ID"
            android:value="ca-app-pub-3940256099942544~3347511713" />
</application>
```

#### 网络配置

需要在 AndroidManifest.xml 添加依赖声明**uses-library android:name="org.apache.http.legacy" android:required="false"**， 且 application标签中添加 **android:usesCleartextTraffic="true"**，适配网络http请求，否则 SDK可能无法正常工作，接入代码示例如下：

```java
<application
    android:name=".MyApplication"
    ......
    android:usesCleartextTraffic="true">

    <uses-library
        android:name="org.apache.http.legacy"
        android:required="false" />
    ......
</application>
```

#### 混淆配置

如果打包时开启了混淆配置，请按需添加以下混淆内容，并保证广告资源文件不被混淆

```java
# OAID混淆
-keep class XI.CA.XI.**{*;}
-keep class XI.K0.XI.**{*;}
-keep class XI.XI.K0.**{*;}
-keep class XI.vs.K0.**{*;}
-keep class XI.xo.XI.XI.**{*;}
-keep class com.asus.msa.SupplementaryDID.**{*;}
-keep class com.asus.msa.sdid.**{*;}
-keep class com.bun.lib.**{*;}
-keep class com.bun.miitmdid.**{*;}
-keep class com.huawei.hms.ads.identifier.**{*;}
-keep class com.samsung.android.deviceidservice.**{*;}
-keep class org.json.**{*;}
-keep public class com.netease.nis.sdkwrapper.Utils {public <methods>;}

# 广点通广告平台混淆
-keep class com.qq.e.** {public protected *;}
-keep class MTT.ThirdAppInfoNew {*;}
-keep class com.tencent.** {*;}

# 头条广告平台混淆
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
-keep class com.pgl.sys.ces.* {*;}

# 莫比乌斯平台混淆
-keepattributes *Annotation*
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName *;
}

# 百度广告平台混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class com.baidu.mobads.** { *; }
-keep class com.baidu.mobad.** { *; }
-keep class com.bun.miitmdid.core.** {*;}

# Smaato广告平台混淆
-keep public class com.smaato.sdk.** { *; }
-keep public interface com.smaato.sdk.** { *; }

# 华为平台混淆
-keep class com.huawei.openalliance.ad.** { *; }
-dontwarn com.huawei.openalliance.ad.activity.PPSActivity
# mimo 平台混淆
-keep class com.miui.zeus.mimo.sdk.* { *; }
-keep class com.miui.analytics.** { *; }
-keep class com.xiaomi.analytics.* { public protected *; }
-keep class * extends android.os.IInterface{ *; }

```

## 示例代码

### SDK初始化

在Application中进行SDK的初始化

```java
// 初始化AdKleinSDK
AdKleinSDK.init(this, "mediaId", "是否开启Debug，开启会有详细的日志信息打印", "初始化成功回调接口");
SDK日志的关键字为AdKleinSDK
```

**mediaId通过后台配置生成，初始化必须在主线程中进行，SDK暂不支持多进程。**

### <a name="ad_splash">开屏广告示例</a>

开屏广告建议在闪屏页进行展示，开屏广告的宽度和高度取决于容器的宽高，都是会撑满广告容器；**开屏广告的高度必须大于等于屏幕高度（手机屏幕完整高度，包括状态栏之类）的75%**，否则可能会影响收益计费（广点通的开屏甚至会影响跳过按钮的回调）。

```java
public class SplashAdActivity extends Activity implements View.OnClickListener {
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
    private SwitchCompat switchBottom;
    private FrameLayout adContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_ad);
        tvSkip = findViewById(R.id.tv_skip);
        adContainer = findViewById(R.id.ad_container);
        clButton = findViewById(R.id.cl_button);
        switchJump = findViewById(R.id.switch_jump);
        switchBottom = findViewById(R.id.switch_bottom_view);
        findViewById(R.id.splash_ad).setOnClickListener(this);
        boolean splashAd = getIntent().getBooleanExtra("SplashAd", false);
        if (splashAd) {
            clButton.setVisibility(View.VISIBLE);
        } else {
            loadSplashAd();
        }
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
        if (switchBottom.isChecked()) {
            findViewById(R.id.relativeLayout).setVisibility(View.VISIBLE);
        }

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
        //设置请求广告为测试环境(华为)
        kleinSplash.setDev(true);

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
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
```
#### 开屏广告优化建议

1. 第一步加载一个白底，带logo的图

2. 第二步给用户看隐私协议，点击同意

3. app做获取权限，获取相应权限后请求广告

4. 第四步还是 让用户看那个白底logo的图，等待2-3s加载广告

5. 第五步放开机引导页

### <a name="ad_banner">横幅广告示例</a>

Banner横幅广告建议放置在 **固定位置**，而非ListView、RecyclerView、ViewPager等控件中充当Item，Banner广告支持多种尺寸比例，可在后台创建广告位时配置，Banner广告的宽度将会撑满容器，高度自适应，建议Banner广告容器高度也为自适应。

```java
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

// 加载banner广告
kleinBanner.load();

// 展示banner广告
kleinBanner.show()
```

### <a name="ad_native">信息流广告示例</a>

信息流广告，具备自渲染和模板两种广告样式：自渲染是SDK将返回广告标题、描述、Icon、图片、多媒体视图等信息，开发者可通过自行拼装渲染成喜欢的样式；模板样式则是返回拼装好的广告视图，开发者只需将视图添加到相应容器即可，模板样式的容器高度建议是自适应。

#### 自渲染

自渲染广告实体类是AdKleinNativeAd，使用时先创建AdKleinNativeAd并且绑定AdKleinNativeAdListener，然后调用load(int adCount)加载广告，广告加载和展示点击等通过AdKleinNativeAdListener回调，下面是示例代码。

广告View的绑定需要调用`AdKleinNativeAdData.bindView(AdKleinNativeAdData adKleinNativeAdData, ViewGroup adContainer, View adView, ViewGroup mediaViewContainer, List\<View> clickViews)`。其中各个参数：`adKleinNativeAdData`是返回的广告实例，`adContainer`是放置广告view的ViewGroup，`adView`是渲染广告的View，`mediaViewContainer`是放置饰品广告的ViewGroup，`clickViews`是广告点击区域的view。

```java
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

    @Override
    public void onAdLoaded(List<AdKleinNativeAdData> list) {
        ToastUtils.toast(NativeAdActivity.this, "feed native ad loaded",
                Toast.LENGTH_SHORT);
        showNativeAd(list.get(0));
    }

    @Override
    public void onAdClose(AdKleinNativeAdData kleinNativeAd) {
        ToastUtils.toast(NativeAdActivity.this, "feed ad close", Toast.LENGTH_SHORT);
        if (this.kleinNativeAd == kleinNativeAd) {
            adContainer.removeAllViews();
            this.kleinNativeAd = null;
        }
    }

    @Override
    public void onError(AdKleinError adKleinError) {
        ToastUtils.toast(NativeAdActivity.this, "feed ad error" +
                adKleinError.getErrorCode() + " " + adKleinError.getErrorMsg(), Toast.LENGTH_SHORT);
    }

    @Override
    public void onAdShow() {
        ToastUtils.toast(NativeAdActivity.this, "feed ad show", Toast.LENGTH_SHORT);
    }

    @Override
    public void onAdClicked() {
        ToastUtils.toast(NativeAdActivity.this, "feed ad clicked",
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
        View adView = getLayoutInflater().inflate(R.layout.item_feed_small_image, adContainer,
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
        View adView = getLayoutInflater().inflate(R.layout.item_feed_large_image, adContainer,
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
        View adView = getLayoutInflater().inflate(R.layout.item_feed_three_image, adContainer,
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
        View adView = getLayoutInflater().inflate(R.layout.item_feed_video, adContainer, false);
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
```

#### 模版渲染

模版渲染广告实体类是AdKleinNativeExpressAd，使用时先创建AdKleinNativeExpressAd并且绑定AdKleinNativeExpressAdListener，然后调用load(int adCount)加载广告，广告加载和展示点击等通过AdKleinNativeExpressAdListener回调，下面是示例代码。

```java
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
        ToastUtils.toast(NativeExpressAdActivity.this, "feed express ad loaded",
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
        ToastUtils.toast(NativeExpressAdActivity.this, "feed express ad render fail" +
                        " " + adKleinError.getErrorMsg() + " " + adKleinError.getErrorCode(),
                Toast.LENGTH_SHORT);
    }

    @Override
    public void onAdClose(AdKleinNativeExpressAdData kleinNativeExpressAd) {
        ToastUtils.toast(NativeExpressAdActivity.this, "feed express ad close", Toast.LENGTH_SHORT);
        adContainer.removeAllViews();
    }

    @Override
    public void onError(AdKleinError adKleinError) {
        ToastUtils.toast(NativeExpressAdActivity.this, "feed express ad error" +
                adKleinError.getErrorCode() + " " + adKleinError.getErrorMsg(), Toast.LENGTH_SHORT);
    }

    @Override
    public void onAdShow() {
        ToastUtils.toast(NativeExpressAdActivity.this, "feed express ad show", Toast.LENGTH_SHORT);
    }

    @Override
    public void onAdClicked() {
        ToastUtils.toast(NativeExpressAdActivity.this, "feed express ad clicked",
                Toast.LENGTH_SHORT);
    }

}
```

### <a name="ad_reward_video">激励视频广告示例</a>

将短视频融入到APP场景当中，用户观看短视频广告后可以给予一些应用内奖励。

```java
rewardVideo = new AdKleinRewardVideoAd(
                this, DemoConstants.REWARD_VIDEO_ID,
                0, true,
                new AdKleinRewardVideoAdListener() {
                    @Override
                    public void onAdClose() {
                        ToastUtils.toast(RewardVideoAdActivity.this, "reward onAdClose",
                                Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onVideoComplete() {
                        ToastUtils.toast(RewardVideoAdActivity.this, "reward onVideoComplete",
                                Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onSkippedVideo() {
                        ToastUtils.toast(RewardVideoAdActivity.this, "reward onSkippedVideo",
                                Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onAdReward() {
                        ToastUtils.toast(RewardVideoAdActivity.this, "reward onAdReward",
                                Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onError(AdKleinError adKleinError) {
                        ToastUtils.toast(RewardVideoAdActivity.this,
                                "reward onError " + adKleinError.getErrorCode() + " " + adKleinError.getErrorMsg(),
                                Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onAdLoaded() {
                        ToastUtils.toast(RewardVideoAdActivity.this, "reward onAdLoaded",
                                Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onAdShow() {
                        ToastUtils.toast(RewardVideoAdActivity.this, "reward onAdShow",
                                Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onAdClicked() {
                        ToastUtils.toast(RewardVideoAdActivity.this, "reward onAdClicked",
                                Toast.LENGTH_SHORT);
                    }
                }
        );

//设置华为广告为测试环境
rewardVideo.setDev(true);

// 加载激励视频
rewardVideo.load();

// 展示激励视频
rewardVideo.show();
```

### <a name="ad_fullscreen_video">全屏视频广告示例</a>

全屏视频广告是类似激励视频样式的广告形式，与激励视频不同之处在于全屏视频广告播放一定时间时间后即可跳过，同时全屏视频广告拥有跳过回调不具备奖励回调。

```java
fullscreenVideo = new AdKleinFullscreenVideoAd(
                this, DemoConstants.FULLSCREEN_ID, 1, false,
                new AdKleinFullscreenVideoAdListener() {
                    @Override
                    public void onAdClose() {
                        ToastUtils.toast(FullScreenVideoAdActivity.this, "fullscreen onAdClose",
                                Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onVideoComplete() {
                        ToastUtils.toast(FullScreenVideoAdActivity.this, "fullscreen " +
                                "onVideoComplete", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onSkippedVideo() {
                        ToastUtils.toast(FullScreenVideoAdActivity.this, "fullscreen " +
                                "onSkippedVideo", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onError(AdKleinError adKleinError) {
                        ToastUtils.toast(FullScreenVideoAdActivity.this,
                                "fullscreen onError " + adKleinError.getErrorCode() + " " + adKleinError.getErrorMsg(), Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onAdLoaded() {
                        ToastUtils.toast(FullScreenVideoAdActivity.this, "fullscreen onAdLoaded",
                                Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onAdShow() {
                        ToastUtils.toast(FullScreenVideoAdActivity.this, "fullscreen onAdShow",
                                Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onAdClicked() {
                        ToastUtils.toast(FullScreenVideoAdActivity.this, "fullscreen onAdClicked"
                                , Toast.LENGTH_SHORT);
                    }
                }
        );

// 加载全屏视频
fullscreenVideo.load();

// 展示全屏视频
fullscreenVideo.show();
```

### <a name="ad_interstitial">插屏广告示例</a>

插屏广告是移动广告的一种常见形式，在应用流程中弹出，当应用展示插屏广告时，用户可以选择点击广告，也可以将其关闭并返回应用。

```java
kleinInterstitial = new AdKleinInterstitialAd(
                this, DemoConstants.INTERSTITIAL_ID,
                new AdKleinInterstitialAdListener() {
                    @Override
                    public void onAdClosed() {
                        ToastUtils.toast(InterstitialAdActivity.this, "interstitial onAdClosed",
                                Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onRenderFail(AdKleinError adKleinError) {
                        ToastUtils.toast(InterstitialAdActivity.this, "interstitial onRenderFail "
                                        + adKleinError.getErrorCode() + " " + adKleinError.getErrorMsg()
                                , Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onRenderSuccess() {
                        ToastUtils.toast(InterstitialAdActivity.this, "interstitial " +
                                "onRenderSuccess", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onError(AdKleinError adKleinError) {
                        ToastUtils.toast(InterstitialAdActivity.this,
                                "interstitial onError " + adKleinError.getErrorCode() + " " + adKleinError.getErrorMsg(), Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onAdLoaded() {
                        ToastUtils.toast(InterstitialAdActivity.this, "interstitial onAdLoaded",
                                Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onAdShow() {
                        ToastUtils.toast(InterstitialAdActivity.this, "interstitial onAdShow",
                                Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onAdClicked() {
                        ToastUtils.toast(InterstitialAdActivity.this, "interstitial onAdClicked",
                                Toast.LENGTH_SHORT);
                    }
                }, (int) (UIUtils.getScreenWidthDp(this) * 3 / 4), UIUtils.getScreenWidthDp(this)
        );

// 加载插屏广告
kleinInterstitial.load();

// 展示插屏广告
kleinInterstitial.show();
```

### <a name="ad_draw">Draw广告示例</a>

适合在竖版全屏视频流中使用，接入方可以控制视频暂停或继续播放，默认视频播放不可干预。

```java
kleinDraw = new AdKleinDrawAd(new AdKleinDrawAdListener() {
                    @Override
                    public void onAdLoaded(List<AdKleinDrawVodAdListener> list) {
                        for (int i = 0; i < list.size(); i++) {
                            int index = i * 3;
                            AdKleinDrawVodAdListener data = list.get(i);
                            if (index <= dataList.size()) {
                                dataList.add(index, new DrawVodAdBean(data));
                            } else {
                                dataList.add(new DrawVodAdBean(data));
                            }
                        }
                        mAdapter.addData(dataList);
                        refreshLayout.finish(refreshType, true, false);
                    }

                    @Override
                    public void onError(final AdKleinError adKleinError) {
                        ToastUtils.toast(this, "draw ad error" +
                                adKleinError.getErrorCode() + " " + adKleinError.getErrorMsg(), Toast.LENGTH_SHORT);
                        refreshLayout.finish(refreshType, false, false);
                    }

                    @Override
                    public void onAdShow() {
                        ToastUtils.toast(this, "draw ad show", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onAdClicked() {
                        ToastUtils.toast(this, "draw ad clicked",
                                Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onRenderSuccess(final View view, final float v, final float v1) {
                    }

                    @Override
                    public void onRenderFail(final AdKleinError adKleinError) {
                        ToastUtils.toast(this, "draw ad render fail" +
                                        " " + adKleinError.getErrorMsg() + " " + adKleinError.getErrorCode(),
                                Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onAdClose() {
                        ToastUtils.toast(this, "draw ad close", Toast.LENGTH_SHORT);
                    }
                },
                DemoConstants.DRAW_ID,
                this,
                getScreenWidth(),
                0.0F);

// 请求广告数据，参数为请求数量[1,3]
kleinDraw.load(DemoConstants.AD_COUNT);
```

## Android集成常见问题

### 通用问题

#### 用demo测试时可以拉到广告，用正式广告位id为什么拉不到广告？

A:查看apk签名是否与媒体平台的签名一致，可能在demo测试时更改了包名使得应用签名不一致导致无法正常拉取广告。

#### sdk最低支持到多少？

A：sdk目前最低支持到17。

#### 错误码6000，详细码102006？

a、两个错误均为常规报错，没有广告填充，非sdk接入异常。

b、广告位请求匹配广告本身是一套复杂的逻辑，有人群画像、广告位效果、底价、屏蔽等多方面原因。

c、如果是新接入测试阶段新建广告位通常原因是缺乏历史数据模型，广告位竞争力差并且设备画像简陋导致匹配不到广告。

优化建议：

1、如果使用的是测试机，可以更换使用人群画像更丰富的设备尝试拉取（比如个人设备）或者在设备登录QQ，微信等丰富用户画像；

#### 错误码5010，广告样式校验失败，请检查广告位与接口使用是否一致？

A：广告样式不匹配，检查后台广告位配置是否与接入的样式一致

#### 能否设置APP广告下载二次弹窗确认？

A：我们提供开启下载提示框的配置，提供【都不提示】、【仅移动网络提示】两个选项，默认为都不提供二次确认。如需进行二次确认弹窗提示，请联系运营申请，一般在1-2个工作日内完成。

广告下载二次确认对于用户体验相对更好，但是同时，由于用户能够对下载广告进行确认，容易导致后续转化率下降，广告效果变差，会引起收益降低，建议开发者进行权衡。

注意：如果是担心广告无提示下载导致华为等应用市场无法过审，可以在【应用管理】的【应用配置】处对送审版本进行控制，指定上架版本不展示广告，过审后再进行展示。

#### 广告请求报错100133是什么意思？

A：广告位不可用，您可以从下面几点排查

1.广告位ID使用是否正确

2.广告位是否开启状态（中途是否有开关操作，有开关操作需要等待半小时后使用）

3.广告位是否是新建的（新建广告位10分钟后生效）

#### 为什么会有重复广告？

AdKleinSDK接入广告属于实时竞价，广告会根据当前展示环境匹配符合的广告进行拉取和展示。 因此可能会出现重复广告。

#### 下载SDK以后只看到两个aar，剩下的到哪里获取？

A：剩余的aar可以从demo的libs里面获取

### 开屏广告常见问题

#### 开屏广告的容器需要注意什么？

**开屏广告的高度必须大于等于屏幕高度（手机屏幕完整高度，包括状态栏）的75%**，否则可能会影响收益计费（广点通的开屏甚至会影响跳过按钮的回调）。

#### 开屏可以自定义底部logo么？

A：可以的，具体参考SDKdemo的开屏示例代码

### 横幅广告常见问题

#### Banner广告的尺寸问题？

A：Banner广告支持多种尺寸比例，可在后台创建广告位时配置，Banner广告的宽度将会撑满容器，高度自适应，建议Banner广告容器高度也为自适应。

#### banner横幅广告的建议位置

A：Banner横幅广告建议放置在 **固定位置**，而非ListView、RecyclerView、ViewPager等控件中充当Item。

### 信息流广告常见问题

#### 信息流广告为什么区分原生渲染和模板两种类型？

A : 不同平台的支持不太一样，有的仅支持原生，有的仅支持模板，合并在一起开发者可以更自由的对接，如果只需要原生渲染，后台只配置原生的即可，如果只需要模板也是同理。

#### 信息流广告如何区分是原生渲染还是模板？

A：自渲染广告实体类是AdKleinNativeAd，使用时先创建AdKleinNativeAd并且绑定AdKleinNativeAdListener，然后调用load(int adCount)加载广告，广告加载和展示点击等通过AdKleinNativeAdListener回调；

模版渲染广告实体类是AdKleinNativeExpressAd，使用时先创建AdKleinNativeExpressAd并且绑定AdKleinNativeExpressAdListener，然后调用load(int adCount)加载广告，广告加载和展示点击等通过AdKleinNativeExpressAdListener回调

### 激励视频、全屏视频、插屏广告常见问题

#### 激励视频、全屏视频、插屏广告有什么区别？

A:**激励视频广告**是播放一段视频，播放完成或其他特定情况下（不同平台有所差异）会给予激励回调，激励视频一般是需要播放完成后才能退出播放界面；**全屏视频广告**类似激励视频，但是全屏视频没有激励回调，全屏视频一般在播放一段时间后即可退出当前播放界面；**插屏广告**一般是以弹窗的形式展示图文或视频，也有部分平台是单独的全屏界面进行展示；


## Android SDK错误码

| 错误码 |               描述               | 排查方向                                                     |
| ------ | :------------------------------: | ------------------------------------------------------------ |
| -1001    |           广告初始化失败            |                                                              |
| -2001    |            必须在主线程获取广告            |                      |
| -2002    |          广告容器不能为空          |                                                              |
| -2003    |   Activity为空或者Activity已被释放   |                                                              |
| -2100    |   请求超时    |                                                              |
| -2101    |          PlaceId不能为空          |                                                              |
| -2102    |      广告配置未加载完成或加载出现错误      |    |
| -2103    |        无填充        |                                                              |
| -2104    |         所有三方广告位均没有获取到广告，请查看三方Adapter失败原因         |          查看是否有错误日志输出|                                                    |
| -2105   |              广告来源不支持       |
| -2106   |   广告类型不支持  |
| -2107   |   内部广告加载错误  |
| -2108   |   视频广告播放错误  |
| -2109   |   广告内容过期  |
| -2110   |   广告渲染失败  |
| -2111   |   视频广告渲染失败  |
| -2112   |   视频广告失效或已过期  |
|   -1    |     其他错误信息(如服务器返回错误码)         |  |

## 更新日志

| 版本号  |    日期    | 更新日志                                                     |
| ------- | :--------: | ------------------------------------------------------------ |
| v3.0.1  | 2021-06-28 | 3.0全新发布，产品更名为AdKleinSDK，2.x版本用户请注意接入代码更新；修复已知问题；         |
