package com.example.inventaris.Http.Interface;

import com.example.inventaris.Model.DetailPinjam.DetailPinjam;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DetailPinjamInterface {
    @GET("api/peminjaman/detail/json")
    Call<DetailPinjam> getDetailPinjam(@Query("id") String idPeminjaman);
}
