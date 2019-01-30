package com.lab.myapplication.converter_entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;


/**
 * Класс, хранящий данные, необходимые для конвертации
 */
public class Converter implements Parcelable {
    static HashMap<String, SimplePair> pairsMap = new HashMap<>();

    /**
     * Названия валют и их коды, отсортированные в лексикографическом порядке
     */
    public static final String[] names = {
            "AED - UAE Dirham",
            "AFN - Afghan Afghani",
            "ALL - Albanian Lek",
            "AMD - Armenian Dram",
            "ANG - Netherlands Antillean Gulden",
            "AOA - Angolan Kwanza",
            "ARS - Argentine Peso",
            "AUD - Australian Dollar",
            "AWG - Aruban Florin",
            "AZN - Azerbaijani Manat",
            "BAM - Bosnia And Herzegovina Konvertibilna Marka",
            "BBD - Barbadian Dollar",
            "BDT - Bangladeshi Taka",
            "BGN - Bulgarian Lev",
            "BHD - Bahraini Dinar",
            "BIF - Burundi Franc",
            "BND - Brunei Dollar",
            "BOB - Bolivian Boliviano",
            "BRL - Brazilian Real",
            "BSD - Bahamian Dollar",
            "BTC - Bitcoin",
            "BTN - Bhutanese Ngultrum",
            "BWP - Botswana Pula",
            "BYN - New Belarusian Ruble",
            "BYR - Belarusian Ruble",
            "BZD - Belize Dollar",
            "CAD - Canadian Dollar",
            "CDF - Congolese Franc",
            "CHF - Swiss Franc",
            "CLP - Chilean Peso",
            "CNY - Chinese Yuan",
            "COP - Colombian Peso",
            "CRC - Costa Rican Colon",
            "CUP - Cuban Peso",
            "CVE - Cape Verdean Escudo",
            "CZK - Czech Koruna",
            "DJF - Djiboutian Franc",
            "DKK - Danish Krone",
            "DOP - Dominican Peso",
            "DZD - Algerian Dinar",
            "EGP - Egyptian Pound",
            "ERN - Eritrean Nakfa",
            "ETB - Ethiopian Birr",
            "EUR - Euro",
            "FJD - Fijian Dollar",
            "FKP - Falkland Islands Pound",
            "GBP - British Pound",
            "GEL - Georgian Lari",
            "GHS - Ghanaian Cedi",
            "GIP - Gibraltar Pound",
            "GMD - Gambian Dalasi",
            "GNF - Guinean Franc",
            "GTQ - Guatemalan Quetzal",
            "GYD - Guyanese Dollar",
            "HKD - Hong Kong Dollar",
            "HNL - Honduran Lempira",
            "HRK - Croatian Kuna",
            "HTG - Haitian Gourde",
            "HUF - Hungarian Forint",
            "IDR - Indonesian Rupiah",
            "ILS - Israeli New Sheqel",
            "INR - Indian Rupee",
            "IQD - Iraqi Dinar",
            "IRR - Iranian Rial",
            "ISK - Icelandic Króna",
            "JMD - Jamaican Dollar",
            "JOD - Jordanian Dinar",
            "JPY - Japanese Yen",
            "KES - Kenyan Shilling",
            "KGS - Kyrgyzstani Som",
            "KHR - Cambodian Riel",
            "KMF - Comorian Franc",
            "KPW - North Korean Won",
            "KRW - South Korean Won",
            "KWD - Kuwaiti Dinar",
            "KYD - Cayman Islands Dollar",
            "KZT - Kazakhstani Tenge",
            "LAK - Lao Kip",
            "LBP - Lebanese Lira",
            "LKR - Sri Lankan Rupee",
            "LRD - Liberian Dollar",
            "LSL - Lesotho Loti",
            "LVL - Latvian Lats",
            "LYD - Libyan Dinar",
            "MAD - Moroccan Dirham",
            "MDL - Moldovan Leu",
            "MGA - Malagasy Ariary",
            "MKD - Macedonian Denar",
            "MMK - Myanma Kyat",
            "MNT - Mongolian Tugrik",
            "MOP - Macanese Pataca",
            "MRO - Mauritanian Ouguiya",
            "MUR - Mauritian Rupee",
            "MVR - Maldivian Rufiyaa",
            "MWK - Malawian Kwacha",
            "MXN - Mexican Peso",
            "MYR - Malaysian Ringgit",
            "MZN - Mozambican Metical",
            "NAD - Namibian Dollar",
            "NGN - Nigerian Naira",
            "NIO - Nicaraguan Cordoba",
            "NOK - Norwegian Krone",
            "NPR - Nepalese Rupee",
            "NZD - New Zealand Dollar",
            "OMR - Omani Rial",
            "PAB - Panamanian Balboa",
            "PEN - Peruvian Nuevo Sol",
            "PGK - Papua New Guinean Kina",
            "PHP - Philippine Peso",
            "PKR - Pakistani Rupee",
            "PLN - Polish Zloty",
            "PYG - Paraguayan Guarani",
            "QAR - Qatari Riyal",
            "RON - Romanian Leu",
            "RSD - Serbian Dinar",
            "RUB - Russian Ruble",
            "RWF - Rwandan Franc",
            "SAR - Saudi Riyal",
            "SBD - Solomon Islands Dollar",
            "SCR - Seychellois Rupee",
            "SDG - Sudanese Pound",
            "SEK - Swedish Krona",
            "SGD - Singapore Dollar",
            "SHP - Saint Helena Pound",
            "SLL - Sierra Leonean Leone",
            "SOS - Somali Shilling",
            "SRD - Surinamese Dollar",
            "STD - Sao Tome And Principe Dobra",
            "SYP - Syrian Pound",
            "SZL - Swazi Lilangeni",
            "THB - Thai Baht",
            "TJS - Tajikistani Somoni",
            "TMT - Turkmenistan Manat",
            "TND - Tunisian Dinar",
            "TOP - Paanga",
            "TRY - Turkish New Lira",
            "TTD - Trinidad and Tobago Dollar",
            "TWD - New Taiwan Dollar",
            "TZS - Tanzanian Shilling",
            "UAH - Ukrainian Hryvnia",
            "UGX - Ugandan Shilling",
            "USD - United States Dollar",
            "UYU - Uruguayan Peso",
            "UZS - Uzbekistani Som",
            "VEF - Venezuelan Bolivar",
            "VND - Vietnamese Dong",
            "VUV - Vanuatu Vatu",
            "WST - Samoan Tala",
            "XAF - Central African CFA Franc",
            "XCD - East Caribbean Dollar",
            "XDR - Special Drawing Rights",
            "XOF - West African CFA Franc",
            "XPF - CFP Franc",
            "YER - Yemeni Rial",
            "ZAR - South African Rand",
            "ZMW - Zambian Kwacha"};

