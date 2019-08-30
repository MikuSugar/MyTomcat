# README
## MyTomcat 1.2
## 一个简单的服务器
### 已实现功能
+ GET请求
+ POST请求
### 项目启动步骤
+ 导入到ide工具
+ 等待依赖导入
+ 依据需要更改配置文件（必须更改WEBROOT)
+ 编译并运行 cn.mikusugar包里的Start的main方法即可
### 动态请求
+ WEBROOT目录下的dynamic文件夹下为动态请求资源.class文件
+ .class文件为实现类cn.mikusugar.dynamic.Dynamic接口的类（必须带有默认构造方法）编译后的.class文件
+ HelloWorld.class为一个简单的样例
```java
import cn.mikusugar.dynamic.Dynamic;
import cn.mikusugar.status.HttpStatus;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HelloWorld implements Dynamic {
    @Override
    public boolean respone(Map<String, String> requestMap, Map<String, String> values, OutputStream out) {
        try {
            out.write(HttpStatus.OK.getBytes());
            out.write("Content-type:text/html\n\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        String res="欢迎 "+values.getOrDefault("name","")+values.get("age")+"使用";
        try {
            out.write(res.getBytes("GBK"));
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
```
### 其他
+ 配置文件在 resources/config.properties
+ resource/web 为一些测试用的资源
+ 项目目录中不能存在中文路径
+ 水平有限


