package com.example.jetecpro_ver1.Values;

public class SortData {

    String deviceType;


    public SortData(String deviceType) {
        this.deviceType = deviceType;

    }

    public String[] TH() {

        String[] a = {"a", "b", "c"};
        return a;
    }

    public String[] TH_2() {
        String[] a = {"e", "f", "g"};
        return a;
    }

    public String[] getNames() {
        switch (SendType.FirstWord) {
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

            case 'L':

                break;

        }
        String[] a = {"亂", "寫", "些", "東", "西", "不", "給", "它", "錯"};
        return a;
    }


}
