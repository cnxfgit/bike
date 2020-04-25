package com.hlx.vbbike.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hlx.vbbike.R;
import com.hlx.vbbike.model.Bill;

import java.util.List;

public class BillAdapter extends BaseAdapter {
    private List<Bill> list;
    private LayoutInflater cInflater;

    public BillAdapter(Context context , List<Bill> list){
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
        View layout = cInflater.inflate(R.layout.list_bill_layout,null);
        TextView bid = layout.findViewById(R.id.bid);
        TextView btime = layout.findViewById(R.id.btime);
        TextView rtime = layout.findViewById(R.id.rtime);
        TextView time = layout.findViewById(R.id.time);
        ImageView button = layout.findViewById(R.id.bButton);

        Bill b = list.get(position);
        bid.setText(b.getBid());
        time.setText(b.getTime());
        btime.setText(b.getBtime());
        rtime.setText(b.getRtime());

        if (b.getIspay().equals("0")){
            button.setBackgroundResource(R.drawable.check);
        }

        return layout;
    }
}
