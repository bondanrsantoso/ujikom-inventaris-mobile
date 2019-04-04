package com.example.inventaris;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inventaris.Adapter.EventListeners.OnButtonClickListener;
import com.example.inventaris.Adapter.EventListeners.OnInputChangeListener;
import com.example.inventaris.Adapter.InventarisListAdapter;
import com.example.inventaris.Adapter.TakeawayListAdapter;
import com.example.inventaris.Http.Controller.InventarisController;
import com.example.inventaris.Http.Controller.PeminjamanController;
import com.example.inventaris.Model.Inventaris.DataItem;
import com.example.inventaris.Model.Inventaris.Inventaris;
import com.example.inventaris.Model.Peminjaman.AddRequest;

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

    private String[] mSelectedID;

    EditText startDateEditText, endDateEditText;
    RecyclerView takeAwayListView;
    RecyclerView.Adapter takeAwayListViewAdapter = null;
    RecyclerView.LayoutManager linearLayoutManager;
    Button pinjamButton;
    Toolbar toolbar;

    Calendar startDateCalendar, endDateCalendar;
    SimpleDateFormat dateFormatter;

    PeminjamanController peminjamanController;

    InventarisController inventarisController;
    Inventaris mInventaris;
    List<Integer> mTakeaways;
    boolean isStartDate = false;
    boolean allValid = true;

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
            mSelectedID = getArguments().getString(SELECTED_ID).split(";");
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

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            ((AppCompatActivity) getActivity()).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("Pinjam");
        toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        final Date today = new Date(System.currentTimeMillis());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);
        startDateCalendar = new GregorianCalendar();
        startDateCalendar.setTime(today);
        endDateCalendar = new GregorianCalendar();
        endDateCalendar.setTime(today);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        inventarisController = new InventarisController(getContext(), this);
        peminjamanController = new PeminjamanController(getContext());

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
                inventarisController.getSpecifics(mSelectedID, dateFormatter.format(startDateCalendar.getTime()),
                        dateFormatter.format(endDateCalendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));

        startDateEditText = (EditText) getView().findViewById(R.id.textfield_starddate);
        startDateEditText.setText(startDateCalendar.get(Calendar.DATE)
                + " " +
                months[startDateCalendar.get(Calendar.MONTH)]
                + " " +
                startDateCalendar.get(Calendar.YEAR));
        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = true;
                datePickerDialog.show();
            }
        });

        endDateEditText = (EditText) getView().findViewById(R.id.textfield_enddate);
        endDateEditText.setText(endDateCalendar.get(Calendar.DATE)
                + " " +
                months[endDateCalendar.get(Calendar.MONTH)]
                + " " +
                endDateCalendar.get(Calendar.YEAR));
        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = false;
                datePickerDialog.show();
            }
        });

        final Callback<Object> peminjamanCallback = new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Peminjaman Gagal, Silahkan coba lagi", Toast.LENGTH_LONG);
                }

                loadFragment(new BarangFragment());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                t.printStackTrace();
            }
        };

        pinjamButton = (Button) getView().findViewById(R.id.button_pinjam) ;
        pinjamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRequest peminjamanAddRequest = new AddRequest();
                peminjamanAddRequest.setAmount(mTakeaways);
                List<String> inventarisIDs = new ArrayList<>();
                for (DataItem takeaway : mInventaris.getData()) {
                    inventarisIDs.add(String.valueOf(takeaway.getIdInventaris()));
                }
                peminjamanAddRequest.setInventarisId(inventarisIDs);
                peminjamanAddRequest.setTakeawayDate(dateFormatter.format(startDateCalendar.getTime()));
                peminjamanAddRequest.setReturnDate(dateFormatter.format(endDateCalendar.getTime()));

                peminjamanController.add(peminjamanAddRequest, peminjamanCallback);
            }
        });

        takeAwayListView = (RecyclerView) getView().findViewById(R.id.recyclerview_takeaway);
        linearLayoutManager = new LinearLayoutManager(getContext());
        takeAwayListView.setLayoutManager(linearLayoutManager);
        takeAwayListView.setHasFixedSize(false);

        inventarisController.getSpecifics(mSelectedID, dateFormatter.format(startDateCalendar.getTime()),
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

            if(takeAwayListViewAdapter == null){
                mTakeaways = new ArrayList<Integer>();
                for (DataItem dataItem: mInventaris.getData()) {
                    mTakeaways.add(0);
                }

                takeAwayListViewAdapter = new TakeawayListAdapter(mInventaris, mTakeaways, new OnInputChangeListener() {
                    @Override
                    public void onChange(View v, int position) {
                        EditText takeawayTextField = (EditText) v;
                        String takeaway = takeawayTextField.getText().toString();
                        takeaway = takeaway.equals("")? "0": takeaway;

                        int takeawayAmount = Integer.parseInt(takeaway);
                        int stok = mInventaris.getData().get(position).getStok();
                        if (takeawayAmount > stok) {
                            takeawayAmount = stok;
                            takeawayTextField.setText(String.valueOf(stok));
                        }

                        mTakeaways.set(position, takeawayAmount);
                    }
                }, new OnButtonClickListener() {
                    @Override
                    public void onButtonClick(View v, int position) {
                        mInventaris.getData().remove(position);
                        mTakeaways.remove(position);

                        ((TakeawayListAdapter) takeAwayListViewAdapter).updateDataSet(mInventaris, mTakeaways);

                        if(mInventaris.getData().size() == 0){
                            Toast.makeText(getContext(), "Tidak ada barang untuk dipinjam, dibatalkan", Toast.LENGTH_LONG).show();
                            loadFragment(new BarangFragment());
                        }

                        mSelectedID = new String[mInventaris.getData().size()];

                        int i = 0;
                        for (DataItem dataItem : mInventaris.getData()) {
                            mSelectedID[i] = String.valueOf(dataItem.getIdInventaris());
                            i++;
                        }
                    }
                });
                takeAwayListView.setAdapter(takeAwayListViewAdapter);
            } else {
                int i = 0;
                for (DataItem takeaway :mInventaris.getData()) {
                    if(takeaway.getStok() < mTakeaways.get(i)){
                        mTakeaways.set(i, takeaway.getStok());
                    }
                    i++;
                }
                ((TakeawayListAdapter) takeAwayListViewAdapter).updateDataSet(mInventaris, mTakeaways);
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
