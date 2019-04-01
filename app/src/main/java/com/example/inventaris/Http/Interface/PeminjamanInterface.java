package com.example.inventaris.Http.Interface;

import com.example.inventaris.Model.Peminjaman.Peminjaman;
import com.example.inventaris.Model.Peminjaman.Request;
import com.example.inventaris.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PeminjamanInterface {
    @POST("api/peminjaman/get/json")
    Call<Peminjaman> getPeminjaman(@Body Request peminjamanRequest);
}
