package com.example.jetecpro_ver1.ModifyPassword;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.Values.SendType;

public class ModifyPSW {

    Context context;
    View view;
    AlertDialog.Builder mBuilder;


    public ModifyPSW(Context context, View view, AlertDialog.Builder mBuilder) {
        this.context = context;
        this.view = view;
        this.mBuilder = mBuilder;

    }

    public void modifyDialog() {
        mBuilder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        mBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        mBuilder.setView(view);
        final AlertDialog dialog = mBuilder.create();
        final EditText edOldPSW = view.findViewById(R.id.oldPassword);
        final EditText edNewPSW = view.findViewById(R.id.newPassword);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edOldPSW.getText().toString().contains(SendType.DevicePSW)
                        && edNewPSW.getText().toString().length() == 6) {
                    Toast.makeText(context, context.getResources().getString(R.string.modifySuccess)
                            +edNewPSW.getText().toString(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    sendModifyPSW(edNewPSW.getText().toString());
                } else if(edNewPSW.getText().toString().isEmpty()
                        || edOldPSW.getText().toString().isEmpty()){
                    Toast.makeText(context,R.string.dont_blank,Toast.LENGTH_LONG).show();
                }else if (edOldPSW.getText().toString().length() < 6
                        || edNewPSW.getText().toString().length() < 6) {
                    Toast.makeText(context, R.string.lessSixWord, Toast.LENGTH_SHORT).show();
                }else if(edNewPSW.getText().toString().contains("111111")){
                    Toast.makeText(context,R.string.noUseThePSW,Toast.LENGTH_LONG).show();
                }else if(edNewPSW.getText().toString().contains("@JETEC")){
                    Toast.makeText(context,R.string.noUseThePSW2,Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context,R.string.oldPSWError,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void sendModifyPSW (String newPSW){
        SendType.SendForBLEDataType = "PWR="+newPSW;
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
        SendType.DevicePSW = newPSW;
    }
}
