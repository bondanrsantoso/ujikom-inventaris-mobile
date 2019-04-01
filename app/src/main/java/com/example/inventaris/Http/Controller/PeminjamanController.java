package com.example.inventaris.Http.Controller;

import android.content.Context;

import com.example.inventaris.Http.Interface.PeminjamanInterface;
import com.example.inventaris.Model.Peminjaman.Peminjaman;
import com.example.inventaris.Model.Peminjaman.Request;
import com.example.inventaris.Model.User;
import com.example.inventaris.Preference.EnvironmentVariables;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PeminjamanController {
    String BASE_URL = "http://8c768972.ngrok.io/";
    Context mContext;

    public PeminjamanController(Context context){
        this.mContext = context;
        this.BASE_URL = EnvironmentVariables.getBaseURL(context);
    }

    public void get(int offset, int limit, Callback<Peminjaman> then){
        Request peminjamanRequest = new Request();

        peminjamanRequest.setLimit(limit);
        peminjamanRequest.setOffset(offset);
        peminjamanRequest.setApiToken(EnvironmentVariables.getUserToken(mContext));

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PeminjamanInterface peminjamanInterface = retrofit.create(PeminjamanInterface.class);

        Call<Peminjaman> get = peminjamanInterface.getPeminjaman(peminjamanRequest);
        get.enqueue(then);
    }
}
