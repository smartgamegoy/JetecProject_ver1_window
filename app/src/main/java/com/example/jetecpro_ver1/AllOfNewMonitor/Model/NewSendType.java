package com.example.jetecpro_ver1.AllOfNewMonitor.Model;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class NewSendType {

    //取得裝置基本資訊
    public static String newDeviceType;
    public static String newDeviceName;
    public static String newDeviceAddress;

    //(BT-XXXXXXXXX-N)
    public static int row;
    public static char newFirstWord;
    public static char newSecondWord;
    public static char newThirdWord;
    public static char newForthWord;
    public static char newFiveWord;
    public static char newSixWord;
    public static char newSevenWord;
    public static char newEightWord;
    public static char newNineWord;
    public static char newTenWord;

    //(BT-XXXXXXXXX-XXXX)後面跟著的-N，預備給以後加入用
    public static char newLastFirstWord;
    public static char newLastSecondWord;
    public static char newLastThirdWord;
    public static char newLastForthWord;

    //是否有紀錄
    public static boolean logSwitch;

    //是否進入工程師模式
    public static boolean engineerMode;
    public static ArrayList<String> engineerModeArrayList = new ArrayList<>();
    public static ArrayAdapter adapter;

    //作弊用
    public static String cheatStringSend;
    public static String cheatByteSend;




}
