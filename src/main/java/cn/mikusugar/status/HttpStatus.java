package cn.mikusugar.status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;

public enum HttpStatus {
    OK("HTTP/1.1 200 OK"),NO_FOUND("HTTP/1.1 404 Not Found\n");

    private final static Logger LOGGER= LogManager.getLogger(HttpStatus.class);
    private String status;
    HttpStatus(String status) {
        this.status=status;
    }
    public byte[] getBytes()
    {
        return status.getBytes();
    }

    public byte[] getBytes(String charsetName)
    {
        try {
            return status.getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e);
        }
        return getBytes();
    }
}
