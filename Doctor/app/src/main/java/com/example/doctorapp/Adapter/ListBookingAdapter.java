package com.example.doctorapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctorapp.Activity.BookingDetailsActivity;
import com.example.doctorapp.Model.Booking;
import com.example.doctorapp.R;
import com.example.doctorapp.Utils.DateTimeFormat;

import java.text.ParseException;
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
        String created = null;
        try {
            created = DateTimeFormat.formatDateMongodb(infor.getCreatedAt(), "hh:mm dd/MM/yyyy");
            holder.day.setText("Created at: " + created);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Glide.with(holder.itemView)
                        .load(infor.getPatient().getAvatarUrl())
                                .into(holder.ivPatientAvatar);
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
        private ImageView ivPatientAvatar;

        public ListBookingViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tv_time_accepted_booking_card);
            name = itemView.findViewById(R.id.tv_name_accepted_booking_card);
            day= itemView.findViewById(R.id.tv_created_at_accepted_booking_card);
            ivPatientAvatar = itemView.findViewById(R.id.iv_paitent_avatar_accepted_booking_card);

        }
    }
}
