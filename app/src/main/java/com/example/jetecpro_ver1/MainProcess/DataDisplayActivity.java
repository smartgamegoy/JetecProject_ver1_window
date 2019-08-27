package com.example.jetecpro_ver1.MainProcess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.Toast;


import com.example.jetecpro_ver1.BLE_function.BluetoothLeService;
import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.SendData.DDA_SendData;
import com.example.jetecpro_ver1.SendData.GetDisplayData;
import com.example.jetecpro_ver1.Values.SendType;
import com.example.jetecpro_ver1.SendData.SortData;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.HashMap;

public class DataDisplayActivity extends Activity {
    private BluetoothLeService mBluetoothLeService;
    private boolean mConnected = true;
    ListView SimpleListView;
    private SimpleAdapter simpleAdapter;
    private DrawerLayout drawerLayout;
    public static Activity DisplayData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);
        DisplayData = this;
        //使用BluetoothLeService的Service功能
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

        //取得Action Bar 抬頭顯示
        getActionBar().setTitle(SendType.DeviceName);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        //載入ListView
        displayListView();
        //DrawerLayout內的活動
        setDrawerFunction();
        Stetho.initializeWithDefaults(this);




    }
    /**取得所有Drawer內功能*/
    private void setDrawerFunction(){
        drawerLayout            = (DrawerLayout) findViewById(R.id.drawerLayout);
        Button btSave           = (Button) findViewById(R.id.Go_saveData);
        Button btLoad           = (Button) findViewById(R.id.Go_LoadData);
        Button btDownloadData   = (Button) findViewById(R.id.Go_DownloadData);
        Button btChart          = (Button) findViewById(R.id.Go_Chart);
        Button btModifyPSW      = (Button) findViewById(R.id.Go_ModifyPSW);
        Button btRecord         = (Button) findViewById(R.id.Go_Record);
        Button btStartRecord    = (Button) findViewById(R.id.Go_StartRecord);
        btSave.setOnClickListener(mListener);
        btLoad.setOnClickListener(mListener);
        btDownloadData.setOnClickListener(mListener);
        btChart.setOnClickListener(mListener);
        btModifyPSW.setOnClickListener(mListener);
        btRecord.setOnClickListener(mListener);
        btStartRecord.setOnClickListener(mListener);
    }

    /**分類按鈕點擊事件*/
    private Button.OnClickListener mListener = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(DataDisplayActivity.this);
            View view2;
            View view ;
            switch (v.getId()){
                case R.id.Go_saveData:
                    view = getLayoutInflater().inflate(R.layout.activity_data_display_save_setting_dialog,null);
                    mBuilder.setView(view);
                    DrawerFunction drawerFunction = new DrawerFunction(getBaseContext(),view,mBuilder);
                    drawerFunction.SaveFunction();
                    break;
                case R.id.Go_LoadData:
                    view = getLayoutInflater().inflate(R.layout.activity_data_display_load_setting_dialog,null);
                    DrawerFunction drawerFunctionLoad = new DrawerFunction(getBaseContext(),view,mBuilder);
                    drawerFunctionLoad.LoadFunction();

                    break;
                case R.id.Go_DownloadData:

                    break;
                case R.id.Go_Chart:

                    break;
                case R.id.Go_ModifyPSW:

                    break;
                case R.id.Go_Record:

                    break;
                case R.id.Go_StartRecord:

                    break;
            }
            drawerLayout.closeDrawers();

        }
    };
    /**獲取返回鍵活動*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            drawerLayout.closeDrawers();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**載入ListView功能*/
    private void displayListView(){//設置ListView內容
        /**從類別取得資料*/
        SortData sortData = new SortData(SendType.DeviceType,getBaseContext());
        String[] nameItems =  sortData.getNames();
        String[] valuesItems = sortData.getValues();
        SimpleListView = findViewById(R.id.listView);
        final ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        for (int i = 0; i < nameItems.length; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", nameItems[i]);
            hashMap.put("values",valuesItems[i]);
            arrayList.add(hashMap);
        }
        String[] from = {"name", "values"};

        int[] to = {R.id.TitleName, R.id.ResultValue};
        simpleAdapter =
                new SimpleAdapter(this, arrayList, R.layout.activity_data_display_style_listview, from, to);
        SimpleListView.setAdapter(simpleAdapter);
        SimpleListView.setOnItemClickListener(onItemClickListener);

    }

    /**設置ListView點擊活動*/
    private AdapterView.OnItemClickListener onItemClickListener = (new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SortData sortData = new SortData(SendType.DeviceType,getBaseContext());
            String[] nameItems =  sortData.getNames();
            String[] valueItems = sortData.getValues();
            String GetName = nameItems[position];
            String GetValues = valueItems[position];
            View v = null;
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(DataDisplayActivity.this);

            if (GetName.contains(SendType.SPK)){
                 v = getLayoutInflater().inflate(R.layout.activity_data_display_switch_dialog,null);
            }else if(SendType.FirstWord == 'I'
                        ||SendType.SecondWord == 'I'
                        ||SendType.ThirdWord  == 'I'){
                if (GetName.contains(SendType.DP1) 
                        ||GetName.contains(SendType.DP2) 
                        ||GetName.contains(SendType.DP2)){
                    v = getLayoutInflater().inflate(R.layout.activity_data_display_switch_dialog,null);
                }
                
            }else if(SendType.ThirdWord == 'L' && GetName.contains(SendType.INTER)){
                    v = getLayoutInflater().inflate(R.layout.activity_data_display_number_picker_function_dialog,null);
                    mBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) { }});  }

            else{
                v  = getLayoutInflater().inflate(R.layout.activity_data_display_input_modify_data_dialog,null);
                mBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) { }});
            }

            final EditText edInput = (EditText) v.findViewById(R.id.editValueInput);
            final Switch   swInput = (Switch)   v.findViewById(R.id.switch1);
            final NumberPicker npHour = (NumberPicker) v.findViewById(R.id.hourPic);
            final NumberPicker npMin  = (NumberPicker) v.findViewById(R.id.minPic);
            final NumberPicker npSec  = (NumberPicker) v.findViewById(R.id.secPick);


                mBuilder.setTitle(GetName);
                mBuilder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) { }});
                mBuilder.setView(v);
                DDA_SendData dda_sendData = new DDA_SendData(GetName,GetValues,edInput,swInput,getBaseContext()
                        ,npHour,npMin,npSec);
                dda_sendData.mAlertDialog(mBuilder);
        }
    });




    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(SendType.DeviceAddress);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gatt_services, menu);
        if (mConnected) {

            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);

        } else {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
        }
        return true;
    }

    /**設置Action Bar的活動*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_connect:
                mBluetoothLeService.connect(SendType.DeviceAddress);
                Toast.makeText(getBaseContext(), "連接裝置", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_disconnect:
                mBluetoothLeService.disconnect();
                Toast.makeText(getBaseContext(), "斷開裝置，請重新連接裝置 ", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }//onOptionsSelected

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
                //如果有連接
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                invalidateOptionsMenu();
            }
            //如果沒有連接
            else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                invalidateOptionsMenu();
            }
            //如果找到GATT服務
            else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
            }
            //接收來自藍芽傳回的資料
            else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                /**接收來自Service的訊息*/
                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };//onReceive
    /**管理來自藍芽的資料*/
    private void displayData(String data) {
        GetDisplayData display = new GetDisplayData(data);
        String getMain = data.substring(0, 3);
        display.analysisData(getMain);
        displayListView();
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                finish();
            }

            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(SendType.DeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService.connect(SendType.DeviceAddress);
        }
    };//serviceConnection

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);//連接一個GATT服務
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);//從GATT服務中斷開連接
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);//查找GATT服務
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);//從服務中接受(收?)數據
        return intentFilter;
    }

    /**這是來自DrawerFunction.java的副程式，在類別裡多寫一個AlertDialog問題太多了==*/
    public void  getModifySQLiteFunctionViewFromDrawerFunction(final SQLiteDatabase mCustomDb, final String DB_TABLE,
                                                               final int GET_ITEM_POSITION, final ListView listView
                                                                , final View origonView, final Context context
    , final AlertDialog.Builder mBuilder){
        LayoutInflater layoutInflater = LayoutInflater.from(DataDisplayActivity.DisplayData);
        View v = layoutInflater.inflate(R.layout.dialog_input_modift_function,null);
        final AlertDialog.Builder modifyDialog = new AlertDialog.Builder(DataDisplayActivity.DisplayData);
        final EditText edModify = (EditText) v.findViewById(R.id.editTextINput);
        modifyDialog.setView(v);
        modifyDialog.setTitle(R.string.plzInputModifyName);
        final Cursor c = mCustomDb.rawQuery("SELECT *  FROM " + DB_TABLE + " WHERE _id=" + GET_ITEM_POSITION, null);
        while (c.moveToNext()) {
            edModify.setText(c.getString(1));
        }
        modifyDialog.setPositiveButton(R.string.modify, new DialogInterface.OnClickListener() {@Override public void onClick(DialogInterface dialog, int which) {}});
        modifyDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {@Override public void onClick(DialogInterface dialog, int which) {}});
        final AlertDialog dialog = modifyDialog.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edModify.getText().toString().length()>0){
                    ContentValues values = new ContentValues();
                    values.put("name",edModify.getText().toString().trim());
                    mCustomDb.update(DB_TABLE, values,"_id="+ GET_ITEM_POSITION,null);
                    //UPDATE BT2TH SET  WHERE
                    //UPDATE "表格" SET "欄位1" = [值1], "欄位2" = [值2]WHERE "條件";
                    DrawerFunction drawerFunction = new DrawerFunction(context,origonView,mBuilder);
                    drawerFunction.setListView(listView);

                    dialog.dismiss();
                }else{
                    Toast.makeText(DataDisplayActivity.DisplayData,R.string.Dont_blank1,Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}