package com.example.jetecpro_ver1.SendData;

import android.content.Context;
import android.util.Log;

import org.apache.commons.lang3.ArrayUtils;//字元陣列合併

import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.Values.SendType;

public class SortData {

    String deviceType;
    public Context ctx;


    public SortData(String deviceType, Context ctx) {
        this.deviceType = deviceType;
        this.ctx = ctx;

    }

    public String trans(int name) {//不是我在講...每個都要寫ctx.getResources().getString(R.string.??);真的會死人
        String str = ctx.getResources().getString(name);
        return str;
    }

    public String[] getNames() {
//        Log.v("BT", "取得型號: "+SendType.FirstWord+", "+ SendType.SecondWord+", "+ SendType.ThirdWord);
        /**標題轉中文*/
        if (Integer.parseInt(String.valueOf(SendType.row)) == 1) {
            switch (SendType.FirstWord) {

                case 'T':
                    SendType.PV1 = trans(R.string.Temperature) + trans(R.string.PV);
                    SendType.EH1 = trans(R.string.Temperature) + trans(R.string.EH);
                    SendType.EL1 = trans(R.string.Temperature) + trans(R.string.EL);
                    SendType.CR1 = trans(R.string.Temperature) + trans(R.string.CR);
                    SendType.HH1 = trans(R.string.Temperature) + trans(R.string.HH);
                    SendType.LL1 = trans(R.string.Temperature) + trans(R.string.LL);

                    break;

                case 'H':
                    SendType.PV1 = trans(R.string.Humidity) + trans(R.string.PV);
                    SendType.EH1 = trans(R.string.Humidity) + trans(R.string.EH);
                    SendType.EL1 = trans(R.string.Humidity) + trans(R.string.EL);
                    SendType.CR1 = trans(R.string.Humidity) + trans(R.string.CR);
                    SendType.HH1 = trans(R.string.Humidity) + trans(R.string.HH);
                    SendType.LL1 = trans(R.string.Humidity) + trans(R.string.LL);
                    break;

                case 'I':
                    SendType.PV1 = trans(R.string.FirstRow) + trans(R.string.PV);
                    SendType.IH1 = trans(R.string.FirstRow) + trans(R.string.IH);
                    SendType.IL1 = trans(R.string.FirstRow) + trans(R.string.IL);
                    SendType.EH1 = trans(R.string.FirstRow) + trans(R.string.EH);
                    SendType.EL1 = trans(R.string.FirstRow) + trans(R.string.EL);
                    SendType.CR1 = trans(R.string.FirstRow) + trans(R.string.CR);
                    SendType.DP1 = trans(R.string.FirstRow) + trans(R.string.decimal_point);
                    SendType.HH1 = trans(R.string.FirstRow) + trans(R.string.HH);
                    SendType.LL1 = trans(R.string.FirstRow) + trans(R.string.LL);
                    break;

                case 'C':
                case 'D':
                case 'E':
                    SendType.PV1 = trans(R.string.CO2) + trans(R.string.PV);
                    SendType.EH1 = trans(R.string.CO2) + trans(R.string.EH);
                    SendType.EL1 = trans(R.string.CO2) + trans(R.string.EL);
                    SendType.CR1 = trans(R.string.CO2) + trans(R.string.CR);
                    SendType.HH1 = trans(R.string.CO2) + trans(R.string.HH);
                    SendType.LL1 = trans(R.string.CO2) + trans(R.string.LL);
                    break;


            }
            if (SendType.row == '1') {
                switch (SendType.SecondWord) {
                    case 'L':
                        SendType.INTER = trans(R.string.INTER);
                        break;
                }
            }
        }

        if (Integer.parseInt(String.valueOf(SendType.row)) == 2) {
            switch (SendType.FirstWord) {

                case 'T':
                    SendType.PV1 = trans(R.string.Temperature) + trans(R.string.PV);
                    SendType.EH1 = trans(R.string.Temperature) + trans(R.string.EH);
                    SendType.EL1 = trans(R.string.Temperature) + trans(R.string.EL);
                    SendType.CR1 = trans(R.string.Temperature) + trans(R.string.CR);
                    SendType.HH1 = trans(R.string.Temperature) + trans(R.string.HH);
                    SendType.LL1 = trans(R.string.Temperature) + trans(R.string.LL);

                    break;

                case 'H':
                    SendType.PV1 = trans(R.string.Humidity) + trans(R.string.PV);
                    SendType.EH1 = trans(R.string.Humidity) + trans(R.string.EH);
                    SendType.EL1 = trans(R.string.Humidity) + trans(R.string.EL);
                    SendType.CR1 = trans(R.string.Humidity) + trans(R.string.CR);
                    SendType.HH1 = trans(R.string.Humidity) + trans(R.string.HH);
                    SendType.LL1 = trans(R.string.Humidity) + trans(R.string.LL);
                    break;

                case 'I':
                    SendType.PV1 = trans(R.string.FirstRow) + trans(R.string.PV);
                    SendType.IH1 = trans(R.string.FirstRow) + trans(R.string.IH);
                    SendType.IL1 = trans(R.string.FirstRow) + trans(R.string.IL);
                    SendType.EH1 = trans(R.string.FirstRow) + trans(R.string.EH);
                    SendType.EL1 = trans(R.string.FirstRow) + trans(R.string.EL);
                    SendType.CR1 = trans(R.string.FirstRow) + trans(R.string.CR);
                    SendType.DP1 = trans(R.string.FirstRow) + trans(R.string.decimal_point);
                    SendType.HH1 = trans(R.string.FirstRow) + trans(R.string.HH);
                    SendType.LL1 = trans(R.string.FirstRow) + trans(R.string.LL);
                    break;

                case 'C':
                case 'D':
                case 'E':
                    SendType.PV1 = trans(R.string.CO2) + trans(R.string.PV);
                    SendType.EH1 = trans(R.string.CO2) + trans(R.string.EH);
                    SendType.EL1 = trans(R.string.CO2) + trans(R.string.EL);
                    SendType.CR1 = trans(R.string.CO2) + trans(R.string.CR);
                    SendType.HH1 = trans(R.string.CO2) + trans(R.string.HH);
                    SendType.LL1 = trans(R.string.CO2) + trans(R.string.LL);
                    break;


            }
            switch (SendType.SecondWord) {
                case 'T':
                    SendType.PV2 = trans(R.string.Temperature) + trans(R.string.PV);
                    SendType.EH2 = trans(R.string.Temperature) + trans(R.string.EH);
                    SendType.EL2 = trans(R.string.Temperature) + trans(R.string.EL);
                    SendType.CR2 = trans(R.string.Temperature) + trans(R.string.CR);
                    SendType.HH2 = trans(R.string.Temperature) + trans(R.string.HH);
                    SendType.LL2 = trans(R.string.Temperature) + trans(R.string.LL);
                    break;
                case 'H':
                    SendType.PV2 = trans(R.string.Humidity) + trans(R.string.PV);
                    SendType.EH2 = trans(R.string.Humidity) + trans(R.string.EH);
                    SendType.EL2 = trans(R.string.Humidity) + trans(R.string.EL);
                    SendType.CR2 = trans(R.string.Humidity) + trans(R.string.CR);
                    SendType.HH2 = trans(R.string.Humidity) + trans(R.string.HH);
                    SendType.LL2 = trans(R.string.Humidity) + trans(R.string.LL);
                    break;
                case 'I':
                    SendType.PV2 = trans(R.string.SecondRow) + trans(R.string.PV);
                    SendType.IH2 = trans(R.string.SecondRow) + trans(R.string.IH);
                    SendType.IL2 = trans(R.string.SecondRow) + trans(R.string.IL);
                    SendType.EH2 = trans(R.string.SecondRow) + trans(R.string.EH);
                    SendType.EL2 = trans(R.string.SecondRow) + trans(R.string.EL);
                    SendType.CR2 = trans(R.string.SecondRow) + trans(R.string.CR);
                    SendType.DP2 = trans(R.string.SecondRow) + trans(R.string.decimal_point);
                    SendType.HH2 = trans(R.string.SecondRow) + trans(R.string.HH);
                    SendType.LL2 = trans(R.string.SecondRow) + trans(R.string.LL);
                    break;
                case 'C':
                case 'D':
                case 'E':
                    SendType.PV2 = trans(R.string.CO2) + trans(R.string.PV);
                    SendType.EH2 = trans(R.string.CO2) + trans(R.string.EH);
                    SendType.EL2 = trans(R.string.CO2) + trans(R.string.EL);
                    SendType.CR2 = trans(R.string.CO2) + trans(R.string.CR);
                    SendType.HH2 = trans(R.string.CO2) + trans(R.string.HH);
                    SendType.LL2 = trans(R.string.CO2) + trans(R.string.LL);
                    break;
                case 'M':
                    SendType.PV2 = trans(R.string.PM2_5) + trans(R.string.PV);
                    SendType.EH2 = trans(R.string.PM2_5) + trans(R.string.EH);
                    SendType.EL2 = trans(R.string.PM2_5) + trans(R.string.EL);
                    SendType.CR2 = trans(R.string.PM2_5) + trans(R.string.CR);
                    SendType.HH2 = trans(R.string.PM2_5) + trans(R.string.HH);
                    SendType.LL2 = trans(R.string.PM2_5) + trans(R.string.LL);
                    break;
            }
            if (SendType.row == '2') {
                switch (SendType.ThirdWord) {
                    case 'L':
                        SendType.INTER = trans(R.string.INTER);
                        break;
                }
            }
        }

        if (Integer.parseInt(String.valueOf(SendType.row)) == 3) {
            switch (SendType.FirstWord) {

                case 'T':
                    SendType.PV1 = trans(R.string.Temperature) + trans(R.string.PV);
                    SendType.EH1 = trans(R.string.Temperature) + trans(R.string.EH);
                    SendType.EL1 = trans(R.string.Temperature) + trans(R.string.EL);
                    SendType.CR1 = trans(R.string.Temperature) + trans(R.string.CR);
                    SendType.HH1 = trans(R.string.Temperature) + trans(R.string.HH);
                    SendType.LL1 = trans(R.string.Temperature) + trans(R.string.LL);

                    break;

                case 'H':
                    SendType.PV1 = trans(R.string.Humidity) + trans(R.string.PV);
                    SendType.EH1 = trans(R.string.Humidity) + trans(R.string.EH);
                    SendType.EL1 = trans(R.string.Humidity) + trans(R.string.EL);
                    SendType.CR1 = trans(R.string.Humidity) + trans(R.string.CR);
                    SendType.HH1 = trans(R.string.Humidity) + trans(R.string.HH);
                    SendType.LL1 = trans(R.string.Humidity) + trans(R.string.LL);
                    break;

                case 'I':
                    SendType.PV1 = trans(R.string.FirstRow) + trans(R.string.PV);
                    SendType.IH1 = trans(R.string.FirstRow) + trans(R.string.IH);
                    SendType.IL1 = trans(R.string.FirstRow) + trans(R.string.IL);
                    SendType.EH1 = trans(R.string.FirstRow) + trans(R.string.EH);
                    SendType.EL1 = trans(R.string.FirstRow) + trans(R.string.EL);
                    SendType.CR1 = trans(R.string.FirstRow) + trans(R.string.CR);
                    SendType.DP1 = trans(R.string.FirstRow) + trans(R.string.decimal_point);
                    SendType.HH1 = trans(R.string.FirstRow) + trans(R.string.HH);
                    SendType.LL1 = trans(R.string.FirstRow) + trans(R.string.LL);
                    break;

                case 'C':
                case 'D':
                case 'E':
                    SendType.PV1 = trans(R.string.CO2) + trans(R.string.PV);
                    SendType.EH1 = trans(R.string.CO2) + trans(R.string.EH);
                    SendType.EL1 = trans(R.string.CO2) + trans(R.string.EL);
                    SendType.CR1 = trans(R.string.CO2) + trans(R.string.CR);
                    SendType.HH1 = trans(R.string.CO2) + trans(R.string.HH);
                    SendType.LL1 = trans(R.string.CO2) + trans(R.string.LL);
                    break;


            }
            switch (SendType.SecondWord) {
                case 'T':
                    SendType.PV2 = trans(R.string.Temperature) + trans(R.string.PV);
                    SendType.EH2 = trans(R.string.Temperature) + trans(R.string.EH);
                    SendType.EL2 = trans(R.string.Temperature) + trans(R.string.EL);
                    SendType.CR2 = trans(R.string.Temperature) + trans(R.string.CR);
                    SendType.HH2 = trans(R.string.Temperature) + trans(R.string.HH);
                    SendType.LL2 = trans(R.string.Temperature) + trans(R.string.LL);
                    break;
                case 'H':
                    SendType.PV2 = trans(R.string.Humidity) + trans(R.string.PV);
                    SendType.EH2 = trans(R.string.Humidity) + trans(R.string.EH);
                    SendType.EL2 = trans(R.string.Humidity) + trans(R.string.EL);
                    SendType.CR2 = trans(R.string.Humidity) + trans(R.string.CR);
                    SendType.HH2 = trans(R.string.Humidity) + trans(R.string.HH);
                    SendType.LL2 = trans(R.string.Humidity) + trans(R.string.LL);
                    break;
                case 'I':
                    SendType.PV2 = trans(R.string.SecondRow) + trans(R.string.PV);
                    SendType.IH2 = trans(R.string.SecondRow) + trans(R.string.IH);
                    SendType.IL2 = trans(R.string.SecondRow) + trans(R.string.IL);
                    SendType.EH2 = trans(R.string.SecondRow) + trans(R.string.EH);
                    SendType.EL2 = trans(R.string.SecondRow) + trans(R.string.EL);
                    SendType.CR2 = trans(R.string.SecondRow) + trans(R.string.CR);
                    SendType.DP2 = trans(R.string.SecondRow) + trans(R.string.decimal_point);
                    SendType.HH2 = trans(R.string.SecondRow) + trans(R.string.HH);
                    SendType.LL2 = trans(R.string.SecondRow) + trans(R.string.LL);
                    break;
                case 'C':
                case 'D':
                case 'E':
                    SendType.PV2 = trans(R.string.CO2) + trans(R.string.PV);
                    SendType.EH2 = trans(R.string.CO2) + trans(R.string.EH);
                    SendType.EL2 = trans(R.string.CO2) + trans(R.string.EL);
                    SendType.CR2 = trans(R.string.CO2) + trans(R.string.CR);
                    SendType.HH2 = trans(R.string.CO2) + trans(R.string.HH);
                    SendType.LL2 = trans(R.string.CO2) + trans(R.string.LL);
                    break;

            }
            switch (SendType.ThirdWord) {
                case 'T':
                    SendType.PV3 = trans(R.string.Temperature) + trans(R.string.PV);
                    SendType.EH3 = trans(R.string.Temperature) + trans(R.string.EH);
                    SendType.EL3 = trans(R.string.Temperature) + trans(R.string.EL);
                    SendType.CR3 = trans(R.string.Temperature) + trans(R.string.CR);
                    SendType.HH3 = trans(R.string.Temperature) + trans(R.string.HH);
                    SendType.LL3 = trans(R.string.Temperature) + trans(R.string.LL);
                    break;
                case 'H':
                    SendType.PV3 = trans(R.string.Humidity) + trans(R.string.PV);
                    SendType.EH3 = trans(R.string.Humidity) + trans(R.string.EH);
                    SendType.EL3 = trans(R.string.Humidity) + trans(R.string.EL);
                    SendType.CR3 = trans(R.string.Humidity) + trans(R.string.CR);
                    SendType.HH3 = trans(R.string.Humidity) + trans(R.string.HH);
                    SendType.LL3 = trans(R.string.Humidity) + trans(R.string.LL);
                    break;
                case 'I':
                    SendType.PV3 = trans(R.string.ThirdRow) + trans(R.string.PV);
                    SendType.IH3 = trans(R.string.ThirdRow) + trans(R.string.IH);
                    SendType.IL3 = trans(R.string.ThirdRow) + trans(R.string.IL);
                    SendType.EH3 = trans(R.string.ThirdRow) + trans(R.string.EH);
                    SendType.EL3 = trans(R.string.ThirdRow) + trans(R.string.EL);
                    SendType.CR3 = trans(R.string.ThirdRow) + trans(R.string.CR);
                    SendType.DP3 = trans(R.string.ThirdRow) + trans(R.string.decimal_point);
                    SendType.HH3 = trans(R.string.SecondRow) + trans(R.string.HH);
                    SendType.LL3 = trans(R.string.SecondRow) + trans(R.string.LL);
                    break;
                case 'C':
                case 'D':
                case 'E':
                    SendType.PV3 = trans(R.string.CO2) + trans(R.string.PV);
                    SendType.EH3 = trans(R.string.CO2) + trans(R.string.EH);
                    SendType.EL3 = trans(R.string.CO2) + trans(R.string.EL);
                    SendType.CR3 = trans(R.string.CO2) + trans(R.string.CR);
                    SendType.HH3 = trans(R.string.CO2) + trans(R.string.HH);
                    SendType.LL3 = trans(R.string.CO2) + trans(R.string.LL);
                    break;
            }
            if (SendType.row == '3') {
                switch (SendType.FourthWord) {
                    case 'L':
                        SendType.INTER = trans(R.string.INTER);
                        break;
                }
            }

        }

        //反正大家都有警報器吧.................
        SendType.SPK = trans(R.string.SPK);
        /**各種型號都放這*/
        String[] TH = {trans(R.string.device_name)
                , SendType.PV1, SendType.PV2
                , SendType.EH1, SendType.EL1
                , SendType.EH2, SendType.EL2
                , SendType.CR1, SendType.CR2
                , SendType.SPK};

        String[] TH_AL2 = {trans(R.string.device_name)
                , SendType.PV1, SendType.PV2
                , SendType.HH1, SendType.EH1, SendType.EL1
                , SendType.HH2, SendType.EH2, SendType.EL2
                , SendType.CR1, SendType.CR2
                , SendType.SPK};


        String[] II = {trans(R.string.device_name)
                , SendType.IH1, SendType.IL1
                , SendType.IH2, SendType.IL2
                , SendType.PV1, SendType.PV2
                , SendType.EH1, SendType.EL1
                , SendType.EH2, SendType.EL2
                , SendType.CR1, SendType.CR2
                , SendType.DP1, SendType.DP2
                , SendType.SPK};

        String[] IM = {trans(R.string.device_name)
                , SendType.IH1, SendType.IL1
                , SendType.PV1, SendType.PV2
                , SendType.EH1, SendType.EL1
                , SendType.EH2, SendType.EL2
                , SendType.CR1, SendType.CR2
                , SendType.DP1
                , SendType.SPK};


        String[] I = {trans(R.string.device_name)
                , SendType.IH1, SendType.IL1
                , SendType.PV1, SendType.EH1
                , SendType.EL1, SendType.CR1
                , SendType.DP1, SendType.SPK};

        String[] single = {trans(R.string.device_name)
                , SendType.PV1, SendType.EH1
                , SendType.EL1, SendType.CR1
                , SendType.SPK};

        String[] THI = {trans(R.string.device_name)
                , SendType.PV1, SendType.PV2, SendType.PV3
                , SendType.EH1, SendType.EL1
                , SendType.EH2, SendType.EL2
                , SendType.EH3, SendType.EL3
                , SendType.IH3, SendType.IH3
                , SendType.CR1, SendType.CR2, SendType.CR3
                , SendType.DP3
                , SendType.SPK};
        String[] THY = {trans(R.string.device_name)
                , SendType.PV1, SendType.PV2
                , SendType.EH1, SendType.EL1
                , SendType.EH2, SendType.EL2
                , SendType.CR1, SendType.CR2
                , SendType.SPK, trans(R.string.Y_function_TimeSet)};
        String[] THD = {trans(R.string.device_name)
                , SendType.PV1, SendType.PV2, SendType.PV3
                , SendType.EH1, SendType.EH2, SendType.EH3
                , SendType.EL1, SendType.EL2, SendType.EL3
                , SendType.CR1, SendType.CR2, SendType.CR3
                , SendType.SPK};
        String[] TTH = {trans(R.string.device_name)
                , SendType.PV1, SendType.PV2, SendType.PV3
                , SendType.EH1, SendType.EH2, SendType.EH3
                , SendType.EL1, SendType.EL2, SendType.EL3
                , SendType.CR1, SendType.CR2, SendType.CR3
                , SendType.SPK};

        String[] CDEII = {trans(R.string.device_name)
                , SendType.PV1, SendType.EH1, SendType.EL1, SendType.CR1, SendType.SPK
                , SendType.PV2, SendType.IH2, SendType.IL2, SendType.EH2, SendType.EL2, SendType.CR2, SendType.DP2
                , SendType.PV3, SendType.IH3, SendType.IL3, SendType.EH3, SendType.EL3, SendType.CR3, SendType.DP3};

        String[] L = {SendType.INTER};
        /**各種型號都放這*/
        String[] n = {"這", "是", "我", "不", "知", "道", "的", "型", "號"};
        int row = Integer.parseInt(String.valueOf(SendType.row));
        switch (row) {
            case 1://判斷為一排
                switch (SendType.SecondWord) {
                    case 'L':
                        if (SendType.FirstWord == 'I') {
                            return ArrayUtils.addAll(I, L);
                        }
//                        if(SendType.FirstWord == 'I'){return ArrayUtils.addAll(I,L);}
                        break;
                    default:
                        if (SendType.FirstWord == 'I') {
                            return I;
                        } else if (SendType.FirstWord != ' ') {
                            return single;
                        }
                        break;
                }
                break;
            case 2:
                switch (SendType.ThirdWord) {
                    case 'L':
                        if (SendType.FirstWord == 'I' && SendType.SecondWord == 'I') {
                            return ArrayUtils.addAll(II, L);
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.mALARM == null) {
                            return ArrayUtils.addAll(TH, L);
                        } else if (Integer.parseInt(SendType.mALARM) == 2) {
                            return ArrayUtils.addAll(TH_AL2, L);
                        }
                        break;

                    default:
                        if (SendType.FirstWord == 'I' && SendType.SecondWord == 'I') {
                            return II;
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H'&& SendType.mALARM == null) {
                            return TH;
                        }else if (SendType.FirstWord == 'I' && SendType.SecondWord == 'M' && SendType.mALARM == null){
                            return IM;
                        } else if (Integer.parseInt(SendType.mALARM) == 2) {
                            return TH_AL2;
                        }

                        break;
                }
                break;
            case 3:
                switch (SendType.FourthWord) {
                    case 'L':
                        if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'D') {
                            return ArrayUtils.addAll(THD, L);
                        } else if (SendType.FirstWord == 'C' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return ArrayUtils.addAll(CDEII, L);
                        } else if (SendType.FirstWord == 'D' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return ArrayUtils.addAll(CDEII, L);
                        } else if (SendType.FirstWord == 'E' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return ArrayUtils.addAll(CDEII, L);
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'T' && SendType.ThirdWord == 'H') {
                            return ArrayUtils.addAll(TTH, L);
                        }else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'Y') {
                            return ArrayUtils.addAll(THY,L);
                        }

                        break;
                    default:
                        if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'I') {
                            return THI;
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'Y') {
                            return THY;
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'D') {
                            return THD;
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'T' && SendType.ThirdWord == 'H') {
                            return TTH;
                        } else if (SendType.FirstWord == 'C' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return CDEII;
                        } else if (SendType.FirstWord == 'D' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return CDEII;
                        } else if (SendType.FirstWord == 'E' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return CDEII;
                        }

