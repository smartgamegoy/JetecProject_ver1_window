package com.example.jetecpro_ver1.AllOfNewMonitor.Model.NDD_NewDataDisplaySupport;

import android.app.Activity;
import android.os.SystemClock;
import android.util.Log;

import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NewSendType;
import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.Values.SendType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NewNDD_ModifiedDataSendOut {
    String TAG = NewNDD_ModifiedDataSendOut.class.getSimpleName();
    int area;
    ArrayList<HashMap<String,String>> arrayList;
    Activity activity;
    ArrayList<Integer> getTabs;
    private final int count= 300;

    public NewNDD_ModifiedDataSendOut(int area, ArrayList<HashMap<String, String>> arrayList, Activity activity, ArrayList<Integer> getTabs) {
        this.area = area;
        this.arrayList = arrayList;
        this.activity = activity;
        this.getTabs = getTabs;
    }

    public void sendOut(){

        Log.d(TAG, "sendOut: "+arrayList+", "+area);
        sendString("ERA"+area);//先送區域
//        Log.d(TAG, "sendOut: "+"ERA"+area);
        SystemClock.sleep(count);
        for (int i=0;i<arrayList.size();i++){
//            sendByte(area,type,DP,value);
            String title = arrayList.get(i).get("Title");
            String value = arrayList.get(i).get("Value");

            if (area<7){//非輸出的區域
                sendByte(area,title2type(title),getDP(title,arrayList),value2SendValue(value,getDP(title,arrayList)));
            }else{//輸出區域


            }



            SystemClock.sleep(count);
        }





    }




    /*============================================模組區============================================*/

    private int title2type(String title){//Ex:將"上線警報"轉為"2",type的部分
            if (title.matches(trans(R.string.PV))){
                return 1;
            }else if(title.matches(trans(R.string.EH))){
                return 2;
            }else if(title.matches(trans(R.string.EL))){
                return 3;
            }else if(title.matches(trans(R.string.IH))){
                return 4;
            }else if(title.matches(trans(R.string.IL))){
                return 5;
            }else if(title.matches(trans(R.string.CR))){
                return 6;
            }else if(title.matches(trans(R.string.ADR))){
                return 7;
            }else if(title.matches(trans(R.string.REG))){
                return 8;
            }else if(title.matches(trans(R.string.LEN))){
                return 9;
            }else if(title.matches(trans(R.string.RL123)+"1")){
                return 10;
            }else if(title.matches(trans(R.string.RL123)+"2")){
                return 11;
            }else if(title.matches(trans(R.string.RL123)+"3")){
                return 12;
            }else if(title.matches(trans(R.string.RL456)+"1")){
                return 13;
            }else if(title.matches(trans(R.string.RL456)+"2")){
                return 14;
            }else if(title.matches(trans(R.string.RL456)+"3")){
                return 15;
            }else{
                return 0;
            }

    }
    private int getDP(String title,ArrayList<HashMap<String,String>> hashMaps){
        for (int i=0;i<hashMaps.size();i++){
            if (title.matches(trans(R.string.new_Decimal))){
                return Integer.parseInt(hashMaps.get(i).get("Value"));
            }

        }
        return 0;

    }

    private int value2SendValue(String value,int dp){
        if (value.matches(trans(R.string.alarmOn))){
            return 1;
        }else if (value.matches(trans(R.string.alarmOff))){
            return 0;
        }
        return Integer.parseInt(value);

    }


    /**
     * 送字串模組
     */
    private void sendString(String getSend) {
        SendType.SendForBLEDataType = getSend;
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
    }

    /**
     * 送byte模組
     */
    private void sendByte(int sendRow, int sendType, int sendDp, int sendValue) {
        String value = Integer.toHexString(sendValue);
        SendType.sentByteChoose = 1;
        SendType.SendForBLEbyteType = fromHexString(
                String.format("%02x", sendRow)//第幾排
                        + String.format("%02x", sendType)//代號
                        + String.format("%02x", sendDp)//小數點
                        + String.format("%08x", Long.parseLong(value, 16)));//00 00 000 000 0000
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
    }
    /**
     * 送String+byte模組
     */
    private void sendMixStringByte(String title, int value) {
        /**
         * 說明：title請使用ASCll送出，例如"WANT"就是57414E54
         * value就是值，沒什麼
         * */
        SendType.sentByteChoose = 1;
        SendType.SendForBLEbyteType = fromHexString(title + String.format("%06x", value));//00 00 000 000 0000
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
    }

    /**
     * 將數值轉從數字字串16進
     */
    private byte[] fromHexString(String src) {
        byte[] biBytes = new BigInteger("10" + src.replaceAll("\\s", ""), 16).toByteArray();
        return Arrays.copyOfRange(biBytes, 1, biBytes.length);
    }

    /**
     * 處理文字
     */
    private String trans(int name) {
        String str = activity.getResources().getString(name);
        return str;
    }
    /*============================================模組區============================================*/
}
