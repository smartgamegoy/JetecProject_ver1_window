package com.example.jetecpro_ver1.Values;

import android.app.AlertDialog;
import android.view.View;

import com.example.jetecpro_ver1.MainProcess.DeviceControlActivity;

public class GetDisplayData{

    String data;

    public GetDisplayData(String data){
        this.data = data;
    }
    public void GetOK(){
        SendType.SendForBLEDataType = "PASSWD";
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic,true);
    }

    public void sendGet(){
        SendType.SendForBLEDataType = "get";
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic,true);

    }




}
