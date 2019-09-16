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
                DataFilterUIControl dataFilterUIControl = new DataFilterUIControl(getBaseContext(),btGoSearch,DataFilterActivity.this);
                if (selectArray[position].contains(trans(R.string.dataFilter_plzSelectDateAndTime))) {
                    setAllViewGONE();
                    dataFilterUIControl.DateTimeSelect(liDTSelecter);
                } else if (selectArray[position].contains(trans(R.string.dataFilter_plzSelectId))) {
                    setAllViewGONE();
                    dataFilterUIControl.IdSelect(liIdSelecter);
                } else if (selectArray[position].contains(trans(R.string.Temperature))) {
                    setAllViewGONE();
                    dataFilterUIControl.valueSelect(liElseData,selectArray[position]);
                } else if (selectArray[position].contains(trans(R.string.Humidity))) {
                    setAllViewGONE();
                    dataFilterUIControl.valueSelect(liElseData,selectArray[position]);
                } else {
                    setAllViewGONE();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    /**消失所有View*/
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

}
