package com.hcmute.findyourdoctor.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hcmute.findyourdoctor.Domain.AppointmentDomain;
import com.hcmute.findyourdoctor.Model.doctorList;
import com.hcmute.findyourdoctor.R;

import java.util.List;

public class AppointmentHistoryAdapter extends RecyclerView.Adapter<AppointmentHistoryAdapter.AppointmentHistoryViewHolder> {
    private List<AppointmentDomain> mList;

    public AppointmentHistoryAdapter(List<AppointmentDomain> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public AppointmentHistoryAdapter.AppointmentHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_appointment_history, parent ,false);


        return new AppointmentHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentHistoryAdapter.AppointmentHistoryViewHolder holder, int position) {
        AppointmentDomain appointmentHistory_pt = mList.get(position);
        if (appointmentHistory_pt == null)
        {
            return;
        }
        holder.nameAppointmentHistory.setText(appointmentHistory_pt.getName());
        holder.timeAppointmentHistory.setText(appointmentHistory_pt.getTime());
//        Glide.with(holder.itemView.getContext())
//                .load(appointmentHistory_pt.getImage())
//                .into(holder.imageAppointmentHistory);

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
        private TextView nameAppointmentHistory;
        private ImageView imageAppointmentHistory;
        private TextView timeAppointmentHistory;

        public AppointmentHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            nameAppointmentHistory = itemView.findViewById(R.id.tv_nameAppointmentHistory);
            imageAppointmentHistory = itemView.findViewById(R.id.imv_AppointmentHistory);
            timeAppointmentHistory = itemView.findViewById(R.id.tv_timeAppointmentHistory);
        }
    }
}
