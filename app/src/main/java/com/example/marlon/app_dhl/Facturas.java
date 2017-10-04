package com.example.marlon.app_dhl;

import android.util.Log;

import com.example.marlon.app_dhl.retrofit.model.EBCD;
import com.example.marlon.app_dhl.retrofit.model.FetchInvoiceData;

import java.util.List;

/**
 * Created by Eukaris on 18/08/17.
 */

/**
 ^SZ2
 ^JMA
 ^MCY
 ^PMN
 ^PW700
 ~JSN
 ^JZY
 ^LH0,0
 ^LRN
 ^XA
 ^FO17,16^GB530,100,3^FS
 ^FO547,16^GB250,100,3^FS
 ^FO50,30^ADN,70,15^FDCUSTOMS DUTY INVOICE^FS
 ^FO580,30^ADN,70,15^FDDDP^FS
 ^CFA,20
 ^FO30,150^FDInvoice Number^FS
 ^FO30,180^FDAccount Number:^FS
 ^FO210,150^FD$$$$$^FS
 ^FO210,180^FD$$$$$^FS

 ^FO400,150^FDInvoice Date:^FS
 ^FO400,180^FDAWB Number:^FS
 ^FO550,150^FD$$$$$^FS
 ^FO550,180^FD$$$$$^FS

 ^FO30,210^FDCustomer Information: ^FS
 ^FO300,210^FDMARILYN T FELDMAN 44 Church Street . HAMILT^FS

 ^FO30,240^FDEntry Date^FS
 ^FO30,270^FDOrigin/Sender^FS
 ^FO210,240^FD$$$$$^FS
 ^FO210,270^FD$$$$$^FS

 ^FO400,280^FDEntry No^FS
 ^FO400,310^FDDestination/Receiver:^FS
 ^FO400,280^FDEntry No^FS
 ^FO400,310^FDDestination/Receiver:^FS


 ^FO50,340^FDDescription of Goods:^FS
 ^FO300,340^FDwood kitchenware -
 New (count 1)^FS
 ^FO50,370^FDPieces:^FS
 ^FO450,370^FDWeight:^FS
 ^FO17,400^GB550,30,3^FS
 ^FO567,400^GB230,30,3^FS
 ^FO150,410^FDAnalysis of Charges^FS
 ^FO600,410^FDTotal Amount^FS
 ^FO17,700^GB230,30,3^FS
 ^FO247,700^GB160,30,3^FS
 ^FO407,700^GB190,30,3^FS
 ^FO597,700^GB100,30,3^FS
 ^FO697,700^GB100,30,3^FS
 ^FO40,710^FDPayment Due Date:^FS
 ^FO270,710^FD3/05/2017^FS
 ^FO420,710^FDTotal Amount:^FS
 ^FO610,710^FD11.39^FS
 ^FO710,710^FDBMD^FS
 ^XZ
 */

public class Facturas {
    private static final String TAG= Facturas.class.getName();

    public Facturas() {
    }

