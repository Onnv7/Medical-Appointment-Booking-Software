package com.hcmute.findyourdoctor.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.model.doctorList;

import java.util.List;

public class doctorListAdapter extends RecyclerView.Adapter<doctorListAdapter.doctorListViewHolder> {
    private List<doctorList> mList;

    public doctorListAdapter(List<doctorList> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public doctorListAdapter.doctorListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_doctor_list, parent ,false);


        return new doctorListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull doctorListAdapter.doctorListViewHolder holder, int position) {
        doctorList doctorList_pt = mList.get(position);
        if (doctorList_pt == null)
        {
            return;
        }
        holder.name.setText(doctorList_pt.getName());
        holder.info.setText(doctorList_pt.getInfo());
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
        private TextView name;
        private TextView info;


        public doctorListViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txtNameDoctorList);
            info = itemView.findViewById(R.id.txtDocListInfo);
        }
    }
}
