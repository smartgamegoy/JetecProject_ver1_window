package com.example.jetecpro_ver1.AllOfNewMonitor.Model.Pagers;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NDD_NewDataDisplaySupport.NewSupportNDDRecyclerViewAdapter;
import com.example.jetecpro_ver1.R;

import java.util.ArrayList;

public class NormalDataSetting extends RelativeLayout {
    private View mView;
    NewSupportNDDRecyclerViewAdapter mAdapter;

    public NormalDataSetting(Context context, ArrayList<String> arrayList) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        mView = inflater.inflate(R.layout.new_ndd_recycler_item_normal_setting,null);
        RecyclerView r = mView.findViewById(R.id.recyclerview_NDD_normal_data_set);
        r.setLayoutManager(new GridLayoutManager(getContext(),2));
        mAdapter = new NewSupportNDDRecyclerViewAdapter(arrayList,getContext());
        r.setAdapter(mAdapter);
        addView(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }
}
