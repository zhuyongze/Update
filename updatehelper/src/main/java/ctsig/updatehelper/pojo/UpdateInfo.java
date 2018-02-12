package ctsig.updatehelper.pojo;


/**
 * Created by ShelWee on 14-5-8.
 */
public class UpdateInfo {
    //app名字
    private String appName;

    private String packageName;
    //版本号
    private String versionCode;
    //版本名称
    private String versionName;
    //下载地址
    private String apkUrl;
    //版本日志
    private String changeLog;

    private String updateTips;
    //是否强制更新
    private boolean forceUpgrade;
    //强制更新版本
    private  String forceUpgradeVersion;

    public boolean isForceUpgrade() {
        return forceUpgrade;
    }

    public void setForceUpgrade(boolean forceUpgrade) {
        this.forceUpgrade = forceUpgrade;
    }

    public String getForceUpgradeVersion() {
        return forceUpgradeVersion;
    }

    public void setForceUpgradeVersion(String forceUpgradeVersion) {
        this.forceUpgradeVersion = forceUpgradeVersion;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getUpdateTips() {
        return updateTips;
    }

    public void setUpdateTips(String updateTips) {
        this.updateTips = updateTips;
    }

    public String getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }
}