    public static String getFacturaDutyInvoice(EBCD codigoIngreso, List<FetchInvoiceData> invoiceDataList){

        String factura=" ^SZ2\n" +
                //" ^JMA\n" +
                //" ^MCY\n" +
                //" ^PMN\n" +
                //" ^PW700\n" +
                //" ~JSN\n" +
                //" ^JZY\n" +
                " ^LH0,0\n" +
                " ^LRN\n" +
                " ^XA\n" +
                " ^FO17,16^GB530,100,1^FS\n" +
                " ^FO547,16^GB250,100,1^FS\n" +
                " ^FO50,30^ADN,70,15^FDCUSTOMS DUTY INVOICE^FS\n" +
                " ^FO580,30^ADN,70,15^FD"+codigoIngreso.getVwPymntTy()+"^FS\n" +
                " ^CFA,20\n" +
                " ^FO30,150^FDInvoice Number^FS\n" +
                " ^FO30,180^FDAccount Number:^FS\n" +
                " ^FO400,150^FDInvoice Date:^FS\n" +
                " ^FO400,180^FDAWB Number:^FS\n" +
                " ^FO30,210^FDCustomer Information: ^FS\n";
        int y=240;
        Log.e(TAG, "numero de carac cutomer information "+codigoIngreso.getVwCustInfo().length());
        int num_letr=60;

        int num_repeticiones=codigoIngreso.getVwCustInfo().length()/num_letr;
        int pos=0;
        for (int i=0; i<num_repeticiones; i++){
            factura+=" ^FO30,"+y+"^FD"+codigoIngreso.getVwCustInfo().substring(pos, (pos+num_letr))+"^FS\n"; //Origin / Sender
            pos+=num_letr;
            y+=30;
        }

        if(pos<codigoIngreso.getVwCustInfo().length()){
            factura+=" ^FO30,"+y+"^FD"+codigoIngreso.getVwCustInfo().substring(pos, codigoIngreso.getVwCustInfo().length())+"^FS\n"; //Origin / Sender
        }
                //factura+=" ^FO300,230^FD   MARILYN T FELDMAN 44 Church Street . HAMILT^FS\n" ;


        factura+=" ^FO30,"+(y+30)+"^FDEntry Date:^FS\n" +
                " ^FO30,"+(y+60)+"^FDOrigin/Sender^FS\n" +
                "\n" +
                " ^FO400,"+(y+30)+"^FDEntry No: ^FS\n" +
                " ^FO400,"+(y+60)+"^FDDestination/Receiver:^FS\n" +
                "\n" +
                " ^FO210,150^FD"+codigoIngreso.getVwDocNum()+"^FS\n" + //Invoice Number
                " ^FO210,180^FD"+codigoIngreso.getVwAccntNo()+"^FS\n" + //Account Number
                "\n" +
                " ^FO560,150^FD"+codigoIngreso.getVwDocDate()+"^FS\n" + //Invoice Date
                " ^FO560,180^FD"+codigoIngreso.getVwAWBNo()+"^FS\n" + //AWB Number:^
                "\n" +
                " ^FO180,"+(y+30)+"^FD"+codigoIngreso.getVwArrivalDt1()+"^FS\n" + // Entry Date
                " ^FO520,"+(y+30)+"^FD"+codigoIngreso.getVwEntryNo()+"^FS\n" // Entry No
                ;
        y+=60;


        num_letr=27;
        int y_sender=y+30; // para compartir los string en trozos
        Log.e(TAG, "numero de caracteres de sender "+codigoIngreso.getVwCnsgnorInfo().length());
        Log.e(TAG, "numero de caracteres de receiver "+codigoIngreso.getVwCnsgneeInfo().length());


        num_repeticiones=codigoIngreso.getVwCnsgnorInfo().length()/num_letr;
        pos=0;
        for (int i=0; i<num_repeticiones; i++){
            factura+=" ^FO30,"+y_sender+"^FD"+codigoIngreso.getVwCnsgnorInfo().substring(pos, (pos+num_letr))+"^FS\n"; //Origin / Sender
            pos+=num_letr;
            y_sender+=30;
        }
        if(pos<codigoIngreso.getVwCnsgnorInfo().length())
            factura+=" ^FO30,"+y_sender+"^FD"+codigoIngreso.getVwCnsgnorInfo().substring(pos, codigoIngreso.getVwCnsgnorInfo().length())+"^FS\n";

        int y_receiver=y+30; // para compartir los string en trozos
        num_repeticiones=codigoIngreso.getVwCnsgneeInfo().length()/num_letr;
        Log.e(TAG, "NUMERO DE REPETICIONES-"+codigoIngreso.getVwCnsgneeInfo()+"-");

        pos=0;
        for (int i=0; i<num_repeticiones; i++){
            factura+="^FO400,"+y_receiver+"^FD"+codigoIngreso.getVwCnsgneeInfo().substring(pos, (pos+num_letr))+"^FS\n"; //Origin / Sender
            pos+=num_letr;
            y_receiver+=30;
        }
        if(pos<codigoIngreso.getVwCnsgneeInfo().length()) {
            factura += " ^FO30," + y_receiver + "^FD" + codigoIngreso.getVwCnsgneeInfo().substring(pos, codigoIngreso.getVwCnsgneeInfo().length()) + "^FS\n"; //Origin / Sender
            y_receiver+=30;
        }
        if(y_sender>y_receiver)
            y=y_sender;
        else
            y=y_receiver;

        factura+="^FO30,"+y+"^FDDescription of Goods:^FS\n";

        y+=30;
        Log.e(TAG, "numero de carac Good Desc "+codigoIngreso.getVwGoodsDesc().length());
        num_letr=60;
        num_repeticiones=codigoIngreso.getVwGoodsDesc().length()/num_letr;
        pos=0;
        for (int i=0; i<num_repeticiones; i++){
            factura+=" ^FO30,"+y+"^FD"+codigoIngreso.getVwGoodsDesc().substring(pos, (pos+num_letr))+"^FS\n"; //Origin / Sender
            pos+=num_letr;
            y+=30;
        }

        if(pos<codigoIngreso.getVwGoodsDesc().length()){
            factura+=" ^FO30,"+y+"^FD"+codigoIngreso.getVwGoodsDesc().substring(pos, codigoIngreso.getVwGoodsDesc().length())+"^FS\n"; //Origin / Sender
            y+=30;
        }

        factura+="^FO30,"+y+"^FDPieces:  "+codigoIngreso.getVwPieces()+"^FS\n" +
                "^FO430,"+y+"^FDWeight:  "+codigoIngreso.getVwWghtQt()+"^FS\n";

        y+=30;
        factura+="^FO17,"+y+"^GB550,30,1^FS\n" +
                 "^FO567,"+y+"^GB230,30,1^FS\n";

        factura+= " ^FO150,"+(y+10)+"^FDAnalysis of Charges^FS\n" +
                " ^FO600,"+(y+10)+"^FDTotal Amount^FS";

        for(FetchInvoiceData invoiceData : invoiceDataList){
            y+=30;
            factura+="^FO17,"+y+"^GB780,30,1^FS\n"; // dibujamos la tabla
            factura+=" ^FO50,"+(y+10)+"^FD"+invoiceData.getVwChargeNm()+"^FS\n" +
                    " ^FO320,"+(y+10)+"^FD"+invoiceData.getVwChargeTy()+"^FS\n" +
                    " ^FO600,"+(y+10)+"^FD"+invoiceData.getVwTotalChargeAmount()+"^FS";
        }

        y+=30;
        factura+=" ^FO17,"+y+"^GB230,30,1^FS\n" +
                " ^FO247,"+y+"^GB160,30,1^FS\n" +
                " ^FO407,"+y+"^GB190,30,1^FS\n" +
                " ^FO597,"+y+"^GB100,30,1^FS\n" +
                " ^FO697,"+y+"^GB100,30,1^FS\n" +
                " ^FO40,"+(y+10)+"^FDPayment Due Date:^FS\n" +
                " ^FO270,"+(y+10)+"^FD"+codigoIngreso.getVwExpireDt()+"^FS\n" +
                " ^FO420,"+(y+10)+"^FDTotal Amount:^FS\n" +
                " ^FO610,"+(y+10)+"^FD"+codigoIngreso.getVwTotalAmount()+"^FS\n" + /**getVwTotalAmount  **/
                " ^FO710,"+(y+10)+"^FDBMD^FS";
        y+=50;
        factura+= "^FO150,"+y+"^FDPLEASE SEND YOUR REMITTANCES TO DHL EXPRESS^FS\n" +
                "^FO125, "+(y+30)+"^FDBERMUDA, 17 COX S HILL, PEMBROKE HM04 BERMUDA^FS\n" +
                "^FO50, "+(y+60)+"^FDDHL Express Bermuda, 17 Cox s Hill, Pembroke HM04 Bermuda^FS\n" +
                "^FO70, "+(y+90)+"^FDTel: +1-441-294-4838 ext 2210 - Fax: +1-441-295-1430^FS\n" +
                "^FO200, "+(y+120)+"^FDCompany Registration Number: 14251^FS"+
                "^XZ"; // FIN DE UNA ETIQUETA;

        factura+="^XA^PH^XZ";


        return factura;
    }

