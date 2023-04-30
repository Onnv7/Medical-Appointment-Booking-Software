package com.hcmute.findyourdoctor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hcmute.findyourdoctor.Activity.DoctorListActitvity;
import com.hcmute.findyourdoctor.Model.Specialist;
import com.hcmute.findyourdoctor.R;

import java.util.List;

public class SeeallSpecialtyAdapter extends RecyclerView.Adapter<SeeallSpecialtyAdapter.SeeallSpecialtyViewHolder> {
    private List<Specialist> mListSpecialist;
    private Context mContext;

    public SeeallSpecialtyAdapter(List<Specialist> mListSpecialist, Context mContext) {
        this.mListSpecialist = mListSpecialist;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SeeallSpecialtyAdapter.SeeallSpecialtyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_see_all_specialty, parent ,false);
        return new SeeallSpecialtyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeeallSpecialtyAdapter.SeeallSpecialtyViewHolder holder, int position) {
        Specialist specialist_pt = mListSpecialist.get(position);

        if (specialist_pt == null)
        {
            return;
        }
        holder.tvTitle.setText(specialist_pt.getName());
        holder.tvQuantity.setText(specialist_pt.getDoctorQuantity() + " doctors");
        Glide.with(holder.ivImage.getContext())
                .load(specialist_pt.getImageUrl())
                .into(holder.ivImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DoctorListActitvity.class);
                intent.putExtra("specialistId", specialist_pt.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListSpecialist != null){
            return  mListSpecialist.size();
        }
        else
        {
            return 0;
        }
    }

    public static class SeeallSpecialtyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvQuantity;
        private ImageView ivImage;
        


        public SeeallSpecialtyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_name_specialty);
            ivImage = itemView.findViewById(R.id.iv_image_specialty);
            tvQuantity = itemView.findViewById(R.id.tv_quantity_speciatly);
        }
    }
}