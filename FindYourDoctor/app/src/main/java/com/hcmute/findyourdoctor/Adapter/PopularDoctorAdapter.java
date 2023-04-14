package com.hcmute.findyourdoctor.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hcmute.findyourdoctor.Domain.PopularDoctorDomain;
import com.hcmute.findyourdoctor.Listener.OnDocterCardClickListener;
import com.hcmute.findyourdoctor.Model.Doctor;
import com.hcmute.findyourdoctor.R;

import java.util.List;

public class PopularDoctorAdapter extends RecyclerView.Adapter<PopularDoctorAdapter.PopularDoctorViewHolder> {
    private List<Doctor> mListPopularDoctor;
    private OnDocterCardClickListener listener;

    public void setOnDocterCardClickListener(OnDocterCardClickListener listener) {
        this.listener = listener;
    }

    public PopularDoctorAdapter(List<Doctor> mListPopularDoctor) {
        this.mListPopularDoctor = mListPopularDoctor;
    }


    @NonNull
    @Override
    public PopularDoctorAdapter.PopularDoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_popular_doctor, parent ,false);

        return new PopularDoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularDoctorAdapter.PopularDoctorViewHolder holder, int position) {
        Doctor popularDoctorDomain_pt = mListPopularDoctor.get(position);
        if (popularDoctorDomain_pt == null)
        {
            return;
        }
        holder.tvName.setText(popularDoctorDomain_pt.getName());
        holder.tvSpecialist.setText(popularDoctorDomain_pt.getSpecialist());
        holder.ratingBar.setStepSize(0.1f);
        holder.ratingBar.setRating(popularDoctorDomain_pt.getRating());

        Glide.with(holder.layoutMain.getContext())
                .load(popularDoctorDomain_pt.getAvatarUrl())
                .into(holder.ivDoctorAvatar);

        holder.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onDoctorCardClick(popularDoctorDomain_pt);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListPopularDoctor != null){
            return  mListPopularDoctor.size();
        }
        else
        {
            return 0;
        }
    }

    public static class PopularDoctorViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvSpecialist;
        private ImageView ivDoctorAvatar;
        private RatingBar ratingBar;
        private LinearLayout layoutMain;


        public PopularDoctorViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name_popular_doctor);
            tvSpecialist = itemView.findViewById(R.id.tv_specialist_popular_doctor);
            ivDoctorAvatar = itemView.findViewById(R.id.iv_avatar_popular_doctor);
            ratingBar = itemView.findViewById(R.id.rtb_popular_doctor);
            layoutMain = itemView.findViewById(R.id.layout_popular_doctor);
        }
    }
}