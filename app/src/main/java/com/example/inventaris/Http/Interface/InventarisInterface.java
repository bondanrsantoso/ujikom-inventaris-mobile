package com.example.inventaris.Http.Interface;

import com.example.inventaris.Model.Inventaris.Inventaris;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InventarisInterface {
    @GET("api/mobile/inventaris/get")
    Call<Inventaris> getInventaris();

    @GET("api/mobile/inventaris/get")
    Call<Inventaris> getInventaris(@Query("only") String ids);

    @GET("api/mobile/inventaris/get")
    Call<Inventaris> getInventaris(@Query("only") String ids,
                                   @Query("start_date") String startDateStr,
                                   @Query("end_date") String endDateStr);

    @GET("api/mobile/inventaris/get")
    Call<Inventaris> getInventaris(@Query("offset") int offset, @Query("limit") int limit);

    @GET("api/mobile/inventaris/get")
    Call<Inventaris> searchInventaris(@Query("q") String search);

    @GET("api/mobile/inventaris/get")
    Call<Inventaris> searchInventaris(@Query("offset") int offset, @Query("limit") int limit, @Query("q") String search);
}