    public static String getFacturaeBCD(EBCD codigoIngreso){

        String factura=" ^SZ2\n" +
              //  " ^JMA\n" +
               // " ^MCY\n" +
                // " ^PMN\n" +
                //" ^PW700\n" +
                //" ~JSN\n" +
                //   " ^JZY\n" +
                " ^LH0,0\n" +
                " ^LRN\n" +
                " ^XA\n" +
                "^FO17,16^GB780,150,3^FS\n" +
                "^FO737,106^GB60,60,3^FS\n" +
                "^FO550,106^GB60,60,3^FS"+
                "^FO50,30^ADN,50,10^FDBermuda Custom Declaration^FS\n" +
                "^FO400,40^ADN,40,10^FDBCD Number: "+codigoIngreso.getVwBCDNum()+"^FS\n" +
                "\n" +
                "^FO650,120^ADN,40,10^FDImport^FS\n" +
                "^FO450,120^ADN,40,10^FDExport^FS\n" +
                "^FO760,120^ADN,40,10^FDX^FS\n" + //importado
                //"^FO570,120^ADN,40,10^FDX^FS\n" + //exportado
                "^FO50,110^ADN,40,10^FDTrader Reference: "+codigoIngreso.getVwTraderRef()+"^FS"+
                "^CFA,20\n" +
                "^FO30,180^FD1 Supplier ID No: "+codigoIngreso.getVwSupID()+"^FS\n"+
                "^FO30,210^FD  a. Name: "+codigoIngreso.getVwSupName()+"^FS\n" ;


        int y=240;
        String Street="  b. Street: "+codigoIngreso.getVwSupAd1();
        int num_letr=27;
        int num_repeticiones=Street.length()/num_letr;

        int pos=0;
        for (int i=0; i<num_repeticiones; i++){
            factura+="^FO30,"+y+"^FD"+Street.substring(pos, (pos+num_letr))+"^FS\n";
            pos+=num_letr;
            y+=30;
        }
        if(pos<Street.length()){
            factura+="^FO30,"+y+"^FD"+Street.substring(pos, Street.length())+"^FS\n";
            y+=30;
        }

        factura+="^FO30,"+y+"^FD  c. State/Province: "+codigoIngreso.getVwSupAd2()+"^FS\n" +
                "^FO30,"+(y+30)+"^FD  d. Zip Code: "+codigoIngreso.getVwSupPosCd()+"^FS\n" +
                "^FO30,"+(y+60)+"^FD  e. Country: "+codigoIngreso.getVwSupCtry()+"^FS"+
                "^FO30,"+(y+90)+"^FD2 Importer: ID No: "+codigoIngreso.getVwImpID()+"^FS\n";
        y+=120;

        String name="  a. Name: "+codigoIngreso.getVwImpName();
        num_repeticiones=name.length()/num_letr;

        pos=0;
        for (int i=0; i<num_repeticiones; i++){
            factura+="^FO30,"+y+"^FD"+name.substring(pos, (pos+num_letr))+"^FS\n";
            pos+=num_letr;
            y+=30;
        }
        if(pos<name.length()) {
            factura += "^FO30," + y + "^FD" + name.substring(pos, name.length()) + "^FS\n";
            y += 30;
        }
        /** NUMBER/STREET**/
        String number="  b. Number/Street: "+codigoIngreso.getVwImpAd1();
        num_repeticiones=number.length()/num_letr;
        pos=0;
        for (int i=0; i<num_repeticiones; i++){
            factura+="^FO30,"+y+"^FD"+number.substring(pos, (pos+num_letr))+"^FS\n";
            pos+=num_letr;
            y+=30;
        }
        if(pos<number.length()) {
            factura += "^FO30," + y + "^FD" + number.substring(pos, number.length()) + "^FS\n";
            y += 30;
        }

        factura+="^FO30,"+y+"^FD  c. Parish: "+codigoIngreso.getVwImpAd2()+"^FS\n" +
                "^FO30,"+(y+30)+"^FD  d. Postal Code: "+codigoIngreso.getVwImpPostCd()+"^FS\n"+
                "^FO30,"+(y+60)+"^FD3 Transport Details^FS\n" +
                "^FO30,"+(y+90)+"^FD  a. Voyage/Flight No: "+codigoIngreso.getVwAirline()+"/"+codigoIngreso.getVwFlightNo()+" ^FS\n" +
                "^FO30,"+(y+120)+"^FD  b. Port of Arrival: "+codigoIngreso.getVwArrivalPort()+"^FS";

        y+=150;
        /** Port of Arrival **/
        String arrival="  c. Arrival Date: "+codigoIngreso.getVwArrivalDt();
        num_repeticiones=arrival.length()/num_letr;
        pos=0;
        for (int i=0; i<num_repeticiones; i++){
            factura+="^FO30,"+y+"^FD"+arrival.substring(pos, (pos+num_letr))+"^FS\n";
            pos+=num_letr;
            y+=30;
        }
        if(pos<arrival.length()) {
            factura += "^FO30," + y + "^FD" + arrival.substring(pos, arrival.length()) + "^FS\n";
            y += 30;
        }

        factura+="^FO30,"+y+"^FD4 Manifest Details ^FS\n" +
                "^CFA,15\n" +
                "^FO30,"+(y+30)+"^FD  Manifest No: "+codigoIngreso.getVwManifestNo()+"^FS\n" +
                "^CFA,20\n" +
                "^FO30,"+(y+60)+"^FD  a. Bill of Lading/AWB No: "+codigoIngreso.getVwMasterAWB()+"^FS\n" +
            //    "^CFA,15\n" +
              //  "^FO100,710^FD6372669903 ^FS\n" +
              //  "^CFA,20\n" +
                "^FO30,"+(y+90)+"^FD  b. House Bill of Lading:^FS";
        y+=120;

        factura+="^FO400,180^FD5 Country of Direct Shipment: "+codigoIngreso.getVwCountryDispatch()+"^FS\n" +
                "^FO400,210^FD   Ctry of Original Shp: "+codigoIngreso.getVwCountryOrigin()+"^FS\n"+ /**TODO STUB*/
                "^FO400,240^FD6 Warehouse Identification: "+codigoIngreso.getVwWarehouseID()+"^FS\n" +
                "^FO400,270^FD7 Additional Information:^FS\n" +
                "^FO400,300^FD8 Method of Payment:^FS\n" +
                "^FO400,330^FD9  Charges/^FS\n" +
                "^FO400,350^FD   Deductions^FS\n" +
                "^FO620,330^FDAmount^FS\n" +
                "^FO620,350^FD(BD$)^FS\n" +
                "^FO590,340^FD%^FS"+

                "^FO400,380^FD10  Valuation Method: "+codigoIngreso.getVwValuationMethod()+"^FS\n" +
                "^FO400,410^FD11  No. of Packages: "+codigoIngreso.getTotPacks()+"^FS\n" +
                "^FO400,440^FD12  No. of Records: "+codigoIngreso.getTotRecs()+"^FS\n" +
                "^FO400,470^FD13  Invoice Amount: "+codigoIngreso.getTotInvoice()+"^FS\n " +
                "^FO400,500^FD14  Payable Amount: "+codigoIngreso.getTotPayable()+"^FS\n" +
                "^FO400,530^FD23  Currency: "+codigoIngreso.getVwCurrency()+" Value: "+codigoIngreso.getCUDValue()+"^FS\n" +
                "^FO400,560^FD24  Exchange Rate: "+codigoIngreso.getDEPRate()+"^FS\n" +
                "^FO400,590^FD25  BD$ Value: "+codigoIngreso.getBDAValue()+"^FS\n" +

                "^FO400,620^FD26  CHG/DED^FS\n" +
                "^FO600,610^FDAmount^FS\n" +
                "^FO600,630^FD(BD$)^FS\n" +
                "^FO550,620^FD%^FS";

        factura+="^FO30,"+y+"^FD15 Record No: "+codigoIngreso.getRecordNo()+"^FS\n" +
                "^FO30,"+(y+30)+"^FD17 Country of Origin: "+codigoIngreso.getVwCtryOrigin()+"^FS\n" +
                "^FO30,"+(y+60)+"^FD19 Description: "+codigoIngreso.getVwGdsDesc()+"^FS\n" +
                "^FO30,"+(y+90)+"^FD21 Quantity/Units2: "+codigoIngreso.getVwQty2()+"^FS\n" +

                "^FO400,660^FD16 CPC: "+codigoIngreso.getCPC()+"^FS\n" +
                "^FO400,690^FD18 Tariff No: "+codigoIngreso.getVwTariffNo()+"^FS\n" +
                "^FO400,720^FD20 Quantity/Units1:^FS\n";
        y+=120;
        factura+="^FO17,"+y+"^GB60,30,1^FS\n" +
                "^FO77,"+y+"^GB140,30,1^FS\n" +
                "^FO217,"+y+"^GB140,30,1^FS\n" +
                "^FO357,"+y+"^GB140,30,1^FS\n" +
                "^FO497,"+y+"^GB140,30,1^FS\n" +
                "^FO637,"+y+"^GB140,30,1^FS\n";

        factura+="^FO30,"+(y+10)+"^FD27^FS\n" +
                "^FO110,"+(y+10)+"^FDType^FS\n" +
                "^FO250,"+(y+10)+"^FDTax ID^FS\n" +
                "^FO390,"+(y+10)+"^FDValue^FS\n" +
                "^FO530,"+(y+10)+"^FDRate^FS\n" +
                "^FO670,"+(y+10)+"^FDAmount^FS\n";

        y+=30;
        factura+="^FO17,"+y+"^GB60,30,1^FS\n" +
                "^FO77,"+y+"^GB140,30,1^FS\n" +
                "^FO217,"+y+"^GB140,30,1^FS\n" +
                "^FO357,"+y+"^GB140,30,1^FS\n" +
                "^FO497,"+y+"^GB140,30,1^FS\n" +
                "^FO637,"+y+"^GB140,30,1^FS\n";

        factura+="^FO30,"+(y+10)+"^FD(1)^FS\n" +
                "^FO110,"+(y+10)+"^FDCUD^FS\n" +
                "^FO250,"+(y+10)+"^FD"+codigoIngreso.getVwCUDTaxID()+"^FS\n" +
                "^FO390,"+(y+10)+"^FD"+codigoIngreso.getCUDValue()+"^FS\n" +
                "^FO530,"+(y+10)+"^FD"+codigoIngreso.getCUDRate()+"^FS\n" +
                "^FO670,"+(y+10)+"^FD"+codigoIngreso.getCUDAmount()+"^FS\n";

        y+=30;
        factura+="^FO17,"+y+"^GB60,30,1^FS\n" +
                "^FO77,"+y+"^GB140,30,1^FS\n" +
                "^FO217,"+y+"^GB140,30,1^FS\n" +
                "^FO357,"+y+"^GB140,30,1^FS\n" +
                "^FO497,"+y+"^GB140,30,1^FS\n" +
                "^FO637,"+y+"^GB140,30,1^FS\n";

        factura+="^FO30,"+(y+10)+"^FD(2)^FS\n" +
                "^FO110,"+(y+10)+"^FDWHA^FS\n" +
                "^FO250,"+(y+10)+"^FD"+codigoIngreso.getWHATaxID()+"^FS\n" +
                "^FO390,"+(y+10)+"^FD"+codigoIngreso.getWHAValue()+"^FS\n" +
                "^FO530,"+(y+10)+"^FD"+codigoIngreso.getWHARate()+"^FS\n" +
                "^FO670,"+(y+10)+"^FD"+codigoIngreso.getWHAAmount()+"^FS\n";

        factura+="^FO500,"+(y+40)+"^FDTotal Due: "+codigoIngreso.getTotalDue()+"^FS";

        y+=70;
        factura+="^FO17,"+y+"^GB60,30,1^FS\n" +
                "^FO77,"+y+"^GB250,30,1^FS\n" +
                "^FO327,"+y+"^GB250,30,1^FS\n" +
                "^FO577,"+y+"^GB200,90,1^FS\n" +

                "^FO17,"+(y+30)+"^GB560,60,1^FS\n" +
                "^FO17,"+(y+90)+"^GB560,30,1^FS\n" +
                "^FO577,"+(y+90)+"^GB200,30,1^FS\n" +

                "^FO30,"+(y+10)+"^FDI^FS\n" +
                "^FO120,"+(y+10)+"^FD DHL EXPRESS ^FS\n" +
                "^FO340,"+(y+10)+"^FDID No: 100307 ^FS\n" +
                "^FO30,"+(y+40)+"^FDDeclare the particulars of this document^FS\n" +
                "^FO30,"+(y+70)+"^FD to be true, accurate and complete.^FS\n" +
                "^FO610,"+(y+40)+"^FDCustoms Only^FS\n" +

                "^FO250,"+(y+95)+"^FDSTATUS^FS\n" +
                "^FO620,"+(y+95)+"^FDRELEASED^FS"+
                "^XZ"; // FIN DE UNA ETIQUETA;

        factura+="^XA^PH^XZ";

        return factura;
    }


