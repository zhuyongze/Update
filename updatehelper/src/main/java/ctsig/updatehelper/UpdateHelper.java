package ctsig.updatehelper;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import ctsig.updatehelper.dialog.MultipleDialog;
import ctsig.updatehelper.listener.OnUpdateListener;
import ctsig.updatehelper.pojo.CheckVersion;
import ctsig.updatehelper.pojo.UpdateInfo;
import ctsig.updatehelper.utils.HttpClientUtils;
import ctsig.updatehelper.utils.HttpRequest;
import ctsig.updatehelper.utils.JSONHandler;
import ctsig.updatehelper.utils.L;
import ctsig.updatehelper.utils.NetWorkUtils;
import ctsig.updatehelper.utils.PermissionUtils;
import ctsig.updatehelper.utils.PictureUtils;
import ctsig.updatehelper.utils.ToastUtils;
import ctsig.updatehelper.utils.URLUtils;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;


/**
 * Created by ShelWee on 14-5-8.<br/>
 * Usage:
 * <p>
 * <pre>
 * UpdateManager updateManager = new UpdateManager.Builder(this)
 * 		.checkUrl(&quot;http://localhost/examples/version.jsp&quot;)
 * 		.isAutoInstall(false)
 * 		.build();
 * updateManager.check();
 * </pre>
 *
 * @author ShelWee(<a href="http://www.shelwee.com">http://www.shelwee.com</a>)
 * @version 0.1 beta
 */
public class UpdateHelper {

    private static final String TAG = "UpdateHelper";
    private Context mContext;
    private String checkUrl;
    private boolean isAutoInstall;
    private boolean isHintVersion;
    private int mIcon;

    private String projectName;
    private int type;
    private int deviceId;
    private OnUpdateListener updateListener;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder ntfBuilder;

    public static final int EXTERNAL_PREMISSION = 2;
    private static final int UPDATE_NOTIFICATION_PROGRESS = 0x1;
    private static final int COMPLETE_DOWNLOAD_APK = 0x2;
    private static final int DOWNLOAD_NOTIFICATION_ID = 0x3;
    private static final String PATH = Environment
            .getExternalStorageDirectory().getPath();
    private static final String SUFFIX = ".apk";
    private static final String APK_PATH = "APK_PATH";
    private static final String APP_NAME = "APP_NAME";
//	private SharedPreferences preferences_update;

    //提示框
    private MultipleDialog mDialog;

