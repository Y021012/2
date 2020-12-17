package com.h2004c.list.presenter;

import com.h2004c.list.Bean;
import com.h2004c.list.MainActivity;
import com.h2004c.list.model.MainModel;
import com.h2004c.list.view.MainView;

public class MainPresenter {
    private MainView mView;
    private final MainModel mMainModel;

    public MainPresenter(MainView view) {

        mView = view;
        mMainModel = new MainModel();
    }

    public void getData() {
        mMainModel.getData(new ResultCallback() {
            @Override
            public void onSuccess(Bean bean) {
                mView.onSuccess(bean);
            }

            @Override
            public void onFail(String msg) {
                mView.onFail(msg);
            }
        });
    }
}
