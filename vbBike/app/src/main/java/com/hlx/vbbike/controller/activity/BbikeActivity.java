package com.hlx.vbbike.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hlx.vbbike.R;
import com.hlx.vbbike.utils.DigitalTimer;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BbikeActivity extends Activity {

    private String loginname;
    private Button rebike;

    private DigitalTimer timer;
    int btime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbike);

        Intent intent = getIntent();
        loginname = intent.getStringExtra("loginname");
        System.out.println(loginname);

        timer = findViewById(R.id.timer);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://192.168.43.141:8080/bike/AndroidUserServlet?method=btime";
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
                                Toast.makeText(getApplicationContext(), "网络连接失败！", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String js = response.body().string();
                        btime = Integer.parseInt(js);
                    }
                });
            }
        }).start();

        Thread thread = new Thread();
        thread.start();
        try{
            thread.sleep(300);
        }catch (Exception e){
            e.printStackTrace();
        }

        timer.setSeconds(btime);
        timer.start();

        rebike = findViewById(R.id.rebike);
        rebike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = "http://192.168.43.141:8080/bike/AndroidUserServlet?method=rebike";
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
                                        Toast.makeText(getApplicationContext(), "网络连接失败！", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                String js = response.body().string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "还车成功", Toast.LENGTH_LONG).show();
                                    }
                                });

                                Intent intent = new Intent(BbikeActivity.this,BillActivity.class);
                                intent.putExtra("loginname",loginname);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                }).start();
            }
        });

    }

}
