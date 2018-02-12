package ctsig.updatehelper.utils;

import android.util.Log;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * @author ShelWee
 *
 */
public class HttpRequest {

    public static InputStream get(String url){
        L.d("版本更新URL",url);
        try {

            URL urlPath = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlPath.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.connect();

            InputStream inputStream = null;
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
            }
            return inputStream;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TimeOut","the connection is timeout, maybe the server was closed.");
            return null;
        }
    }
}
