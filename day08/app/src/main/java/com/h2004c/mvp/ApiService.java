package com.h2004c.mvp;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    String sUrl = "https://www.wanandroid.com/";

    @FormUrlEncoded
    @POST("user/login")
    Flowable<ResponseBody> login(@Field("username") String name,
                                 @Field("password") String psd);
}
