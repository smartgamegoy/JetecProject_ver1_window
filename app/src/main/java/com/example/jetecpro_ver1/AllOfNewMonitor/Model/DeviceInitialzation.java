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
        //送大家都有的值







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
