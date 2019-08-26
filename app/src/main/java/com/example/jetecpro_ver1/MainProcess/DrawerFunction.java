package com.example.jetecpro_ver1.MainProcess;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.SQLite.DBHelper;
import com.example.jetecpro_ver1.Values.SendType;

import org.json.JSONArray;

public class DrawerFunction {

    AlertDialog.Builder mBuilder;
    Context context;
    View view;
    String DB_NAME  = SendType.DB_Name;
    String DB_TABLE = SendType.DB_TABLE;
    SQLiteDatabase mCustomDb;
    private JSONArray jsonArray;
    ListView SimpleListView;
    private SimpleAdapter simpleAdapter;

    public DrawerFunction(Context context ,View view,AlertDialog.Builder mBuilder){
        this.context = context;
        this.view    = view;
        this.mBuilder= mBuilder;
    }

    /**儲存資料*/
    public void SaveFunction(){
        final AlertDialog dialog = mBuilder.create();
        final Button btCreateData   = (Button) view.findViewById(R.id.buttonCreateData);
        final Button btDeleteData   = (Button) view.findViewById(R.id.deleteDataButton);
        final Button btClose        = (Button) view.findViewById(R.id.closeDia);
        final ListView lvDisplay    = (ListView) view.findViewById(R.id.listview_SQLDisplay);

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        DBHelper db = new DBHelper(context,DB_NAME,null,1);
        mCustomDb = db.getWritableDatabase();

        btCreateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**如果沒有資料表，就建立一個；如果有則選擇之*/
                Cursor cursor = mCustomDb.rawQuery(
                        "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'", null);

                if (cursor != null) {
                    if (cursor.getCount() == 0)
                        mCustomDb.execSQL("CREATE TABLE " + DB_TABLE + " ("
                                + "_id INTEGER PRIMARY KEY," + "name TEXT," + "Description TEXT);");
                    cursor.close();
                }


            }
        });

        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**匯入資料功能*/
    public void LoadFunction(){


    }

    /**資料下載功能*/
    public void DownLoadFunction(){

    }

    /**顯示圖表功能*/
    public void ChartFunction(){

    }

    /**修改密碼功能*/
    public void ModifyPSWFunction(){


    }

    /**紀錄*/
    public void Record(){

    }

    /**開始記錄*/
    public void StartRecord(){

    }

}
