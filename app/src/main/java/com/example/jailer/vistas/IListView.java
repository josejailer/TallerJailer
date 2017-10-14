package com.example.jailer.vistas;

import com.example.jailer.modelo.Tarea;

import java.util.List;

/**
 * Created by jggomez on 12-Sep-17.
 */

public interface IListView {

    void clickAddTarea();
    //List<Tarea> lsTarea
    void refrescarListaTareas(List<Tarea> lsTarea);
    //Tarea tarea,
    void refrescarTarea(Tarea tarea,int posicion);

}
