package com.example.jetecpro_ver1.RecordData;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jetecpro_ver1.MainProcess.DataDisplayActivity;
import com.example.jetecpro_ver1.ModifyPassword.ModifyPSW;
import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.Values.SendType;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetRecordDataAndDownload {
    static int i = 0;
    AlertDialog dialog;
    Context context;
    View view;
    AlertDialog.Builder mBuilder;
     int choose;
    Button btDownload,btCencel;
    TextView tvMonitor;

    public GetRecordDataAndDownload(Context context, View view, AlertDialog.Builder mBuilder) {
        this.context = context;
        this.view = view;
        this.mBuilder = mBuilder;
    }

    public void ENDRecord() {

        SendType.SendForBLEDataType = "END";
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
        SystemClock.sleep(1500);
        SendType.SendForBLEDataType = "get";
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
        if (SendType.mLOG == null){
            SendType.SendForBLEDataType = "get";
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        }

    }
    public void MainCheck(){
        mBuilder.setView(view);
        final AlertDialog dialog = mBuilder.create();
        final Button btDownload  = view.findViewById(R.id.GoDownload);
        final Button btCencel    = view.findViewById(R.id.CencelButton);
        final TextView tvMonitor = view.findViewById(R.id.monitorTextView);

        this.btDownload = btDownload;
        this.btCencel   = btCencel;
        this.tvMonitor  = tvMonitor;
        this.dialog     = dialog;
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        tvMonitor.setText(trans(R.string.CountQuantity)+"\t"+SendType.mCOUNT);
        btDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataType();
                dialog.dismiss();
            }
        });

        btCencel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }



    private void checkDataType() {
        AlertDialog.Builder chooseBuilder = new AlertDialog.Builder(DataDisplayActivity.DisplayData);
        chooseBuilder.setTitle(R.string.chooseDownModeTitle);
        chooseBuilder.setMessage(R.string.notifyToDevice);
        chooseBuilder.setPositiveButton(R.string.HighMode, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choose = 1;
               Mode();

            }
        });
        chooseBuilder.setNegativeButton(R.string.stableMode, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choose = 2;
                Mode();
            }
        });
        chooseBuilder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        chooseBuilder.show();

    }


    /**
     * 下載那一坨資料用的副程式
     */
    private void Mode() {
       final Dialog d = ProgressDialog.show(DataDisplayActivity.DisplayData
               ,trans(R.string.plzWait),trans(R.string.progressing),true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SendType.SendForBLEDataType = "END";
                SendType.getSendBluetoothLeService.
                        setCharacteristicNotification(SendType.Mycharacteristic, true);
                SystemClock.sleep(500);
//                SendType.SendForBLEDataType = "get";
//                SendType.getSendBluetoothLeService.
//                        setCharacteristicNotification(SendType.Mycharacteristic, true);
//                SystemClock.sleep(500);
                SendType.SendForBLEDataType = "Count" + SendType.Count2Send;//指定下載數量
                SendType.getSendBluetoothLeService.
                        setCharacteristicNotification(SendType.Mycharacteristic, true);
                SystemClock.sleep(500);
                if(choose == 1){
//                    SendType.SendForBLEDataType = "Delay00020";//高速
                        SendType.SendForBLEDataType = "Delay00005";//高速
                }else if(choose == 2){
//                    SendType.SendForBLEDataType = "Delay00080";//穩定
                    SendType.SendForBLEDataType = "Delay00050";//穩定
                }
                SendType.getSendBluetoothLeService.
                        setCharacteristicNotification(SendType.Mycharacteristic, true);
                SystemClock.sleep(500);
                SendType.SendForBLEDataType = "DOWNLOAD";
                SendType.getSendBluetoothLeService.
                        setCharacteristicNotification(SendType.Mycharacteristic, true);

                d.dismiss();
                Looper.prepare();
                DataDisplayActivity dataDisplayActivity = new DataDisplayActivity();
                dataDisplayActivity.getDownloadData();
                Looper.loop();

            }
        }).start();


    }


    private String trans(int name) {//不是我在講...每個都要寫ctx.getResources().getString(R.string.??);真的會死人
        String str = context.getResources().getString(name);
        return str;
    }
}
