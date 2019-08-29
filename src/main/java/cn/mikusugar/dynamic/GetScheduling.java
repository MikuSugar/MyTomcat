package cn.mikusugar.dynamic;

import cn.mikusugar.utils.INFO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class GetScheduling {
    private final static Logger LOGGER= LogManager.getLogger(GetScheduling.class);

    private final  String WEBROOT;
    private Map<String,String> requestMap;
    private Map<String,String> values;
    private Socket socket;
    private Dynamic dynamic;
    private String path;
    private boolean isOK;

    public GetScheduling(Map<String, String> requestMap, Socket socket) {
        this.requestMap = requestMap;
        this.socket = socket;
        this.isOK=true;
        String get=requestMap.get("GET");
        if(get.contains("?"))this.path=get.substring(0,get.indexOf("?"));
        else this.path=get;
        this.WEBROOT= INFO.getWEBROOT();
        Class<?> aClass = Init.init.getDynamicClass(WEBROOT + path);
        try {
            Object o = aClass.getConstructor().newInstance();
            this.dynamic=(Dynamic)o;
        } catch (Exception e) {
            LOGGER.error(e);
            isOK=false;
        }
        getValues();
    }

    private void getValues() {
        values=new HashMap<>();
        String get = requestMap.get("GET");
        String s = get.substring(get.indexOf('?') + 1);
        String[] split = s.split("&");
        for (String v:split)
        {
           try
           {
               String[] va = v.split("=");
               values.put(va[0],va[1]);
           }catch (Exception e)
           {
               LOGGER.error(e);
               LOGGER.warn("参数不合法");
           }
        }
    }

    public boolean start()
    {
        if(!isOK)return false;
        try {
            return dynamic.respone(requestMap,values,socket.getOutputStream());
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return false;
    }

}
