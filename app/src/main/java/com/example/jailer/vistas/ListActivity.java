package com.example.jailer.vistas;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jailer.modelo.Tarea;
import com.example.jailer.tallerjailer.R;
import com.example.jailer.tallerjailer.SQLiteHelper;
import com.example.jailer.vistas.adaptadores.TodoListAdapter;
import com.example.jailer.vistas.presenters.IListPresenter;
import com.example.jailer.vistas.presenters.ListPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.jailer.tallerjailer.R.id.txtNombreTarea;


public class ListActivity extends AppCompatActivity implements
        IListView, TodoListAdapter.OnListenerItemCheck {
    private int dia, mes, ano;
    private IListPresenter listPresenter;
    EditText tarea;
    EditText fecha;

    SQLiteHelper sqLiteHelper;
    //Tarea objTarea;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @BindView(R.id.rvListTODO)
    RecyclerView rvListTODO;
    String descTarea, descFecha;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj1;
    String SQLiteDataBaseQueryHolder1;
    Cursor cursor;
    String F_Result = "Not_Found";
    // @BindView(R.id.tvTarea)
    //  TextView tvTarea;
    // @BindView(R.id.chkTarea)
    AppCompatCheckBox chkTarea;

    //   @BindView(R.id.tvFecha)
    // TextView tvFecha;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ButterKnife.bind(this);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        listPresenter = new ListPresenter(this);

        sqLiteHelper = new SQLiteHelper(this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        chkTarea = (AppCompatCheckBox) findViewById(R.id.chkTarea);
        rvListTODO.setLayoutManager(llm);

        List<Tarea> lsTarea = listPresenter.obtenerTareas();

        rvListTODO.setAdapter(new TodoListAdapter(lsTarea, this));

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //goLoginScreen();
                }
            }
        };

    }
    public void a() {
        Intent intent = new Intent(ListActivity.this,
                ListActivity.class);
        finish();
        startActivity(intent);
    }
