package cn.mikusugar;

import cn.mikusugar.server.Server;

import cn.mikusugar.utils.INFO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;


//服务启动类
public class Start{

    private final static Logger LOGGER= LogManager.getLogger(Start.class);
    private final static String WEBROOT= INFO.getWEBROOT();
    private final static int PORT =INFO.getPORT();
    private final static int ConcurrentNumber=INFO.getConcurrentNumber();


    public static void main(String[] args) {
        Semaphore conNum=new Semaphore(ConcurrentNumber);
        LOGGER.info("服务器启动中\n"+
                "\n" +
                "                     _ooOoo_\n" +
                "                    o8888888o\n" +
                "                    88\" . \"88\n" +
                "                    (| -_- |)\n" +
                "                     O\\ = /O\n" +
                "                 ____/`---'\\____\n" +
                "               .   ' \\\\| |// `.\n" +
                "                / \\\\||| : |||// \\\n" +
                "              / _||||| -:- |||||- \\\n" +
                "                | | \\\\\\ - /// | |\n" +
                "              | \\_| ''\\---/'' | |\n" +
                "               \\ .-\\__ `-` ___/-. /\n" +
                "            ___`. .' /--.--\\ `. . __\n" +
                "         .\"\" '< `.___\\_<|>_/___.' >'\"\".\n" +
                "        | | : `- \\`.;`\\ _ /`;.`/ - ` : | |\n" +
                "          \\ \\ `-. \\_ __\\ /__ _/ .-` / /\n" +
                "  ======`-.____`-.___\\_____/___.-`____.-'======\n" +
                "                     `=---='\n" +
                " \n" +
                "  .............................................\n" +
                "           佛祖保佑             永无BUG\n" +
                " ");
        try {
            ServerSocket server=new ServerSocket(PORT);
            LOGGER.info("服务器已启动,端口"+ PORT+"  并发数："+ConcurrentNumber);
            while (true)
            {
                Socket socket = server.accept();
                conNum.acquire();
                LOGGER.info("服务器负载"+((ConcurrentNumber-conNum.availablePermits())*100/ConcurrentNumber)+"%\n"+socket+" 连接成功");
                new Server(socket,WEBROOT,conNum).start();
            }
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
    }
}
