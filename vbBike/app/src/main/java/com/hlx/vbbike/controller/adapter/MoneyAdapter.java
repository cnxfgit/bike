package com.hlx.vbbike.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hlx.vbbike.R;
import com.hlx.vbbike.model.Money;

import java.util.List;

public class MoneyAdapter extends BaseAdapter {
    private List<Money> list;
    private LayoutInflater cInflater;

    public MoneyAdapter(Context context, List<Money> list) {
        this.list = list;
        cInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
        View layout = cInflater.inflate(R.layout.money_list_layout,null);
        TextView paydate = layout.findViewById(R.id.paydate);
        TextView iscoupon = layout.findViewById(R.id.iscoupon);
        TextView pay = layout.findViewById(R.id.pay);

        Money m = list.get(position);

        if (m.getIscoupon().equals("1")){
            iscoupon.setText("一元优惠券");
        }
        if (m.getIscoupon().equals("2")){
            iscoupon.setText("二元优惠券");
        }
        if (m.getIscoupon().equals("3")){
            iscoupon.setText("三元优惠券");
        }
        if (m.getIscoupon().equals("5")){
            iscoupon.setText("五元优惠券");
        }
        paydate.setText(m.getDate());

        pay.setText(m.getPay());
        return layout;
    }
}
