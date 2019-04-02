package com.example.inventaris;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.example.inventaris.Adapter.PeminjamanListAdapter;
import com.example.inventaris.Http.Controller.PeminjamanController;
import com.example.inventaris.Model.Peminjaman.Peminjaman;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeminjamanActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView peminjamanRecyclerView;
    RecyclerView.Adapter peminjamanListAdapter;
    RecyclerView.LayoutManager peminjamanListLayoutManager;
    ProgressBar peminjamanLoading;

    PeminjamanController peminjamanController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjaman);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        peminjamanLoading = (ProgressBar) findViewById(R.id.peminjamanlist_loading);
        peminjamanLoading.setVisibility(View.VISIBLE);

        peminjamanRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_peminjaman);
        peminjamanRecyclerView.setHasFixedSize(false);

        peminjamanListLayoutManager = new LinearLayoutManager(this);
        peminjamanRecyclerView.setLayoutManager(peminjamanListLayoutManager);

        peminjamanController = new PeminjamanController(this);
        peminjamanController.get(0, 100, new Callback<Peminjaman>() {
            @Override
            public void onResponse(Call<Peminjaman> call, Response<Peminjaman> response) {
                peminjamanListAdapter = new PeminjamanListAdapter(response.body());
                peminjamanRecyclerView.setAdapter(peminjamanListAdapter);
                peminjamanRecyclerView.setVisibility(View.VISIBLE);
                peminjamanLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Peminjaman> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
