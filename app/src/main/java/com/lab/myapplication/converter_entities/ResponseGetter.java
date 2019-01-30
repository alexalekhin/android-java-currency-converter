package com.lab.myapplication.converter_entities;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Класс для получения результатов выполнения запроса.
 * Содержит логику для получения итогового резлультата конвертации и обновления необходимых полей.
 */
public class ResponseGetter implements RetrieveCurrencyTask.AsyncResponse {
    //Поле, вызывающее обновление
    public TextView textViewUpdater;
    //Поле, которое обновляется
    public TextView textViewToUpdate;
    //Прогресс-бар, отражающий процесс обновления
    public ProgressBar progressBar;

    private Context context;
    //Конвертер
    private Converter converter;


    public ResponseGetter(Context context,
                          TextView textViewUpdater,
                          TextView textViewToUpdate,
                          ProgressBar progressBar) {
        this.context = context;
        this.progressBar = progressBar;
        this.textViewToUpdate = textViewToUpdate;
        this.textViewUpdater = textViewUpdater;
    }

    /**
     * Вызывается в RetrieveCurrencyTask, и выполняется по окончании.
     * Содержит получение итогового значения и обновления необходимого поля этим значением.
     * Использует GSON для парсинга строки запроса
     *
     * @param output - результат запроса к API
     */
    @Override
    public void onProcessFinish(String output) {
        //Для парсинга результата запроса
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        //Пара, хранящая данные из запроса
        Converter.SimplePair simplePair;

        gson = gsonBuilder.create();
        JSONObject jsonObject;
        try {
            //Проверка значения конвертации на пустую строку
            if (!converter.getLastValue().equals("")) {
                //Проверка на то, что запрос пришёл без ошибок: сеть доступна, данные получены
                if (output != null) {
                    jsonObject = new JSONObject(output);
                    //Парсим объект и получаем значения для базовой пары конвертации
                    //Строка соответствует стандрарту API: "ИЗ_В"
                    simplePair = gson.fromJson(
                            jsonObject.getJSONObject(converter.getLastFrom() + "_" + converter.getLastTo()).toString(),
                            Converter.SimplePair.class
                    );
                    //Сохраняем её в HashMap, чтобы позже закешировать и использовать, если нет сети
                    Converter.pairsMap.put(converter.getLastFrom() + "_" + converter.getLastTo(), simplePair);
                    //Убираем фокус из редактируемого поля, чтобы избежать бесконечного цикла
                    textViewToUpdate.clearFocus();
                    //Выставляем в него результирующее значение с двумя знаками после запятой и точкой в качестве разделителя
                    textViewToUpdate.setText(String.format(Locale.ENGLISH, "%.2f", doCalculations(simplePair.getVal())));
                } else { //Случай, когда сеть недоступна, но данные были закешированным ранее
                    CharSequence text;
                    //Длительность уведомления пользователя
                    int duration = Toast.LENGTH_SHORT;
                    if ((simplePair = Converter.pairsMap.get(converter.getLastFrom() + "_" + converter.getLastTo())) != null) {
                        //Если значение есть в карте закешированных конвертаций
                        text = "Network isn't connected!\nInfo may be outdated.";
                        textViewToUpdate.clearFocus();
                        textViewToUpdate.setText(String.format(Locale.ENGLISH, "%.2f", doCalculations(simplePair.getVal())));
                    } else if ((simplePair = Converter.pairsMap.get(converter.getLastTo() + "_" + converter.getLastFrom())) != null) {
                        //Если его нет в карте, конвертаций, но мы можем его восстановить от обратного к нему
                        text = "Network isn't connected!\nInfo may be outdated or inaccurate.";
                        textViewToUpdate.clearFocus();
                        textViewToUpdate.setText(String.format(Locale.ENGLISH, "%.2f", doCalculations(1.0f / simplePair.getVal())));
                    } else {
                        //Случай, когда нет сети, и данные не были закешированны и "догадаться" до них невозможно
                        text = "Network isn't connected!\nThere is no available cached info.";
                    }
                    //Инициализируем систему обратной связи,
                    //где context - активити, куда выводится уведомление
                    //text - выводимое сообщение
                    //duration - продолжительность его нахождения на экране
                    Toast toast = Toast.makeText(context, text, duration);
                    //Центрируем
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    //Уведомляем пользователя
                    toast.show();
                }
            }

        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        //Скрываем прогресс-бар
        progressBar.setVisibility(View.INVISIBLE);
        textViewUpdater.setEnabled(true);

    }

    /**
     * Метод для вычисления итогового значения
     *
     * @param simplePairVal - коэффициент в паре конвертации
     * @return итоговое значение
     */
    private Double doCalculations(Double simplePairVal) {
        //Получаем изначальное значение для конвертации
        double startSum = Double.valueOf(converter.getLastValue());
        //Получаем результат
        return simplePairVal * startSum;
    }

    @Override
    public void setConverter(Converter converter) {
        this.converter = converter;
    }
}
