package com.hcmute.findyourdoctor.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hcmute.findyourdoctor.Domain.SpecialistDomain;
import com.hcmute.findyourdoctor.R;

import java.util.ArrayList;

public class SpecialistAdapter extends RecyclerView.Adapter<SpecialistAdapter.ViewHolder> {

    ArrayList<SpecialistDomain> specialistDomains;

    public SpecialistAdapter(ArrayList<SpecialistDomain> specialistDomains) {
        this.specialistDomains = specialistDomains;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_specialist, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.specialistTitle.setText(specialistDomains.get(position).getTitle());
        int drawableResourseId = holder.itemView.getContext().getResources().getIdentifier(specialistDomains.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourseId)
                .into(holder.specialistPic);
    }

    @Override
    public int getItemCount() {
        return specialistDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView specialistTitle;
        ImageView specialistPic;
        ConstraintLayout specialistLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            specialistTitle = itemView.findViewById(R.id.specialist_title);
            specialistPic = itemView.findViewById(R.id.specialist_pic);
            specialistLayout = itemView.findViewById(R.id.specialist_layout);
        }
    }
}
