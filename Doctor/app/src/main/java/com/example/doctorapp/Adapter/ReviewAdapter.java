package com.example.doctorapp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctorapp.Api.RetrofitClient;
import com.example.doctorapp.Api.ReviewApiService;
import com.example.doctorapp.Model.Review;
import com.example.doctorapp.R;
import com.example.doctorapp.Utils.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.reviewViewHolder> {
    private List<Review> mList;
    private Context mContext;
    private SharedPreferences sharedPreferences;
    private ReviewApiService reviewApiService;
    private String uid;

    public ReviewAdapter(List<Review> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ReviewAdapter.reviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        reviewApiService = RetrofitClient.getRetrofit().create(ReviewApiService.class);
        sharedPreferences = mContext.getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("id", "");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_review, parent ,false);
        return new reviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.reviewViewHolder holder, int position) {
        Review review_pt = mList.get(position);
        if (review_pt == null)
        {
            return;
        }
        holder.tvPatientName.setText(review_pt.getPatient().getName());
        holder.tvDescription.setText(review_pt.getDescription());
        holder.rtbRatingDoctor.setRating(review_pt.getStar());
        holder.tvDatetime.setText(review_pt.getCreatedAt());
        Glide.with(holder.itemView.getContext())
                .load(review_pt.getPatient().getAvatarUrl())
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
        private TextView tvPatientName, tvDatetime;
        private TextView tvDescription, tvLikeQuantity;
        private ImageView ivPatientAvatar, ivLike;
        private RatingBar rtbRatingDoctor;


        public reviewViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPatientName = itemView.findViewById(R.id.tv_patient_name_review);
            tvDescription = itemView.findViewById(R.id.tv_description_review);
            ivPatientAvatar = itemView.findViewById(R.id.iv_patient_avatar_review);
            rtbRatingDoctor = itemView.findViewById(R.id.rtb_doctor_review);
            tvDatetime = itemView.findViewById(R.id.tv_datetime_review);
        }
    }
}