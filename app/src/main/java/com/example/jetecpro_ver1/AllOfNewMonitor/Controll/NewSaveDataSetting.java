package com.example.jetecpro_ver1.AllOfNewMonitor.Controll;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NSDS_NewDBHelper.NewDBHelper;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NewSendType;
import com.example.jetecpro_ver1.R;

import java.util.ArrayList;
import java.util.HashMap;

public class NewSaveDataSetting {
    String TAG = NewSaveDataSetting.class.getSimpleName();
    Activity activity;
    HashMap<String, ArrayList<String>> arrayList;
    SetRecycleView mAdapter;
    EditText edCreate;
    RecyclerView recyclerView;
    Button btCreate;
    private NewDBHelper mDBHelper;
    public NewSaveDataSetting(Activity activity, HashMap<String, ArrayList<String>> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    public void save(){
        mDBHelper = new NewDBHelper(activity);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.new_save_data_dialog,null);
        mBuilder.setView(view);
        AlertDialog dialog = mBuilder.create();
        btCreate = view.findViewById(R.id.button_NewSaveDialogCreate);
        edCreate = view.findViewById(R.id.editText_NewSaveDialogName);
        recyclerView = view.findViewById(R.id.recycleView_NewSaveDialog);
//        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
//        wmlp.gravity = Gravity.TOP|Gravity.LEFT;
//        wmlp.x = 100;
//        wmlp.y = 200;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(850, ViewGroup.LayoutParams.WRAP_CONTENT);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.addItemDecoration(new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL));
        mAdapter = new SetRecycleView();
        recyclerView.setAdapter(mAdapter);
//        mDBHelper.addData(NewSendType.newDeviceType,"設定","Blank3");
        Log.d(TAG, "save: "+mDBHelper.searchByType(NewSendType.newDeviceType));


    }

    private class SetRecycleView extends RecyclerView.Adapter<SetRecycleView.ViewHolder> {

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


}
