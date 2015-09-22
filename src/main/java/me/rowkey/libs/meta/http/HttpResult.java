package me.rowkey.libs.meta.http;

import me.rowkey.libs.meta.Bean;
import org.apache.http.HttpStatus;

/**
 * Author: Bryant
 * Date: 14/11/19
 * Time: 下午3:46
 */
public class HttpResult implements Bean {

    private String result;

    private int code = HttpStatus.SC_OK;

    private String errmsg;

    public HttpResult(int code, String result, String errmsg) {
        this.code = code;
        this.result = result;
        this.errmsg = errmsg;
    }

    public HttpResult(int code) {
        this.code = code;
    }

    public HttpResult(String result) {
        this.result = result;
    }

    public HttpResult(int code, String errmsg) {
        this.code = code;
        this.errmsg = errmsg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return HttpStatus.SC_OK == this.code;
    }
}
