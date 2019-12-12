package com.example.jetecpro_ver1.AllOfNewMonitor.Model.NDD_NewDataDisplaySupport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jetecpro_ver1.R;

import java.util.ArrayList;
import java.util.HashMap;

public class NewSupportNDDRecyclerViewAdapter extends RecyclerView.Adapter<NewSupportNDDRecyclerViewAdapter.ViewHolder> {


    private ArrayList<HashMap<String,String>> arrayList;
    private Context context;
    private View mHeaderView;
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    public NewSupportNDDRecyclerViewAdapter(ArrayList<HashMap<String,String>> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvValue;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.textView_NDD_CellTitle);
            tvValue = itemView.findViewById(R.id.textView_NDD_CellValue);
            view = itemView;
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
        if (arrayList.get(i).get("Value").matches("就說了這個欄位是沒有東西的ㄎㄎ")){
            viewHolder.tvValue.setVisibility(View.GONE);
        }else{
            viewHolder.tvValue.setText(arrayList.get(i).get("Value"));
        }
        viewHolder.tvTitle.setText(arrayList.get(i).get("Title"));
        viewHolder.view.setOnClickListener((v)->{
            Toast.makeText(context,viewHolder.tvTitle.getText().toString(),Toast.LENGTH_SHORT).show();
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
