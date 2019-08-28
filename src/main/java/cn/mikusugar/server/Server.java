package cn.mikusugar.server;

import cn.mikusugar.status.HttpStatus;
import cn.mikusugar.utils.MyIOutls;
import cn.mikusugar.utils.ParsingRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Server extends Thread {
    private final static Logger LOGGER= LogManager.getLogger(Server.class);
    private String WEBROOT;
    private Socket socket;
    private Semaphore conNum;

    public Server(Socket socket,String WEBROOT,Semaphore conNum) {
        this.socket = socket;
        this.WEBROOT=WEBROOT;
        this.conNum=conNum;
    }
    @Override
    public void run() {
        try {
            Map<String,String> request= ParsingRequest.retrunMap(MyIOutls.getString(socket.getInputStream()));
            if(request==null) return;
            File file=new File(WEBROOT+request.get("GET"));
            if(file.isFile())
            {
                LOGGER.info(socket+" GET "+file.toString());
                OutputStream out = socket.getOutputStream();
                out.write(HttpStatus.OK.getBytes());
                out.write("Content-type:text/html\n\n".getBytes());
                MyIOutls.send(file,out);
            }
            else
            {
                LOGGER.warn(socket.toString()+"404");
                OutputStream out = socket.getOutputStream();
                out.write(HttpStatus.NO_FOUND.getBytes());
            }
        } catch (IOException e) {
            LOGGER.error(e);
        }
        finally {
            conNum.release();
            try {
                socket.close();
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
    }

}
