package com.example.jetecpro_ver1.SendData;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.sip.SipSession;
import android.os.SystemClock;
import android.text.method.DialerKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jetecpro_ver1.MainProcess.DataDisplayActivity;
import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.RecordData.GetRecord;
import com.example.jetecpro_ver1.Values.SendType;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class DDA_SendData {
    String selectName;
    String selectValues;
    EditText edInput;
    Switch swInput;
    Context cox;
    NumberPicker npHour;
    NumberPicker npMin;
    NumberPicker npSec;
    NumberPicker nbDppicker;
    int dPNumberSelect;


    public DDA_SendData(String selectName, String selectValues, EditText edInput, Switch swInput, Context cox
            , NumberPicker npHour, NumberPicker npMin, NumberPicker npSec, NumberPicker nbDPpicker) {
        this.nbDppicker     = nbDPpicker;
        this.selectName     = selectName;
        this.selectValues   = selectValues;
        this.edInput        = edInput;
        this.swInput        = swInput;
        this.cox            = cox;
        this.npHour         = npHour;
        this.npMin          = npMin;
        this.npSec          = npSec;

    }

    /**
     * 設置Switch的原始狀態
     */
    private void mSwitch() {//設置switch原始狀態
        if (selectName.contains(SendType.SPK)) {
            if (selectValues.contains("on")) {
                swInput.setChecked(true);
            } else {
                swInput.setChecked(false);
            }
        } else if (SendType.FirstWord == 'I'
                || SendType.SecondWord == 'I'
                || SendType.ThirdWord == 'I') {
            if (selectName.contains(trans(R.string.decimal_point))) {
//                if(swInput != null){
//                    if (selectValues.contains("on")) {
//                        swInput.setChecked(true);
//                    } else {
//                        swInput.setChecked(false);
//                    }
//                }
                nbDppicker.setMinValue(0);
                nbDppicker.setMaxValue(3);
                if(SendType.mDP1 != null &&selectName.contains(SendType.DP1)){
                    nbDppicker.setValue(Integer.parseInt(SendType.mDP1));
                }else if(SendType.mDP2 != null && selectName.contains(SendType.DP2)){
                    nbDppicker.setValue(Integer.parseInt(SendType.mDP2));
                } else if(SendType.mDP3 != null && selectName.contains(SendType.DP3)){
                    nbDppicker.setValue(Integer.parseInt(SendType.mDP3));
                }
                nbDppicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        dPNumberSelect = oldVal;
                        dPNumberSelect = newVal;

                    }
                });

            }
        }
    }

    /**
     * 主要程序:設置AlertDialog以及其功能
     */
    public void mAlertDialog(AlertDialog.Builder builder) {//設置AlertDialog
        final AlertDialog dialog = builder.create();
        if (selectName.contains(trans(R.string.device_name))) {
            edInput.setText(selectValues);

        }
        switch (SendType.FirstWord) {
            case 'T':
                if (selectName.contains(SendType.PV1)) {
                    edInput.setHint("-5~5");
                } else if (selectName.contains(SendType.EH1)||selectName.contains(SendType.HH1)) {
                    edInput.setHint("-10~65");
                } else if (selectName.contains(SendType.EL1)||selectName.contains(SendType.LL1)) {
                    edInput.setHint("-10~65");
                } else if (selectName.contains(SendType.CR1)) {
                    edInput.setHint("-10~65");
                }
                break;
            case 'H':

                break;

            case 'C':
                if (selectName.contains(SendType.PV1)) {
                    edInput.setHint("-500~500");
                } else if (selectName.contains(SendType.EH1)||selectName.contains(SendType.HH1)) {
                    edInput.setHint("-10~65");
                } else if (selectName.contains(SendType.EL1)||selectName.contains(SendType.LL1)) {
                    edInput.setHint("0~2000");
                } else if (selectName.contains(SendType.CR1)) {
                    edInput.setHint("0~2000");
                }
                break;
            case 'D':
                if (selectName.contains(SendType.PV1)) {
                    edInput.setHint("-500~500");
                } else if (selectName.contains(SendType.EH1)||selectName.contains(SendType.HH1)) {
                    edInput.setHint("-10~65");
                } else if (selectName.contains(SendType.EL1)||selectName.contains(SendType.LL1)) {
                    edInput.setHint("0~3000");
                } else if (selectName.contains(SendType.CR1)) {
                    edInput.setHint("0~3000");
                }
                break;
            case 'E':
                if (selectName.contains(SendType.PV1)) {
                    edInput.setHint("-500~500");
                } else if (selectName.contains(SendType.EH1)||selectName.contains(SendType.HH1)) {
                    edInput.setHint("-10~65");
                } else if (selectName.contains(SendType.EL1)||selectName.contains(SendType.LL1)) {
                    edInput.setHint("0~5000");
                } else if (selectName.contains(SendType.CR1)) {
                    edInput.setHint("0~5000");
                }
                break;
            case 'I':
//                Log.v("BT","第一排"+ dPNumberSelect);
//                if (SendType.mDP1.contains("on")){//洨數點打開
//                    if(selectName.contains(SendType.PV1)){
//                        edInput.setHint("-99.9~99.9");
//                    }else if (selectName.contains(trans(R.string.decimal_point))
//                            ||selectName.contains(SendType.SPK)){
//
//                        Log.v("BT","切換系列");
//                    }
//                    else if(selectName.contains(trans(R.string.FirstRow))){
//                        edInput.setHint("-199.9~999.9");
//                    }
//                }else{//洨數點關閉
//                    if(selectName.contains(SendType.PV1)){
//                        edInput.setHint("-999~999");
//                    }else if (selectName.contains(trans(R.string.decimal_point))
//                            ||selectName.contains(SendType.SPK)){}
//                    else if(selectName.contains(trans(R.string.FirstRow))){
//                        edInput.setHint("-999~9999");
//                    }
//                }
                switch (Integer.parseInt(SendType.mDP1)){//小數點開到幾？
                    case 0://沒開
                        if(selectName.contains(SendType.PV1)){
                            edInput.setHint("-999~999");
                        }else if (selectName.contains(trans(R.string.decimal_point))
                                ||selectName.contains(SendType.SPK)){

                        }else if(selectName.contains(trans(R.string.FirstRow))){
                            edInput.setHint("-999~9999");
                        }
                        break;
                    case 1://開一發
                        if(selectName.contains(SendType.PV1)){
                            edInput.setHint("-99.9~99.9");
                        }else if (selectName.contains(trans(R.string.decimal_point))
                                ||selectName.contains(SendType.SPK)){

                        }else if(selectName.contains(trans(R.string.FirstRow))){
                            edInput.setHint("-199.9~99.9");
                        }
                        break;
                    case 2://兩發
                        if(selectName.contains(SendType.PV1)){
                            edInput.setHint("-9.99~9.99");
                        }else if (selectName.contains(trans(R.string.decimal_point))
                                ||selectName.contains(SendType.SPK)){

                        }else if(selectName.contains(trans(R.string.FirstRow))){
                            edInput.setHint("-19.9~99.99");
                        }
                        break;
                    case 3://三發
                        if(selectName.contains(SendType.PV1)){
                            edInput.setHint("-9.990~9.99");
                        }else if (selectName.contains(trans(R.string.decimal_point))
                                ||selectName.contains(SendType.SPK)){

                        }else if(selectName.contains(trans(R.string.FirstRow))){
                            edInput.setHint("-9.999~9.999");
                        }
                        break;

                }
                break;

        }
        switch ((SendType.SecondWord)) {

            case 'T':

                break;

            case 'H':
                if (selectName.contains(SendType.PV2)) {
                    edInput.setHint("-20~20");
                } else if (selectName.contains(SendType.EH2)||selectName.contains(SendType.HH2)) {
                    edInput.setHint("0~100");
                } else if (selectName.contains(SendType.EL2)||selectName.contains(SendType.LL2)) {
                    edInput.setHint("0~100");
                } else if (selectName.contains(SendType.CR2)) {
                    edInput.setHint("0~100");
                }
                break;
            case 'C':
                if (selectName.contains(SendType.PV2)) {
                    edInput.setHint("-500~500");
                } else if (selectName.contains(SendType.EH2)||selectName.contains(SendType.HH2)) {
                    edInput.setHint("0~100");
                } else if (selectName.contains(SendType.EL2)||selectName.contains(SendType.LL2)) {
                    edInput.setHint("0~2000");
                } else if (selectName.contains(SendType.CR2)) {
                    edInput.setHint("0~2000");
                }
                break;
            case 'D':
                if (selectName.contains(SendType.PV2)) {
                    edInput.setHint("-500~500");
                } else if (selectName.contains(SendType.EH2)||selectName.contains(SendType.HH2)) {
                    edInput.setHint("0~100");
                } else if (selectName.contains(SendType.EL2)||selectName.contains(SendType.LL2)) {
                    edInput.setHint("0~3000");
                } else if (selectName.contains(SendType.CR2)) {
                    edInput.setHint("0~3000");
                }
                break;
            case 'E':
                if (selectName.contains(SendType.PV2)) {
                    edInput.setHint("-500~500");
                } else if (selectName.contains(SendType.EH2)||selectName.contains(SendType.HH2)) {
                    edInput.setHint("0~100");
                } else if (selectName.contains(SendType.EL2)||selectName.contains(SendType.LL2)) {
                    edInput.setHint("0~5000");
                } else if (selectName.contains(SendType.CR2)) {
                    edInput.setHint("0~5000");
                }
                break;
            case 'M':
                if (selectName.contains(SendType.PV2)) {
                    edInput.setHint("-10~10");
                } else if (selectName.contains(SendType.EH2)||selectName.contains(SendType.HH2)) {
                    edInput.setHint("0~1000");
                } else if (selectName.contains(SendType.EL2)||selectName.contains(SendType.LL2)) {
                    edInput.setHint("0~1000");
                } else if (selectName.contains(SendType.CR2)) {
                    edInput.setHint("0~1000");
                }
                break;

            case 'I':
//                if (SendType.mDP2.contains("on")){//洨數點打開
//                    if(selectName.contains(SendType.PV2)){
//                        edInput.setHint("-99.9~99.9");
//                    }else if (selectName.contains(trans(R.string.decimal_point))
//                            ||selectName.contains(SendType.SPK)){}
//                    else if(selectName.contains(trans(R.string.SecondRow))){
//                        edInput.setHint("-199.9~999.9");
//                    }
//                }else {//洨數點關閉
//                    if(selectName.contains(SendType.PV2)){
//                        edInput.setHint("-999~999");
//                    }else if (selectName.contains(trans(R.string.decimal_point))
//                            ||selectName.contains(SendType.SPK)){
//
//                    }else if(selectName.contains(trans(R.string.SecondRow))){
//                        edInput.setHint("-999~9999");
//                    }
//                }
                switch (Integer.parseInt(SendType.mDP2)){//小數點開到幾？
                    case 0://沒開
                        if(selectName.contains(SendType.PV2)){
                            edInput.setHint("-999~999");
                        }else if (selectName.contains(trans(R.string.decimal_point))
                                ||selectName.contains(SendType.SPK)){

                        }else if(selectName.contains(trans(R.string.SecondRow))){
                            edInput.setHint("-999~9999");
                        }
                        break;
                    case 1://開一發
                        if(selectName.contains(SendType.PV2)){
                            edInput.setHint("-99.9~99.9");
                        }else if (selectName.contains(trans(R.string.decimal_point))
                                ||selectName.contains(SendType.SPK)){

                        }else if(selectName.contains(trans(R.string.SecondRow))){
                            edInput.setHint("-199.9~99.9");
                        }
                        break;
                    case 2://兩發
                        if(selectName.contains(SendType.PV2)){
                            edInput.setHint("-9.99~9.99");
                        }else if (selectName.contains(trans(R.string.decimal_point))
                                ||selectName.contains(SendType.SPK)){

                        }else if(selectName.contains(trans(R.string.SecondRow))){
                            edInput.setHint("-19.9~99.99");
                        }
                        break;
                    case 3://三發
                        if(selectName.contains(SendType.PV2)){
                            edInput.setHint("-9.990~9.99");
                        }else if (selectName.contains(trans(R.string.decimal_point))
                                ||selectName.contains(SendType.SPK)){

                        }else if(selectName.contains(trans(R.string.SecondRow))){
                            edInput.setHint("-9.999~9.999");
                        }
                        break;

                }
                break;

        }
        switch ((SendType.ThirdWord)) {
            case 'T':


                break;

            case 'H':


                break;


            case 'I':
//                if (SendType.mDP3.contains("on")){//洨數點打開
//                    if(selectName.contains(SendType.PV3)){
//                        edInput.setHint("-99.9~99.9");
//                    }else if (selectName.contains(trans(R.string.decimal_point))
//                            ||selectName.contains(SendType.SPK)){}
//                    else if(selectName.contains(trans(R.string.ThirdRow))){
//                        edInput.setHint("-199.9~999.9");
//                    }
//                }else {//洨數點關閉
//                    if(selectName.contains(SendType.PV3)){
//                        edInput.setHint("-999~999");
//                    }else if (selectName.contains(trans(R.string.decimal_point))
//                            ||selectName.contains(SendType.SPK)){
//
//                    }else if(selectName.contains(trans(R.string.ThirdRow))){
//                        edInput.setHint("-999~9999");
//                    }
//                }
                switch (Integer.parseInt(SendType.mDP3)){//小數點開到幾？
                    case 0://沒開
                        if(selectName.contains(SendType.PV3)){
                        edInput.setHint("-999~999");
                    }else if (selectName.contains(trans(R.string.decimal_point))
                            ||selectName.contains(SendType.SPK)){

                    }else if(selectName.contains(trans(R.string.ThirdRow))){
                        edInput.setHint("-999~9999");
                    }
                        break;
                    case 1://開一發
                        if(selectName.contains(SendType.PV3)){
                            edInput.setHint("-99.9~99.9");
                        }else if (selectName.contains(trans(R.string.decimal_point))
                                ||selectName.contains(SendType.SPK)){

                        }else if(selectName.contains(trans(R.string.ThirdRow))){
                            edInput.setHint("-199.9~99.9");
                        }
                        break;
                    case 2://兩發
                        if(selectName.contains(SendType.PV3)){
                            edInput.setHint("-9.99~9.99");
                        }else if (selectName.contains(trans(R.string.decimal_point))
                                ||selectName.contains(SendType.SPK)){

                        }else if(selectName.contains(trans(R.string.ThirdRow))){
                            edInput.setHint("-19.9~99.99");
                        }
                        break;
                    case 3://三發
                        if(selectName.contains(SendType.PV3)){
                            edInput.setHint("-9.990~9.99");
                        }else if (selectName.contains(trans(R.string.decimal_point))
                                ||selectName.contains(SendType.SPK)){

                        }else if(selectName.contains(trans(R.string.ThirdRow))){
                            edInput.setHint("-9.999~9.999");
                        }
                        break;

                }
                break;
            case 'C':
                if (selectName.contains(SendType.PV3)) {
                    edInput.setHint("-500~500");
                } else if (selectName.contains(SendType.EH3)) {
                    edInput.setHint("0~2000");
                }else if (selectName.contains(SendType.EL3)) {
                    edInput.setHint("0~2000");
                } else if (selectName.contains(SendType.CR3)) {
                    edInput.setHint("0~2000");
                }
                break;
            case 'D':
                if (selectName.contains(SendType.PV3)) {
                    edInput.setHint("-500~500");
                } else if (selectName.contains(SendType.EH3)) {
                    edInput.setHint("0~3000");
                }else if (selectName.contains(SendType.EL3)) {
                    edInput.setHint("0~3000");
                } else if (selectName.contains(SendType.CR3)) {
                    edInput.setHint("0~3000");
                }
                break;
            case 'E':
                if (selectName.contains(SendType.PV3)) {
                    edInput.setHint("-500~500");
                } else if (selectName.contains(SendType.EH3)) {
                    edInput.setHint("0~5000");
                }else if (selectName.contains(SendType.EL3)) {
                    edInput.setHint("0~5000");
                } else if (selectName.contains(SendType.CR3)) {
                    edInput.setHint("0~5000");
                }
                break;



        }

        transSreing(selectName);
        mSwitch();
        if (selectName.contains(SendType.SPK)) {
            SendType.SPK = "SPK";
            sendData2BTWithSwitch();
        } else if (selectName.contains(trans(R.string.INTER))) {
            timeSelect();
        } else if (selectName.contains(trans(R.string.device_name))) {
            edInput.setText(selectValues);
            edInput.setKeyListener(new EditText(cox).getKeyListener());
        }
//        else if (SendType.FirstWord == 'I'
//                || SendType.SecondWord == 'I'
//                || SendType.ThirdWord == 'I') {
//            if(swInput != null){
//                if (selectName.contains(SendType.DP1)
//                        || selectName.contains(SendType.DP2)
//                        || selectName.contains(SendType.DP3)) {
//                    sendData2BTWithDP_Switch(selectName);
//                }
//            }
//
//        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        //分流功能＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (selectName.contains(trans(R.string.SPK))) {
                    dialog.dismiss();
                }  else if (selectName.contains(trans(R.string.INTER))) {
                    AlertDialog.Builder mB = new AlertDialog.Builder(DataDisplayActivity.DisplayData);
                    mB.setTitle(R.string.alertTitle);
                    mB.setMessage(R.string.itWillDeleteAllofData);
                    mB.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface ialog, int which) {
                            timeSend();
                            dialog.dismiss();
                            ialog.dismiss();
                        }
                    });
                    mB.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    mB.show();

                } else if (selectName.contains(trans(R.string.device_name))) {
                    modifyDeviceName(dialog);
                }else if(SendType.FirstWord == 'I'
                        || SendType.SecondWord == 'I'
                        || SendType.ThirdWord == 'I'){
                    if (selectName.contains(trans(R.string.decimal_point))) {
                        //這裡要加入song出
                        modifyDP(dialog);
//                        dialog.dismiss();
                    }else{
                        if (edInput.getText().length()<=0){
                            Toast.makeText(cox, R.string.dont_blank, Toast.LENGTH_SHORT).show();
                        }else{
                            RunEditText(dialog);
                        }
                    }
                }
                else {
                    if (edInput.getText().length()<=0){
                        Toast.makeText(cox, R.string.dont_blank, Toast.LENGTH_SHORT).show();
                    }else{
                        RunEditText(dialog);
                    }

                }



            }//onClick
        });
    }//mAlertDialog


    /**修改洨數點*/
    private void modifyDP(AlertDialog dialog){
        Log.v("BT","洨數點:"+dPNumberSelect);
        if (selectName.contains(trans(R.string.FirstRow) + trans(R.string.decimal_point))){
            SendType.dpNumberSelectedDP1 = dPNumberSelect;
            SendType.SendForBLEDataType = "DP1+000"+dPNumberSelect+".0";
        }else if(selectName.contains(trans(R.string.SecondRow) + trans(R.string.decimal_point))){
            SendType.dpNumberSelectedDP2 = dPNumberSelect;
            SendType.SendForBLEDataType = "DP2+000"+dPNumberSelect+".0";
        }else if(selectName.contains(trans(R.string.ThirdRow) + trans(R.string.decimal_point))){
            SendType.dpNumberSelectedDP3 = dPNumberSelect;
            SendType.SendForBLEDataType = "DP3+000"+dPNumberSelect+".0";
        }else{
            Log.e("DDA_SendData.java","媽的發科？");
        }
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
        SystemClock.sleep(500);
        SendType.SendForBLEDataType = "get";
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
        dialog.dismiss();



    }
    /**
     * 傳送修改的裝置名稱
     */
    private void modifyDeviceName(AlertDialog dialog) {
        try {
            String s = "NAME"+edInput.getText().toString();
            byte[] b3 = s.getBytes("UTF-8");
            Log.v("BT", "名稱的字串長度:"+(b3.length));
            if (b3.length > 20) {
                Toast.makeText(cox, trans(R.string.NameIsTooLong), Toast.LENGTH_LONG).show();
            } else {
                switch (b3.length) {
                    default:
                        SendType.SendForBLEDataType = s;
                        break;

                }//switch
                SendType.getSendBluetoothLeService.
                        setCharacteristicNotification(SendType.Mycharacteristic, true);
                dialog.dismiss();

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    /**
     * 處理記錄間隔功能之運作
     */
    private void timeSelect() {
        npHour.setMinValue(0);
        npHour.setMaxValue(1);
        npHour.setValue(1);
        npHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == 1) {
                    npMin.setMaxValue(0);
                    npMin.setMinValue(0);
                    npSec.setMaxValue(0);
                    npSec.setMinValue(0);
                } else {
                    npMin.setMaxValue(60);
                    npMin.setMinValue(0);
                    npSec.setMaxValue(60);
                    npSec.setMinValue(30);
                    npMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            if (npMin.getValue() == 0) {
                                npSec.setMaxValue(60);
                                npSec.setMinValue(30);
                            } else {
                                npSec.setMaxValue(60);
                                npSec.setMinValue(0);
                            }
                        }
                    });
                }
            }
        });

    }

    /**
     * 傳送記錄間隔的數值
     */
    private void timeSend() {
        int hour = npHour.getValue();
        int min = npMin.getValue();
        int sec = npSec.getValue();
        int hour2Sec = hour * 3600;
        int min2Sec = min * 60;
        int totleSec = hour2Sec + min2Sec + sec;
        String out = String.valueOf(totleSec);
//        GetRecord getRecord = new GetRecord("",cox);
//        getRecord.deleteAllData();
        if (totleSec >= 1000) {
            SendType.SendForBLEDataType = "INTER0" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
            SystemClock.sleep(200);
            SendType.SendForBLEDataType = "START";
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
            SystemClock.sleep(200);
            SendType.SendForBLEDataType = "END";
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);

        } else if (totleSec <= 999 && totleSec >= 100) {
            SendType.SendForBLEDataType = "INTER00" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
            SystemClock.sleep(200);
            SendType.SendForBLEDataType = "START";
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
            SystemClock.sleep(200);
            SendType.SendForBLEDataType = "END";
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (totleSec < 99) {
            SendType.SendForBLEDataType = "INTER000" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
            SystemClock.sleep(500);
            SendType.SendForBLEDataType = "START";
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
            SystemClock.sleep(500);
            SendType.SendForBLEDataType = "END";
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        }
    }


    /**
     * 傳送大部分系列的數值
     */
    private void RunEditText(AlertDialog dialog) {
        Double inputValue = mtoDouble(edInput.getText().toString().trim());
        switch (edInput.getHint().toString()) {
            case "-5~5":
                if (edInput.length() > 0) {
                    if (inputValue > 5) {
                        edInput.setText("5");
                    } else if (inputValue < -5) {
                        edInput.setText("-5");
                    } else {
                        sendData2BT(inputValue, transSreing(selectName));
                        dialog.dismiss();
                    }
                } else {
                    setToast();
                }
                break;

            case "-20~20":
                if (edInput.length() > 0) {
                    if (inputValue > 20) {
                        edInput.setText("20");
                    } else if (inputValue < -20) {
                        edInput.setText("-20");
                    } else {
                        sendData2BT(inputValue, transSreing(selectName));
                        dialog.dismiss();
                    }

                } else {
                    setToast();
                }
                break;

            case "-10~100":
                if (edInput.length() > 0) {
                    if (inputValue > 100) {
                        edInput.setText("100");
                    } else if (inputValue < -10) {
                        edInput.setText("-10");
                    } else {
                        sendData2BT(inputValue, transSreing(selectName));
                        dialog.dismiss();
                    }

                } else {
                    setToast();
                }
                break;

            case "0~100":
                if (edInput.length() > 0) {
                    if (inputValue > 100) {
                        edInput.setText("100");
                    } else if (inputValue < 0) {
                        edInput.setText("0");
                    } else {
                        sendData2BT(inputValue, transSreing(selectName));
                        dialog.dismiss();
                    }

                } else {
                    setToast();
                }
                break;
            case "-10~65":
                if (edInput.length() > 0) {
                    if (inputValue > 65) {
                        edInput.setText("65");
                    } else if (inputValue < -10) {
                        edInput.setText("-10");
                    } else {
                        sendData2BT(inputValue, transSreing(selectName));
                        dialog.dismiss();
                    }

                } else {
                    setToast();
                }
                break;
            case "-999~9999"://小數點未開其他系列
                if (edInput.length() > 0) {
                    if (inputValue > 9999) {
                        edInput.setText("9999");
                    } else if (inputValue < -999) {
                        edInput.setText("-999");
                    } else {
                        sendData2BT(inputValue, transSreing(selectName));
                        dialog.dismiss();
                    }

                } else {
                    setToast();
                }
                break;
            case "-999~999"://小數點未開補正系列
                if (edInput.length() > 0) {
                    if (inputValue > 999) {
                        edInput.setText("999");
                    } else if (inputValue < -999) {
                        edInput.setText("-999");
                    } else {
                        sendData2BT(inputValue, transSreing(selectName));
                        dialog.dismiss();
                    }

                } else {
                    setToast();
                }
                break;
            case "-199.9~999.9":
                if (edInput.length() > 0) {
                    if (inputValue > 999.9) {
                        edInput.setText("999.9");
                    } else if (inputValue < -199.9) {
                        edInput.setText("-199.9");
                    } else {
                        sendData2BT(inputValue, transSreing(selectName));
                        dialog.dismiss();
                    }

                } else {
                    setToast();
                }
                break;
            case "-99.9~99.9":
                if (edInput.length() > 0) {
                    if (inputValue > 99.9) {
                        edInput.setText("99.9");
                    } else if (inputValue < -99.9) {
                        edInput.setText("-99.9");
                    } else {
                        sendData2BT(inputValue, transSreing(selectName));
                        dialog.dismiss();
                    }

                } else {
                    setToast();
                }
                break;
            case "-500~500":
                if (edInput.length() > 0) {
                    if (inputValue > 500) {
                        edInput.setText("500");
                    } else if (inputValue < -500) {
                        edInput.setText("-500");
                    } else {
                        sendData2BT(inputValue, transSreing(selectName));
                        dialog.dismiss();
                    }

                } else {
                    setToast();
                }
                break;
            case "0~300":
                if (edInput.length() > 0) {
                    if (inputValue > 300) {
                        edInput.setText("300");
                    } else if (inputValue < 0) {
                        edInput.setText("0");
                    } else {
                        sendData2BT(inputValue, transSreing(selectName));
                        dialog.dismiss();
                    }

                } else {
                    setToast();
                }
                break;
            case "0~1000":
                if (edInput.length() > 0) {
                    if (inputValue > 1000) {
                        edInput.setText("1000");
                    } else if (inputValue < 0) {
                        edInput.setText("0");
                    } else {
                        sendData2BT(inputValue, transSreing(selectName));
                        dialog.dismiss();
                    }

                } else {
                    setToast();
                }
                break;
            case "0~2000":
                if (edInput.length() > 0) {
                    if (inputValue > 2000) {
                        edInput.setText("2000");
                    } else if (inputValue < 0) {
                        edInput.setText("0");
                    } else {
                        sendData2BT(inputValue, transSreing(selectName));
                        dialog.dismiss();
                    }

                } else {
                    setToast();
                }
                break;
            case "0~3000":
                if (edInput.length() > 0) {
                    if (inputValue > 3000) {
                        edInput.setText("3000");
                    } else if (inputValue < 0) {
                        edInput.setText("0");
                    } else {
                        sendData2BT(inputValue, transSreing(selectName));
                        dialog.dismiss();
                    }

                } else {
                    setToast();
                }
                break;
            case "0~4000":
                if (edInput.length() > 0) {
                    if (inputValue > 4000) {
                        edInput.setText("4000");
                    } else if (inputValue < 0) {
                        edInput.setText("0");
                    } else {
                        sendData2BT(inputValue, transSreing(selectName));
                        dialog.dismiss();
                    }

                } else {
                    setToast();
                }
                break;

                default://隨便送了拉..
                    sendData2BT(inputValue, transSreing(selectName));
                    dialog.dismiss();
                    break;

        }//switch

    }

    /**
     * 將取得的選項名稱轉回至英文代號（未完工），2019/9/3號記載
     */
    public String transSreing(String input) {

        switch (SendType.FirstWord) {

            case 'T':
                if (input.contains(trans(R.string.Temperature) + trans(R.string.PV))) {
                    return "PV1";
                } else if (input.contains(trans(R.string.Temperature) + trans(R.string.EH))) {
                    return "EH1";
                } else if (input.contains(trans(R.string.Temperature) + trans(R.string.EL))) {
                    return "EL1";
                } else if (input.contains(trans(R.string.Temperature) + trans(R.string.CR))) {
                    return "CR1";
                }else if (input.contains(trans(R.string.Temperature) + trans(R.string.HH))) {
                    return "HH1";
                }else if (input.contains(trans(R.string.Temperature) + trans(R.string.LL))) {
                    return "LL1";
                }

                break;

            case 'H':

                break;

            case 'I':
                if (input.contains(trans(R.string.FirstRow) + trans(R.string.IH))) {//最大量程
                    return "IH1";
                } else if (input.contains(trans(R.string.FirstRow) + trans(R.string.IL))) {//最小量程
                    return "IL1";
                } else if (input.contains(trans(R.string.FirstRow) + trans(R.string.EH))) {//上限
                    return "EH1";
                }else if (input.contains(trans(R.string.FirstRow) + trans(R.string.EL))) {//下限
                    return "EL1";
                }else if (input.contains(trans(R.string.FirstRow) + trans(R.string.PV))) {//補正
                    return "PV1";
                }else if (input.contains(trans(R.string.FirstRow) + trans(R.string.CR))) {//顏色
                    return "CR1";
                }else if (input.contains(trans(R.string.FirstRow) + trans(R.string.decimal_point))) {//小數點
                    return "DP1";
                }
                else if (input.contains(trans(R.string.FirstRow) + trans(R.string.HH))) {
                    return "HH1";
                }else if (input.contains(trans(R.string.FirstRow) + trans(R.string.LL))) {
                    return "LL1";
                }
                break;

            case 'C':
            case 'D':
            case 'E':
                if (input.contains(trans(R.string.CO2) + trans(R.string.PV))) {
                    return "PV1";
                } else if (input.contains(trans(R.string.CO2) + trans(R.string.EH))) {
                    return "EH1";
                } else if (input.contains(trans(R.string.CO2) + trans(R.string.EL))) {
                    return "EL1";
                } else if (input.contains(trans(R.string.CO2) + trans(R.string.CR))) {
                    return "CR1";
                }
                else if (input.contains(trans(R.string.CO2) + trans(R.string.HH))) {
                    return "HH1";
                }else if (input.contains(trans(R.string.CO2) + trans(R.string.LL))) {
                    return "LL1";
                }
                break;

        }
        switch (SendType.SecondWord) {
            case 'T':

                break;
            case 'H':
                if (input.contains(trans(R.string.Humidity) + trans(R.string.PV))) {
                    return "PV2";
                } else if (input.contains(trans(R.string.Humidity) + trans(R.string.EH))) {
                    return "EH2";
                } else if (input.contains(trans(R.string.Humidity) + trans(R.string.EL))) {
                    return "EL2";
                } else if (input.contains(trans(R.string.Humidity) + trans(R.string.CR))) {
                    return "CR2";
                } else if (input.contains(trans(R.string.Humidity) + trans(R.string.HH))) {
                    return "HH2";
                } else if (input.contains(trans(R.string.Humidity) + trans(R.string.LL))) {
                    return "LL2";
                }
                break;
            case 'I':
                if (input.contains(trans(R.string.SecondRow) + trans(R.string.IH))) {//最大量程
                    return "IH2";
                } else if (input.contains(trans(R.string.SecondRow) + trans(R.string.IL))) {//最小量程
                    return "IL2";
                } else if (input.contains(trans(R.string.SecondRow) + trans(R.string.EH))) {//上限
                    return "EH2";
                }else if (input.contains(trans(R.string.SecondRow) + trans(R.string.EL))) {//下限
                    return "EL2";
                }else if (input.contains(trans(R.string.SecondRow) + trans(R.string.PV))) {//補正
                    return "PV2";
                }else if (input.contains(trans(R.string.SecondRow) + trans(R.string.CR))) {//顏色
                    return "CR2";
                }else if (input.contains(trans(R.string.SecondRow) + trans(R.string.decimal_point))) {//小數點
                    return "DP2";
                } else if (input.contains(trans(R.string.SecondRow) + trans(R.string.HH))) {
                    return "HH2";
                } else if (input.contains(trans(R.string.SecondRow) + trans(R.string.LL))) {
                    return "LL2";
                }
                break;
            case 'C':
            case 'D':
            case 'E':
                if (input.contains(trans(R.string.CO2) + trans(R.string.PV))) {
                    return "PV2";
                } else if (input.contains(trans(R.string.CO2) + trans(R.string.EH))) {
                    return "EH2";
                } else if (input.contains(trans(R.string.CO2) + trans(R.string.EL))) {
                    return "EL2";
                } else if (input.contains(trans(R.string.CO2) + trans(R.string.CR))) {
                    return "CR2";
                } else if (input.contains(trans(R.string.CO2) + trans(R.string.HH))) {
                    return "HH2";
                } else if (input.contains(trans(R.string.CO2) + trans(R.string.LL))) {
                    return "LL2";
                }

                break;
            case 'M':
                if (input.contains(trans(R.string.PM2_5) + trans(R.string.PV))) {
                    return "PV2";
                } else if (input.contains(trans(R.string.PM2_5) + trans(R.string.EH))) {
                    return "EH2";
                } else if (input.contains(trans(R.string.PM2_5) + trans(R.string.EL))) {
                    return "EL2";
                } else if (input.contains(trans(R.string.PM2_5) + trans(R.string.CR))) {
                    return "CR2";
                } else if (input.contains(trans(R.string.PM2_5) + trans(R.string.HH))) {
                    return "HH2";
                } else if (input.contains(trans(R.string.PM2_5) + trans(R.string.LL))) {
                    return "LL2";
                }
                break;
        }
        switch (SendType.ThirdWord){
            case 'T':

                break;
            case 'H':

                break;
            case 'I':
                if (input.contains(trans(R.string.ThirdRow) + trans(R.string.IH))) {//最大量程
                    return "IH3";
                } else if (input.contains(trans(R.string.ThirdRow) + trans(R.string.IL))) {//最小量程
                    return "IL3";
                } else if (input.contains(trans(R.string.ThirdRow) + trans(R.string.EH))) {//上限
                    return "EH3";
                }else if (input.contains(trans(R.string.ThirdRow) + trans(R.string.EL))) {//下限
                    return "EL3";
                }else if (input.contains(trans(R.string.ThirdRow) + trans(R.string.PV))) {//補正
                    return "PV3";
                }else if (input.contains(trans(R.string.ThirdRow) + trans(R.string.CR))) {//顏色
                    return "CR3";
                }else if (input.contains(trans(R.string.ThirdRow) + trans(R.string.decimal_point))) {//小數點
                    return "DP3";
                }
                else if (input.contains(trans(R.string.ThirdRow) + trans(R.string.HH))) {
                    return "HH3";
                } else if (input.contains(trans(R.string.ThirdRow) + trans(R.string.LL))) {
                    return "LL3";
                }
                break;
            case 'C':
            case 'D':
            case 'E':
                if (input.contains(trans(R.string.CO2) + trans(R.string.PV))) {
                    return "PV3";
                } else if (input.contains(trans(R.string.CO2) + trans(R.string.EH))) {
                    return "EH3";
                } else if (input.contains(trans(R.string.CO2) + trans(R.string.EL))) {
                    return "EL3";
                } else if (input.contains(trans(R.string.CO2) + trans(R.string.CR))) {
                    return "CR3";
                } else if (input.contains(trans(R.string.CO2) + trans(R.string.HH))) {
                    return "HH3";
                } else if (input.contains(trans(R.string.CO2) + trans(R.string.LL))) {
                    return "LL3";
                }
                break;


        }


        return "123";
    }

    /**
     * 將開關系列資料送至藍芽
     */
    public void sendData2BTWithSwitch() {
        Log.v("BT", "選擇警報");
        swInput.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("BT", "Here" + isChecked);
                if (isChecked == true) {
                    Log.v("BT", "Here" + isChecked);
                    SendType.SendForBLEDataType = SendType.SPK + "+0001.0";
                    SendType.getSendBluetoothLeService.
                            setCharacteristicNotification(SendType.Mycharacteristic, true);

                } else {
                    Log.v("BT", "Here" + isChecked);
                    SendType.SendForBLEDataType = SendType.SPK + "+0000.0";
                    SendType.getSendBluetoothLeService.
                            setCharacteristicNotification(SendType.Mycharacteristic, true);
                }
            }
        });
    }

    /**傳送小數點資料*/
    private void sendData2BTWithDP_Switch(final String selectName){
        Log.v("BT", "選擇小數點" +selectName);
        swInput.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    if (selectName.contains(trans(R.string.FirstRow) + trans(R.string.decimal_point))){

                        SendType.SendForBLEDataType = "DP1+0001.0";
                    }else if(selectName.contains(trans(R.string.SecondRow) + trans(R.string.decimal_point))){
                        SendType.SendForBLEDataType = "DP2+0001.0";
                    }else if(selectName.contains(trans(R.string.ThirdRow) + trans(R.string.decimal_point))){
                        SendType.SendForBLEDataType = "DP3+0001.0";
                    }
                    SendType.getSendBluetoothLeService.
                            setCharacteristicNotification(SendType.Mycharacteristic, true);
                }else{
                    if (selectName.contains(trans(R.string.FirstRow) + trans(R.string.decimal_point))){
                        SendType.SendForBLEDataType = "DP1+0000.0";
                    }else if(selectName.contains(trans(R.string.SecondRow) + trans(R.string.decimal_point))){
                        SendType.SendForBLEDataType = "DP2+0000.0";
                    }else if(selectName.contains(trans(R.string.ThirdRow) + trans(R.string.decimal_point))){
                        SendType.SendForBLEDataType = "DP3+0000.0";
                    }
                    SendType.getSendBluetoothLeService.
                            setCharacteristicNotification(SendType.Mycharacteristic, true);
                }
            }
        });
    }

    /**
     * 將一般資料送至藍芽
     */
    public void sendData2BT(Double input, String inputType) {

        if (input >= 1000) {
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "+" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (input >= 100 && input <= 999.9) {
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "+0" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (input >= 10 && input <= 99.9) {
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "+00" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (input >= 0 && input <= 9.9) {
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "+000" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);

        } else if (input >= -9.9 && input <= 0) {
            input = Math.abs(input);
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "-000" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (input >= -99.9 && input <= -10) {
            input = Math.abs(input);
            input = Math.abs(input);
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "-00" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (input >= -999.9 && input <= -100) {
            input = Math.abs(input);
            input = Math.abs(input);
            input = Math.abs(input);
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "-0" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (input <= 1000) {
            input = Math.abs(input);
            input = Math.abs(input);
            input = Math.abs(input);
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "-" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);

        }
        Log.v("BT","送出的值: "+ SendType.SendForBLEDataType);


    }

    /**
     * 設置吐司
     */
    private void setToast() {//設置吐司
        Toast.makeText(cox, R.string.dont_blank, Toast.LENGTH_SHORT).show();
    }

    /**
     * 翻譯R.string.xxx用的
     */
    public String trans(int name) {//不是我在講...每個都要寫ctx.getResources().getString(R.string.??);真的會死人
        String str = cox.getResources().getString(name);
        return str;
    }

    /**
     * 將取得的String轉為Double用的，可有效防止前面輸入0
     */
    public Double mtoDouble(String in) {//轉Double
        Double out = Double.parseDouble(in);

        return out;
    }

}
