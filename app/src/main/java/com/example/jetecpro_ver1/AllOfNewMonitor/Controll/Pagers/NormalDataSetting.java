package com.example.jetecpro_ver1.AllOfNewMonitor.Controll.Pagers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NDD_NewDataDisplaySupport.NewSupportNDDRecyclerViewAdapter;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NewSendType;
import com.example.jetecpro_ver1.BLE_function.BluetoothLeService;
import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.Values.SendType;

import java.util.ArrayList;
import java.util.HashMap;

public class NormalDataSetting extends RelativeLayout {
    private String TAG = NormalDataSetting.class.getSimpleName();
    private View mView;
    NewSupportNDDRecyclerViewAdapter mAdapter;
    Activity mActivity;
    private BluetoothLeService mBluetoothLeService;

    public NormalDataSetting(Context context, HashMap<String, ArrayList<String>> getFromIntentArray
            , int area, Activity activity) {//area是該編號的位置
        super(context);
        mActivity = activity;

        LayoutInflater inflater = LayoutInflater.from(context);
        mView = inflater.inflate(R.layout.new_ndd_recycler_item_normal_setting, null);
        RecyclerView r = mView.findViewById(R.id.recyclerview_NDD_normal_data_set);
        Button btSend = mView.findViewById(R.id.button_NDD_NormalSendOut);
        btSend.setOnClickListener((v -> {
            Log.d(TAG, "NormalDataSetting: "+area);
        }));
        r.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        mAdapter = new NewSupportNDDRecyclerViewAdapter(arrayList,getContext());
        r.setAdapter(mAdapter);
        Intent gattServiceIntent = new Intent(activity, BluetoothLeService.class);
        activity.bindService(gattServiceIntent, mServiceConnection,activity.BIND_AUTO_CREATE);
        addView(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }
    /**
     * ============================================藍芽基礎配置↓=======================================
     */
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                mActivity.finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(SendType.DeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService.connect(SendType.DeviceAddress);
        }
    };//serviceConnection

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);//連接一個GATT服務
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);//從GATT服務中斷開連接
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);//查找GATT服務
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);//從服務中接受(收?)數據
        return intentFilter;
    }


    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            //如果有連接
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
            }
            //如果沒有連接
            else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
            }
            //如果找到GATT服務
            else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
            }
            //接收來自藍芽傳回的資料
            else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                byte[] getByteData = intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                final StringBuilder stringBuilder = new StringBuilder(getByteData.length);
                for (byte byteChar : getByteData)
                    stringBuilder.append(String.format("%02X ", byteChar));
                String stringData = new String(getByteData) + "\n" + stringBuilder.toString();

                returnModifyData(byteArrayToHexStr(getByteData), stringData.substring(0, stringData.indexOf("\n")));
                new Thread(() -> {
                    NewSendType.engineerModeArrayList.add("回傳string>" + stringData);
                    NewSendType.engineerModeArrayList.add("回傳byte>" + byteArrayToHexStr(getByteData));
                    NewSendType.engineerModeArrayList.add("---------------------------");
                    mActivity.runOnUiThread(() -> {

                    });
                }).start();


                /**接收來自Service的訊息*/


            }
        }
    };//onReceive
    /**
     * Byte轉16進字串工具
     */
    private String byteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }

        StringBuilder hex = new StringBuilder(byteArray.length * 2);
        for (byte aData : byteArray) {
            hex.append(String.format("%02X", aData));
        }
        String gethex = hex.toString();
        return gethex;

    }
    /**
     * ============================================藍芽基礎配置↑=======================================
     */
    private void returnModifyData(String strByte, String strString) {
        /*NewSendType.cheatStringSend = strString;
        NewSendType.cheatByteSend = strByte;
        //作弊用的..*/


    }

}
