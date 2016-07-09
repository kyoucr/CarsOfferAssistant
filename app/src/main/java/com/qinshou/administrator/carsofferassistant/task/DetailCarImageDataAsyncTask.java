package com.qinshou.administrator.carsofferassistant.task;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.activity.DetailImageShowActivity;
import com.qinshou.administrator.carsofferassistant.adapter.MyDetailImageAdapter;
import com.qinshou.administrator.carsofferassistant.inter.ImageShowCallback;
import com.qinshou.administrator.carsofferassistant.utils.XMLParserIndependenceUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyj on 2016/7/8.
 */

public class DetailCarImageDataAsyncTask extends AsyncTask<String, Void, List<String>> {
    private ImageShowCallback data;

    public DetailCarImageDataAsyncTask(ImageShowCallback data) {
        this.data = data;
    }

    @Override
    protected List<String> doInBackground(String... params) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                return XMLParserIndependenceUtil.parseDetailImage(is);
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
    protected void onPostExecute(List<String> result) {
        data.callBack(result);
    }
}
