package com.example.jetecpro_ver1.MainProcess;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jetecpro_ver1.Chart.MyMarkerView;
import com.example.jetecpro_ver1.PDFandCSVHelper_ChartAct.CreatePDFandCSV;
import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.SQLite.DBHelper;
import com.example.jetecpro_ver1.Values.SendType;
import com.github.mikephil.charting.charts.Chart;
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
import com.itextpdf.text.pdf.PdfPTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
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
    int displayChartCentrol = Integer.parseInt(String.valueOf(SendType.row));
    JSONArray mJsonArray;
     ArrayList<String> xLabel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chart);
        getWindow().setStatusBarColor(getResources().getColor(R.color.White));
        if (SendType.FirstWord =='Y'
                || SendType.SecondWord == 'Y'
                || SendType.ThirdWord == 'Y'
                || SendType.FourthWord == 'Y'){
            switch (SendType.row){
                case '3':
                    SendType.row = '2';
                    break;
                case '2':
                    SendType.row = '1';
                    break;
            }

            displayChartCentrol =Integer.parseInt(String.valueOf(SendType.row));
        }
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
        Button btList = findViewById(R.id.buttonList);
        Button btExport = findViewById(R.id.buttonExport);
        Button btLookPDF = findViewById(R.id.buttonReport);

        btChooseData.setOnClickListener(mListener);
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
                case R.id.buttonChoose:
                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(ChartActivity.this);
                    final View vvvvvvvvvvvvvvvv = getLayoutInflater().inflate(R.layout.activity_chart_button_choose_dialog, null);
                    mBuilder.setView(vvvvvvvvvvvvvvvv);
                    final ListView thisIsListViewHahahahahahahahhahahaha =
                            vvvvvvvvvvvvvvvv.findViewById(R.id.lvDDDDDDDDialog);
                    Button btOKOK = vvvvvvvvvvvvvvvv.findViewById(R.id.LineChartChooseOK);
                    Button btCancle = vvvvvvvvvvvvvvvv.findViewById(R.id.LineChartChooseCancel);
                    String[] str = new String[0];
                    switch (SendType.row){
                        case '1':
                            str = new String[]{Lab(SendType.FirstWord,1)};
                            break;
                        case '2':
                            str = new String[]{Lab(SendType.FirstWord,1),Lab(SendType.SecondWord,2),trans(R.string.MixxxxxChart)};
                            break;
                        case '3':
                            str = new String[]{Lab(SendType.FirstWord,1),Lab(SendType.SecondWord,2),Lab(SendType.ThirdWord,3),trans(R.string.MixxxxxChart)};
                            break;

                    }
                    ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, str);
                    thisIsListViewHahahahahahahahhahahaha.setAdapter(adapter);
                    final Dialog dialog = mBuilder.create();
                    thisIsListViewHahahahahahahahhahahaha.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            thisIsListViewHahahahahahahahhahahaha.setSelector(R.color.Sakura);//點擊背景色
                            displayChartCentrol = position;
                        }
                    });
                    btOKOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chart.clear();
                            chartView();
                            dialog.dismiss();

                        }
                    });
                    btCancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                    break;
                case R.id.buttonList:
                    Intent intent = new Intent(ChartActivity.this, ListDownloadDataActivity.class);
                    startActivity(intent);

                    break;
                case R.id.buttonExport:
                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
                    }
                    final CreatePDFandCSV choose = new CreatePDFandCSV(ChartActivity.this);
                   AlertDialog.Builder fileChoose = new AlertDialog.Builder(ChartActivity.this);
                   fileChoose.setTitle(R.string.reportTitle);
                   fileChoose.setMessage(R.string.plzChooseExportType);
                   fileChoose.setPositiveButton("PDF", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           switch (SendType.row){
                               case '1':
                                   choose.PDF_1C(getBaseContext());
                                   break;
                               case '2':

                                   choose.PDF_2C(getBaseContext());
                                   break;
                               case '3':
                                   choose.PDF_3C(getBaseContext());
                                   break;
                           }

                       }
                   });
                   fileChoose.setNegativeButton("CSV", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           choose.CSV(getBaseContext());

                       }
                   });
                   fileChoose.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {

                       }
                   });
                   fileChoose.show();

                    break;
                case R.id.buttonReport:

                    break;


            }//switch
        }//click
    });//Button.OnClickListener

    /**
     * 圖表顯示
     */
    private void chartView() {

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
            mJsonArray = new JSONArray(getJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setXLabel();
        YAxis leftAxis = chart.getAxisLeft();//設置Y軸(左)
        YAxis rightAxis = chart.getAxisRight();//設置Y軸(右)
        rightAxis.setEnabled(false);//讓右邊Y消失

        XAxis xAxis = chart.getXAxis();//設定X軸
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//將x軸表示字移到底下
        chart.getDescription().setEnabled(false);//讓右下角文字消失
//        xAxis.setEnabled(false);//去掉X軸數值
        xAxis.setDrawGridLines(false);//將X軸格子消失掉
        xAxis.setLabelRotationAngle(-45f);//讓字變成斜的
        xAxis.setValueFormatter(new MyValueFormatter());

        switch (SendType.row){

            case '1':

                break;
            case '2':
                Row2(jsonArray, leftAxis);
                break;
            case '3':
                Row3(jsonArray, leftAxis);
                break;
        }
    }

    private void Row3(JSONArray jsonArray, YAxis leftAxis) {
        ArrayList<Entry> yValues1 = new ArrayList<>();
        ArrayList<Entry> yValues2 = new ArrayList<>();
        ArrayList<Entry> yValues3 = new ArrayList<>();
        try {

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                float getFirst = trans2Double1(jsonObject.getString("First"));
                float getSceond = trans2Double2(jsonObject.getString("Second"));
                float getThird = trans2Double2(jsonObject.getString("Third"));
                yValues1.add(new Entry(i, getFirst));
                yValues2.add(new Entry(i, getSceond));
                yValues3.add(new Entry(i, getThird));
            }
        } catch (Exception e) {}


        LineDataSet set1 = new LineDataSet(yValues1, Lab(SendType.FirstWord,1));
        LineDataSet set2 = new LineDataSet(yValues2, Lab(SendType.SecondWord,2));
        LineDataSet set3 = new LineDataSet(yValues3, Lab(SendType.ThirdWord,3));

        LineData dataText1 = new LineData(set1);
        dataText1.setDrawValues(false);
        set1.setColor(getResources().getColor(R.color.blueRUREI));//調整線的顏色
        set1.setCircleColor(getResources().getColor(R.color.blueRUREI));//調整小圈圈的顏色
        set1.setCircleRadius(0.5f);
        set1.setLineWidth(1.5f);//條粗細
        set1.setValueTextSize(9f);

        LineData dataText2 = new LineData(set2);
        dataText2.setDrawValues(false);
        set2.setColor(getResources().getColor(R.color.greenTOKIWA));
        set2.setCircleColor(getResources().getColor(R.color.greenTOKIWA));
        set2.setCircleRadius(0.5f);
        set2.setLineWidth(1.5f);//條粗細
        set2.setValueTextSize(9f);

        LineData dataText3 = new LineData(set3);
        dataText3.setDrawValues(false);
        set3.setColor(getResources().getColor(R.color.redGINSYU));
        set3.setCircleColor(getResources().getColor(R.color.redGINSYU));
        set3.setCircleRadius(0.5f);
        set3.setLineWidth(1.5f);//條粗細
        set3.setValueTextSize(9f);

        chart.animateX(2000);//動畫～～～


        chart.setOnChartValueSelectedListener(this);
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(chart);
        chart.setMarker(mv);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        if (displayChartCentrol == 0) {
            chooseChhartName(leftAxis, SendType.FirstWord);
            dataSets.add(set1);
        } else if (displayChartCentrol == 1) {
            chooseChhartName(leftAxis, SendType.SecondWord);
            dataSets.add(set2);
        } else if (displayChartCentrol == 2) {
            chooseChhartName(leftAxis, SendType.ThirdWord);
            dataSets.add(set3);
        } else if (displayChartCentrol == 3) {
            //因為需要三個輸入值一起判斷，因此無法用switch-case寫
            if (SendType.FirstWord == 'I'
                    || SendType.SecondWord == 'I'
                    || SendType.ThirdWord == 'I'){
                leftAxis.setAxisMaximum(10000f);//顯示上限
                leftAxis.setAxisMinimum(-10000f);//顯示下限
            }else if(SendType.FirstWord == 'F'
                    || SendType.SecondWord == 'F'
                    || SendType.ThirdWord == 'F'){
                leftAxis.setAxisMaximum(999f);//顯示上限
                leftAxis.setAxisMinimum(-999f);//顯示下限
            }else if(SendType.FirstWord == 'E'
                    ||SendType.SecondWord == 'E'
                    || SendType.ThirdWord == 'E'){
                leftAxis.setAxisMaximum(5000f);//顯示上限
                leftAxis.setAxisMinimum(0f);//顯示下限
            }else if(SendType.FirstWord == 'D'
                    ||SendType.SecondWord == 'D'
                    || SendType.ThirdWord == 'D'){
                leftAxis.setAxisMaximum(3000f);//顯示上限
                leftAxis.setAxisMinimum(0f);//顯示下限
            }else if(SendType.FirstWord == 'C'
                    ||SendType.SecondWord == 'C'
                    || SendType.ThirdWord == 'C'){
                leftAxis.setAxisMaximum(2000f);//顯示上限
                leftAxis.setAxisMinimum(0f);//顯示下限
            }else if(SendType.FirstWord == 'P'
                    ||SendType.SecondWord == 'P'
                    || SendType.ThirdWord == 'P'
                    ||SendType.FirstWord == 'M'
                    ||SendType.SecondWord == 'M'
                    || SendType.ThirdWord == 'M'
                    ||SendType.FirstWord == 'Q'
                    ||SendType.SecondWord == 'Q'
                    || SendType.ThirdWord == 'Q'){
                leftAxis.setAxisMaximum(1000f);//顯示上限
                leftAxis.setAxisMinimum(0f);//顯示下限
            }else if(SendType.FirstWord == 'G'
                    ||SendType.SecondWord == 'G'
                    || SendType.ThirdWord == 'G'){
                leftAxis.setAxisMaximum(300f);//顯示上限
                leftAxis.setAxisMinimum(0f);//顯示下限
            }else if(SendType.FirstWord == 'O'
                    ||SendType.SecondWord == 'O'
                    || SendType.ThirdWord == 'O'){
                leftAxis.setAxisMaximum(130f);//顯示上限
                leftAxis.setAxisMinimum(30f);//顯示下限
            }else if (SendType.FirstWord == 'H'
                    ||SendType.SecondWord == 'H'
                    || SendType.ThirdWord == 'H'){
                leftAxis.setAxisMaximum(100f);//顯示上限
                leftAxis.setAxisMinimum(0f);//顯示下限
            }else if (SendType.FirstWord == 'T'
                    ||SendType.SecondWord == 'T'
                    || SendType.ThirdWord == 'T'){
                leftAxis.setAxisMaximum(65f);//顯示上限
                leftAxis.setAxisMinimum(-10f);//顯示下限
            }
            dataSets.add(set1);
            dataSets.add(set2);
            dataSets.add(set3);
        }


        LineData lineData = new LineData(dataSets);
        chart.setData(lineData);
    }

    /**揀選名稱*/
    private void chooseChhartName(YAxis leftAxis, char word) {
        switch (word) {

            case 'I':
                leftAxis.setAxisMaximum(10000f);//顯示上限
                leftAxis.setAxisMinimum(-10000f);//顯示下限
                break;

            case 'E':
                leftAxis.setAxisMaximum(3000f);//顯示上限
                leftAxis.setAxisMinimum(0f);//顯示下限
                break;

            case 'D':
                leftAxis.setAxisMaximum(2000f);//顯示上限
                leftAxis.setAxisMinimum(0f);//顯示下限
                break;

            case 'C':
            case 'P':
            case 'M':
            case 'Q':
                leftAxis.setAxisMaximum(1000f);//顯示上限
                leftAxis.setAxisMinimum(0f);//顯示下限
                break;

            case 'F':
                leftAxis.setAxisMaximum(999f);//顯示上限
                leftAxis.setAxisMinimum(-999f);//顯示下限
                break;

            case 'G':
                leftAxis.setAxisMaximum(300f);//顯示上限
                leftAxis.setAxisMinimum(0f);//顯示下限
                break;

            case 'H':
                leftAxis.setAxisMaximum(100f);//顯示上限
                leftAxis.setAxisMinimum(00f);//顯示下限
                break;

            case 'O':
                leftAxis.setAxisMaximum(130f);//顯示上限
                leftAxis.setAxisMinimum(30f);//顯示下限
                break;

            case 'T':
                leftAxis.setAxisMaximum(80f);//顯示上限
                leftAxis.setAxisMinimum(-65f);//顯示下限
                break;

        }
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
    /**畫兩排圖表*/
    private void Row2(JSONArray jsonArray, YAxis leftAxis) {
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
            Log.v("BT", "Row2: "+e.getMessage());
        }


        LineDataSet set1 = new LineDataSet(yValues1, Lab(SendType.FirstWord,1));
        LineData dataText_x = new LineData(set1);
        dataText_x.setDrawValues(false);
        set1.setColor(getResources().getColor(R.color.blueRUREI));//調整線的顏色
        set1.setCircleColor(getResources().getColor(R.color.blueRUREI));//調整小圈圈的顏色
        set1.setCircleRadius(0.5f);
        set1.setLineWidth(1.5f);//條粗細
        set1.setValueTextSize(9f);

        LineDataSet set2 = new LineDataSet(yValues2, Lab(SendType.SecondWord,2));
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
        if (displayChartCentrol == 0) {
            chooseChhartName(leftAxis, SendType.FirstWord);
            dataSets.add(set1);
        } else if (displayChartCentrol == 1) {
            chooseChhartName(leftAxis, SendType.SecondWord);
            dataSets.add(set2);
        } else if (displayChartCentrol == 2) {
            //因為需要兩個輸入值一起判斷，因此無法用switch-case寫
            if (SendType.FirstWord == 'I'
                    || SendType.SecondWord == 'I'){
                leftAxis.setAxisMaximum(10000f);//顯示上限
                leftAxis.setAxisMinimum(-10000f);//顯示下限
            }else if(SendType.FirstWord == 'F'
                    || SendType.SecondWord == 'F'){
                leftAxis.setAxisMaximum(999f);//顯示上限
                leftAxis.setAxisMinimum(-999f);//顯示下限
            }else if(SendType.FirstWord == 'E'
                    ||SendType.SecondWord == 'E'){
                leftAxis.setAxisMaximum(5000f);//顯示上限
                leftAxis.setAxisMinimum(0f);//顯示下限
            }else if(SendType.FirstWord == 'D'
                    ||SendType.SecondWord == 'D'){
                leftAxis.setAxisMaximum(3000f);//顯示上限
                leftAxis.setAxisMinimum(0f);//顯示下限
            }else if(SendType.FirstWord == 'C'
                    ||SendType.SecondWord == 'C'){
                leftAxis.setAxisMaximum(2000f);//顯示上限
                leftAxis.setAxisMinimum(0f);//顯示下限
            }else if(SendType.FirstWord == 'P'
                    ||SendType.SecondWord == 'P'
                    ||SendType.FirstWord == 'M'
                    ||SendType.SecondWord == 'M'
                    ||SendType.FirstWord == 'Q'
                    ||SendType.SecondWord == 'Q'){
                leftAxis.setAxisMaximum(1000f);//顯示上限
                leftAxis.setAxisMinimum(0f);//顯示下限
            }else if(SendType.FirstWord == 'G'
                    ||SendType.SecondWord == 'G'){
                leftAxis.setAxisMaximum(300f);//顯示上限
                leftAxis.setAxisMinimum(0f);//顯示下限
            }else if(SendType.FirstWord == 'O'
                    ||SendType.SecondWord == 'O'){
                leftAxis.setAxisMaximum(130f);//顯示上限
                leftAxis.setAxisMinimum(30f);//顯示下限
            }else if (SendType.FirstWord == 'H'
                    ||SendType.SecondWord == 'H'){
                leftAxis.setAxisMaximum(100f);//顯示上限
                leftAxis.setAxisMinimum(0f);//顯示下限
            }else if (SendType.FirstWord == 'T'
                    ||SendType.SecondWord == 'T'){
                leftAxis.setAxisMaximum(65f);//顯示上限
                leftAxis.setAxisMinimum(-10f);//顯示下限
            }

            dataSets.add(set1);
            dataSets.add(set2);
        }


        LineData lineData = new LineData(dataSets);
        chart.setData(lineData);
    }


    /**
     * 轉floatその〜〜いち！
     */
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

    /**
     * 轉floatその〜〜に！
     */
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

    /**
     * 點選圖表事件
     */
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (SendType.FirstWord =='Y'
                    || SendType.SecondWord == 'Y'
                    || SendType.ThirdWord == 'Y'
                    || SendType.FourthWord == 'Y'){
                switch (SendType.row){
                    case '2':
                        SendType.row = '3';
                        break;
                    case '1':
                        SendType.row = '2';
                        break;
                }
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 設置X軸
     */
    private class MyValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {



            return xLabel.get((int) value);
        }
    }
    /**取得X軸標籤陣列*/
    private void setXLabel() {
        for (int i = 0; i < mJsonArray.length(); i++) {
            try {
                JSONObject jsonObject = mJsonArray.getJSONObject(i);
                String Date = jsonObject.getString("RecordDate");
                String Time = jsonObject.getString("RecordTime");
                xLabel.add(Date + " " + Time);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private String trans(int name) {//不是我在講...每個都要寫ctx.getResources().getString(R.string.??);真的會死人
        String str = getBaseContext().getResources().getString(name);
        return str;
    }
}

