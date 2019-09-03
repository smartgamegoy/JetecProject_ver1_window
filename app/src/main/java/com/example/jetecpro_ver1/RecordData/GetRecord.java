package com.example.jetecpro_ver1.RecordData;

import android.app.AlertDialog;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jetecpro_ver1.MainProcess.DataDisplayActivity;

public class GetRecord {
    String data;
    public static int i = 0;
    public GetRecord(String data){
        this.data = data;
    }

    public  int  getRecord(){
        if (data != null&&data.substring(0,1).contains("+")){
            i++;
            Log.v("BC","序號: "+i+" ,資料: "+data.substring(0,12));
            getDataSave();

        }else if (data!= null&&data.substring(0,1).contains("-")){
            Log.v("BC","OVER");
            i++;
            Log.v("BC","序號: "+i+" ,資料: "+data.substring(0,12));
            getDataSave();

        }else if (data!= null&& data.contains("OVER")){
            Log.v("BC","OVER");
            i=-1;

        }
        return i;
    }
    public void getDataSave(){
        if (data != null&&data.substring(0,1).contains("+")){


        }
    }






}
