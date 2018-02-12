package ctsig.updatehelper.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import java.util.List;

/**
 * Created by wc on 2016/2/23.
 */
public class PermissionUtils {

    public static final String TAG = "PermissionUtils";

    /**
     * 检查是否有Settings.ACTION_USAGE_ACCESS_SETTINGS这个页面
     *
     * @param context
     * @return
     */
    public static boolean isNoOption(Context context) {
        PackageManager packageManager = context
                .getPackageManager();
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }


    /**
     * 检查本应用是否开启了查看使用app权限
     *
     * @param context
     * @return
     */
    public static boolean isNoSwitch(Context context) {
        long ts = System.currentTimeMillis();
        UsageStatsManager usageStatsManager = (UsageStatsManager) context
                .getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> queryUsageStats = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            queryUsageStats = usageStatsManager.queryUsageStats(
                    UsageStatsManager.INTERVAL_BEST, 0, ts);
        }
        if (queryUsageStats == null || queryUsageStats.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 检查查看应用使用权限是否开启
     *
     * @param context
     * @return
     */
    @TargetApi(21)
    public static boolean isCanUseAppHis(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        AppOpsManager appOps = (AppOpsManager) context
                .getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(), context.getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    @TargetApi(23)
    public static boolean canDrawOverlays(Context context) {
        return Settings.canDrawOverlays(context);
    }

    @TargetApi(23)
    public static boolean requestPermissions(Activity activity, int requestCode, String permission, int tip) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 有权限
            if (ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {

            } else {// 去请求权限
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    ToastUtils.show(activity, tip);
                }
                ActivityCompat.requestPermissions(activity,
                        new String[]{permission},
                        requestCode);
                return false;
            }
        }
        return true;
    }
}
