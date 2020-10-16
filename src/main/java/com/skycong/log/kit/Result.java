package com.skycong.log.kit;

import java.io.Serializable;

/**
 * @author ruanmingcong 2020.10.16 16:55
 */
public class Result implements Serializable {

    private static final long serialVersionUID = -5658340610029481776L;
    Integer code = 0;
    String msg = "OK";
    Object data;


    public static Result of(Integer code, String msg, Object data) {
        Result r = new Result();
        r.code = code;
        r.msg = msg;
        r.data = data;
        return r;
    }

    public static Result ofOk(Object... objects) {
        if (objects == null || objects.length == 0 || objects.length > 2) {
            return of(0, "OK", null);
        }
        if (objects.length == 1) {
            if (objects[0] instanceof String) {
                String msg = (String) objects[0];
                return of(0, msg, null);
            }
            return of(0, "OK", objects[0]);
        }
        if (objects[0] instanceof String) {
            String msg = (String) objects[0];
            return of(0, msg, objects[1]);
        }
        return of(0, "OK", null);
    }

    public static Result ofErr(Object... objects) {
        if (objects == null || objects.length == 0 || objects.length > 2) {
            return of(1, "Err", null);
        }
        if (objects.length == 1) {
            if (objects[0] instanceof String) {
                String msg = (String) objects[0];
                return of(1, msg, null);
            }
            return of(1, "Err", objects[0]);
        }
        if (objects[0] instanceof String) {
            String msg = (String) objects[0];
            return of(1, msg, objects[1]);
        }
        return of(1, "Err", null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
