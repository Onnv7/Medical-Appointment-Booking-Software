package com.hcmute.findyourdoctor.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hcmute.findyourdoctor.AdapterObserver;
import com.hcmute.findyourdoctor.Listener.OnSelectedTimeSlot;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Domain.SelectTimeDetailDomain;

import java.util.List;

public class SelectTimeDetailAdapter extends BaseAdapter {
    private AdapterObserver dataObserver;
    private OnSelectedTimeSlot onSelectedTimeSlot;
    private String type;
    private Context context;
    private int layout;

    private List<SelectTimeDetailDomain> handList;

    public SelectTimeDetailAdapter() {
    }

    public SelectTimeDetailAdapter(Context context, int layout, List<SelectTimeDetailDomain> handList, String type, OnSelectedTimeSlot onSelectedTimeSlot, AdapterObserver dataObserver) {
        this.context = context;
        this.layout = layout;
        this.handList = handList;
        this.type = type;
        this.onSelectedTimeSlot = onSelectedTimeSlot;
        this.dataObserver = dataObserver;
        dataObserver.registerAdapter(this);
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

        if (i == dataObserver.getIndex() && type.equals(dataObserver.getType())) {
            viewHolder.DetailTime.setTextColor(Color.BLACK);
            view.setBackgroundResource(R.drawable.background_details_time_selected);
        } else {
            int color = context.getResources().getColor(R.color.primary_green);
            viewHolder.DetailTime.setTextColor(color);
            view.setBackgroundResource(R.drawable.background_details_time);
        }
        SelectTimeDetailDomain selectTimeDetail = handList.get(i);

        viewHolder.DetailTime.setText(selectTimeDetail.getTime());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataObserver.setType(type);
                dataObserver.setIndex(i);
                dataObserver.notifyDataChanged();
            }
        });

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

    public List<SelectTimeDetailDomain> getHandList() {
        return handList;
    }

    public void setHandList(List<SelectTimeDetailDomain> handList) {

        this.handList = handList;
    }
    public String getType() {
        return type;
    }

    public OnSelectedTimeSlot getOnSelectedTimeSlot() {
        return onSelectedTimeSlot;
    }

    public void setOnSelectedTimeSlot(OnSelectedTimeSlot onSelectedTimeSlot) {
        this.onSelectedTimeSlot = onSelectedTimeSlot;
    }

    public void setType(String type) {
        this.type = type;
    }
}
