package com.example.jailer.dominio;

import com.example.jailer.modelo.Tarea;

import java.util.List;

/**
 * Created by jggomez on 12-Sep-17.
 */

public interface ILtarea {


    void addTarea(Tarea tarea);

    List<Tarea> getTareas();
    void actualizar(Tarea... tareas);
    Tarea obtenerXID(int id);
}