package com.example.jetecpro_ver1.MainProcess;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_download_data);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//取消一開畫面就彈出鍵盤
        //設置ActionBar
        setActionBar();
        //取得SQLite資料
        getSQLdata();


    }//onCreate

    /**
     * 設置ActionBar
     */
    private void setActionBar() {
        getActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.ActionBarColor)));
        TextView textView = new TextView(this);
        textView.setText(R.string.listDisplay);
        textView.setTextSize(24);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        textView.setGravity(Gravity.CENTER);//文字置中
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
                hashMap.put("first",firstData+"°C");
                hashMap.put("second",secondData+"%");
                arrayList.add(hashMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] from = {"DateTime", "id","first","second"};
        int [] to = {R.id.listView_dateTime,R.id.listView_RecordId,R.id.listView_firstData,R.id.listView_SecondData};
        simpleAdapter = new SimpleAdapter(this,arrayList,R.layout.activity_list_download_data_custom,from,to);
        listView.setAdapter(simpleAdapter);
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
        });

    }//getSQLdata

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
}//結尾
