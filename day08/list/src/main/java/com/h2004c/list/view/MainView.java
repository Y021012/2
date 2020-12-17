package com.h2004c.list.view;

import com.h2004c.list.Bean;

public interface MainView {
    void onSuccess(Bean bean);

    void onFail(String msg);
}
