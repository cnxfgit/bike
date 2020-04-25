package com.hlx.vbbike.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.inputmethod.EditorInfo;

import com.hlx.vbbike.R;

public class CustomPwdWidget extends android.support.v7.widget.AppCompatEditText {
    private PasswordFullListener mListener;
    // 画笔
    private Paint mPaint;
    //输入的数字是否隐藏
    private boolean mIsHide;
    // 一个密码所占的宽度
    private int mPasswordItemWidth;
    // 密码的个数默认为6位数
    private int mPasswordNumber = 6;
    // 背景边框颜色
    private int mBgColor = Color.parseColor("#d1d2d6");
    // 背景边框大小
    private int mBgSize = 1;
    // 背景边框圆角大小
    private int mBgCorner = 0;
    // 分割线的颜色
    private int mDivisionLineColor = mBgColor;
    // 分割线的大小
    private int mDivisionLineSize = 1;
    // 密码圆点的颜色
    private int mPasswordColor = mDivisionLineColor;
    // 密码圆点的半径大小
    private int mPasswordRadius = 4;

    public CustomPwdWidget(Context context) {
        this(context, null);
    }

    public CustomPwdWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initAttributeSet(context, attrs);
        // 设置输入模式是密码
        //setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        // 不显示光标
        setCursorVisible(false);

       /* 在你的Activity的onCreate()方法中加入以下代码：
        EditText edittext=(EditText )findViewById(R.id.xx);
        edittext.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
        禁用系统软键盘方法2：
        在你的Activity的onCreate()方法中加入以下代码：
        EditText edittext=(EditText )findViewById(R.id.xx);
        edittext.setKeyListener(null);*/
    }

    /**
     * 初始化属性
     */
    private void initAttributeSet(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomPwdWidgt);
        //获取是否隐藏的属性,默认为不隐藏
        mIsHide = array.getBoolean(R.styleable.CustomPwdWidgt_isHide,false);

        //获取密码的位数
        mPasswordNumber = array.getInt(R.styleable.CustomPwdWidgt_passwordNumber,6);
        // 获取大小
        mDivisionLineSize = (int) array.getDimension(R.styleable.CustomPwdWidgt_divisionLineSize, dip2px(mDivisionLineSize));
        mPasswordRadius = (int) array.getDimension(R.styleable.CustomPwdWidgt_passwordRadius, dip2px(mPasswordRadius));
        mBgSize = (int) array.getDimension(R.styleable.CustomPwdWidgt_bgSize, dip2px(mBgSize));
        mBgCorner = (int) array.getDimension(R.styleable.CustomPwdWidgt_bgCorner, 0);
        // 获取颜色
        mBgColor = array.getColor(R.styleable.CustomPwdWidgt_bgColor, mBgColor);
        mDivisionLineColor = array.getColor(R.styleable.CustomPwdWidgt_divisionLineColor, mDivisionLineColor);
        mPasswordColor = array.getColor(R.styleable.CustomPwdWidgt_passwordColor, mDivisionLineColor);
        array.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    /**
     * dip 转 px
     */
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip, getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int passwordWidth = getWidth() - (mPasswordNumber - 1) * mDivisionLineSize - mBgSize * 2;
        mPasswordItemWidth = passwordWidth / mPasswordNumber;   //每个密码框的宽度
        // 绘制背景
        drawBg(canvas);
        // 绘制分割线
        drawDivisionLine(canvas);
        // 绘制密码
        drawPassword(canvas);
    }

    /**
     * 绘制背景
     */
    private void drawBg(Canvas canvas) {
        mPaint.setColor(mBgColor);
        // 设置画笔为空心
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBgSize);
        RectF rectF = new RectF(mBgSize/2, mBgSize/2, getWidth() - mBgSize/2, getHeight() - mBgSize/2);
        // 如果没有设置圆角，就画矩形
        if (mBgCorner == 0) {
            canvas.drawRect(rectF, mPaint);
        } else {
            // 如果有设置圆角就画圆矩形
            canvas.drawRoundRect(rectF, mBgCorner, mBgCorner, mPaint);
        }
    }

    /**
     * 绘制密码
     */
    private void drawPassword(Canvas canvas) {
        int passwordLength = getText().length();
        mPaint.setColor(mPasswordColor);
        // 设置画笔为实心
        mPaint.setStyle(Paint.Style.FILL);
        String password = getText().toString();
        for (int i = 0; i < passwordLength; i++) {
            int cx = i * mDivisionLineSize + i * mPasswordItemWidth + mPasswordItemWidth / 2 + mBgSize;
            //canvas.drawCircle(cx, getHeight() / 2, mPasswordRadius, mPaint);
            //
            //判断是否隐藏的属性，来绘制密码
            if(mIsHide){
                //隐藏密码，用圆圈代替
                canvas.drawCircle(cx, getHeight() / 2, mPasswordRadius, mPaint);
            }else{
                //不隐藏密码，并让数字居中显示
                mPaint.setTextSize(getHeight()/2);
                canvas.drawText(password.charAt(i)+"",cx, getHeight()/4 *3, mPaint);
            }
        }
    }

    /**
     * 绘制分割线
     */
    private void drawDivisionLine(Canvas canvas) {
        mPaint.setStrokeWidth(mDivisionLineSize);
        mPaint.setColor(mDivisionLineColor);
        for (int i = 0; i < mPasswordNumber - 1; i++) {
            //int startX = (i + 1) * mDivisionLineSize + (i + 1) * mPasswordItemWidth + mBgSize;
            int startX = (i)*mDivisionLineSize+(i+1)*mPasswordItemWidth+mBgSize+mDivisionLineSize/2;
            canvas.drawLine(startX, mBgSize, startX, getHeight() - mBgSize, mPaint);
        }
    }


    /**
     * 添加密码
     * @param number
     */
    public void addPassword(String number) {
        number = getText().toString().trim() + number;
        setText(number);
        //当输入的字符数等于或超过设置的字符输入个数时，会回调设置的监听方法
        if (number.length() >= mPasswordNumber) {
            mListener.passwordFullListener(number);
            return;
        }
    }

    /**
     * 删除最后一位密码
     */
    public void deleteLastPassword() {
        String currentText = getText().toString().trim();
        if (TextUtils.isEmpty(currentText)) {
            return;
        }
        currentText = currentText.substring(0, currentText.length() - 1);
        setText(currentText);
    }

    /**
     * 字符填满的监听接口
     */
    public interface PasswordFullListener {
        public void passwordFullListener(String number);
    }

    /**
     * 给输入框添加填满的监听事件
     * @param listener
     */
    public void setPasswordFullListener(PasswordFullListener listener){
        this.mListener = listener;
    }
}