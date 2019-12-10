package com.example.jetecpro_ver1.AllOfNewMonitor.Model.NDD_NewDataDisplaySupport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jetecpro_ver1.R;

import java.util.ArrayList;
import java.util.HashMap;

public class NewSupportNDDRecyclerViewAdapter extends RecyclerView.Adapter<NewSupportNDDRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> arrayList;
    private Context context;
    private View mHeaderView;
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    public NewSupportNDDRecyclerViewAdapter(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvValue;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if (itemView == mHeaderView){
                return;
            }
            tvTitle = itemView.findViewById(R.id.textView_NDD_CellTitle);
            tvValue = itemView.findViewById(R.id.textView_NDD_CellValue);
        }
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycleview_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvTitle.setText(arrayList.get(i));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
