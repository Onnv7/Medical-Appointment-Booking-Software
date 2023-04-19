package com.hcmute.findyourdoctor;

import com.hcmute.findyourdoctor.Adapter.SelectTimeDetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterObserver {
    private int index;
    private String type;
    private List<SelectTimeDetailAdapter> adapters = new ArrayList<>();

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

    public void registerAdapter(SelectTimeDetailAdapter adapter) {
        adapters.add(adapter);
    }

    public void notifyDataChanged() {
        for (SelectTimeDetailAdapter adapter : adapters) {
            adapter.notifyDataSetChanged();
        }
    }

}
