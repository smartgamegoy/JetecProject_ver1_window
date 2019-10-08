package com.example.jetecpro_ver1.MainProcess;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.SQLite.DBHelper;
import com.example.jetecpro_ver1.SupportListDownloadListView.DataFilterActivity;
import com.example.jetecpro_ver1.Values.SendType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListDownloadDataActivity extends Activity {
    String DB_NAME = SendType.DB_Name;
    String DB_TABLE = SendType.DB_TABLE + "GETRecord";
    SQLiteDatabase mCustomDb;
    ListView listView;
    EditText edFilter;
    private SimpleAdapter simpleAdapter;
    long firstTime = 0;
    private int currentIndex=0;  //记录当前ListView的索引

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_download_data);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//取消一開畫面就彈出鍵盤
        //設置ActionBar
        setActionBar();
        //取得SQLite資料
        getSQLdata();
        //將Action上的item字體顏色設為白色
        invalidateOptionsMenu();


    }//onCreate

    /**
     * 設置ActionBar
     */
    private void setActionBar() {
        getActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.yellowNAMAKABE)));

        TextView textView = new TextView(this);
        textView.setText(R.string.listDisplay);
        textView.setTextSize(24);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        textView.setGravity(Gravity.CENTER);//文字置中
        textView.setTextColor(Color.WHITE);
        getActionBar().setHomeAsUpIndicator(R.drawable.ic_navigate_before_black_24dp);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(textView);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }//setActionBar

    /**
     * 取得SQLite的資料
     */
    private void getSQLdata() {
        listView = findViewById(R.id.listView_down);
        edFilter = findViewById(R.id.editTextFilter);
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
                row2(jsonArray, arrayList);
                break;

            case '3':
                row3(jsonArray, arrayList);
                break;

        }



        //搜尋ListView內容
        edFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (ListDownloadDataActivity.this).simpleAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });//edFilter
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {//上華下滑的設置
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {

                    // 滚动之前,手还在屏幕上  记录滚动前的下标
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        //view.getLastVisiblePosition()得到当前屏幕可见的第一个item在整个listview中的下标
                        currentIndex = view.getLastVisiblePosition();
                        break;

                    //滚动停止
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        //记录滚动停止后 记录当前item的位置
                        int scrolled = view.getLastVisiblePosition();
                        //滚动后下标大于滚动前 向下滚动了
                        if (scrolled > currentIndex) {
                            edFilter.setVisibility(View.GONE);
                        }
                        //向上滚动了
                        else if(scrolled<currentIndex) {
                            edFilter.setVisibility(View.VISIBLE);
                        }else if(scrolled == 3 &&currentIndex == 3){
                            edFilter.setVisibility(View.VISIBLE);
                        }
                        break;
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }//getSQLdata

    private void row3(JSONArray jsonArray, ArrayList<HashMap<String, String>> arrayList){
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap<String, String> hashMap = new HashMap<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id           = jsonObject.getString("id");
                String firstData    = trans2Double1(jsonObject.getString("First"));
                String secondData   = trans2Double1(jsonObject.getString("Second"));
                String thirdData   = trans2Double1(jsonObject.getString("Third"));
                String Date         = jsonObject.getString("RecordDate");
                String Time         = jsonObject.getString("RecordTime");
                hashMap.put("DateTime",Date+" "+Time);
                hashMap.put("id",id);
                hashMap.put("first",firstData+unit(SendType.FirstWord,1));
                hashMap.put("second",secondData+unit(SendType.SecondWord,2));
                hashMap.put("third",thirdData+unit(SendType.ThirdWord,3));
                hashMap.put("title1",Lab(SendType.FirstWord,1));
                hashMap.put("title2",Lab(SendType.SecondWord,2));
                hashMap.put("title3",Lab(SendType.ThirdWord,3));
                arrayList.add(hashMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] from = {"title1","title2","title3","DateTime", "id","first","second","third"};
        int [] to = {R.id.listView_FirstDataTitle,R.id.listView_SecendDataTitle,
                R.id.listView_ThirdDataTitle,R.id.listView_dateTime,R.id.listView_RecordId,R.id.listView_firstData,R.id.listView_SecendData,R.id.listView_ThirdData};
        simpleAdapter = new SimpleAdapter(this,arrayList,R.layout.activity_list_download_data_custom,from,to);
        listView.setAdapter(simpleAdapter);
    }

    private void row2(JSONArray jsonArray, ArrayList<HashMap<String, String>> arrayList) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap<String, String> hashMap = new HashMap<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id           = jsonObject.getString("id");
                String firstData    = trans2Double1(jsonObject.getString("First"));
                String secondData   = trans2Double1(jsonObject.getString("Second"));
                String Date         = jsonObject.getString("RecordDate");
                String Time         = jsonObject.getString("RecordTime");
                hashMap.put("DateTime",Date+" "+Time);
                hashMap.put("id",id);
                hashMap.put("first",firstData.substring(0,4)+unit(SendType.FirstWord,1));
                hashMap.put("second",secondData.substring(0,4)+unit(SendType.SecondWord,2));
                arrayList.add(hashMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] from = {"DateTime", "id","first","second"};
        int [] to = {R.id.listView_dateTime,R.id.listView_RecordId,R.id.listView_firstData,R.id.listView_SecendData};
        simpleAdapter = new SimpleAdapter(this,arrayList,R.layout.activity_list_download_data_custom,from,to);
        listView.setAdapter(simpleAdapter);
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

    /**
     * 設置ActionBar的活動
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.dataFilter:
                Intent intent = new Intent(ListDownloadDataActivity.this, DataFilterActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 設置ActionBar內容
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.download_list_display, menu);
        return super.onCreateOptionsMenu(menu);
    }

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
     * 獲取返回鍵活動
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        long secondTime = System.currentTimeMillis();
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (secondTime - firstTime < 2000) {
                finish();
            } else {
                Toast.makeText(getApplicationContext(), R.string.plzPushTwo, Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**將ActionBar內字體設為白*/
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        View view = findViewById(R.id.dataFilter);
        if (view != null && view instanceof TextView) {
            ((TextView) view).setTextColor( Color.WHITE ); // Make text colour blue
        }
        return super.onPrepareOptionsMenu(menu);
    }

}//結尾
