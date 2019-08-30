package cn.mikusugar.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理请求类
 */
public class ParsingRequest {
    private final static Logger LOGGER= LogManager.getLogger(ParsingRequest.class);
    public static Map<String,String> retrunMap(String info)
    {
        LOGGER.info("解析请求信息");
        if(info==null||info.isEmpty())
        {
            LOGGER.warn("请求信息为空");
            return new HashMap<>();
        }
        Map<String,String > res=new HashMap<>();
        String[] infos = info.split("\n");
        if(infos.length<=0)
        {
            LOGGER.warn("请求信息格式错误");
            return res;
        }
        if(infos[0].contains("GET")&&infos[0].contains("HTTP"))
        {
            String[] s = infos[0].split(" ");
            try {
                res.put("GET", URLDecoder.decode(s[1], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                LOGGER.error(e);
            }
            res.put("HTTP",s[2]);
        }
        else if(infos[0].contains("POST")&&infos[0].contains("HTTP"))
        {
            String[] s = infos[0].split(" ");
            try {
                res.put("POST", URLDecoder.decode(s[1], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                LOGGER.error(e);
            }
            res.put("HTTP",s[2]);
        }
        int cur=1;
        while (cur<infos.length)
        {
            if(infos[cur].isEmpty())
            {
                cur++;
                break;
            }
            String[] split = infos[cur++].split(":");
            res.put(split[0],split[1]);
        }
        if(cur<infos.length)
        {
            StringBuilder sb=new StringBuilder();
            while (cur<infos.length)
            {
                sb.append(infos[cur++]);
            }
            res.put("postvalues",sb.toString());
        }
        LOGGER.info("请求信息解析完成");
        return res;
    }
}
