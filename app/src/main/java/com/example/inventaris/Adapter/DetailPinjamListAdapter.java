package com.example.inventaris.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inventaris.Model.DetailPinjam.DetailPinjam;
import com.example.inventaris.R;
import com.squareup.picasso.Picasso;

public class DetailPinjamListAdapter extends RecyclerView.Adapter<DetailPinjamListAdapter.DetailPinjamListViewHolder>{

    DetailPinjam mDetailPinjam;

    public DetailPinjamListAdapter(DetailPinjam detailPinjam){
        mDetailPinjam = detailPinjam;
    }

    @NonNull
    @Override
    public DetailPinjamListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_detailpinjam, viewGroup, false);

        DetailPinjamListViewHolder detailPinjamListViewHolder = new DetailPinjamListViewHolder(cardView);
        return detailPinjamListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailPinjamListViewHolder detailPinjamListViewHolder, int i) {
        detailPinjamListViewHolder.detailPinjamTitleTextView
                .setText(mDetailPinjam.getPeminjamanDetails().get(i).getNama());
        detailPinjamListViewHolder.detailPinjamJenisEditText
                .setText(mDetailPinjam.getPeminjamanDetails().get(i).getNamaJenis());
        detailPinjamListViewHolder.detailPinjamRuangEditText
                .setText(mDetailPinjam.getPeminjamanDetails().get(i).getNamaRuang());
        detailPinjamListViewHolder.detailPinjamTakeawayEditText
                .setText(String.valueOf(mDetailPinjam.getPeminjamanDetails().get(i).getJumlah()));

        Picasso.get().load(mDetailPinjam.getPeminjamanDetails().get(i).getUrlGambar())
                .into(detailPinjamListViewHolder.detailPinjamImageView);
    }

    @Override
    public int getItemCount() {
        return mDetailPinjam.getPeminjamanDetails().size();
    }

    public static class DetailPinjamListViewHolder extends RecyclerView.ViewHolder{

        public ImageView detailPinjamImageView;
        public TextView detailPinjamTitleTextView;
        public EditText detailPinjamJenisEditText, detailPinjamRuangEditText, detailPinjamTakeawayEditText;

        public DetailPinjamListViewHolder(CardView viewGroup){
            super(viewGroup);
            detailPinjamImageView = (ImageView) viewGroup.findViewById(R.id.imageview_takeaway);
            detailPinjamTitleTextView = (TextView) viewGroup.findViewById(R.id.textview_inventarisname);
            detailPinjamRuangEditText = (EditText) viewGroup.findViewById(R.id.textfield_ruang_readonly);
            detailPinjamJenisEditText = (EditText) viewGroup.findViewById(R.id.textfield_jenis_readonly);
            detailPinjamTakeawayEditText = (EditText) viewGroup.findViewById(R.id.textfield_takeaway_readonly);
        }
    }
}
