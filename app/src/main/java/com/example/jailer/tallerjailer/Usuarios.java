package com.example.jailer.tallerjailer;

import com.example.jailer.modelo.Tarea;
/**
 * Created by Jailer on 26/09/2017.
 */

public class Usuarios extends Tarea {


    private String nombre;
    private String email;
    private String password;

    private String token;
    private Tarea tarea;

    public Usuarios() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
