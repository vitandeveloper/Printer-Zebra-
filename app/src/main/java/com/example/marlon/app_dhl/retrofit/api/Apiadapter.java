package com.example.marlon.app_dhl.retrofit.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Marlon on 24/7/2017.
 */

public class Apiadapter {

    private  static  Apiservice API_SERVICE;


    public static  Apiservice getApiService(){
        ///
       /* HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
        loggin.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient= new  OkHttpClient.Builder();
        httpClient.addInterceptor(loggin);*/
        ///


        String baseUrl="http://cbs3_ws2.basiscloud.com/api/";

        if (API_SERVICE==null){
            Retrofit.Builder retrofitBuilder= new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit= retrofitBuilder.client(new OkHttpClient.Builder().build())
                    .build();

                  /*  new Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()).build();
                    //.client(httpClient.build()).build();
                    */
            API_SERVICE= retrofit.create(Apiservice.class);
        }

        return API_SERVICE;

    }
}
