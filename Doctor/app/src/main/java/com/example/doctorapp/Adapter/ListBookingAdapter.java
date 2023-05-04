package com.example.doctorapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorapp.Activity.BookingDetailsActivity;
import com.example.doctorapp.Model.Booking;
import com.example.doctorapp.R;

import java.util.List;

public class ListBookingAdapter extends RecyclerView.Adapter<ListBookingAdapter.ListBookingViewHolder>{
    private List<Booking> mList;
    private Context context;

    public ListBookingAdapter(List<Booking> mList) {
        this.mList = mList;
        this.context = context;
    }

    public ListBookingAdapter(List<Booking> mList, Context context) {
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
        Booking infor = mList.get(position);
        if (infor == null)
        {
            return;
        }
        String datetime = infor.getTime();
        String time = datetime.substring(0, 8); // lấy từ vị trí 0 đến 8
        System.out.println(time);
        holder.time.setText(time);
        holder.name.setText(infor.getPatient().getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookingDetailsActivity.class);
                intent.putExtra("booking_id", infor.getId());
                context.startActivity(intent);
            }
        });
//        holder.day.setText(infor.get());
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
