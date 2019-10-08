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
import android.view.MenuItem;
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
import com.example.jetecpro_ver1.Values.SendType;

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
//        textView.setGravity(Gravity.CENTER);//文字置中
        textView.setTextColor(Color.WHITE);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(textView);
        getActionBar().setHomeAsUpIndicator(R.drawable.ic_navigate_before_black_24dp);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void spinnerSelect() {
        spinner = findViewById(R.id.spinner);
        btGoSearch = findViewById(R.id.buttonGoFilter);
        liDTSelecter = findViewById(R.id.LayoutDateTimeSelecter);
        liIdSelecter = findViewById(R.id.LayoutIdSelecter);
        liElseData = findViewById(R.id.LayoutElseData);

        final String[] selectArray;
        switch (SendType.row){
            case '1':
                selectArray = new String[]{trans(R.string.dataFilter_plzSelect), trans(R.string.dataFilter_plzSelectDateAndTime)
                        ,trans(R.string.dataFilter_plzSelectId)
                        ,Lab(SendType.FirstWord,1)};
                break;
            case '2':
                selectArray = new String[]{trans(R.string.dataFilter_plzSelect), trans(R.string.dataFilter_plzSelectDateAndTime)
                        ,trans(R.string.dataFilter_plzSelectId)
                        ,Lab(SendType.FirstWord,1)
                        ,Lab(SendType.SecondWord,2)};
                break;
            case '3':
                selectArray = new String[]{trans(R.string.dataFilter_plzSelect), trans(R.string.dataFilter_plzSelectDateAndTime)
                        ,trans(R.string.dataFilter_plzSelectId)
                        ,Lab(SendType.FirstWord,1)
                        ,Lab(SendType.SecondWord,2)
                        ,Lab(SendType.ThirdWord,3)};
                break;
                default:
                    selectArray = new String[]{trans(R.string.dataFilter_plzSelect)};
                    break;

        }
        
        ArrayAdapter<String> mList = new ArrayAdapter<>(DataFilterActivity.this,
                android.R.layout.simple_spinner_dropdown_item, selectArray);
        spinner.setAdapter(mList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataFilterUIControl dataFilterUIControl = new DataFilterUIControl(getBaseContext(),btGoSearch,DataFilterActivity.this);
                if (selectArray[position].contains(trans(R.string.dataFilter_plzSelectDateAndTime))){//選時間日期
                    setAllViewGONE();
                    dataFilterUIControl.DateTimeSelect(liDTSelecter,selectArray[position]);
                } else if (selectArray[position].contains(trans(R.string.dataFilter_plzSelectId))) {//選ID
                    setAllViewGONE();
                    dataFilterUIControl.IdSelect(liIdSelecter,selectArray[position]);
                }  else if(selectArray[position].contains(trans(R.string.dataFilter_plzSelect))){//選“請選擇項目”
                    setAllViewGONE();
                }
                else {
                    setAllViewGONE();
                    dataFilterUIControl.valueSelect(liElseData,selectArray[position]);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                    finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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
}
