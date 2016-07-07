package com.qinshou.administrator.carsofferassistant.bean;

/**
 * Created by zyj on 2016/7/6.
 */

public class Series {
    private int id;
    private String price_range;//价格区间
    private String name;//车型
    private String pic;//车的图片地址

    public Series() {
    }

    public Series(int id, String price_range, String name, String pic) {
        this.id = id;
        this.price_range = price_range;
        this.name = name;
        this.pic = pic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice_range() {
        return price_range;
    }

    public void setPrice_range(String price_range) {
        this.price_range = price_range;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

}
