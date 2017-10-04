package com.example.marlon.app_dhl;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marlon.app_dhl.printerzebra.Print;
import com.example.marlon.app_dhl.printerzebra.event.EventBus;
import com.example.marlon.app_dhl.printerzebra.event.GreenRobotEventBus;
import com.example.marlon.app_dhl.printerzebra.event.PrintEvent;
import com.example.marlon.app_dhl.retrofit.api.Apiadapter;
import com.example.marlon.app_dhl.retrofit.model.Codigo;
import com.example.marlon.app_dhl.retrofit.model.EBCD;
import com.example.marlon.app_dhl.retrofit.model.FetchInvoiceData;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Marlon on 14/7/2017.
 *
 final CustomSpinner spinner= (CustomSpinner) dialoglayoutprint.findViewById(R.id.spinnerprint);
 String[] types = getResources().getStringArray(R.array.spinner);
 spinner.initializeStringValues(types, getString(R.string.spinner_hint));
 ArrayAdapter<CharSequence> adapter;
 adapter = ArrayAdapter.createFromResource(this, R.array.spinner, R.layout.view_textspinner);
 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 spinner.setSelection(0);
 spinner.setAdapter(adapter);

 */

public class Activity_home extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbarhome;
    private LinearLayout layoutcontenido,layoutmsjerrorhome;
    private Button btnverify,btnprint;
    private TextView textcontenido,mensaje,mensajeprint;
    private EditText editverify;
    private ManagerLogin managerLogin;
    private ManagerPrint managerPrint;
    private String codigobarra;
    private int opcionimprimir;

    private static final int REQUEST_ENABLE_BT=1145;
    private static final int REQUEST_ENABLE_WF=1145;
    private static final String TAG=Activity_home.class.getName();

    private List<FetchInvoiceData> invoiceDataList;
    private EBCD codigoEBCD;
    private EventBus eventBus;

    private boolean printEBCD;
    private boolean printInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        managerLogin= new ManagerLogin(getApplicationContext());
        managerPrint= new ManagerPrint(getApplicationContext());

        toolbarhome=(Toolbar)findViewById(R.id.Toolbarhome);
        setSupportActionBar(toolbarhome);
        getSupportActionBar().setTitle(R.string.app_name);

        toolbarhome.setTitleTextColor(Color.WHITE);
        toolbarhome.setSubtitleTextColor(Color.WHITE);

        layoutcontenido=(LinearLayout) findViewById(R.id.Layoutcontenido);
        layoutcontenido.setVisibility(View.GONE);

        layoutmsjerrorhome=(LinearLayout) findViewById(R.id.msjerrorhome);
        layoutmsjerrorhome.setVisibility(View.INVISIBLE);
        mensaje=(TextView) findViewById(R.id.Textmsjenotificacionhome);

        btnverify = (Button) findViewById(R.id.botonverify);
        btnverify.setOnClickListener(this);
        btnprint =(Button) findViewById(R.id.botonprint);
        btnprint.setOnClickListener(this);
        editverify = (EditText) findViewById(R.id.editverify);
        editverify.requestFocus();
        textcontenido=(TextView) findViewById(R.id.textocontenido);

        opcionimprimir=0; // valor por defecto
        eventBus= new GreenRobotEventBus(new org.greenrobot.eventbus.EventBus().getDefault());
        eventBus.register(this);
        printEBCD= false;
        printInvoice= false;

        comprobrarEstadoRed();
    }

    /// Menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.nav_logout:
                //Log.e(TAG,Facturas.getFacturaDutyInvoice(codigoEBCD, invoiceDataList));
                //Log.e(TAG,Facturas.getFacturaeBCD(codigoEBCD));
                alertdialog(1);
                return true;
            case R.id.nav_about:
                alertdialog(2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(PrintEvent event){
        switch (event.getEventType()){
            case PrintEvent.enableTestButton:
                //enableTestButton(event.isEnable());
                break;
            case PrintEvent.setStatus:
                //setStatus(event.getStatusMessage(), event.getColor());
                break;

        }
    }
    ////  botones
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botonverify:
                layoutcontenido.setVisibility(View.INVISIBLE);
                //OCULTA TECLADO
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                //
                if(confirmarinternet())
                    webservice();
                else
                    //showAlertConectadoWifi();
                    mostrarmensaje(R.string.nointerent);

                break;

            case R.id.botonprint:
                if(printEBCD && printInvoice){
                    if(comprobarEstadoBluetooth())
                        alertdialogprint();
                    else
                        lanzarIntentActivarBluetooth();
                }
                break;
        }

    }

    ///// Webservice
    public void webservice(){
        codigobarra= editverify.getText().toString();

        Codigo cod = new Codigo();
        cod.setAwbNo(codigobarra);
        cod.setSessionToken(managerLogin.obtnertoken());

        Call <EBCD> call = Apiadapter.getApiService().getEBCD(cod);
        call.enqueue(new Callback<EBCD>() {
            @Override
            public void onResponse(Call<EBCD> call, Response<EBCD> response) {
                if (response.code() == 200 && response.body() != null){
                    if(response.body().getResultCd().equals("0")){
                        layoutcontenido.setVisibility(View.VISIBLE);
                        codigoEBCD=response.body();
                        textcontenido.setText(response.body().contenido());
                        printEBCD=true;

                    }else if(response.body().getResultCd().equals("111")){
                        mostrarmensajewebservice(response.body().getResultMsg());
                        managerLogin.logout();
                        esperarcerrar();
                    }else {
                        mostrarmensajewebservice(response.body().getResultMsg());
                        if (layoutcontenido.getVisibility()== View.VISIBLE){
                            layoutcontenido.setVisibility(View.INVISIBLE);
                        }
                    }

                }else {
                    mostrarmensaje(R.string.errorwebservice);
                }

            }

            @Override
            public void onFailure(Call<EBCD> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"ERROR. Webservice",Toast.LENGTH_SHORT).show();
            }
        });

        Apiadapter.getApiService().fetchInvoiceData(cod).enqueue(new Callback<List<FetchInvoiceData>>() {
            @Override
            public void onResponse(Call<List<FetchInvoiceData>> call, Response<List<FetchInvoiceData>> response) {
                if (response.code() == 200 && response.body() != null){
                    invoiceDataList= new ArrayList<>();
                    invoiceDataList.addAll(response.body());

                    if(invoiceDataList.get(0).getResultCd().equals("0")){
                        printInvoice=true;
                    }else
                    if(invoiceDataList.get(0).getResultCd().equals("111")){
                        mostrarmensajewebservice(invoiceDataList.get(0).getResultMsg());
                        managerLogin.logout();
                        esperarcerrar();
                    }else {
                        mostrarmensajewebservice(invoiceDataList.get(0).getResultMsg());
                    }

                }else {
                    mostrarmensaje(R.string.errorwebservice);
                }
            }

            @Override
            public void onFailure(Call<List<FetchInvoiceData>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"ERROR. Webservice",Toast.LENGTH_SHORT).show();
            }
        });

    }


