package com.example.jetecpro_ver1.AllOfNewMonitor.Controll;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Canvas;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jetecpro_ver1.AllOfNewMonitor.Model.DCA_DeviceControlActivitySupport.NewSupportDCARecycleViewTypeChooser;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NDD_NewDataDisplaySupport.NewSupportNDDPagerAdapter;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NewDeviceInitialzation;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NewSendType;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.Pagers.FragmentList_One;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.Pagers.FragmentList_Three;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.Pagers.FragmentList_Twp;
import com.example.jetecpro_ver1.BLE_function.BluetoothLeService;
import com.example.jetecpro_ver1.MainProcess.DeviceControlActivity;
import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.Values.SendType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class NewDataDisplay extends Activity {
    private String TAG = NewDataDisplay.class.getSimpleName()+"My";
    private BluetoothLeService mBluetoothLeService;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private ArrayList<View> mPages;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_data_display);
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_view);
        setButtonEvent();//設置按鈕事件
        setPager();//設置分頁
        findViewById(R.id.button_NDD_disconnect).setOnClickListener((view)->{finish();});
        Button btTypeselecter =  findViewById(R.id.button_NDD_ENsetType);
        Button btcalibration =findViewById(R.id.button_NDD_calibration);
        if (NewSendType.engineerMode == false){
            btcalibration.setVisibility(View.GONE);
            btTypeselecter.setVisibility(View.GONE);
        }else{
            btcalibration.setVisibility(View.VISIBLE);
            btTypeselecter.setVisibility(View.VISIBLE);
        }

        btcalibration.setOnClickListener((v -> {}));
        btTypeselecter.setOnClickListener((v -> {setNewMonitorTypeSetter();}));
    }
    /**設置按鈕事件*/
    private void setButtonEvent() {
        bottomNavigationView.getMenu().setGroupCheckable(0,false,false);
        if (NewSendType.logSwitch == false){//開啟or關閉紀錄系列功能
            bottomNavigationView.getMenu().getItem(4).setEnabled(false);
            bottomNavigationView.getMenu().getItem(3).setEnabled(false);

        }
        bottomNavigationView.setOnNavigationItemSelectedListener((item)->{
            switch (item.getItemId()){
                case R.id.button_NDD_Save:

                    break;
                case R.id.button_NDD_Export:

                    break;
                case R.id.button_NDD_modifyPasswd:

                    break;
                case R.id.button_NDD_startlog:

                    break;
                case R.id.button_NDD_chart:

                    break;

            }
            return true;
        });
    }

    /**設置分頁*/
    private void setPager() {
        mPages = new ArrayList<>();
        mPages.add(new FragmentList_One(this));//預設首
        mPages.add(new FragmentList_One(this));//預設尾
        int tabCount = NewSendType.row-getMaches(NewSendType.newDeviceType, "Y")-getMaches(NewSendType.newDeviceType, "Z");

        for (int i=0;i< tabCount;i++){
            mPages.add(new FragmentList_One(this));
        }
        NewSupportNDDPagerAdapter a= new NewSupportNDDPagerAdapter(mPages,getBaseContext());
        mViewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(a);
    }
    private int getMaches(String str,String substr){
        int count = 0;//count用來接收子字串substr在字串str中出現的次數
        //使用for迴圈從字串的0位置開始迴圈擷取和子字串長度相同的字串；
        //然後判斷擷取的字串是否和子字串substr相同，若相同則count加一。
        for(int i=0;i<str.length()+1-substr.length();i++) {
            if(str.substring(i, substr.length()+i).equals(substr)) {
                count++;
            }
        }
        return count;
    }

    /**
     * ============================================藍芽基礎配置↓=======================================
     */
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
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(SendType.DeviceAddress);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBluetoothLeService.disconnect();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
        unregisterReceiver(mGattUpdateReceiver);
    }
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            //如果有連接
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
            }
            //如果沒有連接
            else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
            }
            //如果找到GATT服務
            else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
            }
            //接收來自藍芽傳回的資料
            else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                /**接收來自Service的訊息*/


            }
        }
    };//onReceive
    /**
     * ============================================藍芽基礎配置↑=======================================
     */

    /**
     * ============================================工程師模式↓=======================================
     */
    /**
     * 設定組合大顯型號
     */
    private void setNewMonitorTypeSetter() {
        AlertDialog.Builder sendType = new AlertDialog.Builder(NewDataDisplay.this);
        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.new_dialog_type_chooser, null);
        sendType.setView(view);
        AlertDialog dialog = sendType.create();
        RecyclerView recyclerView = view.findViewById(R.id.recycleView_DCA_dialog_getType);
        Button btAdd = view.findViewById(R.id.button_DCA_dialog_add);
        Button btSendOut = view.findViewById(R.id.button_DCA_dialog_sendOut);
        Button btClear = view.findViewById(R.id.button_DCA_dialog_clear);
        Button btCancel = view.findViewById(R.id.button_DCA_dialog_cancel);
        ArrayList<String> mChoose = NewSupportDCARecycleViewTypeChooser.mSelectType;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this
                , DividerItemDecoration.VERTICAL));
        NewSupportDCARecycleViewTypeChooser mAdapter = new NewSupportDCARecycleViewTypeChooser();

        btSendOut.setOnClickListener((v -> {

            if (mChoose.size() > 0) {
                ProgressDialog progressDialog = ProgressDialog.show(NewDataDisplay.this
                        , getResources().getString(R.string.progressing)
                        , getResources().getString(R.string.plzWait), false);
                new Thread(() -> {
                    int row = mChoose.size();
                    if (mChoose.contains("L")){
                        row--;
                    }

                    String defaultName = "BT-" + row + "-";
                    String getType;
                    switch (mChoose.size()) {
                        case 1:
                            getType = mChoose.get(0);
                            break;
                        case 2:
                            getType = mChoose.get(0) + mChoose.get(1);
                            break;
                        case 3:
                            getType = mChoose.get(0) + mChoose.get(1)
                                    + mChoose.get(2);
                            break;
                        case 4:
                            getType = mChoose.get(0) + mChoose.get(1)
                                    + mChoose.get(2) + mChoose.get(3);
                            break;
                        case 5:
                            getType = mChoose.get(0) + mChoose.get(1)
                                    + mChoose.get(2) + mChoose.get(3) + mChoose.get(4);
                            break;
                        case 6:
                            getType = mChoose.get(0) + mChoose.get(1)
                                    + mChoose.get(2) + mChoose.get(3) + mChoose.get(4)
                                    + mChoose.get(5);
                            break;
                        case 7:
                            getType = mChoose.get(0) + mChoose.get(1)
                                    + mChoose.get(2) + mChoose.get(3) + mChoose.get(4)
                                    + mChoose.get(5) +mChoose.get(6);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + mChoose.size());
                    }
                    NewDeviceInitialzation d = new NewDeviceInitialzation();
                    d.inItalzation(defaultName,getType,"-N",mChoose);
                    mChoose.clear();

                    runOnUiThread(()->{
                        mAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                        finish();
                    });
                }).start();

            }

        }));

        btCancel.setOnClickListener((v) -> {
            dialog.dismiss();
            mChoose.clear();
        });
        btClear.setOnClickListener((v -> {
            mChoose.clear();
            mAdapter.notifyDataSetChanged();
        }));
        setAddFunction(view, btAdd, mChoose, mAdapter);//增加型號的按鈕
        makeRecycleAnimate(recyclerView, mChoose, mAdapter);
        recyclerView.setAdapter(mAdapter);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }

    /**
     * 處理RecycleView內部的排序以及左滑刪除
     */
    private void makeRecycleAnimate(RecyclerView recyclerView, ArrayList<String> mChoose, NewSupportDCARecycleViewTypeChooser mAdapter) {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, 0) {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.LEFT;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView
                    , @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {

                int position_dragged = dragged.getAdapterPosition();
                int position_target = target.getAdapterPosition();
                Collections.swap(mChoose, position_dragged, position_target);
                mAdapter.notifyItemMoved(position_dragged, position_target);
                if (mChoose.contains("L")&&mChoose.indexOf("L") !=mChoose.size()-1){
                    Collections.swap(mChoose,position_target,position_dragged);
                    mAdapter.notifyItemMoved(position_target,position_dragged);
                }

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        mChoose.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        break;
                }

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(NewDataDisplay.this, R.color.redGINSYU))
                        .addActionIcon(R.drawable.ic_delete_black_24dp)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }

    /**
     * 增加型號的按鈕
     */
    private void setAddFunction(View view, Button btAdd, ArrayList<String> mChoose, NewSupportDCARecycleViewTypeChooser mAdapter) {
        btAdd.setOnClickListener((v -> {
            AlertDialog.Builder mAdd = new AlertDialog.Builder(NewDataDisplay.this);
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            double weight = metrics.widthPixels * 0.7;
            double height = metrics.heightPixels * 0.6;
            View mV = LayoutInflater.from(view.getContext()).inflate(R.layout.new_dialog_type_chooser_item, null);
            mAdd.setView(mV);
            AlertDialog alertDialog = mAdd.create();
            ListView mListView = mV.findViewById(R.id.listView_DSA_dialog_items);
            String[] ableSetType = {"T", "H", "C", "D", "E", "M", "O", "P", "Q", "I", "R", "Y", "Z", "L"};
            ArrayAdapter adapter = new ArrayAdapter(mV.getContext(), android.R.layout.simple_list_item_1, ableSetType);
            mListView.setAdapter(adapter);
            alertDialog.show();
            alertDialog.getWindow().setLayout((int) weight, ViewGroup.LayoutParams.WRAP_CONTENT);
            mListView.setOnItemClickListener(((parent, view1, position, id) -> {
                if (mChoose.size() < 6 ) {//最多就六個不能再多
                    String getType = ableSetType[position];
                    if(mChoose.indexOf("L")==-1){//如果還沒有Ｌ
                        mChoose.add(getType);
                        mAdapter.notifyDataSetChanged();
                        getYZ(mChoose, mAdapter, getType);//處理Y,Z的憲制問題
                    }else {//有L
                        mChoose.add(getType);
                        Collections.swap(mChoose,mChoose.size()-1,mChoose.size()-2);
                        mAdapter.notifyDataSetChanged();
                        if (mChoose.indexOf("L")>0&& getType.contains("L")){//L重複
                            mChoose.remove(mChoose.indexOf("L"));
                            mAdapter.notifyItemRemoved(mChoose.indexOf("L"));
                        }
                        getYZ(mChoose, mAdapter, getType);//處理Y,Z的憲制問題
                    }//else
                    if (mChoose.indexOf("L")==0){//L在第一個
                        mChoose.remove(0);
                        mAdapter.notifyItemRemoved(0);
                    }
                } else {
                    if (ableSetType[position].contains("L")&&!mChoose.contains("L")){
                        mChoose.add("L");
                        mAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(mV.getContext(), "不可以再新增了", Toast.LENGTH_SHORT).show();
                    }

                }
            }));
        }));
    }
    /**處理Y,Z限制問題*/
    private void getYZ(ArrayList<String> mChoose, NewSupportDCARecycleViewTypeChooser mAdapter, String getType) {
        /*規則
         * 有Y就不可有Z
         * 有Z就不可有Y
         * Z只能出現一次
         * Y只能出現兩次*/
        if (getType.contains("Y") && mChoose.contains("Z")) {
            mChoose.remove(mChoose.indexOf("Y"));
            mAdapter.notifyItemRemoved(mChoose.indexOf("Y"));
        } else if (getType.contains("Z") && mChoose.contains("Y")) {
            mChoose.remove(mChoose.indexOf("Z"));
            mAdapter.notifyItemRemoved(mChoose.indexOf("Z"));
        }
        if (Collections.frequency(mChoose, "Y") > 2) {
            mChoose.remove(mChoose.indexOf("Y"));
            mAdapter.notifyItemRemoved(mChoose.indexOf("Y"));
        }
        if (Collections.frequency(mChoose, "Z") > 1) {
            mChoose.remove(mChoose.indexOf("Z"));
            mAdapter.notifyItemRemoved(mChoose.indexOf("Z"));
        }
    }
/**
 * ============================================工程師模式↑=======================================
 */


}
