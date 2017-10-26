package com.honey.jdcom.model;

import com.honey.jdcom.common.API;
import com.honey.jdcom.utils.LoginUtils;
import com.honey.jdcom.view.LoginView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by robot on 2017/9/27.
 */

public class LoginModel {
    /**
     * 登录的操作
     * @param mobile
     * @param pwd
     */
    public void login(String mobile,String pwd,final LoginView iLogin)
    {
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody.Builder builder=new FormBody.Builder();
        builder.add("mobile",mobile);
        builder.add("password",pwd);
        FormBody body=builder.build();
        final Request request=new Request.Builder().post(body).url(API.LOGIN_API).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iLogin.failure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response!=null&&response.isSuccessful())
                {
                    String result=response.body().string();
//                    System.out.println("result========="+result);
                    try {
                        JSONObject obj=new JSONObject(result);
                        String code = obj.getString("code");
                        if(code.equals("0"))
                        {
                            iLogin.loginSuccess(code,result);
                        }else{
                            iLogin.loginFail(code,"111");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
    /**
     * 注册的操作
     * @param mobile
     * @param pwd
     */
    public void regist(final String mobile, final String pwd,final LoginView iLogin)
    {
        LoginUtils.getResult(mobile, pwd, new LoginUtils.IRegist() {
            @Override
            public void registSuccess(String result) {
                System.out.println("注册成功");
            }
            @Override
            public void registFail() {
                iLogin.loginFail(mobile,pwd);
            }
        });
    }
}
