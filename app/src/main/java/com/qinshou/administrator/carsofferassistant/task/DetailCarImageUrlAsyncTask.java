package com.qinshou.administrator.carsofferassistant.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.adapter.MyDetailImageAdapter;
import com.qinshou.administrator.carsofferassistant.utils.XMLParserIndependenceUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by zyj on 2016/7/8.
 */

public class DetailCarImageUrlAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private List<Map<String,Object>> dataSource;
    private int position;
    private MyDetailImageAdapter adapter;

    public DetailCarImageUrlAsyncTask(List<Map<String,Object>> dataSource,int position,MyDetailImageAdapter adapter) {
        this.dataSource = dataSource;
        this.position = position;
        this.adapter = adapter;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                return BitmapFactory.decodeStream(is);
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
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null){
            dataSource.get(position).put("iv_default_detail_image_id", bitmap);
            adapter.notifyDataSetChanged();
        }
    }
}
