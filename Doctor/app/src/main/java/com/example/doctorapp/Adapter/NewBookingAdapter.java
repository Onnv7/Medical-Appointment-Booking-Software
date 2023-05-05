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
import com.example.doctorapp.Activity.NewBookingDetailsActivity;
import com.example.doctorapp.Model.Booking;
import com.example.doctorapp.R;
import com.example.doctorapp.Utils.DateTimeFormat;

import java.text.ParseException;
import java.util.List;

public class NewBookingAdapter extends RecyclerView.Adapter<NewBookingAdapter.NewBookingViewHolder>{
private List<Booking> mList;
private Context context;

public NewBookingAdapter(List<Booking> mList, Context context) {
    this.mList = mList;
    this.context = context;
}

@NonNull
@Override
public NewBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_new_booking_card, parent ,false);

    return new NewBookingViewHolder(view);
}

@Override
public void onBindViewHolder(@NonNull NewBookingViewHolder holder, int position) {
    Booking booking = mList.get(position);
    if (booking == null) {
        return;
    }
    String[] parts = booking.getTime().split(" ", 2);
    String time = parts[0];
    String date = parts[1];
    System.out.println(booking.getTime());
    holder.tvTime.setText(time);
    holder.tvDate.setText(date);
    holder.tvName.setText(booking.getPatient().getName());
    String created = null;
    try {
        created = DateTimeFormat.formatDateMongodb(booking.getCreatedAt(), "hh:mm dd/MM/yyyy");
        holder.tvCreatedAt.setText("Created at: " + created);
    } catch (ParseException e) {
        throw new RuntimeException(e);
    }
    Glide.with(holder.itemView)
            .load(booking.getPatient().getAvatarUrl())
            .into(holder.ivPatientAvatar);

    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, NewBookingDetailsActivity.class);
            intent.putExtra("booking_id", booking.getId());
            context.startActivity(intent);
        }
    });
}

@Override
public int getItemCount() {
        return mList.size();
        }

public static class NewBookingViewHolder extends RecyclerView.ViewHolder {

    private TextView tvTime, tvName, tvCreatedAt, tvDate;
    private ImageView ivPatientAvatar;

    public NewBookingViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTime = itemView.findViewById(R.id.tv_time_new_booking_card);
        tvName = itemView.findViewById(R.id.tv_name_new_booking_card);
        tvDate = itemView.findViewById(R.id.tv_date_new_booking_card);
        tvCreatedAt= itemView.findViewById(R.id.tv_created_at_new_booking_card);
        ivPatientAvatar = itemView.findViewById(R.id.iv_paitent_avatar_new_booking_card);

    }
}
}
