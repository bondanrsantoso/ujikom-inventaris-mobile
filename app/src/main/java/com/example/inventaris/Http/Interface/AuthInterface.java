package com.example.inventaris.Http.Interface;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import com.example.inventaris.Model.User;

public interface AuthInterface {
    @POST("api/auth/login")
    Call<User> authenticateUser(@Body User user);

    @POST("api/auth/refresh")
    Call<User> refreshToken(@Body User user);
}
