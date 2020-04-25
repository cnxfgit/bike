package com.hlx.vbbike.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.hlx.vbbike.R.*;

public class LoginActivity extends Activity {

    private EditText loginnameEt;
    private EditText loginpassEt;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login);

        loginButton = findViewById(id.loginbutton);
        loginnameEt = findViewById(id.loginname);
        loginpassEt = findViewById(id.loginpass);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginname = loginnameEt.getText().toString();
                System.out.println(loginname);
                String loginpass = loginpassEt.getText().toString();
                System.out.println(loginpass);

                if (loginname.equals("")) {// 非空校验
                    Toast.makeText(LoginActivity.this, "用户名不能为空！", Toast.LENGTH_LONG).show();
                    return;
                } else if (loginname.length() < 3 || loginname.length() > 16) {//长度校验
                    Toast.makeText(LoginActivity.this, "用户名为3~16位！", Toast.LENGTH_LONG).show();
                    return;
                }

                if (loginpass.equals("")) {// 非空校验
                    Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_LONG).show();
                    return;
                } else if (loginpass.length() < 3 || loginname.length() > 16) {//长度校验
                    Toast.makeText(LoginActivity.this, "密码为3~16位！", Toast.LENGTH_LONG).show();
                    return;
                }

                sendnamepass(loginname, loginpass);

            }
        });
    }

    private void sendnamepass(final String loginname, final String loginpass) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://192.168.43.141:8080/bike/AndroidUserServlet?method=login";
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
                formBody.add("loginname", loginname);
                formBody.add("loginpass", loginpass);
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
                                Toast.makeText(LoginActivity.this, "网络连接失败！", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String res = response.body().string();
                        System.out.println(res);

                        if (res.equals("用户名或密码错误")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_LONG).show();
                                }
                            });

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("loginname", loginname);
                            startActivity(intent);
                            finish();
                        }

                    }
                });

            }
        }).start();
    }


}
