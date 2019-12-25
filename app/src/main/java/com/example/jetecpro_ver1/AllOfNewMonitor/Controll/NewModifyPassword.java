package com.example.jetecpro_ver1.AllOfNewMonitor.Controll;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.Values.SendType;

public class NewModifyPassword {
    String TAG = NewModifyPassword.class.getSimpleName();
    Activity activity;

    public NewModifyPassword(Activity activity) {
        this.activity = activity;
    }

    public void modifyPassword() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.new_password_modify, null);
        mBuilder.setView(view);
        AlertDialog dialog = mBuilder.create();

        EditText edNew = view.findViewById(R.id.editText_NDD_PSWNEW);
        EditText edOld = view.findViewById(R.id.editText_NDD_PSWOld);
        Button btCancel = view.findViewById(R.id.button_NDD_PSWCancel);
        Button btOK = view.findViewById(R.id.button_NDD_PSWOK);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(850, ViewGroup.LayoutParams.WRAP_CONTENT);
        btCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        btOK.setOnClickListener(v -> {
            if (edNew.getText().length() > 0 && edOld.getText().length() > 0) {
                if (edOld.getText().toString().matches(SendType.DevicePSW)) {
                    if (edNew.getText().length()!=6){
                        Toast.makeText(activity, R.string.new_plzInputSix, Toast.LENGTH_LONG).show();
                    }else{
                        sendString("PWR="+edNew.getText().toString());
                        Toast.makeText(activity,activity.getResources()
                                .getString(R.string.modifySuccess)+edNew.getText()
                                .toString(),Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }

                }else{
                    Toast.makeText(activity, R.string.new_oldPasswDisError, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(activity, R.string.dont_blank, Toast.LENGTH_LONG).show();
            }
        });

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
