package ctsig.update;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ctsig.updatehelper.UpdateHelper;
import ctsig.updatehelper.utils.PictureUtils;

public class MainActivity extends AppCompatActivity {
    private Button mBtnUpdate;
    private NotificationCompat.Builder notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnUpdate = findViewById(R.id.btn_update);

        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {


                UpdateHelper updateHelper = new UpdateHelper.Builder(MainActivity.this)
                        //填写版本检测url地址  项目名称  type 及设备id
                        .checkUrl("https://app.ctsig.com/appcms/api/app/checkVersion/", "documentsShareCTG", 1, 1)
                        //设置为false需在下载完手动点击安装;默认值为true，下载后自动安装。
                        .isAutoInstall(true)
                        //当没有新版本时，是否Toast提示
                        .isHintNewVersion(false)
                        //设置小图标
                        .setSmallIcon(R.drawable.logo)
                        //设置下拉后大图标
                        .setBigIcon(R.mipmap.ic_logo_new)
                        .build();


                //自定义Dialog 提示更新, 并调用 updateHelper.downlaod(); 进行下载.

//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("title")
//                        .setMessage("message")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                                updateHelper.downlaod();
//
//
//                            }
//                        })
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                            }
//                        });
//                builder.create();
//                updateHelper.setDialog(Dialog);
//
               // 检查app是否有新版本，check之前先Builer所需参数
                updateHelper.check();


                //如果要自定义下载进度条,实现此接口
//                updateHelper.check(new OnUpdateListener() {
//                    @Override
//                    public void onStartCheck() {
//
//                    }
//
//                    @Override
//                    public void onFinishCheck(UpdateInfo info) {
//
//                    }
//
//                    @Override
//                    public void onStartDownload() {
//
//                    }
//
//                    @Override
//                    public void onDownloading(int progress) {
//                        L.d("进度",progress+"");
//
//                        showDownloadNotificationUI(progress);
//
//                    }
//
//                    @Override
//                    public void onFinshDownload() {
//
//                    }
//                });
            }
        });


    }
//
//    private void showDownloadNotificationUI(int progress) {
//        L.d("下载进度",progress+"");
//        String contentText = new StringBuffer().append(progress)
//                .append("%").toString();
//        PendingIntent contentIntent = PendingIntent.getActivity(this,
//                0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
//        if (notificationManager == null) {
//            notificationManager = (NotificationManager) this
//                    .getSystemService(Context.NOTIFICATION_SERVICE);
//        }
//        if (ntfBuilder == null) {
//            ntfBuilder = new NotificationCompat.Builder(this)
//                    .setSmallIcon(R.drawable.logo)
//                    .setTicker("开始下载...")
//                    .setContentTitle("文档分享")
//                    .setContentIntent(contentIntent);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                ntfBuilder.setLargeIcon(PictureUtils.drawable2Bitmap(this.getResources().getDrawable(R.drawable.logo, null)));
//            } else {
//                ntfBuilder.setLargeIcon(PictureUtils.drawable2Bitmap(this.getResources().getDrawable(R.drawable.logo)));
//            }
//        }
//        ntfBuilder.setContentText(contentText);
//        L.i("进度", "当前进度为：" + progress + "%");
//        ntfBuilder.setProgress(100, progress, false);
//        notificationManager.notify(1,
//                ntfBuilder.build());
//
//
//
//
//    }
}
