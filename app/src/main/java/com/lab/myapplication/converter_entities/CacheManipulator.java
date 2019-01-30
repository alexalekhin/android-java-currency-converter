package com.lab.myapplication.converter_entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.lab.myapplication.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

/**
 * Класс для манипуляции закешированными данными
 */
public class CacheManipulator implements Parcelable {
    private MainActivity activity;
    private String jsonValues = "";

    public CacheManipulator(MainActivity activity) {
        this.activity = activity;
    }

    //Необходимо для возможности передавать через Bundle в фрагменты
    private CacheManipulator(Parcel in) {
    }

    //Необходимо для возможности передавать через Bundle в фрагменты
    public static final Creator<CacheManipulator> CREATOR = new Creator<CacheManipulator>() {
        @Override
        public CacheManipulator createFromParcel(Parcel in) {
            return new CacheManipulator(in);
        }

        @Override
        public CacheManipulator[] newArray(int size) {
            return new CacheManipulator[size];
        }
    };


    /**
     * Метод сохраняющий данные после работы, вызывается в onStop() MainActivity
     *
     * @param outputFileName - имя файла для записи
     */
    public void saveValuesToCache(String outputFileName) {
        try {
            jsonValues = activity.converter.getValuesAsJson();
            ObjectOutput out = new ObjectOutputStream(new FileOutputStream
                    (new File(activity.getCacheDir(), "") + File.separator + outputFileName));
            out.writeObject(jsonValues);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, загружающий закешированные ранне данные, вызывается в onStart() MainActivity
     *
     * @param inputFileName - имя файла для считывания
     */
    public void loadCachedValues(String inputFileName) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream
                    (new File(activity.getCacheDir() + File.separator + inputFileName)));
            jsonValues = (String) in.readObject();
            activity.converter.getValuesFromJson(jsonValues);
            in.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
