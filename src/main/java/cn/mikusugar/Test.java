package cn.mikusugar;


import cn.mikusugar.utils.MyIOutls;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Test {
    private final static Logger LOGGER=LogManager.getLogger(Test.class);

    public static void main(String[] args) throws FileNotFoundException {
        String res= MyIOutls.getString(new FileInputStream(new File("/Users/fangjie/Code/MyTomcat/src/main/resources/a.txt")));
        System.out.println(res);
    }

}
