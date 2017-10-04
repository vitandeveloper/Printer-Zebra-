package com.example.marlon.app_dhl.retrofit.api;

import com.example.marlon.app_dhl.retrofit.model.Codigo;
import com.example.marlon.app_dhl.retrofit.model.EBCD;
import com.example.marlon.app_dhl.retrofit.model.FetchInvoiceData;
import com.example.marlon.app_dhl.retrofit.model.Usuario;
import com.example.marlon.app_dhl.retrofit.model.UsuarioIngreso;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Marlon on 24/7/2017.
 */

public interface Apiservice {

    @POST("mobile/login")
    Call<UsuarioIngreso> postusuaruio(@Body Usuario usuario);

    @POST("mobile/FetchEBCDS")
    Call<EBCD> getEBCD(@Body Codigo codigo);

    @POST("mobile/FetchInvoiceData")
    Call<List<FetchInvoiceData>> fetchInvoiceData(@Body Codigo codigo);
}
