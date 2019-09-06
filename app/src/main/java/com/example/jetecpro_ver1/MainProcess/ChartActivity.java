package com.example.jetecpro_ver1.MainProcess;

import android.app.Activity;
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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        //設定各按鈕
        setButtons();
        //設定圖表
        chartView();

    }//onCreate

    /**
     * 連接各按鈕
     */
    private void setButtons() {
        Button btList = findViewById(R.id.buttonList);
        Button btExport = findViewById(R.id.buttonExport);
        Button btLookPDF = findViewById(R.id.buttonReport);
        btList.setOnClickListener(mListener);
        btExport.setOnClickListener(mListener);
        btLookPDF.setOnClickListener(mListener);
    }//setButtons

    /**
     * 分類按鈕點擊事件
     */
    private Button.OnClickListener mListener = (new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonList:

                    break;
                case R.id.buttonExport:

                    break;
                case R.id.buttonReport:

                    break;


            }//switch
        }//click
    });//Button.OnClickListener

    private void chartView(){
        Button btChooseChart = findViewById(R.id.buttonChoose);
        chart = findViewById(R.id.dataChart);
        DBHelper db = new DBHelper(getBaseContext(),DB_NAME,null,1);
        mCustomDb = db.getWritableDatabase();
        Cursor cursor = mCustomDb.rawQuery(
                "SELECT * FROM " + DB_TABLE , null);


        YAxis leftAxis = chart.getAxisLeft();//設置Y軸(左)
        YAxis rightAxis = chart.getAxisRight();//設置Y軸(右)
        rightAxis.setEnabled(false);//讓右邊Y消失
        leftAxis.setAxisMaximum(100f);//顯示上限
        leftAxis.setAxisMinimum(0f);//顯示下限
        XAxis xAxis = chart.getXAxis();//設定X軸
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//將x軸表示字移到底下
        chart.getDescription().setEnabled(false);//讓右下角文字消失
//        xAxis.setEnabled(false);//去掉X軸數值
        xAxis.setDrawGridLines(false);//將X軸格子消失掉
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);

            @Override
            public String getFormattedValue(float value) {
                /*DBHelper db = new DBHelper(getBaseContext(),DB_NAME,null,1);
                mCustomDb = db.getWritableDatabase();
                Cursor cursor = mCustomDb.rawQuery(
                        "SELECT * FROM " + DB_TABLE , null);*/
                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));
//                return "RRR";

            }
        });
        xAxis.setLabelCount(4,false);



        ArrayList<Entry> yValues1 = new ArrayList<>();
        ArrayList<Entry> yValues2 = new ArrayList<>();
        while (cursor.moveToNext()){
            float getV = trans2Double1(cursor.getString(1));
            float get = trans2Double2(cursor.getString(2));
            int g = Integer.parseInt(cursor.getString(0));
            yValues1.add(new Entry(g,getV));
            yValues2.add(new Entry(g,get));


        }

        LineDataSet set1 = new LineDataSet(yValues1,"溫度");
        LineDataSet set2 = new LineDataSet(yValues2,"濕度");
        set1.setColor(getResources().getColor(R.color.blueRUREI));//調整線的顏色
        set1.setCircleColor(getResources().getColor(R.color.blueRUREI));//調整小圈圈的顏色
        set1.setCircleRadius(0.5f);
        set1.setLineWidth(1.5f);//條粗細
        set1.setValueTextSize(9f);


        set2.setColor(getResources().getColor(R.color.greenTOKIWA));
        set2.setCircleColor(getResources().getColor(R.color.greenTOKIWA));
        set2.setCircleRadius(0.5f);
        set2.setLineWidth(1.5f);//條粗細
        set2.setValueTextSize(9f);
        chart.animateX(2000);//動畫～～～


        {
            chart.setOnChartValueSelectedListener(this);
            MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
            mv.setChartView(chart);
            chart.setMarker(mv);
        }//設置圖表點


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        LineData data = new LineData(dataSets);
        chart.setData(data);

    }

    private float trans2Double1(String input){
        String s1 = input.substring(0,5);
        float d1 = Float.parseFloat(s1);
        float output;
        switch (input.substring(5)){
            case "1":
                output = d1*0.1f;
                break;
            case "2":
                output = d1*0.01f;
                break;
                default:
                    output = d1;
                    break;

        }
//        Log.v("transD", String.valueOf(output));
        return output;
    }
    private float trans2Double2(String input){
        String s1 = input.substring(0,5);
        float d1 = Float.parseFloat(s1);
        float output;
        switch (input.substring(5)){
            case "1":
                output = d1*0.1f;
                break;
            case "2":
                output = d1*0.01f;
                break;
            default:
                output = d1;
                break;

        }
//        Log.v("transD", String.valueOf(output));
        return output;
    }


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
}
