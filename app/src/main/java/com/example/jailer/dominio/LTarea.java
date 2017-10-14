package com.example.jailer.dominio;

import com.example.jailer.modelo.Tarea;
import com.example.jailer.repository.AppDB;

import java.util.List;

/**
 * Created by jggomez on 05-Sep-17.
 */

public class LTarea implements ILtarea {

    private AppDB database;
    public LTarea(){
        database=AppDB.getInstancia();
    }
    @Override
    public void addTarea(Tarea tarea){
        database.getTareaDAO().insert(tarea);
    }

    @Override
    public List<Tarea> getTareas(){
        return database.getTareaDAO().obtenerTodo();
    }

    @Override
    public void actualizar(Tarea... tareas) {
        database.getTareaDAO().update(tareas);
    }

    @Override
    public Tarea obtenerXID(int id) {
        return database.getTareaDAO().obtenerXID(id);
    }
}

