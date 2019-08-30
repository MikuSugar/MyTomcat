package cn.mikusugar.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
//IO工具类
public class MyIOutls {
    private final static Logger LOGGER= LogManager.getLogger(MyIOutls.class);

    public static void send(File file,OutputStream out)
    {
        LOGGER.info("发送静态资源");
        FileInputStream input=null;
        try {
            input= new FileInputStream(file);
            byte[] bytes=new byte[1024*8];
            int len=0;
            while ((len=input.read(bytes))!=-1)
            {
                out.write(bytes,0,len);
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("静态资源发送完毕");
    }
    public static String getString(InputStream input)
    {
        LOGGER.info("开始接收请求头");
        BufferedReader reader=null;
        try{
            reader=new BufferedReader(new InputStreamReader(input));
            String line = null;
            StringBuilder sb=new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line+"\n");
                if(line.isEmpty())break;
            }
            if(sb.toString().startsWith("POST"))
            {
                char[] chars=new char[4096*20];
                int len = reader.read(chars, 0, chars.length);
                sb.append(chars,0,len);
            }
            return sb.toString();

        }catch (IOException e)
        {
            LOGGER.error(e);
        }
        finally {
            LOGGER.info("请求头信息接收成功");
        }
       return null;
    }
}
