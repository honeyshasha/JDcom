package com.honey.jdcom.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.honey.jdcom.R;
import com.honey.jdcom.app.App;
import com.honey.jdcom.fragment.FragmentMe;
import com.honey.jdcom.presenter.LoginPresenter;
import com.honey.jdcom.view.LoginView;
import com.honey.jdcom.common.API;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private EditText et_pwd;
    private EditText et_mobile;
    private LoginPresenter loginPresenter;
    private String username;
    private String nickname;
    private String icon;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //第一次启动时初始化
        API.init(getApplicationContext());
        initView();
        initData();
    }

    private void initData() {
        loginPresenter = new LoginPresenter(this);
    }

    private void initView() {
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
    }
    /**
     * 登录
     *
     * @param v
     */
    public void but_load(View v) {
        loginPresenter.login(et_mobile.getText().toString(), et_pwd.getText().toString());
    }
    /**
     * 注册
     *
     * @param v
     */
    public void register(View v) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loginPresenter.regist(et_mobile.getText().toString(), et_pwd.getText().toString());
            }
        });
    }

    @Override
    public void nameError(final String msg) {
        //用户名错误
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void passError(final String msg) {
        //密码错误
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void loginSuccess(String code, final String msg) {
        //走登陆成功的逻辑
        if (code.equals("0")) {
            System.out.println(msg + "======  登录成功");
            try {
                API.sp.edit().putBoolean("islogin",true).commit();

                JSONObject obj = new JSONObject(msg);
                JSONObject data = obj.getJSONObject("data");
                icon = data.getString("icon");
                nickname = data.getString("nickname");
                username = data.getString("username");
                uid = data.getString("uid");

                API.editor.putString("username", username).commit();
                API.editor.putString("icon", icon).commit();
                API.editor.putString("nickname", nickname).commit();
                API.editor.putString("uid", uid).commit();
               // URL url = new URL(icon);
//                HttpURLConnection uc = (HttpURLConnection) url.openConnection();
//                uc.connect();
//                InputStream in = uc.getInputStream();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        }else{
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loginFail(String code, String msg) {
        //走登陆失败的逻辑
        System.out.println("登录失败");
    }

    @Override
    public void failure(Call call, IOException e) {
        //走请求失败的逻辑
    }
    public void tui(View v){
        finish();
    }
}
