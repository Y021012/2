package com.h2004c.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RlvAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final ArrayList<Bean.ResultsBean> mList;

    public RlvAdapter(Context context, ArrayList<Bean.ResultsBean> list) {

        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new ItemVH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemVH itemVH = (ItemVH) holder;
        itemVH.mTv.setText(mList.get(position).getUrl());

        Glide.with(mContext).load(mList.get(position).getUrl()).into(itemVH.mIv);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addData(List<Bean.ResultsBean> results) {
        if (results != null && results.size()>0){
            mList.addAll(results);
            notifyDataSetChanged();
        }
    }

    public class ItemVH extends RecyclerView.ViewHolder{

        private final ImageView mIv;
        private final TextView mTv;

        public ItemVH(@NonNull View itemView) {
            super(itemView);
            mIv = itemView.findViewById(R.id.iv);
            mTv = itemView.findViewById(R.id.tv);
        }
    }
}
