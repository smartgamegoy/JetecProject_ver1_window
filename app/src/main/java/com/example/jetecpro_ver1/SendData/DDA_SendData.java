package com.example.jetecpro_ver1.SendData;

import android.app.AlertDialog;
import android.content.Context;
import android.net.sip.SipSession;
import android.text.method.DialerKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jetecpro_ver1.R;
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


    public DDA_SendData(String selectName, String selectValues, EditText edInput, Switch swInput, Context cox
            , NumberPicker npHour, NumberPicker npMin, NumberPicker npSec) {

        this.selectName = selectName;
        this.selectValues = selectValues;
        this.edInput = edInput;
        this.swInput = swInput;
        this.cox = cox;
        this.npHour = npHour;
        this.npMin = npMin;
        this.npSec = npSec;

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
            if (selectName.contains(SendType.DP1)
                    || selectName.contains(SendType.DP2)
                    || selectName.contains(SendType.DP2)) {
                if (selectValues.contains("on")) {
                    swInput.setChecked(true);
                } else {
                    swInput.setChecked(false);
                }
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
                } else if (selectName.contains(SendType.EH1)) {
                    edInput.setHint("-10~65");
                } else if (selectName.contains(SendType.EL1)) {
                    edInput.setHint("-10~65");
                } else if (selectName.contains(SendType.CR1)) {
                    edInput.setHint("-10~65");
                }
                break;
            case 'H':

                break;
            case 'I':

                break;

        }
        switch ((SendType.SecondWord)) {

            case 'T':

                break;

            case 'H':
                if (selectName.contains(SendType.PV2)) {
                    edInput.setHint("-10~10");
                } else if (selectName.contains(SendType.EH2)) {
                    edInput.setHint("0~100");
                } else if (selectName.contains(SendType.EL2)) {
                    edInput.setHint("0~100");
                } else if (selectName.contains(SendType.CR2)) {
                    edInput.setHint("0~100");
                }
                break;

            case 'I':

                break;

        }
        switch ((SendType.ThirdWord)) {
            case 'T':


                break;

            case 'H':


                break;

            case 'I':

                break;


        }

        transSreing(selectName);
        mSwitch();
        if (selectName.contains(SendType.SPK)) {
            SendType.SPK = "SPK";
            sendData2BTWithSwitch();
        } else if (SendType.FirstWord == 'I'
                || SendType.SecondWord == 'I'
                || SendType.ThirdWord == 'I') {
            if (selectName.contains(SendType.DP1)
                    || selectName.contains(SendType.DP2)
                    || selectName.contains(SendType.DP2)) {
            }
        } else if (SendType.ThirdWord == 'L' && selectName.contains(SendType.INTER)) {
            timeSelect();
        } else if (selectName.contains(trans(R.string.device_name))) {
            edInput.setText(selectValues);
            edInput.setKeyListener(new EditText(cox).getKeyListener());
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        //分流功能＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (selectName.contains(trans(R.string.SPK))) {
                    dialog.dismiss();
                } else if (SendType.FirstWord == 'I'
                        || SendType.SecondWord == 'I'
                        || SendType.ThirdWord == 'I') {
                    if (selectName.contains(SendType.DP1)
                            || selectName.contains(SendType.DP2)
                            || selectName.contains(SendType.DP2)) {
                        dialog.dismiss();
                    }
                } else if (SendType.ThirdWord == 'L' && selectName.contains(SendType.INTER)) {
                    timeSend();
                    dialog.dismiss();

                } else if (selectName.contains(trans(R.string.device_name))) {
                    modifyDeviceName(dialog);
                } else {
                    RunEditText(dialog);
                }


            }//onClick
        });
    }//mAlertDialog

    /**
     * 傳送修改的裝置名稱
     */
    private void modifyDeviceName(AlertDialog dialog) {
        try {
            String s = "NAME"+edInput.getText().toString();
            byte[] b3 = s.getBytes("UTF-8");
            Log.v("BT", String.valueOf(b3.length));
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
                    npSec.setMinValue(0);
                    npMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            if (newVal == 0) {
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
        if (totleSec >= 1000) {
            SendType.SendForBLEDataType = "INTER0" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (totleSec <= 999 && totleSec >= 100) {
            SendType.SendForBLEDataType = "INTER00" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (totleSec < 99) {
            SendType.SendForBLEDataType = "INTER000" + out;
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

            case "-10~10":
                if (edInput.length() > 0) {
                    if (inputValue > 10) {
                        edInput.setText("10");
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
                }

                break;

            case 'H':
                SendType.PV1 = "PV1";
                SendType.EH1 = "EH1";
                SendType.EL1 = "EL1";
                SendType.CR1 = "CR1";
                break;

            case 'I':
                SendType.PV1 = "PV1";
                SendType.EH1 = "EH1";
                SendType.EL1 = "EL1";
                SendType.CR1 = "CR1";
                SendType.DP1 = "DP1";
                break;

            case 'C':

                break;

            case 'D':

                break;

            case 'E':

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
                }
                break;
            case 'I':

                break;
            case 'C':

                break;
            case 'D':

                break;
            case 'E':

                break;
        }


        return "123";
    }

    /**
     * 將開關系列資料送至藍芽
     */
    public void sendData2BTWithSwitch() {
        Log.v("BT", "Here");
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

    /**
     * 將一般資料送至藍芽
     */
    public void sendData2BT(Double input, String inputType) {

        if (input >= 1000) {
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "+" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (input >= 100 && input <= 999) {
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "+0" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (input >= 10 && input <= 99) {
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "+00" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (input >= 0 && input <= 9) {
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "+000" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);

        } else if (input >= -9 && input <= 0) {
            input = Math.abs(input);
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "-000" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (input >= -99 && input <= -10) {
            input = Math.abs(input);
            input = Math.abs(input);
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType + "-00" + out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        } else if (input >= -999 && input <= -100) {
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
