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

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {
    private List<AppointmentDomain> mList;

    public AppointmentAdapter(List<AppointmentDomain> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public AppointmentAdapter.AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_appointment, parent ,false);


        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.AppointmentViewHolder holder, int position) {
        AppointmentDomain appointment_pt = mList.get(position);
        if (appointment_pt == null)
        {
            return;
        }
        holder.name.setText(appointment_pt.getName());
        holder.status.setText(appointment_pt.getStatus());
        holder.time.setText(appointment_pt.getTime());

//        Glide.with(holder.itemView.getContext())
//                .load(appointment_pt.getImage())
//                .into(holder.image);

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

    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView status;
        private ImageView image;
        private TextView time;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameAppointment);
            status = itemView.findViewById(R.id.statusAppointment);
            image = itemView.findViewById(R.id.imageAppointment);
            time = itemView.findViewById(R.id.timeAppointment);
        }
    }
}
