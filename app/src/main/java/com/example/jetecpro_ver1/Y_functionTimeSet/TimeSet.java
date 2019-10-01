package com.example.jetecpro_ver1.Y_functionTimeSet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.Values.SendType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeSet {
    AlertDialog.Builder timeSetBuilder;
    Activity activity;
    String GetName;

    public TimeSet(AlertDialog.Builder timeSetBuilder, Activity activity, String getName) {
        this.timeSetBuilder = timeSetBuilder;
        this.activity = activity;
        GetName = getName;
    }

    public void flashSetting(){
        SimpleDateFormat nowTime = new SimpleDateFormat("HHmmss");
        SimpleDateFormat today   = new SimpleDateFormat("yyMMdd");
        Date date = new Date();
        String Stoday = today.format(date);
        String Stime = nowTime.format(date);

        SendType.SendForBLEDataType = "DATE"+Stoday;
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
        SystemClock.sleep(500);
        SendType.SendForBLEDataType = "TIME"+Stime;
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
        Toast.makeText(activity,R.string.Y_modifyOK,Toast.LENGTH_SHORT).show();


    }
    public void manualSetting(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.activity_data_display_manual_timeset, null);
        mBuilder.setView(view);
        mBuilder.setTitle(GetName);
        final NumberPicker nbHr  = view.findViewById(R.id.nb_Hour);
        final NumberPicker nbMin = view.findViewById(R.id.nb_Min);
        final NumberPicker nbSec = view.findViewById(R.id.nb_Sec);

        SimpleDateFormat HH = new SimpleDateFormat("HH");
        SimpleDateFormat MM = new SimpleDateFormat("mm");
        SimpleDateFormat SS = new SimpleDateFormat("ss");
        SimpleDateFormat DD = new SimpleDateFormat("yyMMdd");
        Date date = new Date();
        String hour = HH.format(date);
        String min = MM.format(date);
        String sec = SS.format(date);
        final String Day = DD.format(date);

        nbHr.setMinValue(Integer.parseInt(hour));
        nbMin.setMinValue(Integer.parseInt(min));
        nbSec.setMinValue(Integer.parseInt(sec));

        nbHr.setMinValue(0);
        nbMin.setMinValue(0);
        nbSec.setMinValue(0);
        nbHr.setMaxValue(24);
        nbMin.setMaxValue(60);
        nbSec.setMaxValue(60);

        mBuilder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newHour = String.valueOf(nbHr.getValue());
                String newMin = String.valueOf(nbMin.getValue());
                String newSec = String.valueOf(nbSec.getValue());

                String SendH;
                String SendM;
                String SendS;

                if (nbHr.getValue()<10){
                    SendH = "0"+newHour;
                }else{
                    SendH = newHour;
                }
                if(nbMin.getValue()<10){
                    SendM = "0"+newMin;
                }else{
                    SendM = newMin;
                }if(nbSec.getValue()<10){
                    SendS = "0"+newMin;
                }else{
                    SendS = newSec;
                }
//                Log.v("BT","TIME"+SendH+SendM+SendS+",   "+Day);
                SendType.SendForBLEDataType = "DATE"+Day;
                SendType.getSendBluetoothLeService.
                        setCharacteristicNotification(SendType.Mycharacteristic, true);
                SystemClock.sleep(500);
                Log.v("BT","TIME"+SendH+" "+SendM+" "+SendS);//因好像有問題又好像沒問題，故留著
                SendType.SendForBLEDataType = "TIME"+SendH+SendM+SendS;
                SendType.getSendBluetoothLeService.
                        setCharacteristicNotification(SendType.Mycharacteristic, true);
                Toast.makeText(activity,R.string.Y_modifyOK,Toast.LENGTH_SHORT).show();

            }
        });
        mBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        mBuilder.show();
    }
}
