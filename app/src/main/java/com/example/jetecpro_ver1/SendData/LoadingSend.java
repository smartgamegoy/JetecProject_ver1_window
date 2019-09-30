package com.example.jetecpro_ver1.SendData;

import android.content.Context;
import android.util.Log;

import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.Values.SendType;

public class LoadingSend {


    String id;
    String value;
    Context co;

    public LoadingSend(String id, String value, Context context){
        this.id = id;
        this.value = value;
        this.co = context;
    }
    /**分類資料*/
    public void ChickData(){
        if(id.contains(co.getString(R.string.device_name))){
            //先不做處理
        }else{
            switch (id){

                case "SPK":
                    sendSPK2BT(id,value);
                    break;

                case "DP1":
                case "DP2":
                case "DP3":
                    sendDP2BT(id,value);
                    break;

                case "INTER":
                    sendINTER2BT(value);
                    break;
                default:
                    sendData2BT(Double.parseDouble(value),id);
                    break;

            }

        }

    }
    /**一般數據傳送*/
    private void sendData2BT(Double input, String inputType) {

        if (input >= 1000) {
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "+" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (input >= 100 && input <= 999.9) {
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "+0" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (input >= 10 && input <= 99.9) {
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "+00" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (input >= 0 && input <= 9.9) {
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "+000" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);

        } else if (input >= -9.9 && input <= 0) {
            input = Math.abs(input);
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "-000" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (input >= -99.9 && input <= -10) {
            input = Math.abs(input);
            input = Math.abs(input);
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "-00" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (input >= -999.9 && input <= -100) {
            input = Math.abs(input);
            input = Math.abs(input);
            input = Math.abs(input);
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "-0" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (input <= 1000) {
            input = Math.abs(input);
            input = Math.abs(input);
            input = Math.abs(input);
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "-" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);

        }


    }
    /**警報數據傳送*/
    private void sendSPK2BT(String id,String value){
        if (value.contains("on")){
            SendType.SendForBLEDataType = id + "+0001.0";
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        }else if(value.contains("off")){
            SendType.SendForBLEDataType = id + "+0000.0";
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        }else{
            Log.v("BT","花惹發??????????????");
        }

    }
    /**小數點數據傳送*/
    private void sendDP2BT(String id,String value){
        if (value.contains("on")){
            SendType.SendForBLEDataType = id + "+0001.0";
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        }else if(value.contains("off")){
            SendType.SendForBLEDataType = id + "+0000.0";
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        }else{
            Log.v("BT","花惹發??????????????");
        }
    }
    /**INTER數據傳送*/
    private void sendINTER2BT(String value){
        int v = Integer.parseInt(value);
        if(v >= 1000){
            SendType.SendForBLEDataType = "INTER0"+v;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        }else if(v <=999 && v >=100){
            SendType.SendForBLEDataType = "INTER00"+v;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        }else if(v <99){
            SendType.SendForBLEDataType = "INTER000"+v;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        }
    }


}
