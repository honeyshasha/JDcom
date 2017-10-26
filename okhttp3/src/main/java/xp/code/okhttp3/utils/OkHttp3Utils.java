package xp.code.okhttp3.utils;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import xp.code.okhttp3.utils.cookie.CookieJarImpl;
import xp.code.okhttp3.utils.cookie.store.MemoryCookieStore;
import xp.code.okhttp3.utils.interceptor.LoggingInterceptor;

/**
 * Created by XP on 2017/9/8.
 */
//单例模式来实现工具类
public class OkHttp3Utils {
    /**
     * 为什么只添加了请求头就好使了
     * 因为Cookie的数据传递在请求头中实现的
     * Cookie的传递是基于请求头中的
     */
    private static Handler mHandler = null;

    public synchronized static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }
    private static OkHttpClient okHttpClient;
    //私有化的构造器
    private OkHttp3Utils(){}
    public static OkHttpClient getInstance()
    {
        if(okHttpClient==null)
        {
            synchronized (OkHttp3Utils.class)
            {
                //缓存的目录
                File sd_cache=new File(Environment.getExternalStorageDirectory(),"ok_cache");
                //缓存存储的大小
                int cacheSize=1024*1024*10;
                //OkHttp3拦截器
                HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        //Log.i("xxx", message.toString());
                    }
                });
                //OkHttp3的拦截器日志分类  4种
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                okHttpClient=new OkHttpClient.Builder()
                        .addInterceptor(interceptor)
//                        .addInterceptor(new LoggingInterceptor())   //第二周周考必选的拦截器才能访问百度接口
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30,TimeUnit.SECONDS)
                        .connectTimeout(60,TimeUnit.SECONDS)
//                        .addNetworkInterceptor(new NetWorkInterceptor())
                        .cache(new Cache(sd_cache,cacheSize))
                        //保存Cookie
                        .cookieJar(new CookieJarImpl(new MemoryCookieStore()))
                        .build();
            }
        }
        return okHttpClient;
    }

    //get请求
    public static void doGet(String url, Map<String,String> headers, Callback callback)
    {
        OkHttpClient okHttpClient=getInstance();
        //创建Request
        Request.Builder builder=new Request.Builder();
        builder.url(url);
        //添加请求头
        if(headers!=null)
        {
            for(String header:headers.keySet())
            {
                builder.addHeader(header,headers.get(header));
            }
        }
        Request request=builder.build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    //post请求
    public static void doPost(String url,Map<String,String> headers, Map<String, String> params, Callback callback)
    {
        OkHttpClient okHttpClient=getInstance();

        FormBody.Builder builder=new FormBody.Builder();
        if(params!=null)
        {
            for(String key:params.keySet())
            {
                builder.add(key,params.get(key));
            }
        }
        Request.Builder reqbuilder=new Request.Builder();
        reqbuilder.post(builder.build());
        if(headers!=null)
        {
            for(String header:headers.keySet())
            {
                reqbuilder.addHeader(header,headers.get(header));
            }
        }
        reqbuilder.url(url);
        Request request=reqbuilder.build();

        Call call=okHttpClient.newCall(request);
        call.enqueue(callback);
    }
}
