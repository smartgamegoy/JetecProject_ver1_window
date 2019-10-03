package com.example.jetecpro_ver1.EngineerMode;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jetecpro_ver1.BLE_function.BluetoothLeService;
import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.Values.SendType;

public class EngineerMode extends Activity {
    Button btSend;
    EditText edWriteText;
    TextView tvLogMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_mode);
        getActionBar().setTitle("工程師模式");

        tvLogMessage = findViewById(R.id.tectview_Log);
        btSend = findViewById(R.id.buttonSend);
        edWriteText = findViewById(R.id.editTextWrite);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendType.SendForBLEDataType = "get";
                SendType.getSendBluetoothLeService.
                        setCharacteristicNotification(SendType.Mycharacteristic, true);
            }
        });
    }

    private final BroadcastReceiver mget = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if(BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)){
                Log.v("BT","連線中");
            }
            else if(BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)){
                Log.v("BT","斷線");
            }else if(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)){

            }else if(BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)){
                Log.v("BT",intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);//連接一個GATT服務
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);//從GATT服務中斷開連接
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);//查找GATT服務
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);//從服務中接受(收?)數據
        return intentFilter;
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mget);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
