package com.example.inventaris;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inventaris.Adapter.EventListeners.OnInputChangeListener;
import com.example.inventaris.Adapter.InventarisListAdapter;
import com.example.inventaris.Adapter.TakeawayListAdapter;
import com.example.inventaris.Http.Controller.InventarisController;
import com.example.inventaris.Model.Inventaris.DataItem;
import com.example.inventaris.Model.Inventaris.Inventaris;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TakeawayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TakeawayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TakeawayFragment extends Fragment implements Callback<Inventaris>{
    private static final String SELECTED_ID = "selectedId";

    private String mSelectedID;

    EditText startDateEditText, endDateEditText;
    RecyclerView takeAwayListView;
    RecyclerView.Adapter takeAwayListViewAdapter = null;
    RecyclerView.LayoutManager linearLayoutManager;

    Calendar startDateCalendar, endDateCalendar;
    SimpleDateFormat dateFormatter;

    InventarisController inventarisController;
    Inventaris mInventaris;
    List<Integer> mTakeaways;
    boolean isStartDate = false;

    String[] months = {
            "Januari",
            "Februari",
            "Maret",
            "April",
            "Mei",
            "Juni",
            "Juli",
            "Agustus",
            "September",
            "Oktober",
            "November",
            "Desember"
    };

    DatePickerDialog datePickerDialog;

    private OnFragmentInteractionListener mListener;

    public TakeawayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment TakeawayFragment.
     */
    public static TakeawayFragment newInstance(String param1) {
        TakeawayFragment fragment = new TakeawayFragment();
        Bundle args = new Bundle();
        args.putString(SELECTED_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSelectedID = getArguments().getString(SELECTED_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_takeaway, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        final Date today = new Date(System.currentTimeMillis());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);
        startDateCalendar = new GregorianCalendar();
        startDateCalendar.setTime(today);
        endDateCalendar = new GregorianCalendar();
        endDateCalendar.setTime(today);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(isStartDate){
                    startDateCalendar.set(year, month, dayOfMonth);
                    startDateEditText.setText(dayOfMonth + " " + months[month] + " " + year);

                    if(startDateCalendar.getTimeInMillis() < today.getTime() ||
                            (startDateCalendar.getTimeInMillis() > endDateCalendar.getTimeInMillis() &&
                                    endDateEditText.getText().length() > 0)){
                        Toast.makeText(getContext(), "Tanggal Pinjam tidak valid", Toast.LENGTH_SHORT).show();
                        datePickerDialog.show();
                    }
                } else{
                    endDateCalendar.set(year, month,dayOfMonth);
                    endDateEditText.setText(dayOfMonth + " " + months[month] + " " + year);

                    if((startDateCalendar.getTimeInMillis() > endDateCalendar.getTimeInMillis() &&
                                    startDateEditText.getText().length() > 0)) {
                        Toast.makeText(getContext(), "Tanggal Kembali tidak valid", Toast.LENGTH_SHORT).show();
                        datePickerDialog.show();
                    }
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));

        startDateEditText = (EditText) getView().findViewById(R.id.textfield_starddate);

        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = true;
                datePickerDialog.show();
            }
        });

        endDateEditText = (EditText) getView().findViewById(R.id.textfield_enddate);
        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = false;
                datePickerDialog.show();
            }
        });

        takeAwayListView = (RecyclerView) getView().findViewById(R.id.recyclerview_takeaway);
        linearLayoutManager = new LinearLayoutManager(getContext());
        takeAwayListView.setLayoutManager(linearLayoutManager);
        takeAwayListView.setHasFixedSize(false);

        inventarisController = new InventarisController(getContext(), this);
        String[] selectedIDs = mSelectedID.split(";");
        inventarisController.getSpecifics(selectedIDs, dateFormatter.format(startDateCalendar.getTime()),
                dateFormatter.format(endDateCalendar.getTime()));
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
    public void onResponse(Call<Inventaris> call, Response<Inventaris> response) {
        if(response.isSuccessful()){
            mInventaris = response.body();
            mTakeaways = new ArrayList<Integer>();
            for (DataItem dataItem: mInventaris.getData()) {
                mTakeaways.add(0);
            }
            if(takeAwayListViewAdapter == null){
                takeAwayListViewAdapter = new TakeawayListAdapter(mInventaris, new OnInputChangeListener() {
                    @Override
                    public void onChange(View v, int position) {
                        int takeawayAmount = Integer.parseInt(((EditText) v).getText().toString());
                        int stok = mInventaris.getData().get(position).getStok();
                        if(takeawayAmount > stok){
                            takeawayAmount = stok;
                            ((EditText) v).setText(String.valueOf(stok));
                        }
                        mTakeaways.set(position, takeawayAmount);
                    }
                });
                takeAwayListView.setAdapter(takeAwayListViewAdapter);
            } else {
                ((TakeawayListAdapter) takeAwayListViewAdapter).updateDataSet(mInventaris);
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
