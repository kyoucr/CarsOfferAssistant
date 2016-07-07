package com.qinshou.administrator.carsofferassistant.inter;

import android.graphics.Bitmap;

import com.qinshou.administrator.carsofferassistant.bean.Series;

import java.util.List;
import java.util.Map;

/**
 * Created by zyj on 2016/7/6.
 */

public interface IndependenceDataCallback {
    void callBack(List<Series> seriesList);

    void imageCallBack(Map<String,Object> map, Bitmap bitmap);
}