///// ALERT DIALOGO
    public void alertdialog(int ban){
        LayoutInflater inflater = getLayoutInflater();
        final View dialoglayout = inflater.inflate(R.layout.view_alertdialog, null);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_home.this);
        dialog.setView(dialoglayout);
        final AlertDialog d = dialog.show();

        final TextView textcontenido = (TextView) dialoglayout.findViewById(R.id.textalertdialog);
        final Button btnyes= (Button)dialoglayout.findViewById(R.id.botonsi);
        final Button btnno= (Button) dialoglayout.findViewById(R.id.botonno);

        if(ban==1){
            textcontenido.setText(R.string.salir);
            btnyes.setText(R.string.yes);
            btnno.setText(R.string.no);
            btnyes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    managerLogin.logout();
                    Intent login = new Intent(Activity_home.this,Activity_login.class);
                    startActivity(login);
                    finish();
                }
            });

            btnno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });
        }

        if(ban==2){

            textcontenido.setText("User:"+managerLogin.obtenerusername()+"\n"+ getResources().getString(R.string.version_app));
            btnyes.setText(R.string.ok);
            btnyes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });
            btnno.setVisibility(View.GONE);
        }

    }

    public void alertdialogprint(){
        LayoutInflater inflater = getLayoutInflater();
        final View dialoglayoutprint = inflater.inflate(R.layout.view_alerdialogprint, null);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_home.this);
        dialog.setView(dialoglayoutprint);
        final AlertDialog d = dialog.show();


        final Button printeBCD= (Button) dialoglayoutprint.findViewById(R.id.printeBCD);
        final Button printInvoice= (Button) dialoglayoutprint.findViewById(R.id.printInvoice);
        final Button printBoth= (Button) dialoglayoutprint.findViewById(R.id.printBoth);


        final Button btncancelar= (Button) dialoglayoutprint.findViewById(R.id.botoncancelar);
        final Button btncambiarprinter = (Button) dialoglayoutprint.findViewById(R.id.botoncambiarimpresora);

        mensajeprint = (TextView) dialoglayoutprint.findViewById(R.id.Textmsjdialogprinter);

        if (managerPrint.isconnected()){
            mensajeprint.setText("Mac address: "+managerPrint.obtenermacddress());

            printeBCD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    opcionimprimir=1;
                    imprimiFactura(opcionimprimir);
                    d.dismiss();
                }
            });

            printInvoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    opcionimprimir=2;
                    imprimiFactura(opcionimprimir);
                    d.dismiss();
                }
            });

            printBoth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    opcionimprimir=3;
                    imprimiFactura(opcionimprimir);
                    d.dismiss();
                }
            });

            btncancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });

            btncambiarprinter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                    Intent printer= new Intent(Activity_home.this, Activity_printer.class);
                    startActivity(printer);
                    finish();
                }
            });

        }else {
            mensajeprint.setText(R.string.missingmacaddress);

            printeBCD.setVisibility(View.GONE);
            printBoth.setVisibility(View.GONE);
            printInvoice.setVisibility(View.GONE);

            btncancelar.setVisibility(View.GONE);
            btncambiarprinter.setText(R.string.conectarimpresora);
            btncambiarprinter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent printer= new Intent(Activity_home.this, Activity_printer.class);
                    startActivity(printer);
                    finish();
                }
            });
        }

    }

    private void imprimiFactura(int opcionimprimir) {
        Print print = new Print(getApplicationContext(), new GreenRobotEventBus(new org.greenrobot.eventbus.EventBus().getDefault()));
        print.setDirMac(managerPrint.obtenermacddress());

        switch (opcionimprimir){
            case 1:
                print.setFacturaNPL(Facturas.getFacturaeBCD(codigoEBCD));
                print.imprimir();
                break;
            case 2:
                print.setFacturaNPL(Facturas.getFacturaDutyInvoice(codigoEBCD, invoiceDataList));
                print.imprimir();
                break;
            case 3:
                print.setFacturaNPL(Facturas.getBoth(codigoEBCD, invoiceDataList));
                print.imprimir();
                break;
        }
    }

    //////Mostrar mensajewebservices
    public void mostrarmensajewebservice(String msj){
        layoutmsjerrorhome.setVisibility(View.VISIBLE);
        mensaje.setText(msj);
        esperar();
    }
