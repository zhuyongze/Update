package ctsig.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ctsig.updatehelper.Main2Activity;
import ctsig.updatehelper.UpdateHelper;
import ctsig.updatehelper.listener.OnUpdateListener;
import ctsig.updatehelper.pojo.UpdateInfo;
import ctsig.updatehelper.utils.L;
import ctsig.updatehelper.utils.PictureUtils;

public class MainActivity extends AppCompatActivity {
    private Button mBtnUpdate;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder ntfBuilder;
    private static final int DOWNLOAD_NOTIFICATION_ID = 0x4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnUpdate = findViewById(R.id.btn_update);

        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//                Notification notification=new NotificationCompat.Builder(MainActivity.this)
//                        .setContentTitle("这是头部")
//                        .setContentText("这是内容")
//                        .setSmallIcon(R.drawable.logo)
//                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.icon_me))
//                        .build();
//                manager.notify(1,notification);




                UpdateHelper updateHelper = new UpdateHelper.Builder(MainActivity.this)
                        .checkUrl("https://app.ctsig.com/appcms/api/app/checkVersion/","documentsShareCTG", 1, 1)
                        .isAutoInstall(true) //设置为false需在下载完手动点击安装;默认值为true，下载后自动安装。
                        .setIcon(R.drawable.logo)
                        .build();
                updateHelper.check();
                //自定义进度监听
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
