package com.qinshou.administrator.carsofferassistant.utils;

import android.util.Log;

import com.qinshou.administrator.carsofferassistant.bean.Car;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 禽兽先生 on 2016.
 */

public class ParseXml {
    public static Map<String, List<Car>> parseSelectModels(InputStream xmlData) {
        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(xmlData, "UTF-8");
            int eventType = xmlPullParser.getEventType();
            List<Car> cars = null;
//            Car car = null;
            Map<String, List<Car>> map = null;
            String groupName = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        map = new HashMap<>();
                        break;
                    case XmlPullParser.START_TAG:
                        if ("group".equals(tagName)) {
                            cars = new ArrayList<>();
                            groupName = xmlPullParser.nextText();
                        } else if ("master".equals(tagName)) {
                            Car car = new Car();
                            int count = xmlPullParser.getAttributeCount();
                            for (int i = 0; i < count; i++) {
                                String attributeName = xmlPullParser.getAttributeName(i);
                                String attributeValue = xmlPullParser.getAttributeValue(i);
                                if ("bsID".equals(attributeName)) {
                                    car.setBsID(attributeValue);
                                } else if ("bsName".equals(attributeName)) {
                                    car.setBsName(attributeValue);
                                } else if ("pic".equals(attributeName)) {
                                    car.setPic(attributeValue);
                                }
                            }
                            cars.add(car);
                            Log.i("toString", car.toString());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        map.put(groupName, cars);
                        break;
                }
                eventType = xmlPullParser.next();
            }

            return map;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
