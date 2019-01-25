package com.lab.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.lab.myapplication.fragments.AdditionalTaskFragment;
import com.lab.myapplication.fragments.BasicTaskFragment;
import com.lab.myapplication.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {
    StatePagerAdapter statePagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statePagerAdapter = new StatePagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.base_container);

        setupViewPager(viewPager);
        setViewPagerFragment(0);
    }

    private void setupViewPager(ViewPager viewPager) {
        StatePagerAdapter adapter = new StatePagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new MainFragment(), "MainFragment");
        adapter.addFragment(new BasicTaskFragment(), "BasicTaskFragment");
        adapter.addFragment(new AdditionalTaskFragment(), "AdditionalTaskFragment");

        viewPager.setAdapter(adapter);
    }

    public void setViewPagerFragment(int fragmentNum) {
        viewPager.setCurrentItem(fragmentNum);
    }

}
