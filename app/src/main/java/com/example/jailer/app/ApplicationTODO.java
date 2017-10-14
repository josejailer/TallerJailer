package com.example.jailer.app;

import android.app.Application;

import com.example.jailer.repository.AppDB;

/**
 * Created by Jailer on 7/10/2017.
 */


public class ApplicationTODO extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        AppDB.init(getApplicationContext());
    }

}


