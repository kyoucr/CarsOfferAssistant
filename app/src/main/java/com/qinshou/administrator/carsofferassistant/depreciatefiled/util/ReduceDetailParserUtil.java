package com.qinshou.administrator.carsofferassistant.depreciatefiled.util;

import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.CitiesListInfoBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.CityBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.DealerListBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.DealersBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.ListReduceCar;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.ProvinceBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.ReduceCar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;


/**
 * @author 岩 by 2016/7/8
 *         解析xml数据的工具类
 */

public class ReduceDetailParserUtil {


    /**
     * 解析降价车辆详情
     *
     * @param is 从网络获取销售汽车的信息流
     * @return 返回DealerListBean类
     */
    public static ListReduceCar parseDealers(InputStream is) {
        ListReduceCar listCar = new ListReduceCar();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();// 解析器工厂实例的构建
            XmlPullParser parser = factory.newPullParser();    // 解析器实例的构建
            parser.setInput(is, "UTF-8");

            String tagName = null;
            int eventType = parser.getEventType();

            EXIT_FLG:
            while (true) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break EXIT_FLG;
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();
                        if ("car_series".equals(tagName)) {
                            for (int i = 0; i < parser.getAttributeCount(); i++) {
                                String attributeName = parser.getAttributeName(i);
                                String attributeValue = parser.getAttributeValue(i);
                                if ("csName".equals(attributeName)) {
                                    listCar.setCsName(attributeValue);
                                } else if ("id".equals(attributeName)) {
                                    listCar.setId(attributeValue);
                                } else if ("dealerId".equals(attributeName)) {
                                    listCar.setDealerId(attributeValue);
                                } else if ("carId".equals(attributeName)) {
                                    listCar.setCarId(attributeValue);
                                } else if ("carName".equals(attributeName)) {
                                    listCar.setCarName(attributeValue);
                                } else if ("dealerName".equals(attributeName)) {
                                    listCar.setDealerName(attributeValue);
                                } else if ("original_price".equals(attributeName)) {
                                    listCar.setOriginal_price(attributeValue);
                                } else if ("new_price".equals(attributeName)) {
                                    listCar.setNew_price(attributeValue);
                                } else if ("carYear".equals(attributeName)) {
                                    listCar.setCarYear(attributeValue);
                                } else if ("reduce".equals(attributeName)) {
                                    listCar.setReduce(attributeValue);
                                } else if ("range".equals(attributeName)) {
                                    listCar.setRange(attributeValue);
                                } else if ("pic".equals(attributeName)) {
                                    listCar.setPic(attributeValue);
                                } else if ("last_day".equals(attributeName)) {
                                    listCar.setLast_day(attributeValue);
                                } else if ("acivityDate".equals(attributeName)) {
                                    listCar.setAcivityDate(attributeValue);
                                } else if ("shopAddress".equals(attributeName)) {
                                    listCar.setShopAddress(attributeValue);
                                } else if ("shopLat".equals(attributeName)) {
                                    listCar.setShopLat(attributeValue);
                                } else if ("csPic".equals(attributeName)) {
                                    listCar.setCsPic(attributeValue);
                                }
                            }
                        } else if ("series".equals(tagName)) {
                            ReduceCar data = new ReduceCar();
                            for (int i = 0; i < parser.getAttributeCount(); i++) {
                                String attributeName = parser.getAttributeName(i);
                                String attributeValue = parser.getAttributeValue(i).trim();

                                if ("id".equals(attributeName)) {
                                    data.setId(attributeValue);
                                } else if ("carId".equals(attributeName)) {
                                    data.setCarId(attributeValue);
                                } else if ("name".equals(attributeName)) {
                                    data.setName(attributeValue);
                                } else if ("carYear".equals(attributeName)) {
                                    data.setCarYear(attributeValue);
                                } else if ("vendorPrice".equals(attributeName)) {
                                    data.setVendorPrice(attributeValue);
                                } else if ("promotePrice".equals(attributeName)) {
                                    data.setPromotePrice(attributeValue);
                                } else if ("reduce".equals(attributeName)) {
                                    data.setReduce(attributeValue);
                                } else if ("csName".equals(attributeName)) {
                                    data.setCsName(attributeValue);
                                } else if ("pic".equals(attributeName)) {
                                    data.setPic(attributeValue);
                                } else if ("csPic".equals(attributeName)) {
                                    data.setCsPic(attributeValue);
                                    listCar.getListCars().add(data);
                                }
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tagName = null;
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return listCar;
    }
}
