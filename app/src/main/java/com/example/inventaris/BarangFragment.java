package com.example.inventaris;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.inventaris.Adapter.EventListeners.OnButtonClickListener;
import com.example.inventaris.Adapter.InventarisListAdapter;
import com.example.inventaris.Http.Controller.InventarisController;
import com.example.inventaris.Model.Inventaris.DataItem;
import com.example.inventaris.Model.Inventaris.Inventaris;

import java.util.ArrayList;
import java.util.List;

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
    TextView selectedItemCountTextView;
    Button lanjutButton;

    Inventaris mInventaris = null;
    List<String> selectedID;
    int page = 0;
    int selectedItem = 0;

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
//        getActivity().setTitle("Barang");

        toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        selectedItemCountTextView = (TextView) getView().findViewById(R.id.textview_peminjamancount);
        lanjutButton = (Button) getView().findViewById(R.id.button_lanjut);

        lanjutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedIDs = TextUtils.join(";", selectedID);
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, TakeawayFragment.newInstance(selectedIDs))
                        .commit();
            }
        });

        inventarisRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview_inventaris);
        inventarisRecyclerView.setHasFixedSize(false);

        selectedID = new ArrayList<String>();

        inventarisListViewLayoutManager = new LinearLayoutManager(BarangFragment.this.getActivity());
        inventarisRecyclerView.setLayoutManager(inventarisListViewLayoutManager);

        inventarisController = new InventarisController(getActivity(), this);
        inventarisController.get(0, 100);
    }

    @Override
    public void onResponse(Call<Inventaris> call, Response<Inventaris> response) {
        if(response.isSuccessful()){
            if(mInventaris == null){
                mInventaris = response.body();
                inventarisListViewAdapter = new InventarisListAdapter(mInventaris, new OnButtonClickListener() {
                    @Override
                    public void onButtonClick(View v, int position) {
                        DataItem selectedItem = mInventaris.getData().get(position);
                        selectedItem.setAkanDipinjam(!selectedItem.isAkanDipinjam());
                        ((Button) v).setText(selectedItem.isAkanDipinjam()? R.string.batal_pinjam : R.string.pinjam);

                        BarangFragment.this.selectedItem += selectedItem.isAkanDipinjam()? 1 : -1;
                        BarangFragment.this.selectedItemCountTextView
                                .setText(getString(R.string.jumlah_pinjam_placeholder)
                                        .replace("0", String.valueOf(BarangFragment.this.selectedItem)));

                        BarangFragment.this.lanjutButton.setEnabled(BarangFragment.this.selectedItem > 0);
                        if(selectedItem.isAkanDipinjam()){
                            selectedID.add(String.valueOf(selectedItem.getIdInventaris()));
                        } else {
                            selectedID.remove(String.valueOf(selectedItem.getIdInventaris()));
                        }
                    }
                });
                inventarisRecyclerView.setAdapter(inventarisListViewAdapter);
            } else {
                List<DataItem> list = mInventaris.getData();
                list.addAll(response.body().getData());
                mInventaris.setData(list);
                ((InventarisListAdapter) inventarisListViewAdapter).appendDataSet(response.body());
            }
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
