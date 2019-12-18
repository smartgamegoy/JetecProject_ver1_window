package com.example.jetecpro_ver1.AllOfNewMonitor.Model.NDD_NewDataDisplaySupport;

import android.app.Activity;
import android.util.Log;

import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NewSendType;
import com.example.jetecpro_ver1.R;

import java.util.ArrayList;
import java.util.HashMap;

public class NewSupportSortData {
    String TAG = NewSupportSortData.class.getSimpleName();
    int area;
    String data;
    Activity activity;

    public NewSupportSortData(int area, String data, Activity activity) {
        this.area = area;
        this.data = data;
        this.activity = activity;
    }

    /**設置標題*/
    public String ReBackTitle() {
        int row = Integer.parseInt(data.substring(0, 2), 16);
        int type = Integer.parseInt(data.substring(2, 4), 16);

        if (row == area) {//取出同一個排的
            switch (type) {
                case 0:
                    return "xx";
                case 1:
                    return trans(R.string.PV);
                case 2:
                    return trans(R.string.EH);
                case 3:
                    return trans(R.string.EL);
                case 4:
                    return trans(R.string.IH);
                case 5:
                    return trans(R.string.IL);
                case 6:
                    return trans(R.string.CR);
                case 7:
                    return trans(R.string.ADR);
                case 8:
                    return trans(R.string.REG);
                case 9:
                    return trans(R.string.LEN);
                case 10:
                    return trans(R.string.RL1);
                case 11:
                    return trans(R.string.RL2);
                case 12:
                    return trans(R.string.RL3);
                case 13:
                    return trans(R.string.RL4);
                case 14:
                    return trans(R.string.RL5);
                case 15:
                    return trans(R.string.RL6);

            }


        }
        return "xx";
    }
    /**設置內容*/
    public String ReBackValues() {
        int row = Integer.parseInt(data.substring(0, 2), 16);
        int type = Integer.parseInt(data.substring(2, 4), 16);
        int dp = Integer.parseInt(data.substring(4, 6), 16);
        double values;
        Log.d(TAG, "ReBackValues: "+data);
        try {
            double i1 =Long.valueOf(data.substring(6),16).intValue();
            switch (dp){
                default:
                    values =i1;
                    break;
                case 1:
                    values = i1/10;
//                    values = Integer.parseInt(Integer.toHexString((short) Integer.parseInt(data.substring(6), 16)/10));
                    break;
                case 2:
                    values = i1/100;
                    /*values = (short)Integer.parseInt(Long.toHexString(Long.parseLong(data.substring(6)
                            , 16)/Long.parseLong("0064", 16)),16);*/

                    break;
                case 3:
                    values = i1/1000;
//                    values = Integer.parseInt(Integer.toHexString((short) Integer.parseInt(data.substring(6), 16)/1000));
                    break;

            }

        } catch (Exception e) {
            values = (short) Integer.parseInt(data.substring(10), 16);
            Log.d(TAG, "ReBackValues: here"+values);
        }
//        Log.d(TAG, "ReBackValues: "+data);
//        Log.d(TAG, "ReBackValues: "+data.substring(6));
//        Log.d(TAG, "ReBackValues: "+values);

        switch (type) {
            case 10:
            case 11:
            case 12:
                if (values == 0) {
                    return trans(R.string.alarmOff);
                } else if (values == 1) {
                    return trans(R.string.alarmOn);
                } else {
                    return String.valueOf(values);
                }
            case 13:
            case 14:
            case 15:
//                Log.d(TAG, "ReBackValues: "+values);

                    return RL456GetValue((int)values);

        }

//        return String.valueOf(values);
        switch (dp){
            case 1:
                return String.format("%.1f",values);
            case 2:
                return String.format("%.2f",values);
            case 3:
                return String.format("%.3f",values);
                default:
                    return String.format("%.0f",values);
        }


    }
    /**設置RL456的標籤*/
    private String RL456GetValue(int input) {
        String TypeTag = NewSendType.newDeviceType.substring(5, NewSendType.newDeviceType.lastIndexOf("-"));
//        Log.d(TAG, "RL456GetValue: "+TypeTag);
        if (input>0){
            switch (TypeTag.charAt(input-1)) {
                case 'T':
                    return trans(R.string.Temperature);
                case 'H':
                    return trans(R.string.Humidity);
                case 'C':
                case 'D':
                case 'E':
                    return trans(R.string.CO2);
                case 'M':
                    return trans(R.string.PM2_5);
                case 'O':
                    return trans(R.string.Noise);
                case 'P':
                    return trans(R.string.press);
                case 'Q':
                    return trans(R.string.PM10);
                case 'I':
                    return trans(R.string.analog);
                case 'R':
                    return "R";
                default:
                    return " ";

            }
        }else{
            return " ";
        }



    }

    /**設置小數點調整欄位的數值*/
    public String getDP() {
        int row = Integer.parseInt(data.substring(0, 2), 16);
        if (row == area) {
            return String.valueOf(Integer.parseInt(data.substring(4, 6), 16));
        } else {
            return "9";
        }
    }

    private String trans(int id) {
        return activity.getResources().getString(id);
    }
}
