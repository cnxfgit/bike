package com.hlx.vbbike.controller.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlx.vbbike.R;
import com.hlx.vbbike.controller.adapter.BillAdapter;
import com.hlx.vbbike.controller.adapter.MoneyAdapter;
import com.hlx.vbbike.model.Bill;
import com.hlx.vbbike.model.Money;

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

public class MoneyActivity extends Activity {

    private String money;
    private TextView tv1;
    private String loginname;
    private ListView moneylv;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        Intent intent = getIntent();
        money = intent.getStringExtra("money");
        loginname = intent.getStringExtra("loginname");

        System.out.println(money);

        tv1 = findViewById(R.id.tv2);
        tv1.setText("你的余额为:" + money);


        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://192.168.43.141:8080/bike/AndroidUserServlet?method=moneybutton";
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
                                Toast.makeText(MoneyActivity.this, "网络连接失败！", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Type listType = new TypeToken<List<Money>>() {}.getType();
                        String js = response.body().string();
                        Gson gson = new Gson();
                        List<Money> list = gson.fromJson(js, listType);// 把response string化
                        showMoney(list);
                    }
                });
            }
        }).start();
    }


    private void showMoney(final List<Money> list1) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                moneylv = findViewById(R.id.moneylv);
                int i = list1.size();
                List<Money> list = new ArrayList<>(i);

                for (Money m : list1) {
                    list.add(m);
                }
                MoneyAdapter moneyAdapter = new MoneyAdapter(MoneyActivity.this, list);
                moneylv.setAdapter(moneyAdapter);

            }
        });
    }
}
