package com.example.jetecpro_ver1.SendData;

import android.util.Log;

import com.example.jetecpro_ver1.Values.SendType;

public class GetDisplayData {

    String data;

    public GetDisplayData(String data) {
        this.data = data;
    }

    public void GetOK() {
        SendType.SendForBLEDataType = "PASSWD";
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
    }

    public void sendGet() {
        SendType.SendForBLEDataType = "get";
        SendType.getSendBluetoothLeService.
                setCharacteristicNotification(SendType.Mycharacteristic, true);
    }

    public void analysisData(String getMain) {
        GetDisplayData d = new GetDisplayData(data);
//用的副程式在最底下....transform 跟 transformToswitch,inter是轉時間用的
        switch (getMain) {
            case "PV1":
                SendType.PV1 = getMain;
                transform(data);
                SendType.mPV1 = transform(data);
                break;

            case "PV2":
                SendType.PV2 = getMain;
                transform(data);
                SendType.mPV2 = d.transform(data);
                break;

            case "PV3":
                SendType.PV3 = getMain;
                transform(data);
                SendType.mPV3 = d.transform(data);
                break;

            case "EH1":
                SendType.EH1 = getMain;
                transform(data);
                SendType.mEH1 = d.transform(data);
                break;

            case "EH2":
                SendType.EH2 = getMain;
                transform(data);
                SendType.mEH2 = d.transform(data);
                break;

            case "EH3":
                SendType.EH3 = getMain;
                transform(data);
                SendType.mEH3 = d.transform(data);
                break;

            case "EL1":
                SendType.EL1 = getMain;
                transform(data);
                SendType.mEL1 = d.transform(data);
                break;

            case "EL2":
                SendType.EL2 = getMain;
                transform(data);
                SendType.mEL2 = d.transform(data);
                break;

            case "EL3":
                SendType.EL3 = getMain;
                transform(data);
                SendType.mEL3 = d.transform(data);
                break;

            case "IH1":
                SendType.IH1 = getMain;
                transform(data);
                SendType.mIH1 = d.transform(data);
                break;

            case "IH2":
                SendType.IH2 = getMain;
                transform(data);
                SendType.mIH2 = d.transform(data);
                break;

            case "IH3":
                SendType.IH3 = getMain;
                transform(data);
                SendType.mIH3 = d.transform(data);
                break;

            case "IL1":
                SendType.IL1 = getMain;
                transform(data);
                SendType.mIL1 = d.transform(data);
                break;

            case "IL2":
                SendType.IL2 = getMain;
                transform(data);
                SendType.mIL2 = d.transform(data);
                break;

            case "IL3":
                SendType.IL3 = getMain;
                transform(data);
                SendType.mIL3 = d.transform(data);
                break;

            case "CR1":
                SendType.CR1 = getMain;
                transform(data);
                SendType.mCR1 = d.transform(data);
                break;

            case "CR2":
                SendType.CR2 = getMain;
                transform(data);
                SendType.mCR2 = d.transform(data);
                break;

            case "CR3":
                SendType.CR3 = getMain;
                transform(data);
                SendType.mCR3 = d.transform(data);
                break;

            case "DP1":
                SendType.DP1 = getMain;
                transform(data);
                SendType.mDP1 = d.transfromTodpFuntion(data);
                SendType.dpNumberSelectedDP1 = Integer.parseInt(SendType.mDP1);
//                transformToswitch(data);
//                SendType.mDP1 = d.transformToswitch(data);

                break;

            case "DP2":
                SendType.DP2 = getMain;
                transform(data);
                SendType.mDP2 = d.transfromTodpFuntion(data);
                SendType.dpNumberSelectedDP2 = Integer.parseInt(SendType.mDP2);
//                transformToswitch(data);
//                SendType.mDP2 = d.transformToswitch(data);
                break;

            case "DP3":
                SendType.DP3 = getMain;
                transform(data);
                SendType.mDP3 = d.transfromTodpFuntion(data);
                SendType.dpNumberSelectedDP3 = Integer.parseInt(SendType.mDP3);
//                transformToswitch(data);
//                SendType.mDP3 = d.transformToswitch(data);
                break;

            case "SPK":
                SendType.SPK = getMain;
                transformToswitch(data);
                SendType.mSPK = d.transformToswitch(data);
                break;

        }//Switch
        if (data.contains("COUNT")) {
            SendType.COUNT = data.substring(0, 5);
            int count = Integer.parseInt(data.substring(5, 10));
            SendType.Count2Send = data.substring(5, 10);
            String co = String.valueOf(count);
            SendType.mCOUNT = co;

        } else if (data.contains("INTER")) {
            SendType.INTER = data.substring(0, 5);
            inter(data);
//            SendType.mINTER = inter(data);
            SendType.mINTER = inter(data);

        } else if (data.contains("DATE")) {
            SendType.DATE = data.substring(0, 4);
            Double count = Double.valueOf(data.substring(4, 10));
            String co = String.valueOf(count);
            SendType.mDATE = co;

        } else if (data.contains("TIME")) {
            SendType.TIME = data.substring(0, 4);
            Double count = Double.valueOf(data.substring(4, 10));
            String co = String.valueOf(count);
            SendType.mTIME = co;
            SendType.mTIME2COUNT = data.substring(4, 10);

        } else if (data.contains("LOG")) {
            SendType.LOG = data.substring(0, 3);
            SendType.mLOG = data.substring(3);

        } else if (data.contains("NAME")) {

            String dataaaa = data.replace("\n", "$");
            String dateeee = dataaaa.substring(4, dataaaa.indexOf("$"));

            SendType.DeviceName = dateeee;
        }


    }

