package com.example.jetecpro_ver1.AllOfNewMonitor.Controll.Pagers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NewSendType;
import com.example.jetecpro_ver1.BLE_function.BluetoothLeService;
import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.Values.SendType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FirstPageSetting extends RelativeLayout {
    private String TAG = FirstPageSetting.class.getSimpleName();
    public MyRecyclerView mAdapter;
    public ArrayList<HashMap<String, String>> valueArray = new ArrayList<>();
    private View mView;
    private Activity mActivity;
    private BluetoothLeService mBluetoothLeService;

    public FirstPageSetting(Context context
            , HashMap<String, ArrayList<String>> getFromIntentArray
            , ArrayList<Integer> getTab, Activity activity) {
        super(context);
        mActivity = activity;
        makeSettingData(context, getFromIntentArray);

        LayoutInflater inflater = LayoutInflater.from(context);
        mView = inflater.inflate(R.layout.new_viewpager, null);
        RecyclerView r = mView.findViewById(R.id.recycleView_Pages);
        r.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mAdapter = new MyRecyclerView();
        r.setAdapter(mAdapter);
        addView(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }

    private void makeSettingData(Context context, HashMap<String, ArrayList<String>> getFromIntentArray) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Title", trans(context, R.string.device_name));
        hashMap.put("Value", NewSendType.newDeviceName);
        valueArray.add(hashMap);
        if (NewSendType.newDeviceType.contains("Y") || NewSendType.newDeviceType.contains("Z")) {
            HashMap<String, String> hashMap2 = new HashMap<>();
            hashMap2.put("Title", trans(context, R.string.Y_function_TimeSet));
            hashMap2.put("Value", "就說了這個欄位是沒有東西的ㄎㄎ");
            valueArray.add(hashMap2);
        }
        for (int i = 0; i < getFromIntentArray.get("Byte").size(); i++) {
            String spk = getTag(getFromIntentArray.get("Byte").get(i).substring(2, 4), context);
            if (spk.matches(trans(context, R.string.SPK))) {
                HashMap<String, String> hashMap3 = new HashMap<>();
                hashMap3.put("Title", trans(context, R.string.SPK));
                hashMap3.put("Value", "空");
                valueArray.add(hashMap3);
            }
        }

        Log.d(TAG, "FirstPageSetting: " + valueArray);
    }


    private String getTag(String getrow, Context context) {
        int getSymbol = Integer.parseInt(getrow, 16);
        Log.d(TAG, "getTag: " + getSymbol);
        switch (getSymbol) {
            case 16:
                return trans(context, R.string.SPK);
        }
        return " ";

    }

    private String trans(Context context, int name) {//不是我在講...每個都要寫ctx.getResources().getString(R.string.??);真的會死人
        String str = context.getResources().getString(name);
        return str;
    }

    public class MyRecyclerView extends RecyclerView.Adapter<MyRecyclerView.ViewHolder> {
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle, tvValue;
            View mView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.textView_NDD_CellTitle);
                tvValue = itemView.findViewById(R.id.textView_NDD_CellValue);
                mView = itemView;
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = mActivity.getLayoutInflater().inflate(R.layout.recycleview_item, null);
            return new ViewHolder(view);
        }
        @Override
        public int getItemCount() {
            return valueArray.size();
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            String title = valueArray.get(i).get("Title");
            String value = valueArray.get(i).get("Value");
            viewHolder.tvTitle.setText(title);

            if (valueArray.get(i).get("Value").matches("就說了這個欄位是沒有東西的ㄎㄎ")) {
                viewHolder.tvValue.setVisibility(View.GONE);
            } else {
                viewHolder.tvValue.setText(value);
            }

            chickEvent(viewHolder, i, title, value);/**點擊事件*/
        }

        /**點擊事件*/
        private void chickEvent(@NonNull ViewHolder viewHolder, int i, String title, String value) {
            viewHolder.mView.setOnClickListener((v -> {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(mActivity);
                View view = LayoutInflater.from(mActivity).inflate(R.layout.new_normal_modify_setting, null);
                EditText editText = view.findViewById(R.id.editText_NDD_modify);
                Button btCancel = view.findViewById(R.id.button_NDD_cancel);
                Button btSendOut = view.findViewById(R.id.button_NDD_modify);
                TextView tvNDDTitle = view.findViewById(R.id.textView_NDD_modifyTitle);
                tvNDDTitle.setText(title);
                editText.setHint(value);
                mBuilder.setView(view);
                AlertDialog dialog = mBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                if (title.matches(trans(mActivity, R.string.device_name))) {

                    setName(i, editText, btCancel, btSendOut, dialog);/**設定名字*/

                } else if (title.matches(trans(mActivity, R.string.Y_function_TimeSet))) {
                    setTime();/**設定時間*/
                }
            }));
        }

        /**設定時間*/
        private void setTime() {
            AlertDialog.Builder timeSet = new AlertDialog.Builder(mActivity);
            timeSet.setTitle(R.string.Y_function_TimeSet);
            timeSet.setMessage(R.string.question);
            timeSet.setPositiveButton(R.string.Y_freshSet, (dialog1, which) -> {
                flashSetting();/**時間快速設定*/
            });
            timeSet.setNegativeButton(R.string.Y_mutSet, (dialog2, which) -> {
                manualSetting();/**時間手動設定*/
            });
            timeSet.setNeutralButton(R.string.cancel, null);
            timeSet.show();
        }

        /**設定名字*/
        private void setName(int i, EditText editText, Button btCancel, Button btSendOut, AlertDialog dialog) {
            btCancel.setOnClickListener((v1 -> {
                dialog.dismiss();
            }));
            btSendOut.setOnClickListener(v1 -> {
                if (editText.getText().toString().length() > 0) {
                    sendString("NAME" + editText.getText().toString());
                    valueArray.get(i).put("Value", editText.getText().toString());
                    mAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(mActivity, R.string.dont_blank, Toast.LENGTH_SHORT).show();
                }

            });
            dialog.show();
        }


        /**
         * 時間快速設定
         */
        private void flashSetting() {
            ProgressDialog dialog = ProgressDialog.show(mActivity
                    , trans(mActivity, R.string.progressing), trans(mActivity, R.string.plzWait)
                    , false);
            new Thread(() -> {
                SimpleDateFormat nowTime = new SimpleDateFormat("HHmmss");
                SimpleDateFormat today = new SimpleDateFormat("yyMMdd");
                Date date = new Date();
                String Stoday = today.format(date);
                String Stime = nowTime.format(date);

                SendType.SendForBLEDataType = "DATE" + Stoday;
                SendType.getSendBluetoothLeService.
                        setCharacteristicNotification(SendType.Mycharacteristic, true);
                SystemClock.sleep(500);
                SendType.SendForBLEDataType = "TIME" + Stime;
                SendType.getSendBluetoothLeService.
                        setCharacteristicNotification(SendType.Mycharacteristic, true);
                dialog.dismiss();
                Looper.prepare();
                Toast.makeText(mActivity, R.string.Y_modifyOK, Toast.LENGTH_SHORT).show();
                Looper.loop();

            }).start();
        }
    }

    /**
     * 時間手動設定
     */
    private void manualSetting() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mActivity);
        View view = mActivity.getLayoutInflater().inflate(R.layout.activity_data_display_manual_timeset, null);
        mBuilder.setView(view);
        mBuilder.setTitle(trans(mActivity, R.string.Y_mutSet));
        final NumberPicker nbHr = view.findViewById(R.id.nb_Hour);
        final NumberPicker nbMin = view.findViewById(R.id.nb_Min);
        final NumberPicker nbSec = view.findViewById(R.id.nb_Sec);

        SimpleDateFormat HH = new SimpleDateFormat("HH");
        SimpleDateFormat MM = new SimpleDateFormat("mm");
        SimpleDateFormat SS = new SimpleDateFormat("ss");
        SimpleDateFormat DD = new SimpleDateFormat("yyMMdd");
        Date date = new Date();
        String hour = HH.format(date);
        String min = MM.format(date);
        String sec = SS.format(date);
        final String Day = DD.format(date);

        nbHr.setMinValue(Integer.parseInt(hour));
        nbMin.setMinValue(Integer.parseInt(min));
        nbSec.setMinValue(Integer.parseInt(sec));

        nbHr.setMinValue(0);
        nbMin.setMinValue(0);
        nbSec.setMinValue(0);
        nbHr.setMaxValue(24);
        nbMin.setMaxValue(60);
        nbSec.setMaxValue(60);

        mBuilder.setPositiveButton(R.string.OK, (dialog,which)-> {

                String newHour = String.valueOf(nbHr.getValue());
                String newMin = String.valueOf(nbMin.getValue());
                String newSec = String.valueOf(nbSec.getValue());

                String SendH;
                String SendM;
                String SendS;

                if (nbHr.getValue() < 10) {
                    SendH = "0" + newHour;
                } else {
                    SendH = newHour;
                }
                if (nbMin.getValue() < 10) {
                    SendM = "0" + newMin;
                } else {
                    SendM = newMin;
                }
                if (nbSec.getValue() < 10) {
                    SendS = "0" + newMin;
                } else {
                    SendS = newSec;
                }
//                Log.v("BT","TIME"+SendH+SendM+SendS+",   "+Day);
                SendType.SendForBLEDataType = "DATE" + Day;
                SendType.getSendBluetoothLeService.
                        setCharacteristicNotification(SendType.Mycharacteristic, true);
                SystemClock.sleep(500);
                Log.v("BT", "TIME" + SendH + " " + SendM + " " + SendS);//因好像有問題又好像沒問題，故留著
                SendType.SendForBLEDataType = "TIME" + SendH + SendM + SendS;
                SendType.getSendBluetoothLeService.
                        setCharacteristicNotification(SendType.Mycharacteristic, true);
                Toast.makeText(mActivity, R.string.Y_modifyOK, Toast.LENGTH_SHORT).show();


        });
        mBuilder.setNegativeButton(R.string.cancel, null);
        mBuilder.show();

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
