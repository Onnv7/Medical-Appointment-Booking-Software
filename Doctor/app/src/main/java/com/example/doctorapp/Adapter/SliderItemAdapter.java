package com.example.doctorapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorapp.Domain.SliderItemDomain;
import com.example.doctorapp.R;

import java.util.List;

public class SliderItemAdapter extends RecyclerView.Adapter<SliderItemAdapter.PhotoViewHolder> {
    private List<SliderItemDomain> sliderItemDomainList;
    private Context context;
    public SliderItemAdapter(List<SliderItemDomain> sliderItemDomainList, Context context) {
        this.sliderItemDomainList = sliderItemDomainList;
        this.context = context;
    }

    @NonNull
    @Override
    public SliderItemAdapter.PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_slider_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderItemAdapter.PhotoViewHolder holder, int position) {
        SliderItemDomain item = sliderItemDomainList.get(position);
        if(item == null) {
            return;
        }
        holder.ivImage.setImageResource(item.getResourceId());

    }

    @Override
    public int getItemCount() {
        if(sliderItemDomainList != null) {
            return sliderItemDomainList.size();
        }
        return 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImage;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image_slider_item);
        }
    }
}
