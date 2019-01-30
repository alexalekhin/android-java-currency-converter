package com.lab.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import java.util.ArrayList;

/**
 * Класс, предоставляющий адаптер для заполнения страниц внутри ViewPager.
 * Использует фрагменты для управления каждой страницей.
 */
public class StatePagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<Fragment> FragmentList = new ArrayList<>();
    private final ArrayList<String> FragmentTitleList = new ArrayList<>();

    void addFragment(Fragment fragment, String title) {
        FragmentList.add(fragment);
        FragmentTitleList.add(title);
    }


    StatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return FragmentList.get(i);
    }

    @Override
    public int getCount() {
        return FragmentList.size();
    }
}
