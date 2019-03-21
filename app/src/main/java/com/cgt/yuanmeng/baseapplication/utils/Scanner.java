package com.cgt.yuanmeng.baseapplication.utils;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by wujn on 2018/4/19.
 * Version : v1.0
 * Function: android hardware device - Scanner
 * <p>
 * 扫码枪：相当于软键盘使用的
 * 1、一定需要一个EditText（充当扫码枪输入的容器）
 * 2、有弹出软键盘的，扫码内容可能会顺序不对和乱码，需要关闭软键盘，内容正确
 * 3、扫码出来后，一般扫码枪是有标识符结束的，一般是键盘上的
 * keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN
 * 返回当前扫码内容
 * 4、最后会有KeyEvent.KEYCODE_DPAD_DOWN，到其他view上去
 * 5、还没能解决如何设置timeout问题，让接口提示扫码失败
 */
public class Scanner {

    private Activity activity;

    public Scanner(Activity activity) {
        this.activity = activity;
    }

    /**
     * 显示的/隐藏的 EditText 获得光标，准备扫码
     */
    public void scan(final EditText editText) {
        //获得光标
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

        //关闭软键盘：防止顺序乱码
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(editText.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        //增加软键盘监听，扫出来内容会自己填充到editText中去的
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onScanResultCallBack.OnScanSucccess(editText.getText().toString());
                    //返回结果值，看需要使用了
                    return true;
                }
                return false;
            }
        });
    }


    /**
     * 扫码枪接口：有timeout设置，当然光标移除的时候关闭计时
     * 成功 返回扫码结果
     * 失败 返回错误信息
     */
    public interface OnScanResultCallBack {
        void OnScanSucccess(String result);

        void OnScanFail(String errorMsg);
    }


    private OnScanResultCallBack onScanResultCallBack = new OnScanResultCallBack() {
        @Override
        public void OnScanSucccess(String result) {
        }

        @Override
        public void OnScanFail(String errorMsg) {
        }
    };

    /**
     * 子类实现回调函数
     */
    public void setOnScanResultCallBack(Scanner.OnScanResultCallBack onScanResultCallBack) {
        if (onScanResultCallBack == null)
            throw new IllegalArgumentException("empty onScanResultCallBack");
        this.onScanResultCallBack = onScanResultCallBack;
    }

}