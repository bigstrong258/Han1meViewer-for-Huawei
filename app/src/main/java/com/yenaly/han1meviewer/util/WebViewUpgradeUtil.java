package com.yenaly.han1meviewer.util;

import static com.yenaly.yenaly_libs.utils.ContextUtil.getApplicationContext;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.norman.webviewup.lib.UpgradeCallback;
import com.norman.webviewup.lib.WebViewUpgrade;
import com.norman.webviewup.lib.source.UpgradeAssetSource;
import com.norman.webviewup.lib.source.UpgradeSource;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebViewUpgradeUtil {


    private static final Map<String, List<UpgradeInfo>> UPGRADE_PACKAGE_MAP = new HashMap<>();

    static {
        UPGRADE_PACKAGE_MAP.put("x86", List.of(
                new UpgradeInfo("com.google.android.webview",
                        "146.0.7680.115",
                        "com.google.android.webview.mp3",
                        "内置")));
    }


    public void Upgrade(Context context) {

        WebViewUpgrade.addUpgradeCallback(new UpgradeCallback() {
            @Override
            public void onUpgradeProcess(float percent) {
                Toast.makeText(getApplicationContext(), "正在更新", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpgradeComplete() {
                Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpgradeError(Throwable throwable) {
                Toast.makeText(getApplicationContext(), "更新失败", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "message:" + throwable.getMessage() + "\nstackTrace:" + Log.getStackTraceString(throwable));
            }
        });

        List<UpgradeInfo> upgradeInfoList = UPGRADE_PACKAGE_MAP.get("x86");

        UpgradeInfo upgradeInfo = upgradeInfoList.get(0);
        UpgradeSource upgradeSource = getUpgradeSource(upgradeInfo, context);
        System.out.println();
        WebViewUpgrade.upgrade(upgradeSource);
    }

    private static UpgradeSource getUpgradeSource(UpgradeInfo upgradeInfo, Context context) {
        UpgradeSource upgradeSource = null;

        upgradeSource = new UpgradeAssetSource(
                context,
                upgradeInfo.url,
                new File(context.getFilesDir(), upgradeInfo.packageName + "/" + upgradeInfo.versionName + ".apk")
        );
        return upgradeSource;
    }


    static class UpgradeInfo {
        public UpgradeInfo(String packageName, String versionName, String url, String extraInfo) {
            this.title = packageName + "\n" + versionName;
            this.extraInfo = !TextUtils.isEmpty(extraInfo) ? extraInfo : "";
            if (!extraInfo.isEmpty()) {
                this.title = this.title + "\n" + extraInfo;
            }
            this.url = url;
            this.packageName = packageName;
            this.versionName = versionName;
        }

        public UpgradeInfo(String packageName, String versionName, String url) {

            this(packageName, versionName, url, "");
        }

        String title;
        String url;
        String packageName;
        String versionName;
        String extraInfo;
    }


}
