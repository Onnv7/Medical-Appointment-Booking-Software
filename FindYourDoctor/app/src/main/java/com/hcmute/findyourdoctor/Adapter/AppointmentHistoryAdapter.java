package com.hcmute.findyourdoctor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hcmute.findyourdoctor.Activity.AppointmentDetailsActivity;
import com.hcmute.findyourdoctor.Activity.DoctorDetailActivity;
import com.hcmute.findyourdoctor.Activity.HistoryDetailsActivity;
import com.hcmute.findyourdoctor.Model.Booking;
import com.hcmute.findyourdoctor.Listener.OnHistoryAppointmentClickListener;
import com.hcmute.findyourdoctor.R;

import java.util.List;

public class AppointmentHistoryAdapter extends RecyclerView.Adapter<AppointmentHistoryAdapter.AppointmentHistoryViewHolder> {
    private List<Booking> mList;
    private Context context;
    private OnHistoryAppointmentClickListener listener;

    public AppointmentHistoryAdapter(List<Booking> mList, Context context) {
        this.mList = mList;
        this.context = context;
//        this.listener = listener;
    }

    @NonNull
    @Override
    public AppointmentHistoryAdapter.AppointmentHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_appointment_history, parent ,false);

        return new AppointmentHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentHistoryAdapter.AppointmentHistoryViewHolder holder, int position) {
        Booking appointmentHistory_pt = mList.get(position);
        if (appointmentHistory_pt == null)
        {
            return;
        }
        holder.nameAppointmentHistory.setText(appointmentHistory_pt.getDoctor().getName());
        holder.timeAppointmentHistory.setText(appointmentHistory_pt.getTime());
        String status = appointmentHistory_pt.getStatus();
        if(status.equals("succeeded")) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.status_succeeded));
        }
        else if(status.equals("denied")) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.status_denied));
        } else if (status.equals("accepted")) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.status_accepted));
        } else if (status.equals("waiting")) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.status_waiting));
        }
        holder.tvStatus.setText("Status: " + status);
        Glide.with(holder.itemView.getContext())
                .load(appointmentHistory_pt.getDoctor().getAvatarUrl())
                .into(holder.imageAppointmentHistory);

        holder.cvLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HistoryDetailsActivity.class);
                intent.putExtra("booking_id", appointmentHistory_pt.getId());
                context.startActivity(intent);
            }
        });

        holder.imageAppointmentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DoctorDetailActivity.class);
                intent.putExtra("id", appointmentHistory_pt.getDoctor().getId());
                holder.itemView.getContext().startActivity(intent);
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

    public static class AppointmentHistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView nameAppointmentHistory, tvStatus;
        private ImageView imageAppointmentHistory;
        private TextView timeAppointmentHistory;
        private CardView cvLayout;

        public AppointmentHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            nameAppointmentHistory = itemView.findViewById(R.id.tv_nameAppointmentHistory);
            imageAppointmentHistory = itemView.findViewById(R.id.imv_AppointmentHistory);
            timeAppointmentHistory = itemView.findViewById(R.id.tv_timeAppointmentHistory);
            tvStatus = itemView.findViewById(R.id.tv_status_history);
            cvLayout = itemView.findViewById(R.id.cv_history_appointment);
        }
    }
}
