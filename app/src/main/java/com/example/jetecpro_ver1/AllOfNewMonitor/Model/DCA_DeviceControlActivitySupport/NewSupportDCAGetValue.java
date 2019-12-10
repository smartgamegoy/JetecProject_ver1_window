package com.example.jetecpro_ver1.AllOfNewMonitor.Model.DCA_DeviceControlActivitySupport;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;

import com.example.jetecpro_ver1.AllOfNewMonitor.Controll.NewDataDisplay;

public class NewSupportDCAGetValue {
    public static final String TAG = NewSupportDCAGetValue.class.getSimpleName();
    Activity activity;
    Dialog waitDialog;

    public NewSupportDCAGetValue(Activity activity,Dialog waitDialog) {
        this.activity = activity;
        this.waitDialog = waitDialog;
    }

    public void getValueFromDevice(String stringData, String byteData){
        Log.d(TAG, "newGetValue byte： " + byteData + ", 字串: " + stringData);






        if (stringData.contains("OVER")&&byteData.contains("4F564552")){
            waitDialog.dismiss();
            Intent intent = new Intent(activity, NewDataDisplay.class);

            activity.startActivity(intent);
            activity.finish();
        }

    }
}
