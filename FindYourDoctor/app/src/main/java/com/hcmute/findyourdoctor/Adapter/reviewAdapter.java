package com.hcmute.findyourdoctor.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.model.review;

import java.util.List;

public class reviewAdapter extends RecyclerView.Adapter<reviewAdapter.reviewViewHolder> {
    private List<review> mList;

    public reviewAdapter(List<review> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public reviewAdapter.reviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_review, parent ,false);


        return new reviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull reviewAdapter.reviewViewHolder holder, int position) {
        review review_pt = mList.get(position);
        if (review_pt == null)
        {
            return;
        }
        holder.name.setText(review_pt.getName());
        holder.comment.setText(review_pt.getComment());
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
        private TextView name;
        private TextView comment;


        public reviewViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txtNameReview);
            comment = itemView.findViewById(R.id.txtContentReview);
        }
    }
}
