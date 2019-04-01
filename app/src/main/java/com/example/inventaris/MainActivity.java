package com.example.inventaris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.inventaris.Http.Controller.AuthController;
import com.example.inventaris.Model.User;
import com.example.inventaris.Preference.EnvironmentVariables;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<User> {
//    UI Elements
    Toolbar toolbar;
    EditText nipTextField, passwordTextField;
    LinearLayout loginForm;
    ProgressBar loginProgressCircular;
    Button loginButton;

//    HTTP Controllers
    AuthController mAuthController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EnvironmentVariables.setBaseURL(this, "http://34312410.ngrok.io/");
        setContentView(R.layout.activity_main);

//        Initializing Auth Controller
        mAuthController = new AuthController(this, this);

//        Initializing View
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        loginForm = (LinearLayout) findViewById(R.id.form_login);
        nipTextField = (EditText) findViewById(R.id.textfield_nip);
        passwordTextField = (EditText) findViewById(R.id.textfield_password);
        loginProgressCircular = (ProgressBar) findViewById(R.id.progress_circular_login);
        loginButton = (Button) findViewById(R.id.button_login);

        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String userToken = EnvironmentVariables.getUserToken(this);
        if(userToken != EnvironmentVariables.NULL){

            User currentUser = new User();
            currentUser.setApiToken(userToken);

            mAuthController.refreshToken(currentUser);

            loginProgressCircular.setVisibility(View.VISIBLE);
            loginForm.setVisibility(View.GONE);
        } else {
            loginForm.setVisibility(View.VISIBLE);
            loginProgressCircular.setVisibility(View.GONE);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = new User();
                    user.setNip(nipTextField.getText().toString());
                    user.setPassword(passwordTextField.getText().toString());

                    mAuthController.authenticate(user);
                }
            });
        }
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if(response.isSuccessful()){
            User user = response.body();

            EnvironmentVariables.setUserToken(this, user.getApiToken());
            Intent i = new Intent(this, PeminjamanActivity.class);
            startActivity(i);
            finish();
        } else {
            Log.e("RequestFailed", response.errorBody().toString());
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        t.printStackTrace();
    }
}
