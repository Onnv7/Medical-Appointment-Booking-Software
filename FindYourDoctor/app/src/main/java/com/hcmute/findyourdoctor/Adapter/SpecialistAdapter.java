package com.hcmute.findyourdoctor.Adapter;

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
import com.hcmute.findyourdoctor.Model.Specialist;
import com.hcmute.findyourdoctor.R;

import java.util.List;


public class SpecialistAdapter extends RecyclerView.Adapter<SpecialistAdapter.SpecialListViewHolder> {
    private List<Specialist> mListSpecialist;

    public SpecialistAdapter(List<Specialist> mListSpecialist) {
        this.mListSpecialist = mListSpecialist;
    }

    @NonNull
    @Override
    public SpecialistAdapter.SpecialListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_specialist, parent ,false);
        return new SpecialListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialistAdapter.SpecialListViewHolder holder, int position) {
        Specialist specialist_pt = mListSpecialist.get(position);
        Log.d("nva", "SpecialListViewHolder: " + mListSpecialist.size());
        if (specialist_pt == null)
        {
            return;
        }
        holder.specialist_title.setText(specialist_pt.getName());
        Glide.with(holder.ivSpecialist.getContext())
                .load(specialist_pt.getImageUrl())
                .into(holder.ivSpecialist);
//      holder.info.setText(specialist_pt.getInfo());
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

    public static class SpecialListViewHolder extends RecyclerView.ViewHolder {
        private TextView specialist_title;
        private ImageView ivSpecialist;
        private LinearLayout layoutSpecialist;
//        private TextView info;


        public SpecialListViewHolder(@NonNull View itemView) {
            super(itemView);

            specialist_title = itemView.findViewById(R.id.specialist_title2);
            ivSpecialist = itemView.findViewById(R.id.iv_specialist);
            layoutSpecialist = itemView.findViewById(R.id.layout_specialist);
//            info = itemView.findViewById(R.id.txtDocListInfo);
        }
    }
}