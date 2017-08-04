package com.fecmobile.jiubeirobot.ui.activity.cellar.manager;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.serialport.SerialPort;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.utils.SharedPreferencesUtils;
import com.fecmobile.jiubeirobot.utils.T;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author TheJoker丶豪
 *         这是在系统管理登录成功之后显示的activity
 *         里面分为系统管理和本地管理 以及酒窖信息的显示的页面
 *         酒窖信息的显示可以定义工具类来直接显示 在酒窖信息的下方  比如温度 湿度 当前电量 类似的
 * @date 创建时间:2017/5/16
 */

public class ManagerGroupActivity extends BaseActivity {
    String uid;
    String account;
    String aid;
    String busiCompName;
    String bid;
    String busiCompLogo_url;
    String userName;
    @BindView(R.id.tv_name_jiujiao)
    TextView tvCellerName;//酒窖名称

    @BindView(R.id.OnLock_button)
    Button OpenDoor;
    /**
     * 仓库信息
     */
    @BindView(R.id.Temp_textView)//仓库温度
            TextView Temp_text;
    @BindView(R.id.Humi_TextView)//仓库湿度
            TextView Humi_text;
    @BindView(R.id.OpenDoorNub_TextView)//开门员工
            TextView OpenDoorNub_text;
    @BindView(R.id.DoorCondition_TextView)//仓门状态
            TextView DoorCondition_text;
    @BindView(R.id.WarehouseCondition_TextView)//仓库状态
            TextView WarehouseCondition_text;

    private static short Uart_Frame_Head = 'S';        //帧头
    private static short Uart_Frame_End = 'T';        //帧尾
    private static short PacketShell_Len = 6;        //数据包壳长度

    private static short My_Addr = 0x10;            //我的地址
    private static short WareHouse_Addr = 0x11;        //仓库管理地址

    private static int WarehouseTemp;                        //仓库温度
    private static int WarehouseHumi;                        //仓库湿度
    private static int WarehouseOpenDoorNumber;            //开仓库门者编号
    private static int WarehouseDoorCondition;                //仓库门的状态
    private static int WarehouseCondition;                    //仓库内部状态

    private SerialPort mSerialPort_1;
    private OutputStream mOutputStream_1;
    private InputStream mInputStream_1;
    private ManagerGroupActivity.ReadThread_1 mReadThread_1;
    private byte[] Uart1_Rx_Buffer = new byte[256];
    private short Uart1_Rx_Len;

    /**
     * 0x10 ARM 中控
     * 0x11  仓库管理板
     * 剩下的暂时还没有用上
     */
    private byte[] Uart1_Tx_Buffer = {0x53, 0x10, 0x11, 0x03, (byte) 0xA0, 0x00, 0x00, 0x00, 0x54};//
    private byte Tx_Count = 0;//计数
    private Handler handler_1;

    private ManagerGroupActivity.RefreshDisplayThread newThread; //声明一个子线程

    @Override
    public int pageLayout() {
        return R.layout.activity_manager_group;
    }

    @Override
    public void initTitle() {
        //监听开锁键
        OpenDoor.setOnClickListener(new ManagerGroupActivity.OpenDoorOnClickListener());
        //打开串口1
        onCreate_1("/dev/ttyS1", 115200);     ///dev/ttyS1
        newThread = new ManagerGroupActivity.RefreshDisplayThread();
        newThread.start();
        handler_1 = new Handler() {
            public void handleMessage(Message msg) {
                RefreshDisplay(msg);
            }
        };
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        account = intent.getStringExtra("account");
        aid = intent.getStringExtra("aid");
        busiCompName = intent.getStringExtra("busiCompName");
        bid = intent.getStringExtra("bid");
        busiCompLogo_url = intent.getStringExtra("busiCompLogo_url");
        userName = intent.getStringExtra("userName");
        String CellarName = SharedPreferencesUtils.getString(ManagerGroupActivity.this, "CellarName");
        if (null != CellarName) {
            tvCellerName.setText(CellarName);
        }
    }

