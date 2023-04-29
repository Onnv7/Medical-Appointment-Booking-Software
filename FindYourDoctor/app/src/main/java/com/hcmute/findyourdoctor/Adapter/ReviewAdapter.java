package com.hcmute.findyourdoctor.Adapter;

import static com.hcmute.findyourdoctor.Utils.Constant.SHARE;

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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Activity.LoginActivity;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Api.ReviewApiService;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Model.Review;
import com.hcmute.findyourdoctor.Utils.Constant;

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
        holder.tvLikeQuantity.setText(review_pt.getLiker().size() + "");
        Glide.with(holder.itemView.getContext())
                .load(review_pt.getPatient().getAvatarUrl())
                .into(holder.ivPatientAvatar);
        if(!review_pt.getLiker().contains(uid)) {
            Toast.makeText(mContext, "not like", Toast.LENGTH_SHORT).show();
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.ic_like_gray)
                    .into(holder.ivLike);
        }
        else {
            Toast.makeText(mContext, "like", Toast.LENGTH_SHORT).show();
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.ic_like_green_filled)
                    .into(holder.ivLike);
        }
        holder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject body = new JsonObject();
                body.addProperty("likerId", uid);
                reviewApiService.updateLiker(review_pt.getId(), body).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Gson gson = new Gson();
                        if(response.isSuccessful() && response.body().get("success").getAsBoolean())
                        {
                            JsonObject result = response.body().getAsJsonObject("result");
                            Review review = gson.fromJson(result, Review.class);
                            review_pt.setLiker(review.getLiker());
                            notifyItemChanged(position);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }
        });
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
            ivLike = itemView.findViewById(R.id.iv_like_review);
            tvLikeQuantity = itemView.findViewById(R.id.tv_like_quantity_review);
        }
    }
}
