package com.example.jailer.modelo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by jggomez on 05-Sep-17.
 */

@Entity(tableName ="tareas")
public class Tarea {

    @ColumnInfo
    private String nombre;

    @ColumnInfo
    private boolean realizada;

    @ColumnInfo
    private String fecha;

    @PrimaryKey(autoGenerate = true)
    private  int id;

    public Tarea() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isRealizada() {
        return realizada;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}

