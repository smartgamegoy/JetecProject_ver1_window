package com.example.jetecpro_ver1.AllOfNewMonitor.Controll.Pagers;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NDD_NewDataDisplaySupport.NewSupportNDDRecyclerViewAdapter;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NewSendType;
import com.example.jetecpro_ver1.R;

import java.util.ArrayList;
import java.util.HashMap;

public class FirstPageSetting extends RelativeLayout {
    private String TAG = FirstPageSetting.class.getSimpleName();
    NewSupportNDDRecyclerViewAdapter mAdapter;
    ArrayList<HashMap<String,String>> valueArray = new ArrayList<>();
    private View mView;

    public FirstPageSetting(Context context
            , HashMap<String,ArrayList<String>> getFromIntentArray
            , ArrayList<Integer> getTab) {
        super(context);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("Title",trans(context,R.string.device_name));
        hashMap.put("Value",NewSendType.newDeviceName);
        valueArray.add(hashMap);
        if (NewSendType.newDeviceType.contains("Y")||NewSendType.newDeviceType.contains("Z")){
            HashMap<String,String> hashMap2 = new HashMap<>();
            hashMap2.put("Title",trans(context,R.string.Y_function_TimeSet));
            hashMap2.put("Value","就說了這個欄位是沒有東西的ㄎㄎ");
            valueArray.add(hashMap2);
        }
        for (int i=0;i<getFromIntentArray.get("Byte").size();i++){
            String spk =  getTag(getFromIntentArray.get("Byte").get(i).substring(2,4),context);
            if (spk.matches(trans(context,R.string.SPK))){
                HashMap<String,String> hashMap3 = new HashMap<>();
                hashMap3.put("Title",trans(context,R.string.SPK));
                hashMap3.put("Value","空");
                valueArray.add(hashMap3);
            }
        }

        Log.d(TAG, "FirstPageSetting: "+valueArray);

        LayoutInflater inflater = LayoutInflater.from(context);
        mView = inflater.inflate(R.layout.new_viewpager,null);
        RecyclerView r = mView.findViewById(R.id.recycleView_Pages);
        r.setLayoutManager(new GridLayoutManager(getContext(),2));
        mAdapter = new NewSupportNDDRecyclerViewAdapter(valueArray,getContext());
        r.setAdapter(mAdapter);
        addView(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }

    private String getTag (String getrow,Context context){
        int getSymbol = Integer.parseInt(getrow,16);
        Log.d(TAG, "getTag: "+getSymbol);
        switch (getSymbol){
            case 16:
                return trans(context,R.string.SPK);
        }
        return " ";

    }
    private String trans(Context context,int name) {//不是我在講...每個都要寫ctx.getResources().getString(R.string.??);真的會死人
        String str = context.getResources().getString(name);
        return str;
    }


}
