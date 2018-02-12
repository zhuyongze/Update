package ctsig.updatehelper.utils;


import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import ctsig.updatehelper.pojo.UpdateBean;
import ctsig.updatehelper.pojo.UpdateInfo;


/**
 * Created by ShelWee on 14-5-8.
 */
public class JSONHandler {
    private static UpdateBean updateBean;

    public static UpdateInfo toUpdateInfo(InputStream is) throws Exception {
        if (is == null) {
            return null;
        }
        String byteData = new String(readStream(is));
        L.d("版本更新", byteData);
        is.close();
        JSONObject jsonObject = new JSONObject(byteData);
        int status = jsonObject.getInt("code");
        if (status == 200) {
            //JSONObject versionObject = jsonObject.getJSONObject("datas") ;
            UpdateInfo updateInfo = new UpdateInfo();

//            updateInfo.setApkUrl(versionObject.getString("apkUrl"));
//            updateInfo.setAppName(versionObject.getString("apkName"));
//            updateInfo.setVersionCode(versionObject.getString("versionCode"));
//            updateInfo.setVersionName(versionObject.getString("versionName"));
//            updateInfo.setChangeLog(versionObject.getString("changeLog"));
//            updateInfo.setUpdateTips(versionObject.getString("updateTips"));

            updateBean = GSONUtils.GsonToBean(jsonObject.toString(), UpdateBean.class);
            L.d("获取数据", updateBean.toString());
            updateInfo = new UpdateInfo();
            updateInfo.setApkUrl(updateBean.getDatas().getDownloadPath());
            updateInfo.setAppName(updateBean.getDatas().getPath());
            updateInfo.setVersionCode(String.valueOf(updateBean.getDatas().getVersionCode()));
            updateInfo.setVersionName(updateBean.getDatas().getVersion());
            // mUpdateInfo.setChangeLog(updateBean.getDatas().get(0).getChangeLog());
            updateInfo.setForceUpgrade(updateBean.getDatas().getForceUpgrade());
            updateInfo.setForceUpgradeVersion(updateBean.getDatas().getForceUpgradeVersion());


            return updateInfo;
        }
        return null;
    }

    private static byte[] readStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] array = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(array)) != -1) {
            outputStream.write(array, 0, len);
        }
        inputStream.close();
        outputStream.close();
        return outputStream.toByteArray();
    }

}
