package com.example.inventaris.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inventaris.Adapter.EventListeners.OnButtonClickListener;
import com.example.inventaris.Adapter.EventListeners.OnInputChangeListener;
import com.example.inventaris.Model.Inventaris.DataItem;
import com.example.inventaris.Model.Inventaris.Inventaris;
import com.example.inventaris.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TakeawayListAdapter extends RecyclerView.Adapter<TakeawayListAdapter.TakeawayListViewHolder> {

    Inventaris mInventaris;
    OnInputChangeListener mOnInputChangeListener;


    public static class TakeawayListViewHolder extends RecyclerView.ViewHolder{

        public ImageView inventarisImageView;
        public TextView inventarisTitleTextView;
        public EditText inventarisStokEditText, inventarisTakeawayEditText;
        public Button inventarisTakeawayButton;

        public TakeawayListViewHolder(CardView viewGroup){
            super(viewGroup);
            inventarisImageView = (ImageView) viewGroup.findViewById(R.id.imageview_takeaway);
            inventarisStokEditText = (EditText) viewGroup.findViewById(R.id.textfield_stok);
            inventarisTitleTextView = (TextView) viewGroup.findViewById(R.id.textview_inventarisname);
            inventarisTakeawayEditText = (EditText) viewGroup.findViewById(R.id.textfield_takeaway);
            inventarisTakeawayButton = (Button) viewGroup.findViewById(R.id.button_takeawaydelete);
        }
    }

    public TakeawayListAdapter(Inventaris dataset, OnInputChangeListener onInputChangeListener){
        this.mInventaris = dataset;
        mOnInputChangeListener = onInputChangeListener;
    }

    public void updateDataSet(Inventaris dataset){
        this.mInventaris = dataset;
        this.notifyDataSetChanged();
    }

    public void appendDataSet(Inventaris dataset){
        List<DataItem> dataItems = this.mInventaris.getData();
        dataItems.addAll(dataset.getData());
        mInventaris.setData(dataItems);
        this.notifyItemInserted(mInventaris.getData().size() - 1);
    }

    @NonNull
    @Override
    public TakeawayListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView inventarisCard = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_takeaway, viewGroup, false);

        TakeawayListViewHolder listViewHolder = new TakeawayListViewHolder(inventarisCard);

        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TakeawayListViewHolder TakeawayListViewHolder, int i) {
        DataItem inventarisData = mInventaris.getData().get(i);

        TakeawayListViewHolder.inventarisTitleTextView.setText(inventarisData.getNama());
        TakeawayListViewHolder.inventarisStokEditText.setText(String.valueOf(inventarisData.getStok()));
        TakeawayListViewHolder.inventarisTakeawayEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mOnInputChangeListener.onChange(TakeawayListViewHolder.inventarisTakeawayEditText, TakeawayListViewHolder.getAdapterPosition());
            }
        });
        Picasso.get().load(inventarisData.getUrlGambar()).into(TakeawayListViewHolder.inventarisImageView);
    }

    @Override
    public int getItemCount() {
        return mInventaris.getData().size();
    }

}
