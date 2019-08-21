package com.example.jetecpro_ver1.MainProcess;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.jetecpro_ver1.BLE_function.BluetoothLeService;
import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.Values.SendType;
import com.example.jetecpro_ver1.Values.SortData;

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


        /**從類別取得資料*/
        SortData sortData = new SortData(SendType.DeviceType);
        String[] at =  sortData.TH();
        String[] bt = sortData.TH_2();
        SimpleListView = findViewById(R.id.listView);
        final ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        for (int i = 0; i < at.length; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", at[i]);
            hashMap.put("values",bt[i]);
            arrayList.add(hashMap);
        }
        String[] from = {"name", "values"};

        int[] to = {R.id.TitleName, R.id.ResultValue};
        simpleAdapter =
                new SimpleAdapter(this, arrayList, R.layout.style_listview, from, to);
        SimpleListView.setAdapter(simpleAdapter);

    }










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
