package com.example.marlon.app_dhl;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Marlon on 26/7/2017.
 */

public class ManagerPrint {
    private SharedPreferences contenedor;

    public ManagerPrint(Context context){
        contenedor = context.getSharedPreferences("prefenciaprint", Context.MODE_PRIVATE);
    }

    public void guardarimpresora(String mac,Boolean guardado){
        SharedPreferences.Editor editor = contenedor.edit();
        editor.putString("mac_address", mac);
        editor.putBoolean("mac_guardado", guardado);
        editor.commit();
    }

    public boolean isconnected(){
        return contenedor.getBoolean("mac_guardado",false);
    }

    public void logoutprint(){
        SharedPreferences.Editor editor = contenedor.edit();
        editor.clear();
        editor.commit();
    }

    public String obtenermacddress(){
        return contenedor.getString("mac_address","");
    }
}
