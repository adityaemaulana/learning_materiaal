package com.example.learnmaterial.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.learnmaterial.R;
import com.example.learnmaterial.model.Modul;

import java.util.ArrayList;

public class DaftarModulAdapter extends RecyclerView.Adapter<DaftarModulAdapter.ModulVH> {
    private ArrayList<Modul> arrModul = new ArrayList<>();
    private Context context;
    private OnItemClick onItemClick;

    public DaftarModulAdapter(Context context) {
        this.context = context;
    }

    public void setModul(ArrayList<Modul> arrModul) {
        this.arrModul.clear();
        this.arrModul = arrModul;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }


    @NonNull
    @Override
    public ModulVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_modul, viewGroup, false);
        return new ModulVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ModulVH modulVH, int i) {
        Modul m = arrModul.get(i);
        modulVH.tvNama.setText(m.getNama());
        modulVH.tvPertemuan.setText(m.getPertemuan());
        modulVH.tvSlide.setText(m.getJlhSlide() + " Slide");
        modulVH.tvDiskusi.setText(m.getJlhDiskusi() + " Discussion");

        modulVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClicked(modulVH.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrModul.size();
    }

    public class ModulVH extends RecyclerView.ViewHolder {
        private TextView tvNama;
        private TextView tvPertemuan;
        private TextView tvSlide;
        private TextView tvDiskusi;

        public ModulVH(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama_modul);
            tvPertemuan = itemView.findViewById(R.id.tv_pertemuan_modul);
            tvSlide = itemView.findViewById(R.id.tv_jlh_slide_modul);
            tvDiskusi = itemView.findViewById(R.id.tv_jlh_diskusi_modul);
        }
    }

    public interface OnItemClick {
        void onItemClicked(int position);
    }
}
