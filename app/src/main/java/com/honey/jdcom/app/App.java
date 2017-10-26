package com.honey.jdcom.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.honey.jdcom.common.API;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by 小傻瓜 on 2017/10/9.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
        API.init(getApplicationContext());
    }
}
