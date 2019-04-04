package com.example.inventaris;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.inventaris.Adapter.EventListeners.OnItemClickListener;
import com.example.inventaris.Adapter.PeminjamanListAdapter;
import com.example.inventaris.Http.Controller.DetailPinjamFragment;
import com.example.inventaris.Http.Controller.PeminjamanController;
import com.example.inventaris.Model.Peminjaman.Peminjaman;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PeminjamanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PeminjamanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PeminjamanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Toolbar toolbar;
    RecyclerView peminjamanRecyclerView;
    RecyclerView.Adapter peminjamanListAdapter;
    RecyclerView.LayoutManager peminjamanListLayoutManager;
    ProgressBar peminjamanLoading;

    PeminjamanController peminjamanController;
    private Peminjaman mPeminjaman;

    public PeminjamanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PeminjamanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PeminjamanFragment newInstance(String param1, String param2) {
        PeminjamanFragment fragment = new PeminjamanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_peminjaman, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("Peminjaman");
        toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        peminjamanLoading = (ProgressBar) getView().findViewById(R.id.peminjamanlist_loading);
        peminjamanLoading.setVisibility(View.VISIBLE);

        peminjamanRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView_peminjaman);
        peminjamanRecyclerView.setHasFixedSize(false);

        peminjamanListLayoutManager = new LinearLayoutManager(this.getActivity());
        peminjamanRecyclerView.setLayoutManager(peminjamanListLayoutManager);

        peminjamanController = new PeminjamanController(this.getActivity());
        peminjamanController.get(0, 100, new Callback<Peminjaman>() {
            @Override
            public void onResponse(Call<Peminjaman> call, Response<Peminjaman> response) {
//                Log.d("RETORFIT", "onResponse: " + response.body().toString());
                mPeminjaman = response.body();
                peminjamanListAdapter = new PeminjamanListAdapter(mPeminjaman, new OnItemClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, DetailPinjamFragment
                                        .newInstance(String.valueOf(mPeminjaman.getData().get(position)
                                                .getIdPeminjaman()))).commit();
                    }
                });
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
