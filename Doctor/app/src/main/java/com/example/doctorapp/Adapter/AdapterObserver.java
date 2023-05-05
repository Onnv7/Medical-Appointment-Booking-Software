package com.example.doctorapp.Adapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterObserver {
    private int index;
    private String type;
    private List<TimeSlotAdapter> adapters = new ArrayList<>();

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

    public void registerAdapter(TimeSlotAdapter adapter) {
        adapters.add(adapter);
    }

    public void notifyDataChanged() {
        for (TimeSlotAdapter adapter : adapters) {
            adapter.notifyDataSetChanged();
        }
    }

}
