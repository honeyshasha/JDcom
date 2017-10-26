package com.honey.jdcom.view;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by robot on 2017/9/27.
 */

public interface LoginView {
    void nameError(String msg);
    void passError(String msg);
    void loginSuccess(String code, String msg);
    void loginFail(String code, String msg);
    void failure(Call call, IOException e);
}
