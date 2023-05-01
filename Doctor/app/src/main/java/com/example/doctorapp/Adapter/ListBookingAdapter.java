package com.example.doctorapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorapp.Domain.DayDomain;
import com.example.doctorapp.Domain.InforPatientDomain;
import com.example.doctorapp.R;

import java.util.List;

public class ListBookingAdapter extends RecyclerView.Adapter<ListBookingAdapter.ListBookingViewHolder>{
    private List<InforPatientDomain> mList;
    private Context context;

    public ListBookingAdapter(List<InforPatientDomain> mList) {
        this.mList = mList;
        this.context = context;
    }

    public ListBookingAdapter(List<InforPatientDomain> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_infor_patient, parent ,false);

        return new ListBookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListBookingViewHolder holder, int position) {
        InforPatientDomain infor = mList.get(position);
        if (infor == null)
        {
            return;
        }
        holder.time.setText(infor.getTime());
        holder.name.setText(infor.getName());
        holder.day.setText(infor.getDay());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ListBookingViewHolder extends RecyclerView.ViewHolder {

        private TextView time, name, day;

        public ListBookingViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tv_time_manage_booking);
            name = itemView.findViewById(R.id.tv_name_manage_booking);
            day= itemView.findViewById(R.id.tv_year_manage_booking);

        }
    }
}
