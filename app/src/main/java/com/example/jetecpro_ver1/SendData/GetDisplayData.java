package com.example.jetecpro_ver1.SendData;

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
                transformToswitch(data);
                SendType.mDP1 = d.transformToswitch(data);
                break;

            case "DP2":
                SendType.DP2 = getMain;
                transformToswitch(data);
                SendType.mDP2 = d.transformToswitch(data);
                break;

            case "DP3":
                SendType.DP3 = getMain;
                transformToswitch(data);
                SendType.mDP3 = d.transformToswitch(data);
                break;

            case "SPK":
                SendType.SPK = getMain;
                transformToswitch(data);
                SendType.mSPK = d.transformToswitch(data);
                break;

        }//Switch
        if (data.contains("COUNT")) {
            SendType.COUNT = data.substring(0,5);
            Double count = Double.valueOf(data.substring(5,10));
            String co    = String.valueOf(count);
            SendType.mCOUNT = co;

        }else if(data.contains("INTER")){
            SendType.INTER = data.substring(0,5);
            inter(data);
            SendType.mINTER = inter(data);

        }else if(data.contains("DATE")){
            SendType.DATE = data.substring(0,4);
            Double count = Double.valueOf(data.substring(4,10));
            String co    = String.valueOf(count);
            SendType.mDATE = co;

        }else if(data.contains("TIME")){
            SendType.TIME = data.substring(0,4);
            Double count = Double.valueOf(data.substring(4,10));
            String co    = String.valueOf(count);
            SendType.mTIME = co;

        }else if(data.contains("LOG")){
            SendType.LOG = data.substring(0,3);
            SendType.mLOG = data.substring(3);

        }


    }
    private String inter (String in){
        String to = in.substring(6,10);
        SendType.INTER2SQL = to;
        int origin = Integer.parseInt(to);
        int sec = 0;
        int min = 0;
        int hour = 0;

        min = origin/60;
        sec = origin%60;
        if (min>=60){
            hour = min/60;
            min = min%60;
        }
         if(hour == 0 && min == 0){//0小時0分鐘x秒
             String strSec = String.valueOf(sec);
             return strSec+"s";
        }else if (hour == 0 && sec == 0){//0小時x分鐘0秒
             String strMin = String.valueOf(min);
             return strMin+"m";

         }else if(hour == 0 && sec != 0 && min != 0){//0小時x分鐘x秒
             String strSec = String.valueOf(sec);
             String strMin = String.valueOf(min);
             return strMin+"m"+strSec+"s";
         }else if(hour != 0 && min == 0 &sec == 0){//1小時0分鐘0秒 ->最大就1h了
             String strHour = String.valueOf(hour);
             return strHour+"h";
         }

        return "怪怪的";
    }

    private String transform(String in) {//偶很懶所以就...多寫這點鳥東西,
        //偶很懶所以就...多寫這點鳥東西
        //我也嫌煩..先轉過去才能正確取出小數點,才能適應所有數值傳遞，懂？
        String to = in.substring(3, 10);
        Double d = Double.valueOf(to);
        String s = String.valueOf(d);
        return s;

    }
    private String transformToswitch(String input){
        String str = input.substring(4,10);
        if(str.contains("0000.0")){
            String jugOn = "off";
            return jugOn;
        }else if(str.contains("0001.0")){
            String jugOff = "on";
            return jugOff;
        }else return "What the fuck?";

    }

}


