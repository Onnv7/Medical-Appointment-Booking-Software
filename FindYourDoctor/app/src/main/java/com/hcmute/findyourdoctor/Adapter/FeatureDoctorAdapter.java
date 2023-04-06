package com.hcmute.findyourdoctor.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.findyourdoctor.Domain.FeatureDoctorDomain;
import com.hcmute.findyourdoctor.R;

import java.util.List;

public class FeatureDoctorAdapter extends RecyclerView.Adapter<FeatureDoctorAdapter.FeatureDoctorViewHolder> {
    private List<FeatureDoctorDomain> mListFeatureDoctor;

    public FeatureDoctorAdapter(List<FeatureDoctorDomain> mListFeatureDoctor) {
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
        FeatureDoctorDomain feature_doctor = mListFeatureDoctor.get(position);
        if (feature_doctor == null)
        {
            return;
        }
        holder.name.setText(feature_doctor.getName());
        holder.numberStar.setText(Integer.toString(feature_doctor.getNumber()));
        holder.priceFDoctor.setText(Integer.toString(feature_doctor.getPrice()));
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


        public FeatureDoctorViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameFeatureDoctor);
            numberStar = itemView.findViewById(R.id.numberStarFeatureDoctor);
            priceFDoctor = itemView.findViewById(R.id.priceFeatureDoctor);
        }
    }
}