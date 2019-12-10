package com.example.jetecpro_ver1.AllOfNewMonitor.Controll;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NDD_NewDataDisplaySupport.NewSupportNDDPagerAdapter;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NewSendType;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.Pagers.FragmentList_One;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.Pagers.FragmentList_Three;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.Pagers.FragmentList_Twp;
import com.example.jetecpro_ver1.BLE_function.BluetoothLeService;
import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.Values.SendType;

import java.util.ArrayList;

public class NewDataDisplay extends Activity {
    private BluetoothLeService mBluetoothLeService;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private ArrayList<View> mPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_data_display);
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setPager();

    }

    private void setPager() {
        mPages = new ArrayList<>();
        mPages.add(new FragmentList_One(this));//預設首
        mPages.add(new FragmentList_One(this));//預設尾
        for (int i=0;i< NewSendType.row;i++){
            mPages.add(new FragmentList_One(this));
        }
        NewSupportNDDPagerAdapter a= new NewSupportNDDPagerAdapter(mPages);
        mViewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(a);
    }

    /**
     * ============================================藍芽基礎配置↓=======================================
     */
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                finish();
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
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(SendType.DeviceAddress);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBluetoothLeService.disconnect();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
        unregisterReceiver(mGattUpdateReceiver);
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
                /**接收來自Service的訊息*/


            }
        }
    };//onReceive
    /**
     * ============================================藍芽基礎配置↑=======================================
     */


}
