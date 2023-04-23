package com.hcmute.findyourdoctor.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Model.review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.reviewViewHolder> {
    private List<review> mList;

    public ReviewAdapter(List<review> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ReviewAdapter.reviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_review, parent ,false);
        return new reviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.reviewViewHolder holder, int position) {
        review review_pt = mList.get(position);
        if (review_pt == null)
        {
            return;
        }
        holder.tvPatientName.setText(review_pt.getPatientName());
        holder.tvDescription.setText(review_pt.getDescription());
        holder.rtbRatingDoctor.setRating(review_pt.getStar());
        Glide.with(holder.itemView.getContext())
                .load(review_pt.getAvatarUrl())
                .into(holder.ivPatientAvatar);
    }

    @Override
    public int getItemCount() {
        if (mList != null){
            return  mList.size();
        }
        else
        {
            return 0;
        }
    }

    public static class reviewViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPatientName;
        private TextView tvDescription;
        private ImageView ivPatientAvatar;
        private RatingBar rtbRatingDoctor;


        public reviewViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPatientName = itemView.findViewById(R.id.tv_patient_name_review);
            tvDescription = itemView.findViewById(R.id.tv_description_review);
            ivPatientAvatar = itemView.findViewById(R.id.iv_patient_avatar_review);
            rtbRatingDoctor = itemView.findViewById(R.id.rtb_doctor_review);
        }
    }
}
