package com.cgt.yuanmeng.baseapplication.ui.main;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.widget.Toast;

import com.cgt.yuanmeng.baseapplication.App;
import com.cgt.yuanmeng.baseapplication.print.DeviceConnFactoryManager;
import com.cgt.yuanmeng.baseapplication.print.UsbUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author zhonghang
 * description:
 */
public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    private CompositeDisposable compositeDisposable;
    public static final String ACTION_USB_PERMISSION="cn.com.cgt.yuanmeng.usb";
    public MainPresenter() {

    }

    @Override
    public void detachView() {
        //此处用于销毁网络连接，或者查询数据库的操作，耗时操作导致view不能被正常回收导致内存泄漏
        compositeDisposable.clear();
        mView = null;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void attachView(MainContract.View view) {
        mView = view;
        compositeDisposable = new CompositeDisposable();
        EventBus.getDefault().register(this);
    }

    @Override
    public void connectPrint() {
        UsbManager usbManager= (UsbManager) App.getApp().getSystemService(Context.USB_SERVICE);
        UsbDevice usbDevice = UsbUtils.getUsbDeviceList();
        if (usbDevice == null) {
            mView.setPrintState("未连接打印机");
        } else {
            if (usbManager.hasPermission(usbDevice)) {
                new DeviceConnFactoryManager.Build()
                        .setConnMethod(DeviceConnFactoryManager.CONN_METHOD.USB)
                        .setUsbDevice(usbDevice)
                        .setContext(App.getApp())
                        .build();
                DeviceConnFactoryManager.getDeviceConnFactoryManager().openPort();
            } else {//请求权限
                PendingIntent mPermissionIntent = PendingIntent.getBroadcast(App.getApp(), 0, new Intent(ACTION_USB_PERMISSION), 0);
                usbManager.requestPermission(usbDevice, mPermissionIntent);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String state) {
        Toast.makeText(App.getApp(), "EventBus执行", Toast.LENGTH_SHORT).show();
//        switch (state) {
//            case DeviceConnFactoryManager.CONN_STATE_DISCONNECT:
//                mView.setPrintState("断开连接");
//                break;
//            case DeviceConnFactoryManager.CONN_STATE_CONNECTING:
//                mView.setPrintState("连接中");
//                break;
//            case DeviceConnFactoryManager.CONN_STATE_CONNECTED:
//                mView.setPrintState("已连接");
//                break;
//            case DeviceConnFactoryManager.CONN_STATE_FAILED:
//                mView.setPrintState("连接失败");
//                break;
//            default:
//                break;
//        }
    }

}
