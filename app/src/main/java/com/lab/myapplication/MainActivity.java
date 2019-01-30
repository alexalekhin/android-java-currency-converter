package com.lab.myapplication;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.lab.myapplication.converter_entities.CacheManipulator;
import com.lab.myapplication.converter_entities.Converter;
import com.lab.myapplication.fragments.AdditionalTaskFragment2;
import com.lab.myapplication.fragments.AdditionalTaskFragment1;
import com.lab.myapplication.fragments.BasicTaskFragment;
import com.lab.myapplication.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {
    //Используем ViewPager для просмотра приложения как книги
    ViewPager viewPager;
    //Адаптер для ViewPager
    StatePagerAdapter statePagerAdapter;


    public CacheManipulator cacheManipulator;
    public Converter converter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Выставляем layout'ы
        setContentView(R.layout.activity_main);
        statePagerAdapter = new StatePagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.base_container);

        cacheManipulator = new CacheManipulator(this);
        converter = new Converter();

        setupViewPager(viewPager);
        //Устанавливаем MainFragment по умолчанию
        setViewPagerFragment(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cacheManipulator.loadCachedValues("values.txt");
    }

    @Override
    protected void onStop() {
        super.onStop();
        cacheManipulator.saveValuesToCache("values.txt");

    }

    /**
     * Настривает ViewPager,заполняет его фрагментами
     *
     * @param viewPager - ViewPager для настройки
     */
    private void setupViewPager(ViewPager viewPager) {
        StatePagerAdapter adapter = new StatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(MainFragment.newInstance(), "MainFragment");
        adapter.addFragment(BasicTaskFragment.newInstance(cacheManipulator, converter), "BasicTaskFragment");
        adapter.addFragment(AdditionalTaskFragment1.newInstance(cacheManipulator, converter), "AdditionalTaskFragment1");
        adapter.addFragment(AdditionalTaskFragment2.newInstance(cacheManipulator, converter), "AdditionalTaskFragment2");
        viewPager.setAdapter(adapter);
    }

    /**
     * Меняет выбранный в данный момент фрагмент ViewPager'а по индексу
     *
     * @param fragmentNum - индекс фрагмента, на который переходим
     */
    public void setViewPagerFragment(int fragmentNum) {
        viewPager.setCurrentItem(fragmentNum);
    }

}