////////Mostrar mensaje
    public void mostrarmensaje(int msj){
        layoutmsjerrorhome.setVisibility(View.VISIBLE);
        mensaje.setText(msj);
        esperar();
    }
//////// Esperar y actuar
    public void esperar() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                    layoutmsjerrorhome.setVisibility(View.INVISIBLE);
            }}, 2000);
    }
////// Esperar y cerrar
    public void esperarcerrar() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent login = new Intent(Activity_home.this,Activity_login.class);
                startActivity(login);
                finish();
            }}, 2000);
    }


    private boolean comprobrarEstadoRed(){
        if (!conectadoWifi()){
            return false;
        }else
            return true;

    }

    private void showAlertConectadoWifi() {
        final View dialoglayout = getLayoutInflater().inflate(R.layout.view_alertdialog, null);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_home.this);
        dialog.setView(dialoglayout);
        final AlertDialog d = dialog.show();

        final TextView textcontenido = (TextView) dialoglayout.findViewById(R.id.textalertdialog);
        final Button btnyes= (Button)dialoglayout.findViewById(R.id.botonsi);
        final Button btnno= (Button) dialoglayout.findViewById(R.id.botonno);



        textcontenido.setText(R.string.conectarWifi);
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS), REQUEST_ENABLE_WF);
                d.dismiss();
            }
        });

        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

    }

    protected Boolean conectadoWifi(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean comprobarEstadoBluetooth(){
        BluetoothAdapter bAdapter= BluetoothAdapter.getDefaultAdapter();
        if(bAdapter!=null) {
            if (!bAdapter.isEnabled())
                return false;
            else
                return true;
        }else
            return false;
    }

    private void lanzarIntentActivarBluetooth(){
        //startActivityForResult(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS), REQUEST_ENABLE_BT);
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK) {
                }
                break;
        }
    }
    ////// Comprobar coneccion a internet
    private boolean confirmarinternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}