                        break;
                }
                break;
        }//判斷排數
        return n;
    }//getNames End

    public String[] getValues() {

        String[] mTHI = {SendType.DeviceName
                , SendType.mPV1, SendType.mPV2, SendType.mPV3
                , SendType.mEH1, SendType.mEL1
                , SendType.mEH2, SendType.mEL2
                , SendType.mEH3, SendType.mEL3
                , SendType.mIH3, SendType.mIH3
                , SendType.mCR1, SendType.mCR2, SendType.mCR3
                , SendType.mDP3
                , SendType.mSPK};

        String[] mTH = {SendType.DeviceName
                , SendType.mPV1, SendType.mPV2
                , SendType.mEH1, SendType.mEL1
                , SendType.mEH2, SendType.mEL2
                , SendType.mCR1, SendType.mCR2
                , SendType.mSPK};

        String[] mTH_AL2 = {SendType.DeviceName
                , SendType.mPV1, SendType.mPV2
                , SendType.mHH1, SendType.mEH1, SendType.mEL1
                , SendType.mHH2, SendType.mEH2, SendType.mEL2
                , SendType.mCR1, SendType.mCR2
                , SendType.mSPK};

        String[] mII = {SendType.DeviceName
                , SendType.mIH1, SendType.mIL1
                , SendType.mIH2, SendType.mIL2
                , SendType.mPV1, SendType.mPV2
                , SendType.mEH1, SendType.mEL1
                , SendType.mEH2, SendType.mEL2
                , SendType.mCR1, SendType.mCR2
                , SendType.mDP1, SendType.mDP2
                , SendType.mSPK};

        String[] mIM = {SendType.DeviceName
                , SendType.mIH1, SendType.mIL1
                , SendType.mPV1, SendType.mPV2
                , SendType.mEH1, SendType.mEL1
                , SendType.mEH2, SendType.mEL2
                , SendType.mCR1, SendType.mCR2
                , SendType.mDP1
                , SendType.mSPK};

        String[] mI = {SendType.DeviceName
                , SendType.mIH1, SendType.mIL1
                , SendType.mPV1, SendType.mEH1
                , SendType.mEL1, SendType.mCR1
                , SendType.mDP1, SendType.mSPK};
        String[] msingle = {trans(R.string.device_name)
                , SendType.mPV1, SendType.mEH1
                , SendType.mEL1, SendType.mCR1
                , SendType.mSPK};

        String[] mTHY = {SendType.DeviceName
                , SendType.mPV1, SendType.mPV2
                , SendType.mEH1, SendType.mEL1
                , SendType.mEH2, SendType.mEL2
                , SendType.mCR1, SendType.mCR2
                , SendType.mSPK, " "};

        String[] mTHD = {SendType.DeviceName
                , SendType.mPV1, SendType.mPV2, SendType.mPV3
                , SendType.mEH1, SendType.mEH2, SendType.mEH3
                , SendType.mEL1, SendType.mEL2, SendType.mEL3
                , SendType.mCR1, SendType.mCR2, SendType.mCR3
                , SendType.mSPK};
        String[] mTTH = {SendType.DeviceName
                , SendType.mPV1, SendType.mPV2, SendType.mPV3
                , SendType.mEH1, SendType.mEH2, SendType.mEH3
                , SendType.mEL1, SendType.mEL2, SendType.mEL3
                , SendType.mCR1, SendType.mCR2, SendType.mCR3
                , SendType.mSPK};
        String[] mCDEII = {SendType.DeviceName
                , SendType.mPV1, SendType.mEH1, SendType.mEL1, SendType.mCR1, SendType.mSPK
                , SendType.mPV2, SendType.mIH2, SendType.mIL2, SendType.mEH2, SendType.mEL2, SendType.mCR2, SendType.mDP2
                , SendType.mPV3, SendType.mIH3, SendType.mIL3, SendType.mEH3, SendType.mEL3, SendType.mCR3, SendType.mDP3};


        String[] mL = {SendType.mINTER};
        String[] mn = {"這", "是", "我", "不", "知", "道", "的", "型", "號"};

        int row = Integer.parseInt(String.valueOf(SendType.row));
        switch (row) {
            case 1://判斷為一排
                switch (SendType.SecondWord) {
                    case 'L':
                        if (SendType.FirstWord == 'I') {
                            return ArrayUtils.addAll(mI, mL);
                        }
                        break;
                    default:
                        if (SendType.FirstWord == 'I') {
                            return mI;
                        } else if (SendType.FirstWord != ' ') {
                            return msingle;
                        }
                        break;
                }
                break;
            case 2:
                switch (SendType.ThirdWord) {
                    case 'L':
                        if (SendType.FirstWord == 'I' && SendType.SecondWord == 'I') {
                            return ArrayUtils.addAll(mII, mL);
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H'&& SendType.mALARM == null) {
                            return ArrayUtils.addAll(mTH, mL);
                        } else if (Integer.parseInt(SendType.mALARM) == 2) {
                            return ArrayUtils.addAll(mTH_AL2, mL);
                        }
                        break;

                    default:
                        if (SendType.FirstWord == 'I' && SendType.SecondWord == 'I') {
                            return mII;
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H'&& SendType.mALARM == null) {
                            return mTH;
                        }else if (SendType.FirstWord == 'I' && SendType.SecondWord == 'M' && SendType.mALARM == null){
                            return mIM;
                        } else if (Integer.parseInt(SendType.mALARM) == 2) {
                            return mTH_AL2;
                        }
                        break;
                }
                break;
            case 3:
                switch (SendType.FourthWord) {
                    case 'L':
                        if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'D') {
                            return ArrayUtils.addAll(mTHD, mL);
                        } else if (SendType.FirstWord == 'C' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return ArrayUtils.addAll(mCDEII, mL);
                        } else if (SendType.FirstWord == 'D' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return ArrayUtils.addAll(mCDEII, mL);
                        } else if (SendType.FirstWord == 'E' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return ArrayUtils.addAll(mCDEII, mL);
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'T' && SendType.ThirdWord == 'H') {
                            return ArrayUtils.addAll(mTTH, mL);
                        }else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'Y') {
                            return ArrayUtils.addAll(mTHY,mL);
                        }
                        break;
                    default:
                        if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'I') {
                            return mTHI;
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'Y') {
                            return mTHY;
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'D') {
                            return mTHD;
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'T' && SendType.ThirdWord == 'H') {
                            return mTTH;
                        } else if (SendType.FirstWord == 'C' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return mCDEII;
                        } else if (SendType.FirstWord == 'D' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return mCDEII;
                        } else if (SendType.FirstWord == 'E' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return mCDEII;
                        }

                        break;
                }
                break;
        }//判斷排數
        return mn;
    }

    /**************************************************************************************************/

    /**
     * 有增加型號的話要改這裡，這裡是將資料寫入資料庫時用的
     */
    public String[] getSQLiteTrs() {

        switch (SendType.FirstWord) {
            case 'T':
            case 'C':
            case 'D':
            case 'E':

                SendType.HH2 = "HH1";
                SendType.LL2 = "LL1";
                SendType.PV1 = "PV1";
                SendType.EH1 = "EH1";
                SendType.EL1 = "EL1";
                SendType.CR1 = "CR1";

                break;

            case 'H':

                break;

            case 'I':
                SendType.PV1 = "PV1";
                SendType.IH1 = "IH1";
                SendType.IL1 = "IL1";
                SendType.EH1 = "EH1";
                SendType.EL1 = "EL1";
                SendType.CR1 = "CR1";
                SendType.DP1 = "DP1";
                break;

        }
        switch (SendType.SecondWord) {
            case 'T':
            case 'C':
            case 'D':
            case 'E':
            case 'H':

                SendType.HH2 = "HH2";
                SendType.LL2 = "LL2";
                SendType.PV2 = "PV2";
                SendType.EH2 = "EH2";
                SendType.EL2 = "EL2";
                SendType.CR2 = "CR2";
                break;


            case 'I':
                SendType.PV2 = "PV2";
                SendType.IH2 = "IH2";
                SendType.IL2 = "IL2";
                SendType.EH2 = "EH2";
                SendType.EL2 = "EL2";
                SendType.CR2 = "CR2";
                SendType.DP2 = "DP2";
                break;

        }
        if (SendType.row == '2') {
            switch (SendType.ThirdWord) {
                case 'L':
                    SendType.INTER = "INTER";
                    break;
            }
        }
        if (SendType.row == '3') {
            switch (SendType.ThirdWord) {

                case 'T':
                case 'H':
                case 'C':
                case 'D':
                case 'E':
                    SendType.HH2 = "HH3";
                    SendType.LL2 = "LL3";
                    SendType.PV3 = "PV3";
                    SendType.EH3 = "EH3";
                    SendType.EL3 = "EL3";
                    SendType.CR3 = "CR3";
                    break;

                case 'I':
                    SendType.PV3 = "PV3";
                    SendType.IH3 = "IH3";
                    SendType.IL3 = "IL3";
                    SendType.EH3 = "EH3";
                    SendType.EL3 = "EL3";
                    SendType.CR3 = "CR3";
                    SendType.DP3 = "DP3";
                    break;
            }

        }
        if (SendType.row == '3') {
            switch (SendType.FourthWord) {
                case 'L':
                    SendType.INTER = "INTER";
                    break;
            }
        }

        SendType.SPK = "SPK";

        String[] I = {trans(R.string.Device_name)
                , SendType.IH1, SendType.IL1
                , SendType.PV1, SendType.EH1
                , SendType.EL1, SendType.CR1
                , SendType.DP1, SendType.SPK};
        String[] single = {trans(R.string.device_name)
                , SendType.PV1, SendType.EH1
                , SendType.EL1, SendType.CR1
                , SendType.SPK};
        String[] TH = {trans(R.string.device_name)
                , SendType.PV1, SendType.PV2
                , SendType.EH1, SendType.EL1
                , SendType.EH2, SendType.EL2
                , SendType.CR1, SendType.CR2
                , SendType.SPK};
        String[] TH_AL2 = {trans(R.string.device_name)
                , SendType.PV1, SendType.PV2
                , SendType.HH1, SendType.EH1, SendType.EL1
                , SendType.HH2, SendType.EH2, SendType.EL2
                , SendType.CR1, SendType.CR2
                , SendType.SPK};

        String[] II = {trans(R.string.device_name)
                , SendType.IH1, SendType.IL1
                , SendType.IH2, SendType.IL2
                , SendType.PV1, SendType.PV2
                , SendType.EH1, SendType.EL1
                , SendType.EH2, SendType.EL2
                , SendType.CR1, SendType.CR2
                , SendType.DP1, SendType.DP2
                , SendType.SPK};

        String[] THI = {trans(R.string.device_name)
                , SendType.PV1, SendType.PV2, SendType.PV3
                , SendType.EH1, SendType.EL1
                , SendType.EH2, SendType.EL2
                , SendType.EH3, SendType.EL3
                , SendType.IH3, SendType.IH3
                , SendType.CR1, SendType.CR2, SendType.CR3
                , SendType.DP3
                , SendType.SPK};
        String[] THY = {trans(R.string.device_name)
                , SendType.PV1, SendType.PV2
                , SendType.EH1, SendType.EL1
                , SendType.EH2, SendType.EL2
                , SendType.CR1, SendType.CR2
                , SendType.SPK};

        String[] THD = {trans(R.string.device_name)
                , SendType.PV1, SendType.PV2, SendType.PV3
                , SendType.EH1, SendType.EH2, SendType.EH3
                , SendType.EL1, SendType.EL2, SendType.EL3
                , SendType.CR1, SendType.CR2, SendType.CR3
                , SendType.SPK};

        String[] TTH = {trans(R.string.device_name)
                , SendType.PV1, SendType.PV2, SendType.PV3
                , SendType.EH1, SendType.EH2, SendType.EH3
                , SendType.EL1, SendType.EL2, SendType.EL3
                , SendType.CR1, SendType.CR2, SendType.CR3
                , SendType.SPK};

        String[] CDEII = {trans(R.string.device_name)
                , SendType.PV1, SendType.EH1, SendType.EL1, SendType.CR1, SendType.SPK
                , SendType.PV2, SendType.IH2, SendType.IL2, SendType.EH2, SendType.EL2, SendType.CR2, SendType.DP2
                , SendType.PV3, SendType.IH3, SendType.IL3, SendType.EH3, SendType.EL3, SendType.CR3, SendType.DP3};

        String[] L = {SendType.INTER};
        String[] n = {"這", "是", "我", "不", "知", "道", "的", "型", "號"};

        int row = Integer.parseInt(String.valueOf(SendType.row));
        switch (row) {
            case 1://判斷為一排
                switch (SendType.SecondWord) {
                    case 'L':
                        if (SendType.FirstWord == 'I') {
                            return ArrayUtils.addAll(I, L);
                        }
                        break;
                    default:
                        if (SendType.FirstWord == 'I') {
                            return I;
                        } else if (SendType.FirstWord != ' ') {
                            return single;
                        }
                        break;
                }
                break;
            case 2:
                switch (SendType.ThirdWord) {
                    case 'L':
                        if (SendType.FirstWord == 'I' && SendType.SecondWord == 'I') {
                            return ArrayUtils.addAll(II, L);
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H'&& SendType.mALARM == null) {
                            return ArrayUtils.addAll(TH, L);
                        } else if (Integer.parseInt(SendType.mALARM) == 2) {
                            return ArrayUtils.addAll(TH_AL2, L);
                        }
                        break;

                    default:
                        if (SendType.FirstWord == 'I' && SendType.SecondWord == 'I') {
                            return II;
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H'&& SendType.mALARM == null) {
                            return TH;
                        } else if (Integer.parseInt(SendType.mALARM) == 2) {
                            return TH_AL2;
                        }
                        break;
                }
                break;
            case 3:
                switch (SendType.FourthWord) {
                    case 'L':
                        if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'D') {
                            return ArrayUtils.addAll(THD, L);
                        } else if (SendType.FirstWord == 'C' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return ArrayUtils.addAll(CDEII, L);
                        } else if (SendType.FirstWord == 'D' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return ArrayUtils.addAll(CDEII, L);
                        } else if (SendType.FirstWord == 'E' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return ArrayUtils.addAll(CDEII, L);
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'T' && SendType.ThirdWord == 'H') {
                            return ArrayUtils.addAll(TTH, L);
                        }else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'Y') {
                            return ArrayUtils.addAll(THY,L);
                        }
                        break;
                    default:
                        if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'I') {
                            return THI;
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'Y') {
                            return THY;
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'D') {
                            return THD;
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'T' && SendType.ThirdWord == 'H') {
                            return TTH;
                        } else if (SendType.FirstWord == 'C' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return CDEII;
                        } else if (SendType.FirstWord == 'D' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return CDEII;
                        } else if (SendType.FirstWord == 'E' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return CDEII;
                        }


                        break;
                }
                break;
        }//判斷排數
        return n;
    }


    public String[] getSQLiteData() {
        String[] mTHI = {SendType.DeviceName
                , SendType.mPV1, SendType.mPV2, SendType.mPV3
                , SendType.mEH1, SendType.mEL1
                , SendType.mEH2, SendType.mEL2
                , SendType.mEH3, SendType.mEL3
                , SendType.mIH3, SendType.mIH3
                , SendType.mCR1, SendType.mCR2, SendType.mCR3
                , SendType.mDP3
                , SendType.mSPK};
        String[] mTHY = {SendType.DeviceName
                , SendType.mPV1, SendType.mPV2
                , SendType.mEH1, SendType.mEL1
                , SendType.mEH2, SendType.mEL2
                , SendType.mCR1, SendType.mCR2
                , SendType.mSPK};

        String[] mTH = {SendType.DeviceName
                , SendType.mPV1, SendType.mPV2
                , SendType.mEH1, SendType.mEL1
                , SendType.mEH2, SendType.mEL2
                , SendType.mCR1, SendType.mCR2
                , SendType.mSPK};

        String[] mTH_AL2 = {SendType.DeviceName
                , SendType.mPV1, SendType.mPV2
                , SendType.mHH1, SendType.mEH1, SendType.mEL1
                , SendType.mHH2, SendType.mEH2, SendType.mEL2
                , SendType.mCR1, SendType.mCR2
                , SendType.mSPK};

        String[] mII = {SendType.DeviceName
                , SendType.mIH1, SendType.mIL1
                , SendType.mIH2, SendType.mIL2
                , SendType.mPV1, SendType.mPV2
                , SendType.mEH1, SendType.mEL1
                , SendType.mEH2, SendType.mEL2
                , SendType.mCR1, SendType.mCR2
                , SendType.mDP1, SendType.mDP2
                , SendType.mSPK};

        String[] mI = {SendType.DeviceName
                , SendType.mIH1, SendType.mIL1
                , SendType.mPV1, SendType.mEH1
                , SendType.mEL1, SendType.mCR1
                , SendType.mDP1, SendType.mSPK};

        String[] msingle = {trans(R.string.device_name)
                , SendType.mPV1, SendType.mEH1
                , SendType.mEL1, SendType.mCR1
                , SendType.mSPK};

        String[] mTHD = {SendType.DeviceName
                , SendType.mPV1, SendType.mPV2, SendType.mPV3
                , SendType.mEH1, SendType.mEH2, SendType.mEH3
                , SendType.mEL1, SendType.mEL2, SendType.mEL3
                , SendType.mCR1, SendType.mCR2, SendType.mCR3
                , SendType.mSPK};

        String[] mTTH = {SendType.DeviceName
                , SendType.mPV1, SendType.mPV2, SendType.mPV3
                , SendType.mEH1, SendType.mEH2, SendType.mEH3
                , SendType.mEL1, SendType.mEL2, SendType.mEL3
                , SendType.mCR1, SendType.mCR2, SendType.mCR3
                , SendType.mSPK};

        String[] mCDEII = {SendType.DeviceName
                , SendType.mPV1, SendType.mEH1, SendType.mEL1, SendType.mCR1, SendType.mSPK
                , SendType.mPV2, SendType.mIH2, SendType.mIL2, SendType.mEH2, SendType.mEL2, SendType.mCR2, SendType.mDP2
                , SendType.mPV3, SendType.mIH3, SendType.mIL3, SendType.mEH3, SendType.mEL3, SendType.mCR3, SendType.mDP3};

        String[] mL = {SendType.INTER2SQL};
        String[] mn = {"這", "是", "我", "不", "知", "道", "的", "型", "號"};

        int row = Integer.parseInt(String.valueOf(SendType.row));
        switch (row) {
            case 1://判斷為一排
                switch (SendType.SecondWord) {
                    case 'L':
                        if (SendType.FirstWord == 'I') {
                            return ArrayUtils.addAll(mI, mL);
                        }
                        break;
                    default:
                        if (SendType.FirstWord == 'I') {
                            return mI;
                        } else if (SendType.FirstWord != ' ') {
                            return msingle;
                        }
                        break;
                }
                break;
            case 2:
                switch (SendType.ThirdWord) {
                    case 'L':
                        if (SendType.FirstWord == 'I' && SendType.SecondWord == 'I') {
                            return ArrayUtils.addAll(mII, mL);
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H'&& SendType.mALARM == null) {
                            return ArrayUtils.addAll(mTH, mL);
                        } else if (Integer.parseInt(SendType.mALARM) == 2) {
                            return ArrayUtils.addAll(mTH_AL2, mL);
                        }
                        break;

                    default:
                        if (SendType.FirstWord == 'I' && SendType.SecondWord == 'I') {
                            return mII;
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H'&& SendType.mALARM == null) {
                            return mTH;
                        } else if (Integer.parseInt(SendType.mALARM) == 2) {
                            return mTH_AL2;
                        }
                        break;
                }
                break;
            case 3:
                switch (SendType.FourthWord) {
                    case 'L':
                        if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'D') {
                            return ArrayUtils.addAll(mTHD, mL);
                        } else if (SendType.FirstWord == 'C' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return ArrayUtils.addAll(mCDEII, mL);
                        } else if (SendType.FirstWord == 'D' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return ArrayUtils.addAll(mCDEII, mL);
                        } else if (SendType.FirstWord == 'E' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return ArrayUtils.addAll(mCDEII, mL);
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'T' && SendType.ThirdWord == 'H') {
                            return ArrayUtils.addAll(mTTH, mL);
                        }else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'Y') {
                            return ArrayUtils.addAll(mTHY,mL);
                        }
                        break;
                    default:
                        if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'I') {
                            return mTHI;
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'Y') {
                            return mTHY;
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'H' && SendType.ThirdWord == 'D') {
                            return mTHD;
                        } else if (SendType.FirstWord == 'T' && SendType.SecondWord == 'T' && SendType.ThirdWord == 'H') {
                            return mTTH;
                        } else if (SendType.FirstWord == 'C' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return mCDEII;
                        } else if (SendType.FirstWord == 'D' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return mCDEII;
                        } else if (SendType.FirstWord == 'E' && SendType.SecondWord == 'I' && SendType.ThirdWord == 'I') {
                            return mCDEII;
                        }

                        break;
                }
                break;
        }//判斷排數
        return mn;
    }


}
