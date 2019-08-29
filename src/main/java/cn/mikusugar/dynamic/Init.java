package cn.mikusugar.dynamic;


import cn.mikusugar.utils.INFO;
import cn.mikusugar.utils.MyClassLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态页面初始化加载
 */
public class Init {
    private final static Logger LOGGER= LogManager.getLogger(Init.class);
    private Map<String,Class<?>> map;
    private ClassLoader loader;

    public final static Init init=new Init();

    private Init()
    {
        LOGGER.info("动态目录加载中");
        this.map=new HashMap<>();
        String path= INFO.getWEBROOT()+"/dynamic";
        this.loader=new MyClassLoader();
        helpLoad(path);
        LOGGER.info("动态目录加载完成");
    }

    private void helpLoad(String path) {
        File file=new File(path);
        if(file.isDirectory())
        {
            for (File next:file.listFiles())
            {
                helpLoad(next.getPath());
            }
        }
        else if(file.isFile())
        {
            add(file.getPath());
        }
    }

    private void add(String path)
    {
        try {
            Class<?> aClass = loader.loadClass(path);
            this.map.put(path,aClass);
        } catch (ClassNotFoundException e) {
            LOGGER.error(e);
        }
    }

    public Class<?> getDynamicClass(String path)
    {
        return map.get(path);
    }



}
