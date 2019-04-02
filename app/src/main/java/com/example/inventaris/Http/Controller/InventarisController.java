package com.example.inventaris.Http.Controller;

import android.content.Context;

import com.example.inventaris.Http.Interface.InventarisInterface;
import com.example.inventaris.Model.Inventaris.Inventaris;
import com.example.inventaris.Model.Peminjaman.Peminjaman;
import com.example.inventaris.Preference.EnvironmentVariables;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InventarisController {
    String BASE_URL;
    Context mContext;
    InventarisInterface mInventarisInterface;
    Callback<Inventaris> mDefaultCallback = null;

    public InventarisController(Context context){
        this.mContext = context;
        this.BASE_URL = EnvironmentVariables.getBaseURL(context);
        build();
    }

    public InventarisController(Context context, Callback<Inventaris> defaultCallback){
        this.mContext = context;
        this.BASE_URL = EnvironmentVariables.getBaseURL(context);
        this.mDefaultCallback = defaultCallback;
        build();
    }

    public void build(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mInventarisInterface = retrofit.create(InventarisInterface.class);
    }

    public void get(int offset, int limit){
        Call<Inventaris> get = mInventarisInterface.getInventaris(offset, limit);
        get.enqueue(mDefaultCallback);
    }

    public void get(int offset, int limit, Callback<Inventaris> then){
        Call<Inventaris> get = mInventarisInterface.getInventaris(offset, limit);
        get.enqueue(then);
    }

    public void search(String query){
        Call<Inventaris> search = mInventarisInterface.searchInventaris(query);
        search.enqueue(mDefaultCallback);
    }

    public void search(String query, Callback<Inventaris> then){
        Call<Inventaris> search = mInventarisInterface.searchInventaris(query);
        search.enqueue(then);
    }
}
