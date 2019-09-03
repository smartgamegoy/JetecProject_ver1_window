package com.example.jetecpro_ver1.Values;

import android.bluetooth.BluetoothGattCharacteristic;

import com.example.jetecpro_ver1.BLE_function.BluetoothLeService;

public class SendType {

    public static String SendForBLEDataType;//要送出的指令
    public static String DeviceType;//大顯型號
    public static char row;//大顯排數
    public static char FirstWord;//大顯字1
    public static char SecondWord;//大顯字2
    public static char ThirdWord;//大顯字3
    public static String DeviceName;//裝置名稱
    public static String DeviceAddress;//裝置位址
    public static BluetoothGattCharacteristic Mycharacteristic;//每次傳輸資料用的
    public static BluetoothLeService getSendBluetoothLeService;
    public static String DevicePSW;//裝置密碼
    public static String NormalData;//顯示不經任何分類的回傳值
    public static String DownloadData;//處理下載下來的資料

//    SQLite會用到的資料
    public static String DB_Name = "LeCustomDB.db";
    public static String DB_TABLE;

//    DownLoad下來的資料分類
    public static String FirstRecordData;
    public static String SecondRecordData;
    public static String ThirdRecordData;
    public static int TemperatureInt;
    public static int HumidityInt;


//    以下是所有會出現的資料
    public static String PV1 ;//補正
    public static String PV2 ;//補正
    public static String PV3 ;//補正

    public static String EH1 ;//警報上限
    public static String EL1 ;//警報下限

    public static String EH2 ;//警報上限
    public static String EL2 ;//警報下限

    public static String EH3 ;//警報上限
    public static String EL3 ;//警報下限

    public static String IH1 ;//量程上限
    public static String IL1 ;//量程下限

    public static String IH2;//量程上限
    public static String IL2;//量程下限

    public static String IH3;//量程上限
    public static String IL3;//量程下限

    public static String CR1;//顏色轉換
    public static String CR2;//顏色轉換
    public static String CR3;//顏色轉換

    public static String DP1;//小數點
    public static String DP2;//小數點
    public static String DP3;//小數點

    public static String SPK;//警報聲
    //記錄系列
    public static String DATE;//日期
    public static String TIME;//時間
    public static String LOG;//記錄功能(ON或OFF)
    public static String COUNT;//多少筆資料
    public static String INTER;//秒數設定

    //以下是所有出現的值的變數
    public static String mPV1;
    public static String mPV2;
    public static String mPV3;

    public static String mEH1;
    public static String mEL1;

    public static String mEH2;
    public static String mEL2;

    public static String mEH3;
    public static String mEL3;

    public static String mIH1;
    public static String mIL1;

    public static String mIH2;
    public static String mIL2;

    public static String mIH3;
    public static String mIL3;

    public static String mCR1;
    public static String mCR2;
    public static String mCR3;

    public static String mDP1;
    public static String mDP2;
    public static String mDP3;

    public static String mSPK;

    //記錄系列
    public static String mDATE;//日期
    public static String mTIME;//時間
    public static String mLOG;//記錄功能(ON或OFF)
    public static String mCOUNT;//多少筆資料
    public static String mINTER;//秒數設定
    public static String INTER2SQL;
    public static String Count2Send;
}