    private HashMap<String, String> cache = new HashMap<String, String>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_NOTIFICATION_PROGRESS:
                    showDownloadNotificationUI((UpdateInfo) msg.obj, msg.arg1);
                    break;
                case COMPLETE_DOWNLOAD_APK:
                    if (UpdateHelper.this.isAutoInstall) {
                        installApk(Uri.parse("file://" + cache.get(APK_PATH)));
                    } else {
                        if (ntfBuilder == null) {
                            ntfBuilder = new NotificationCompat.Builder(mContext);
                        }
                        ntfBuilder.setSmallIcon(mContext.getApplicationInfo().icon)
                                .setContentTitle(cache.get(APP_NAME))
                                .setContentText(mContext.getString(R.string.notice_click_install))
                                .setTicker(mContext.getString(R.string.notice_download_over));
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(
                                Uri.parse("file://" + cache.get(APK_PATH)),
                                "application/vnd.android.package-archive");
                        PendingIntent pendingIntent = PendingIntent.getActivity(
                                mContext, 0, intent, 0);
                        ntfBuilder.setContentIntent(pendingIntent);
                        if (notificationManager == null) {
                            notificationManager = (NotificationManager) mContext
                                    .getSystemService(Context.NOTIFICATION_SERVICE);
                        }
                        notificationManager.notify(DOWNLOAD_NOTIFICATION_ID,
                                ntfBuilder.build());
                    }
                    break;
            }
        }

    };

    private UpdateHelper(Builder builder) {
        this.mContext = builder.context;
        this.checkUrl = builder.checkUrl + "?" + "projectName=" + builder.projectName + "&" + "type=" + builder.type + "&" + "deviceId=" + builder.deviceId;
        this.isAutoInstall = builder.isAutoInstall;
        this.isHintVersion = builder.isHintNewVersion;
        this.mIcon = builder.icon;
        //初始化弹出框
        mDialog = new MultipleDialog((Activity) mContext, 4);
//		preferences_update = mContext.getSharedPreferences("Updater",
//				Context.MODE_PRIVATE);
    }

    public UpdateHelper(Context context, boolean isAutoInstall, boolean isHintVersion) {
        this.mContext = context;
        this.isAutoInstall = isAutoInstall;
        this.isHintVersion = isHintVersion;
    }

    /**
     * 检查app是否有新版本，check之前先Builer所需参数
     */
    public void check() {
        check(null);
    }

    public void check(OnUpdateListener listener) {
        if (listener != null) {
            this.updateListener = listener;
        }
        if (mContext == null) {
            Log.e("NullPointerException", "The context must not be null.");
            return;
        }
        L.d("版本更新", checkUrl.toString());
        AsyncCheck asyncCheck = new AsyncCheck();
        asyncCheck.execute(checkUrl);
    }

    public void setOnUpdateListener(OnUpdateListener listener) {
        if (listener != null) {
            this.updateListener = listener;
        }
        if (mContext == null) {
            Log.e("NullPointerException", "The context must not be null.");
            return;
        }
    }

    /**
     * 2014-10-27新增流量提示框，当网络为数据流量方式时，下载就会弹出此对话框提示
     *
     * @param updateInfo
     */
    private void showNetDialog(final UpdateInfo updateInfo) {
        mDialog.setCancelable(true);
        mDialog.show();
        mDialog.showTwoBtnDialog(R.string.content_download_tips_new, "继续", "取消",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AsyncDownLoad asyncDownLoad = new AsyncDownLoad();
                        asyncDownLoad.execute(updateInfo);

                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });


    }

    public void showUpdataUI(CheckVersion checkVersion) {
        if (!PermissionUtils.requestPermissions((Activity) mContext, 1001, Manifest.permission.WRITE_EXTERNAL_STORAGE, R.string.tv_tip_apply_write_external_storage)) {
            ToastUtils.show(mContext, R.string.tv_tip_apply_write_external_storage);
            return;
        }


        final UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setApkUrl(checkVersion.getDownloadUrl());
        updateInfo.setAppName(checkVersion.getDescription());
        updateInfo.setVersionName(checkVersion.getVersionName());
        mDialog.setCancelable(true);
        mDialog.show();
        mDialog.showSingleBtnDialog(R.string.content_download_alert, "确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                NetWorkUtils netWorkUtils = new NetWorkUtils(mContext);
                int type = netWorkUtils.getNetType();
                if (type != 1) {
                    showNetDialog(updateInfo);
                } else {
                    AsyncDownLoad asyncDownLoad = new AsyncDownLoad();
                    asyncDownLoad.execute(updateInfo);


                }
            }
        });
    }

    /**
     * 弹出提示更新窗口
     *
     * @param updateInfo
     */
    private void showUpdateTrue(final UpdateInfo updateInfo) {
        //取消物理返回键
        mDialog.setCancelable(false);
        mDialog.show();

        mDialog.showSingleBtnDialog(R.string.content_download_alert, "确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                NetWorkUtils netWorkUtils = new NetWorkUtils(mContext);
                int type = netWorkUtils.getNetType();
                if (type != 1) {
                    showNetDialog(updateInfo);
                } else {
                    AsyncDownLoad asyncDownLoad = new AsyncDownLoad();
                    asyncDownLoad.execute(updateInfo);


                }

            }
        });

    }


    /**
     * 弹出提示强制更新窗口
     *
     * @param updateInfo
     */
    private void showUpdateUI(final UpdateInfo updateInfo) {
        mDialog.setCancelable(true);
        mDialog.show();
        mDialog.showTwoBtnDialog("有新版本是否更新?", "下载", "下次再说",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        NetWorkUtils netWorkUtils = new NetWorkUtils(mContext);
                        int type = netWorkUtils.getNetType();
                        if (type != 1) {
                            showNetDialog(updateInfo);
                        } else {
                            AsyncDownLoad asyncDownLoad = new AsyncDownLoad();
                            asyncDownLoad.execute(updateInfo);
                        }
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });


    }


    /**
     * 通知栏弹出下载提示进度
     *
     * @param updateInfo
     * @param progress
     */
    private void showDownloadNotificationUI(UpdateInfo updateInfo,
                                            final int progress) {
        if (mContext != null) {
            String contentText = new StringBuffer().append(progress)
                    .append("%").toString();
            PendingIntent contentIntent = PendingIntent.getActivity(mContext,
                    0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
            if (notificationManager == null) {
                notificationManager = (NotificationManager) mContext
                        .getSystemService(Context.NOTIFICATION_SERVICE);
            }
            if (ntfBuilder == null) {
//                if (mIcon == 0) {
//                    mIcon = R.drawable.ic_launcher;
//
//                }
                ntfBuilder = new NotificationCompat.Builder(mContext)
                        .setSmallIcon(mIcon)
                        .setTicker("开始下载...")
                        .setContentTitle(updateInfo.getAppName())
                        .setContentIntent(contentIntent);
                L.d("图片地址", mContext.getResources().getDrawable(mIcon) + "");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    L.d("sssss");
                    ntfBuilder.setLargeIcon(PictureUtils.drawable2Bitmap(mContext.getResources().getDrawable(mIcon, null)));
                } else {
                    L.d("000000");
                    ntfBuilder.setLargeIcon(PictureUtils.drawable2Bitmap(mContext.getResources().getDrawable(mIcon)));
                }
            }
            ntfBuilder.setContentText(contentText);
            L.i(TAG, "当前进度为：" + progress + "%");
            ntfBuilder.setProgress(100, progress, false);
            notificationManager.notify(DOWNLOAD_NOTIFICATION_ID,
                    ntfBuilder.build());
        }
    }

    /**
     * 获取当前app版本
     *
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    private PackageInfo getPackageInfo() {
        PackageInfo pinfo = null;
        if (mContext != null) {
            try {
                pinfo = mContext.getPackageManager().getPackageInfo(
                        mContext.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return pinfo;
    }

    /**
     * 检查更新任务
     */
    private class AsyncCheck extends AsyncTask<String, Integer, UpdateInfo> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (UpdateHelper.this.updateListener != null) {
                UpdateHelper.this.updateListener.onStartCheck();
            }
        }

        @Override
        protected UpdateInfo doInBackground(String... params) {
            UpdateInfo updateInfo = null;
            if (params.length == 0) {
                Log.e("NullPointerException",
                        " Url parameter must not be null.");
                return null;
            }
            String url = params[0];
            if (!URLUtils.isNetworkUrl(url)) {
                Log.e("Exception", "IllegalArgumentException:The URL is invalid.");
                return null;
            }
            try {
                updateInfo = JSONHandler.toUpdateInfo(HttpRequest.get(url));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return updateInfo;
        }

        @Override
        protected void onPostExecute(UpdateInfo updateInfo) {
            super.onPostExecute(updateInfo);
//			SharedPreferences.Editor editor = preferences_update.edit();
            if (mContext != null && updateInfo != null) {
                //getPackageInfo().versionCode
                if (Integer.parseInt(updateInfo.getVersionCode()) > 10000) {
                    //ForceUpgrade=true 强制更新
                    if (updateInfo.isForceUpgrade()) {
                        showUpdateTrue(updateInfo);

                    } else {
                        showUpdateUI(updateInfo);

                    }


//					editor.putBoolean("hasNewVersion", true);
//					editor.putString("lastestVersionCode",
//							updateInfo.getVersionCode());
//					editor.putString("lastestVersionName",
//							updateInfo.getVersionName());
                } else {
                    if (isHintVersion) {
                        Toast.makeText(mContext, "当前已是最新版", Toast.LENGTH_LONG).show();
                    }
                    if (UpdateHelper.this.updateListener != null) {
                        UpdateHelper.this.updateListener.onFinishCheck(updateInfo);
                    }
//					editor.putBoolean("hasNewVersion", false);
                }
            } else {
                if (isHintVersion) {
                    Toast.makeText(mContext, "当前已是最新版", Toast.LENGTH_LONG).show();
                }
                if (UpdateHelper.this.updateListener != null) {
                    UpdateHelper.this.updateListener.onFinishCheck(updateInfo);
                }
            }
//			editor.putString("currentVersionCode", getPackageInfo().versionCode
//					+ "");
//			editor.putString("currentVersionName", getPackageInfo().versionName);
//			editor.commit();
        }
    }

    /**
     * 异步下载app任务
     */
    private class AsyncDownLoad extends AsyncTask<UpdateInfo, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (UpdateHelper.this.updateListener != null) {
                UpdateHelper.this.updateListener.onStartCheck();
            }
        }

        @Override
        protected Boolean doInBackground(UpdateInfo... params) {
            HttpClient httpClient = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                httpClient = HttpClients.createDefault();
            } else {
                httpClient = new DefaultHttpClient();
            }
            L.d("下载地址", params[0].getApkUrl());
            HttpGet httpGet = new HttpGet(params[0].getApkUrl());
            try {
                HttpResponse response = httpClient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    Log.e("Exception", "IllegalArgumentException:APK路径出错，请检查服务端配置接口。");
                    return false;
                } else {
                    HttpEntity entity = response.getEntity();
                    InputStream inputStream = entity.getContent();
                    long total = entity.getContentLength();
                    String apkName = params[0].getAppName();
                    // + params[0].getVersionName() + SUFFIX;
                    cache.put(APP_NAME, params[0].getAppName());
                    cache.put(APK_PATH,
                            PATH + File.separator + params[0].getAppName()
                                    + File.separator + apkName);
                    File savePath = new File(PATH + File.separator
                            + params[0].getAppName());
                    if (!savePath.exists())
                        savePath.mkdirs();
                    L.i(TAG, "savePath = " + savePath + "    apkName = " + apkName);
                    File apkFile = new File(savePath, apkName);
                    if (apkFile.exists()) {
                        return true;
                    }
                    L.d("apk", apkFile.exists() + "");
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    byte[] buf = new byte[1024];
                    int count = 0;
                    int length = -1;
                    int lastProgress = -1;//不要让ui工作太频繁,每隔5%直发一次
                    while ((length = inputStream.read(buf)) != -1) {
                        fos.write(buf, 0, length);
                        count += length;
                        int progress = (int) ((count / (float) total) * 100);
                        if (progress % 5 == 0 && progress > lastProgress) {
                            lastProgress = progress;
                            if (UpdateHelper.this.updateListener != null) {
                                UpdateHelper.this.updateListener
                                        .onDownloading(progress);
                            } else {
                                handler.obtainMessage(UPDATE_NOTIFICATION_PROGRESS,
                                        progress, -1, params[0]).sendToTarget();
                            }

                        }


                    }
                    inputStream.close();
                    fos.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean flag) {
            if (flag) {
                handler.obtainMessage(COMPLETE_DOWNLOAD_APK).sendToTarget();
                if (UpdateHelper.this.updateListener != null) {
                    UpdateHelper.this.updateListener.onFinshDownload();
                }
            } else {
                Log.e("Error", "下载失败。");
            }
        }
    }

    public static class Builder {
        private Context context;
        private String checkUrl;
        private String projectName;
        private int icon;
        private int type;
        private int deviceId;
        private boolean isAutoInstall = true;
        private boolean isHintNewVersion = true;

        public Builder(Context ctx) {
            this.context = ctx;
        }

        /**
         * 检查是否有新版本App的URL接口路径
         *
         * @param checkUrl
         * @param projectName
         * @param type
         * @param deviceId
         * @return
         */
        public Builder checkUrl(String checkUrl, String projectName, int type, int deviceId) {
            this.checkUrl = checkUrl;
            this.projectName = projectName;
            this.type = type;
            this.deviceId = deviceId;
            return this;
        }

        /*
        * 设置icon
        *@param icon
         * @return
        * */



        public Builder setIcon(int icon) {
            this.icon = icon;
            return this;
        }

        /**
         * 是否需要自动安装, 不设置默认自动安装
         *
         * @param isAuto true下载完成后自动安装，false下载完成后需在通知栏手动点击安装
         * @return
         */
        public Builder isAutoInstall(boolean isAuto) {
            this.isAutoInstall = isAuto;
            return this;
        }

        /**
         * 当没有新版本时，是否Toast提示
         *
         * @param isHint
         * @return true提示，false不提示
         */
        public Builder isHintNewVersion(boolean isHint) {
            this.isHintNewVersion = isHint;
            return this;
        }

        /**
         * 构造UpdateManager对象
         *
         * @return
         */
        public UpdateHelper build() {
            return new UpdateHelper(this);
        }
    }

    private void installApk(Uri data) {
        if (mContext != null) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setDataAndType(data, "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
            if (notificationManager != null) {
                notificationManager.cancel(DOWNLOAD_NOTIFICATION_ID);
            }
        } else {
            Log.e("NullPointerException", "The context must not be null.");
        }
    }
}