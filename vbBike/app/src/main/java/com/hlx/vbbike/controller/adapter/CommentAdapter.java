package com.hlx.vbbike.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hlx.vbbike.R;
import com.hlx.vbbike.model.Comment;

import java.util.List;

public class CommentAdapter extends BaseAdapter {
    private List<Comment> list;
    private LayoutInflater cInflater;

    public CommentAdapter(Context context , List<Comment> list){
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
        View layout = cInflater.inflate(R.layout.list_item_layout,null);
        TextView loginname = layout.findViewById(R.id.loginname);
        TextView time = layout.findViewById(R.id.time);
        TextView comment = layout.findViewById(R.id.comment);

        Comment c = list.get(position);
        loginname.setText(c.getLoginname());
        time.setText(c.getTime());
        comment.setText(c.getComment());

        return layout;
    }
}
