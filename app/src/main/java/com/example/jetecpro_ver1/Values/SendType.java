package com.example.jetecpro_ver1.Values;

import android.bluetooth.BluetoothGattCharacteristic;

import com.example.jetecpro_ver1.BLE_function.BluetoothLeService;

public class SendType {

    public static String SendForBLEDataType;//要送出的指令
    public static String DeviceType;//大顯型號
    public static String DeviceName;//裝置名稱
    public static String DeviceAddress;//裝置位址
    public static BluetoothGattCharacteristic Mycharacteristic;//每次連接資料用的
    public static BluetoothLeService getSendBluetoothLeService;
}
