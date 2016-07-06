package com.qinshou.administrator.carsofferassistant.bean;

/**
 * Created by 禽兽先生 on 2016.
 */

public class Car {
    private String bsID;
    private String bsName;
    private String pic;

    public String getBsID() {
        return bsID;
    }

    public void setBsID(String bsID) {
        this.bsID = bsID;
    }

    public String getBsName() {
        return bsName;
    }

    public void setBsName(String bsName) {
        this.bsName = bsName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "Car{" +
                "bsID='" + bsID + '\'' +
                ", bsName='" + bsName + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
