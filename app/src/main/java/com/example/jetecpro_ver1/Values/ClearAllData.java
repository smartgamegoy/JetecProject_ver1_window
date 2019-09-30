package com.example.jetecpro_ver1.Values;

public class ClearAllData {

    public ClearAllData(){

    }

    public void clearAllData(){
        SendType.SendForBLEDataType = null;//要送出的指令
        SendType.SendForBLEbyteType = null;//要送出的指令(byte)
//        SendType.DeviceType = null;//大顯型號
//        SendType.DeviceName = null;//裝置名稱
//        SendType.DeviceAddress = null;//裝置位址
        SendType.DevicePSW = "";//裝置密碼
        SendType.NormalData = null;//顯示不經任何分類的回傳值
        SendType.FirstWord = ' ';
        SendType.SecondWord = ' ';
        SendType.ThirdWord = ' ';

//      SQLite會用到的資料
//        SendType.DB_Name = null;

//    以下是所有會出現的資料
        SendType.PV1 = null;
        SendType.PV2 = null;
        SendType.PV3 = null;

        SendType.EH1 = null;
        SendType.EL1 = null;

        SendType.EH2 = null;
        SendType.EL2 = null;

        SendType.EH3 = null;
        SendType.EL3 = null;

        SendType.IH1 = null;
        SendType.IL1 = null;

        SendType.IH2 = null;
        SendType.IL2 = null;

        SendType.IH3 = null;
        SendType.IL3 = null;

        SendType.CR1 = null;
        SendType.CR2 = null;
        SendType.CR3 = null;

        SendType.DP1 = "";
        SendType.DP2 = "";
        SendType.DP3 = "";

        SendType.SPK = null;

        SendType.DATE = null;
        SendType.TIME = null;
        SendType.LOG = null;
        SendType.COUNT = null;
        SendType.INTER = null;

        SendType.mPV1 = null;
        SendType.mPV2 = null;
        SendType.mPV3 = null;

        SendType.mEH1 = null;
        SendType.mEL1 = null;

        SendType.mEH2 = null;
        SendType.mEL2 = null;

        SendType.mEH3 = null;
        SendType.mEL3 = null;

        SendType.mIH1 = null;
        SendType.mIL1 = null;

        SendType.mIH2 = null;
        SendType.mIL2 = null;

        SendType.mIH3 = null;
        SendType.mIL3 = null;

        SendType.mCR1 = null;
        SendType.mCR2 = null;
        SendType.mCR3 = null;

        SendType.mDP1 = null;
        SendType.mDP2 = null;
        SendType.mDP3 = null;

        SendType.mSPK = null;

        SendType.mDATE = null;//日期
        SendType.mTIME = null;//時間
        SendType.mTIME2COUNT = null;//時間
        SendType.mLOG = null;//記錄功能(ON或OFF)
        SendType.mCOUNT = null;//多少筆資料
        SendType.mINTER = null;//秒數設定
        SendType.INTER2SQL = null;
        SendType.Count2Send= null;

        SendType.arrayDate = null;
        SendType.arrayTime = null;





    }
}
