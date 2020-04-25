package com.hlx.vbbike.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.hlx.vbbike.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings({"RedundantThrows"})
@SuppressLint("AppCompatCustomView")
public class DigitalTimer extends TextView {
    private static final float DEFAULT_TEXT_SIZE = 12;
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private float textSize;
    private int textColor;
    private int mCurrentSecond = 0; //当前秒
    private String hours, minutes, seconds; //展示的时分秒
    private Disposable mDisposable;

    public DigitalTimer(Context context) {
        super(context);
        init();
    }

    public DigitalTimer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DigitalTimer);
        textSize = a.getDimension(R.styleable.DigitalTimer_textSize, DEFAULT_TEXT_SIZE);
        textColor = a.getColor(R.styleable.DigitalTimer_textColor, DEFAULT_TEXT_COLOR);
        a.recycle();
        init();
    }

    //初始化
    private void init() {
        setTextSize(textSize);
        setTextColor(textColor);
        setGravity(Gravity.CENTER);
        setBackgroundColor(Color.TRANSPARENT);
    }

    //开始计时
    public void start() {
        mDisposable = Observable.interval(1, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mCurrentSecond++;
                        int hour = mCurrentSecond / 3600;
                        int minute = (mCurrentSecond % 3600) / 60;
                        int second = mCurrentSecond % 60;

                        if (second < 10) { //处理秒
                            seconds = "0" + second;
                        } else {
                            seconds = String.valueOf(second);
                        }

                        if (minute < 10) { //处理分
                            minutes = "0" + minute;
                        } else {
                            minutes = String.valueOf(minute);
                        }

                        if (hour < 10) { //处理小时
                            hours = "0" + hour;
                        } else {
                            hours = String.valueOf(hour);
                        }

                        //设置数据
                        setText(String.format("%s:%s:%s", hours, minutes, seconds));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("自定义计时器数据异常------" + throwable.getMessage());
                    }
                });

    }

    //停止计时
    public void stop() {
        System.out.println("当前计时时长----->" + getText().toString());
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    //重置数据
    @SuppressLint("SetTextI18n")
    public void reset() {
        mCurrentSecond = 0;
        setText("00:00:00");
        stop();
    }

    public void setSeconds(int seconds) {
        mCurrentSecond = seconds;
    }
}

