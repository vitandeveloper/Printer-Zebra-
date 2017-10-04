package com.example.marlon.app_dhl.printerzebra;

import android.content.Context;
import android.graphics.Color;
import android.os.Looper;


import com.example.marlon.app_dhl.printerzebra.event.EventBus;
import com.example.marlon.app_dhl.printerzebra.event.PrintEvent;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.comm.TcpConnection;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.PrinterStatus;
import com.zebra.sdk.printer.SGD;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;
import com.zebra.sdk.printer.ZebraPrinterLinkOs;

public class Print {

    private Connection connection;
    private ZebraPrinter printer;
    private Context context;
    private EventBus eventBus;
    private String dirMac;
    private String facturaNPL;

    public Print(Context context, EventBus eventBus) {
        this.context = context;
        this.eventBus = eventBus;
    }

    public void setDirMac(String dirMac) {
        this.dirMac = dirMac;
    }

    public void setFacturaNPL(String facturaNPL) {
        this.facturaNPL = facturaNPL;
    }

    public void imprimir(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                enableTestButton(false);
                Looper.prepare();
                doConnectionTest();
                Looper.loop();
                Looper.myLooper().quit();
            }
        }).start();
    }


    public ZebraPrinter connect(){
        //setStatus("Connected....", Color.YELLOW); modificado por el cliente
        setStatus("Connecting", Color.YELLOW);
        connection = null;
        if(isBluetoothSelected()){
            connection = new BluetoothConnection(getMacAddressFieldText());
            //SettingsHelper.saveBluetoothAddress(context, getMacAddressFieldText());
        }else{
            try{
                int port = Integer.parseInt(getTcpPortNumber());
                connection = new TcpConnection(getTcpAddress(), port);
                //SettingsHelper.saveIp(context, getTcpAddress());
                //SettingsHelper.savePört(context, getTcpPortNumber());
            }catch (NumberFormatException e){
                setStatus("Invalid port", Color.RED);
                return null;
            }
        }
        try{
            connection.open();
            setStatus("Connected", Color.GREEN);
        }catch(ConnectionException e){
            setStatus("Could not connect", Color.RED);
            DemoSleeper.sleep(1000);
            disconnect();
        }

        ZebraPrinter printer = null ;

        if(connection.isConnected()){
            try{
                printer = ZebraPrinterFactory.getInstance(connection);
                //setStatus("Getting print language", Color.YELLOW);
                String pl = SGD.GET("devices.languages", connection);
                //setStatus("Language of impression " + pl, Color.BLUE);
            }catch(ConnectionException e){
                setStatus("The connection was not made", Color.RED);
                printer = null;
                DemoSleeper.sleep(1000);
                disconnect();
            }catch(ZebraPrinterLanguageUnknownException e){
                setStatus("Not recognized", Color.RED);
                printer = null;
                DemoSleeper.sleep(1000);
                disconnect();
            }

        }
        return printer;
    }

    public void disconnect(){
        try{
            setStatus("Disconnecting...",Color.RED);
            if(connection != null){
                connection.close();
            }
            setStatus("It is not connected...", Color.RED);
        }catch(ConnectionException e){
            setStatus("COMM Error!", Color.RED);
        }finally{
            enableTestButton(true);
        }
    }

    private void setStatus(final String statusMessage, final int color){
        PrintEvent event= new PrintEvent();
        event.setEventType(PrintEvent.setStatus);
        event.setStatusMessage(statusMessage);
        event.setColor(color);
        eventBus.post(event);

        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                statusField.setBackgroundColor(color);
                statusField.setText(statusMessage);
            }
        });
        DemoSleeper.sleep(1000);*/
    }

    private void sendTestLabel(){
        try{
            ZebraPrinterLinkOs linkOsPrinter = ZebraPrinterFactory.createLinkOsPrinter(printer);
            PrinterStatus printerStatus = (linkOsPrinter != null) ? linkOsPrinter.getCurrentStatus() : printer.getCurrentStatus();
            if(printerStatus.isReadyToPrint){
                byte [] configLabel = getConfigLabel();
                connection.write(configLabel);
                setStatus("Submitting test", Color.BLUE);
            }else if(printerStatus.isHeadOpen){
                setStatus("Printer open.", Color.RED);
            }else if(printerStatus.isPaused){
                setStatus("Printer paused", Color.RED);
            }else if(printerStatus.isPaperOut){
                setStatus("Insert paper and try again", Color.RED);
            }
            DemoSleeper.sleep(1500);
            if(connection instanceof BluetoothConnection){
                String friendlyName = ((BluetoothConnection) connection).getFriendlyName();
                setStatus(friendlyName, Color.MAGENTA);
                DemoSleeper.sleep(500);
            }
        }catch (ConnectionException e){
            setStatus(e.getMessage(), Color.RED);
        }finally{
            disconnect();
        }
    }
    private void enableTestButton(final boolean enabled){

        PrintEvent event= new PrintEvent();
        event.setEventType(PrintEvent.enableTestButton);
        event.setEnable(enabled);
        eventBus.post(event);

        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                testButton.setEnabled(enabled);
            }
        });*/
    }

    private byte[] getConfigLabel(){
        byte [] configLabel = null;
        try{
            PrinterLanguage printerLanguage = printer.getPrinterControlLanguage();
            SGD.SET("device.language", "zpl", connection);

            if(printerLanguage == PrinterLanguage.ZPL){
                configLabel = getFacturaNPL().getBytes(); /** TEXTO CON EL CODIGO DE LA IMPRESORA CON LA FACTURA A IMPRIMIR */
            }else
                if (printerLanguage == PrinterLanguage.CPCL){
                String cpcl = "! 0 200 200 406 1\r\n" + "ON-FEDD IGNORE\r\n" + "BOX 20 20 380 380 8\r\n" + "T 0 6 137 177 BASIS\r\n" + "PRINT\r\n";
                configLabel = cpcl.getBytes();
            }
        }catch (ConnectionException e){

        }
        return configLabel;
    }
    private void doConnectionTest(){
        printer = connect();

        if(printer != null && getFacturaNPL()!=null){
            sendTestLabel();
        }else{
            disconnect();
        }
    }

    private boolean isBluetoothSelected(){return true;}
    private String getMacAddressFieldText(){return dirMac;}

    private String getFacturaNPL() {
        return facturaNPL;
    }
    private String getTcpPortNumber(){return "";}
    private String getTcpAddress(){return "";}
}
