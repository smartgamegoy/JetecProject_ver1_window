package com.example.jetecpro_ver1.AllOfNewMonitor.Model.DCA_DeviceControlActivitySupport;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;

import com.example.jetecpro_ver1.AllOfNewMonitor.Controll.NewDataDisplay;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NewSendType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class NewSupportDCAGetValue {
    public static final String TAG = NewSupportDCAGetValue.class.getSimpleName();
    Activity activity;
    Dialog waitDialog;

    static HashMap<String,ArrayList<String>> hashMap = new HashMap<>();
    static ArrayList <String> arrayList = new ArrayList<>();
    static ArrayList <String> arrayByte = new ArrayList<>();

    static HashSet<Integer> hashSet = new HashSet<>();


    public NewSupportDCAGetValue(Activity activity, Dialog waitDialog) {
        this.activity = activity;
        this.waitDialog = waitDialog;
    }

    public void getValueFromDevice(String stringData, String byteData) {

        Log.d(TAG, "newGetValue byte： " + byteData + ", 字串: " + stringData.substring(0, stringData.indexOf("\n")));

        if (stringData.substring(0, 5).contains("COUNT")) {
            //暫時沒有
            arrayList.add(stringData.substring(0,stringData.indexOf("\n")));
            hashMap.put("String",arrayList);
        } else if (stringData.substring(0, 5).contains("INTER")) {
            //暫時沒有
            arrayList.add(stringData.substring(0,stringData.indexOf("\n")));
            hashMap.put("String",arrayList);
        } else if (stringData.substring(0, 4).contains("TIME")) {
            //暫時沒有
            arrayList.add(stringData.substring(0,stringData.indexOf("\n")));
            hashMap.put("String",arrayList);
        } else if (stringData.substring(0, 4).contains("DATE")) {
            //暫時沒有
            arrayList.add(stringData.substring(0,stringData.indexOf("\n")));
            hashMap.put("String",arrayList);
        } else if (stringData.substring(0, 3).contains("LOG")) {
            arrayList.add(stringData.substring(0,stringData.indexOf("\n")));
            hashMap.put("String",arrayList);
        }else if (stringData.contains("OVER") && byteData.contains("4F564552")) {
            waitDialog.dismiss();
            ArrayList<Integer> getRow = new ArrayList<Integer>(hashSet);
            Intent intent = new Intent(activity, NewDataDisplay.class);
            intent.putExtra("SettingArray",hashMap);
            intent.putExtra("GetTabRow",getRow);
            activity.startActivity(intent);
            activity.finish();
        }else if(stringData.substring(0, 4).contains("BYTE")){
            hashMap.clear();
            arrayList.clear();
            hashSet.clear();
        }else{
            arrayByte.add(byteData);
            hashMap.put("Byte",arrayByte);
            int row = Integer.parseInt(byteData.substring(0,2));
            hashSet.add(row);

        }

    }
}
