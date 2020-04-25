package com.hlx.vbbike.controller.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.hlx.vbbike.R;
import com.hlx.vbbike.utils.CustomKeyboard;
import com.hlx.vbbike.utils.CustomPwdWidget;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BorrowActivity extends Activity implements CustomPwdWidget.PasswordFullListener,
        CustomKeyboard.CustomerKeyboardClickListener{

    private CustomPwdWidget pwdEdit;
    private CustomKeyboard keboard;
    private String mNumber;
    private String loginname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);

        Intent intent = getIntent();
        loginname = intent.getStringExtra("loginname");
        System.out.println(loginname);

        pwdEdit = (CustomPwdWidget) findViewById(R.id.pwdEdit);
        keboard = (CustomKeyboard) findViewById(R.id.keyboard);
        pwdEdit.setPasswordFullListener(this);
        keboard.setOnCustomerKeyboardClickListener(this);
    }
    @Override
    public void passwordFullListener(String number) {
        mNumber = number;
    }

    /**
     * 键盘的点击数字键的响应事件
     * @param number
     */
    @Override
    public void click(String number) {
        pwdEdit.addPassword(number);
    }

    /**
     * 点击确认键的回调事件
     */
    @Override
    public void confirm() {
        if(!TextUtils.isEmpty(mNumber)){
            mNumber = mNumber.substring(0,4);   //4为设置输入数字长度,截取输入的长度
            AlertDialog.Builder builder = new AlertDialog.Builder(BorrowActivity.this);
            builder.setTitle("提示");
            builder.setMessage("你确定要租用这辆车吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, final int i) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url = "http://192.168.43.141:8080/bike/AndroidUserServlet?method=borrow";
                            OkHttpClient okHttpClient = new OkHttpClient();
                            FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
                            formBody.add("loginname", loginname);
                            formBody.add("bid", mNumber);
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
                                    if(js.equals("你输入的号码错误")){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "你输入的号码错误！", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                    else if(js.equals("你正在租借的车未归还")){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "你正在租借的车未归还！", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                    else if(js.equals("你上个清单未结算")){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "你上个清单未结算！", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                    else if(js.equals("单车状态不可用")){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "单车状态不可用！", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                    else if(js.equals("租车成功")){

                                        Intent intent = new Intent(BorrowActivity.this,BbikeActivity.class);
                                        intent.putExtra("loginname",loginname);
                                        startActivity(intent);
                                    }
                                    else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "错误", Toast.LENGTH_LONG).show();
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
    }

    /**
     * 点击删除按键的回调事件
     */
    @Override
    public void cancel() {
        mNumber = null;
        pwdEdit.deleteLastPassword();
    }

}
