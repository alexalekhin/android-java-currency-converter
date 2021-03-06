package com.lab.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.lab.myapplication.R;
import com.lab.myapplication.converter_entities.CacheManipulator;
import com.lab.myapplication.converter_entities.Converter;
import com.lab.myapplication.converter_entities.ResponseGetter;

import java.util.Objects;

/**
 * Фрагмент для Дополнительного задания
 * Соответствует пункту 2 дополнительного задания
 */
public class AdditionalTaskFragment2 extends Fragment {
    //Выпадающие списки
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    //Поля ввода
    private EditText convertFromField;
    private EditText convertToField;
    //Наблюдатели за обновлениями полей
    public TextWatcher fromTextWatcher;
    public TextWatcher toTextWatcher;
    //Прогресс-бары для каждого из полей
    private ProgressBar progressBarFrom;
    private ProgressBar progressBarTo;

    View view;

    Converter converter;
    CacheManipulator cacheManipulator;

    ResponseGetter responseGetter;

    public AdditionalTaskFragment2() {
        // Required empty public constructor
    }

    /**
     * Инициализация фрагмента с параметрами
     *
     * @param cacheManipulator - ссылка на манипулятор кешируемыми данными
     * @param converter        - ссылка на конвертер
     * @return экземпляр фрагмента
     */
    public static AdditionalTaskFragment2 newInstance(CacheManipulator cacheManipulator, Converter converter) {
        AdditionalTaskFragment2 fragment = new AdditionalTaskFragment2();
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
                null);
        //Получаем ссылки на конвертер и манипулятор кешируемыми данными
        assert getArguments() != null;
        converter = getArguments().getParcelable("converter");
        cacheManipulator = getArguments().getParcelable("cacheManipulator");

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_additional_task2, container, false);
        // Создаём адаптер для выпадающих списков
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                Objects.requireNonNull(getActivity()),
                android.R.layout.simple_spinner_item, Converter.names
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Получаем выпадающие списки
        spinnerFrom = view.findViewById(R.id.add_task_convert_from_spinner);
        spinnerTo = view.findViewById(R.id.add_task_convert_to_spinner);
        //Прикрпеляем к ним адаптеры
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);
        //Получаем редактируемые поля
        convertFromField = view.findViewById(R.id.add_task_convert_from_field);
        convertToField = view.findViewById(R.id.add_task_convert_to_field);
        //Получаем прогресс-бары
        progressBarFrom = view.findViewById(R.id.add_task_progress_bar_from);
        progressBarTo = view.findViewById(R.id.add_task_progress_bar_to);
        //Инициализируем наблюдателя за полем "ИЗ"
        fromTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Проверяем на наличие фокуса в изменяемом окне
                //для избежания бесконечного цикла изменения полей
                if (Objects.requireNonNull(getActivity()).getCurrentFocus() == convertFromField) {
                    if (!s.toString().equals("")) {
                        //Выводим  прогресс-бар на экран
                        progressBarTo.setVisibility(View.VISIBLE);
                        //Устанавливаем поля "ИЗ" и "В" и прогресс-бар в получателя,
                        //который будет наобходимо скрыть после операции
                        responseGetter.progressBar = progressBarTo;
                        responseGetter.textViewToUpdate = convertToField;
                        responseGetter.textViewUpdater = convertFromField;
                        //Устанавливаем получателя для конвертации
                        converter.setAdditionalResponseGetter(responseGetter);
                        //Вызываем конвертацию
                        converter.ConvertAdditional2(
                                convertFromField.getText().toString(),
                                spinnerFrom.getSelectedItem().toString(),
                                spinnerTo.getSelectedItem().toString()
                        );
                    }
                }
            }
        };
        //Инициализируем наблюдателя за полем "В"
        toTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Objects.requireNonNull(getActivity()).getCurrentFocus() == convertToField) {
                    //Проверяем на наличие фокуса в изменяемом окне
                    //для избежания бесконечного цикла изменения полей
                    if (!s.toString().equals("")) {
                        progressBarFrom.setVisibility(View.VISIBLE);
                        //Устанавливаем поля "ИЗ" и "В" и прогресс-бар в получателя,
                        //который будет наобходимо скрыть после операции
                        responseGetter.progressBar = progressBarFrom;
                        responseGetter.textViewToUpdate = convertFromField;
                        responseGetter.textViewUpdater = convertToField;
                        //Устанавливаем получателя для конвертации
                        converter.setAdditionalResponseGetter(responseGetter);
                        //Вызываем конвертацию
                        converter.ConvertAdditional2(
                                convertToField.getText().toString(),
                                spinnerTo.getSelectedItem().toString(),
                                spinnerFrom.getSelectedItem().toString()
                        );

                    }
                }
            }

        };

        //Прикрепляем наблюдателей за полями
        convertFromField.addTextChangedListener(fromTextWatcher);
        convertToField.addTextChangedListener(toTextWatcher);

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
