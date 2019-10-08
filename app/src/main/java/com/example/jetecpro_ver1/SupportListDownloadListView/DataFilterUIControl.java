package com.example.jetecpro_ver1.SupportListDownloadListView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.SQLite.DBHelper;
import com.example.jetecpro_ver1.Values.SendType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DataFilterUIControl {
    Context context;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Button btGoSearch;
    LinearLayout liDTSelecter, liIDSelecter;
    Activity activity;
    NumberPicker numberPicker, numberPickerStart, numberPickerEnd;
    String DB_NAME = SendType.DB_Name;
    String DB_TABLE = SendType.DB_TABLE + "GETRecord";
    SQLiteDatabase mCustomDb;
    TextView tvIdResult,tvResult;
    int startOld, endOld, startNew, endNew, NpMathFunction;
    EditText editText;

    String FirstID, LastID, FirstDateRecord, LastDateRecord, FirstTimeRecord, LastTimeRecord, getSelectedDate, getSelectedTime, selectType;
    int f_YEAR, f_MONTH, f_DAY, l_YEAR, l_MONTH, l_DAY;

    String selectedFirID, selectedSecID;


    public DataFilterUIControl(Context context, Button btGoSearch, Activity activity) {
        this.context = context;
        this.btGoSearch = btGoSearch;
        this.activity = activity;
    }
    //================================分隔線

    /**
     * 取得SQLite內的已下載資料
     */
    private void getSQLite() {
        DBHelper db = new DBHelper(context, DB_NAME, null, 1);
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
        try {
            for (int i = 0; i < 1; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                LastID = jsonObject.getString("id");
                LastDateRecord = jsonObject.getString("RecordDate");
                LastTimeRecord = jsonObject.getString("RecordTime");

            }
            for (int i = jsonArray.length() - 1; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                FirstID = jsonObject.getString("id");
                FirstDateRecord = jsonObject.getString("RecordDate");
                FirstTimeRecord = jsonObject.getString("RecordTime");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //================================分隔線(時間篩選)

    /**
     * 日期與時間的選擇介面
     */
    public void DateTimeSelect(LinearLayout liDTSelecter, String selectType) {
        this.selectType = selectType;
        this.liDTSelecter = liDTSelecter;

        getSQLite();
        tvResult = activity.findViewById(R.id.dataFilter_textViewResult);
        TextView tvTiiiiiiiitle = activity.findViewById(R.id.textViewTTTTTTTTTititititi);
        Button selectDate = activity.findViewById(R.id.dataFilter_buttonSelectDate);
        Button selectTime = activity.findViewById(R.id.dataFilter_buttonSelectTime);
        selectDate.setOnClickListener(DateTimeFunction);
        selectTime.setOnClickListener(DateTimeFunction);
        tvTiiiiiiiitle.setText(trans(R.string.dataFilter_SelectRange) + "\n" + FirstDateRecord + " " + FirstTimeRecord +
                "~" + LastDateRecord + " " + LastTimeRecord);
        tvResult.setText(trans(R.string.dataFilter_YouChoose)+trans(R.string.plz_notify)
                +"\n"
                +"-- --");
        //載入TimePicker
        timeDataSelectEvent_TimePickerSupport();
        pickerrrrrsssssss();

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                switch (newVal) {
                    case 0:
                        if (getSelectedTime == null || getSelectedDate == null) {
                            tvResult.setText(trans(R.string.dataFilter_YouChoose)+trans(R.string.plz_notify)
                                    +"\n"
                                    +"-- --"
                                    +trans(R.string.BBBBBefore));
                        } else {
                            tvResult.setText(trans(R.string.dataFilter_YouChoose)+trans(R.string.plz_notify)
                                    +"\n"
                                    +getSelectedDate + " " + getSelectedTime
                                    +trans(R.string.BBBBBefore));
                        }
                        break;
                    case 1:
                        if (getSelectedTime == null || getSelectedDate == null) {
                            tvResult.setText(trans(R.string.dataFilter_YouChoose)+trans(R.string.plz_notify)
                                    +"\n"
                                    +"-- --"
                                    +trans(R.string.AAAAAfter));
                        } else {
                            tvResult.setText(trans(R.string.dataFilter_YouChoose)+trans(R.string.plz_notify)
                                    +"\n"
                                    +getSelectedDate + " " + getSelectedTime
                                    +trans(R.string.AAAAAfter));
                        }
                        break;
                    case 2:
                        if (getSelectedTime == null || getSelectedDate == null) {
                            tvResult.setText(trans(R.string.dataFilter_YouChoose)+trans(R.string.plz_notify)
                                    +"\n"
                                    +"-- --");
                        } else {
                            tvResult.setText(trans(R.string.dataFilter_YouChoose)+trans(R.string.plz_notify)
                                    + "\n" + getSelectedDate + " " + getSelectedTime+"~59");
                        }

                        break;

                }

            }
        });
        buttonClickData();

    }

    /**
     * 設置大於小於的NumberPicker
     */
    private void timeDataSelectEvent_TimePickerSupport() {
        liDTSelecter.setVisibility(View.VISIBLE);
        btGoSearch.setVisibility(View.VISIBLE);
        numberPicker = activity.findViewById(R.id.dataFilter_numberPicker);
        final String[] mathMethod = activity.getResources().getStringArray(R.array.meth_Function_time);
        numberPicker.setMinValue(0);
        numberPicker.setValue(2);
        numberPicker.setMaxValue(mathMethod.length - 1);
        numberPicker.setDisplayedValues(mathMethod);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

    }

    /**
     * 日期與時間選項的選擇按鈕
     */
    private Button.OnClickListener DateTimeFunction = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dataFilter_buttonSelectDate:
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    trans2Date();
                    Calendar c = Calendar.getInstance();
                    Calendar b = Calendar.getInstance();
                    b.set(l_YEAR, l_MONTH - 1, l_DAY);//放大->F
                    c.set(f_YEAR, f_MONTH - 1, f_DAY);//Year,Mounth -1,Day//放小->L
                    try{
                        datePickerDialog.getDatePicker().setMaxDate(b.getTimeInMillis());
                        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                        datePickerDialog.show();
                    }catch (Exception e){
                        Toast.makeText(context,R.string.ExMessage1,Toast.LENGTH_SHORT).show();
                        tvResult.setText(trans(R.string.dataFilter_YouChoose)
                                + "\n" + FirstDateRecord);
                    }

                    break;
                case R.id.dataFilter_buttonSelectTime:
                    timePickerDialog.show();

                    break;
            }
        }
    };

    /**
     * 時間日期選擇器視窗設置
     */
    private void pickerrrrrsssssss() {

        GregorianCalendar calendar = new GregorianCalendar();

        datePickerDialog = new DatePickerDialog(activity, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        DecimalFormat decimalFormat = new DecimalFormat("00");
//                        Log.v("BT", "選擇日期為:" + decimalFormat.format(year) + decimalFormat.format(month + 1) + decimalFormat.format(dayOfMonth));
                        getSelectedDate = decimalFormat.format(year) + "-" + decimalFormat.format(month + 1) + "-" + decimalFormat.format(dayOfMonth);
                        tvResultDisplay();
//                Log.v("BT","選擇日期為:"+year+month+dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                DecimalFormat decimalFormat = new DecimalFormat("00");
//                Log.v("BT", "選擇時間為:" + decimalFormat.format(hourOfDay) + decimalFormat.format(minute));
                getSelectedTime = decimalFormat.format(hourOfDay) + ":00";
                tvResultDisplay();
//                Log.v("BT","選擇時間為:"+hourOfDay+minute);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(calendar.MINUTE), true);


    }
    private void tvResultDisplay(){
        String method;
        switch (numberPicker.getValue()){
            case 0:
                method = trans(R.string.BBBBBefore);
                break;
            case 1:
                method = trans(R.string.AAAAAfter);
                break;
                default:
                    method = "~59";
                    break;
        }
        if (getSelectedTime == null){
            tvResult.setText(trans(R.string.dataFilter_YouChoose)+trans(R.string.plz_notify)
                    + "\n" + getSelectedDate + " " + "--");
        }else if(getSelectedDate == null){
            tvResult.setText(trans(R.string.dataFilter_YouChoose)+trans(R.string.plz_notify)
                    + "\n" + "--" + " " + getSelectedTime+method);

        }else if(getSelectedDate == null &&getSelectedTime == null){
            tvResult.setText(trans(R.string.dataFilter_YouChoose)+trans(R.string.plz_notify)

                    + "\n" + "--" + " " + "--");
        }else {
            tvResult.setText(trans(R.string.dataFilter_YouChoose)+trans(R.string.plz_notify)
                    + "\n" + getSelectedDate + " " + getSelectedTime+method);
        }
    }

    //==============================分隔線(編號篩選)

    /**
     * 編號的選擇介面
     */
    public void IdSelect(LinearLayout liIDSelecter, String selectType) {
        this.selectType = selectType;
        this.liIDSelecter = liIDSelecter;
        getSQLite();
        tvIdResult = activity.findViewById(R.id.dataFilter_IdResult);
        btGoSearch.setVisibility(View.VISIBLE);
        liIDSelecter.setVisibility(View.VISIBLE);
        numberPickerStart = activity.findViewById(R.id.numberPickerStart);
        numberPickerEnd = activity.findViewById(R.id.numberPickerEnd);
        numberPickerStart.setMinValue(Integer.parseInt(LastID));
        numberPickerStart.setMaxValue(Integer.parseInt(FirstID));
        numberPickerEnd.setMinValue(Integer.parseInt(LastID));
        numberPickerEnd.setMaxValue(Integer.parseInt(FirstID));

        numberPickerStart.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                startOld = oldVal;
                startNew = newVal;
                if (newVal > endOld) {
                    numberPickerEnd.setMinValue(newVal);
                    tvIdResult.setText(trans(R.string.dataFilter_YouChoose) + "\n"
                            + newVal + "~" + newVal);
                    selectedFirID = String.valueOf(newVal);
                    selectedSecID = String.valueOf(newVal);
                } else {
                    numberPickerEnd.setMinValue(newVal);
                    numberPickerEnd.setMaxValue(Integer.parseInt(FirstID));
                    tvIdResult.setText(trans(R.string.dataFilter_YouChoose) + "\n"
                            + newVal + "~" + endNew);
                    selectedFirID = String.valueOf(newVal);
                    selectedSecID = String.valueOf(endNew);
                }


            }
        });
        numberPickerEnd.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                endOld = oldVal;
                endNew = newVal;
                if (startOld < newVal) {
                    numberPickerStart.setMaxValue(newVal);
                } else {
                    numberPickerStart.setMinValue(Integer.parseInt(LastID));
                    numberPickerStart.setMaxValue(Integer.parseInt(FirstID));
                }
                tvIdResult.setText(trans(R.string.dataFilter_YouChoose) + "\n"
                        + startNew + "~" + newVal);
                selectedFirID = String.valueOf(startNew);
                selectedSecID = String.valueOf(newVal);
            }
        });
        buttonClickData();

    }

    //==============================分隔線(其他數值篩選)
    public void valueSelect(LinearLayout liValueSelect, String selectType) {
        this.selectType = selectType;

        EditText editText = activity.findViewById(R.id.dataFilter_EditInput);
        TextView tvValueRe = activity.findViewById(R.id.dataFilter_dataResult);
        NumberPicker numberPickerMath = activity.findViewById(R.id.numberPickerValueSelect);
        liValueSelect.setVisibility(View.VISIBLE);
        btGoSearch.setVisibility(View.VISIBLE);

        final String[] mathMethod = activity.getResources().getStringArray(R.array.meth_Function_time);
        numberPickerMath.setMinValue(0);
        numberPickerMath.setValue(2);
        numberPickerMath.setMaxValue(mathMethod.length - 1);
        numberPickerMath.setDisplayedValues(mathMethod);
        numberPickerMath.setWrapSelectorWheel(false);
        numberPickerMath.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        if (selectType.contains(trans(R.string.Temperature))) {
            editText.setHint("-10~65");
            mySelect(editText, numberPickerMath, tvValueRe);
        } else if (selectType.contains(trans(R.string.Humidity))) {
            editText.setHint("0~100");
            mySelect(editText, numberPickerMath, tvValueRe);
        }else if (selectType.contains(trans(R.string.CO2))){
            editText.setHint("0~5000");//幹...懶得寫詳細了
            mySelect(editText, numberPickerMath, tvValueRe);
        }else if(selectType.contains("第一排")
                ||selectType.contains("第二排")
                ||selectType.contains("第三排")){
            editText.setHint("-999~9999");
            mySelect(editText, numberPickerMath, tvValueRe);
        }

    }//end

    /**
     * 選擇篩選溫濕度
     */
    private void mySelect(final EditText editText, NumberPicker numberPickerDataSelect, final TextView tvValueResult) {
        this.editText = editText;
        buttonClickData();
        numberPickerDataSelect.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                switch (newVal) {
                    case 0:
                        NpMathFunction = newVal;
                        tvValueResult.setText(trans(R.string.dataFilter_YouChoose)
                                + trans(R.string.BBBBBig) + editText.getText().toString());
                        break;
                    case 1:
                        NpMathFunction = newVal;
                        tvValueResult.setText(trans(R.string.dataFilter_YouChoose)
                                + trans(R.string.SSSSmail) + editText.getText().toString());
                        break;
                    case 2:
                        NpMathFunction = newVal;
                        tvValueResult.setText(trans(R.string.dataFilter_YouChoose)
                                + trans(R.string.equal) + editText.getText().toString());
                        break;

                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER);
                if (editText.getHint().toString().contains("-10~65")) {//溫度
                    try {
                        if (Double.valueOf(String.valueOf(s)) < -10.0) {
                            editText.setText("-10");
                        } else if (Double.valueOf(String.valueOf(s)) > 65.0) {
                            editText.setText("65");
                        } else {
                            setTextFunction(tvValueResult, s);
                        }
                    } catch (Exception e) {
                        tvValueResult.setText(trans(R.string.dataFilter_YouChoose) + "--");
                    }

                } else if (editText.getHint().toString().contains("0~100")) {//濕度
                    try {
                        if (Double.valueOf(String.valueOf(s)) < 0) {
                            editText.setText("0");
                        } else if (Double.valueOf(String.valueOf(s)) > 100) {
                            editText.setText("100");
                        } else {
                            setTextFunction(tvValueResult, s);
                        }
                    } catch (Exception e) {
                        tvValueResult.setText(trans(R.string.dataFilter_YouChoose) + "--");
                    }

                }else if (editText.getHint().toString().contains("0~5000")){//CO2
                    try {
                        if (Double.valueOf(String.valueOf(s)) < 0) {
                            editText.setText("0");
                        } else if (Double.valueOf(String.valueOf(s)) > 5000) {
                            editText.setText("5000");
                        } else {
                            setTextFunction(tvValueResult, s);
                        }
                    } catch (Exception e) {
                        tvValueResult.setText(trans(R.string.dataFilter_YouChoose) + "--");
                    }
                }
                else if (editText.getHint().toString().contains("-999~9999")){//I
                    try {
                        if (Double.valueOf(String.valueOf(s)) < -999) {
                            editText.setText("-999");
                        } else if (Double.valueOf(String.valueOf(s)) > 9999) {
                            editText.setText("9999");
                        } else {
                            setTextFunction(tvValueResult, s);
                        }
                    } catch (Exception e) {
                        tvValueResult.setText(trans(R.string.dataFilter_YouChoose) + "--");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setTextFunction(TextView tvValueResult, CharSequence s) {
        switch (NpMathFunction) {
            case 0:
                tvValueResult.setText(trans(R.string.dataFilter_YouChoose)
                        + trans(R.string.BBBBBig) + s);
                break;
            case 1:
                tvValueResult.setText(trans(R.string.dataFilter_YouChoose)
                        + trans(R.string.SSSSmail) + s);
                break;
            case 2:
                tvValueResult.setText(trans(R.string.dataFilter_YouChoose)
                        + trans(R.string.equal) + s);
                break;
            default:
                tvValueResult.setText(trans(R.string.dataFilter_YouChoose)
                        + trans(R.string.equal) + "--");
                break;

        }

    }


    //==============================分隔線

    /**
     * 簡化字串轉換
     */
    private String trans(int name) {
        String str = context.getResources().getString(name);
        return str;
    }

    /**
     * 取得時間日期的最大值與最小值
     */
    private void trans2Date() {

        f_YEAR = Integer.parseInt(FirstDateRecord.substring(0, FirstDateRecord.indexOf("-")));
        f_MONTH = Integer.parseInt(FirstDateRecord.substring(5, FirstDateRecord.lastIndexOf("-")));
        f_DAY = Integer.parseInt(FirstDateRecord.substring(8));
        l_YEAR = Integer.parseInt(LastDateRecord.substring(0, LastDateRecord.indexOf("-")));
        l_MONTH = Integer.parseInt(LastDateRecord.substring(5, LastDateRecord.lastIndexOf("-")));
        l_DAY = Integer.parseInt(LastDateRecord.substring(8));

//        Log.v("BT",l_YEAR+" "+l_MONTH+" "+l_DAY+"大的");
//        Log.v("BT",f_YEAR+" "+f_MONTH+" "+f_DAY+"小的");
    }

    //==============================分隔線(按鈕送出事件)
    /**按鈕按下後的篩選*/
    private void buttonClickData() {
        btGoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadDataFilteredActivity.mSelectType = selectType;
                if (selectType.contains(trans(R.string.dataFilter_plzSelectDateAndTime))) {
                    DateTimeSelect();
                } else if (selectType.contains(trans(R.string.dataFilter_plzSelectId))) {
                    IDSelect();
                } else  {
                    elseDataSelect();
                }

            }
        });
    }

    /**
     * 其他數值篩選送出
     */
    private void elseDataSelect() {
        //要傳的資料為>=<以及數值
//        Log.v("BT", String.valueOf(NpMathFunction));
        try{
            Intent intent = new Intent(activity,DownloadDataFilteredActivity.class);
            Bundle bundle = new Bundle();
            bundle.putDouble("Value",Double.valueOf(editText.getText().toString()));
            bundle.putInt("Method",NpMathFunction);
            intent.putExtras(bundle);
            activity.startActivity(intent);
//            activity.finish();
        }catch (Exception e){
            Toast.makeText(context, R.string.dont_blank, Toast.LENGTH_LONG).show();
        }

    }

    /**
     * ID篩選送出
     */
    private void IDSelect() {
        if (selectType.contains(trans(R.string.dataFilter_plzSelectId))) {
            if (selectedFirID == null || selectedSecID == null) {
                Toast.makeText(context, R.string.dont_blank, Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(activity, DownloadDataFilteredActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("FirstId", selectedFirID);
                bundle.putString("SecondId", selectedSecID);
                intent.putExtras(bundle);
                activity.startActivity(intent);
//                activity.finish();
            }
        }
    }

    /**日期時間篩選送出*/
    private void DateTimeSelect(){
//        Log.v("BT", String.valueOf(numberPicker.getValue()));
        try{
            Intent intent = new Intent(activity,DownloadDataFilteredActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("dtMethod",numberPicker.getValue());
            bundle.putString("getD",getSelectedDate);
            bundle.putString("getT",getSelectedTime);
            intent.putExtras(bundle);
            if(getSelectedDate == null || getSelectedTime == null){
                Toast.makeText(context,R.string.dataFilter_plzInputDT＿dontblank,Toast.LENGTH_LONG).show();
            }else
                activity.startActivity(intent);

        }catch(Exception x){
            Log.v("BT","爆了");
        }

    }





}
