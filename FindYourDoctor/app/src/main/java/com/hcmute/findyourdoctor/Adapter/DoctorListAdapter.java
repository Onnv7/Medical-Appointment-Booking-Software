package com.hcmute.findyourdoctor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hcmute.findyourdoctor.Activity.DoctorDetailActivity;
import com.hcmute.findyourdoctor.Activity.DoctorSelectTimeDetailActivity;
import com.hcmute.findyourdoctor.Model.Doctor;
import com.hcmute.findyourdoctor.R;

import java.util.List;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.doctorListViewHolder> {
    private List<Doctor> mList;
    private Context mContext;

    public DoctorListAdapter(List<Doctor> mList, Context mContext) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public DoctorListAdapter.doctorListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_doctor_list, parent ,false);


        return new doctorListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorListAdapter.doctorListViewHolder holder, int position) {
        int index = holder.getAdapterPosition();
        Doctor popularDoctorDomain_pt = mList.get(position);
        if (popularDoctorDomain_pt == null)
        {
            return;
        }
        holder.tvName.setText(popularDoctorDomain_pt.getName());
        holder.tvSpecialistName.setText(popularDoctorDomain_pt.getSpecialist().getName());
        holder.tvPatientQuantity.setText("(" + popularDoctorDomain_pt.getPatientQuantity() + " patients)");
        holder.tvPrice.setText("$ " + popularDoctorDomain_pt.getPrice());
        Glide.with(holder.itemView)
                .load(popularDoctorDomain_pt.getAvatarUrl())
                .into(holder.ivAvatar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DoctorDetailActivity.class);
                intent.putExtra("id", mList.get(index).getId());
                mContext.startActivity(intent);
            }
        });
        holder.btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DoctorSelectTimeDetailActivity.class);
                intent.putExtra("doctor", mList.get(index));
                mContext.startActivity(intent);
            }
        });
//        holder.info.setText(popularDoctorDomain_pt.getInfo());
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

    public static class doctorListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvSpecialistName, tvPatientQuantity, tvPrice;
        private ImageView ivAvatar;
        private Button btnBookNow;


        public doctorListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name_doctor_card);
            tvSpecialistName = itemView.findViewById(R.id.tv_specialist_name_doctor_card);
            tvPatientQuantity = itemView.findViewById(R.id.tv_patient_quantity_doctor_card);
            tvPrice = itemView.findViewById(R.id.tv_price_doctor_card);
            ivAvatar = itemView.findViewById(R.id.iv_avatar_doctor_card);
            btnBookNow = itemView.findViewById(R.id.btn_book_now_doctor_card);
        }
    }
}
