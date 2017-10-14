package com.example.jailer.vistas.presenters;

import com.example.jailer.modelo.Tarea;

import java.util.List;

/**
 * Created by jggomez on 12-Sep-17.
 */

public interface IListPresenter {

    void addTarea(String descTarea, String fecha);

    List<Tarea> obtenerTareas();

    void itemCambioEstado(int posicion, boolean realizado);

}
