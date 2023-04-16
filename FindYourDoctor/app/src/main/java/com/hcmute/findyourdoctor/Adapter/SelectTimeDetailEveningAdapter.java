package com.hcmute.findyourdoctor.Adapter;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmute.findyourdoctor.Activity.DoctorSelectTimeDetailActivity;
import com.hcmute.findyourdoctor.Database.ConnectionDatabase;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Model.selectTimeDetail;

import java.util.List;

public class SelectTimeDetailEveningAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private int selectedIndex = -1;
    private List<selectTimeDetail> handList;

    public SelectTimeDetailEveningAdapter(Context context, int layout, List<selectTimeDetail> handList) {
        this.context = context;
        this.layout = layout;
        this.handList = handList;
        System.out.println("AA=============" + handList.size() );
    }

    @Override
    public int getCount() {
        if(handList == null)
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

    private class ViewHolder
    {
        LinearLayout layoutMain;
        TextView DetailTime;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(layout, viewGroup, false);
            viewHolder.DetailTime = (TextView) view.findViewById(R.id.DetailTime);
            viewHolder.layoutMain = view.findViewById(R.id.layout_time_details);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (i == selectedIndex) {
            viewHolder.DetailTime.setTextColor(Color.BLACK);
            view.setBackgroundResource(R.drawable.background_details_time_selected);
        } else {
            int color = context.getResources().getColor(R.color.primary_green);
            viewHolder.DetailTime.setTextColor(color);
            view.setBackgroundResource(R.drawable.background_details_time);
        }
        selectTimeDetail selectTimeDetail = handList.get(i);

        viewHolder.DetailTime.setText(selectTimeDetail.getTime());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionDatabase dbHelper = new ConnectionDatabase((DoctorSelectTimeDetailActivity) context);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                Cursor cursor = db.rawQuery("SELECT * FROM myCheckSelectTime", null);

                if (cursor == null)
                {
                    Log.d(TAG, "rỗng " );
                }
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        @SuppressLint("Range") String isCheckedAfternoon = cursor.getString(cursor.getColumnIndex("isCheckedAfternoon"));
                        @SuppressLint("Range") String isCheckedEvening = cursor.getString(cursor.getColumnIndex("isCheckedEvening"));

                        if (Integer.parseInt(isCheckedAfternoon) == 0)
                        {
                            selectedIndex = i;
                            notifyDataSetChanged();

                            isCheckedEvening = "1";

                            String updateQuery = "UPDATE myCheckSelectTime SET isCheckedEvening = '" + Integer.parseInt(isCheckedEvening) + "'";

                            db.execSQL(updateQuery);

                        }
                        else
                        {
                            Toast.makeText(context, "Bạn đã chọn lịch buổi trưa", Toast.LENGTH_SHORT).show();
                        }
                    } while (cursor.moveToNext());
                }
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

    public List<selectTimeDetail> getHandList() {
        return handList;
    }

    public void setHandList(List<selectTimeDetail> handList) {

        this.handList = handList;
    }
}
