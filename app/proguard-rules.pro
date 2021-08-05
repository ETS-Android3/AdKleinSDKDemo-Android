# 指定代码的压缩级别 0 - 7(指定代码进行迭代优化的次数，在Android里面默认是5，这条指令也只有在可以优化时起作用。)
-optimizationpasses 5
# 混淆时不会产生形形色色的类名(混淆时不使用大小写混合类名)
-dontusemixedcaseclassnames
# 指定不去忽略非公共的库类(不跳过library中的非public的类)
-dontskipnonpubliclibraryclasses
# 指定不去忽略包可见的库类的成员
-dontskipnonpubliclibraryclassmembers
#不进行优化，建议使用此选项，
-dontoptimize
 # 不进行预校验,Android不需要,可加快混淆速度。
-dontpreverify
# 屏蔽警告
-ignorewarnings
# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
# 保护代码中的Annotation不被混淆
-keepattributes *Annotation*
# 避免混淆泛型, 这在JSON实体映射时非常重要
-keepattributes Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
 #优化时允许访问并修改有修饰符的类和类的成员，这可以提高优化步骤的结果。
# 比如，当内联一个公共的getter方法时，这也可能需要外地公共访问。
# 虽然java二进制规范不需要这个，要不然有的虚拟机处理这些代码会有问题。当有优化和使用-repackageclasses时才适用。
#指示语：不能用这个指令处理库中的代码，因为有的类和类成员没有设计成public ,而在api中可能变成public
-allowaccessmodification
#当有优化和使用-repackageclasses时才适用。
-repackageclasses 'com.iusmob.adklein.demo'
 # 混淆时记录日志(打印混淆的详细信息)
 # 这句话能够使我们的项目混淆后产生映射文件
 # 包含有类名->混淆后类名的映射关系
-verbose

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

# MobiusAd平台混淆
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

# 芒果广告平台混淆
-keep class android.support.v4.** { public *; }
-keep class android.support.v7.** { public *; }
-keep class * implements java.io.Serializable {*;}
-keep class com.hunantv.media.** { *;}
-keep class com.mgmi.** { *;}
-keep class com.mgadplus.** { *;}
-dontwarn com.hmt.analytics.**
-dontwarn org.apaches.commons.codec.**
-keep class com.hmt.analytics.**{*; }
-keep class org.apaches.commons.codec.**{*; }
# freco
-dontwarn com.facebook.**
-keep enum com.facebook.**
-keep public interface com.facebook.**
-keep class com.facebook.**
-keep class com.facebook.** { *; }
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
 @com.facebook.common.internal.DoNotStrip *;
}
-dontwarn org.eclipse.**
-keep class org.eclipse.** { *;}
-dontwarn com.squareup.**
-keep class com.squareup.** { *;}
-keep public class com.mi.ad.sdk.**{*;}
-keep public class com.doman.core.**{*;}
-keep public class com.core.cell.** {*;}
# android
-keep class android.**{*;}
-keep @interface system.** {*;}
-keepclassmembers class system.**{ public *;}
-dontwarn android.**
-dontwarn com.android.**
-dontwarn system.**
-keep @interface com.core.cell.helper.Keep {*;}
-keep @interface com.android.a.a.**{*;}
-keep class io.reactivex.**{*;}
-keep class com.github.megatronking.stringfog.**{*;}
-keep @interface com.github.megatronking.stringfog.**{*;}
# 华为平台混淆
-keep class com.huawei.openalliance.ad.** { *; }
-dontwarn com.huawei.openalliance.ad.activity.PPSActivity

# mimo 平台混淆
-keep class com.miui.zeus.mimo.sdk.* { *; }
-keep class com.miui.analytics.** { *; }
-keep class com.xiaomi.analytics.* { public protected *; }
-keep class * extends android.os.IInterface{ *; }
