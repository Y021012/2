package com.h2004c.mvp.presenter;

import android.util.Log;
import android.view.View;

import com.h2004c.mvp.ApiService;
import com.h2004c.mvp.MainActivity;
import com.h2004c.mvp.model.MainModel;
import com.h2004c.mvp.view.MainView;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class MainPresenter {

    private final MainModel mModel;
    private MainView mView;

    //view本质上是MainActivity
    public MainPresenter(MainView view){
        mView = view;
        mModel = new MainModel();
    }
    public void login(String name, String psd) {
        mModel.login(name,psd, new ResultCallBack() {
                @Override
            public void onSuccess(String json) {
                //p层收到m层传递的结果
                mView.onSuccess(json);
            }

            @Override
            public void onFail(String msg) {
                mView.onFail(msg);
            }
        });
    }
}
