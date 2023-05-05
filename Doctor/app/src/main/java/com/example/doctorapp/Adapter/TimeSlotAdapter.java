package com.example.doctorapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.doctorapp.Domain.TimeSlotDomain;
import com.example.doctorapp.R;

import java.util.List;

public class TimeSlotAdapter extends BaseAdapter {
    private String type;
    private Context context;
    private int layout;



    private List<TimeSlotDomain> handList;

    public TimeSlotAdapter() {
    }

    public TimeSlotAdapter(Context context, int layout, List<TimeSlotDomain> handList, String type) {
        this.context = context;
        this.layout = layout;
        this.handList = handList;
        this.type = type;
    }


    @Override
    public int getCount() {
        if (handList == null)
            return 0;
        return handList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        LinearLayout layoutMain;
        TextView DetailTime;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(layout, viewGroup, false);
            viewHolder.DetailTime = (TextView) view.findViewById(R.id.DetailTime);
            viewHolder.layoutMain = view.findViewById(R.id.layout_time_details);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        TimeSlotDomain selectTimeDetail = handList.get(i);

        viewHolder.DetailTime.setText(selectTimeDetail.getStart());

        if(selectTimeDetail.getSelected()) {
            viewHolder.layoutMain.setBackgroundResource(R.drawable.background_selected);
            viewHolder.DetailTime.setTextColor(Color.BLACK);
        }
        else {
            viewHolder.layoutMain.setVisibility(View.INVISIBLE);
        }


        return view;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public List<TimeSlotDomain> getHandList() {
        return handList;
    }

    public void setHandList(List<TimeSlotDomain> handList) {

        this.handList = handList;
    }
    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }
}
