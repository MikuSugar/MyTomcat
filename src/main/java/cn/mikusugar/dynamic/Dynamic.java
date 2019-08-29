package cn.mikusugar.dynamic;

import java.io.OutputStream;
import java.util.Map;

//动态服务接口类
public interface Dynamic {
    boolean respone(Map<String,String> requestMap, Map<String,String> values, OutputStream out);
}
