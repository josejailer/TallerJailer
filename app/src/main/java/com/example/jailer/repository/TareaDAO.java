package com.example.jailer.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.jailer.modelo.Tarea;

import java.util.List;

/**
 * Created by Jailer on 7/10/2017.
 */

@Dao
public interface TareaDAO {

    @Insert
    void insert(Tarea... tareas);
    @Update
    void update(Tarea... tareas);

    @Delete
    void delete(Tarea... tarea);

    @Query("select * from tareas")
    List<Tarea> obtenerTodo();

    @Query("select * from tareas where id= :id")
    Tarea obtenerXID(int id);
}
