package com.qinshou.administrator.carsofferassistant.utils;

import com.qinshou.administrator.carsofferassistant.bean.MyDefaultHandler;
import com.qinshou.administrator.carsofferassistant.bean.Series;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by zyj on 2016/7/6.
 */

public class XMLParserIndependenceUtil {
    public static List<Series> parserIndependence(String data) {
        // 步骤：
        // ①解析器工厂实例的构建
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            // ②解析器
            SAXParser parser = factory.newSAXParser();

            // ③解析
            MyDefaultHandler handler = new MyDefaultHandler();

            parser.parse(data, handler);

            // ④处理解析后的结果
            List<Series> seriesList = handler.getSeriesList();
            return seriesList;

        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
