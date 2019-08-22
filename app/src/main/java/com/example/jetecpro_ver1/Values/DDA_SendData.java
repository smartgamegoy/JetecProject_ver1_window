package com.example.jetecpro_ver1.Values;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jetecpro_ver1.R;

public class DDA_SendData {
    String selectName;
    String selectValues;
    EditText edInput;
    Context cox;


    public DDA_SendData(String selectName, String selectValues, EditText edInput, Context cox){

        this.selectName= selectName;
        this.selectValues = selectValues;
        this.edInput = edInput;
        this.cox = cox;

    }

    public void mAlertDialog(AlertDialog.Builder builder){
        final AlertDialog dialog = builder.create();
        if (selectName.contains(trans(R.string.device_name))){
            edInput.setText(selectValues);

        }
        switch (SendType.FirstWord){
            case 'T':
                if(selectName.contains(SendType.PV1)){
                    edInput.setHint("-5~5");
                }else if(selectName.contains(SendType.EH1)){
                    edInput.setHint("-10~65");
                }else if(selectName.contains(SendType.EL1)){
                    edInput.setHint("-10~65");
                }else if(selectName.contains(SendType.CR1)){
                    edInput.setHint("-10~65");
                }
                break;
            case 'H':

                break;
            case 'I':

                break;

        }
        switch ((SendType.SecondWord)){

            case 'T':

                break;

            case 'H':
                if(selectName.contains(SendType.PV2)){
                    edInput.setHint("-10~10");
                }else if(selectName.contains(SendType.EH2)){
                    edInput.setHint("0~100");
                }else if(selectName.contains(SendType.EL2)){
                    edInput.setHint("0~100");
                }else if(selectName.contains(SendType.CR2)){
                    edInput.setHint("0~100");
                }
                break;

            case 'I':

                break;

        }switch ((SendType.ThirdWord)){
            case 'T':


                break;

            case 'H':


                break;

            case 'I':

                break;


        }
        transSreing(selectName);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double inputValue = mtoDouble(edInput.getText().toString().trim());
                switch (edInput.getHint().toString()){
                    case "-5~5":
                        if(edInput.length()>0){
                            if(inputValue>5){
                                edInput.setText("5");
                            }else if(inputValue<-5){
                                edInput.setText("-5");
                            }else{
                                sendData2BT(inputValue,transSreing(selectName));
                                dialog.dismiss();
                            }
                        } else{ setToast(); }
                        break;

                    case "-10~10":
                        if(edInput.length()>0){
                            if(inputValue>10){
                                edInput.setText("10");
                            }else if(inputValue<-10){
                                edInput.setText("-10");
                            }else{
                                sendData2BT(inputValue,transSreing(selectName));
                                dialog.dismiss();
                            }

                        } else{ setToast(); }
                        break;

                    case "-10~100":
                        if(edInput.length()>0){
                            if(inputValue>100){
                                edInput.setText("100");
                            }else if(inputValue<-10){
                                edInput.setText("-10");
                            }else{
                                sendData2BT(inputValue,transSreing(selectName));
                                dialog.dismiss();
                            }

                        } else{ setToast(); }
                        break;

                    case "0~100":
                        if(edInput.length()>0){
                            if(inputValue>100){
                                edInput.setText("100");
                            }else if(inputValue<0){
                                edInput.setText("0");
                            }else{
                                sendData2BT(inputValue,transSreing(selectName));
                                dialog.dismiss();
                            }

                        } else{ setToast(); }
                        break;
                    case "-10~65":
                        if(edInput.length()>0){
                            if(inputValue>65){
                                edInput.setText("65");
                            }else if(inputValue<-10){
                                edInput.setText("-10");
                            }else{
                                sendData2BT(inputValue,transSreing(selectName));
                                dialog.dismiss();
                            }

                        } else{ setToast(); }
                        break;

                }//switch

            }//onClick
        });
    }//mAlertDialog
    public String trans(int name) {//不是我在講...每個都要寫ctx.getResources().getString(R.string.??);真的會死人
        String str = cox.getResources().getString(name);
        return str;
    }
    public Double mtoDouble(String in){
        Double out = Double.parseDouble(in);

        return out;
    }

    private void setToast(){
        Toast.makeText(cox,R.string.dont_blank,Toast.LENGTH_SHORT).show();
    }
    public void sendData2BT(Double input,String inputType){

        if (input>=1000){
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType+"+"+out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        }else if(input>=100 && input<=999){
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType+"+0"+out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        }else if(input>=10 && input <=99){
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType+"+00"+out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        }else if(input>=0 && input <=9){
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType+"+000"+out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);

        }else if(input>=-9 && input <= 0){
            input = Math.abs(input);
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType+"-000"+out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        }else if(input>=-99 && input <= -10){
            input = Math.abs(input);
            input = Math.abs(input);
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType+"-00"+out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        }else if(input>=-999 && input <=-100){
            input = Math.abs(input);
            input = Math.abs(input);
            input = Math.abs(input);
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType+"-0"+out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);
        }else if(input<=1000){
            input = Math.abs(input);
            input = Math.abs(input);
            input = Math.abs(input);
            String out = String.valueOf(input);
            SendType.SendForBLEDataType = inputType+"-"+out;
            SendType.getSendBluetoothLeService.
                    setCharacteristicNotification(SendType.Mycharacteristic, true);

        }


    }
    public String transSreing(String input){

        switch (SendType.FirstWord) {

            case 'T':
                if (input.contains(trans(R.string.Temperature)+trans(R.string.PV))){
                    return "PV1";
                }else if (input.contains(trans(R.string.Temperature)+trans(R.string.EH))){
                    return "EH1";
                }else if (input.contains(trans(R.string.Temperature)+trans(R.string.EL))){
                    return "EL1";
                }else if (input.contains(trans(R.string.Temperature)+trans(R.string.CR))){
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
                SendType.PV2 = trans(R.string.Temperature) + trans(R.string.PV);
                SendType.EH2 = trans(R.string.Temperature) + trans(R.string.EH);
                SendType.EL2 = trans(R.string.Temperature) + trans(R.string.EL);
                SendType.CR2 = trans(R.string.Temperature) + trans(R.string.CR);
                break;
            case 'H':
                if (input.contains(trans(R.string.Humidity)+trans(R.string.PV))){
                    return "PV2";
                }else if (input.contains(trans(R.string.Humidity)+trans(R.string.EH))){
                    return "EH2";
                }else if (input.contains(trans(R.string.Humidity)+trans(R.string.EL))){
                    return "EL2";
                }else if (input.contains(trans(R.string.Humidity)+trans(R.string.CR))){
                    return "CR2";
                }
                break;
            case 'I':
                SendType.PV2 = trans(R.string.SecondRow) + trans(R.string.PV);
                SendType.IH2 = trans(R.string.SecondRow) + trans(R.string.IH);
                SendType.IL2 = trans(R.string.SecondRow) + trans(R.string.IL);
                SendType.EH2 = trans(R.string.SecondRow) + trans(R.string.EH);
                SendType.EL2 = trans(R.string.SecondRow) + trans(R.string.EL);
                SendType.CR2 = trans(R.string.SecondRow) + trans(R.string.CR);
                SendType.DP2 = trans(R.string.SecondRow) + trans(R.string.decimal_point);
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

}
