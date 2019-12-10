package com.example.jetecpro_ver1.AllOfNewMonitor.Model.Pagers;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jetecpro_ver1.R;

public class FragmentList_One extends RelativeLayout {
    private View mView;

    public FragmentList_One(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        mView = inflater.inflate(R.layout.new_viewpager,null);

        addView(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }
}