    @OnClick({R.id.btn_sysmager, R.id.btn_localmager, R.id.llyt_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llyt_back:
                this.finish();
                break;
            case R.id.btn_sysmager:
                //点击进入系统管理
                Activitys.toSysMainManager(ManagerGroupActivity.this, aid, bid, uid, account, busiCompName, busiCompLogo_url, userName);
                break;
            case R.id.btn_localmager:
                //点击进入本地管理
                Activitys.toLocalMainManager(ManagerGroupActivity.this, aid, bid, uid, account, busiCompName, busiCompLogo_url, userName);
                break;
        }
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
    }

    /**
     * 刷新界面
     */
    private void RefreshDisplay(Message msg) {
        switch (msg.what) {
            case 0:
                //new DecimalFormat("#0.00").format(Temp)
                /**
                 * 根据接收到的消息 经过处理之后 根据判断数据  然后设置到相应的控件上显示出来
                 */
                Temp_text.setText(String.valueOf((Double) (WarehouseTemp / 10.00)));//设置温度文字内容

                Humi_text.setText(String.valueOf((Double) (WarehouseHumi / 10.00)));//设置湿度文字内容

                OpenDoorNub_text.setText(String.valueOf(WarehouseOpenDoorNumber));//设置开门号文字内容
                /**
                 * 判断传递过来的数据是否为0
                 */
                if (0 != WarehouseDoorCondition) {
                    DoorCondition_text.setText("仓门开启");//设置仓门文字内容
                    DoorCondition_text.setTextColor(getResources().getColor(R.color.abcde));
                } else {
                    DoorCondition_text.setTextColor(getResources().getColor(R.color.default_status_abcd));
                    DoorCondition_text.setText("仓门正常");//设置仓门文字内容
                }

                if (0 != WarehouseCondition) {
                    WarehouseCondition_text.setText("仓库有人");//设置仓内文字内容
                    WarehouseCondition_text.setTextColor(getResources().getColor(R.color.abde));
                } else {
                    WarehouseCondition_text.setTextColor(getResources().getColor(R.color.default_status_abcd));
                    WarehouseCondition_text.setText("仓库正常");//设置仓内文字内容
                }
                break;
            case 1:
                T.showToCenter("请检查是否正常连接");
                break;
            default:
                break;
        }
    }

