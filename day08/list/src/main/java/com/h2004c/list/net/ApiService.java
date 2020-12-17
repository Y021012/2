package com.h2004c.list.net;

import com.h2004c.list.Bean;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface ApiService {
    String sUrl = "https://gank.io/api/";

    @GET("data/%E7%A6%8F%E5%88%A9/10/3")
    Flowable<Bean> getData();
}
