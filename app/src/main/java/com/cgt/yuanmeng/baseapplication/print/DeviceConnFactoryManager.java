package com.cgt.yuanmeng.baseapplication.print;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.cgt.yuanmeng.baseapplication.App;
import com.cgt.yuanmeng.baseapplication.R;
import com.gprinter.io.PortManager;
import com.gprinter.io.UsbPort;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_DETACHED;

/**
 * 作者： Circle
 * 创造于 2018/5/24.
 */
public class DeviceConnFactoryManager {

    public PortManager mPort;

    private static final String TAG = DeviceConnFactoryManager.class.getSimpleName();

    private CONN_METHOD connMethod;

    private String ip;

    private int port;

    private String macAddress;

    private UsbDevice mUsbDevice;

    private Context mContext;

    private String serialPortPath;

    private int baudrate;

    private int id;

    private static DeviceConnFactoryManager deviceConnFactoryManager;

    private boolean isOpenPort;
    /**
     * ESC查询打印机实时状态指令
     */
    private byte[] esc = {0x10, 0x04, 0x02};

    /**
     * ESC查询打印机实时状态 缺纸状态
     */
    private static final int ESC_STATE_PAPER_ERR = 0x20;

    /**
     * ESC指令查询打印机实时状态 打印机开盖状态
     */
    private static final int ESC_STATE_COVER_OPEN = 0x04;

    /**
     * ESC指令查询打印机实时状态 打印机报错状态
     */
    private static final int ESC_STATE_ERR_OCCURS = 0x40;

    /**
     * TSC查询打印机状态指令
     */
    private byte[] tsc = {0x1b, '!', '?'};

    /**
     * TSC指令查询打印机实时状态 打印机缺纸状态
     */
    private static final int TSC_STATE_PAPER_ERR = 0x04;

    /**
     * TSC指令查询打印机实时状态 打印机开盖状态
     */
    private static final int TSC_STATE_COVER_OPEN = 0x01;

    /**
     * TSC指令查询打印机实时状态 打印机出错状态
     */
    private static final int TSC_STATE_ERR_OCCURS = 0x80;

    /**
     * CPCL查询打印机状态指令
     */
    public static byte[] cpcl = new byte[]{0x1b, 0x68};

    /**
     * CPCL指令查询打印机实时状态 打印机缺纸状态
     */
    public static final int CPCL_STATE_PAPER_ERR = 0x02;

    /**
     * CPCL指令查询打印机实时状态 打印机开盖状态
     */
    public static final int CPCL_STATE_COVER_OPEN = 0x04;
    private byte[] sendCommand;
    /**
     * 判断打印机所使用指令是否是ESC指令
     */
    private PrinterCommand currentPrinterCommand;
    public static final byte FLAG = 0x10;
    private static final int READ_DATA = 10000;
    private static final int DEFAUIT_COMMAND = 20000;
    private static final String READ_DATA_CNT = "read_data_cnt";
    private static final String READ_BUFFER_ARRAY = "read_buffer_array";
    public static final String ACTION_CONN_STATE = "action_connect_state";
    public static final String ACTION_QUERY_PRINTER_STATE = "action_query_printer_state";
    public static final String STATE = "state";
    public static final String DEVICE_ID = "id";
    public static final int CONN_STATE_DISCONNECT = 0x90;
    public static final int CONN_STATE_CONNECTING = CONN_STATE_DISCONNECT << 1;
    public static final int CONN_STATE_FAILED = CONN_STATE_DISCONNECT << 2;
    public static final int CONN_STATE_CONNECTED = CONN_STATE_DISCONNECT << 3;
    public PrinterReader reader;
    private int queryPrinterCommandFlag;
    private final int ESC = 1;
    private final int TSC = 3;
    private final int CPCL = 2;

