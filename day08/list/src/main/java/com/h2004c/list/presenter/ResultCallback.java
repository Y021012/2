package com.h2004c.list.presenter;

import com.h2004c.list.Bean;

public interface ResultCallback {
    void onSuccess(Bean bean);
    void onFail(String msg);
}
