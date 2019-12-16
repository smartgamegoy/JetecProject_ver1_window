package com.example.jetecpro_ver1.AllOfNewMonitor.Model.NDD_NewDataDisplaySupport;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.Values.SendType;

import java.util.ArrayList;
import java.util.HashMap;

public class NewSupportNDDRecyclerViewAdapter extends RecyclerView.Adapter<NewSupportNDDRecyclerViewAdapter.ViewHolder> {


    public ArrayList<HashMap<String,String>> arrayList;
    private Context context;
    private View mHeaderView;

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
        String titleName =arrayList.get(i).get("Title");
        String ValueName = viewHolder.tvValue.getText().toString();
        viewHolder.tvTitle.setText(titleName);
        viewHolder.view.setOnClickListener((v)->{
            if (titleName.matches(trans(R.string.Y_function_TimeSet))){

            }else {
                setValue(titleName,ValueName);
            }

        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    /**設定名字*/
    private void setValue(String title,String value) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.new_normal_modify_setting,null);
        TextView tvDialogTitle = view.findViewById(R.id.textView_NDD_modifyTitle);
        EditText edModify = view.findViewById(R.id.editText_NDD_modify);
        Button btOK = view.findViewById(R.id.button_NDD_modify);
        Button btCancel = view.findViewById(R.id.button_NDD_cancel);
        edModify.setHint(value);
        tvDialogTitle.setText(title);
        mBuilder.setView(view);

        AlertDialog dialog = mBuilder.create();
        btOK.setOnClickListener((v -> {
            if (title.matches(trans(R.string.device_name))){//修改名字
                if (edModify.getText().toString().length()>0){
                    sendString("NAME"+edModify.getText().toString());
                    dialog.dismiss();
                }else{
                    Toast.makeText(context,R.string.plzInputModifyName,Toast.LENGTH_LONG).show();
                }

            }else{}

            dialog.dismiss();

        }));
        btCancel.setOnClickListener((v -> {dialog.dismiss();}));
        dialog.show();
    }


    private String trans(int name) {
        String str = context.getResources().getString(name);
        return str;
    }
    /**
     * 送字串模組
     */
    private void sendString(String getSend) {
        SendType.SendForBLEDataType = getSend;
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
    }
}
