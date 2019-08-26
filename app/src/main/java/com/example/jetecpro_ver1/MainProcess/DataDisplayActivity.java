package com.example.jetecpro_ver1.MainProcess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.HashMap;

public class DataDisplayActivity extends Activity {
    private BluetoothLeService mBluetoothLeService;
    private boolean mConnected = true;
    ListView SimpleListView;
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        getActionBar().setTitle(SendType.DeviceName);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        displayListView();

    }


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
                Log.v("BT","跑警報");
            }else if(SendType.FirstWord == 'I'
                        ||SendType.SecondWord == 'I'
                        ||SendType.ThirdWord  == 'I'){
                if (GetName.contains(SendType.DP1) 
                        ||GetName.contains(SendType.DP2) 
                        ||GetName.contains(SendType.DP2)){
                    v = getLayoutInflater().inflate(R.layout.activity_data_display_switch_dialog,null);
                }
                
            }else if(SendType.ThirdWord == 'L' && GetName.contains(SendType.INTER)){
                    v = getLayoutInflater().inflate(R.layout.activity_data_display_record_function_dialog,null);
                    mBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) { }});  }

            else{
                v  = getLayoutInflater().inflate(R.layout.activity_data_display_input_modify_data_dialog,null);
                Log.v("BT","其他選項");
                mBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) { }});
            }

            final EditText edInput = (EditText) v.findViewById(R.id.editValueInput);
            final Switch   swInput = (Switch)   v.findViewById(R.id.switch1);
            final EditText edHour  = (EditText) v.findViewById(R.id.editTextHr);
            final EditText edMin   = (EditText) v.findViewById(R.id.editTextMin);
            final EditText edSec   = (EditText) v.findViewById(R.id.editTextSec);
            final NumberPicker npHour = (NumberPicker) v.findViewById(R.id.hourPic);
            final NumberPicker npMin  = (NumberPicker) v.findViewById(R.id.minPic);
            final NumberPicker npSec  = (NumberPicker) v.findViewById(R.id.secPick);


                mBuilder.setTitle(GetName);
                mBuilder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) { }});
                mBuilder.setView(v);
                DDA_SendData dda_sendData = new DDA_SendData(GetName,GetValues,edInput,swInput,getBaseContext()
                                                            ,edHour,edMin,edSec
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
                //drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }//onOptionsSelected

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                invalidateOptionsMenu();

            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                invalidateOptionsMenu();

                /**接下來是重點===============*/
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {

            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                /**接收來自Service的訊息*/
                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };//onReceive

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
}
