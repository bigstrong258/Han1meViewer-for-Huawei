# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keepattributes SourceFile, LineNumberTable

-keepnames class * extends android.app.Activity
-keepnames class * extends androidx.fragment.app.Fragment

-keep class * extends cn.jzvd.** { *; }

-keep class com.google.android.gms.** { *; }
-keep interface com.google.android.gms.** { *; }
-keep class androidx.appcompat.view.** { *; }
-keep class androidx.window.extensions.embedding.** { *; }
-keep class is.xyz.mpv.** { *; }
-keep class lis.xyz.mpv.** { *; }

# 1. 保护 Norman 的库全家桶，防止类名和方法名被混淆导致反射失败
-keep class com.norman.webviewup.lib.** { *; }

# 2. 保护你自己的工具类
-keep class com.yenaly.han1meviewer.util.WebViewUpgradeUtil { *; }

# 3. 保护 Android 系统 WebView 相关内部类（必须保留，库需要 Hook 这些类）
-keep class android.webkit.** { *; }
-keep class com.android.webview.** { *; }

# 4. 保留反射需要的元数据
-keepattributes Signature, *Annotation*, EnclosingMethod, SourceFile, LineNumberTable