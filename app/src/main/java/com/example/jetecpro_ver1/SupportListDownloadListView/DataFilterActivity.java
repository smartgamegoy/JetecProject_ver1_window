package com.example.jetecpro_ver1.SupportListDownloadListView;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.jetecpro_ver1.R;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DataFilterActivity extends Activity {

    Spinner spinner;
    Button btGoSearch;
    LinearLayout liDTSelecter, liIdSelecter, liElseData;
    NumberPicker numberPicker;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_filter);
        //設置ActionBar
        setActionBar();
        //設置Spinner
        spinnerSelect();


    }//onCreate

    private void setActionBar() {
        getActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.redKABA)));

        TextView textView = new TextView(this);
        textView.setText(R.string.dataFilter);
        textView.setTextSize(24);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);//文字置中
        textView.setTextColor(Color.WHITE);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(textView);
    }

    private void spinnerSelect() {
        spinner = findViewById(R.id.spinner);
        btGoSearch = findViewById(R.id.buttonGoFilter);
        liDTSelecter = findViewById(R.id.LayoutDateTimeSelecter);
        liIdSelecter = findViewById(R.id.LayoutIdSelecter);
        liElseData = findViewById(R.id.LayoutElseData);


        /**注意:這邊要做各種類裝置的分別，但是目前先只做TH*/
        final String[] selectArray = {trans(R.string.dataFilter_plzSelect)
                , trans(R.string.dataFilter_plzSelectDateAndTime)
                , trans(R.string.dataFilter_plzSelectId)
                , trans(R.string.Temperature)
                , trans(R.string.Humidity)};
        ArrayAdapter<String> mList = new ArrayAdapter<>(DataFilterActivity.this,
                android.R.layout.simple_spinner_dropdown_item, selectArray);
        spinner.setAdapter(mList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (selectArray[position].contains(trans(R.string.dataFilter_plzSelectDateAndTime))) {
                    setAllViewGONE();
                    timeDataSelectEvent();
                } else if (selectArray[position].contains(trans(R.string.dataFilter_plzSelectId))) {
                    setAllViewGONE();
                } else if (selectArray[position].contains(trans(R.string.Temperature))) {
                    setAllViewGONE();
                } else if (selectArray[position].contains(trans(R.string.Humidity))) {
                    setAllViewGONE();
                } else {
                    setAllViewGONE();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setAllViewGONE() {
        btGoSearch.setVisibility(View.GONE);
        liDTSelecter.setVisibility(View.GONE);
        liIdSelecter.setVisibility(View.GONE);
        liElseData.setVisibility(View.GONE);
    }

    /**
     * 轉換R.string.xxx
     */
    private String trans(int name) {
        String str = getBaseContext().getResources().getString(name);
        return str;
    }

    /**
     * 處理時間選擇事件
     */
    private void timeDataSelectEvent() {
        TextView tvResult = findViewById(R.id.dataFilter_textViewResult);
        Button selectDate = findViewById(R.id.dataFilter_buttonSelectDate);
        Button selectTime = findViewById(R.id.dataFilter_buttonSelectTime);
        selectDate.setOnClickListener(DateTimeFunction);
        selectTime.setOnClickListener(DateTimeFunction);
        //載入TimePicker
        timeDataSelectEvent_TimePickerSupport();
        pickerrrrrsssssss();

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            }
        });



    }

    /**
     * 協助載入TimePicker用的
     */
    private void timeDataSelectEvent_TimePickerSupport() {
        liDTSelecter.setVisibility(View.VISIBLE);
        btGoSearch.setVisibility(View.VISIBLE);
        numberPicker = findViewById(R.id.dataFilter_numberPicker);
        final String[] mathMethod = getResources().getStringArray(R.array.meth_Function_time);
        numberPicker.setMinValue(0);
        numberPicker.setValue(2);
        numberPicker.setMaxValue(mathMethod.length - 1);
        numberPicker.setDisplayedValues(mathMethod);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


    }

    private Button.OnClickListener DateTimeFunction = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dataFilter_buttonSelectDate:
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        datePickerDialog.show();
                    break;
                case R.id.dataFilter_buttonSelectTime:
                    timePickerDialog.show();

                    break;
            }
        }
    };
    private void pickerrrrrsssssss(){

        GregorianCalendar calendar = new GregorianCalendar();

        datePickerDialog = new DatePickerDialog(this ,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                DecimalFormat decimalFormat = new DecimalFormat("00");
                Log.v("BT","選擇日期為:"+decimalFormat.format(year)+decimalFormat.format(month)+decimalFormat.format(dayOfMonth));
//                Log.v("BT","選擇日期為:"+year+month+dayOfMonth);
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));


        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                DecimalFormat decimalFormat = new DecimalFormat("00");
                Log.v("BT","選擇時間為:"+decimalFormat.format(hourOfDay)+decimalFormat.format(minute));
//                Log.v("BT","選擇時間為:"+hourOfDay+minute);
            }
        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(calendar.MINUTE),false);

    }




}
