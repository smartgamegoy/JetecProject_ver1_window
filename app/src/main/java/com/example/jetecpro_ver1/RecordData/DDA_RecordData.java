package com.example.jetecpro_ver1.RecordData;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.jetecpro_ver1.MainProcess.DataDisplayActivity;
import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.SendData.DDA_SendData;
import com.example.jetecpro_ver1.Values.SendType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DDA_RecordData {

    Context context;

    public DDA_RecordData(Context context) {
        this.context = context;
    }

    /**
     * 發送開始紀錄指令(背景執行)
     */
    public void sendRecordMessage() {
        String dateformat = "yyyyMMdd";
        String dateTime = "HH:mm:ss";
        Calendar mCal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        SimpleDateFormat dt = new SimpleDateFormat(dateTime);
        String today = df.format(mCal.getTime());//輸出20190828
        String time = dt.format(mCal.getTime());//輸出16:54:19
        String todaySend = today.substring(2);
        String timeSend = time.replaceAll(":", "");
        Log.v("BT", "年: " + todaySend + " 時間: " + timeSend);
        SendType.SendForBLEDataType = "DATE" + todaySend;
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
        SystemClock.sleep(500);
        SendType.SendForBLEDataType = "TIME" + timeSend;
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
        SystemClock.sleep(500);
        sendINTER2BT(SendType.INTER2SQL);
        SystemClock.sleep(500);
        SendType.SendForBLEDataType = "START";
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
        SystemClock.sleep(500);
        SendType.SendForBLEDataType = "get";
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
        SystemClock.sleep(500);


    }

    /**
     * 輔助INTER送出
     */
    private void sendINTER2BT(String value) {
        int v = Integer.parseInt(value);
        if (v >= 1000) {
            SendType.SendForBLEDataType = "INTER0" + v;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (v <= 999 && v >= 100) {
            SendType.SendForBLEDataType = "INTER00" + v;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (v < 99) {
            SendType.SendForBLEDataType = "INTER000" + v;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        }
    }

    /**
     * 停止紀錄指令(背景執行)
     */
    public void stopRecordMessage() {

        SendType.SendForBLEDataType = "END";
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
        SystemClock.sleep(500);
        SendType.SendForBLEDataType = "get";
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
        SystemClock.sleep(500);

    }
}
