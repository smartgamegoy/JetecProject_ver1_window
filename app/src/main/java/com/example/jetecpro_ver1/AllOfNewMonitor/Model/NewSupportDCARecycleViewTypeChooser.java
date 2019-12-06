package com.example.jetecpro_ver1.AllOfNewMonitor.Model;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jetecpro_ver1.R;

import java.util.ArrayList;

public class NewSupportDCARecycleViewTypeChooser extends RecyclerView.Adapter<NewSupportDCARecycleViewTypeChooser.ViewHolder> {
    public static ArrayList<String> mSelectType = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvChosenItem ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChosenItem = itemView.findViewById(R.id.textView_DCA_dialog_chosen_item);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.new_dialog_type_chooser_main_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.tvChosenItem.setText(mSelectType.get(i));
    }

    @Override
    public int getItemCount() {
        return mSelectType.size();
    }




}
