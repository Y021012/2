package com.h2004c.mvp.presenter;

public interface ResultCallBack {
    void onSuccess(String json);
    void onFail(String msg);
}
