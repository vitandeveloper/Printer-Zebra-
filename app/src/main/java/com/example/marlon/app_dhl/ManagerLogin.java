package com.example.marlon.app_dhl;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Marlon on 26/7/2017.
 */

public class ManagerLogin {

    private SharedPreferences contenedor;

    public ManagerLogin(Context context){
        contenedor = context.getSharedPreferences("prefencia", Context.MODE_PRIVATE);
    }

    public void guardarpreference(String nombre, String token, Boolean login){
        SharedPreferences.Editor editor = contenedor.edit();
        editor.putString("nombre_usuario", nombre);
        editor.putString("token_sesion", token);
        editor.putBoolean("login_usuario", login);
        editor.commit();

    }

    public boolean islogin(){
        return contenedor.getBoolean("login_usuario",false);
    }

    public void logout(){
        SharedPreferences.Editor editor = contenedor.edit();
        editor.clear();
        editor.commit();
    }

    public String obtenerusername (){
        return  contenedor.getString("nombre_usuario","");
    }

    public String obtnertoken(){
        return contenedor.getString("token_sesion","");
    }
}
