package com.example.jailer.tallerjailer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistroActivity extends AppCompatActivity {

/*
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;*/

    //EditText txtNombres;

    //EditText txtEmail;

   // EditText txtPassword;
    private EditText Email, Password, Name ;
    private Button Register;
    private String NameHolder, EmailHolder, PasswordHolder;
    private Boolean EditTextEmptyHolder;
    private SQLiteDatabase sqLiteDatabaseObj;
    private String SQLiteDataBaseQueryHolder ;
    private SQLiteHelper sqLiteHelper;
    private Cursor cursor;
    private String F_Result = "Not_Found";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ButterKnife.bind(this);


/*
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("usuarios");
        */
        Register = (Button)findViewById(R.id.btnRegistrarUsu);

        Name = (EditText)findViewById(R.id.txtNombreUsu);
        Email = (EditText)findViewById(R.id.txtEmailUsu);
        Password = (EditText)findViewById(R.id.txtPasswordUsu);


        sqLiteHelper = new SQLiteHelper(this);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDataBaseBuild();

                SQLiteTableBuild();

                CheckEditTextStatus();

                CheckingEmailAlreadyExistsOrNot();

                EmptyEditTextAfterDataInsert();


            }
        });

    }

    public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild() {

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE_NAME
                + "(" + SQLiteHelper.Table_Column_ID + " PRIMARY KEY , "
                + SQLiteHelper.Table_Column_1_Name + " VARCHAR, "
                + SQLiteHelper.Table_Column_2_Email + " VARCHAR, "
                + SQLiteHelper.Table_Column_3_Password + " VARCHAR);");

    }

    public void InsertDataIntoSQLiteDatabase(){

        if(EditTextEmptyHolder == true)
        {

            SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (nombre,email,password)" +
                    " VALUES('"+NameHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"');";

            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            sqLiteDatabaseObj.close();

            Toast.makeText(RegistroActivity.this,"Usuario Registrado Satisfactoriamente", Toast.LENGTH_LONG).show();

        }
        else {

            Toast.makeText(RegistroActivity.this,"Todos los Campos Son Obligatorios.", Toast.LENGTH_LONG).show();

        }

    }

    public void EmptyEditTextAfterDataInsert(){

        Name.getText().clear();

        Email.getText().clear();

        Password.getText().clear();

    }

    public void CheckEditTextStatus(){

        NameHolder = Name.getText().toString() ;
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }

    public void CheckingEmailAlreadyExistsOrNot(){

        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " "
                + SQLiteHelper.Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);

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

            Toast.makeText(RegistroActivity.this,"Este Email Ya Esta Registrado",Toast.LENGTH_LONG).show();

        }
        else {

            InsertDataIntoSQLiteDatabase();

        }

        F_Result = "Not_Found" ;

    }





/*
    @OnClick(R.id.btnRegistrar)
    public void clickRegistrar() {

        String nombres = txtNombres.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            final String uid = task.getResult().getUser().getUid();

                            task.getResult().getUser().getToken(true)
                                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<GetTokenResult> task) {

                                            if (task.isSuccessful()) {

                                                final String nomUsuario = txtNombres.getText().toString();

                                                Usuarios objUsuario = new Usuarios();
                                                objUsuario.setNombre(nomUsuario);
                                                objUsuario.setToken(task.getResult().getToken());
                                                 // /Tarea objTarea= new Tarea();
                                                //objTarea.
                                                reference.child(uid).setValue(objUsuario)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {

                                                                    Intent intent = new Intent(RegistroActivity.this,
                                                                            ListActivity.class);

                                                                    intent.putExtra("nomUsuario", nomUsuario);

                                                                    startActivity(intent);

                                                                    finish();
                                                                } else {
                                                                    Toast.makeText(RegistroActivity.this,
                                                                            "Error " + task.getException().getMessage(),
                                                                            Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });

                                            } else {

                                                Toast.makeText(RegistroActivity.this,
                                                        "Error " + task.getException().getMessage(),
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                        } else {

                            Toast.makeText(RegistroActivity.this,
                                    "Error " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    */

    @OnClick(R.id.tienesUsuario)
    public void clickTienesUsuario() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

}

