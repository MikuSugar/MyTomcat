package cn.mikusugar.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Content-type工具类
 */
public class ContentType {
    private final static Logger LOGGER= LogManager.getLogger(ContentType.class);
    private static ContentType contentType=new ContentType();
    public static ContentType getType(){return contentType;}

    private final static String pre="Content-type:";
    private Map<String,String> book;

    private ContentType()
    {
        LOGGER.info("开始读取ContentType配置文件");
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("ContentType.txt");
        book=new HashMap<>();
        book.put(null,pre+"application/octet-stream\n\n");
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        try {
            while (reader.ready())
            {
                String s = reader.readLine();
                String[] split = s.split(" = ");
                if(split.length==2)
                {
                    book.put(split[0],pre+split[1]+"\n\n");
                }
            }
        }catch (IOException e)
        {
            LOGGER.error(e);
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                LOGGER.error(e);
            }
            LOGGER.info("ContentType配置文件读取完毕");
        }
    }
    public String find(String name)
    {
        return book.getOrDefault(name,"application/octet-stream\n\n");
    }
    public byte[] findBytes(String name)
    {
        return find(name).getBytes();
    }
    public byte[] findBytes(String name,String charsetName)
    {
        try {
            return find(name).getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e);
            return find(name).getBytes();
        }
    }
    public String findByPath(String path)
    {
        if(path.lastIndexOf('.')>=0)
        {
            String s = path.substring(path.lastIndexOf('.'));
            return find(s);
        }
        return book.get(null);
    }
    public byte[] findBytesByPath(String path)
    {
        return findByPath(path).getBytes();
    }
    public byte[] findBytesByPath(String path,String charsetName)
    {
        try {
            return findByPath(path).getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e);
            return findBytesByPath(path);
        }
    }
}