    /**
     * 监听开锁键
     */
    private class OpenDoorOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            //发送开门请求
            Uart1_Tx_Buffer[5] |= 0x80;//固定命令识别
            Tx_Count = 1;//开门命令发出  次数由0变为1
        }
    }

    /***
     * 清除开门命令的次数缓存
     */
    public void CleanOpenDoorOnClick() {
        if (Tx_Count != 0) {
            Tx_Count++;
            //TODO  后面这里需要改为发送端  目前是接收端
            if (Tx_Count > 3) {
                Tx_Count = 0;
                Uart1_Tx_Buffer[5] &= ~0x80;
            }
        }
    }

    /**
     * 初始化串口信息
     */
    public void onCreate_1(String device, int baudrate) {
        try {
            //传入设备名和波特率 初始化端口
            mSerialPort_1 = new SerialPort(new File(device), baudrate);
            mOutputStream_1 = mSerialPort_1.getOutputStream();
            mInputStream_1 = mSerialPort_1.getInputStream();

            mReadThread_1 = new ManagerGroupActivity.ReadThread_1();
            mReadThread_1.start();//开启新的线程来获取接收数据
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 串口1发送数据
     */
    public void sendCmds_1(byte[] mBuffer) {
        try {
            if (mOutputStream_1 != null) {
                try {
                    if (mBuffer != null) {
                        mOutputStream_1.write(mBuffer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean sendBuffer(byte[] mBuffer) {
        boolean result = true;

        byte[] mBufferTemp = new byte[mBuffer.length];
        System.arraycopy(mBuffer, 0, mBufferTemp, 0, mBuffer.length);
        try {
            if (mOutputStream_1 != null) {
                mOutputStream_1.write(mBufferTemp);
            } else {
                result = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * 串口1数据校验
     */
    private boolean SourceWareHouse_DataCheck(short[] Buff) {
        short i;
        int CheckSum = 0;

        if (Buff[0] != Uart_Frame_Head)
            return false;
        if (Buff[1] != WareHouse_Addr)
            return false;
        if (Buff[2] != My_Addr)
            return false;
        if (Buff[3] != (Uart1_Rx_Len - PacketShell_Len))
            return false;
        if (Buff[Uart1_Rx_Len - 1] != Uart_Frame_End)
            return false;

        for (i = 3; i < 12; i++)
            CheckSum += Buff[i];

//    	if(Buff[Uart1_Rx_Len-2] != CheckSum)
//    		return false;
        return true;
    }

    /**
     * 串口1数据归属
     */
    private void Uart1_Rx_Data_Affiliation(short[] Buff) {

        //仓门状态
        if ((Buff[5] & 0x0080) != 0) {
            WarehouseDoorCondition = 1;
        } else {
            WarehouseDoorCondition = 0;
        }
        //仓库内部状态
        if ((Buff[5] & 0x0040) != 0) {
            WarehouseCondition = 1;
        } else {
            WarehouseCondition = 0;
        }
        //温度赋值
        WarehouseTemp = ((int) Buff[9]) << 8;
        WarehouseTemp |= (int) Buff[10];

        //湿度赋值
        WarehouseHumi = ((int) Buff[7]) << 8;
        WarehouseHumi |= (int) Buff[8];

        //开门请求号
        WarehouseOpenDoorNumber = ((int) Buff[11]) << 8;
        WarehouseOpenDoorNumber |= (int) Buff[12];

        Log.d("print", "Uart1_Rx_Data_Affiliation: " + WarehouseTemp + WarehouseHumi);

        if (WarehouseTemp != 0 && WarehouseHumi != 0) {
            Message message = new Message();
            message.what = 0;
            handler_1.sendMessage(message);
        } else {
            Message message = new Message();
            message.what = 1;
            handler_1.sendMessage(message);
        }
    }

    /**
     * 避免造成内存泄漏
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler_1.removeCallbacksAndMessages(null);
    }

    /**
     * 串口1数据解析
     */
    private void Uart1_Rx_Data_Analysis() {
        short i;
        short[] Buff = new short[256];

        for (i = 0; i < Uart1_Rx_Len; i++) {
            Buff[i] = ((short) (((int) Uart1_Rx_Buffer[i]) & 0x000000ff));
        }
        CleanOpenDoorOnClick();//清空开门命令的缓存
        if (SourceWareHouse_DataCheck(Buff)) {//检验数据格式
            Uart1_Tx_Buffer[4] = (byte) 0xA0;//接收数据成功 命令为0xA0  失败为0XA1
            switch (Buff[4]) {
                case 0x00C0:
                    //判断接收数据是属于哪一个值
                    Uart1_Rx_Data_Affiliation(Buff);
                    break;
                default:
                    break;
            }
        } else {
            //格式失败
            Uart1_Tx_Buffer[4] = (byte) 0xA1;
        }
        //根据格式发送数据
        sendCmds_1(Uart1_Tx_Buffer);
    }

    /**
     * 串口1接收线程
     */
    private class ReadThread_1 extends Thread {
        @Override
        public void run() {
            int i;
            int Buf_Len = 0;
            if (mInputStream_1 == null)
                return;
            try {
                while (!isInterrupted()) {
                    for (i = 0; i < 3; i++) {
                        Buf_Len = mInputStream_1.available();
                        if (Buf_Len > 0) {
                            Uart1_Rx_Len += mInputStream_1.read(Uart1_Rx_Buffer, Uart1_Rx_Len, Buf_Len);
                        } else {
                            break;
                        }
                        Thread.sleep(20);
                    }
                    if (Uart1_Rx_Len > 0) {
                        Uart1_Rx_Data_Analysis();
//                    	Temp = String.valueOf(temp);
                        for (i = 0; i < 100; i++) {
                            Uart1_Rx_Buffer[i] = 0;
                        }
                        Uart1_Rx_Len = 0;
                    }
                    Thread.sleep(50);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private class RefreshDisplayThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}