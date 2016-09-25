package com.zhi.www.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/9/25.
 */
public class HttpHelper {

    private static HttpHelper httpHelper = null;

    private HttpHelper() {
    }

    public static HttpHelper getInstance() {
        if (null == httpHelper) {
            httpHelper = new HttpHelper();
        }
        return httpHelper;
    }

    /**
     * 从网络上获取图片，显示在界面中
     */
    public Bitmap getBitmap(String url) {
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.connect();

            InputStream in = connection.getInputStream();
            return BitmapFactory.decodeStream(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下载网络图片至本地SD卡
     */
    public void downloadImage(String url) {
        File f = createFile();
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.connect();
            InputStream in = connection.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(in);

            FileOutputStream fos = new FileOutputStream(f);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }

            in.close();
            fos.close();
            bos.flush();
            bis.close();
            bos.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File createFile() {
        File file = Environment.getExternalStorageDirectory();
        String fileName = "down.jpg";
        File dir = new File(file.getPath());
        if (!dir.exists()) {
            dir.mkdir();
        }

        File f = new File(file, fileName);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }
}