package com.example.jetecpro_ver1.AllOfNewMonitor.Model.Pagers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jetecpro_ver1.R;

public class FragmentList_Twp extends RelativeLayout {
    private View mView;

    public FragmentList_Twp(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        mView = inflater.inflate(R.layout.new_viewpager,null);
        addView(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }
}
