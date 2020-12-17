package com.h2004c.mvp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.h2004c.mvp.presenter.MainPresenter;
import com.h2004c.mvp.view.MainView;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * View: 视图层, Activity,Fragment,负责界面展示+用户交互
 * Presenter: P层,负责业务逻辑,V和M层的桥梁
 * Model: M,数据模型层,负责网络请求,数据库,io操作...耗时操作
 *
 * V  < --- > P < --- > M
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainView {

    private static final String TAG = "MainActivity";
    private EditText mEtName;
    private EditText mEtPsd;
    private Button mBtn;
    private MainPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        initView();
    }

    private void initView() {
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPsd = (EditText) findViewById(R.id.et_psd);
        mBtn = (Button) findViewById(R.id.btn);

        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                login();

                break;
        }
    }

    /**
     *  https://www.wanandroid.com/user/login
     *
     * 方法：POST
     * 参数：
     * 	username，password
     */
    //登录
    private void login() {
       //1.从edittext先把用户名和密码拿到
        //trim() : 去掉字符串首尾两端的空格
        //界面
        String name = mEtName.getText().toString().trim();
        String psd = mEtPsd.getText().toString().trim();
        //业务逻辑,比较少,索引放到v也可以
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(psd)){
            showToast("用户名或者密码不能为空");
            return;
        }

        //通知p层去登录
        mPresenter.login(name,psd);



    }

  /*  private void login() {
        //1.从edittext先把用户名和密码拿到
        //trim() : 去掉字符串首尾两端的空格
        //界面
        String name = mEtName.getText().toString().trim();
        String psd = mEtPsd.getText().toString().trim();
        //业务逻辑,比较少,索引放到v也可以
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(psd)){
            showToast("用户名或者密码不能为空");
            return;
        }

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
                        try {
                            Log.d(TAG, "onNext: "+responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "onError: "+t.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }*/

    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSuccess(String json) {
        mBtn.setText(json);
        showToast("登录结果:"+json);
    }

    @Override
    public void onFail(String msg) {
        showToast("登录失败:"+msg);
    }
}
