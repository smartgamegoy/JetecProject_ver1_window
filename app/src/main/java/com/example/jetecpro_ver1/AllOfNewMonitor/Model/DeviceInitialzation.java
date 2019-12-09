package com.example.jetecpro_ver1.AllOfNewMonitor.Model;

import android.os.SystemClock;
import android.util.Log;

import com.example.jetecpro_ver1.Values.SendType;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DeviceInitialzation {
    public static final String TAG = DeviceInitialzation.class.getSimpleName();


    public DeviceInitialzation() {
    }

    public void inItalzation(String defaultName, String getType, String newMonitorWord
            , ArrayList<String> mChoose) {
        //    String defaultName;//BT-...
        //    String getType;//BT-XXXXXXXXXX...
        //    String newMonitorWord;//BT-XXXXXXXXXX-O

        Log.d(TAG, "取得型號: " + defaultName + getType + newMonitorWord);
        //範例：sendByte(1, 7, 1, 20);
        //範例：sendString(PSW000000)
        //範例：sendMixStringByte("57414E54",20)
        SimpleDateFormat date = new SimpleDateFormat("yyMMdd");
        SimpleDateFormat time = new SimpleDateFormat("HHmmss");

        Date dNow = new Date();
        //送大家都有的值
        sendString("NAMEJTC-N-Test");
        SystemClock.sleep(500);
        sendString("PWR=000000");//密碼
        SystemClock.sleep(500);
        sendString("DATE"+date.format(dNow));
        SystemClock.sleep(500);
        sendString("TIME"+time.format(dNow));
        SystemClock.sleep(500);
        sendString(defaultName + getType + newMonitorWord);
        SystemClock.sleep(500);
        //送大家都有的值
        for (int i=0;i<mChoose.size();i++){//取得選擇型號與取得它的排數
            Log.d(TAG, "inItalzation: 第"+(i+1)+"排是："+mChoose.get(i));
            switch (mChoose.get(i)){
                case "T"://溫
                    sendByte(i+1,1,0,0);//PV
                    SystemClock.sleep(500);
                    sendByte(i+1,2,0,65);//EH
                    SystemClock.sleep(500);
                    sendByte(i+1,3,0,-10);//EL
                    SystemClock.sleep(500);
                    sendByte(i+1,6,0,0);//CR
                    SystemClock.sleep(500);
                    sendByte(i+1,10,0,0);//RL1
                    SystemClock.sleep(500);
                    sendByte(i+1,11,0,0);//RL2
                    SystemClock.sleep(500);
                    sendByte(i+1,12,0,0);//RL3
                    SystemClock.sleep(500);
                    break;
                case "H"://濕
                    sendByte(i+1,1,0,0);//PV
                    SystemClock.sleep(500);
                    sendByte(i+1,2,0,100);//EH
                    SystemClock.sleep(500);
                    sendByte(i+1,3,0,0);//EL
                    SystemClock.sleep(500);
                    sendByte(i+1,6,0,0);//CR
                    SystemClock.sleep(500);
                    sendByte(i+1,10,0,0);//RL1
                    SystemClock.sleep(500);
                    sendByte(i+1,11,0,0);//RL2
                    SystemClock.sleep(500);
                    sendByte(i+1,12,0,0);//RL3
                    SystemClock.sleep(500);
                    break;
                case "C"://二氧化碳2000
                    sendByte(i+1,1,0,0);//PV
                    SystemClock.sleep(500);
                    sendByte(i+1,2,0,2000);//EH
                    SystemClock.sleep(500);
                    sendByte(i+1,3,0,0);//EL
                    SystemClock.sleep(500);
                    sendByte(i+1,6,0,0);//CR
                    SystemClock.sleep(500);
                    sendByte(i+1,10,0,0);//RL1
                    SystemClock.sleep(500);
                    sendByte(i+1,11,0,0);//RL2
                    SystemClock.sleep(500);
                    sendByte(i+1,12,0,0);//RL3
                    SystemClock.sleep(500);
                    break;
                case "D"://二氧化碳3000
                    sendByte(i+1,1,0,0);//PV
                    SystemClock.sleep(500);
                    sendByte(i+1,2,0,3000);//EH
                    SystemClock.sleep(500);
                    sendByte(i+1,3,0,0);//EL
                    SystemClock.sleep(500);
                    sendByte(i+1,6,0,0);//CR
                    SystemClock.sleep(500);
                    sendByte(i+1,10,0,0);//RL1
                    SystemClock.sleep(500);
                    sendByte(i+1,11,0,0);//RL2
                    SystemClock.sleep(500);
                    sendByte(i+1,12,0,0);//RL3
                    SystemClock.sleep(500);
                    break;
                case "E"://二氧化碳5000
                    sendByte(i+1,1,0,0);//PV
                    SystemClock.sleep(500);
                    sendByte(i+1,2,0,5000);//EH
                    SystemClock.sleep(500);
                    sendByte(i+1,3,0,0);//EL
                    SystemClock.sleep(500);
                    sendByte(i+1,6,0,0);//CR
                    SystemClock.sleep(500);
                    sendByte(i+1,10,0,0);//RL1
                    SystemClock.sleep(500);
                    sendByte(i+1,11,0,0);//RL2
                    SystemClock.sleep(500);
                    sendByte(i+1,12,0,0);//RL3
                    SystemClock.sleep(500);
                    break;
                case "M"://PM2.5
                case "P"://壓力
                case "Q"://PM10
                    sendByte(i+1,1,0,0);//PV
                    SystemClock.sleep(500);
                    sendByte(i+1,2,0,1000);//EH
                    SystemClock.sleep(500);
                    sendByte(i+1,3,0,0);//EL
                    SystemClock.sleep(500);
                    sendByte(i+1,6,0,0);//CR
                    SystemClock.sleep(500);
                    sendByte(i+1,10,0,0);//RL1
                    SystemClock.sleep(500);
                    sendByte(i+1,11,0,0);//RL2
                    SystemClock.sleep(500);
                    sendByte(i+1,12,0,0);//RL3
                    SystemClock.sleep(500);
                    break;
                case "O"://噪音
                    sendByte(i+1,1,0,0);//PV
                    SystemClock.sleep(500);
                    sendByte(i+1,2,0,130);//EH
                    SystemClock.sleep(500);
                    sendByte(i+1,3,0,30);//EL
                    SystemClock.sleep(500);
                    sendByte(i+1,6,0,0);//CR
                    SystemClock.sleep(500);
                    sendByte(i+1,10,0,0);//RL1
                    SystemClock.sleep(500);
                    sendByte(i+1,11,0,0);//RL2
                    SystemClock.sleep(500);
                    sendByte(i+1,12,0,0);//RL3
                    SystemClock.sleep(500);
                    break;

                case "I"://類比
                    sendByte(i+1,1,0,0);//PV
                    SystemClock.sleep(500);
                    sendByte(i+1,2,0,9999);//EH
                    SystemClock.sleep(500);
                    sendByte(i+1,3,0,-9999);//EL
                    SystemClock.sleep(500);
                    sendByte(i+1,6,0,9999);//CR
                    SystemClock.sleep(500);
                    sendByte(i+1,10,0,0);//RL1
                    SystemClock.sleep(500);
                    sendByte(i+1,11,0,0);//RL2
                    SystemClock.sleep(500);
                    sendByte(i+1,12,0,0);//RL3
                    SystemClock.sleep(500);
                    break;
                case "R":

                    break;
                case "Y":

                    break;
                case "Z":

                    break;
                case "L":

                    break;

            }

        }



    }





    //+++++++++++++++++++++++++++以下為模組區+++++++++++++++++++++++++++//

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
}
