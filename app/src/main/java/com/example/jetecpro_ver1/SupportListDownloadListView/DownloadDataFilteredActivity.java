package com.example.jetecpro_ver1.SupportListDownloadListView;

import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
        } else {
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

        switch (SendType.row){

            case '1':

                break;
            case '2':
                IdSelectRow2(fir, sec, jsonArray, arrayList);
                break;
            case '3':
                IdSelectRow3(fir, sec, jsonArray, arrayList);
                break;
        }

    }
    private void IdSelectRow2(String fir, String sec, JSONArray jsonArray, ArrayList<HashMap<String, String>> arrayList) {
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
                hashMap.put("first", firstData+unit(SendType.FirstWord,1));
                hashMap.put("second", secondData+unit(SendType.SecondWord,2));
                hashMap.put("firstT",Lab(SendType.FirstWord,1));
                hashMap.put("secondT",Lab(SendType.SecondWord,2));
                arrayList.add(hashMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] from = {"firstT","secondT","DateTime", "id", "first", "second"};
        int[] to = {R.id.listView_FirstDataTitle,R.id.listView_SecendDataTitle
                ,R.id.listView_dateTime, R.id.listView_RecordId, R.id.listView_firstData, R.id.listView_SecendData};
        simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.activity_list_download_data_custom, from, to);
        lv.setAdapter(simpleAdapter);
    }
    private void IdSelectRow3(String fir, String sec, JSONArray jsonArray, ArrayList<HashMap<String, String>> arrayList) {
        try {
            for (int i = Integer.parseInt(fir) - 1; i < Integer.parseInt(sec); i++) {
                HashMap<String, String> hashMap = new HashMap<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String firstData = trans2Double1(jsonObject.getString("First"));
                String secondData = trans2Double1(jsonObject.getString("Second"));
                String thirdData = trans2Double1(jsonObject.getString("Third"));
                String Date = jsonObject.getString("RecordDate");
                String Time = jsonObject.getString("RecordTime");
                hashMap.put("DateTime", Date + " " + Time);
                hashMap.put("id", id);
                hashMap.put("first", firstData+unit(SendType.FirstWord,1));
                hashMap.put("second", secondData+unit(SendType.SecondWord,2));
                hashMap.put("third", thirdData+unit(SendType.ThirdWord,3));
                hashMap.put("firstT",Lab(SendType.FirstWord,1));
                hashMap.put("secondT",Lab(SendType.SecondWord,2));
                hashMap.put("thirdT",Lab(SendType.ThirdWord,3));
                arrayList.add(hashMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] from = {"firstT","secondT","thirdT","DateTime", "id", "first", "second", "third"};
        int[] to = {R.id.listView_FirstDataTitle,R.id.listView_SecendDataTitle,R.id.listView_ThirdDataTitle
                ,R.id.listView_dateTime, R.id.listView_RecordId, R.id.listView_firstData, R.id.listView_SecendData,R.id.listView_ThirdData};
        simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.activity_list_download_data_custom, from, to);
        lv.setAdapter(simpleAdapter);
    }


    /**
     * 顯示選擇的值範圍
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

        switch (SendType.row){

            case '1':

                break;
            case '2':
                valueSelectRow2(mathMethod, mathRange, jsonArray, arrayList);
                break;
            case '3':
                valueSelectRow3(mathMethod, mathRange, jsonArray, arrayList);
                break;
        }



    }
    private void valueSelectRow2(int mathMethod, Double mathRange, JSONArray jsonArray, ArrayList<HashMap<String, String>> arrayList) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap<String, String> hashMap = new HashMap<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String firstData = trans2Double1(jsonObject.getString("First"));
                String secondData = trans2Double1(jsonObject.getString("Second"));
                String Date = jsonObject.getString("RecordDate");
                String Time = jsonObject.getString("RecordTime");
                    switch (mathMethod) {
                        case 0://大於
                            if (Double.valueOf(firstData) > mathRange) {
                                hashMap.put("DateTime", Date + " " + Time);
                                hashMap.put("id", id);
                                hashMap.put("first", firstData+unit(SendType.FirstWord,1));
                                hashMap.put("second", secondData+unit(SendType.FirstWord,2));
                                hashMap.put("firstT", Lab(SendType.FirstWord,1));
                                hashMap.put("secondT", Lab(SendType.SecondWord,2));
                                arrayList.add(hashMap);
                            }
                            break;
                        case 1://小於
                            if (Double.valueOf(firstData) < mathRange) {
                                hashMap.put("DateTime", Date + " " + Time);
                                hashMap.put("id", id);
                                hashMap.put("first", firstData+unit(SendType.FirstWord,1));
                                hashMap.put("second", secondData+unit(SendType.FirstWord,2));
                                hashMap.put("firstT", Lab(SendType.FirstWord,1));
                                hashMap.put("secondT", Lab(SendType.SecondWord,2));
                                arrayList.add(hashMap);
                            }
                            break;
                        case 2://等於
                            if (Double.valueOf(firstData).equals(mathRange)) {
                                hashMap.put("DateTime", Date + " " + Time);
                                hashMap.put("id", id);
                                hashMap.put("first", firstData+unit(SendType.FirstWord,1));
                                hashMap.put("second", secondData+unit(SendType.FirstWord,2));
                                hashMap.put("firstT", Lab(SendType.FirstWord,1));
                                hashMap.put("secondT", Lab(SendType.SecondWord,2));
                                arrayList.add(hashMap);
                            }
                            break;
                    }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] from = {"firstT","secondT","DateTime", "id", "first", "second"};
        int[] to = {R.id.listView_FirstDataTitle,R.id.listView_SecendDataTitle
                ,R.id.listView_dateTime, R.id.listView_RecordId, R.id.listView_firstData, R.id.listView_SecendData};
        simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.activity_list_download_data_custom, from, to);
        lv.setAdapter(simpleAdapter);
        if (arrayList.size() == 0) {
            Toast.makeText(getBaseContext(), R.string.sorry, Toast.LENGTH_SHORT).show();
        }
    }
    private void valueSelectRow3(int mathMethod, Double mathRange, JSONArray jsonArray, ArrayList<HashMap<String, String>> arrayList) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap<String, String> hashMap = new HashMap<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String firstData = trans2Double1(jsonObject.getString("First"));
                String secondData = trans2Double1(jsonObject.getString("Second"));
                String thirdData = trans2Double1(jsonObject.getString("Third"));
                String Date = jsonObject.getString("RecordDate");
                String Time = jsonObject.getString("RecordTime");
                switch (mathMethod) {
                    case 0://大於
                        if (Double.valueOf(firstData) > mathRange) {
                            hashMap.put("DateTime", Date + " " + Time);
                            hashMap.put("id", id);
                            hashMap.put("first", firstData+unit(SendType.FirstWord,1));
                            hashMap.put("second", secondData+unit(SendType.FirstWord,2));
                            hashMap.put("third", thirdData+unit(SendType.FirstWord,3));
                            hashMap.put("firstT", Lab(SendType.FirstWord,1));
                            hashMap.put("secondT", Lab(SendType.SecondWord,2));
                            hashMap.put("thirdT", Lab(SendType.SecondWord,3));
                            arrayList.add(hashMap);
                        }
                        break;
                    case 1://小於
                        if (Double.valueOf(firstData) < mathRange) {
                            hashMap.put("DateTime", Date + " " + Time);
                            hashMap.put("id", id);
                            hashMap.put("first", firstData+unit(SendType.FirstWord,1));
                            hashMap.put("second", secondData+unit(SendType.FirstWord,2));
                            hashMap.put("third", thirdData+unit(SendType.FirstWord,3));
                            hashMap.put("firstT", Lab(SendType.FirstWord,1));
                            hashMap.put("secondT", Lab(SendType.SecondWord,2));
                            hashMap.put("thirdT", Lab(SendType.SecondWord,3));
                            arrayList.add(hashMap);
                        }
                        break;
                    case 2://等於
                        if (Double.valueOf(firstData).equals(mathRange)) {
                            hashMap.put("DateTime", Date + " " + Time);
                            hashMap.put("id", id);
                            hashMap.put("first", firstData+unit(SendType.FirstWord,1));
                            hashMap.put("second", secondData+unit(SendType.FirstWord,2));
                            hashMap.put("third", thirdData+unit(SendType.FirstWord,3));
                            hashMap.put("firstT", Lab(SendType.FirstWord,1));
                            hashMap.put("secondT", Lab(SendType.SecondWord,2));
                            hashMap.put("thirdT", Lab(SendType.SecondWord,3));
                            arrayList.add(hashMap);
                        }
                        break;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] from = {"firstT","secondT","thirdT","DateTime", "id", "first", "second","third"};
        int[] to = {R.id.listView_FirstDataTitle,R.id.listView_SecendDataTitle,R.id.listView_ThirdDataTitle
                ,R.id.listView_dateTime, R.id.listView_RecordId, R.id.listView_firstData, R.id.listView_SecendData,R.id.listView_ThirdData};
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

        switch (SendType.row){

            case '1':

                break;
            case '2':
                TimeDateSelectRow2(mathMethod, dSelected, ft, jsonArray, arrayList);
                break;
            case '3':
                TimeDateSelectRow3(mathMethod, dSelected, ft, jsonArray, arrayList);
                break;
        }


    }
    private void TimeDateSelectRow2(int mathMethod, Date dSelected, SimpleDateFormat ft, JSONArray jsonArray, ArrayList<HashMap<String, String>> arrayList) {
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
                            hashMap.put("first", firstData+unit(SendType.FirstWord,1));
                            hashMap.put("second", secondData+unit(SendType.FirstWord,2));
                            hashMap.put("firstT", Lab(SendType.FirstWord,1));
                            hashMap.put("secondT", Lab(SendType.SecondWord,2));
                            arrayList.add(hashMap);
                        }

                        break;
                    case 1:
                        if (dSelected.getTime() - recordDT.getTime()<=0) {
                            hashMap.put("DateTime", Date + " " + Time);
                            hashMap.put("id", id);
                            hashMap.put("first", firstData+unit(SendType.FirstWord,1));
                            hashMap.put("second", secondData+unit(SendType.FirstWord,2));
                            hashMap.put("firstT", Lab(SendType.FirstWord,1));
                            hashMap.put("secondT", Lab(SendType.SecondWord,2));
                            arrayList.add(hashMap);
                        }
                        break;
                    case 2:
//                        Log.v("BT", String.valueOf(dSelected.getTime()-recordDT.getTime()));
                        if (dSelected.getTime()-recordDT.getTime()>=-36*100000
                                &&dSelected.getTime()-recordDT.getTime()<=0) {
                            hashMap.put("DateTime", Date + " " + Time);
                            hashMap.put("id", id);
                            hashMap.put("first", firstData+unit(SendType.FirstWord,1));
                            hashMap.put("second", secondData+unit(SendType.FirstWord,2));
                            hashMap.put("firstT", Lab(SendType.FirstWord,1));
                            hashMap.put("secondT", Lab(SendType.SecondWord,2));
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
        String[] from = {"firstT","secondT","DateTime", "id", "first", "second"};
        int[] to = {R.id.listView_FirstDataTitle,R.id.listView_SecendDataTitle
                ,R.id.listView_dateTime, R.id.listView_RecordId, R.id.listView_firstData, R.id.listView_SecendData};
        simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.activity_list_download_data_custom, from, to);
        lv.setAdapter(simpleAdapter);
        if (arrayList.size() == 0) {
            Toast.makeText(getBaseContext(), R.string.sorry, Toast.LENGTH_SHORT).show();
        }
    }
    private void TimeDateSelectRow3(int mathMethod, Date dSelected, SimpleDateFormat ft, JSONArray jsonArray, ArrayList<HashMap<String, String>> arrayList) {
        Date recordDT;
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap<String, String> hashMap = new HashMap<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String firstData = trans2Double1(jsonObject.getString("First"));
                String secondData = trans2Double1(jsonObject.getString("Second"));
                String thirdData = trans2Double1(jsonObject.getString("Third"));
                String Date = jsonObject.getString("RecordDate");
                String Time = jsonObject.getString("RecordTime");
                recordDT = ft.parse(Date + " " + Time);
                switch (mathMethod) {

                    case 0:
                        if (dSelected.getTime() - recordDT.getTime()>=0) {
                            hashMap.put("DateTime", Date + " " + Time);
                            hashMap.put("id", id);
                            hashMap.put("first", firstData+unit(SendType.FirstWord,1));
                            hashMap.put("second", secondData+unit(SendType.FirstWord,2));
                            hashMap.put("third", thirdData+unit(SendType.FirstWord,3));
                            hashMap.put("firstT", Lab(SendType.FirstWord,1));
                            hashMap.put("secondT", Lab(SendType.SecondWord,2));
                            hashMap.put("thirdT", Lab(SendType.SecondWord,3));
                            arrayList.add(hashMap);
                        }

                        break;
                    case 1:
                        if (dSelected.getTime() - recordDT.getTime()<=0) {
                            hashMap.put("DateTime", Date + " " + Time);
                            hashMap.put("id", id);
                            hashMap.put("first", firstData+unit(SendType.FirstWord,1));
                            hashMap.put("second", secondData+unit(SendType.FirstWord,2));
                            hashMap.put("third", thirdData+unit(SendType.FirstWord,3));
                            hashMap.put("firstT", Lab(SendType.FirstWord,1));
                            hashMap.put("secondT", Lab(SendType.SecondWord,2));
                            hashMap.put("thirdT", Lab(SendType.SecondWord,3));
                            arrayList.add(hashMap);
                        }
                        break;
                    case 2:
//                        Log.v("BT", String.valueOf(dSelected.getTime()-recordDT.getTime()));
                        if (dSelected.getTime()-recordDT.getTime()>=-36*100000
                                &&dSelected.getTime()-recordDT.getTime()<=0) {
                            hashMap.put("DateTime", Date + " " + Time);
                            hashMap.put("id", id);
                            hashMap.put("first", firstData+unit(SendType.FirstWord,1));
                            hashMap.put("second", secondData+unit(SendType.FirstWord,2));
                            hashMap.put("third", thirdData+unit(SendType.FirstWord,3));
                            hashMap.put("firstT", Lab(SendType.FirstWord,1));
                            hashMap.put("secondT", Lab(SendType.SecondWord,2));
                            hashMap.put("thirdT", Lab(SendType.SecondWord,3));
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
        String[] from = {"firstT","secondT","thirdT","DateTime", "id", "first", "second","third"};
        int[] to = {R.id.listView_FirstDataTitle,R.id.listView_SecendDataTitle,R.id.listView_ThirdDataTitle
                ,R.id.listView_dateTime, R.id.listView_RecordId, R.id.listView_firstData, R.id.listView_SecendData,R.id.listView_ThirdData};
        simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.activity_list_download_data_custom, from, to);
        lv.setAdapter(simpleAdapter);
        if (arrayList.size() == 0) {
            Toast.makeText(getBaseContext(), R.string.sorry, Toast.LENGTH_SHORT).show();
        }
    }

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

    /**揀選標籤*/
    private String Lab(char name,int x) {
        switch (name){
            case 'T':

                return "溫度";

            case 'H':
                return "濕度";

            case 'C':
            case 'D':
            case 'E':
                return "二氧化碳";

            case 'P':
                return "壓力";

            case 'M':
                return "PM2.5";

            case 'Q':
                return "PM10";

            case 'O':
                return "噪音";

            case 'G':
                return "一氧化碳";

            case 'F':
                return "流量";

            case 'I':
                if(x == 1){
                    return "第一排";
                }else if(x ==2){
                    return "第二排";
                }else if(x == 3){
                    return "第三排";
                }

                break;

        }
        return "不知道";
    }
    /**揀選單位*/
    private String unit(char name,int x) {
        switch (name){
            case 'T':

                return "°C";

            case 'H':
                return "%";

            case 'C':
            case 'D':
            case 'E':
                return "ppm";

            case 'P':
                return "Pa N/m2";

            case 'M':
            case 'Q':
                return "μm";

            case 'O':
                return "db";

            case 'G':
                return "ppm";

            case 'F':
                return "m3/s";

            case 'I':
                if(x == 1){
                    return " ";
                }else if(x ==2){
                    return " ";
                }else if(x == 3){
                    return " ";
                }

                break;

        }
        return "不知道";
    }
}
