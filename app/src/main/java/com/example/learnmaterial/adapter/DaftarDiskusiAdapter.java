package com.example.learnmaterial.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.learnmaterial.R;
import com.example.learnmaterial.model.Diskusi;

import java.util.ArrayList;

public class DaftarDiskusiAdapter extends RecyclerView.Adapter<DaftarDiskusiAdapter.DiskusiVH> {
    private ArrayList<Diskusi> arrDiskusi = new ArrayList<>();
    private OnItemClick onItemClick;
    private Context context;

    public DaftarDiskusiAdapter(Context context) {
        this.context = context;
    }

    public void setDiskusi(ArrayList<Diskusi> arrDiskusi) {
        this.arrDiskusi.clear();
        this.arrDiskusi = arrDiskusi;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(DaftarDiskusiAdapter.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public DiskusiVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_diskusi, viewGroup, false);
        return new DiskusiVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DiskusiVH diskusiVH, int i) {
        Diskusi d = arrDiskusi.get(i);
        diskusiVH.tvJudul.setText(d.getJudul());
        diskusiVH.tvLike.setText(d.getJlhLike() + " Likes");
        diskusiVH.tvDiskusi.setText(d.getJlhDiskusi() + " Discussion");

        if (d.getIsLike() == 1) {
            diskusiVH.btnLike.setImageDrawable(context.getDrawable(R.drawable.ic_like_selected));
        } else {
            diskusiVH.btnLike.setImageDrawable(context.getDrawable(R.drawable.ic_like));
        }

        diskusiVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClicked(diskusiVH.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrDiskusi.size();
    }

    public class DiskusiVH extends RecyclerView.ViewHolder {
        private TextView tvJudul;
        private TextView tvLike;
        private TextView tvDiskusi;
        private ImageButton btnLike;

        public DiskusiVH(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tv_judul_diskusi);
            tvLike = itemView.findViewById(R.id.tv_jlh_like_diskusi);
            tvDiskusi = itemView.findViewById(R.id.tv_jlh_reply_diskusi);
            btnLike = itemView.findViewById(R.id.btn_like_diskusi);
        }
    }

    public interface OnItemClick {
        void onItemClicked(int position);
    }
}
