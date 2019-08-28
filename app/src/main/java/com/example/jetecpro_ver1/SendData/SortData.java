package com.example.jetecpro_ver1.SendData;

import android.content.Context;

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

    public String[] getValues(){

        String[] TH = {SendType.DeviceName
                , SendType.mPV1, SendType.mPV2
                , SendType.mEH1, SendType.mEL1
                , SendType.mEH2, SendType.mEL2
                , SendType.mCR1, SendType.mCR2
                , SendType.mSPK};

        String[] II = {SendType.DeviceName
                , SendType.mIH1, SendType.mIL1
                , SendType.mIH2, SendType.mIL2
                , SendType.mPV1, SendType.mPV2
                , SendType.mEH1, SendType.mEL1
                , SendType.mEH2, SendType.mEL2
                , SendType.mCR1, SendType.mCR2
                , SendType.mDP1,SendType.mDP2
                , SendType.mSPK};

        String[] L = {SendType.mINTER};
        String[] n = {"這", "是", "我", "不", "知", "道", "的", "型","號"};

        if (SendType.FirstWord == 'T') {
            if (SendType.SecondWord == 'H') {
                if (SendType.ThirdWord == 'L') {
                    String[] THL = ArrayUtils.addAll(TH, L);
                    return THL;
                }//THL
                return TH;
            }//TH
            return n;
        }//T
        else if (SendType.FirstWord == 'I') {
            if (SendType.SecondWord == 'I') {
                if (SendType.ThirdWord == 'L') {
                    String[] IIL = ArrayUtils.addAll(II, L);
                    return IIL;
                }//IIL
                return II;
            }//II
            return n;
        }//I


        return n;
    }

