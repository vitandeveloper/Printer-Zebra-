package com.example.marlon.app_dhl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marlon.app_dhl.retrofit.api.Apiadapter;
import com.example.marlon.app_dhl.retrofit.model.Usuario;
import com.example.marlon.app_dhl.retrofit.model.UsuarioIngreso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_login extends AppCompatActivity {

    private EditText editusername,editpassword;
    private Button btnlogin;
    private LinearLayout mensajeerror;
    private TextView mensaje;
    private String user, password;
    private ManagerLogin managerLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        managerLogin = new ManagerLogin(getApplicationContext());
        if (managerLogin.islogin()){
            Intent home = new Intent(Activity_login.this, Activity_home.class);
            startActivity(home);
            finish();
        }

        editusername = (EditText)findViewById(R.id.campousuario);
        editpassword = (EditText) findViewById(R.id.campopassword);

        mensajeerror =(LinearLayout) findViewById(R.id.mensajederror);
        mensajeerror.setVisibility(View.INVISIBLE);
        mensaje=(TextView)findViewById(R.id.Textmsjenotificacion);

        btnlogin = (Button) findViewById(R.id.btningresar);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarcampos()){
                    webserviceusuario();
                }

            }
        });
    }

    ///// Webservice usuario
    public void webserviceusuario(){

        Usuario usu = new Usuario();
        usu.setUserName(user);
        usu.setPassword(password);
        usu.setDevieLatLong("");

        Call<UsuarioIngreso> call = Apiadapter.getApiService().postusuaruio(usu);
        call.enqueue(new Callback<UsuarioIngreso>() {
            @Override
            public void onResponse(Call<UsuarioIngreso> call, Response<UsuarioIngreso> response) {
                if (response.code() == 200 && response.body() != null) {

                    if(response.body().getResultCd().equals("0")){
                        String nombre= response.body().getUsername();
                        String token=response.body().getSessionToken();
                       /// guardarpreferenciaidrepa(nombre,token,true);
                        managerLogin.guardarpreference(nombre,token,true);
                        Intent home = new Intent(Activity_login.this, Activity_home.class);
                        startActivity(home);
                        finish();

                    }else {
                        String mensajewebs=response.body().getResultMsg();
                        mostrarmensajewebservice(mensajewebs);

                    }

                }else {
                    mostrarmensaje(R.string.errorwebservice);
                }
            }
            @Override
            public void onFailure(Call<UsuarioIngreso> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"ERROR. Webservice",Toast.LENGTH_SHORT).show();

            }
        });
    }
    //////Validar Campos
    public boolean validarcampos(){
        boolean ban= false;
        user = editusername.getText().toString().trim();
        password= editpassword.getText().toString().trim();

        if(confirmarinternet()){
            if (TextUtils.isEmpty(user) || TextUtils.isEmpty(password)){
                //mostrarmensaje("Empty field");
                if (TextUtils.isEmpty(user)){
                    mostrarmensaje(R.string.userempy);
                    editusername.requestFocus();
                    editpassword.setText("");
                }else {
                    mostrarmensaje(R.string.passwordempy);
                    editpassword.requestFocus();
                }
            }else {
                ban=true;
            }
        }else {
            mostrarmensaje(R.string.nointerent);
        }
        return ban;
    }
    //// Mostrar mensaje
    public void mostrarmensaje(int msj){
        mensaje.setText(msj);
        mensajeerror.setVisibility(View.VISIBLE);
        esperar();
    }
    //// Mostrar mensajewebservices
    public void mostrarmensajewebservice(String msj){
        mensaje.setText(msj);
        mensajeerror.setVisibility(View.VISIBLE);
        esperar();
    }
    //// Esperar y actuar
    public void esperar() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                    mensajeerror.setVisibility(View.INVISIBLE);
            }
        }, 2000);
    }
    ////// Comprobar coneccion a internet
    private boolean confirmarinternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}
