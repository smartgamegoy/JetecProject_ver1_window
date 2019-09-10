package com.example.jetecpro_ver1.RecordData;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jetecpro_ver1.MainProcess.DataDisplayActivity;
import com.example.jetecpro_ver1.SQLite.DBHelper;
import com.example.jetecpro_ver1.Values.SendType;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class GetRecord {
    Context context;
    String data;
    public static int i = 0;
    String DB_NAME = SendType.DB_Name;
    String DB_TABLE = SendType.DB_TABLE + "GETRecord";
    SQLiteDatabase mCustomDb;


    public GetRecord(String data, Context context) {
        this.data = data;
        this.context = context;
    }

    public int getRecord() {
        if (data != null && data.substring(0, 1).contains("+")) {
            if (i == 0){
                getRecordDateTime();//取得最新資料的時間
            }
            i++;
            Log.v("BC", "序號: " + i + " ,資料: " + data.substring(0, 12));
            getDataSave(data.substring(0, 12));

        } else if (data != null && data.substring(0, 1).contains("-")) {
            getDataSave(data.substring(0, 12));
            if (i == 0){
                getRecordDateTime();//取得最新資料的時間
            }
            i++;
            Log.v("BC", "序號: " + i + " ,資料: " + data.substring(0, 12));

        } else if (data != null && data.contains("OVER")) {
            Log.v("BC", "OVER");

            i = -1;
        }

        return i;
    }

    public void getDataSave(String mData) {
        //把未分類資料通通丟進SQLite
        String mFirstData = mData.substring(0, 6);
        String mSecondData = mData.substring(6, 12);
        if (i == 0){
            getRecordDateTime();//取得最新資料的時間
        }



        DBHelper db = new DBHelper(context,DB_NAME,null,1);
        mCustomDb = db.getWritableDatabase();
        Cursor cursor = mCustomDb.rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'", null);
        if (cursor != null) {
            if (cursor.getCount() == 0)
                mCustomDb.execSQL("CREATE TABLE " + DB_TABLE + " ("
                        + "_id INTEGER PRIMARY KEY," + "First TEXT,"
                        + "SecondData TEXT" + "RecordDate TEXT" + "RecordTime TEXT);");
            cursor.close();
        }
        ContentValues newRow = new ContentValues();
        newRow.put("First",mFirstData);
        newRow.put("SecondData",mSecondData);
        newRow.put("RecordDate",SendType.arrayDate);
        newRow.put("RecordTime",SendType.arrayTime);
        mCustomDb.insert(DB_TABLE,null,newRow);
        writeTime();

    }

    /**發出第一筆資料的時間給資料表*/
    private void getRecordDateTime()  {
        String newTime;
        String hour = SendType.mTIME2COUNT.substring(0,2);
        String min  = SendType.mTIME2COUNT.substring(2,4);
        String sec  = SendType.mTIME2COUNT.substring(4,6);
//        Log.v("TimeLog", "mTIME輸入樣式(第一筆)"+SendType.mTIME);
        //三個輸出182027
        String year = "20"+SendType.mDATE.substring(0,2);
        String month= SendType.mDATE.substring(2,4);
        String day  = SendType.mDATE.substring(4,6);
//        Log.v("TimeLog", "mDATE輸入樣式(第一筆)"+SendType.mDATE);
        //三個輸出20190903
//        Log.v("TimeLog","日期:"+year+month+day+" ,時間:"+hour+min+sec);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        String string = year+"-"+month+"-"+day+","+hour+":"+min+":"+sec;
        Date dt = null;
        try {
            dt = sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        int t = Integer.parseInt(SendType.mCOUNT)*Integer.parseInt(SendType.INTER2SQL);
        rightNow.add(Calendar.SECOND,t);
        Date dt1 = rightNow.getTime();
        newTime = sdf.format(dt1);
//        Log.v("TimeLog","那堆資料的最新時間為(第一筆): "+newTime);
        SendType.arrayDate = newTime.substring(0,newTime.indexOf(","));
        SendType.arrayTime = newTime.substring(11);
//        Log.v("TimeLog","輸出給資料表的日期(第一筆): "+SendType.arrayDate+" ,"+"輸出給資料表的時間(第一筆): "+SendType.arrayTime);
        SendType.mTIME2COUNT = SendType.arrayTime.replaceAll(":","");
        SendType.mDATE = SendType.arrayDate.replaceAll("-","").substring(2);
//        Log.v("TimeLog","輸出回mDate(第一筆): "+SendType.mDATE+" ,"+"輸出回mTime(第一筆): "+SendType.mTIME+"\n換行");



    }

    private void writeTime(){
        String newTime;
        String hour = SendType.mTIME2COUNT.substring(0,2);
        String min  = SendType.mTIME2COUNT.substring(2,4);
        String sec  = SendType.mTIME2COUNT.substring(4,6);
//        Log.v("TimeLog", "mTIME輸入樣式"+SendType.mTIME);
        //三個輸出182027
        String year = "20"+SendType.mDATE.substring(0,2);
        String month= SendType.mDATE.substring(2,4);
        String day  = SendType.mDATE.substring(4,6);
//        Log.v("TimeLog", "mDATE輸入樣式"+SendType.mDATE);
        //三個輸出20190903
//        Log.v("TimeLog","日期:"+year+month+day+" ,時間:"+hour+min+sec);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        String string = year+"-"+month+"-"+day+","+hour+":"+min+":"+sec;
        Date dt = null;
        try {
            dt = sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        int t =Integer.parseInt(SendType.INTER2SQL);
        rightNow.add(Calendar.SECOND,-t);
        Date dt1 = rightNow.getTime();
        newTime = sdf.format(dt1);
//        Log.v("TimeLog","那堆資料的最新時間為: "+newTime);
        SendType.arrayDate = newTime.substring(0,newTime.indexOf(","));
        SendType.arrayTime = newTime.substring(11);
//        Log.v("TimeLog","輸出給資料表的日期: "+SendType.arrayDate+" ,"+"輸出給資料表的時間: "+SendType.arrayTime);
        SendType.mTIME2COUNT = SendType.arrayTime.replaceAll(":","");
        SendType.mDATE = SendType.arrayDate.replaceAll("-","").substring(2);
//        Log.v("TimeLog","輸出回mDate: "+SendType.mDATE+" ,"+"輸出回mTime: "+SendType.mTIME+"\n換行");
    }


    public void deleteAllData(){

        DBHelper db = new DBHelper(context,DB_NAME,null,1);
        mCustomDb = db.getWritableDatabase();
        Cursor cursor = mCustomDb.rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'", null);
        if (cursor != null) {
            if (cursor.getCount() == 0)
                mCustomDb.execSQL("CREATE TABLE " + DB_TABLE + " ("
                        + "_id INTEGER PRIMARY KEY," + "First TEXT,"
                        + "SecondData TEXT," + "RecordDate TEXT," + "RecordTime TEXT);");
            cursor.close();
        }
        mCustomDb.delete(DB_TABLE,null,null);
    }
    public void takeOutSQLite(){
        DBHelper db = new DBHelper(context,DB_NAME,null,1);
        mCustomDb = db.getWritableDatabase();
        Cursor cursor = mCustomDb.rawQuery(
                "SELECT * FROM " + DB_TABLE , null);
        final ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        while (cursor.moveToNext()){
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("id",cursor.getString(0));
            hashMap.put("First",cursor.getString(1));
            hashMap.put("Second",cursor.getString(2));
            hashMap.put("RecordDate",cursor.getString(3));
            hashMap.put("RecordTime",cursor.getString(4));
            arrayList.add(hashMap);
        }

        {//取出紀錄好的資料在輸出成json上傳回SQLite
            String json = new Gson().toJson(arrayList);
//            Log.v("GETJSON",json);
            deleteAllData();
            ContentValues newRow = new ContentValues();
            newRow.put("First",json);
            mCustomDb.insert(DB_TABLE,null,newRow);
        }


//        嘗試輸出
        /*try {
            JSONArray array = new JSONArray(json);
            for (int l = 0;l<array.length();l++){
                JSONObject jsonObject = array.getJSONObject(l);
                String id = jsonObject.getString("id");
                String F = jsonObject.getString("First");
                String S = jsonObject.getString("Second");
                Log.v("BT","id:"+id+"T:"+F+"H:"+S);
            }
        }catch (Exception e){

        }*/
    }


}


