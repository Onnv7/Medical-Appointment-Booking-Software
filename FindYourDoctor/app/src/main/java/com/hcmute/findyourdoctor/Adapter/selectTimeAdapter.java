package com.hcmute.findyourdoctor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.model.selectTime;

import java.util.List;

public class selectTimeAdapter extends RecyclerView.Adapter<selectTimeAdapter.selectTimeViewHolder> {
    private List<selectTime> mList;
    private Context context;

    public selectTimeAdapter(List<selectTime> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }


    @NonNull
    @Override
    public selectTimeAdapter.selectTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_time, parent ,false);


        return new selectTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull selectTimeAdapter.selectTimeViewHolder holder, int position) {
        selectTime selectTime_pt = mList.get(position);
        if (selectTime_pt == null)
        {
            return;
        }
        holder.date.setText(selectTime_pt.getDate());
        holder.slot.setText(selectTime_pt.getSlot());
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

    public static class selectTimeViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView slot;


        public selectTimeViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.selectTimeDate);
            slot = itemView.findViewById(R.id.selectTimeSlot);
        }
    }
}