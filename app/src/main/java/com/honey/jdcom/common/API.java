package com.honey.jdcom.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 小傻瓜 on 2017/9/30.
 */

public class API {
    //登录接口
    public  static  final String LOGIN_API = "http://120.27.23.105/user/login";
    //注册接口
    public  static  final String REGISTER_API = "http://120.27.23.105/user/reg";
    //头像接口
    public  static  final String PHOTO_API="http://120.27.23.105/file/upload";
    //获取用户信息接口
    public  static  final String USER_API="http://120.27.23.105/user/getUserInfo";
    //首页广告接口
    public  static  final String SHOUYE_API="http://120.27.23.105/ad/getAd";
    //商品分类接口
    public  static  final String SHOPPING_API="http://120.27.23.105/product/getCatagory";
    //商品子分类接口
    public  static  final String SHOP_API="http://120.27.23.105/product/getProductCatagory?cid=";
    //当前子分类下的商品列表
    public  static  final String ZIFENLEI_API="http://120.27.23.105/product/getProducts?pscid=";
    //商品详情接口
    public  static  final String DETAL_API="http://120.27.23.105/product/getProductDetail";
    //根据关键词搜索商品
    public  static  final String GUANJIAN_API="http://120.27.23.105/product/searchProducts";
    //修改昵称
    public  static  final String NickName_API="http://120.27.23.105/user/updateNickName";
    //添加购物车接口
    public static final String ADDCART_API="http://120.27.23.105/product/addCart";
    //删除购物车
    public static final String DELCART_API="http://120.27.23.105/product/deleteCart";
    //查询购物车
    public static final String GETCARTS_API="http://120.27.23.105/product/getCarts";
    //更新购物车
    public static final String UPDATECARTS_API="http://120.27.23.105/product/updateCarts?uid=71&sellerid=1&pid=1&selected=0&num=10";
    //修改订单状态修改订单状态
    public static final String UPDATEOrder_API="http://120.27.23.105/product/updateOrder?uid=71&status=1&orderId=1";
    //创建订单
    public static final String CREATE_API="http://120.27.23.105/product/createOrder";
    //订单列表
    public static final String GETORDER_PAI="http://120.27.23.105/product/getOrders";
    //常用收货地址列表
    public static final String GETADD_API="http://120.27.23.105/user/getAddrs?uid=71";
    //添加常用收获地址
    public static final String ADDAddR_API="http://120.27.23.105/user/addAddr?uid=71&addr=北京市昌平区金域国际1-1-1&mobile=18612991023&name=kson";
    //修改常用收货地址
    public static final String UPDATEADD_API="http://120.27.23.105/user/updateAddr?uid=71&addrid=2";
    //设置默认地址
    public static final String SETADDR_API="http://120.27.23.105/user/setAddr?uid=71&addrid=3&status=1";
    //获取默认地址
    public static final String GETDefaultAddr_API="http://120.27.23.105/user/getDefaultAddr?uid=71";

    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;

    public static String username;
    public static String uid;
    public static String nickname;
    public static String icon;




    public static void init(Context context){
        sp=context.getSharedPreferences("gss",Context.MODE_PRIVATE);
        editor=sp.edit();
//
//        username=sp.getString("username","未登录");
//        username=sp.getString("nickname","未登录");
//        username=sp.getString("uid","未登录");
//        username=sp.getString("icon","");
    }
}
