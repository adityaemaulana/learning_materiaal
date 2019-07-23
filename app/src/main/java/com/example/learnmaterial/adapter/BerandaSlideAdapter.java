package com.example.learnmaterial.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.learnmaterial.R;
import com.example.learnmaterial.model.Slide;

import java.util.ArrayList;

public class BerandaSlideAdapter extends RecyclerView.Adapter<BerandaSlideAdapter.SlideVH> {
    private ArrayList<Slide> slides = new ArrayList<>();
    private OnItemSlideClick onItemSlideClick;
    private Context context;

    public BerandaSlideAdapter(Context context) {
        this.context = context;
    }

    public void setSlides(ArrayList<Slide> slides) {
        this.slides.clear();
        this.slides = slides;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemSlideClick onItemSlideClick) {
        this.onItemSlideClick = onItemSlideClick;
    }

    @NonNull
    @Override
    public SlideVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_beranda_slide, viewGroup, false);
        return new SlideVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SlideVH slideVH, int i) {
        Slide s = slides.get(i);
        slideVH.tvMK.setText(s.getMataKuliah());
        slideVH.tvJudul.setText(s.getJudul());
        slideVH.tvJurusan.setText(s.getJurusan());

//        Glide.with(context).load(s.getImage()).into(slideVH.ivImage);
//        To be updated
        Glide.with(context).load(ResourcesCompat.getDrawable(context.getResources(), R.drawable.logomateri, null)).into(slideVH.ivImage);

        slideVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSlideClick.onItemClicked(slideVH.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return slides.size();
    }

    public class SlideVH extends RecyclerView.ViewHolder {
        private TextView tvMK;
        private TextView tvJudul;
        private TextView tvJurusan;
        private ImageView ivImage;

        public SlideVH(@NonNull View itemView) {
            super(itemView);
            tvMK = itemView.findViewById(R.id.tv_beranda_slide_matakuliah);
            tvJudul = itemView.findViewById(R.id.tv_beranda_slide_judul);
            tvJurusan = itemView.findViewById(R.id.tv_beranda_slide_jurusan);
            ivImage = itemView.findViewById(R.id.iv_beranda_slide);
        }
    }

    public interface OnItemSlideClick {
        void onItemClicked(int position);
    }
}
