package com.hlx.vbbike.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hlx.vbbike.R;

// 欢迎页面
public class SplashActivity extends Activity {

    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            if(isFinishing()){
                return;
            }

            // 判断是否进入主页面
            toMainOrLogin();
        }
    };

    private void toMainOrLogin() {
        new Thread(){
            public void run(){
                // 判断当前账号是否登录
                if(1==0){
                    // 获取登录信息

                    // 登陆过 跳转到主页面
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else {
                    // 没有登录 跳转到登录页面
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                }

                // 结束当前页面
                finish();
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 发送两秒钟的延时
        handler.sendMessageDelayed(Message.obtain(),2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
