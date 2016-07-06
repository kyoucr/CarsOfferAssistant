package com.qinshou.administrator.carsofferassistant.utils;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 禽兽先生 on 2016.
 */

public class DownloadXml {
    public static InputStream downloadSelectModels(String url) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            InputStream result = response.body().byteStream();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