    private String lastFrom;
    private String lastTo;
    private String lastValue;

    private RetrieveCurrencyTask.AsyncResponse basicResponseGetter;
    private RetrieveCurrencyTask.AsyncResponse addResponseGetter;
    private RetrieveCurrencyTask.AsyncResponse addResponseGetter2;

    /**
     * Базовая конвертационная пара. Содержит коэффициент, связывающий две валюты
     */
    class SimplePair {
        @SerializedName("val")
        @Expose
        private Double val;

        Double getVal() {
            return val;
        }

    }

    public Converter() {

    }

    //Необходимо для возможности передавать через Bundle в фрагменты
    private Converter(Parcel in) {
        lastFrom = in.readString();
        lastTo = in.readString();
        lastValue = in.readString();
    }

    //Необходимо для возможности передавать через Bundle в фрагменты
    public static final Creator<Converter> CREATOR = new Creator<Converter>() {
        @Override
        public Converter createFromParcel(Parcel in) {
            return new Converter(in);
        }

        @Override
        public Converter[] newArray(int size) {
            return new Converter[size];
        }
    };


    /**
     * Метод конвертации для основного задания
     *
     * @param value - значение конвертируемой валюты
     * @param from  - валюта, из которой конвертируем
     * @param to    - валюта, в которую конвертируем
     */
    public void ConvertBasic(String value, String from, String to) {
        //Устанавливаем значения для конвертации
        lastValue = value;
        setLastFrom(optimizeIncomingString(from));
        setLastTo(optimizeIncomingString(to));
        //Отправляем запрос к API в потоке, отеделённом от UI потока
        final RetrieveCurrencyTask currencyTask = new RetrieveCurrencyTask(basicResponseGetter);
        currencyTask.execute(optimizeIncomingString(from), optimizeIncomingString(to));
    }


