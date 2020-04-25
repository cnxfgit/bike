package com.hlx.vbbike.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hlx.vbbike.R;
import com.hlx.vbbike.model.Coupon;

import java.util.List;

public class CouponAdapter extends BaseAdapter {

    private List<Coupon> list;
    private LayoutInflater cInflater;

    public CouponAdapter(Context context , List<Coupon> list){
        this.list = list;
        cInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout = cInflater.inflate(R.layout.couponlayout,null);
        ImageView zhe = layout.findViewById(R.id.you);

        Coupon c = list.get(position);
        if (c.getZhe().equals("1")){
            zhe.setBackgroundResource(R.drawable.yuan1);
        }
        if (c.getZhe().equals("2")){
            zhe.setBackgroundResource(R.drawable.yuan2);
        }
        if (c.getZhe().equals("3")){
            zhe.setBackgroundResource(R.drawable.yuan3);
        }
        if (c.getZhe().equals("5")){
            zhe.setBackgroundResource(R.drawable.yuan5);
        }


        return layout;
    }
}
