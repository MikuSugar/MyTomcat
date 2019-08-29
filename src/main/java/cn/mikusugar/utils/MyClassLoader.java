package cn.mikusugar.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 自定义类加载器
 */
public class MyClassLoader extends ClassLoader {

    private final static Logger LOGGER= LogManager.getLogger(MyClassLoader.class);
    @Override
    protected Class<?> findClass(String name){
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(name));
            return defineClass(null, bytes, 0, bytes.length);
        } catch (IOException e) {
            LOGGER.error("class文件加载失败"+e);
            return null;
        }
    }
}
