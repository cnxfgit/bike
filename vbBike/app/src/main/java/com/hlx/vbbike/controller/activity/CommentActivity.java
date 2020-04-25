package com.hlx.vbbike.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlx.vbbike.R;
import com.hlx.vbbike.controller.adapter.CommentAdapter;
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

public class CommentActivity extends Activity {

    private String loginname;
    private ListView clv;
    private Button send;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent intent = getIntent();
        loginname = intent.getStringExtra("loginname");
        System.out.println(loginname);

        findComment();

        send = findViewById(R.id.send);
        editText = findViewById(R.id.comment);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String comment = editText.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = "http://192.168.43.141:8080/bike/AndroidUserServlet?method=addComment";
                        OkHttpClient okHttpClient = new OkHttpClient();
                        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
                        formBody.add("loginname", loginname);
                        formBody.add("commnet", comment);
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
                                if (js.equals("发送评论成功")){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "发送评论成功！", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    Intent i = new Intent(CommentActivity.this,CommentActivity.class);
                                    i.putExtra("loginname",loginname);
                                    startActivity(i);
                                    finish();
                                }
                                else if (js.equals("发送评论失败")){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "发送评论失败！", Toast.LENGTH_LONG).show();
                                        }
                                    });
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

    private void findComment() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://192.168.43.141:8080/bike/AndroidUserServlet?method=findComment";
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
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
                                Toast.makeText(CommentActivity.this,"网络连接失败！",Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Type listType=new TypeToken<List<Comment>>(){}.getType();
                        String js = response.body().string();
                        Gson gson=new Gson();
                        List<Comment> list=gson.fromJson(js, listType);// 把response string化
                        showComment(list);

                    }
                });
            }
        }).start();
    }

    private void showComment(final List<Comment> list1) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                clv = findViewById(R.id.clv);
                int i = list1.size();
                List<Comment> list = new ArrayList<>(i);

                for (Comment c:list1) {
                    list.add(c);
                }
                CommentAdapter commentAdapter = new CommentAdapter(CommentActivity.this,list);
                clv.setAdapter(commentAdapter);
            }
        });


    }
}
