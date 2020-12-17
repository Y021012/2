package com.h2004c.list;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.h2004c.list.presenter.MainPresenter;
import com.h2004c.list.view.MainView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainView {

    private RecyclerView mRlv;
    private RlvAdapter mAdapter;
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter.getData();
    }

    private void initView() {
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Bean.ResultsBean> list = new ArrayList<>();
        mAdapter = new RlvAdapter(this, list);
        mRlv.setAdapter(mAdapter);
    }

    @Override
    public void onSuccess(Bean bean) {
        mAdapter.addData(bean.getResults());
    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
    }

    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
