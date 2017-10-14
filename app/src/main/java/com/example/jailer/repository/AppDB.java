package com.example.jailer.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.jailer.modelo.Tarea;

/**
 * Created by Jailer on 7/10/2017.
 */

@Database(entities = {Tarea.class},version = 1)
public abstract class AppDB extends RoomDatabase {

    private static  AppDB instacia=null;


    public static void init(Context context){

        if(instacia==null){
            instacia= Room.databaseBuilder(context,AppDB.class,"tareas-db").allowMainThreadQueries().build();
        }

    }
    public static AppDB getInstancia(){
        return instacia;
    }
    public abstract TareaDAO getTareaDAO();
}