    private String inter(String in) {
        String to = in.substring(6, 10);
        SendType.INTER2SQL = to;
        int origin = Integer.parseInt(to);
        int sec = 0;
        int min = 0;
        int hour = 0;

        min = origin / 60;
        sec = origin % 60;
        if (min >= 60) {
            hour = min / 60;
            min = min % 60;
        }
        if (hour == 0 && min == 0) {//0小時0分鐘x秒
            String strSec = String.valueOf(sec);
            return strSec + "s";
        } else if (hour == 0 && sec == 0) {//0小時x分鐘0秒
            String strMin = String.valueOf(min);
            return strMin + "m";

        } else if (hour == 0 && sec != 0 && min != 0) {//0小時x分鐘x秒
            String strSec = String.valueOf(sec);
            String strMin = String.valueOf(min);
            return strMin + "m" + strSec + "s";
        } else if (hour != 0 && min == 0 & sec == 0) {//1小時0分鐘0秒 ->最大就1h了
            String strHour = String.valueOf(hour);
            return strHour + "h";
        }

        return "怪怪的";
    }

    private String transform(String in) {//偶很懶所以就...多寫這點鳥東西,
        //偶很懶所以就...多寫這點鳥東西
        //我也嫌煩..先轉過去才能正確取出小數點,才能適應所有數值傳遞，懂？
        String s = null;
    if (data.charAt(2) == '1'){
        switch (SendType.dpNumberSelectedDP1) {
            case 0:
                String to = in.substring(3, 10);
                Double d = Double.valueOf(to);
                s = String.valueOf(d);
                break;
            case 1:
                String to1 = in.substring(3, 10);
                Double d1 = Double.valueOf(to1);
                Double d11 = d1 / 10;
                s = String.valueOf(d11);
                break;
            case 2:
                String to2 = in.substring(3, 10);
                Double d2 = Double.valueOf(to2);
                Double d22 = d2 / 100;
                s = String.valueOf(d22);
                break;
            case 3:
                if (data.contains("PV1")){
                    String to3 = in.substring(3, 10);
                    Double d3 = Double.valueOf(to3);
                    Double d33 = d3 / 100;
                    s = String.valueOf(d33);
                }else {
                    String to3 = in.substring(3, 10);
                    Double d3 = Double.valueOf(to3);
                    Double d33 = d3 / 1000;
                    s = String.valueOf(d33);
                }

                break;
        }
    }else if(data.charAt(2) == '2'){
        switch (SendType.dpNumberSelectedDP2) {
            case 0:
                String to = in.substring(3, 10);
                Double d = Double.valueOf(to);
                s = String.valueOf(d);
                break;
            case 1:
                String to1 = in.substring(3, 10);
                Double d1 = Double.valueOf(to1);
                Double d11 = d1 / 10;
                s = String.valueOf(d11);
                break;
            case 2:
                String to2 = in.substring(3, 10);
                Double d2 = Double.valueOf(to2);
                Double d22 = d2 / 100;
                s = String.valueOf(d22);
                break;
            case 3:
                if (data.contains("PV2")){
                    String to3 = in.substring(3, 10);
                    Double d3 = Double.valueOf(to3);
                    Double d33 = d3 / 100;
                    s = String.valueOf(d33);
                }else{
                    String to3 = in.substring(3, 10);
                    Double d3 = Double.valueOf(to3);
                    Double d33 = d3 / 1000;
                    s = String.valueOf(d33);
                }

                break;
        }
    }else if(data.charAt(2) == '3'){
        switch (SendType.dpNumberSelectedDP3) {
            case 0:
                String to = in.substring(3, 10);
                Double d = Double.valueOf(to);
                s = String.valueOf(d);
                break;
            case 1:
                String to1 = in.substring(3, 10);
                Double d1 = Double.valueOf(to1);
                Double d11 = d1 / 10;
                s = String.valueOf(d11);
                break;
            case 2:
                String to2 = in.substring(3, 10);
                Double d2 = Double.valueOf(to2);
                Double d22 = d2 / 100;
                s = String.valueOf(d22);
                break;
            case 3:
                if (data.contains("PV3")){
                    String to3 = in.substring(3, 10);
                    Double d3 = Double.valueOf(to3);
                    Double d33 = d3 / 100;
                    s = String.valueOf(d33);
                }else {
                    String to3 = in.substring(3, 10);
                    Double d3 = Double.valueOf(to3);
                    Double d33 = d3 / 1000;
                    s = String.valueOf(d33);
                    break;
                }
        }
    }



        return s;
    }

    private String transformToswitch(String input) {
        String str = input.substring(4, 10);
        if (str.contains("0000.0")) {
            String jugOn = "off";
            return jugOn;
        } else if (str.contains("0001.0")) {
            String jugOff = "on";
            return jugOff;
        } else return "What the fuck?";

    }

    private String transfromTodpFuntion(String input) {
        String to = input.substring(3, 8);
//        Log.v("BT","trans:"+to);
        int d = Integer.parseInt(to);
        String s = String.valueOf(d);
        return s;
    }

}


