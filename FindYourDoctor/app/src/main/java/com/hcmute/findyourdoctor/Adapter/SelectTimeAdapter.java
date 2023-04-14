package com.hcmute.findyourdoctor.Adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.findyourdoctor.Listener.OnAvailableDateClickListener;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Model.selectTime;

import java.util.List;

public class SelectTimeAdapter extends RecyclerView.Adapter<SelectTimeAdapter.selectTimeViewHolder> {
    private List<selectTime> mList;
    private OnAvailableDateClickListener listener;
    private int indexSelected = 0;
//    private Context context;

    public SelectTimeAdapter(List<selectTime> mList, OnAvailableDateClickListener listener) {
        this.mList = mList;
        this.listener = listener;
//        this.context = context;
    }


    @NonNull
    @Override
    public SelectTimeAdapter.selectTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_time, parent ,false);

        return new selectTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTimeAdapter.selectTimeViewHolder holder, int position) {
        selectTime selectTime_pt = mList.get(position);
        if (selectTime_pt == null)
        {
            return;
        }
        holder.date.setText(selectTime_pt.getDate());
        holder.slot.setText(selectTime_pt.getSlot() + " slots available");

        if(position == indexSelected) {
            holder.itemView.setSelected(true);
            if(listener != null) {
                listener.onClick(holder.date.getText().toString());
            }
        }
        else {
            holder.itemView.setSelected(false);
        }

        holder.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = holder.getAdapterPosition();
                indexSelected = index;
                notifyDataSetChanged();
                
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

    public static class selectTimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView date;
        private TextView slot;
        private LinearLayout layoutMain;


        public selectTimeViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.tv_date_select_time);
            slot = itemView.findViewById(R.id.tv_slot_select_time);
            layoutMain = itemView.findViewById(R.id.layout_available_date_item);

        }

        @Override
        public void onClick(View view) {
            boolean isSelected = itemView.isActivated();
            Toast.makeText(itemView.getContext(), "lmaoooo " + isSelected, Toast.LENGTH_SHORT).show();
            itemView.setSelected(isSelected);
        }
    }
}