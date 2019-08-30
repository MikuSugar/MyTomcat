package cn.mikusugar.dynamic;

import cn.mikusugar.utils.INFO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class PostScheduling {
    private final static Logger LOGGER= LogManager.getLogger(PostScheduling.class);

    private final  String WEBROOT;
    private Map<String,String> requestMap;
    private Map<String,String> values;
    private Socket socket;
    private Dynamic dynamic;
    private String path;
    private boolean isOK;

    public PostScheduling(Map<String, String> requestMap, Socket socket) {
        this.requestMap = requestMap;
        this.socket = socket;
        this.isOK=true;
        this.path=requestMap.get("POST");
        this.WEBROOT= INFO.getWEBROOT();
        values=new HashMap<>();
        if(requestMap.get("postvalues").isEmpty())
        {
            isOK=false;
            return;
        }
        getValues();
        Class<?> aClass = Init.init.getDynamicClass(WEBROOT + path);
        try {
            Object o = aClass.getConstructor().newInstance();
            this.dynamic=(Dynamic)o;
        } catch (Exception e) {
            LOGGER.error(e);
            isOK=false;
        }

    }

    private void getValues() {
        String postvalues = requestMap.get("postvalues");
        String[] split = postvalues.split("&");
        for (String s:split)
        {
            String[] split1 = s.split("=");
            try
            {
                values.put(split1[0],split1[1]);
            }catch (Exception e)
            {
                LOGGER.error(e);
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
