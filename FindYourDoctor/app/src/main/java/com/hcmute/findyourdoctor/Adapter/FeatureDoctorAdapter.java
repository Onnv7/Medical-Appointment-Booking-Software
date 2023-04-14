package com.hcmute.findyourdoctor.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hcmute.findyourdoctor.Domain.FeatureDoctorDomain;
import com.hcmute.findyourdoctor.Listener.OnDocterCardClickListener;
import com.hcmute.findyourdoctor.Model.Doctor;
import com.hcmute.findyourdoctor.R;

import java.util.List;

public class FeatureDoctorAdapter extends RecyclerView.Adapter<FeatureDoctorAdapter.FeatureDoctorViewHolder> {
    private List<Doctor> mListFeatureDoctor;
    private OnDocterCardClickListener listener;

    public void setOnDocterCardClickListener(OnDocterCardClickListener listener) {
        this.listener = listener;
    }

    public FeatureDoctorAdapter(List<Doctor> mListFeatureDoctor) {
        this.mListFeatureDoctor = mListFeatureDoctor;
    }


    @NonNull
    @Override
    public FeatureDoctorAdapter.FeatureDoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feature_doctor, parent ,false);

        return new FeatureDoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeatureDoctorAdapter.FeatureDoctorViewHolder holder, int position) {
        Doctor feature_doctor = mListFeatureDoctor.get(position);
        if (feature_doctor == null)
        {
            return;
        }
        holder.name.setText(feature_doctor.getName());
        holder.numberStar.setText(feature_doctor.getRating() + "");
        holder.priceFDoctor.setText(feature_doctor.getPrice() + "");
        Glide.with(holder.layoutMain.getContext())
                .load(feature_doctor.getAvatarUrl())
                .into(holder.ivDoctorAvatar);
        holder.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    System.out.println("lmao");
                    listener.onDoctorCardClick(feature_doctor);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListFeatureDoctor != null){
            return  mListFeatureDoctor.size();
        }
        else
        {
            return 0;
        }
    }

    public static class FeatureDoctorViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView numberStar;
        private TextView priceFDoctor;
        private ImageView ivDoctorAvatar;
        private LinearLayout layoutMain;


        public FeatureDoctorViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameFeatureDoctor);
            numberStar = itemView.findViewById(R.id.numberStarFeatureDoctor);
            priceFDoctor = itemView.findViewById(R.id.priceFeatureDoctor);
            ivDoctorAvatar = itemView.findViewById(R.id.iv_feature_doctor_avatar);
            layoutMain = itemView.findViewById(R.id.layout_feature_doctor);
        }
    }
}