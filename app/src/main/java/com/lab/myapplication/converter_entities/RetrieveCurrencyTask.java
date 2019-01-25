package com.lab.myapplication.converter_entities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetrieveCurrencyTask extends AsyncTask<String, Void, String> {

    private Exception exception;

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public AsyncResponse delegate = null;

    public RetrieveCurrencyTask(AsyncResponse delegate){
        this.delegate = delegate;
    }

    protected void onPreExecute() {
//        progressBar.setVisibility(View.VISIBLE);
//        responseView.setText("");
    }

    protected String doInBackground(String... currencies) {
        /**
         * //basic query
         * */
        //https://free.currencyconverterapi.com/api/v6/convert?q=USD_PHP
        /**
         * //List of currencies
         * */
        // https://api.currencyconverterapi.com/api/v6/currencies?apiKey=[YOUR_API_KEY]

        //TODO get params
        String from = currencies[0];
        String to = currencies[1];
//        String email = emailText.getText().toString();
        // Do some validation here

        try {
            URL url = new URL("https://free.currencyconverterapi.com/api/v6/"
                    + "convert?"
                    + "q="
                    + from + "_" + to +
                    "&compact=y");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        if (response == null) {
            response = "THERE WAS AN ERROR";
        }
        delegate.processFinish(response);

        //progressBar.setVisibility(View.GONE);
        //Log.i("INFO", response);
        //responseView.setText(response);
    }
}