package com.lab.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.lab.myapplication.R;
import com.lab.myapplication.converter_entities.CacheManipulator;
import com.lab.myapplication.converter_entities.Converter;
import com.lab.myapplication.converter_entities.ResponseGetter;

import java.util.Objects;


/**
 * Фрагмент для основного задания
 */
public class BasicTaskFragment extends Fragment {

    //Выпадающие списки
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    //Поле ввода
    private EditText convertFromField;
    //Поле вывода
    private TextView textView;
    //Кнопка для вызова конвертации
    private Button convertBtn;
    //Прогресс-бар
    private ProgressBar progressBar;

    View view;

    Converter converter;
    CacheManipulator cacheManipulator;

    ResponseGetter responseGetter;


    public BasicTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Инициализация фрагмента с параметрами
     *
     * @param cacheManipulator - ссылка на манипулятор кешируемыми данными
     * @param converter        - ссылка на конвертер
     * @return экземпляр фрагмента
     */
    public static BasicTaskFragment newInstance(CacheManipulator cacheManipulator, Converter converter) {
        BasicTaskFragment fragment = new BasicTaskFragment();
        Bundle args = new Bundle();
        args.putParcelable("cacheManipulator", cacheManipulator);
        args.putParcelable("converter", converter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Инициализируем responseGetter,который пока что не знает,
        //куда и откуда записывать и какой progressBar использовать
        responseGetter = new ResponseGetter(
                getActivity(),
                null,
                null,
                null
        );
        //Получаем ссылки на конвертер и манипулятор кешируемыми данными
        assert getArguments() != null;
        converter = getArguments().getParcelable("converter");
        cacheManipulator = getArguments().getParcelable("cacheManipulator");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_basic_task, container, false);
        // Создаём адаптер для выпадающих списков
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                Objects.requireNonNull(getActivity()),
                android.R.layout.simple_spinner_item,
                Converter.names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Получаем выпадающие списки
        spinnerFrom = view.findViewById(R.id.basic_task_convert_from_spinner);
        spinnerTo = view.findViewById(R.id.basic_task_convert_to_spinner);
        //Прикрпеляем к ним адаптеры
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);
        //Получаем кнопку для активации конвертации
        convertBtn = view.findViewById(R.id.basic_task_convert_button);
        //Получаем прогресс-бар
        progressBar = view.findViewById(R.id.basic_task_progressBar);
        //Получаем поле вывода
        textView = view.findViewById(R.id.basic_task_convert_to_field);
        //Получаем редактируемое поле
        convertFromField = view.findViewById(R.id.basic_task_convert_from_field);
        //Инициализируем слушателя кнопки
        convertBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Деактивируем кнопку, на время конвертации
                convertBtn.setEnabled(false);
                textView.setText("");
                progressBar.setVisibility(View.VISIBLE);
                //Заполняем получателя данными о
                //Прогресс-баре
                responseGetter.progressBar = progressBar;
                //Обновляемом поле
                responseGetter.textViewToUpdate = textView;
                //Обновляющем поле
                responseGetter.textViewUpdater = convertBtn;
                //Устанавливаем получателя результата
                converter.setBasicResponseGetter(responseGetter);
                //Выполняем конвертацию
                converter.ConvertBasic(
                        convertFromField.getText().toString(),
                        spinnerFrom.getSelectedItem().toString(),
                        spinnerTo.getSelectedItem().toString()
                );


            }
        });

        return view;
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
