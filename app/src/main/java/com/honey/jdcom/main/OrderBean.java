package com.honey.jdcom.main;

import java.util.List;

/**
 * Created by robot on 2017/10/22.
 */

public class OrderBean {

    /**
     * msg : 请求成功
     * code : 0
     * data : [{"createtime":"2017-10-22T15:23:11","orderid":895,"price":159,"status":1,"uid":147},{"createtime":"2017-10-22T15:24:04","orderid":896,"price":159,"status":1,"uid":147},{"createtime":"2017-10-22T15:28:44","orderid":898,"price":0,"status":0,"uid":147},{"createtime":"2017-10-22T15:36:36","orderid":903,"price":159,"status":0,"uid":147},{"createtime":"2017-10-22T15:36:41","orderid":904,"price":159,"status":0,"uid":147},{"createtime":"2017-10-22T15:37:14","orderid":907,"price":0,"status":0,"uid":147},{"createtime":"2017-10-22T15:37:22","orderid":908,"price":0,"status":0,"uid":147},{"createtime":"2017-10-22T15:56:16","orderid":919,"price":399,"status":0,"uid":147},{"createtime":"2017-10-22T15:59:45","orderid":922,"price":0,"status":0,"uid":147},{"createtime":"2017-10-22T18:38:19","orderid":1058,"price":99.99,"status":0,"uid":147}]
     * page : 1
     */

    private String msg;
    private String code;
    private String page;
    private List<DataBean> data;

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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createtime : 2017-10-22T15:23:11
         * orderid : 895
         * price : 159.0
         * status : 1
         * uid : 147
         */

        private String createtime;
        private int orderid;
        private double price;
        private int status;
        private int uid;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getOrderid() {
            return orderid;
        }

        public void setOrderid(int orderid) {
            this.orderid = orderid;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
