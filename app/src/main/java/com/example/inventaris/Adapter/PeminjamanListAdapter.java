package com.example.inventaris.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.inventaris.Model.Peminjaman.DataItem;
import com.example.inventaris.Model.Peminjaman.Peminjaman;
import com.example.inventaris.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeminjamanListAdapter extends RecyclerView.Adapter<PeminjamanListAdapter.PeminjamanListViewHolder> {
    private Peminjaman mPeminjaman;

    final String[] months = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

    public static class PeminjamanListViewHolder extends RecyclerView.ViewHolder{
        public TextView peminjamanTitleTextView;
        public TextView peminjamanStatusTextView;
        public CircleImageView peminjamanCircleImageView;

        public PeminjamanListViewHolder(LinearLayout linearLayout){
            super(linearLayout);
            peminjamanTitleTextView = (TextView) linearLayout.findViewById(R.id.peminjaman_title_textView);
            peminjamanStatusTextView = (TextView) linearLayout.findViewById(R.id.peminjaman_status_textView);
            peminjamanCircleImageView = (CircleImageView) linearLayout.findViewById(R.id.peminjaman_imageview);
        }
    }

    public PeminjamanListAdapter(Peminjaman dataset){
        mPeminjaman = dataset;
    }

    @Override
    public PeminjamanListAdapter.PeminjamanListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_peminjaman, parent,false);

        PeminjamanListViewHolder viewHolder = new PeminjamanListViewHolder(linearLayout);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PeminjamanListViewHolder peminjamanListViewHolder, int i) {
        DataItem peminjamanData = mPeminjaman.getData().get(i);
        int takeawayTimestamp = mPeminjaman.getData().get(i).getTanggalPinjam();
        int returnTimestamp = mPeminjaman.getData().get(i).getTanggalKembali();
        long today = new Date().getTime() / 1000;

        Context ctx;
        if(peminjamanData.isKembali()){
            peminjamanListViewHolder.peminjamanCircleImageView.setImageResource(R.drawable.ic_check_white);
            peminjamanListViewHolder.peminjamanCircleImageView.setCircleBackgroundColor(Color.parseColor("#5994ed"));
        } else if(today > takeawayTimestamp && today < returnTimestamp){
            peminjamanListViewHolder.peminjamanCircleImageView.setImageResource(R.drawable.ic_in_use);
        } else if(today > returnTimestamp){
            peminjamanListViewHolder.peminjamanCircleImageView.setImageResource(R.drawable.ic_warning_white);
            peminjamanListViewHolder.peminjamanCircleImageView.setCircleBackgroundColor(Color.parseColor("#de3c4b"));
        } else {
            peminjamanListViewHolder.peminjamanCircleImageView.setImageResource(R.drawable.ic_pending);
            peminjamanListViewHolder.peminjamanCircleImageView.setCircleBackgroundColor(Color.parseColor("#7cafc4"));
        }

        Calendar startCal = new GregorianCalendar();
        startCal.setTime(new Date((long) takeawayTimestamp * 1000));

        Calendar endCal = new GregorianCalendar();
        endCal.setTime(new Date((long) returnTimestamp * 1000));

        peminjamanListViewHolder.peminjamanTitleTextView
                .setText(startCal.get(Calendar.DATE) + " " + months[startCal.get(Calendar.MONTH)] + " " + startCal.get(Calendar.YEAR)
                        + " - " +
                        endCal.get(Calendar.DATE) + " " + months[endCal.get(Calendar.MONTH)] + " " + endCal.get(Calendar.YEAR) );

        peminjamanListViewHolder.peminjamanStatusTextView.setText(mPeminjaman.getData().get(i).getStatus());
    }

    @Override
    public int getItemCount() {
        return mPeminjaman.getData().size();
    }
}
