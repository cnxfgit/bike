package com.hlx.vbbike.controller.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlx.vbbike.R;
import com.hlx.vbbike.controller.adapter.BillAdapter;
import com.hlx.vbbike.controller.adapter.CommentAdapter;
import com.hlx.vbbike.model.Bill;
import com.hlx.vbbike.model.Comment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BillActivity extends Activity {

    private String loginname;
    private ListView blv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        Intent intent = getIntent();
        loginname = intent.getStringExtra("loginname");
        System.out.println(loginname);

        findRecord(loginname);

    }

    private void findRecord(final String loginname) {
        String url = "http://192.168.43.141:8080/bike/AndroidUserServlet?method=findBill";
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("loginname", loginname);
        final Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BillActivity.this, "网络连接失败！", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Type listType = new TypeToken<List<Bill>>() {
                }.getType();
                String js = response.body().string();
                Gson gson = new Gson();
                List<Bill> list = gson.fromJson(js, listType);// 把response string化
                showBill(list);
                System.out.println(list);
            }
        });
    }

    private void showBill(final List<Bill> list1) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                blv = findViewById(R.id.blv);
                int i = list1.size();
                List<Bill> list = new ArrayList<>(i);

                for (Bill b : list1) {
                    list.add(b);
                }
                BillAdapter billAdapter = new BillAdapter(BillActivity.this, list);
                blv.setAdapter(billAdapter);
                blv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            if (list1.get(0).getIspay().equals("0")) {
                                billbutton(loginname);
                            }

                        }
                    }
                });
            }
        });
    }

    private void billbutton(final String loginname) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BillActivity.this);
        builder.setTitle("提示");
        builder.setMessage("你确定要结算账单吗");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String url = "http://192.168.43.141:8080/bike/AndroidUserServlet?method=billbutton";
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
                formBody.add("loginname", loginname);
                final Request request = new Request.Builder()
                        .url(url)
                        .post(formBody.build())
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BillActivity.this, "网络连接失败！", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String s = response.body().string();
                        if (s.equals("没有需要结算的账单")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(BillActivity.this, "没有需要结算的账单", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        else if (s.equals("你的余额不足")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(BillActivity.this, "你的余额不足", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        else if (s.equals("结算成功")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(BillActivity.this, "结算成功", Toast.LENGTH_LONG).show();
                                }
                            });
                            Intent intent = new Intent(BillActivity.this,BillActivity.class);
                            intent.putExtra("loginname",loginname);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();


    }

}
