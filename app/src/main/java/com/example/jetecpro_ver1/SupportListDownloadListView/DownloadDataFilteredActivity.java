package com.example.jetecpro_ver1.SupportListDownloadListView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.SQLite.DBHelper;
import com.example.jetecpro_ver1.Values.SendType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DownloadDataFilteredActivity extends Activity {
    ListView lv;
    String DB_NAME = SendType.DB_Name;
    String DB_TABLE = SendType.DB_TABLE + "GETRecord";
    SQLiteDatabase mCustomDb;
    private SimpleAdapter simpleAdapter;

    public static String mSelectType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_data_filtered);
        //設置ActionBar
        setActionBar();
        lv = findViewById(R.id.dataFiltered_listView);
        if (mSelectType.contains(trans(R.string.dataFilter_plzSelectDateAndTime))) {
            dateTimeRangeSelect();
        } else if (mSelectType.contains(trans(R.string.dataFilter_plzSelectId))) {
            idRangeSelect();
        } else if (mSelectType.contains(trans(R.string.Temperature))) {
            valueRangeSelect();
        } else if (mSelectType.contains(trans(R.string.Humidity))) {
            valueRangeSelect();
        }

    }

    /**
     * 設置ActionBar
     */
    private void setActionBar() {
        getActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.redGINSYU)));

        TextView textView = new TextView(this);
        textView.setText(R.string.dataFilter);
        textView.setTextSize(24);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        textView.setGravity(Gravity.CENTER);//文字置中
        textView.setTextColor(Color.WHITE);
        getActionBar().setHomeAsUpIndicator(R.drawable.ic_navigate_before_black_24dp);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(textView);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 設置ActionBar內活動
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 顯示所選擇的ID範圍
     */
    private void idRangeSelect() {
        Bundle bundle = getIntent().getExtras();
        String fir = bundle.getString("FirstId");
        String sec = bundle.getString("SecondId");
        DBHelper db = new DBHelper(getBaseContext(), DB_NAME, null, 1);
        mCustomDb = db.getWritableDatabase();
        Cursor cursor = mCustomDb.rawQuery(
                "SELECT * FROM " + DB_TABLE, null);
        String getJson = null;
        JSONArray jsonArray = null;
        try {
            while (cursor.moveToNext()) {
                getJson = cursor.getString(1);
            }
            jsonArray = new JSONArray(getJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        try {
            for (int i = Integer.parseInt(fir) - 1; i < Integer.parseInt(sec); i++) {
                HashMap<String, String> hashMap = new HashMap<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String firstData = trans2Double1(jsonObject.getString("First"));
                String secondData = trans2Double1(jsonObject.getString("Second"));
                String Date = jsonObject.getString("RecordDate");
                String Time = jsonObject.getString("RecordTime");
                hashMap.put("DateTime", Date + " " + Time);
                hashMap.put("id", id);
                hashMap.put("first", firstData + "°C");
                hashMap.put("second", secondData + "%");
                arrayList.add(hashMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] from = {"DateTime", "id", "first", "second"};
        int[] to = {R.id.listView_dateTime, R.id.listView_RecordId, R.id.listView_firstData, R.id.listView_SecondData};
        simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.activity_list_download_data_custom, from, to);
        lv.setAdapter(simpleAdapter);
    }

    /**
     * 顯示選擇的溫度值範圍
     */
    private void valueRangeSelect() {
        Bundle bundle = getIntent().getExtras();
        int mathMethod = bundle.getInt("Method");
        Double mathRange = bundle.getDouble("Value");

        DBHelper db = new DBHelper(getBaseContext(), DB_NAME, null, 1);
        mCustomDb = db.getWritableDatabase();
        Cursor cursor = mCustomDb.rawQuery(
                "SELECT * FROM " + DB_TABLE, null);
        String getJson = null;
        JSONArray jsonArray = null;
        try {
            while (cursor.moveToNext()) {
                getJson = cursor.getString(1);
            }
            jsonArray = new JSONArray(getJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap<String, String> hashMap = new HashMap<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String firstData = trans2Double1(jsonObject.getString("First"));
                String secondData = trans2Double1(jsonObject.getString("Second"));
                String Date = jsonObject.getString("RecordDate");
                String Time = jsonObject.getString("RecordTime");
                if (mSelectType.contains(trans(R.string.Temperature))) {
                    switch (mathMethod) {
                        case 0://大於
                            if (Double.valueOf(firstData) > mathRange) {
                                hashMap.put("DateTime", Date + " " + Time);
                                hashMap.put("id", id);
                                hashMap.put("first", firstData + "°C");
                                hashMap.put("second", secondData + "%");
                                arrayList.add(hashMap);
                            }
                            break;
                        case 1://小於
                            if (Double.valueOf(firstData) < mathRange) {
                                hashMap.put("DateTime", Date + " " + Time);
                                hashMap.put("id", id);
                                hashMap.put("first", firstData + "°C");
                                hashMap.put("second", secondData + "%");
                                arrayList.add(hashMap);
                            }
                            break;
                        case 2://等於
                            if (Double.valueOf(firstData).equals(mathRange)) {
                                hashMap.put("DateTime", Date + " " + Time);
                                hashMap.put("id", id);
                                hashMap.put("first", firstData + "°C");
                                hashMap.put("second", secondData + "%");
                                arrayList.add(hashMap);
                            }
                            break;
                    }
                } else if (mSelectType.contains(trans(R.string.Humidity))) {
                    switch (mathMethod) {
                        case 0://大於
                            if (Double.valueOf(secondData) > mathRange) {
                                hashMap.put("DateTime", Date + " " + Time);
                                hashMap.put("id", id);
                                hashMap.put("first", firstData + "°C");
                                hashMap.put("second", secondData + "%");
                                arrayList.add(hashMap);
                            }
                            break;
                        case 1://小於
                            if (Double.valueOf(secondData) < mathRange) {
                                hashMap.put("DateTime", Date + " " + Time);
                                hashMap.put("id", id);
                                hashMap.put("first", firstData + "°C");
                                hashMap.put("second", secondData + "%");
                                arrayList.add(hashMap);
                            }
                            break;
                        case 2://等於
                            if (Double.valueOf(secondData).equals(mathRange)) {
                                hashMap.put("DateTime", Date + " " + Time);
                                hashMap.put("id", id);
                                hashMap.put("first", firstData + "°C");
                                hashMap.put("second", secondData + "%");
                                arrayList.add(hashMap);
                            }
                            break;
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] from = {"DateTime", "id", "first", "second"};
        int[] to = {R.id.listView_dateTime, R.id.listView_RecordId, R.id.listView_firstData, R.id.listView_SecondData};
        simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.activity_list_download_data_custom, from, to);
        lv.setAdapter(simpleAdapter);
        if (arrayList.size() == 0) {
            Toast.makeText(getBaseContext(), R.string.sorry, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 顯示選擇的時間值範圍
     */
    private void dateTimeRangeSelect() {
        Bundle bundle = getIntent().getExtras();
        int mathMethod = bundle.getInt("dtMethod");
        String selectedDate = bundle.getString("getD");
        ;//自選擇日期範圍
        String selectedTime = bundle.getString("getT");
        ;//自選擇的時間範圍
//        Log.v("BT", selectedDate + ", " + selectedTime + ", " + mathMethod);
        Date dSelected = null;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            dSelected = ft.parse(selectedDate + " " + selectedTime + ":00");
//            Log.v("BT", dSelected.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            Log.v("BT", "boom" + e);
        }
        DBHelper db = new DBHelper(getBaseContext(), DB_NAME, null, 1);
        mCustomDb = db.getWritableDatabase();
        Cursor cursor = mCustomDb.rawQuery(
                "SELECT * FROM " + DB_TABLE, null);
        String getJson = null;
        JSONArray jsonArray = null;
        try {
            while (cursor.moveToNext()) {
                getJson = cursor.getString(1);
            }
            jsonArray = new JSONArray(getJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        Date recordDT;
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap<String, String> hashMap = new HashMap<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String firstData = trans2Double1(jsonObject.getString("First"));
                String secondData = trans2Double1(jsonObject.getString("Second"));
                String Date = jsonObject.getString("RecordDate");
                String Time = jsonObject.getString("RecordTime");
                recordDT = ft.parse(Date + " " + Time);
                switch (mathMethod) {

                    case 0:
                        if (dSelected.getTime() - recordDT.getTime()>=0) {
                            hashMap.put("DateTime", Date + " " + Time);
                            hashMap.put("id", id);
                            hashMap.put("first", firstData + "°C");
                            hashMap.put("second", secondData + "%");
                            arrayList.add(hashMap);
                        }

                        break;
                    case 1:
                        if (dSelected.getTime() - recordDT.getTime()<=0) {
                            hashMap.put("DateTime", Date + " " + Time);
                            hashMap.put("id", id);
                            hashMap.put("first", firstData + "°C");
                            hashMap.put("second", secondData + "%");
                            arrayList.add(hashMap);
                        }
                        break;
                    case 2:
//                        Log.v("BT", String.valueOf(dSelected.getTime()-recordDT.getTime()));
                        if (dSelected.getTime()-recordDT.getTime()>=-36*100000
                                &&dSelected.getTime()-recordDT.getTime()<=0) {
                            hashMap.put("DateTime", Date + " " + Time);
                            hashMap.put("id", id);
                            hashMap.put("first", firstData + "°C");
                            hashMap.put("second", secondData + "%");
                            arrayList.add(hashMap);
                        }
                        break;

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
            Log.v("BT", String.valueOf(e));
        }
        String[] from = {"DateTime", "id", "first", "second"};
        int[] to = {R.id.listView_dateTime, R.id.listView_RecordId, R.id.listView_firstData, R.id.listView_SecondData};
        simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.activity_list_download_data_custom, from, to);
        lv.setAdapter(simpleAdapter);
        if (arrayList.size() == 0) {
            Toast.makeText(getBaseContext(), R.string.sorry, Toast.LENGTH_SHORT).show();
        }

    }

    /**顯示選擇的XX值範圍*/


    /**
     * 轉數字
     */
    private String trans2Double1(String input) {
        String s1 = input.substring(0, 5);
        float d1 = Float.parseFloat(s1);
        float out;
        switch (input.substring(5)) {
            case "1":
                out = d1 * 0.1f;
                break;
            case "2":
                out = d1 * 0.01f;
                break;
            default:
                out = d1;
                break;

        }
//        Log.v("transD", String.valueOf(output));
        return String.valueOf(out);
    }

    /**
     * 簡化字串轉換
     */
    private String trans(int name) {
        String str = this.getResources().getString(name);
        return str;
    }
}
