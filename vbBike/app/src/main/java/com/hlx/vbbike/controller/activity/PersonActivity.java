package com.hlx.vbbike.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hlx.vbbike.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PersonActivity extends Activity {
    private String loginname;
    private ListView lv1;
    private String[] data = {"余额", "账单", "优惠券","退出登录"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        Intent intent = getIntent();
        loginname = intent.getStringExtra("loginname");
        System.out.println(loginname);

        lv1 = findViewById(R.id.lv1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                PersonActivity.this, android.R.layout.simple_list_item_1, data);
        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    findmoney(loginname);
                }
                else if (position == 1){
                    findrecord(loginname);
                }
                else if (position == 2){
                    findcoupon();
                }
                else if (position == 3){
                    relogin();
                }
                else {
                    Toast.makeText(PersonActivity.this,"错误",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void findcoupon() {
        Intent i = new Intent(PersonActivity.this,CouponActivity.class);
        i.putExtra("loginname",loginname);
        startActivity(i);
    }

    private void relogin() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(getApplicationContext(), LoginActivity.class);
        getApplicationContext().startActivity(intent);
        finish();
    }

    private void findrecord(String loginname) {
        Intent i = new Intent(PersonActivity.this,BillActivity.class);
        i.putExtra("loginname",loginname);
        startActivity(i);
    }

    private void findmoney(final String loginname) {

        String url = "http://192.168.43.141:8080/bike/AndroidUserServlet?method=findmoney";
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
                        Toast.makeText(PersonActivity.this,"网络连接失败！",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String res = response.body().string();
                System.out.println(res);
                Intent intent = new Intent(PersonActivity.this,MoneyActivity.class);
                intent.putExtra("money",res);
                intent.putExtra("loginname",loginname);
                startActivity(intent);
            }
        });
    }
}
