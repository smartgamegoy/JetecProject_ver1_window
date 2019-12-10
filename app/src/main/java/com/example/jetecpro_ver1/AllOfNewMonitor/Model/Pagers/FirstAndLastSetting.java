package com.example.jetecpro_ver1.AllOfNewMonitor.Model.Pagers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

public class FirstAndLastSetting extends RelativeLayout {

    NewSupportNDDRecyclerViewAdapter mAdapter;
    private View mView;

    public FirstAndLastSetting(Context context, ArrayList<String> arrayList) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        mView = inflater.inflate(R.layout.new_viewpager,null);
        RecyclerView r = mView.findViewById(R.id.recycleView_Pages);
        r.setLayoutManager(new GridLayoutManager(getContext(),2));
        mAdapter = new NewSupportNDDRecyclerViewAdapter(arrayList,getContext());
        r.setAdapter(mAdapter);
        addView(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }


}
