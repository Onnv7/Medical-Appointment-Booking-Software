package com.example.doctorapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorapp.Domain.DayDomain;
import com.example.doctorapp.Listener.OnDateSelectedListener;
import com.example.doctorapp.R;

import java.util.List;

public class TimeManageBookingAdapter extends RecyclerView.Adapter<TimeManageBookingAdapter.TimeManageBookingViewHolder>{
    private List<DayDomain> mList;
    private OnDateSelectedListener listener;
    private Context context;
    private int indexSelected = 0;

    public TimeManageBookingAdapter(List<DayDomain> mList) {
        this.mList = mList;
        this.context = context;
    }

    public TimeManageBookingAdapter(List<DayDomain> mList, Context context, OnDateSelectedListener listener) {
        this.mList = mList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TimeManageBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_time_manage_booking, parent ,false);

        return new TimeManageBookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeManageBookingViewHolder holder, int position) {
        DayDomain day1 = mList.get(position);
        if (day1 == null)
        {
            return;
        }
        holder.oneDay.setText(day1.getOneDay());
        holder.day.setText(day1.getDay());
        if(position == indexSelected) {
            holder.itemView.setSelected(true);
            listener.onDateSelected(day1.getOneDay());
        }
        else {
            holder.itemView.setSelected(false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = holder.getAdapterPosition();
                indexSelected = index;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class TimeManageBookingViewHolder extends RecyclerView.ViewHolder {

        private TextView oneDay, day;

        public TimeManageBookingViewHolder(@NonNull View itemView) {
            super(itemView);
            oneDay = itemView.findViewById(R.id.oneDay);
            day = itemView.findViewById(R.id.day);
        }
    }
}
