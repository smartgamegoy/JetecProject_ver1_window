package com.example.jetecpro_ver1.AllOfNewMonitor.Model.NDD_NewDataDisplaySupport;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

public class NewSupportNDDRecyclerViewAdapter extends RecyclerView.Adapter<NewSupportNDDRecyclerViewAdapter.ViewHolder> {

    private ArrayList<HashMap<String,String>> arrayList;

    public NewSupportNDDRecyclerViewAdapter(ArrayList<HashMap<String, String>> arrayList) {
        this.arrayList = arrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