    public enum CONN_METHOD {
        //蓝牙连接
        BLUETOOTH("BLUETOOTH"),
        //USB连接
        USB("USB"),
        //wifi连接
        WIFI("WIFI"),
        //串口连接
        SERIAL_PORT("SERIAL_PORT");

        private String name;

        private CONN_METHOD(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public static DeviceConnFactoryManager getDeviceConnFactoryManager() {
        return deviceConnFactoryManager;
    }

    /**
     * 打开端口
     */
    public void openPort() {
        Logger.d("打开端口");
        deviceConnFactoryManager.isOpenPort = false;
//        sendStateBroadcast(CONN_STATE_CONNECTING);
        mPort = new UsbPort(mContext, mUsbDevice);
        isOpenPort = mPort.openPort();
        Logger.d("是否已端口" + isOpenPort);
        if (isOpenPort) {
            IntentFilter filter = new IntentFilter(ACTION_USB_DEVICE_DETACHED);
            mContext.registerReceiver(usbStateReceiver, filter);
        }
        //端口打开成功后，检查连接打印机所使用的打印机指令ESC、TSC、CPCL
        if (isOpenPort) {
            queryCommand();
        } else {
            if (this.mPort != null) {
                this.mPort = null;
            }
            sendStateBroadcast(CONN_STATE_FAILED);
        }
    }

    /**
     * 查询当前连接打印机所使用打印机指令（ESC（EscCommand.java）、TSC（LabelCommand.java））
     */
    private void queryCommand() {
        //开启读取打印机返回数据线程
        reader = new PrinterReader();
        reader.start();
        //查询打印机所使用指令，此处进行屏蔽，因为该打印机是ESC指令打印机
        EventBus.getDefault().post(CONN_STATE_CONNECTED);
//        queryPrinterCommand();
    }

    /**
     * 获取端口连接方式
     */
    public CONN_METHOD getConnMethod() {
        return connMethod;
    }

    /**
     * 获取端口打开状态（true 打开，false 未打开）
     */
    public boolean getConnState() {
        return isOpenPort;
    }

    /**
     * 获取连接蓝牙的物理地址
     *
     * @return
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * 获取连接网口端口号
     */
    public int getPort() {
        return port;
    }

    /**
     * 获取连接网口的IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 获取连接的USB设备信息
     */
    public UsbDevice usbDevice() {
        return mUsbDevice;
    }

    /**
     * 关闭端口
     */
    public void closePort() {
        if (this.mPort != null) {
            reader.cancel();
            boolean b = this.mPort.closePort();
            if (b) {
                this.mPort = null;
                isOpenPort = false;
                currentPrinterCommand = null;
            }
        }
        sendStateBroadcast(CONN_STATE_DISCONNECT);
    }

    /**
     * 获取串口号
     */
    public String getSerialPortPath() {
        return serialPortPath;
    }

    /**
     * 获取波特率
     */
    public int getBaudrate() {
        return baudrate;
    }

    public static void closeAllPort() {
        if (deviceConnFactoryManager != null) {
            Log.e(TAG, "cloaseAllPort() id -> " + deviceConnFactoryManager.id);
            deviceConnFactoryManager.closePort();
            DeviceConnFactoryManager.deviceConnFactoryManager = null;
        }
    }

    private DeviceConnFactoryManager(Build build) {
        this.connMethod = build.connMethod;
        this.macAddress = build.macAddress;
        this.port = build.port;
        this.ip = build.ip;
        this.mUsbDevice = build.usbDevice;
        this.mContext = build.context;
        this.serialPortPath = build.serialPortPath;
        this.baudrate = build.baudrate;
        deviceConnFactoryManager = this;
    }

    /**
     * 获取当前打印机指令
     */
    public PrinterCommand getCurrentPrinterCommand() {
        return deviceConnFactoryManager.currentPrinterCommand;
    }

    public static final class Build {
        private String ip;
        private String macAddress;
        private UsbDevice usbDevice;
        private int port;
        private CONN_METHOD connMethod;
        private Context context;
        private String serialPortPath;
        private int baudrate;

        public Build setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Build setMacAddress(String macAddress) {
            this.macAddress = macAddress;
            return this;
        }

        public Build setUsbDevice(UsbDevice usbDevice) {
            this.usbDevice = usbDevice;
            return this;
        }

        public Build setPort(int port) {
            this.port = port;
            return this;
        }

        public Build setConnMethod(CONN_METHOD connMethod) {
            this.connMethod = connMethod;
            return this;
        }

        public Build setContext(Context context) {
            this.context = context;
            return this;
        }

        public Build setSerialPort(String serialPortPath) {
            this.serialPortPath = serialPortPath;
            return this;
        }

        public Build setBaudrate(int baudrate) {
            this.baudrate = baudrate;
            return this;
        }

        public DeviceConnFactoryManager build() {
            return new DeviceConnFactoryManager(this);
        }
    }

    public void sendDataImmediately(final Vector<Byte> data) {
        if (this.mPort == null) {
            Toast.makeText(mContext, "端口未开", Toast.LENGTH_SHORT).show();
            return;
        } else {
            try {
                Log.e(TAG, "data ---> " + new String(convertVectorByteTobytes(data), "gb2312"));
                this.mPort.writeDataImmediately(data, 0, data.size());
            } catch (IOException e) {
                Toast.makeText(mContext, "发送失败", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    public byte[] convertVectorByteTobytes(Vector<Byte> data) {
        byte[] sendData = new byte[data.size()];
        if (data.size() > 0) {
            for (int i = 0; i < data.size(); ++i) {
                sendData[i] = ((Byte) data.get(i)).byteValue();
            }
        }
        return sendData;
    }

    public int readDataImmediately(byte[] buffer) throws IOException {
        return this.mPort.readData(buffer);
    }

    /**
     * 查询打印机当前使用的指令（ESC、CPCL、TSC、）
     */
    private void queryPrinterCommand() {
        queryPrinterCommandFlag = ESC;
        ThreadPool.getInstantiation().addTask(new Runnable() {
            @Override
            public void run() {
                //开启计时器，隔2000毫秒没有没返回值时发送查询打印机状态指令，先发票据，面单，标签
                final ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder("Timer");
                final ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1, threadFactoryBuilder);
                scheduledExecutorService.scheduleAtFixedRate(threadFactoryBuilder.newThread(new Runnable() {
                    @Override
                    public void run() {
                        if (currentPrinterCommand == null && queryPrinterCommandFlag > TSC) {
//                            if (reader != null) {//三种状态，查询无返回值，发送连接失败广播
//                                reader.cancel();
//                                mPort.closePort();
//                                isOpenPort = false;
//                                sendStateBroadcast(CONN_STATE_FAILED);
//                                scheduledExecutorService.shutdown();
//                            }
                            if (currentPrinterCommand == null) {   //三种状态查询，完毕均无返回值，默认票据（针对凯仕、盛源机器查询指令没有返回值，导致连不上）
                                currentPrinterCommand = PrinterCommand.ESC;
                                sendCommand = esc;
                                sendStateBroadcast(CONN_STATE_CONNECTED);
                                mHandler.sendMessage(mHandler.obtainMessage(DEFAUIT_COMMAND, ""));
                                scheduledExecutorService.shutdown();
                            }
                        }
                        if (currentPrinterCommand != null) {
                            if (scheduledExecutorService != null && !scheduledExecutorService.isShutdown()) {
                                scheduledExecutorService.shutdown();
                            }
                            return;
                        }
                        switch (queryPrinterCommandFlag) {
                            case ESC:
                                //发送ESC查询打印机状态指令
                                sendCommand = esc;
                                break;
                            case TSC:
                                //发送ESC查询打印机状态指令
                                sendCommand = tsc;
                                break;
                            case CPCL:
                                //发送CPCL查询打印机状态指令
                                sendCommand = cpcl;
                                break;
                            default:
                                break;
                        }
                        Vector<Byte> data = new Vector<>(sendCommand.length);
                        for (int i = 0; i < sendCommand.length; i++) {
                            data.add(sendCommand[i]);
                        }
                        sendDataImmediately(data);
                        queryPrinterCommandFlag++;
                    }
                }), 1500, 1500, TimeUnit.MILLISECONDS);
            }
        });
    }

    class PrinterReader extends Thread {
        private boolean isRun = false;

        private byte[] buffer = new byte[100];

        public PrinterReader() {
            isRun = true;
        }

        @Override
        public void run() {
            try {
                while (isRun) {
                    //读取打印机返回信息
                    int len = readDataImmediately(buffer);
                    if (len > 0) {
                        Message message = Message.obtain();
                        message.what = READ_DATA;
                        Bundle bundle = new Bundle();
                        bundle.putInt(READ_DATA_CNT, len); //数据长度
                        bundle.putByteArray(READ_BUFFER_ARRAY, buffer); //数据
                        message.setData(bundle);
                        mHandler.sendMessage(message);
                    }
                }
            } catch (Exception e) {
                if (deviceConnFactoryManager != null) {
                    Toast.makeText(mContext, "读取打印机返回信息报错", Toast.LENGTH_SHORT).show();
                    closePort();
                }
            }
        }

        public void cancel() {
            isRun = false;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DEFAUIT_COMMAND:
//                    Toast.makeText(App.getContext().getString(R.string.str_default) + App.getContext().getString(R.string.str_esc));
                    break;
                case READ_DATA:
                    int cnt = msg.getData().getInt(READ_DATA_CNT);
                    byte[] buffer = msg.getData().getByteArray(READ_BUFFER_ARRAY);
                    //这里只对查询状态返回值做处理，其它返回值可参考编程手册来解析
                    if (buffer == null) {
                        return;
                    }
                    int result = judgeResponseType(buffer[0]);
                    Log.i("Circle", buffer[0] + "");
                    String status = App.getApp().getString(R.string.str_printer_conn_normal);
                    //设置当前打印机模式为ESC模式
                    if (currentPrinterCommand == null) {
                        currentPrinterCommand = PrinterCommand.ESC;
                        sendStateBroadcast(CONN_STATE_CONNECTED);
//                            SmarnetTool.Toast(App.getContext().getString(R.string.esc_set));
                    } else {//查询打印机状态
                        if (result == 0) {//打印机状态查询
                            Intent intent = new Intent(ACTION_QUERY_PRINTER_STATE);
                            intent.putExtra(DEVICE_ID, id);
                            App.getApp().sendBroadcast(intent);
                        } else if (result == 1) {//查询打印机实时状态
                            if ((buffer[0] & ESC_STATE_PAPER_ERR) > 0) {
                                status += " " + App.getApp().getString(R.string.str_printer_out_of_paper);
                            }
                            if ((buffer[0] & ESC_STATE_COVER_OPEN) > 0) {
                                status += " " + App.getApp().getString(R.string.str_printer_open_cover);
                            }
                            if ((buffer[0] & ESC_STATE_ERR_OCCURS) > 0) {
                                status += " " + App.getApp().getString(R.string.str_printer_error);
                            }
                            Log.i("Circle", App.getApp().getString(R.string.str_state) + status);
                            Utils.toast(App.getApp(), status);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void sendStateBroadcast(int state) {
        EventBus.getDefault().post(state);
    }

    /**
     * 判断是实时状态（10 04 02）还是查询状态（1D 72 01）
     */
    private int judgeResponseType(byte r) {
        return (byte) ((r & FLAG) >> 4);
    }

    BroadcastReceiver usbStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_USB_DEVICE_DETACHED:
                    sendStateBroadcast(CONN_STATE_DISCONNECT);
                    break;
                default:
                    break;
            }
        }
    };

}