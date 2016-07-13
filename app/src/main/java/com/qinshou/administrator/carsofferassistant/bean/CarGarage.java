package com.qinshou.administrator.carsofferassistant.bean;

/**
 * Created by zyj on 2016/7/13.
 */

public class CarGarage {
    private int _id;
    private String url;
    private String name_string;
    private String name;

    public CarGarage() {
    }

    public CarGarage(int _id, String url, String name_string, String name) {
        this._id = _id;
        this.url = url;
        this.name_string = name_string;
        this.name = name;
    }

    public CarGarage(String url, String name_string, String name) {
        this.url = url;
        this.name_string = name_string;
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName_string() {
        return name_string;
    }

    public void setName_string(String name_string) {
        this.name_string = name_string;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CarGarage{" +
                "_id=" + _id +
                ", url='" + url + '\'' +
                ", name_string='" + name_string + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
