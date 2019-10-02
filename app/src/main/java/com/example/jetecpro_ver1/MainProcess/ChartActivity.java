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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.jetecpro_ver1.Chart.MyMarkerView;
import com.example.jetecpro_ver1.PDFandCSVHelper_ChartAct.CreatePDFandCSV;
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
    int displayChartCentrol = 2;
    JSONArray mJsonArray;

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
                    String[] str = {trans(R.string.Temperature), trans(R.string.Humidity), trans(R.string.MixxxxxChart)};
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
        if (displayChartCentrol == 0) {
            leftAxis.setAxisMaximum(50f);//顯示上限
            leftAxis.setAxisMinimum(10f);//顯示下限
            dataSets.add(set1);
        } else if (displayChartCentrol == 1) {
            leftAxis.setAxisMaximum(80f);//顯示上限
            leftAxis.setAxisMinimum(40f);//顯示下限
            dataSets.add(set2);
        } else if (displayChartCentrol == 2) {
            leftAxis.setAxisMaximum(90f);//顯示上限
            leftAxis.setAxisMinimum(0f);//顯示下限
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

    /**
     * 設置X軸
     */
    private class MyValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {

            final ArrayList<String> xLabel = new ArrayList<>();
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
            return xLabel.get((int) value);
        }
    }

    private String trans(int name) {//不是我在講...每個都要寫ctx.getResources().getString(R.string.??);真的會死人
        String str = getBaseContext().getResources().getString(name);
        return str;
    }
}

