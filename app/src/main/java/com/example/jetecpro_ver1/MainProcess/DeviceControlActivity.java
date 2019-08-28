/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jetecpro_ver1.MainProcess;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jetecpro_ver1.BLE_function.BluetoothLeService;
import com.example.jetecpro_ver1.BLE_function.SampleGattAttributes;
import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.SendData.GetDisplayData;
import com.example.jetecpro_ver1.Values.SendType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * For a given BLE device, this Activity provides the user interface to connect, display data,
 * and display GATT services and characteristics supported by the device.  The Activity
 * communicates with {@code BluetoothLeService}, which in turn interacts with the
 * Bluetooth LE API.
 */
public class DeviceControlActivity extends Activity {
    private final static String TAG = DeviceControlActivity.class.getSimpleName();

    public static Activity OptionThis;


    private TextView mConnectionState;
    private TextView mDataField;
    private BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;

    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";

    // Code to manage Service lifecycle.

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }

            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(SendType.DeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };//serviceConnection




    // Handles various events fired by the Service.處理服務所激發的各種事件
    // ACTION_GATT_CONNECTED: connected to a GATT server. 連接一個GATT服務
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.從GATT服務中斷開連接
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.查找GATT服務
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.從服務中接受(收?)數據
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                updateConnectionState(R.string.Connect_true,getBaseContext().getResources().getColor(R.color.Green_Yanagizome));
                invalidateOptionsMenu();

            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                updateConnectionState(R.string.Connect_false,getBaseContext().getResources().getColor(R.color.Red_Syojyohi));
                invalidateOptionsMenu();
                clearUI();

                /**接下來是重點===============*/
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
                /**送出資訊*/
                sendData();
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                /**接收來自Service的訊息*/
                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };//onReceive
    private void displayData(final String data) {
        if (data != null) {
            mDataField.setText(data);
            mDataField.setTextColor(getBaseContext().getResources().getColor(R.color.define));

        }
        GetDisplayData get = new GetDisplayData(data);
        if (data.contains("OK")) {
            get.GetOK();
        }
        if (data.contains("BT-")) {
            SendType.DeviceType = data;
            SendType.FirstWord = data.charAt(5);
            SendType.SecondWord = data.charAt(6);
            SendType.row = data.charAt(3);
            if (data.charAt(3) == '3') {
                SendType.ThirdWord = data.charAt(7);
            }else if(data.length() == 33) {
                SendType.ThirdWord = data.charAt(7);
                String getTable = data.substring(0,7);
                SendType.DB_TABLE = getTable.replace("-","");
            }
            else if (data.charAt(3) == '2'){
                Log.v("BT", "裝置只有兩個輸入");
                String getTable = data.substring(0,6);
                SendType.DB_TABLE = getTable.replace("-","");
            }

        }
        if (data.contains("PASS")) {
            mDataField.setText(R.string.Pz_input_psw);
            AlertDialog.Builder mBuidler = new AlertDialog.Builder(DeviceControlActivity.this);
            View v = getLayoutInflater().inflate(R.layout.activity_device_control_input_psw_dialog, null);
            final EditText edInput = (EditText) v.findViewById(R.id.editTextInput);
            mBuidler.setTitle("請輸入裝置密碼");
            mBuidler.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            mBuidler.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            mBuidler.setView(v);
            final AlertDialog dialog = mBuidler.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("BT","取得裝置密碼: "+ data.substring(4));
                    if (!edInput.getText().toString().isEmpty() &&
                            edInput.getText().toString().contains(data.substring(4, 10))) {
                        Toast.makeText(getBaseContext(), "登入成功", Toast.LENGTH_LONG).show();
                        mDataField.setText(R.string.Correct);
                        GetDisplayData get = new GetDisplayData(data);
                        get.sendGet();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getBaseContext(), "輸入錯誤", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }//PSW
        /**接收其他數據並分析*/
        String getMain = data.substring(0, 3);
        GetDisplayData get1 = new GetDisplayData(data);
        get1.analysisData(getMain);
        if (data.contains("OVER")) {
            Intent intent = new Intent(DeviceControlActivity.this, DataDisplayActivity.class);
            startActivity(intent);
            finish();
        }


    }//displayData(回傳值都在這邊操作)

    private void sendData() {
        final BluetoothGattCharacteristic characteristic =
                mGattCharacteristics.get(2).get(0);
        mNotifyCharacteristic = characteristic;
        mBluetoothLeService.setCharacteristicNotification(characteristic, true);
        SendType.getSendBluetoothLeService = mBluetoothLeService;
        SendType.Mycharacteristic = characteristic;
        SendType.SendForBLEDataType = "Jetec";
    }

    private void clearUI() {
        mDataField.setText(R.string.no_data);
        mDataField.setTextColor(getBaseContext().getResources().getColor(R.color.Yellow_Yamafuki));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_control);
        getActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.ActionBarColor)));
        OptionThis = this;

        // Sets up UI references.
        ((TextView) findViewById(R.id.device_address)).setText(SendType.DeviceName);
        mConnectionState = (TextView) findViewById(R.id.connection_state);
        mDataField = (TextView) findViewById(R.id.data_value);



        getActionBar().setTitle(R.string.Connecting3);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());

        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(SendType.DeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gatt_services, menu);
        if (mConnected) {

            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);

        } else {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_connect:
                mBluetoothLeService.connect(SendType.DeviceAddress);
                return true;
            case R.id.menu_disconnect:
                mBluetoothLeService.disconnect();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateConnectionState(final int resourceId,final int colorId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionState.setText(resourceId);
                mConnectionState.setTextColor(colorId);
            }
        });
    }


    // Demonstrates how to iterate through the supported GATT Services/Characteristics.
    // In this sample, we populate the data structure that is bound to the ExpandableListView
    // on the UI.

    /**
     * 以下示範如何將搜尋到的服務顯示至ExpandableListView上
     */
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = "未知";
        String unknownCharaString = "未知";
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();

        //將可用的GATT Service迴圈顯示
        /**這邊顯示的是關於裝置的基本性質*/
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(
                    LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();

            //將可用的GATT Characteristics迴圈顯示
            /**這邊顯示的是關於裝置可寫入與輸出等等*/
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(
                        LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);

        }

    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);//連接一個GATT服務
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);//從GATT服務中斷開連接
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);//查找GATT服務
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);//從服務中接受(收?)數據
        return intentFilter;
    }

}