/*
        public void onClick(View view) {
            boolean isChecked = ((CheckBox)view).isChecked();

            if (isChecked) {
                chkTarea.setChecked(true);
                Intent intent = new Intent(ListActivity.this,
                        ListActivity.class);
                startActivity(intent);

            }
            else {
                chkTarea.setChecked(false);
                Intent intent = new Intent(ListActivity.this,
                        ListActivity.class);
                startActivity(intent);

            }
        }*/

    View.OnClickListener list = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ListActivity.this,
                    ListActivity.class);
            boolean estado=false;
            if (estado == false) {
                startActivity(intent);

            } else if (estado == true) {

                startActivity(intent);

            }
        }
    };



    @OnClick(R.id.btnEnviarTarea)
    @Override
    public void clickAddTarea() {
        //   String descTarea = txtTarea.getText().toString();
        // listPresenter.addTarea(descTarea);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ListActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_fecha, null);
        tarea = (EditText) mView.findViewById(txtNombreTarea);
         fecha = (EditText) mView.findViewById(R.id.txtFecha);
        Button mLogin = (Button) mView.findViewById(R.id.btnGuardar);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
 fecha.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         fecha.setInputType(InputType.TYPE_NULL);

         if (v == fecha) {
             fecha.setInputType(InputType.TYPE_NULL);
             final Calendar c = Calendar.getInstance();
             dia = c.get(Calendar.DAY_OF_MONTH);
             mes = c.get(Calendar.MONTH);
             ano = c.get(Calendar.YEAR);
             fecha.setInputType(InputType.TYPE_NULL);
             aVoid();

         }
     }
 });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // final String descTarea = tarea.getText().toString();
                //final String descFecha= fecha.getText().toString();
              //  Date r= new Date(descFecha);
                dialog.hide();


             //    boolean checke=chkTarea.isChecked();
                SQLiteDataBaseBuild();

                SQLiteTableBuild();

                CheckEditTextStatus();

                CheckingEmailAlreadyExistsOrNot();

                EmptyEditTextAfterDataInsert();

                /*
              reference.child("tarea").setValue(objTarea)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                              if (task.isSuccessful()) {

                                    Intent intent = new Intent(ListActivity.this,
                                          ListActivity.class);

                                    intent.putExtra("desTarea", descTarea);
                                   intent.putExtra("desFecha", descFecha);


                                   startActivity(intent);

                                    finish();
                                } else {
                                    Toast.makeText(ListActivity.this,
                                            "Error " + task.getException().getMessage(),

                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            */

        }
        });
    }



    public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj1 = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild() {
        sqLiteDatabaseObj1.execSQL("CREATE TABLE IF NOT EXISTS "+ SQLiteHelper.TABLE2_NAME
                + "(" + SQLiteHelper.COLUMN_ID + " VARCHAR, "
                + SQLiteHelper.COLUMN_NAME + " VARCHAR, "
                + SQLiteHelper.COLUMN_FECHA + " VARCHAR, "
                + SQLiteHelper.COLUMN_REALIZADA + " VARCHAR);");


    }

    public void InsertDataIntoSQLiteDatabase(){

        if(EditTextEmptyHolder == true)
        {

             descTarea = tarea.getText().toString();
            descFecha= fecha.getText().toString();
            String i="hola";
            Tarea objTarea = new Tarea();
          //  objTarea.setNombre(descTarea);
            //objTarea.setFecha(descFecha);
           //listPresenter.addTarea(descTarea,descFecha);


            finish();
            SQLiteDataBaseQueryHolder1 = "INSERT INTO "+SQLiteHelper.TABLE2_NAME+" (nombre,fecha,realizada)" +
                    " VALUES('"+descTarea+"', '"+descFecha+"', '"+i+"');";

            sqLiteDatabaseObj1.execSQL(SQLiteDataBaseQueryHolder1);

            SQLiteDatabase db=sqLiteHelper.getReadableDatabase();
            Cursor cursor=db.rawQuery("SELECT * FROM "+ sqLiteHelper.TABLE2_NAME,null);
          while (cursor.moveToNext()){

                objTarea.setNombre(cursor.getString(1));
                objTarea.setFecha(cursor.getString(2));
             }
            listPresenter.addTarea(objTarea.getNombre(),objTarea.getFecha());

            Intent intent = new Intent(ListActivity.this,
                    ListActivity.class);

            intent.putExtra("desTarea", descTarea);
            intent.putExtra("desFecha", descFecha);


            startActivity(intent);
            sqLiteDatabaseObj1.close();

            Toast.makeText(ListActivity.this,"Tarea Agregada Satisfactoriamente", Toast.LENGTH_LONG).show();

        }
        else {

            Toast.makeText(ListActivity.this,"Todos Los Campos Son Obligatorios.", Toast.LENGTH_LONG).show();

        }

    }

    public void EmptyEditTextAfterDataInsert(){

        tarea.getText().clear();

        fecha.getText().clear();


    }

    public void CheckEditTextStatus(){

     descTarea = tarea.getText().toString() ;
        descFecha = fecha.getText().toString();


        if(TextUtils.isEmpty(descTarea) || TextUtils.isEmpty(descFecha)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }

    public void CheckingEmailAlreadyExistsOrNot(){

        sqLiteDatabaseObj1 = sqLiteHelper.getWritableDatabase();

        cursor = sqLiteDatabaseObj1.query(SQLiteHelper.TABLE2_NAME, null, " "
                + SQLiteHelper.COLUMN_NAME + "=?", new String[]{descTarea}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                F_Result = "Email Found";

                cursor.close();
            }
        }

        CheckFinalResult();

    }


    public void CheckFinalResult(){

        if(F_Result.equalsIgnoreCase("Email Found"))
        {

            Toast.makeText(ListActivity.this,"Ya Existe Una Tarea Con Ese Nombre",Toast.LENGTH_LONG).show();

        }
        else {

            InsertDataIntoSQLiteDatabase();

        }

        F_Result = "Not_Found" ;

    }





    public  void aVoid(){
        fecha.setInputType(InputType.TYPE_NULL);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fecha.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }
                , dia, mes, ano);
        fecha.setInputType(InputType.TYPE_NULL);
        datePickerDialog.show();
    }

//List<Tarea> lsTarea
    @Override
    public void refrescarListaTareas(List<Tarea> lsTarea) {
        rvListTODO.getAdapter().notifyDataSetChanged();

        rvListTODO.scrollToPosition(
                rvListTODO.getAdapter().getItemCount() - 1);

        fecha.setText("");
    }

//Tarea tarea,
    @Override
    public void refrescarTarea(Tarea tarea,int posicion) {
        rvListTODO.getAdapter().notifyItemChanged(posicion);
    }

    @Override
    public void itemCambioEstado(int posicion, boolean realizada) {
        listPresenter.itemCambioEstado(posicion, realizada);
        a();
    }
    /*
    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    */
/*
    @OnClick(R.id.btnCerrarSesion)
    public void clickCerrarSesion() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goLoginScreen();
        //chkTarea.setText("");
        //tvFecha.setText("");
        //tvTarea.setText("");

    }
*/

}
