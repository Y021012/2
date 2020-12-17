package com.h2004c.list.model;

import com.h2004c.list.Bean;
import com.h2004c.list.net.ApiService;
import com.h2004c.list.presenter.ResultCallback;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainModel {
    public void getData(final ResultCallback callback) {
        new Retrofit.Builder()
                .baseUrl(ApiService.sUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class)
                .getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<Bean>() {
                    @Override
                    public void onNext(Bean bean) {
                        callback.onSuccess(bean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        callback.onFail(t.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
