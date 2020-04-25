package com.hlx.vbbike.controller.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlx.vbbike.R;
import com.hlx.vbbike.controller.adapter.CommentAdapter;
import com.hlx.vbbike.controller.adapter.CouponAdapter;
import com.hlx.vbbike.model.Comment;
import com.hlx.vbbike.model.Coupon;

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

public class CouponActivity extends Activity {

    private String loginname;
    private ListView couponlv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        Intent intent = getIntent();
        loginname = intent.getStringExtra("loginname");
        System.out.println(loginname);

        findcoupon();

    }

    private void findcoupon() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://192.168.43.141:8080/bike/AndroidUserServlet?method=findcoupon";
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
                                Toast.makeText(CouponActivity.this, "网络连接失败！", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Type listType = new TypeToken<List<Coupon>>() {
                        }.getType();
                        String js = response.body().string();
                        Gson gson = new Gson();
                        List<Coupon> list = gson.fromJson(js, listType);// 把response string化

                        showCoupon(list);
                        System.out.println(list);
                    }
                });
            }
        }).start();
    }

    private void showCoupon(final List<Coupon> list1) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                couponlv = findViewById(R.id.couponlv);
                int i = list1.size();
                final List<Coupon> list = new ArrayList<>(i);

                for (Coupon c : list1) {
                    list.add(c);
                }

                CouponAdapter couponAdapter = new CouponAdapter(CouponActivity.this, list);
                couponlv.setAdapter(couponAdapter);
                couponlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Coupon c = list.get(position);
                        final String zhe = c.getZhe();
                        AlertDialog.Builder builder = new AlertDialog.Builder(CouponActivity.this);
                        builder.setTitle("提示");
                        builder.setMessage("你确定要使用此优惠券吗？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, final int i) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String url = "http://192.168.43.141:8080/bike/AndroidUserServlet?method=couponbutton";
                                        OkHttpClient okHttpClient = new OkHttpClient();
                                        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
                                        formBody.add("loginname", loginname);
                                        formBody.add("zhe", zhe);
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
                                                        Toast.makeText(CouponActivity.this, "网络连接失败！", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                                String js = response.body().string();
                                                if(js.equals("你没有需要结算的账单")){
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(CouponActivity.this, "你没有需要结算的账单！", Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }
                                                else if (js.equals("支付成功")){
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(CouponActivity.this, "支付成功！", Toast.LENGTH_LONG).show();
                                                        }
                                                    });

                                                    Intent intent = new Intent(CouponActivity.this,CouponActivity.class);
                                                    intent.putExtra("loginname",loginname);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else if (js.equals("支付失败，余额不足")){
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(CouponActivity.this, "支付失败，余额不足！", Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }
                                                else {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(CouponActivity.this, "错误！", Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }).start();

                            }

                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        builder.show();
                    }
                });
            }
        });

    }
}
