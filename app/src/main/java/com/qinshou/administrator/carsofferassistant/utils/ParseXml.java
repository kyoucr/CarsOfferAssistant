package com.qinshou.administrator.carsofferassistant.utils;

import com.qinshou.administrator.carsofferassistant.bean.Brand;
import com.qinshou.administrator.carsofferassistant.bean.Car;
import com.qinshou.administrator.carsofferassistant.bean.CarIntro;
import com.qinshou.administrator.carsofferassistant.bean.ModelsSeries;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 禽兽先生 on 2016.07.06
 */

public class ParseXml {
    public static Map<String, List<Brand>> parseSelectModels(InputStream xmlData) {
        List<Brand> brands = null;
        Map<String, List<Brand>> map = null;
        String groupName = null;
        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(xmlData, "UTF-8");
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        map = new LinkedHashMap<>();
                        break;
                    case XmlPullParser.START_TAG:
                        String tagName = xmlPullParser.getName();
                        if ("group".equals(tagName)) {
                            brands = new ArrayList<>();
                            groupName = xmlPullParser.getAttributeValue(0);
                        } else if ("master".equals(tagName)) {
                            Brand brand = new Brand();
                            int count = xmlPullParser.getAttributeCount();
                            for (int i = 0; i < count; i++) {
                                String attributeName = xmlPullParser.getAttributeName(i);
                                String attributeValue = xmlPullParser.getAttributeValue(i);
                                if ("bsID".equals(attributeName)) {
                                    brand.setBsID(attributeValue);
                                } else if ("bsName".equals(attributeName)) {
                                    brand.setBsName(attributeValue);
                                } else if ("pic".equals(attributeName)) {
                                    brand.setPic(attributeValue);
                                }
                            }
                            brands.add(brand);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        map.put(groupName, brands);
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, List<ModelsSeries>> parseBrandDetail(InputStream xmlData) {
        List<ModelsSeries> modelsSeriesList = null;
        Map<String, List<ModelsSeries>> map = null;
        String groupName = null;
        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(xmlData, "UTF-8");
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        map = new LinkedHashMap<>();
                        break;
                    case XmlPullParser.START_TAG:
                        String tagName = xmlPullParser.getName();
                        if ("group".equals(tagName)) {
                            modelsSeriesList = new ArrayList<>();
                            groupName = xmlPullParser.getAttributeValue(0);
                        } else if ("brand".equals(tagName)) {
                            ModelsSeries modelsSeries = new ModelsSeries();
                            int count = xmlPullParser.getAttributeCount();
                            for (int i = 0; i < count; i++) {
                                String attributeName = xmlPullParser.getAttributeName(i);
                                String attributeValue = xmlPullParser.getAttributeValue(i);
                                if ("csShowName".equals(attributeName)) {
                                    modelsSeries.setCsShowName(attributeValue);
                                } else if ("pic".equals(attributeName)) {
                                    modelsSeries.setPic(attributeValue);
                                } else if ("csID".equals(attributeName)) {
                                    modelsSeries.setCsID(attributeValue);
                                } else if ("price_range".equals(attributeName)) {
                                    modelsSeries.setPrice_range(attributeValue);
                                }
                            }
                            modelsSeriesList.add(modelsSeries);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        map.put(groupName, modelsSeriesList);
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, List<Car>> parseCarDetail(InputStream xmlData) {
        List<Car> cars = null;
        Map<String, List<Car>> map = null;
        String displacements = null;
        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(xmlData, "UTF-8");
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        map = new LinkedHashMap<>();
                        break;
                    case XmlPullParser.START_TAG:
                        String tagName = xmlPullParser.getName();
                        if ("displacements".equals(tagName)) {
                            cars = new ArrayList<>();
                            displacements = xmlPullParser.getAttributeValue(0);
                        } else if ("car".equals(tagName)) {
                            Car car = new Car();
                            int count = xmlPullParser.getAttributeCount();
                            for (int i = 0; i < count; i++) {
                                String attributeName = xmlPullParser.getAttributeName(i);
                                String attributeValue = xmlPullParser.getAttributeValue(i);
                                if ("id".equals(attributeName)) {
                                    car.setId(attributeValue);
                                } else if ("name".equals(attributeName)) {
                                    car.setName(attributeValue);
                                } else if ("power".equals(attributeName)) {
                                    car.setPower(attributeValue);
                                } else if ("carYear".equals(attributeName)) {
                                    car.setCarYear(attributeValue);
                                } else if ("gearBox".equals(attributeName)) {
                                    car.setGearBox(attributeValue);
                                } else if ("price".equals(attributeName)) {
                                    car.setPrice(attributeValue);
                                } else if ("carReferPrice".equals(attributeName)) {
                                    car.setCarReferPrice(attributeValue);
                                }
                            }
                            cars.add(car);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (displacements != null) {
                            map.put(displacements, cars);
                        }
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static CarIntro parseCarIntro(InputStream xmlData) {
        CarIntro carIntro = null;
        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(xmlData, "UTF-8");
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tagName = xmlPullParser.getName();
                        if ("car_serial".equals(tagName)) {
                            carIntro = new CarIntro();
                            int count = xmlPullParser.getAttributeCount();
                            for (int i = 0; i < count; i++) {
                                String attributeName = xmlPullParser.getAttributeName(i);
                                String attributeValue = xmlPullParser.getAttributeValue(i);
                                if ("name".equals(attributeName)) {
                                    carIntro.setName(attributeValue);
                                } else if ("country".equals(attributeName)) {
                                    carIntro.setCountry(attributeValue);
                                } else if ("pic".equals(attributeName)) {
                                    carIntro.setPic(attributeValue);
                                } else if ("level".equals(attributeName)) {
                                    carIntro.setLevel(attributeValue);
                                } else if ("displacement".equals(attributeName)) {
                                    carIntro.setDisplacement(attributeValue);
                                }
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return carIntro;
    }
}
