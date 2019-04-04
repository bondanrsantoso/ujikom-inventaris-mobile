package com.example.inventaris.Http.Controller;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.inventaris.Adapter.DetailPinjamListAdapter;
import com.example.inventaris.Model.DetailPinjam.DetailPinjam;
import com.example.inventaris.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailPinjamFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailPinjamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailPinjamFragment extends Fragment implements Callback<DetailPinjam> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ID_PEMINJAMAN = "idpeminjaman";

    // TODO: Rename and change types of parameters
    private String idPeminjaman;
    private DetailPinjam mDetailPinjam;

    Toolbar toolbar;
    EditText namapegawaiTextField, startDateTextField, endDateTextField, statusTextField;
    LinearLayout container;
    ProgressBar loadingProgress;

    RecyclerView detailPinjamRecyclerView;
    RecyclerView.Adapter detailPinjamListAdapter;
    RecyclerView.LayoutManager linearLayoutManager;

    SimpleDateFormat dateFormatter;
    Calendar startDateCalendar, endDateCalendar;

    DetailPinjamController detailPinjamController;

    private OnFragmentInteractionListener mListener;

    public DetailPinjamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param idPeminjaman Parameter 1.
     * @return A new instance of fragment DetailPinjamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailPinjamFragment newInstance(String idPeminjaman) {
        DetailPinjamFragment fragment = new DetailPinjamFragment();
        Bundle args = new Bundle();
        args.putString(ID_PEMINJAMAN, idPeminjaman);
        fragment.setArguments(args);
        return fragment;
    }

    String parseDate(int day, int month, int year){
        final String[] months = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli",
            "Agustus", "September", "Oktober", "November", "Desember"};
        return String.valueOf(day) + " " + months[month] + " " + String.valueOf(year);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idPeminjaman = getArguments().getString(ID_PEMINJAMAN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_pinjam, container, false);
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
        toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle("Detail");
        container = (LinearLayout) getView().findViewById(R.id.content_container);
        loadingProgress = (ProgressBar) getView().findViewById(R.id.peminjamanlist_loading);
        loadingProgress.setVisibility(View.VISIBLE);
        container.setVisibility(View.GONE);

        namapegawaiTextField = (EditText) getView().findViewById(R.id.textfield_pegawainame);
        startDateTextField = (EditText) getView().findViewById(R.id.textfield_startdate);
        endDateTextField = (EditText) getView().findViewById(R.id.textfield_enddate);
        statusTextField = (EditText) getView().findViewById(R.id.textfield_status);
        linearLayoutManager = new LinearLayoutManager(getContext());

        detailPinjamRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview_detailpinjam);
        detailPinjamRecyclerView.setLayoutManager(linearLayoutManager);
        detailPinjamRecyclerView.setHasFixedSize(false);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        detailPinjamController = new DetailPinjamController(getContext(), this);
        detailPinjamController.get(idPeminjaman);

    }

    @Override
    public void onResponse(Call<DetailPinjam> call, Response<DetailPinjam> response) {
        if(response.isSuccessful()){
            mDetailPinjam = response.body();
            startDateCalendar = new GregorianCalendar();
            startDateCalendar.setTime(new Date((long) mDetailPinjam.getTanggalPinjam() * 1000));
            endDateCalendar = new GregorianCalendar();
            endDateCalendar.setTime(new Date((long) mDetailPinjam.getTanggalKembali() * 1000));

            startDateTextField.setText(
                    parseDate(
                        startDateCalendar.get(Calendar.DATE),
                        startDateCalendar.get(Calendar.MONTH),
                        startDateCalendar.get(Calendar.YEAR)
                    )
            );

            endDateTextField.setText(
                    parseDate(
                            endDateCalendar.get(Calendar.DATE),
                            endDateCalendar.get(Calendar.MONTH),
                            endDateCalendar.get(Calendar.YEAR)
                    )
            );

            namapegawaiTextField.setText(mDetailPinjam.getNamaPegawai());
            statusTextField.setText(mDetailPinjam.getStatus());

            detailPinjamListAdapter = new DetailPinjamListAdapter(mDetailPinjam);
            detailPinjamRecyclerView.setAdapter(detailPinjamListAdapter);
            container.setVisibility(View.VISIBLE);
            loadingProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure(Call<DetailPinjam> call, Throwable t) {

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
