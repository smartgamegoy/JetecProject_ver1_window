package com.example.jetecpro_ver1.MainProcess;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.SQLite.DBHelper;
import com.example.jetecpro_ver1.SendData.SortData;
import com.example.jetecpro_ver1.Values.SendType;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DrawerFunction {

    AlertDialog.Builder mBuilder;
    Context              context;
    View                 view;

    String               DB_NAME  = SendType.DB_Name;
    String               DB_TABLE = SendType.DB_TABLE;
    SQLiteDatabase       mCustomDb;
    private JSONArray    jsonArray;
    ListView             SimpleListView;
    private int          GET_ITEM_POSITION = 100;
    private SimpleAdapter simpleAdapter;

    public DrawerFunction(Context context ,View view,AlertDialog.Builder mBuilder){
        this.context        = context;
        this.view           = view;
        this.mBuilder       = mBuilder;

    }

    /**儲存資料*/
    public void SaveFunction(){
        final AlertDialog dialog = mBuilder.create();
        final Button btCreateData   = (Button)  view.findViewById(R.id.buttonCreateData);
        final Button btModifyData   = (Button)  view.findViewById(R.id.modifyButton);
        final Button btDeleteData   = (Button)  view.findViewById(R.id.deleteDataButton);
        final Button btClose        = (Button)  view.findViewById(R.id.closeDia);
        final EditText edCreate     = (EditText)view.findViewById(R.id.edText);
        final ListView lvDisplay    = (ListView)view.findViewById(R.id.listview_SQLDisplay);

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        DBHelper db = new DBHelper(context,DB_NAME,null,1);
        mCustomDb = db.getWritableDatabase();

        /**如果沒有資料表，就建立一個；如果有則選擇之*/
        searchTABLE();
        /**獲取ListView*/
        setListView(lvDisplay);


        lvDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lvDisplay.setSelector(R.color.Sakura);
                final Cursor data = mCustomDb.query(true, DB_TABLE, new String[]{"_id", "name", "Description"},
                        null, null, null, null, null, null);
                final ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
                while (data.moveToNext()) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("name", data.getString(1));
                    hashMap.put("id", data.getString(0));
                    arrayList.add(hashMap);
                }
                String selected  = arrayList.get(position).toString();
                String str = selected.substring(selected.indexOf(", id="),selected.indexOf("}"));
                String strGet = str.substring(5);
                GET_ITEM_POSITION = Integer.parseInt(strGet);

            }
        });



        btCreateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edCreate.getText().toString().length() > 0){
                    SortData sortData   = new SortData(SendType.DeviceType,context);
                    String[] nameItems  = sortData.getSQLiteTrs();
                    String[] valueItems = sortData.getSQLiteData();




                    final ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
                    for (int i =0;i<nameItems.length ;i++){
                        HashMap<String, String> data = new HashMap<>();
                        data.put("id",nameItems[i]);
                        data.put("value",valueItems[i]);
                        arrayList.add(data);
                    }
                    String json = new Gson().toJson(arrayList);//我操GSON真他媽好用.....
                    ContentValues newRow = new ContentValues();
                    newRow.put("name",edCreate.getText().toString().trim());
                    newRow.put("Description",json);
                    mCustomDb.insert(DB_TABLE,null,newRow);
                    Toast.makeText(context,R.string.createSuccess,Toast.LENGTH_SHORT).show();
                    edCreate.setText("");
                    setListView(lvDisplay);
                }else{
                    Toast.makeText(context,R.string.plzGetName,Toast.LENGTH_SHORT).show();
                }

            }
        });//createData

        btModifyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GET_ITEM_POSITION == 100){
                    Toast.makeText(context,R.string.plzSelectModifyItem,Toast.LENGTH_SHORT).show();
                }else {
                    DataDisplayActivity displayActivity = new DataDisplayActivity();
                    displayActivity.getModifySQLiteFunctionViewFromDrawerFunction(mCustomDb,DB_TABLE
                            ,GET_ITEM_POSITION,lvDisplay,view,context,mBuilder);
                }
            }
        });//modifyData


        btClose.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
                { dialog.dismiss();}});

        btDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GET_ITEM_POSITION == 100){
                    Toast.makeText(context,R.string.plzSelectDeleteItem,Toast.LENGTH_SHORT).show();
                }else{
                    mCustomDb.delete(DB_TABLE, "_id=" + GET_ITEM_POSITION, null);
                    setListView(lvDisplay);
                    Toast.makeText(context,R.string.deleteSuccess,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**匯入資料功能*/
    public void LoadFunction(){
        final AlertDialog dialog = mBuilder.create();
        final Button btLoadData  = view.findViewById(R.id.LoadButton);
        final Button btcloseDia  = view.findViewById(R.id.closeDiaL);
        final ListView listView  = view.findViewById(R.id.listview_Load);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        DBHelper db = new DBHelper(context,DB_NAME,null,1);
        mCustomDb = db.getWritableDatabase();

        /**如果沒有資料表，就建立一個；如果有則選擇之*/
        searchTABLE();
        setListView(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.setSelector(R.color.Sakura);
                final Cursor data = mCustomDb.query(true, DB_TABLE, new String[]{"_id", "name", "Description"},
                        null, null, null, null, null, null);
                final ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
                while (data.moveToNext()) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("name", data.getString(1));
                    hashMap.put("id", data.getString(0));
                    arrayList.add(hashMap);
                }
                String selected  = arrayList.get(position).toString();
                String str = selected.substring(selected.indexOf(", id="),selected.indexOf("}"));
                String strGet = str.substring(5);
                GET_ITEM_POSITION = Integer.parseInt(strGet);
            }
        });

        btLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GET_ITEM_POSITION != 100){
                    DataDisplayActivity displayActivity = new DataDisplayActivity();
                    displayActivity.getLoadSQLiteFunctionViewFromDrawerFunction(mCustomDb,DB_TABLE,
                            GET_ITEM_POSITION,listView,view,context,mBuilder);
                }else{
                    Toast.makeText(context,R.string.plzSelectModifyItem,Toast.LENGTH_SHORT).show();
                }
            }
        });

        btcloseDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { dialog.dismiss();}});

    }



    private void searchTABLE(){
        Cursor cursor = mCustomDb.rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + DB_TABLE + "'", null);
        if (cursor != null) {
            if (cursor.getCount() == 0)
                mCustomDb.execSQL("CREATE TABLE " + DB_TABLE + " ("
                        + "_id INTEGER PRIMARY KEY," + "name TEXT," + "Description TEXT);");
            cursor.close();
        }
    }
    public  void setListView(ListView lvDisplay){
        DBHelper db = new DBHelper(context,DB_NAME,null,1);
        mCustomDb = db.getWritableDatabase();
        final Cursor data = mCustomDb.query(true, DB_TABLE, new String[]{"_id", "name", "Description"},
                null, null, null, null, null, null);
        final ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        while (data.moveToNext()) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", data.getString(1));
            hashMap.put("id", data.getString(0));
            arrayList.add(hashMap);
        }

        final String[] from = {"name", "id"};
        int[] to = {android.R.id.text1};
        simpleAdapter =
                new SimpleAdapter(context, arrayList, android.R.layout.simple_list_item_1, from, to);
        lvDisplay.setAdapter(simpleAdapter);
        GET_ITEM_POSITION =100;

    }

}
