package com.honey.jdcom.entity;

/**
 * Created by 小傻瓜 on 2017/10/18.
 */

public class ShopCar {

    /**
     * msg : 加购成功
     * code : 0
     */
    private String msg;
    private String code;
    private int uid;
    private int pid;
    private int sellerid;

    public ShopCar(String msg, String code, int uid, int pid, int sellerid) {
        this.msg = msg;
        this.code = code;
        this.uid = uid;
        this.pid = pid;
        this.sellerid = sellerid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getSellerid() {
        return sellerid;
    }

    public void setSellerid(int sellerid) {
        this.sellerid = sellerid;
    }

    @Override
    public String toString() {
        return "ShopCar{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", uid=" + uid +
                ", pid=" + pid +
                ", sellerid=" + sellerid +
                '}';
    }
}
