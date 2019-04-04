package com.example.inventaris.Http.Controller;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.inventaris.Http.Interface.DetailPinjamInterface;
import com.example.inventaris.Model.DetailPinjam.DetailPinjam;
import com.example.inventaris.Preference.EnvironmentVariables;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailPinjamController {
    private Context mContext;
    private Callback<DetailPinjam> mDefaultCallback;
    private String BASE_URL;
    private DetailPinjamInterface mDetailPinjamInterface;

    public DetailPinjamController(Context context, Callback<DetailPinjam> defaultCalback){
        this.mContext = context;
        this.mDefaultCallback = defaultCalback;
        BASE_URL = EnvironmentVariables.getBaseURL(mContext);

        build();
    }

    private void build(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mDetailPinjamInterface = retrofit.create(DetailPinjamInterface.class);
    }

    public void get(@NonNull String idPeminjaman){
        Call<DetailPinjam> get = mDetailPinjamInterface.getDetailPinjam(idPeminjaman);
        get.enqueue(mDefaultCallback);
    }
}
