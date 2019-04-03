package com.example.inventaris.Adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.inventaris.Adapter.EventListeners.OnButtonClickListener;
import com.example.inventaris.Model.Inventaris.DataItem;
import com.example.inventaris.Model.Inventaris.Inventaris;
import com.example.inventaris.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InventarisListAdapter extends RecyclerView.Adapter<InventarisListAdapter.InventarisListViewHolder> {

    Inventaris mInventaris;
    OnButtonClickListener onButtonClickListener;

    public static class InventarisListViewHolder extends RecyclerView.ViewHolder{

        public ImageView inventarisImageView;
        public TextView inventarisTitleTextView, inventarisDetailTextView;
        //        EditText inventarisStokEditText, inventarisTakeawayEditText;
        public Button inventarisTakeawayButton;

        public InventarisListViewHolder(CardView viewGroup){
            super(viewGroup);
            inventarisImageView = (ImageView) viewGroup.findViewById(R.id.imageview_inventaris);
            inventarisTitleTextView = (TextView) viewGroup.findViewById(R.id.textview_inventarisname);
            inventarisDetailTextView = (TextView) viewGroup.findViewById(R.id.textview_inventarisdetail);
//            inventarisStokEditText = (EditText) viewGroup.findViewById(R.id.textfield_stok);
//            inventarisTakeawayEditText = (EditText) viewGroup.findViewById(R.id.textfield_takeaway);
            inventarisTakeawayButton = (Button) viewGroup.findViewById(R.id.button_inventaristakeaway);
        }
    }

    public InventarisListAdapter(Inventaris dataset, OnButtonClickListener onButtonClickListener){
        this.mInventaris = dataset;
        this.onButtonClickListener = onButtonClickListener;
    }

    public void appendDataSet(Inventaris dataset){
        List<DataItem> dataItems = this.mInventaris.getData();
        dataItems.addAll(dataset.getData());
        mInventaris.setData(dataItems);
        this.notifyItemInserted(mInventaris.getData().size() - 1);
    }

    @NonNull
    @Override
    public InventarisListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView inventarisCard = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_inventaris, viewGroup, false);

        InventarisListViewHolder listViewHolder = new InventarisListViewHolder(inventarisCard);

        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final InventarisListViewHolder inventarisListViewHolder, int i) {
        DataItem inventarisData = mInventaris.getData().get(i);
        String inventarisDetail =
                "Jenis: " + inventarisData.getNamaJenis() + "\n" +
                "Ruangan: " + inventarisData.getNamaRuang() + "\n\n" + inventarisData.getKeterangan();

        inventarisListViewHolder.inventarisTitleTextView.setText(inventarisData.getNama());
        inventarisListViewHolder.inventarisDetailTextView.setText(inventarisDetail);
//        inventarisListViewHolder.inventarisImageView;
        Picasso.get().load(inventarisData.getUrlGambar()).into(inventarisListViewHolder.inventarisImageView);
        inventarisListViewHolder.inventarisTakeawayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onButtonClick(v, inventarisListViewHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mInventaris.getData().size();
    }

}
