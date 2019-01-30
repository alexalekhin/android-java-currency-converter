package com.lab.myapplication.converter_entities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;

/**
 * Класс, производящий всё взаимодействие с API.
 */
public class RetrieveCurrencyTask extends AsyncTask<String, Void, String> {
    //Адрес используемого API
    private static final String API_URL = "https://free.currencyconverterapi.com/api/v6/";

    /**
     * Класс определяющий интерфейс для получения результатов выполнения запроса
     */
    public interface AsyncResponse {
        void onProcessFinish(String output);

        void setConverter(Converter converter);
    }

    //Объект для обработки лезультата зпроса
    private AsyncResponse delegate;

    RetrieveCurrencyTask(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    protected void onPreExecute() {
    }

    /**
     * Формирует запрос и выполняет взаимодействие с API
     *
     * @param currencies - валюты для конвертации
     * @return JSON-строка с результатом запроса или null, если произошли ошибки при работе с сетью
     */
    protected String doInBackground(String... currencies) {
        //Подразумевается, что первый параметр всегд валюта "ИЗ"
        String from = currencies[0];
        //Подразумевается, что второй параметр всегд валюта "В"
        String to = currencies[1];
        //Проверяем подключение к сети
        if (isOnline()) { //Сеть работает

            try {
                //Формируем строку запроса на получение конвертационной пары по стандарту API
                URL url = new URL(API_URL
                        + "convert?"
                        + "q="
                        + from + "_" + to +
                        "&compact=y");
                //Устанавливаем соединение
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    //Считываем строку
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(urlConnection.getInputStream())
                    );

                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();

                    //Возвращаем результат
                    return stringBuilder.toString();
                } finally {
                    //Закрываем соединение
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR:", e.getMessage(), e);
                return null;
            }

        } else { //Сеть не доступна
            return null;
        }
    }

    /**
     * Исполняется по окончании
     *
     * @param response - результат из doInBackground
     */
    protected void onPostExecute(String response) {
        //Вызываем метод из получателя результата
        delegate.onProcessFinish(response);
    }

    /**
     * Проверяет сеть на доступность
     *
     * @return true - если сеть доступна, false - если не доступна
     */
    private boolean isOnline() {
        try {
            //Выставляем макс задержку проверки
            int timeoutMs = 1500;
            //Подключаемся к сокету и проверяем на доступность адрес
            Socket sock = new Socket();
            SocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);
            sock.connect(socketAddress, timeoutMs);
            sock.close();
            //Возвращаем результат проверки
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}