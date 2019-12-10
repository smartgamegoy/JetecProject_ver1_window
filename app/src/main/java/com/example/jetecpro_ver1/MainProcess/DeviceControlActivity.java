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

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jetecpro_ver1.AllOfNewMonitor.Model.ClearNewSendType;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NewDeviceInitialzation;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.DCA_DeviceControlActivitySupport.NewSupportDCAGetValue;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.DCA_DeviceControlActivitySupport.NewSupportDCARecycleViewTypeChooser;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NewSendType;
import com.example.jetecpro_ver1.BLE_function.BluetoothLeService;
import com.example.jetecpro_ver1.BLE_function.SampleGattAttributes;
import com.example.jetecpro_ver1.EngineerMode.EngineerMode;
import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.SendData.GetDisplayData;
import com.example.jetecpro_ver1.Values.ClearAllData;
import com.example.jetecpro_ver1.Values.SendType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * For a given BLE device, this Activity provides the user interface to connect, display data,
 * and display GATT services and characteristics supported by the device.  The Activity
 * communicates with {@code BluetoothLeService}, which in turn interacts with the
 * Bluetooth LE API.
 */
public class DeviceControlActivity extends Activity {
    private final static String TAG = DeviceControlActivity.class.getSimpleName() + "My";

    public static Activity OptionThis;

    public Dialog waitdialog;
    private TextView mConnectionState;
    private TextView mDataField;
    private BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    HashMap<String, String> hashMap = new HashMap<>();

    private boolean mConnected = false;
    int tt = 0;
    int coint = 0;
    boolean PASSOK = false;
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
            waitdialog = ProgressDialog.show(DeviceControlActivity.this,//顯示等待圖示
                    getResources().getString(R.string.plzWait), getResources().getString(R.string.progressing), true);
            Log.v("BT", "自動連線第一次");
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
//            for (int x = 0;x<mBluetoothLeService.getSupportedGattServices().size();x++){
//                Log.v("BT","DCA.取得Gatt:"+ mBluetoothLeService.getSupportedGattServices().get(x).getUuid().toString());
//            }
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                waitdialog.dismiss();
                updateConnectionState(R.string.Connect_true, getBaseContext().getResources().getColor(R.color.Green_Yanagizome));
                invalidateOptionsMenu();

            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                updateConnectionState(R.string.Connect_false, getBaseContext().getResources().getColor(R.color.Red_Syojyohi));
                if (tt <= 0) {
                    Log.v("BT", "自動連線第二次");

                    mBluetoothLeService.connect(SendType.DeviceAddress);
                    count();
                }

