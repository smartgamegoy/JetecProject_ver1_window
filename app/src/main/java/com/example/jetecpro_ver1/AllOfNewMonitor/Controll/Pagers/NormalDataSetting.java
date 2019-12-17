package com.example.jetecpro_ver1.AllOfNewMonitor.Controll.Pagers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NDD_NewDataDisplaySupport.NewSupportNDDRecyclerViewAdapter;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NDD_NewDataDisplaySupport.NewSupportSortData;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NewSendType;
import com.example.jetecpro_ver1.BLE_function.BluetoothLeService;
import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.Values.SendType;

import java.util.ArrayList;
import java.util.HashMap;

public class NormalDataSetting extends RelativeLayout {
    private String TAG = NormalDataSetting.class.getSimpleName();
    private View mView;
//    NewSupportNDDRecyclerViewAdapter mAdapter;
    MyRecyclerView mAdapter;
    Activity mActivity;
    private BluetoothLeService mBluetoothLeService;
    private ArrayList<HashMap<String,String>> displayHashMap = new ArrayList<>();

    public NormalDataSetting(Context context, HashMap<String, ArrayList<String>> getFromIntentArray
            , int area, Activity activity) {//area是該編號的位置
        super(context);
        mActivity = activity;
        displayHashMap.clear();

        LayoutInflater inflater = LayoutInflater.from(context);
        mView = inflater.inflate(R.layout.new_ndd_recycler_item_normal_setting, null);
        RecyclerView r = mView.findViewById(R.id.recyclerview_NDD_normal_data_set);
        Button btSend = mView.findViewById(R.id.button_NDD_NormalSendOut);
//        Log.d(TAG, "NormalDataSetting: "+getFromIntentArray);
        Log.d(TAG, "NormalDataSetting: (位置)"+area);
        for (int i=0;i<getFromIntentArray.get("Byte").size();i++){
            NewSupportSortData s = new NewSupportSortData(area,getFromIntentArray.get("Byte").get(i),activity);
            HashMap<String,String> hashMap = new HashMap<>();
            if (!s.ReBackTitle().matches("xx")){
                hashMap.put("Title",s.ReBackTitle());
                hashMap.put("Value",s.ReBackValues());
                displayHashMap.add(hashMap);
            }
        }

        String TypeTag = NewSendType.newDeviceType.substring(5,NewSendType.newDeviceType.lastIndexOf("-"));
        try {
            switch (TypeTag.charAt(area-1)){
                case 'T':
                case 'H':
                case 'C':
                case 'D':
                case 'E':
                case 'M':
                case 'O':
                case 'P':
                case 'Q':
                case 'I':
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("Title","小數點");
                    String getDP = "9";
                    for (int i=0;i<getFromIntentArray.get("Byte").size();i++){
                        NewSupportSortData s = new NewSupportSortData(area,getFromIntentArray.get("Byte").get(i),activity);
                        if (!s.getDP().matches("9")){
                            getDP = s.getDP();
//                            Log.d(TAG, "NormalDataSetting: "+getDP);
                        }
                    }

                    hashMap.put("Value",getDP);
                    displayHashMap.add(hashMap);
                    break;
            }
        }catch (Exception e){

        }


//        Log.d(TAG, "NormalDataSetting: "+displayHashMap);
        btSend.setOnClickListener((v -> {
            Log.d(TAG, "NormalDataSetting: "+area);
        }));
        r.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        mAdapter = new NewSupportNDDRecyclerViewAdapter(arrayList,getContext());
//        r.setAdapter(mAdapter);
        mAdapter = new MyRecyclerView();
        r.setAdapter(mAdapter);

        addView(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }

    private class MyRecyclerView extends RecyclerView.Adapter<MyRecyclerView.ViewHolder>{
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle,tvValue;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.textView_NDD_CellTitle);
                tvValue = itemView.findViewById(R.id.textView_NDD_CellValue);
            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = mActivity.getLayoutInflater().inflate(R.layout.recycleview_item,null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.tvTitle.setText(displayHashMap.get(i).get("Title"));
            viewHolder.tvValue.setText(displayHashMap.get(i).get("Value"));
        }

        @Override
        public int getItemCount() {
            return displayHashMap.size();
        }


    }

}
