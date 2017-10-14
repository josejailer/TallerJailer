package com.example.jailer.vistas;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.jailer.modelo.Tarea;
import com.example.jailer.tallerjailer.R;
import com.example.jailer.vistas.adaptadores.TodoListAdapter;
import com.example.jailer.vistas.presenters.IListPresenter;
import com.example.jailer.vistas.presenters.ListPresenter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FechaActivity extends AppCompatActivity implements
        View.OnClickListener,IListView, TodoListAdapter.OnListenerItemCheck {
    EditText efecha;
    private  int dia,mes,ano;
    @BindView(R.id.txtNombreTarea)
    EditText txtNombreTarea;

    @BindView(R.id.txtFecha)
    EditText txtFecha;

    private IListPresenter listPresenter;

    @BindView(R.id.rvListTODO)
    RecyclerView rvListTODO;
    @BindView(R.id.btnGuardar)
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fecha);

        ButterKnife.bind(this);

        efecha=(EditText)findViewById(R.id.txtFecha);

        efecha.setOnClickListener(this);
        listPresenter = new ListPresenter(this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rvListTODO.setLayoutManager(llm);

        List<Tarea> lsTarea = listPresenter.obtenerTareas();

        rvListTODO.setAdapter(new TodoListAdapter(lsTarea, this));


    }

    @Override
    public void onClick(View v) {
        efecha.setInputType(InputType.TYPE_NULL);

        if(v==efecha){
            final Calendar c= Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            ano=c.get(Calendar.YEAR);
            efecha.setInputType(InputType.TYPE_NULL);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    efecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                }
            }
                    ,dia,mes,ano);
            datePickerDialog.show();
        }

    }

    @OnClick(R.id.btnGuardar)
    @Override
    public void clickAddTarea() {
        String descTarea = txtNombreTarea.getText().toString();
        String descFecha = txtFecha.getText().toString();
        Date r = new Date(descFecha);
        listPresenter.addTarea(descTarea,descFecha);

    }

    //lo iniciamos pasandole la intencion, con todos sus parametros guardados
    // startActivity(intent);
    //List<Tarea> lsTarea
    @Override
    public void refrescarListaTareas(List<Tarea> lsTarea) {
        rvListTODO.getAdapter().notifyDataSetChanged();

        rvListTODO.scrollToPosition(
                rvListTODO.getAdapter().getItemCount() - 1);

        txtNombreTarea.setText("");
    }

//Tarea tarea,
    @Override
    public void refrescarTarea(Tarea tarea, int posicion) {
        rvListTODO.getAdapter().notifyItemChanged(posicion);
    }

    @Override
    public void itemCambioEstado(int posicion, boolean realizada) {
        listPresenter.itemCambioEstado(posicion, realizada);
    }


}

