package com.example.learnmaterial.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.learnmaterial.R;
import com.example.learnmaterial.model.Pelajaran;

import java.util.ArrayList;

public class DaftarPelajaranAdapter extends RecyclerView.Adapter<DaftarPelajaranAdapter.PelajaranVH> {
    private ArrayList<Pelajaran> arrPelajaran = new ArrayList<>();
    private Context context;
    private OnItemClick onItemClick;

    public DaftarPelajaranAdapter(Context context) {
        this.context = context;
    }

    public void setPelajaran(ArrayList<Pelajaran> arrPelajaran) {
        this.arrPelajaran.clear();
        this.arrPelajaran = arrPelajaran;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public PelajaranVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pelajaran, viewGroup, false);
        return new PelajaranVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PelajaranVH pelajaranVH, int i) {
        Pelajaran p = arrPelajaran.get(i);
        pelajaranVH.tvNama.setText(p.getNama());
        pelajaranVH.tvSlide.setText(p.getJlhSlide() + " Slide");
        pelajaranVH.tvDiskusi.setText(p.getJlhDiskusi() + " Discussion");

        pelajaranVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClicked(pelajaranVH.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrPelajaran.size();
    }

    public class PelajaranVH extends RecyclerView.ViewHolder {
        private TextView tvNama;
        private TextView tvSlide;
        private TextView tvDiskusi;

        public PelajaranVH(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama_pelajaran);
            tvSlide = itemView.findViewById(R.id.tv_jlh_slide_pelajaran);
            tvDiskusi = itemView.findViewById(R.id.tv_jlh_diskusi_pelajaran);
        }
    }

    public interface OnItemClick {
        void onItemClicked(int position);
    }
}
