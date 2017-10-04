package com.example.marlon.app_dhl;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.marlon.app_dhl.printerzebra.DemoSleeper;
import com.example.marlon.app_dhl.printerzebra.Print;
import com.example.marlon.app_dhl.printerzebra.event.EventBus;
import com.example.marlon.app_dhl.printerzebra.event.GreenRobotEventBus;
import com.example.marlon.app_dhl.printerzebra.event.PrintEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Marlon on 26/7/2017.
 */

public class Activity_printer extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView msjprinter;
    private EditText editmac;
    private Button btnconect, btnlimpiarmac;
    private ManagerPrint managerPrint;
    private String macaddress;

    private EventBus eventBus;
    private Print print;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);

        toolbar = (Toolbar) findViewById(R.id.Toolbarprinter);
        msjprinter = (TextView) findViewById(R.id.notificacionprint);
        editmac = (EditText) findViewById(R.id.editmac);
        btnconect = (Button) findViewById(R.id.botonconectar);
        btnlimpiarmac = (Button) findViewById(R.id.botonlimpiarmac);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.titulotoolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        btnlimpiarmac.setVisibility(View.GONE);

        managerPrint = new ManagerPrint(getApplicationContext());

        if (managerPrint.isconnected()) {
            setStatus("Mac Address: " + managerPrint.obtenermacddress(), Color.WHITE);
            btnlimpiarmac.setVisibility(View.VISIBLE);
            //editmac.setText(managerPrint.obtenermacddress());
        } else {
            setStatus("Insert Mac Address", Color.WHITE);
        }

        eventBus = new GreenRobotEventBus(new org.greenrobot.eventbus.EventBus().getDefault());
        eventBus.register(this);
        print = new Print(getApplicationContext(), new GreenRobotEventBus(new org.greenrobot.eventbus.EventBus().getDefault()));
        print.setFacturaNPL(null); // factura de prueba

        btnconect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editmac.getText().toString().toString().isEmpty()) {
                    macaddress = editmac.getText().toString();
                    print.setDirMac(macaddress);
                    print.imprimir();
                } else {
                    editmac.requestFocus();
                }
            }
        });

        btnlimpiarmac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (managerPrint.isconnected()) {
                    borrarmac();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarl_menuprint, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_back:
                Intent home = new Intent(Activity_printer.this, Activity_home.class);
                startActivity(home);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(PrintEvent event){
        switch (event.getEventType()){
            case PrintEvent.enableTestButton:
                enableTestButton(event.isEnable());
                break;
            case PrintEvent.setStatus:
                if(event.getStatusMessage().equals("Disconnecting..."))
                    break;
                if(event.getStatusMessage().equals("It is not connected..."))
                    break;

                setStatus(event.getStatusMessage(), event.getColor());

                if(event.getStatusMessage().equals("Connected")){
                    managerPrint.guardarimpresora(macaddress, true);
                }
                break;

        }
    }


    private void enableTestButton(final boolean enabled) {
        runOnUiThread(new Runnable() {
            public void run() {
                btnconect.setEnabled(enabled);
            }
        });
    }

    private void setStatus(final String statusMessage, final int color) {
        runOnUiThread(new Runnable() {
            public void run() {
                msjprinter.setTextColor(color);
                msjprinter.setText(statusMessage);
            }
        });
        DemoSleeper.sleep(1000);
    }


    public void borrarmac(){
        setStatus("Mac address clear ", Color.WHITE);
        managerPrint.logoutprint();
        btnlimpiarmac.setVisibility(View.GONE);
        editmac.setText("");

        /**
        try {
            setStatus("Mac address clear",Color.WHITE);
            managerPrint.logoutprint();
            btnlimpiarmac.setVisibility(View.GONE);
            if (connection != null) {
                connection.close();
                setStatus("Disconnected",Color.GREEN);
            }else {
                setStatus("Printer was disconnected",Color.WHITE);
            }
        }catch (ConnectionException e){
            setStatus("COMM Error! Disconnected", Color.RED);
        }*/

    }


}
