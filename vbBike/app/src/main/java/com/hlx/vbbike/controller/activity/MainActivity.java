package com.hlx.vbbike.controller.activity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.MapView;
import com.hlx.vbbike.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {

    private String loginname;
    private TextView tv1;
    private Button pmbtn;
    private Button mbtn;
    private Button bbtn;
    private MapView mMapView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);

        Intent intent = getIntent();
        loginname = intent.getStringExtra("loginname");
        if (loginname.equals("")){
            Toast.makeText(MainActivity.this,"你还未登录！",Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);
        }
        else {
            tv1 = findViewById(R.id.tv1);
            tv1.setText("你好"+loginname);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://192.168.43.141:8080/bike/AndroidUserServlet?method=main";
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
                        if(js.equals("你正在租车")){
                            Intent intent = new Intent(MainActivity.this,BbikeActivity.class);
                            intent.putExtra("loginname",loginname);
                            startActivity(intent);
                        }
                        else if(js.equals("你现在没有租车")){
                            // 无事发生
                        }
                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "错误！", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
            }
        }).start();


        pmbtn = findViewById(R.id.personMessageButton);
        mbtn = findViewById(R.id.messageButton);
        bbtn = findViewById(R.id.borrow);

        pmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,PersonActivity.class);
                i.putExtra("loginname",loginname);
                startActivity(i);
            }
        });

        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CommentActivity.class);
                i.putExtra("loginname",loginname);
                startActivity(i);
            }
        });

        bbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = "http://192.168.43.141:8080/bike/AndroidUserServlet?method=main";
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
                                if(js.equals("你正在租车")){
                                    Intent intent = new Intent(MainActivity.this,BbikeActivity.class);
                                    intent.putExtra("loginname",loginname);
                                    startActivity(intent);
                                }
                                else if(js.equals("你现在没有租车")){
                                    // 无事发生
                                    Intent i = new Intent(MainActivity.this,BorrowActivity.class);
                                    i.putExtra("loginname",loginname);
                                    startActivity(i);
                                }
                                else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "错误！", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                        });
                    }
                }).start();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时必须调用mMapView. onResume ()
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时必须调用mMapView. onPause ()
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时必须调用mMapView.onDestroy()
        mMapView.onDestroy();
    }

}
