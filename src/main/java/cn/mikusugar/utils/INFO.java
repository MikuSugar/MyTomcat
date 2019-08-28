package cn.mikusugar.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * 读取配置文件工具类
 */
public class INFO {
    private  String WEBROOT;
    private  int PORT;
    private  int ConcurrentNumber;
    private  final static Logger LOGGER= LogManager.getLogger(INFO.class);
    private  final static INFO INFO=new INFO();

    public static String getWEBROOT() {
        return INFO.WEBROOT;
    }

    public static int getPORT() {
        return INFO.PORT;
    }

    public static int getConcurrentNumber()
    {
        return INFO.ConcurrentNumber;
    }
    private INFO()
    {
        Properties properties=new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
            WEBROOT=properties.getProperty("WEBROOT");
            PORT=Integer.parseInt(properties.getProperty("PORT"));
            ConcurrentNumber=Integer.parseInt(properties.getProperty("ConcurrentNumber"));
            LOGGER.info("配置文件读取成功");
        } catch (IOException e) {
            LOGGER.error(e);
            LOGGER.warn("配置文件读取失败，采用默认配置");
            WEBROOT="/Users/fangjie/Code/MyTomcat/src/main/resources/web";
            PORT=8080;
            ConcurrentNumber=100;
        }
    }

}
