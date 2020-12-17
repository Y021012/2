package com.h2004c.mvp.model;

import android.util.Log;

import com.h2004c.mvp.ApiService;
import com.h2004c.mvp.presenter.ResultCallBack;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class MainModel {
    public void login(String name, String psd, final ResultCallBack callBack) {
        //网络请求,属于M
        //2.如果能到这里说明用户名和密码都不为空
        new Retrofit.Builder()
                .baseUrl(ApiService.sUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.addConverterFactory(GsonConverterFactory.create());
                .build()
                .create(ApiService.class)
                .login(name,psd)
                .subscribeOn(Schedulers.io())//被观察者运行的线程,子线程
                .observeOn(AndroidSchedulers.mainThread())//观察者运行的线程
                .subscribeWith(new ResourceSubscriber<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        //谁委托的M层请求的数据,结果肯定要告诉谁
                        //通过接口回调的 方式传递
                        try {
                            callBack.onSuccess(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        callBack.onFail(t.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
