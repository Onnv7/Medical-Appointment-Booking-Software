package com.hcmute.findyourdoctor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Model.selectTimeDetail;

import java.util.List;

public class SelectTimeDetailAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<selectTimeDetail> handList;

    public SelectTimeDetailAdapter(Context context, int layout, List<selectTimeDetail> handList) {
        this.context = context;
        this.layout = layout;
        this.handList = handList;
    }

    @Override
    public int getCount() {
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

    private class ViewHolder
    {
        TextView DetailTime;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null)
        {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(layout,null);

            viewHolder.DetailTime = (TextView) view.findViewById(R.id.DetailTime);

            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        selectTimeDetail selectTimeDetail = handList.get(i);

        viewHolder.DetailTime.setText(selectTimeDetail.getTime());

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

    public List<selectTimeDetail> getHandList() {
        return handList;
    }

    public void setHandList(List<selectTimeDetail> handList) {

        this.handList = handList;
    }
}
