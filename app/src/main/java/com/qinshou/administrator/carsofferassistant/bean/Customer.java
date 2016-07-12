package com.qinshou.administrator.carsofferassistant.bean;

/**
 * Created by zyj on 2016/7/12.
 */

public class Customer {
    private String id;
    private String name;
    private String phone;
    private String gender;
    private String city;

    public Customer() {
    }

    public Customer(String name, String phone, String gender, String city) {
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.city = city;
    }

    public Customer(String id, String name, String phone, String gender, String city) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                ", city='" + city + '\'' +
                '}';
    }
}