                invalidateOptionsMenu();
                clearUI();
                /**接下來是重點===============*/
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
                /**送出資訊*/
                sendData();
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                /**接收來自Service的訊息*/
                byte[] getByteData = intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                final StringBuilder stringBuilder = new StringBuilder(getByteData.length);
                for (byte byteChar : getByteData)
                    stringBuilder.append(String.format("%02X ", byteChar));
                String stringData = new String(getByteData) + "\n" + stringBuilder.toString();
                if (stringData.contains("BYTE")) {
                    SendType.newMonitorChooser = 1;
                }
                if (SendType.newMonitorChooser == 1) {
                    newGetValue(stringData, byteArrayToHexStr(getByteData));
                } else {
                    displayData(stringData,byteArrayToHexStr(getByteData));
                }

            }
        }
    };//onReceive

    /**
     * ======================================組合式大顯===============================================
     */
    /*組合式大顯*/
    private void newGetValue(String stringData, String byteData) {
        mDataField.setText("byte： " + byteData + "\n" + "字串: " + stringData);
        NewSupportDCAGetValue s= new NewSupportDCAGetValue(DeviceControlActivity.this,waitdialog);
        s.getValueFromDevice(stringData,byteData);//將控制權轉給NewSupportDCAGetValue類別
    }


    /**
     * 設定組合大顯型號
     */
    private void setNewMonitorTypeSetter() {
        AlertDialog.Builder sendType = new AlertDialog.Builder(DeviceControlActivity.this);
        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.new_dialog_type_chooser, null);
        sendType.setView(view);
        AlertDialog dialog = sendType.create();
        RecyclerView recyclerView = view.findViewById(R.id.recycleView_DCA_dialog_getType);
        Button btAdd = view.findViewById(R.id.button_DCA_dialog_add);
        Button btSendOut = view.findViewById(R.id.button_DCA_dialog_sendOut);
        Button btClear = view.findViewById(R.id.button_DCA_dialog_clear);
        Button btCancel = view.findViewById(R.id.button_DCA_dialog_cancel);
        ArrayList<String> mChoose = NewSupportDCARecycleViewTypeChooser.mSelectType;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this
                , DividerItemDecoration.VERTICAL));
        NewSupportDCARecycleViewTypeChooser mAdapter = new NewSupportDCARecycleViewTypeChooser();

        btSendOut.setOnClickListener((v -> {

            if (mChoose.size() > 0) {
                ProgressDialog progressDialog = ProgressDialog.show(DeviceControlActivity.this
                        , getResources().getString(R.string.progressing)
                        , getResources().getString(R.string.plzWait), false);
                new Thread(() -> {
                    int row = mChoose.size();
                    if (mChoose.contains("L")){
                        row--;
                    }

                    String defaultName = "BT-" + row + "-";
                    String getType;
                    switch (mChoose.size()) {
                        case 1:
                            getType = mChoose.get(0);
                            break;
                        case 2:
                            getType = mChoose.get(0) + mChoose.get(1);
                            break;
                        case 3:
                            getType = mChoose.get(0) + mChoose.get(1)
                                    + mChoose.get(2);
                            break;
                        case 4:
                            getType = mChoose.get(0) + mChoose.get(1)
                                    + mChoose.get(2) + mChoose.get(3);
                            break;
                        case 5:
                            getType = mChoose.get(0) + mChoose.get(1)
                                    + mChoose.get(2) + mChoose.get(3) + mChoose.get(4);
                            break;
                        case 6:
                            getType = mChoose.get(0) + mChoose.get(1)
                                    + mChoose.get(2) + mChoose.get(3) + mChoose.get(4)
                                    + mChoose.get(5);
                            break;
                        case 7:
                            getType = mChoose.get(0) + mChoose.get(1)
                                    + mChoose.get(2) + mChoose.get(3) + mChoose.get(4)
                                    + mChoose.get(5) +mChoose.get(6);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + mChoose.size());
                    }
                    NewDeviceInitialzation d = new NewDeviceInitialzation();
                    d.inItalzation(defaultName,getType,"-N",mChoose);
                    mChoose.clear();

                    runOnUiThread(()->{
                        mAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                        finish();
                    });
                }).start();

            }

        }));

        btCancel.setOnClickListener((v) -> {
            dialog.dismiss();
            mChoose.clear();
            finish();
        });
        btClear.setOnClickListener((v -> {
            mChoose.clear();
            mAdapter.notifyDataSetChanged();
        }));
        setAddFunction(view, btAdd, mChoose, mAdapter);//增加型號的按鈕
        makeRecycleAnimate(recyclerView, mChoose, mAdapter);
        recyclerView.setAdapter(mAdapter);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }

    /**
     * 處理RecycleView內部的排序以及左滑刪除
     */
    private void makeRecycleAnimate(RecyclerView recyclerView, ArrayList<String> mChoose, NewSupportDCARecycleViewTypeChooser mAdapter) {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, 0) {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.LEFT;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView
                    , @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {

                int position_dragged = dragged.getAdapterPosition();
                int position_target = target.getAdapterPosition();
                Collections.swap(mChoose, position_dragged, position_target);
                mAdapter.notifyItemMoved(position_dragged, position_target);
                if (mChoose.contains("L")&&mChoose.indexOf("L") !=mChoose.size()-1){
                    Collections.swap(mChoose,position_target,position_dragged);
                    mAdapter.notifyItemMoved(position_target,position_dragged);
                }

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        mChoose.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        break;
                }

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(DeviceControlActivity.this, R.color.redGINSYU))
                        .addActionIcon(R.drawable.ic_delete_black_24dp)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }

    /**
     * 增加型號的按鈕
     */
    private void setAddFunction(View view, Button btAdd, ArrayList<String> mChoose, NewSupportDCARecycleViewTypeChooser mAdapter) {
        btAdd.setOnClickListener((v -> {
            AlertDialog.Builder mAdd = new AlertDialog.Builder(DeviceControlActivity.this);
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            double weight = metrics.widthPixels * 0.7;
            double height = metrics.heightPixels * 0.6;
            View mV = LayoutInflater.from(view.getContext()).inflate(R.layout.new_dialog_type_chooser_item, null);
            mAdd.setView(mV);
            AlertDialog alertDialog = mAdd.create();
            ListView mListView = mV.findViewById(R.id.listView_DSA_dialog_items);
            String[] ableSetType = {"T", "H", "C", "D", "E", "M", "O", "P", "Q", "I", "R", "Y", "Z", "L"};
            ArrayAdapter adapter = new ArrayAdapter(mV.getContext(), android.R.layout.simple_list_item_1, ableSetType);
            mListView.setAdapter(adapter);
            alertDialog.show();
            alertDialog.getWindow().setLayout((int) weight, ViewGroup.LayoutParams.WRAP_CONTENT);
            mListView.setOnItemClickListener(((parent, view1, position, id) -> {
                if (mChoose.size() < 6 ) {//最多就六個不能再多
                    String getType = ableSetType[position];
                    if(mChoose.indexOf("L")==-1){//如果還沒有Ｌ
                        mChoose.add(getType);
                        mAdapter.notifyDataSetChanged();
                        getYZ(mChoose, mAdapter, getType);//處理Y,Z的憲制問題
                    }else {//有L
                        mChoose.add(getType);
                        Collections.swap(mChoose,mChoose.size()-1,mChoose.size()-2);
                        mAdapter.notifyDataSetChanged();
                        if (mChoose.indexOf("L")>0&& getType.contains("L")){//L重複
                            mChoose.remove(mChoose.indexOf("L"));
                            mAdapter.notifyItemRemoved(mChoose.indexOf("L"));
                        }
                        getYZ(mChoose, mAdapter, getType);//處理Y,Z的憲制問題
                    }//else
                    if (mChoose.indexOf("L")==0){//L在第一個
                        mChoose.remove(0);
                        mAdapter.notifyItemRemoved(0);
                    }
                } else {
                    if (ableSetType[position].contains("L")&&!mChoose.contains("L")){
                        mChoose.add("L");
                        mAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(mV.getContext(), "不可以再新增了", Toast.LENGTH_SHORT).show();
                    }

                }
            }));
        }));
    }
    /**處理Y,Z限制問題*/
    private void getYZ(ArrayList<String> mChoose, NewSupportDCARecycleViewTypeChooser mAdapter, String getType) {
        /*規則
        * 有Y就不可有Z
        * 有Z就不可有Y
        * Z只能出現一次
        * Y只能出現兩次*/
        if (getType.contains("Y") && mChoose.contains("Z")) {
            mChoose.remove(mChoose.indexOf("Y"));
            mAdapter.notifyItemRemoved(mChoose.indexOf("Y"));
        } else if (getType.contains("Z") && mChoose.contains("Y")) {
            mChoose.remove(mChoose.indexOf("Z"));
            mAdapter.notifyItemRemoved(mChoose.indexOf("Z"));
        }
        if (Collections.frequency(mChoose, "Y") > 2) {
            mChoose.remove(mChoose.indexOf("Y"));
            mAdapter.notifyItemRemoved(mChoose.indexOf("Y"));
        }
        if (Collections.frequency(mChoose, "Z") > 1) {
            mChoose.remove(mChoose.indexOf("Z"));
            mAdapter.notifyItemRemoved(mChoose.indexOf("Z"));
        }
    }


    /**
     * ======================================組合式大顯===============================================
     */
    private boolean count() {
        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                Log.v("BT", "倒數中:" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                if (mConnected == false) {
                    Log.v("BT", "自動連線第三次");
                    mBluetoothLeService.connect(SendType.DeviceAddress);
                    new CountDownTimer(5000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            Log.v("BT", "再次倒數中:" + millisUntilFinished / 1000);
                        }

                        @Override
                        public void onFinish() {
                            if (mConnected == false) {
                                waitdialog.dismiss();
                                Toast.makeText(getBaseContext(), R.string.maybeSoFair, Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    }.start();

                }

            }
        }.start();


        return false;
    }

    private void displayData(final String data,String byteString) {
        if (byteString.matches("455252")) {//如果是組合式大顯顯顯顯顯.....(還沒設定前)
            setNewMonitorTypeSetter();
        }
        if (data.contains("BT-")&&data.substring(data.lastIndexOf("-")+1).contains("N")){//取得新大顯型號資訊
            new Thread(()->{
                String type = data.substring(0,data.lastIndexOf("\n"));
                Log.d(TAG, "displayData: "+type);
                NewSendType.newDeviceType = type;
                NewSendType.row = Integer.parseInt(type.substring(3,4));
                String getType = type.substring(5,type.lastIndexOf("-"));
                int typeLength = type.substring(5,type.lastIndexOf("-")).length();//取得有幾個字
                if (typeLength-Integer.parseInt(type.substring(3,4))!=0&&type.substring(5,type.lastIndexOf("-")).contains("L")){
                    Log.d(TAG, "displayData: 有紀錄");
                    NewSendType.logSwitch = true;//開啟記錄
                }
                switch (typeLength){
                    case 1:
                        NewSendType.newFirstWord = getType.charAt(0);
                        break;
                    case 2:
                        NewSendType.newSecondWord = getType.charAt(1);
                        NewSendType.newFirstWord = getType.charAt(0);
                        break;
                    case 3:
                        NewSendType.newThirdWord= getType.charAt(2);
                        NewSendType.newSecondWord = getType.charAt(1);
                        NewSendType.newFirstWord = getType.charAt(0);
                        break;
                    case 4:
                        NewSendType.newForthWord = getType.charAt(3);
                        NewSendType.newThirdWord= getType.charAt(2);
                        NewSendType.newSecondWord = getType.charAt(1);
                        NewSendType.newFirstWord = getType.charAt(0);
                        break;
                    case 5:
                        NewSendType.newFiveWord =getType.charAt(4) ;
                        NewSendType.newForthWord = getType.charAt(3);
                        NewSendType.newThirdWord= getType.charAt(2);
                        NewSendType.newSecondWord = getType.charAt(1);
                        NewSendType.newFirstWord = getType.charAt(0);
                        break;
                    case 6:
                        NewSendType.newSixWord = getType.charAt(5);
                        NewSendType.newFiveWord =getType.charAt(4) ;
                        NewSendType.newForthWord = getType.charAt(3);
                        NewSendType.newThirdWord= getType.charAt(2);
                        NewSendType.newSecondWord = getType.charAt(1);
                        NewSendType.newFirstWord = getType.charAt(0);
                        break;
                    case 7:
                        NewSendType.newSevenWord = getType.charAt(6);
                        NewSendType.newSixWord = getType.charAt(5);
                        NewSendType.newFiveWord =getType.charAt(4) ;
                        NewSendType.newForthWord = getType.charAt(3);
                        NewSendType.newThirdWord= getType.charAt(2);
                        NewSendType.newSecondWord = getType.charAt(1);
                        NewSendType.newFirstWord = getType.charAt(0);
                        break;
                    case 8:
                        NewSendType.newEightWord = getType.charAt(7);
                        NewSendType.newSevenWord = getType.charAt(6);
                        NewSendType.newSixWord = getType.charAt(5);
                        NewSendType.newFiveWord =getType.charAt(4) ;
                        NewSendType.newForthWord = getType.charAt(3);
                        NewSendType.newThirdWord= getType.charAt(2);
                        NewSendType.newSecondWord = getType.charAt(1);
                        NewSendType.newFirstWord = getType.charAt(0);
                        break;
                    case 9:
                        NewSendType.newNineWord = getType.charAt(8);
                        NewSendType.newEightWord = getType.charAt(7);
                        NewSendType.newSevenWord = getType.charAt(6);
                        NewSendType.newSixWord = getType.charAt(5);
                        NewSendType.newFiveWord =getType.charAt(4) ;
                        NewSendType.newForthWord = getType.charAt(3);
                        NewSendType.newThirdWord= getType.charAt(2);
                        NewSendType.newSecondWord = getType.charAt(1);
                        NewSendType.newFirstWord = getType.charAt(0);
                        break;
                    case 10:
                        NewSendType.newTenWord = getType.charAt(9);
                        NewSendType.newNineWord = getType.charAt(8);
                        NewSendType.newEightWord = getType.charAt(7);
                        NewSendType.newSevenWord = getType.charAt(6);
                        NewSendType.newSixWord = getType.charAt(5);
                        NewSendType.newFiveWord =getType.charAt(4) ;
                        NewSendType.newForthWord = getType.charAt(3);
                        NewSendType.newThirdWord= getType.charAt(2);
                        NewSendType.newSecondWord = getType.charAt(1);
                        NewSendType.newFirstWord = getType.charAt(0);
                        break;
                }

            }).start();


        }


        if (data != null) {
            mDataField.setText(data);
            SendType.NormalData = data;
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
                Log.v("BT", "裝置有三個輸入" + data.substring(0, 8));
                if (data.length() == 37) {
                    Log.v("BT", "裝置有三個輸入而且還有記錄功能" + data.substring(0, 9));
                    SendType.FourthWord = data.charAt(8);
                    SendType.ThirdWord = data.charAt(7);
                    String getTable = data.substring(0, 8);
                    SendType.DB_TABLE = getTable.replace("-", "");
                } else {
                    SendType.ThirdWord = data.charAt(7);
                    String getTable = data.substring(0, 8);
                    SendType.DB_TABLE = getTable.replace("-", "");
                }

            } else if (data.charAt(3) == '2') {
                Log.v("BT", "裝置有兩個輸入");
                if (data.length() == 33) {
                    Log.v("BT", "裝置有兩個輸入而且還有記錄功能");
                    SendType.ThirdWord = data.charAt(7);
                    String getTable = data.substring(0, 8);
                    SendType.DB_TABLE = getTable.replace("-", "");
                } else {
                    String getTable = data.substring(0, 7);
                    SendType.DB_TABLE = getTable.replace("-", "");
                }

            } else if (data.charAt(3) == '1') {
                Log.v("BT", "裝置有一個輸入");
                String getTable = data.substring(0, 6);
                SendType.DB_TABLE = getTable.replace("-", "");
            }

        }
        if (data.contains("PASS")) {
//            SendType.SendForBLEDataType = "END";
//            SendType.getSendBluetoothLeService.
//                    setCharacteristicNotification(SendType.Mycharacteristic, true);
            mDataField.setText(R.string.Pz_input_psw);
            AlertDialog.Builder mBuidler = new AlertDialog.Builder(DeviceControlActivity.this);
            View v = getLayoutInflater().inflate(R.layout.activity_device_control_input_psw_dialog, null);
            final EditText edInput = (EditText) v.findViewById(R.id.editTextInput);
            mBuidler.setTitle(R.string.inputDevicePassword);
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
                    Log.v("BT", "取得裝置密碼: " + data.substring(4));
                    SendType.DevicePSW = data.substring(4, 10);
                    if (!edInput.getText().toString().isEmpty() &&
                            edInput.getText().toString().contains(data.substring(4, 10))) {
                        Toast.makeText(getBaseContext(), "登入成功", Toast.LENGTH_SHORT).show();
                        mDataField.setText(R.string.Correct);
                        GetDisplayData get = new GetDisplayData(data);
                        get.sendGet();
                        PASSOK = true;
                        dialog.dismiss();
                        waitdialog = ProgressDialog.show(DeviceControlActivity.this,//顯示等待圖示
                                getResources().getString(R.string.plzWait), getResources().getString(R.string.progressing), true);

                    } else if (!edInput.getText().toString().isEmpty() &&
                            edInput.getText().toString().contains("@JETEC")) {
                        Toast.makeText(getBaseContext(), "工程師模式", Toast.LENGTH_SHORT).show();
                        if (NewSendType.newDeviceType!= " "){
                            SendType.newMonitorChooser =1;
                            mDataField.setText(R.string.Correct);
                            GetDisplayData get = new GetDisplayData(data);
                            get.sendGet();
                            PASSOK = true;
                            dialog.dismiss();
                            waitdialog = ProgressDialog.show(DeviceControlActivity.this,//顯示等待圖示
                                    getResources().getString(R.string.plzWait), getResources().getString(R.string.progressing), true);
                        }
                        if (SendType.newMonitorChooser ==1){
                            NewSendType.engineerMode = true;

                        }else{
                            Intent intent = new Intent(DeviceControlActivity.this, EngineerMode.class);
                            startActivity(intent);
                        }

                    } else {
                        Toast.makeText(getBaseContext(), R.string.inputError, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }//PSW
        /**接收其他數據並分析*/
        String getMain = data.substring(0, 3);
        GetDisplayData get1 = new GetDisplayData(data);
        get1.analysisData(getMain);
        if (PASSOK == true) {

//            hashMap.put(getMain,data.substring(3,10));

            new CountDownTimer(5000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
//                    Log.v("BT","綜合讀秒中:"+millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    if (SendType.NormalData.contains("OVER")) {
//                        Log.v("BT","已正常連線取得數據");
                    } else {
                        SendType.SendForBLEDataType = "get";
                        SendType.getSendBluetoothLeService.
                                setCharacteristicNotification(SendType.Mycharacteristic, true);
                    }
                }
            }.start();
        }


        if (data.contains("OVER")) {
            switch (SendType.ThirdWord) {
                case 'L':
                    if (SendType.SecondWord == 'L' || SendType.ThirdWord == 'L') {
                        if (SendType.LOG == null) {
                            Log.v("BT", "出現null了");
                            SendType.SendForBLEDataType = "get";
                            SendType.getSendBluetoothLeService.
                                    setCharacteristicNotification(SendType.Mycharacteristic, true);
                        }
                        break;
                    }
            }
            switch (SendType.SecondWord) {
                case 'L':
                    if (SendType.SecondWord == 'L' || SendType.ThirdWord == 'L') {
                        if (SendType.LOG == null) {
                            Log.v("BT", "出現null了");
                            SendType.SendForBLEDataType = "get";
                            SendType.getSendBluetoothLeService.
                                    setCharacteristicNotification(SendType.Mycharacteristic, true);
                        }
                    }
                    break;

            }
            if (coint == 0) {
                SendType.SendForBLEDataType = "get";
                SendType.getSendBluetoothLeService.
                        setCharacteristicNotification(SendType.Mycharacteristic, true);
                coint++;
            } else if (coint == 1) {
                PASSOK = false;
//                Log.v("BT","HashMap測試:\n"+hashMap);
                goNextActivity();
            }


        }


    }//displayData(回傳值都在這邊操作)


    private void goNextActivity() {
        Intent intent = new Intent(DeviceControlActivity.this, DataDisplayActivity.class);
        startActivity(intent);
        waitdialog.dismiss();
        finish();
    }

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
        getWindow().setStatusBarColor(getResources().getColor(R.color.ActionBarColor));
        OptionThis = this;
        ClearAllData clearAllData = new ClearAllData();
        clearAllData.clearAllData();//清除上一個裝置連線的所有資料

        new ClearNewSendType().clearNewAll();//清除所有新大顯資料

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
                Log.v("BT", "手動連線");
                mBluetoothLeService.connect(SendType.DeviceAddress);
                tt = 0;
                return true;
            case R.id.menu_disconnect:
                mBluetoothLeService.disconnect();
                tt = 1;
                Log.v("BT", "手動取消");
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateConnectionState(final int resourceId, final int colorId) {
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
//        for (BluetoothGattService gattService : gattServices) {
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

    /**
     * Byte轉16進字串工具
     */
    public static String byteArrayToHexStr(byte[] byteArray) {
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

}
