package com.qinshou.administrator.carsofferassistant.utils;

import com.qinshou.administrator.carsofferassistant.bean.DetailImageDefaultHandler;
import com.qinshou.administrator.carsofferassistant.bean.MyDefaultHandler;
import com.qinshou.administrator.carsofferassistant.bean.Series;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by zyj on 2016/7/6.
 */

public class XMLParserIndependenceUtil {
    /**
     * 解析自助选车列表
     *
     * @param is
     * @return
     */
    public static List<Series> parserIndependence(InputStream is) {
        // 步骤：
        // ①解析器工厂实例的构建
        SAXParserFactory factory = SAXParserFactory.newInstance();
        // ③解析
        MyDefaultHandler handler = new MyDefaultHandler();
        try {
            // ②解析器
            SAXParser parser = factory.newSAXParser();

            parser.parse(is, handler);

            // ④处理解析后的结果

        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return handler.getSeriesList();
    }

    /**
     * 解析汽车图片详细图片
     *
     * @return
     */
    public static List<String> parseDetailImage(InputStream is) {
        // 步骤：
        // ①解析器工厂实例的构建
        SAXParserFactory factory = SAXParserFactory.newInstance();
        // ③解析
        DetailImageDefaultHandler defaultHandler = new DetailImageDefaultHandler();
        try {
            // ②解析器
            SAXParser parser = factory.newSAXParser();

            parser.parse(is, defaultHandler);

        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defaultHandler.getImagesUrls();
    }
}
