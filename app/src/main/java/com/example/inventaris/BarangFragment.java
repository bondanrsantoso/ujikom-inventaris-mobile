package com.example.inventaris;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inventaris.Adapter.InventarisListAdapter;
import com.example.inventaris.Http.Controller.InventarisController;
import com.example.inventaris.Model.Inventaris.Inventaris;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BarangFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BarangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarangFragment extends Fragment implements Callback<Inventaris> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Toolbar toolbar;
    RecyclerView inventarisRecyclerView;
    RecyclerView.Adapter inventarisListViewAdapter;
    RecyclerView.LayoutManager inventarisListViewLayoutManager;

    InventarisController inventarisController;

    private OnFragmentInteractionListener mListener;

    public BarangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BarangFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BarangFragment newInstance(String param1, String param2) {
        BarangFragment fragment = new BarangFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_barang, container, false);
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
        getActivity().setTitle("Barang");

        toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        inventarisController = new InventarisController(getActivity(), this);
        inventarisController.get(0, 100, new Callback<Inventaris>() {
            @Override
            public void onResponse(Call<Inventaris> call, Response<Inventaris> response) {
                if(response.isSuccessful()){
                    Inventaris inventaris = response.body();
                    inventarisRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerview_inventaris);
                    inventarisRecyclerView.setHasFixedSize(false);

                    inventarisListViewLayoutManager = new LinearLayoutManager(BarangFragment.this.getActivity());
                    inventarisRecyclerView.setLayoutManager(inventarisListViewLayoutManager);

                    inventarisListViewAdapter = new InventarisListAdapter(response.body());
                    inventarisRecyclerView.setAdapter(inventarisListViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<Inventaris> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResponse(Call<Inventaris> call, Response<Inventaris> response) {
        if(response.isSuccessful()){
            Inventaris inventaris = response.body();
            inventarisListViewAdapter = new InventarisListAdapter(inventaris);
            inventarisRecyclerView.setAdapter(inventarisListViewAdapter);
        }
    }

    @Override
    public void onFailure(Call<Inventaris> call, Throwable t) {

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
