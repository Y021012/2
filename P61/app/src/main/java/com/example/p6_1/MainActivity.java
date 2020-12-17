package com.example.p6_1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                execute();
                break;
            case R.id.btn2:
                enqueue();
                break;
            case R.id.btn3:
                postExecute();
                break;
            case R.id.btn4:
                postEnqueue();
                break;
            case R.id.btn5:
                postString();
                break;
        }
    }
    //提交字符串
    private void postString() {
        //java调用方法几种方式,2种
        //普通方法,对象.方法()
        //静态方法:类名.方法()

        //同一个类中,方法名相同,参数列表不同 --- 重载
        //子类重新复写父类的方法---重写

        //MediaType: 媒体类型,网络交互的数据都是媒体(文字,图片,音视频....)
        //text:文本
        //audio:音频
        //video:视频
        //image:图片
        //二级制流: application/octet-stream

        MediaType type = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody body = RequestBody.create(type, "啦啦啦");
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(body)
                .build();
        new OkHttpClient()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: "+e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d(TAG, "onResponse: "+response.body().string());
                    }
                });
    }

    private void postEnqueue() {
        //post异步请求
        //client.newCall(request).enqueue();
        //请求体中添加参数
        RequestBody body = new FormBody.Builder()
                .add("username", "xts")
                .add("password", "123456")
                .add("repassword", "123456")
                .build();
        Request request = new Request.Builder()
                .url("https://www.wanandroid.com/user/register")
                .post(body)
                .build();
        new OkHttpClient()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d(TAG, "onResponse: " + response.body().string());
                    }
                });
    }

    private void postExecute() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //username: 用户名
                //password:密码
                //repassword:确认密码
                //.addEncoded():添加参数,需要编码,参数有中文使用
                //add:参数没有中文使用
                RequestBody body = new FormBody.Builder()
                        .add("username", "xts")
                        .add("password", "123456")
                        .add("repassword", "123456")
                        .build();
                Request request = new Request.Builder()
                        .post(body)//指定post请求方式,参数RequestBody请求体
                        .url("https://www.wanandroid.com/user/register")
                        .build();

                try {
                    Response execute = new OkHttpClient()
                            .newCall(request)
                            .execute();
                    Log.d(TAG, "run: "+execute.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //异步
    private void enqueue() {
        Request build = new Request.Builder()
                .url("https://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/3")
                .build();
        new OkHttpClient.Builder()
                .build()//返回的就是okhttpclient对象
                .newCall(build)
                //异步请求方式,不会阻塞当前线程
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: "+e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d(TAG, "onResponse: "+response.body().string());
                    }
                });
        Log.d(TAG, "enqueue: 滴答");
    }

    //同步
    //如果没有添加网络权限,会崩,报一下异常
    //SecurityException: Permission denied (missing INTERNET permission?)
    private void execute() {

        //1.创建OKhttpClient对象
        //2.创建request对象
        //3.OKhttpClient对象.newCall(request)
        //4.调用enqueue(回调对象)

        //链式调用
        //client.newCall().enqueue(异步)/excute(同步);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/3")
                .get()//指定请求方式,默认是get
                .build();

        final Call call = okHttpClient.newCall(request);//指定网址

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //同步方法,会阻塞当前线程,
                    Response execute = call.execute();
                    Log.d(TAG, "run: "+execute.body().string());
                    Log.d(TAG, "run: 滴滴");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Toast.makeText(MainActivity.this, "土司", Toast.LENGTH_SHORT).show();
    }
}