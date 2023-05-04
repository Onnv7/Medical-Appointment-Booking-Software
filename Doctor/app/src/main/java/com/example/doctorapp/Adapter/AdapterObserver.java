package com.example.doctorapp.Adapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterObserver {
    private int index;
    private String type;
    private List<ScheduleAdapter> adapters = new ArrayList<>();

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void registerAdapter(ScheduleAdapter adapter) {
        adapters.add(adapter);
    }

    public void notifyDataChanged() {
        for (ScheduleAdapter adapter : adapters) {
            adapter.notifyDataSetChanged();
        }
    }

}
