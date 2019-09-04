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

import java.util.ArrayList;
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
            i++;
            Log.v("BC", "序號: " + i + " ,資料: " + data.substring(0, 12));
            getDataSave(data.substring(0, 12));

        } else if (data != null && data.substring(0, 1).contains("-")) {
            getDataSave(data.substring(0, 12));
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

        DBHelper db = new DBHelper(context,DB_NAME,null,1);
        mCustomDb = db.getWritableDatabase();
        Cursor cursor = mCustomDb.rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'", null);
        if (cursor != null) {
            if (cursor.getCount() == 0)
                mCustomDb.execSQL("CREATE TABLE " + DB_TABLE + " ("
                        + "_id INTEGER PRIMARY KEY," + "First TEXT," + "SecondData TEXT);");
            cursor.close();
        }
        ContentValues newRow = new ContentValues();
        newRow.put("First",mFirstData);
        newRow.put("SecondData",mSecondData);
        mCustomDb.insert(DB_TABLE,null,newRow);

    }


    public void deleteAllData(){

        DBHelper db = new DBHelper(context,DB_NAME,null,1);
        mCustomDb = db.getWritableDatabase();
        Cursor cursor = mCustomDb.rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'", null);
        if (cursor != null) {
            if (cursor.getCount() == 0)
                mCustomDb.execSQL("CREATE TABLE " + DB_TABLE + " ("
                        + "_id INTEGER PRIMARY KEY," + "First TEXT," + "SecondData TEXT);");
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
            arrayList.add(hashMap);
//            Log.v("BT",cursor.getString(0)+", "+cursor.getString(1)+", "+cursor.getString(2));
        }
        String json = new Gson().toJson(arrayList);
//        Log.v("BT",json);
        //先暫時不輸入JSON到資料庫，日後視情況使用
        //嘗試輸出
       /* try {
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


