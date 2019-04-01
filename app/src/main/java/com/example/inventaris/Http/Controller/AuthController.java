package com.example.inventaris.Http.Controller;

import android.content.Context;
import android.util.Log;

import com.example.inventaris.Http.Interface.AuthInterface;
import com.example.inventaris.Preference.EnvironmentVariables;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.inventaris.Model.User;

public class AuthController {

    final String TAG = "AUTHCONTROLLER";

    String BASE_URL = "http://8c768972.ngrok.io/";
    Context mContext;
    Callback<User> mCallback;

    public AuthController(Context context, Callback<User> callback){
        this.mCallback = callback;
        this.mContext = context;
        this.BASE_URL = EnvironmentVariables.getBaseURL(context);
    }

    public void authenticate(User user) {
//        Gson userDataGson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory
                .create()).build();

        AuthInterface authenticator = retrofit.create(AuthInterface.class);

        Log.d(TAG, "authenticate: " + user.toString());
        Call<User> authCall = authenticator.authenticateUser(user);
        authCall.enqueue(this.mCallback);
    }

    public void refreshToken(User user){
//        Gson userDataGson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory
                        .create()).build();

        AuthInterface authenticator = retrofit.create(AuthInterface.class);

        Call<User> authCall = authenticator.refreshToken(user);
        authCall.enqueue(this.mCallback);
    }
}
