package com.qinshou.administrator.carsofferassistant.utils;

import com.qinshou.administrator.carsofferassistant.bean.Car;

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
 * Created by 禽兽先生 on 2016.
 */

public class ParseXml {
    public static Map<String, List<Car>> parseSelectModels(InputStream xmlData) {
        List<Car> cars = null;
        Map<String, List<Car>> map = null;
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
                            cars = new ArrayList<>();
                            groupName = xmlPullParser.getAttributeValue(0);
                        } else if ("master".equals(tagName)) {
                            Car car = new Car();
                            int count = xmlPullParser.getAttributeCount();
                            for (int j = 0; j < count; j++) {
                                String attributeName = xmlPullParser.getAttributeName(j);
                                String attributeValue = xmlPullParser.getAttributeValue(j);
                                if ("bsID".equals(attributeName)) {
                                    car.setBsID(attributeValue);
                                } else if ("bsName".equals(attributeName)) {
                                    car.setBsName(attributeValue);
                                } else if ("pic".equals(attributeName)) {
                                    car.setPic(attributeValue);
                                }
                            }
                            cars.add(car);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        map.put(groupName, cars);
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
}
