package cn.mikusugar.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

//IO工具类
public class MyIOutls {
    private final static Logger LOGGER= LogManager.getLogger(MyIOutls.class);

    public static void send(File file,OutputStream out)
    {
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
    }
    public static String getString(InputStream input)
    {
        BufferedReader reader=null;
        try{
            reader=new BufferedReader(new InputStreamReader(input));
            StringBuilder sb=new StringBuilder();
            while (reader.ready())
            {
                sb.append(reader.readLine()+'\n');
            }
            if(sb.length()>0)sb.deleteCharAt(sb.length()-1);
            return sb.toString();
        }catch (IOException e)
        {
            LOGGER.error(e);
        }
       return null;
    }
}
