package com.qinshou.administrator.carsofferassistant.depreciatefiled.network;

import android.os.AsyncTask;

import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.DealerListBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.ListReduceCar;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.ReduceCar;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.inter.ReduceCarDataCallBack;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.inter.TestInterface;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.util.ParseXml;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.util.ReduceDetailParserUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zyj on 2016/7/10.
 */

public class ReduceCarDetailAsyncTask extends AsyncTask<String, Void, ListReduceCar> {
    ReduceCarDataCallBack data;

    public ReduceCarDetailAsyncTask(ReduceCarDataCallBack data) {
        this.data = data;
    }

    @Override
    protected ListReduceCar doInBackground(String... params) {

        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(params[0]).build();
            Response response = okHttpClient.newCall(request).execute();
            InputStream result = response.body().byteStream();
            return ReduceDetailParserUtil.parseDealers(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        HttpURLConnection connection = null;
//        try {
//            URL url = new URL(params[0]);
//            connection = (HttpURLConnection) url.openConnection();
//            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
//                InputStream inputStream = connection.getInputStream();
////                return ParseXml.parseDealers(inputStream);
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if(connection != null){
//                connection.disconnect();
//            }
//        }
        return null;
    }

    @Override
    protected void onPostExecute(ListReduceCar carList) {
        data.dataCallBack(carList);
        super.onPostExecute(carList);
    }
}