/**************************************************************************************************/


    public String[] getSQLiteTrs(){
        switch (SendType.FirstWord){
            case 'T':
                SendType.PV1 = "PV1";
                SendType.EH1 = "EH1";
                SendType.EL1 = "EL1";
                SendType.CR1 = "CR1";
                break;

            case 'H':

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
        switch (SendType.SecondWord){
            case 'T':

                break;

            case 'H':
                SendType.PV2 = "PV2";
                SendType.EH2 = "EH2";
                SendType.EL2 = "EL2";
                SendType.CR2 = "CR2";
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
        if (SendType.row == '2'){
            switch (SendType.ThirdWord) {
                case 'L':
                    SendType.INTER = "INTER";
                    break;
            }
        }
        if (SendType.row == '3') {
            switch (SendType.ThirdWord) {
                case 'T':

                    break;
                case 'H':

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
        }

        SendType.SPK = "SPK";
        String[] TH = {trans(R.string.device_name)
                , SendType.PV1, SendType.PV2
                , SendType.EH1, SendType.EL1
                , SendType.EH2, SendType.EL2
                , SendType.CR1, SendType.CR2
                , SendType.SPK};

        String[] II = {trans(R.string.device_name)
                , SendType.IH1, SendType.IL1
                , SendType.IH2, SendType.IL2
                , SendType.PV1, SendType.PV2
                , SendType.EH1, SendType.EL1
                , SendType.EH2, SendType.EL2
                , SendType.CR1, SendType.CR2
                , SendType.DP1,SendType.DP2
                , SendType.SPK};

        String[] L = {SendType.INTER};
        String[] n = {"這", "是", "我", "不", "知", "道", "的", "型","號"};

        if (SendType.FirstWord == 'T') {
            if (SendType.SecondWord == 'H') {
                if (SendType.ThirdWord == 'L') {
                    String[] THL = ArrayUtils.addAll(TH, L);
                    return THL;
                }//THL
                return TH;
            }//TH
            return n;
        }//T
        else if (SendType.FirstWord == 'I') {
            if (SendType.SecondWord == 'I') {
                if (SendType.ThirdWord == 'L') {
                    String[] IIL = ArrayUtils.addAll(II, L);
                    return IIL;
                }//IIL
                return II;
            }//II
            return n;
        }//I


        return n;
    }





    public String[] getNames() {
        switch (SendType.FirstWord) {

            case 'T':
                SendType.PV1 = trans(R.string.Temperature) + trans(R.string.PV);
                SendType.EH1 = trans(R.string.Temperature) + trans(R.string.EH);
                SendType.EL1 = trans(R.string.Temperature) + trans(R.string.EL);
                SendType.CR1 = trans(R.string.Temperature) + trans(R.string.CR);

                break;

            case 'H':
                SendType.PV1 = trans(R.string.Humidity) + trans(R.string.PV);
                SendType.EH1 = trans(R.string.Humidity) + trans(R.string.EH);
                SendType.EL1 = trans(R.string.Humidity) + trans(R.string.EL);
                SendType.CR1 = trans(R.string.Humidity) + trans(R.string.CR);
                break;

            case 'I':
                SendType.PV1 = trans(R.string.FirstRow) + trans(R.string.PV);
                SendType.IH1 = trans(R.string.FirstRow) + trans(R.string.IH);
                SendType.IL1 = trans(R.string.FirstRow) + trans(R.string.IL);
                SendType.EH1 = trans(R.string.FirstRow) + trans(R.string.EH);
                SendType.EL1 = trans(R.string.FirstRow) + trans(R.string.EL);
                SendType.CR1 = trans(R.string.FirstRow) + trans(R.string.CR);
                SendType.DP1 = trans(R.string.FirstRow) + trans(R.string.decimal_point);
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
                SendType.PV2 = trans(R.string.Humidity) + trans(R.string.PV);
                SendType.EH2 = trans(R.string.Humidity) + trans(R.string.EH);
                SendType.EL2 = trans(R.string.Humidity) + trans(R.string.EL);
                SendType.CR2 = trans(R.string.Humidity) + trans(R.string.CR);
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
        if (SendType.row == '2'){
            switch (SendType.ThirdWord) {
                case 'L':
                    SendType.INTER = trans(R.string.INTER);
                    break;
            }
        }
        if (SendType.row == '3') {
            switch (SendType.ThirdWord) {
                case 'T':
                    SendType.PV3 = trans(R.string.Temperature) + trans(R.string.PV);
                    SendType.EH3 = trans(R.string.Temperature) + trans(R.string.EH);
                    SendType.EL3 = trans(R.string.Temperature) + trans(R.string.EL);
                    SendType.CR3 = trans(R.string.Temperature) + trans(R.string.CR);
                    break;
                case 'H':
                    SendType.PV3 = trans(R.string.Humidity) + trans(R.string.PV);
                    SendType.EH3 = trans(R.string.Humidity) + trans(R.string.EH);
                    SendType.EL3 = trans(R.string.Humidity) + trans(R.string.EL);
                    SendType.CR3 = trans(R.string.Humidity) + trans(R.string.CR);
                    break;
                case 'I':
                    SendType.PV3 = trans(R.string.ThirdRow) + trans(R.string.PV);
                    SendType.IH3 = trans(R.string.ThirdRow) + trans(R.string.IH);
                    SendType.IL3 = trans(R.string.ThirdRow) + trans(R.string.IL);
                    SendType.EH3 = trans(R.string.ThirdRow) + trans(R.string.EH);
                    SendType.EL3 = trans(R.string.ThirdRow) + trans(R.string.EL);
                    SendType.CR3 = trans(R.string.ThirdRow) + trans(R.string.CR);
                    SendType.DP3 = trans(R.string.ThirdRow) + trans(R.string.decimal_point);
                    break;
                case 'C':

                    break;
                case 'D':

                    break;
                case 'E':

                    break;
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

        String[] II = {trans(R.string.device_name)
                , SendType.IH1, SendType.IL1
                , SendType.IH2, SendType.IL2
                , SendType.PV1, SendType.PV2
                , SendType.EH1, SendType.EL1
                , SendType.EH2, SendType.EL2
                , SendType.CR1, SendType.CR2
                , SendType.DP1,SendType.DP2
                , SendType.SPK};

        String[] L = {SendType.INTER};
        /**各種型號都放這*/
        String[] n = {"這", "是", "我", "不", "知", "道", "的", "型","號"};


        if (SendType.FirstWord == 'T') {
            if (SendType.SecondWord == 'H') {
                if (SendType.ThirdWord == 'L') {
                    String[] THL = ArrayUtils.addAll(TH, L);
                    return THL;
                }//THL
                return TH;
            }//TH
            return n;
        }//T
        else if (SendType.FirstWord == 'I') {
            if (SendType.SecondWord == 'I') {
                if (SendType.ThirdWord == 'L') {
                    String[] IIL = ArrayUtils.addAll(II, L);
                    return IIL;
                }//IIL
                return II;
            }//II
            return n;
        }//I


        return n;
    }//getNames End
    public String[] getSQLiteData(){

        String[] TH = {SendType.DeviceName
                , SendType.mPV1, SendType.mPV2
                , SendType.mEH1, SendType.mEL1
                , SendType.mEH2, SendType.mEL2
                , SendType.mCR1, SendType.mCR2
                , SendType.mSPK};

        String[] II = {SendType.DeviceName
                , SendType.mIH1, SendType.mIL1
                , SendType.mIH2, SendType.mIL2
                , SendType.mPV1, SendType.mPV2
                , SendType.mEH1, SendType.mEL1
                , SendType.mEH2, SendType.mEL2
                , SendType.mCR1, SendType.mCR2
                , SendType.mDP1,SendType.mDP2
                , SendType.mSPK};

        String[] L = {SendType.INTER2SQL};
        String[] n = {"這", "是", "我", "不", "知", "道", "的", "型","號"};

        if (SendType.FirstWord == 'T') {
            if (SendType.SecondWord == 'H') {
                if (SendType.ThirdWord == 'L') {
                    String[] THL = ArrayUtils.addAll(TH, L);
                    return THL;
                }//THL
                return TH;
            }//TH
            return n;
        }//T
        else if (SendType.FirstWord == 'I') {
            if (SendType.SecondWord == 'I') {
                if (SendType.ThirdWord == 'L') {
                    String[] IIL = ArrayUtils.addAll(II, L);
                    return IIL;
                }//IIL
                return II;
            }//II
            return n;
        }//I


        return n;
    }






}