    /**
     * Метод, конвертации для дополнительного задания 1.
     * Дублирование необходимо для возможности выполнения конвертаций одновременно,
     * можно было использовать два отдельных конвертатора на каждое из заданий.
     *
     * @param value - значение конвертируемой валюты
     * @param from  - валюта, из которой конвертируем
     * @param to    - валюта, в которую конвертируем
     */
    public void ConvertAdditional1(String value, String from, String to) {
        //Устанавливаем значения для конвертации
        lastValue = value;
        setLastFrom(optimizeIncomingString(from));
        setLastTo(optimizeIncomingString(to));
        //Отправляем запрос к API в потоке, отеделённом от UI потока
        final RetrieveCurrencyTask currencyTask = new RetrieveCurrencyTask(addResponseGetter2);
        currencyTask.execute(optimizeIncomingString(from), optimizeIncomingString(to));
    }

    /**
     * Метод, конвертации для дополнительного задания 2.
     * Дублирование необходимо для возможности выполнения конвертаций одновременно,
     * можно было использовать два отдельных конвертатора на каждое из заданий.
     *
     * @param value - значение конвертируемой валюты
     * @param from  - валюта, из которой конвертируем
     * @param to    - валюта, в которую конвертируем
     */
    public void ConvertAdditional2(String value, String from, String to) {
        //Устанавливаем значения для конвертации
        lastValue = value;
        setLastFrom(optimizeIncomingString(from));
        setLastTo(optimizeIncomingString(to));
        //Отправляем запрос к API в потоке, отеделённом от UI потока
        final RetrieveCurrencyTask currencyTask = new RetrieveCurrencyTask(addResponseGetter);
        currencyTask.execute(optimizeIncomingString(from), optimizeIncomingString(to));
    }

    /**
     * Устанавливает получателя результатов запроса для основного задания
     *
     * @param responseGetter - получатель
     */
    public void setBasicResponseGetter(RetrieveCurrencyTask.AsyncResponse responseGetter) {
        this.basicResponseGetter = responseGetter;
        responseGetter.setConverter(this);
    }

    /**
     * Устанавливает получателя результатов запроса для дополнитльного задания 1
     * Дублирование необходимо для возможности выполнения конвертаций одновременно,
     * можно было использовать два отдельных конвертатора на каждое из заданий.
     *
     * @param responseGetter - получатель
     */
    public void setAdditionalResponseGetter(RetrieveCurrencyTask.AsyncResponse responseGetter) {
        this.addResponseGetter = responseGetter;
        responseGetter.setConverter(this);
    }

    /**
     * Устанавливает получателя результатов запроса для дополнитльного задания 2
     * Дублирование необходимо для возможности выполнения конвертаций одновременно,
     * можно было использовать два отдельных конвертатора на каждое из заданий.
     *
     * @param responseGetter - получатель
     */
    public void setAdditionalResponseGetter2(RetrieveCurrencyTask.AsyncResponse responseGetter) {
        this.addResponseGetter2 = responseGetter;
        responseGetter.setConverter(this);
    }


    /**
     * Сохраняет последнюю использованную валюту, из которой конвертировали
     *
     * @param lastFrom - валюта, из которой прозошёлпоследний перевод
     */
    private void setLastFrom(String lastFrom) {
        this.lastFrom = lastFrom;
    }

    /**
     * Сохраняет последнюю использованную валюту, в которую конвертировали
     *
     * @param lastTo - валюта, в которую произошёл последний перевод
     */
    private void setLastTo(String lastTo) {
        this.lastTo = lastTo;
    }

    String getLastFrom() {
        return lastFrom;
    }

    String getLastTo() {
        return lastTo;
    }

    String getLastValue() {
        return lastValue;
    }

    /**
     * Возврат значений в качестве JSON-строки
     *
     * @return - JSON-строка с данными
     */
    String getValuesAsJson() {
        Gson gson = new Gson();
        return gson.toJson(pairsMap);
    }

    /**
     * Получение закешированных значений в качестве JSON-строки и преобразование из для пользования
     *
     * @param json - JSON-строка с данными
     */
    void getValuesFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, SimplePair>>() {
        }.getType();
        pairsMap = gson.fromJson(json, type);
    }

    private String optimizeIncomingString(String str) {
        if (str.contains(" ")) {
            str = str.substring(0, str.indexOf(" "));
        }
        return str;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lastFrom);
        dest.writeString(lastTo);
        dest.writeString(lastValue);
    }


}
