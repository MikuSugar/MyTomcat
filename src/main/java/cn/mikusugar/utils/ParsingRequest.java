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
        if(info.isEmpty())return null;
        Map<String,String > res=new HashMap<>();
        String[] infos = info.split("\n");
        if(infos.length<=0)return null;
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
        for (int i=1;i<infos.length;i++)
        {
            String[] split = infos[i].split(":");
            res.put(split[0],split[1]);
        }
        return res;
    }
}
