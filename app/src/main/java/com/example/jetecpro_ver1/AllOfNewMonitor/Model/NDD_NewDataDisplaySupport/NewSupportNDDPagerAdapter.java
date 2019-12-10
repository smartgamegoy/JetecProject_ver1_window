package com.example.jetecpro_ver1.AllOfNewMonitor.Model.NDD_NewDataDisplaySupport;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class NewSupportNDDPagerAdapter extends PagerAdapter {
    private List<View> fragmentList;

    public NewSupportNDDPagerAdapter(List<View> fragmentList) {
        this.fragmentList = fragmentList;
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
        container.addView(fragmentList.get(position));
        return fragmentList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:

                return "Setting";
            case 1:

                return "Second";
            case 2:

                return "Third";
            case 3:

                return "Forth";
            case 4:

                return "Five";
            case 5:

                return "Six";
            case 6:

                return "Seven";
                default:
                    return "多得";
        }
    }
}
