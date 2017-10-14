package com.example.jailer.tallerjailer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jailer.vistas.ListActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPassword;
    private  Button btnIniciar;

    private LoginButton loginButton;

    private CallbackManager callbackManager;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;

    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private ProgressBar progressBar;
    private Usuarios user;

    private String EmailHolder, PasswordHolder;
    private Boolean EditTextEmptyHolder;
    private SQLiteDatabase sqLiteDatabaseObj;
    private SQLiteHelper sqLiteHelper;
    private Cursor cursor;
    private String TempPassword = "NOT_FOUND" ;
    public static final String UserEmail = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         ButterKnife.bind(this);


        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("usuarios");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.loginButton);

        loginButton.setReadPermissions(Arrays.asList("email"));


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    goLoginScreen();
                }
            }
        };

        btnIniciar = (Button)findViewById(R.id.btnIngresar);

        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPassword = (EditText)findViewById(R.id.txtPassword);


        sqLiteHelper = new SQLiteHelper(this);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextStatus();

                LoginFunction();


            }
        });

    }

    public void LoginFunction(){

        if(EditTextEmptyHolder) {

            sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

            cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_2_Email
                    + "=?", new String[]{EmailHolder}, null, null, null);

            while (cursor.moveToNext()) {

                if (cursor.isFirst()) {

                    cursor.moveToFirst();

                    TempPassword = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_Password));

                    cursor.close();
                }
            }

            CheckFinalResult();

        }
        else {

            Toast.makeText(LoginActivity.this,"Por Faver Ingrese Su Email o Contraceña.",Toast.LENGTH_LONG).show();

        }

    }

    public void CheckEditTextStatus(){

        EmailHolder = txtEmail.getText().toString();
        PasswordHolder = txtPassword.getText().toString();

        if( TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }

    public void CheckFinalResult(){

        if(TempPassword.equalsIgnoreCase(PasswordHolder))
        {

            Toast.makeText(LoginActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(LoginActivity.this, ListActivity.class);

            intent.putExtra(UserEmail, EmailHolder);

            startActivity(intent);


        }
        else {

            Toast.makeText(LoginActivity.this,"Usuario o Contraceña Son Incorrectas.",Toast.LENGTH_LONG).show();

        }

        TempPassword = "NOT_FOUND" ;
    }




    private void handleFacebookAccessToken(AccessToken accessToken) {
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), R.string.firebase_error_login, Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
                loginButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, ListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListener);

    }

/*
    @OnClick(R.id.btnIngresar)
    public void ClickInbresar(View v) {
        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Cargando...", "Por Favor Espera...", true);

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        (firebaseAuth.signInWithEmailAndPassword(email,password))
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        progressDialog.dismiss();
                        String uid = authResult.getUser().getUid();

                        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Intent intent = new Intent(LoginActivity.this,
                                     ListActivity.class);

                                intent.putExtra("nomUsuario",
                                        dataSnapshot.getValue(Usuarios.class).getNombre());

                                startActivity(intent);

                                finish();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,
                        "Error " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
*/


    @OnClick(R.id.btnRegistrar)
    public void clickRegistrar() {
        startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
    }

}
