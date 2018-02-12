package ctsig.updatehelper.pojo;

/**
 * Created by hdly on 2016/12/23.
 */
public class CheckVersion {

    private String apiVersion;
    private String deadVersionCode;
    private String description;
    private String downloadUrl;
    private long updateTime;
    private String versionCode;
    private String versionName;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getDeadVersionCode() {
        return deadVersionCode;
    }

    public void setDeadVersionCode(String deadVersionCode) {
        this.deadVersionCode = deadVersionCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
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
}
