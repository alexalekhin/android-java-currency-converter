package com.lab.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lab.myapplication.MainActivity;
import com.lab.myapplication.R;

import java.util.Objects;

/**
 * Фрагмент для выюора основного/доплнительного задания
 */
public class MainFragment extends Fragment {
    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        setupButtons(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void setupButtons(View view) {
        //Получаем кнопку для перехода на основное задание
        Button mainTaskBtn = view.findViewById(R.id.main_task_button);
        //Прикрепляем к ней слушателя
        mainTaskBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity) Objects.requireNonNull(getActivity())).setViewPagerFragment(1);
            }
        });

        //Получаем кнопку для перехода на дополнительное задание
        Button addTaskBtn1 = view.findViewById(R.id.add_task_button);
        //Прикрепляем к ней слушателя
        addTaskBtn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity) Objects.requireNonNull(getActivity())).setViewPagerFragment(2);
            }
        });

        //Получаем кнопку для перехода на дополнительное задание
        Button addTaskBtn2 = view.findViewById(R.id.add_task_button2);
        //Прикрепляем к ней слушателя
        addTaskBtn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity) Objects.requireNonNull(getActivity())).setViewPagerFragment(3);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
