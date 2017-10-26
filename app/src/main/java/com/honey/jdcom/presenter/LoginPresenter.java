package com.honey.jdcom.presenter;

import android.text.TextUtils;

import com.honey.jdcom.main.LoginActivity;
import com.honey.jdcom.model.LoginModel;
import com.honey.jdcom.utils.LoginUtils;
import com.honey.jdcom.view.LoginView;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;

/**
 * Created by robot on 2017/9/27.
 */

public class LoginPresenter/* implements LoginModel.ILogin*/ {

    private LoginView loginView;
    private LoginModel loginModel;

    public LoginPresenter(LoginView loginView)
    {
        this.loginView=loginView;
        loginModel=new LoginModel();
    }

    /**
     * 登录
     * @param mobile
     * @param pass
     */
    public void login(String mobile,String pass)
    {
        if(TextUtils.isEmpty(mobile))
        {
            loginView.nameError("用户名不能为空");
            return;
        }
        if(TextUtils.isEmpty(pass))
        {
            loginView.passError("密码不能为空");
            return;
        }
        loginModel.login(mobile,pass,loginView);
    }

    /**
     * 注册
     */
    public void regist(String mobile,String pass)
    {
        if(TextUtils.isEmpty(mobile))
        {
            loginView.nameError("号码不能为空");
            return;
        }
        if(TextUtils.isEmpty(pass))
        {
            loginView.passError("密码不能为空");
            return;
        }
        //注册具体操作的方法
        loginModel.regist(mobile,pass,loginView);
    }
}