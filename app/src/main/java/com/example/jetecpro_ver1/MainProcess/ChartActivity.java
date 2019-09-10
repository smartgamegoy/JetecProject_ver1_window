package com.example.jetecpro_ver1.MainProcess;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.jetecpro_ver1.Chart.MyMarkerView;
import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.SQLite.DBHelper;
import com.example.jetecpro_ver1.Values.SendType;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ChartActivity extends Activity implements OnChartValueSelectedListener {
    private LineChart chart;
    String DB_NAME = SendType.DB_Name;
    String DB_TABLE = SendType.DB_TABLE + "GETRecord";
    SQLiteDatabase mCustomDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chart);
        //連接各按鈕
        setButtons();
        //設定圖表
        chartView();

        //嘗試


    }//onCreate

    /**
     * 連接各按鈕
     */
    private void setButtons() {
        Button btChooseData = findViewById(R.id.buttonChoose);
        Button btList       = findViewById(R.id.buttonList);
        Button btExport     = findViewById(R.id.buttonExport);
        Button btLookPDF    = findViewById(R.id.buttonReport);

        btChooseData.setOnClickListener(mListener);
        btList      .setOnClickListener(mListener);
        btExport    .setOnClickListener(mListener);
        btLookPDF   .setOnClickListener(mListener);
    }//setButtons

    /**
     * 分類按鈕點擊事件
     */
    private Button.OnClickListener mListener = (new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonChoose:

                    break;
                case R.id.buttonList:
                    Intent intent = new Intent(ChartActivity.this,ListDownloadDataActivity.class);
                    startActivity(intent);

                    break;
                case R.id.buttonExport:

                    break;
                case R.id.buttonReport:

                    break;


            }//switch
        }//click
    });//Button.OnClickListener
    /**圖表顯示*/
    private void chartView() {
//        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun","Aug"};
        Button btChooseChart = findViewById(R.id.buttonChoose);
        chart = findViewById(R.id.dataChart);
        DBHelper db = new DBHelper(getBaseContext(), DB_NAME, null, 1);
        mCustomDb = db.getWritableDatabase();
        final Cursor cursor = mCustomDb.rawQuery(
                "SELECT * FROM " + DB_TABLE, null);
        //取得Json資料
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


        YAxis leftAxis = chart.getAxisLeft();//設置Y軸(左)
        YAxis rightAxis = chart.getAxisRight();//設置Y軸(右)
        rightAxis.setEnabled(false);//讓右邊Y消失
        leftAxis.setAxisMaximum(90f);//顯示上限
        leftAxis.setAxisMinimum(0f);//顯示下限
        XAxis xAxis = chart.getXAxis();//設定X軸
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//將x軸表示字移到底下
        chart.getDescription().setEnabled(false);//讓右下角文字消失
//        xAxis.setEnabled(false);//去掉X軸數值
        xAxis.setDrawGridLines(false);//將X軸格子消失掉
        xAxis.setLabelRotationAngle(-45f);//讓字變成斜的
        xAxis.setValueFormatter(new MyValueFormatter());


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

        Date dt1 = rightNow.getTime();
        newTime = sdf.format(dt1);


        ArrayList<Entry> yValues1 = new ArrayList<>();
        ArrayList<Entry> yValues2 = new ArrayList<>();
        try {

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                float getFirst = trans2Double1(jsonObject.getString("First"));
                float getSceond = trans2Double2(jsonObject.getString("Second"));
                yValues1.add(new Entry(i, getFirst));
                yValues2.add(new Entry(i, getSceond));

            }

        } catch (Exception e) {

        }



        LineDataSet set1 = new LineDataSet(yValues1, "溫度");
        LineData dataText_x = new LineData(set1);
        dataText_x.setDrawValues(false);
        set1.setColor(getResources().getColor(R.color.blueRUREI));//調整線的顏色
        set1.setCircleColor(getResources().getColor(R.color.blueRUREI));//調整小圈圈的顏色
        set1.setCircleRadius(0.5f);
        set1.setLineWidth(1.5f);//條粗細
        set1.setValueTextSize(9f);

        LineDataSet set2 = new LineDataSet(yValues2, "濕度");
        LineData dataText_y = new LineData(set2);
        dataText_y.setDrawValues(false);
        set2.setColor(getResources().getColor(R.color.greenTOKIWA));
        set2.setCircleColor(getResources().getColor(R.color.greenTOKIWA));
        set2.setCircleRadius(0.5f);
        set2.setLineWidth(1.5f);//條粗細
        set2.setValueTextSize(9f);
        chart.animateX(2000);//動畫～～～


        chart.setOnChartValueSelectedListener(this);
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(chart);
        chart.setMarker(mv);
        //設置圖表點


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        LineData lineData = new LineData(dataSets);
        chart.setData(lineData);

    }



    /**轉float其一*/
    private float trans2Double1(String input) {
        String s1 = input.substring(0, 5);
        float d1 = Float.parseFloat(s1);
        float output;
        switch (input.substring(5)) {
            case "1":
                output = d1 * 0.1f;
                break;
            case "2":
                output = d1 * 0.01f;
                break;
            default:
                output = d1;
                break;

        }
//        Log.v("transD", String.valueOf(output));
        return output;
    }

    /**轉float其二*/
    private float trans2Double2(String input) {
        String s1 = input.substring(0, 5);
        float d1 = Float.parseFloat(s1);
        float output;
        switch (input.substring(5)) {
            case "1":
                output = d1 * 0.1f;
                break;
            case "2":
                output = d1 * 0.01f;
                break;
            default:
                output = d1;
                break;

        }
//        Log.v("transD", String.valueOf(output));
        return output;
    }

    /**點選圖表事件*/
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("Entry selected", "low: " + chart.getLowestVisibleX() + ", high: " + chart.getHighestVisibleX());
        Log.i("Entry selected", "xMin: " + chart.getXChartMin() + ", xMax: " + chart.getXChartMax() +
                ", yMin: " + chart.getYChartMin() + ", yMax: " + chart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {

    }
    /**設置X軸(未完工)*/
    private class MyValueFormatter extends ValueFormatter {


        @Override
        public String getFormattedValue(float value) {
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

            Date dt1 = rightNow.getTime();
            newTime = sdf.format(dt1);
//            Log.v("BT","看如何執行");
            return newTime;
        }
    }
}

