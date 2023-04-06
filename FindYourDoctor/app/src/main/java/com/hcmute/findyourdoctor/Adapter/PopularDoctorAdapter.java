package com.hcmute.findyourdoctor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.findyourdoctor.Model.doctorList;
import com.hcmute.findyourdoctor.R;

import java.util.List;

public class PopularDoctorAdapter extends RecyclerView.Adapter<PopularDoctorAdapter.PopularDoctorViewHolder> {
    private List<doctorList> mListPopularDoctor;

    public PopularDoctorAdapter(List<doctorList> mListPopularDoctor) {
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
        doctorList doctorList_pt = mListPopularDoctor.get(position);
        if (doctorList_pt == null)
        {
            return;
        }
        holder.name.setText(doctorList_pt.getName());
        holder.info.setText(doctorList_pt.getInfo());
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
        private TextView name;
        private TextView info;


        public PopularDoctorViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txtPopularDoctorName);
            info = itemView.findViewById(R.id.txtPopularDoctorDes);
        }
    }
}