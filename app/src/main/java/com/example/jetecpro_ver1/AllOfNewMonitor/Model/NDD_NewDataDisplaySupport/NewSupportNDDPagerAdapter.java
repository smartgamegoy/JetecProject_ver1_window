package com.example.jetecpro_ver1.AllOfNewMonitor.Model.NDD_NewDataDisplaySupport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NewSendType;
import com.example.jetecpro_ver1.R;

import java.util.ArrayList;
import java.util.List;

public class NewSupportNDDPagerAdapter extends PagerAdapter {
    private String TAG = NewSupportNDDPagerAdapter.class.getSimpleName();
    private List<View> fragmentList;
    private Context context;
    private ArrayList<Integer> getTab;

    public NewSupportNDDPagerAdapter(List<View> fragmentList,Context context,ArrayList<Integer> getTab) {
        this.fragmentList = fragmentList;
        this.context = context;
        this.getTab = getTab;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (mChildCount>0){
            mChildCount --;
            return POSITION_NONE;
        }
        return  super.getItemPosition(object);

    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return o == view;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        container.addView(fragmentList.get(position));
//        return fragmentList.get(position);

        fragmentList.get(position).setTag(position);
        ((ViewPager) container).addView(fragmentList.get(position));
        return fragmentList.get(position);
    }

    private int mChildCount = 0;
    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

            String str = NewSendType.newDeviceType;
            if (NewSendType.newDeviceType.contains("Y")){
                str = NewSendType.newDeviceType.replaceAll("Y","");
            }else if(NewSendType.newDeviceType.contains("Z")){
                str = NewSendType.newDeviceType.replaceAll("Z","");
            }
        Log.d(TAG, "getPageTitle: "+str);
        Log.d(TAG, "getPageTitle: "+getTab);
            switch (position){
                case 0:
                    return trans(R.string.deviceSetting);
                case 1:
                    try {
                        return getTag(str.charAt(5),getTab.get(0));
                    }catch (Exception e){
                        return getTag(str.charAt(5),1);
                    }

                case 2:
                    try {
                        return getTag(str.charAt(6),getTab.get(1));
                    }catch (Exception e){
                        return getTag(str.charAt(6),2);
                    }

                case 3:
                    try {
                        return getTag(str.charAt(7),getTab.get(2));
                    }catch (Exception e){
                        return getTag(str.charAt(7),3);
                    }

                case 4:
                    try {
                        return getTag(str.charAt(8),getTab.get(3));
                    }catch (Exception e){
                        return getTag(str.charAt(8),4);
                    }

                case 5:
                    try {
                        return getTag(str.charAt(9),getTab.get(4));
                    }catch (Exception e){
                        return getTag(str.charAt(9),5);
                    }

                case 6:
                    try {
                        return getTag(str.charAt(10),getTab.get(5));
                    }catch (Exception e){
                        return getTag(str.charAt(10),6);
                    }

                default:
                    return trans(R.string.output);


        }

    }
    private String getTag(char word,int row){
        switch (word){

            case 'T':
                return trans(R.string.Temperature);
            case 'H':
                return trans(R.string.Humidity);
            case 'C':
            case 'D':
            case 'E':
                return trans(R.string.CO2);
            case 'M':
                return "PM2.5";
            case 'Q':
                return "PM10";
            case 'O':
                return trans(R.string.Noise);
            case 'I':
                return trans(R.string.analog)+row;
            case 'P':
                return trans(R.string.press);
            case 'R':
                return "R";
                default:
                    return trans(R.string.output);
        }

    }

    /**
     * 處理文字
     */
    private String trans(int name) {
        String str = context.getResources().getString(name);
        return str;
    }
}
