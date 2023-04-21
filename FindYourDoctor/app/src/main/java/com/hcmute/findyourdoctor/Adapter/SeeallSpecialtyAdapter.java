package com.hcmute.findyourdoctor.Adapter;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hcmute.findyourdoctor.Domain.SpecialistDomain;
import com.hcmute.findyourdoctor.R;

import java.util.List;

public class SeeallSpecialtyAdapter extends RecyclerView.Adapter<SeeallSpecialtyAdapter.SeeallSpecialtyViewHolder> {
    private List<SpecialistDomain> mListSpecialist;

    public SeeallSpecialtyAdapter(List<SpecialistDomain> mListSpecialist) {
        this.mListSpecialist = mListSpecialist;
    }

    @NonNull
    @Override
    public SeeallSpecialtyAdapter.SeeallSpecialtyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_see_all_specialty, parent ,false);
        return new SeeallSpecialtyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeeallSpecialtyAdapter.SeeallSpecialtyViewHolder holder, int position) {
        SpecialistDomain specialist_pt = mListSpecialist.get(position);

        Log.d(TAG, "onBindViewHolder: " + specialist_pt.getTitle());
        if (specialist_pt == null)
        {
            return;
        }
        holder.tv_seeall_specialty.setText(specialist_pt.getTitle());
        Glide.with(holder.imv_seeall_specialty.getContext())
                .load(specialist_pt.getPic())
                .into(holder.imv_seeall_specialty);
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
        private TextView tv_seeall_specialty;
        private ImageView imv_seeall_specialty;


        public SeeallSpecialtyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_seeall_specialty = itemView.findViewById(R.id.tv_seeall_specialty);
            imv_seeall_specialty = itemView.findViewById(R.id.imv_seeall_specialty);
        }
    }
}