package com.example.jailer.vistas.presenters;

import com.example.jailer.dominio.ILtarea;
import com.example.jailer.dominio.LTarea;
import com.example.jailer.modelo.Tarea;
import com.example.jailer.vistas.IListView;
import com.example.jailer.vistas.ListActivity;

import java.util.List;

/**
 * Created by jggomez on 12-Sep-17.
 */

public class ListPresenter implements IListPresenter {

    private IListView view;
    private ILtarea ltarea;

    public ListPresenter(IListView view) {
        this.view = view;
        ltarea = new LTarea();
    }

    @Override
    public void addTarea(String descTarea, String fecha) {
        Tarea objTarea = new Tarea();
        objTarea.setNombre(descTarea);
        objTarea.setFecha(fecha);
        objTarea.setRealizada(false);

        ltarea.addTarea(objTarea);
//ltarea.getTareas()tarea,
        view.refrescarListaTareas(ltarea.getTareas());
    }

    @Override
    public List<Tarea> obtenerTareas() {
        return ltarea.getTareas();
    }

    @Override
    public void itemCambioEstado(int posicion, boolean realizado) {
        Tarea tarea=ltarea.obtenerXID(posicion + 1);
        tarea.setRealizada(realizado);
        ltarea.actualizar(tarea);
        view.refrescarTarea(tarea,posicion);
    }
}
