package com.qinshou.administrator.carsofferassistant.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.qinshou.administrator.carsofferassistant.inter.IndependenceDataCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zyj on 2016/7/6.
 */

public class IndependenceImageAsyncTask extends AsyncTask<String, Void, byte[]> {
    /*Map<String, Object> map;

    public IndependenceImageAsyncTask(Map<String, Object> map) {
        this.map = map;
    }*/
//    private Map<String, Object> map;
//    private IndependenceDataCallback imageReturn;
    private ImageView iv_log;

//    public IndependenceImageAsyncTask(Map<String, Object> map, IndependenceDataCallback imageReturn) {
//        this.map = map;
//        this.imageReturn = imageReturn;
//    }

    public IndependenceImageAsyncTask(ImageView iv_log) {
        this.iv_log = iv_log;
    }

    @Override
    protected byte[] doInBackground(String... params) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                InputStream is = connection.getInputStream();
                byte[] buffer = new byte[1024 * 10];
                connection.setConnectTimeout(1000 * 10);
                connection.setReadTimeout(1000 * 10);
                int len = -1;
                while ((len = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                    baos.flush();
                }
                return baos.toByteArray();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;

    }


    @Override
    protected void onPostExecute(byte[] result) {
        if (result != null && result.length != 0) {
            // 应该字节数组转换成BitMap的对象
            Bitmap bm = BitmapFactory.decodeByteArray(result, 0, result.length);
            iv_log.setImageBitmap(bm);

//            map.put("iv_self_log_id", bm);
        }
    }
}
