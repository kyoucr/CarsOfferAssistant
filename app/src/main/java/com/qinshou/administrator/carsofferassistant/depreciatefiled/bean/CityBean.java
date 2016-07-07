package com.qinshou.administrator.carsofferassistant.depreciatefiled.bean;

/**
 * Created by 岩 on 2016/7/7.
 * 城市列表实体类
 */

public class CityBean {
    private String cityName;    // 城市名
    private int cityCode;  // 城市编号
    private String cityPY;  // 城市名拼音

    public CityBean() {
    }

    public CityBean(String cityName, int cityCode, String cityPY) {
        this.cityName = cityName;
        this.cityCode = cityCode;
        this.cityPY = cityPY;
    }

    public String getcityName() {
        return cityName;
    }

    public void setcityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityPY() {
        return cityPY;
    }

    public void setCityPY(String cityPY) {
        this.cityPY = cityPY;
    }
}