    public static String getBoth(EBCD codigoIngreso, List<FetchInvoiceData> invoiceDataList){

        String factura=" ^SZ2\n" +
                //" ^JMA\n" +
                //" ^MCY\n" +
                //" ^PMN\n" +
                //" ^PW700\n" +
                //" ~JSN\n" +
                //" ^JZY\n" +
                " ^LH0,0\n" +
                " ^LRN\n" +
                " ^XA\n" +
                " ^FO17,16^GB530,100,1^FS\n" +
                " ^FO547,16^GB250,100,1^FS\n" +
                " ^FO50,30^ADN,70,15^FDCUSTOMS DUTY INVOICE^FS\n" +
                " ^FO580,30^ADN,70,15^FD"+codigoIngreso.getVwPymntTy()+"^FS\n" +
                " ^CFA,20\n" +
                " ^FO30,150^FDInvoice Number^FS\n" +
                " ^FO30,180^FDAccount Number:^FS\n" +
                " ^FO400,150^FDInvoice Date:^FS\n" +
                " ^FO400,180^FDAWB Number:^FS\n" +
                " ^FO30,210^FDCustomer Information: ^FS\n";
        int y=240;
        Log.e(TAG, "numero de carac cutomer information "+codigoIngreso.getVwCustInfo().length());
        int num_letr=60;

        int num_repeticiones=codigoIngreso.getVwCustInfo().length()/num_letr;
        int pos=0;
        for (int i=0; i<num_repeticiones; i++){
            factura+=" ^FO30,"+y+"^FD"+codigoIngreso.getVwCustInfo().substring(pos, (pos+num_letr))+"^FS\n"; //Origin / Sender
            pos+=num_letr;
            y+=30;
        }

        if(pos<codigoIngreso.getVwCustInfo().length()){
            factura+=" ^FO30,"+y+"^FD"+codigoIngreso.getVwCustInfo().substring(pos, codigoIngreso.getVwCustInfo().length())+"^FS\n"; //Origin / Sender
        }
        //factura+=" ^FO300,230^FD   MARILYN T FELDMAN 44 Church Street . HAMILT^FS\n" ;


        factura+=" ^FO30,"+(y+30)+"^FDEntry Date:^FS\n" +
                " ^FO30,"+(y+60)+"^FDOrigin/Sender^FS\n" +
                "\n" +
                " ^FO400,"+(y+30)+"^FDEntry No: ^FS\n" +
                " ^FO400,"+(y+60)+"^FDDestination/Receiver:^FS\n" +
                "\n" +
                " ^FO210,150^FD"+codigoIngreso.getVwDocNum()+"^FS\n" + //Invoice Number
                " ^FO210,180^FD"+codigoIngreso.getVwAccntNo()+"^FS\n" + //Account Number
                "\n" +
                " ^FO560,150^FD"+codigoIngreso.getVwDocDate()+"^FS\n" + //Invoice Date
                " ^FO560,180^FD"+codigoIngreso.getVwAWBNo()+"^FS\n" + //AWB Number:^
                "\n" +
                " ^FO180,"+(y+30)+"^FD"+codigoIngreso.getVwArrivalDt1()+"^FS\n" + // Entry Date
                " ^FO520,"+(y+30)+"^FD"+codigoIngreso.getVwEntryNo()+"^FS\n" // Entry No
        ;
        y+=60;


        num_letr=27;
        int y_sender=y+30; // para compartir los string en trozos
        Log.e(TAG, "numero de caracteres de sender "+codigoIngreso.getVwCnsgnorInfo().length());
        Log.e(TAG, "numero de caracteres de receiver "+codigoIngreso.getVwCnsgneeInfo().length());


        num_repeticiones=codigoIngreso.getVwCnsgnorInfo().length()/num_letr;
        pos=0;
        for (int i=0; i<num_repeticiones; i++){
            factura+=" ^FO30,"+y_sender+"^FD"+codigoIngreso.getVwCnsgnorInfo().substring(pos, (pos+num_letr))+"^FS\n"; //Origin / Sender
            pos+=num_letr;
            y_sender+=30;
        }
        if(pos<codigoIngreso.getVwCnsgnorInfo().length())
            factura+=" ^FO30,"+y_sender+"^FD"+codigoIngreso.getVwCnsgnorInfo().substring(pos, codigoIngreso.getVwCnsgnorInfo().length())+"^FS\n";

        int y_receiver=y+30; // para compartir los string en trozos
        num_repeticiones=codigoIngreso.getVwCnsgneeInfo().length()/num_letr;
        Log.e(TAG, "NUMERO DE REPETICIONES-"+codigoIngreso.getVwCnsgneeInfo()+"-");

        pos=0;
        for (int i=0; i<num_repeticiones; i++){
            factura+="^FO400,"+y_receiver+"^FD"+codigoIngreso.getVwCnsgneeInfo().substring(pos, (pos+num_letr))+"^FS\n"; //Origin / Sender
            pos+=num_letr;
            y_receiver+=30;
        }
        if(pos<codigoIngreso.getVwCnsgneeInfo().length()) {
            factura += " ^FO30," + y_receiver + "^FD" + codigoIngreso.getVwCnsgneeInfo().substring(pos, codigoIngreso.getVwCnsgneeInfo().length()) + "^FS\n"; //Origin / Sender
            y_receiver+=30;
        }
        if(y_sender>y_receiver)
            y=y_sender;
        else
            y=y_receiver;

        factura+="^FO30,"+y+"^FDDescription of Goods:^FS\n";

        y+=30;
        Log.e(TAG, "numero de carac Good Desc "+codigoIngreso.getVwGoodsDesc().length());
        num_letr=60;
        num_repeticiones=codigoIngreso.getVwGoodsDesc().length()/num_letr;
        pos=0;
        for (int i=0; i<num_repeticiones; i++){
            factura+=" ^FO30,"+y+"^FD"+codigoIngreso.getVwGoodsDesc().substring(pos, (pos+num_letr))+"^FS\n"; //Origin / Sender
            pos+=num_letr;
            y+=30;
        }

        if(pos<codigoIngreso.getVwGoodsDesc().length()){
            factura+=" ^FO30,"+y+"^FD"+codigoIngreso.getVwGoodsDesc().substring(pos, codigoIngreso.getVwGoodsDesc().length())+"^FS\n"; //Origin / Sender
            y+=30;
        }

        factura+="^FO30,"+y+"^FDPieces:  "+codigoIngreso.getVwPieces()+"^FS\n" +
                "^FO430,"+y+"^FDWeight:  "+codigoIngreso.getVwWghtQt()+"^FS\n";

        y+=30;
        factura+="^FO17,"+y+"^GB550,30,1^FS\n" +
                "^FO567,"+y+"^GB230,30,1^FS\n";

        factura+= " ^FO150,"+(y+10)+"^FDAnalysis of Charges^FS\n" +
                " ^FO600,"+(y+10)+"^FDTotal Amount^FS";

        for(FetchInvoiceData invoiceData : invoiceDataList){
            y+=30;
            factura+="^FO17,"+y+"^GB780,30,1^FS\n"; // dibujamos la tabla
            factura+=" ^FO50,"+(y+10)+"^FD"+invoiceData.getVwChargeNm()+"^FS\n" +
                    " ^FO320,"+(y+10)+"^FD"+invoiceData.getVwChargeTy()+"^FS\n" +
                    " ^FO600,"+(y+10)+"^FD"+invoiceData.getVwTotalChargeAmount()+"^FS";
        }

        y+=30;
        factura+=" ^FO17,"+y+"^GB230,30,1^FS\n" +
                " ^FO247,"+y+"^GB160,30,1^FS\n" +
                " ^FO407,"+y+"^GB190,30,1^FS\n" +
                " ^FO597,"+y+"^GB100,30,1^FS\n" +
                " ^FO697,"+y+"^GB100,30,1^FS\n" +
                " ^FO40,"+(y+10)+"^FDPayment Due Date:^FS\n" +
                " ^FO270,"+(y+10)+"^FD"+codigoIngreso.getVwExpireDt()+"^FS\n" +
                " ^FO420,"+(y+10)+"^FDTotal Amount:^FS\n" +
                " ^FO610,"+(y+10)+"^FD"+codigoIngreso.getVwTotalAmount()+"^FS\n" + /**getVwTotalAmount  **/
                " ^FO710,"+(y+10)+"^FDBMD^FS";
        y+=50;
        factura+= "^FO150,"+y+"^FDPLEASE SEND YOUR REMITTANCES TO DHL EXPRESS^FS\n" +
                "^FO125, "+(y+30)+"^FDBERMUDA, 17 COX S HILL, PEMBROKE HM04 BERMUDA^FS\n" +
                "^FO50, "+(y+60)+"^FDDHL Express Bermuda, 17 Cox s Hill, Pembroke HM04 Bermuda^FS\n" +
                "^FO70, "+(y+90)+"^FDTel: +1-441-294-4838 ext 2210 - Fax: +1-441-295-1430^FS\n" +
                "^FO200, "+(y+120)+"^FDCompany Registration Number: 14251^FS"+
                "^XZ"; // FIN DE UNA ETIQUETA

        factura+="^XA^PH^XZ";

        /*** TODO STUB ebcd    "^FO17,16^GB780,150,3^FS\n" +***/

        y=16;

        factura+="^XA"+
                "^FO17,"+y+"^GB780,150,3^FS\n" +
                "^FO737,"+(y+90)+"^GB60,60,3^FS\n" +
                "^FO550,"+(y+90)+"^GB60,60,3^FS"+
                "^FO50,"+(y+30)+"^ADN,50,10^FDBermuda Custom Declaration^FS\n" +
                "^FO400,"+(y+40)+"^ADN,40,10^FDBCD Number: "+codigoIngreso.getVwBCDNum()+"^FS\n" +
                "\n" +
                "^FO650,"+(y+110)+"^ADN,40,10^FDImport^FS\n" +
                "^FO450, "+(y+110)+"^ADN,40,10^FDExport^FS\n" +
                "^FO760,"+(y+110)+"^ADN,40,10^FDX^FS\n" + //importado
                //"^FO570,120^ADN,40,10^FDX^FS\n" + //exportado
                "^FO50,"+(y+100)+"^ADN,40,10^FDTrader Reference: "+codigoIngreso.getVwTraderRef()+"^FS"+
                "^CFA,20\n" +
                "^FO30,"+(y+170)+"^FD1 Supplier ID No: "+codigoIngreso.getVwSupID()+"^FS\n"+
                "^FO30,"+(y+200)+"^FD  a. Name: "+codigoIngreso.getVwSupName()+"^FS\n" ;
        y+=230;

        String Street="  b. Street: "+codigoIngreso.getVwSupAd1();
        num_letr=27;
        num_repeticiones=Street.length()/num_letr;

        pos=0;
        for (int i=0; i<num_repeticiones; i++){
            factura+="^FO30,"+y+"^FD"+Street.substring(pos, (pos+num_letr))+"^FS\n";
            pos+=num_letr;
            y+=30;
        }
        if(pos<Street.length()){
            factura+="^FO30,"+y+"^FD"+Street.substring(pos, Street.length())+"^FS\n";
            y+=30;
        }

        factura+="^FO30,"+y+"^FD  c. State/Province: "+codigoIngreso.getVwSupAd2()+"^FS\n" +
                "^FO30,"+(y+30)+"^FD  d. Zip Code: "+codigoIngreso.getVwSupPosCd()+"^FS\n" +
                "^FO30,"+(y+60)+"^FD  e. Country: "+codigoIngreso.getVwSupCtry()+"^FS"+
                "^FO30,"+(y+90)+"^FD2 Importer: ID No: "+codigoIngreso.getVwImpID()+"^FS\n";
        y+=120;

        String name="  a. Name: "+codigoIngreso.getVwImpName();
        num_repeticiones=name.length()/num_letr;

        pos=0;
        for (int i=0; i<num_repeticiones; i++){
            factura+="^FO30,"+y+"^FD"+name.substring(pos, (pos+num_letr))+"^FS\n";
            pos+=num_letr;
            y+=30;
        }
        if(pos<name.length()) {
            factura += "^FO30," + y + "^FD" + name.substring(pos, name.length()) + "^FS\n";
            y += 30;
        }
        String number="  b. Number/Street: "+codigoIngreso.getVwImpAd1();
        num_repeticiones=number.length()/num_letr;
        pos=0;
        for (int i=0; i<num_repeticiones; i++){
            factura+="^FO30,"+y+"^FD"+number.substring(pos, (pos+num_letr))+"^FS\n";
            pos+=num_letr;
            y+=30;
        }
        if(pos<number.length()) {
            factura += "^FO30," + y + "^FD" + number.substring(pos, number.length()) + "^FS\n";
            y += 30;
        }

        factura+="^FO30,"+y+"^FD  c. Parish: "+codigoIngreso.getVwImpAd2()+"^FS\n" +
                "^FO30,"+(y+30)+"^FD  d. Postal Code: "+codigoIngreso.getVwImpPostCd()+"^FS\n"+
                "^FO30,"+(y+60)+"^FD3 Transport Details^FS\n" +
                "^FO30,"+(y+90)+"^FD  a. Voyage/Flight No: "+codigoIngreso.getVwAirline()+"/"+codigoIngreso.getVwFlightNo()+" ^FS\n" +
                "^FO30,"+(y+120)+"^FD  b. Port of Arrival: "+codigoIngreso.getVwArrivalPort()+"^FS";

        y+=150;

        String arrival="  c. Arrival Date: "+codigoIngreso.getVwArrivalDt();
        num_repeticiones=arrival.length()/num_letr;
        pos=0;
        for (int i=0; i<num_repeticiones; i++){
            factura+="^FO30,"+y+"^FD"+arrival.substring(pos, (pos+num_letr))+"^FS\n";
            pos+=num_letr;
            y+=30;
        }
        if(pos<arrival.length()) {
            factura += "^FO30," + y + "^FD" + arrival.substring(pos, arrival.length()) + "^FS\n";
            y += 30;
        }

        factura+="^FO30,"+y+"^FD4 Manifest Details ^FS\n" +
                "^CFA,15\n" +
                "^FO30,"+(y+30)+"^FD  Manifest No: "+codigoIngreso.getVwManifestNo()+"^FS\n" +
                "^CFA,20\n" +
                "^FO30,"+(y+60)+"^FD  a. Bill of Lading/AWB No: "+codigoIngreso.getVwMasterAWB()+"^FS\n" +
                //    "^CFA,15\n" +
                //  "^FO100,710^FD6372669903 ^FS\n" +
                //  "^CFA,20\n" +
                "^FO30,"+(y+90)+"^FD  b. House Bill of Lading:^FS";

        y+=120;

        int y_d=16;

        factura+="^FO400,"+(y_d+170)+"^FD5 Country of Direct Shipment: "+codigoIngreso.getVwCountryDispatch()+"^FS\n" +
                "^FO400,"+(y_d+200)+"^FD   Ctry of Original Shp: "+codigoIngreso.getVwCountryOrigin()+"^FS\n"+
                "^FO400,"+(y_d+230)+"^FD6 Warehouse Identification: "+codigoIngreso.getVwWarehouseID()+"^FS\n" +
                "^FO400,"+(y_d+260)+"^FD7 Additional Information:^FS\n" +
                "^FO400,"+(y_d+290)+"^FD8 Method of Payment:^FS\n" +
                "^FO400,"+(y_d+310)+"^FD9  Charges/^FS\n" +
                "^FO400,"+(y_d+340)+"^FD   Deductions^FS\n" +
                "^FO620,"+(y_d+310)+"^FDAmount^FS\n" +
                "^FO620,"+(y_d+340)+"^FD(BD$)^FS\n" +
                "^FO590,"+(y_d+330)+"^FD%^FS"+

                "^FO400,"+(y_d+370)+"^FD10  Valuation Method: "+codigoIngreso.getVwValuationMethod()+"^FS\n" +
                "^FO400,"+(y_d+400)+"^FD11  No. of Packages: "+codigoIngreso.getTotPacks()+"^FS\n" +
                "^FO400,"+(y_d+430)+"^FD12  No. of Records: "+codigoIngreso.getTotRecs()+"^FS\n" +
                "^FO400,"+(y_d+460)+"^FD13  Invoice Amount: "+codigoIngreso.getTotInvoice()+"^FS\n " +
                "^FO400,"+(y_d+490)+"^FD14  Payable Amount: "+codigoIngreso.getTotPayable()+"^FS\n" +
                "^FO400,"+(y_d+520)+"^FD23  Currency: "+codigoIngreso.getVwCurrency()+" Value: "+codigoIngreso.getCUDValue()+"^FS\n" +
                "^FO400,"+(y_d+550)+"^FD24  Exchange Rate: "+codigoIngreso.getDEPRate()+"^FS\n" +
                "^FO400,"+(y_d+580)+"^FD25  BD$ Value: "+codigoIngreso.getBDAValue()+"^FS\n" +

                "^FO400,"+(y_d+610)+"^FD26  CHG/DED^FS\n" +
                "^FO600,"+(y_d+600)+"^FDAmount^FS\n" +
                "^FO600,"+(y_d+620)+"^FD(BD$)^FS\n" +
                "^FO550,"+(y_d+610)+"^FD%^FS"+

                "^FO400,"+(y_d+650)+"^FD16 CPC: "+codigoIngreso.getCPC()+"^FS\n" +
                "^FO400,"+(y_d+680)+"^FD18 Tariff No: "+codigoIngreso.getVwTariffNo()+"^FS\n" +
                "^FO400,"+(y_d+710)+"^FD20 Quantity/Units1:^FS\n";

        factura+="^FO30,"+y+"^FD15 Record No: "+codigoIngreso.getRecordNo()+"^FS\n" +
                "^FO30,"+(y+30)+"^FD17 Country of Origin: "+codigoIngreso.getVwCtryOrigin()+"^FS\n" +
                "^FO30,"+(y+60)+"^FD19 Description: "+codigoIngreso.getVwGdsDesc()+"^FS\n" +
                "^FO30,"+(y+90)+"^FD21 Quantity/Units2: "+codigoIngreso.getVwQty2()+"^FS\n" ;



        y+=120;
        factura+="^FO17,"+y+"^GB60,30,1^FS\n" +
                "^FO77,"+y+"^GB140,30,1^FS\n" +
                "^FO217,"+y+"^GB140,30,1^FS\n" +
                "^FO357,"+y+"^GB140,30,1^FS\n" +
                "^FO497,"+y+"^GB140,30,1^FS\n" +
                "^FO637,"+y+"^GB140,30,1^FS\n";

        factura+="^FO30,"+(y+10)+"^FD27^FS\n" +
                "^FO110,"+(y+10)+"^FDType^FS\n" +
                "^FO250,"+(y+10)+"^FDTax ID^FS\n" +
                "^FO390,"+(y+10)+"^FDValue^FS\n" +
                "^FO530,"+(y+10)+"^FDRate^FS\n" +
                "^FO670,"+(y+10)+"^FDAmount^FS\n";

        y+=30;
        factura+="^FO17,"+y+"^GB60,30,1^FS\n" +
                "^FO77,"+y+"^GB140,30,1^FS\n" +
                "^FO217,"+y+"^GB140,30,1^FS\n" +
                "^FO357,"+y+"^GB140,30,1^FS\n" +
                "^FO497,"+y+"^GB140,30,1^FS\n" +
                "^FO637,"+y+"^GB140,30,1^FS\n";

        factura+="^FO30,"+(y+10)+"^FD(1)^FS\n" +
                "^FO110,"+(y+10)+"^FDCUD^FS\n" +
                "^FO250,"+(y+10)+"^FD"+codigoIngreso.getVwCUDTaxID()+"^FS\n" +
                "^FO390,"+(y+10)+"^FD"+codigoIngreso.getCUDValue()+"^FS\n" +
                "^FO530,"+(y+10)+"^FD"+codigoIngreso.getCUDRate()+"^FS\n" +
                "^FO670,"+(y+10)+"^FD"+codigoIngreso.getCUDAmount()+"^FS\n";

        y+=30;
        factura+="^FO17,"+y+"^GB60,30,1^FS\n" +
                "^FO77,"+y+"^GB140,30,1^FS\n" +
                "^FO217,"+y+"^GB140,30,1^FS\n" +
                "^FO357,"+y+"^GB140,30,1^FS\n" +
                "^FO497,"+y+"^GB140,30,1^FS\n" +
                "^FO637,"+y+"^GB140,30,1^FS\n";

        factura+="^FO30,"+(y+10)+"^FD(2)^FS\n" +
                "^FO110,"+(y+10)+"^FDWHA^FS\n" +
                "^FO250,"+(y+10)+"^FD"+codigoIngreso.getWHATaxID()+"^FS\n" +
                "^FO390,"+(y+10)+"^FD"+codigoIngreso.getWHAValue()+"^FS\n" +
                "^FO530,"+(y+10)+"^FD"+codigoIngreso.getWHARate()+"^FS\n" +
                "^FO670,"+(y+10)+"^FD"+codigoIngreso.getWHAAmount()+"^FS\n";

        factura+="^FO500,"+(y+40)+"^FDTotal Due: "+codigoIngreso.getTotalDue()+"^FS";

        y+=70;
        factura+="^FO17,"+y+"^GB60,30,1^FS\n" +
                "^FO77,"+y+"^GB250,30,1^FS\n" +
                "^FO327,"+y+"^GB250,30,1^FS\n" +
                "^FO577,"+y+"^GB200,90,1^FS\n" +

                "^FO17,"+(y+30)+"^GB560,60,1^FS\n" +
                "^FO17,"+(y+90)+"^GB560,30,1^FS\n" +
                "^FO577,"+(y+90)+"^GB200,30,1^FS\n" +

                "^FO30,"+(y+10)+"^FDI^FS\n" +
                "^FO120,"+(y+10)+"^FD DHL EXPRESS ^FS\n" +
                "^FO340,"+(y+10)+"^FDID No: 100307 ^FS\n" +
                "^FO30,"+(y+40)+"^FDDeclare the particulars of this document^FS\n" +
                "^FO30,"+(y+70)+"^FD to be true, accurate and complete.^FS\n" +
                "^FO610,"+(y+40)+"^FDCustoms Only^FS\n" +

                "^FO250,"+(y+95)+"^FDSTATUS^FS\n" +
                "^FO620,"+(y+95)+"^FDRELEASED^FS"+
                "^XZ";

        factura+="^XA^PH^XZ";

        return factura;
    }



}
