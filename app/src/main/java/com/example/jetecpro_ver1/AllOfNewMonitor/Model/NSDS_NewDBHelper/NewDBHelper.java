package com.example.jetecpro_ver1.AllOfNewMonitor.Model.NSDS_NewDBHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jetecpro_ver1.SQLite.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class NewDBHelper extends SQLiteOpenHelper {
    private final static int DBVersion = 1; //<-- 版本
    private final static String DBName = "NewRemote.db";  //<-- db name
    private final static String TableName = "newRemote";
    public NewDBHelper(Context context) {
        super(context,DBName , null, DBVersion);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL = "CREATE TABLE IF NOT EXISTS " + TableName + "( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "DeviceType TEXT, " +
                "Name TEXT,"+
                "SettingJSON TEXT"+
                ");";
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL = "DROP TABLE " + TableName;
        db.execSQL(SQL);
    }

    public void addData(String deviceType,String name,String json){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("DeviceType",deviceType);
        values.put("Name",name);
        values.put("SettingJSON",json);
        db.insert(TableName,null,values);
    }

    public ArrayList<HashMap<String,String>> displayAllData(){
        Cursor c =getReadableDatabase().rawQuery(" SELECT * FROM "+TableName,null);
        ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
        while (c.moveToNext()){
            String deviceType = c.getString(1);
            String name = c.getString(2);
            String Json = c.getString(3);
            HashMap<String ,String> hashMap = new HashMap<>();
            hashMap.put("DeviceType",deviceType);
            hashMap.put("Name",name);
            hashMap.put("Json",Json);
            arrayList.add(hashMap);
        }

        return arrayList;

    }
    public ArrayList<HashMap<String,String>> searchByType(String getDeviceType){
        Cursor c =getReadableDatabase().rawQuery(" SELECT * FROM "+TableName
                +" WHERE DeviceType ="+"'"+getDeviceType+"'"  ,null);
        ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
        while (c.moveToNext()){
            String deviceType = c.getString(1);
            String name = c.getString(2);
            String Json = c.getString(3);
            HashMap<String ,String> hashMap = new HashMap<>();
            hashMap.put("DeviceType",deviceType);
            hashMap.put("Name",name);
            hashMap.put("Json",Json);
            arrayList.add(hashMap);
        }

        return arrayList;

    }

    public void deleteData(String name){

    }

    public void openDB(Activity activity){
        new NewDBHelper(activity);
    }

    public void closeDB(Activity activity){
        new NewDBHelper(activity).close();
    }




}
