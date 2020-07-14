package com.aquarium.util;

import java.util.*;

public class DataHistory<T> {
    private int size;
    private T[] values;
    private long[] timestamps;
    private int index;
    private boolean full;

    public DataHistory(int size) {
        values = (T[]) new Object[size];
        timestamps = new long[size];
        this.size = size;
        index = 0;
        full = false;
    }

    public void add(T elem) {
        values[index] = elem;
        timestamps[index] = new Date().getTime();
        index = (index + 1) % size;
        if (index == 0) {
            full = true;
        }
    }

    public List<T> getDataSince(long date) {
        List<T> ret = new ArrayList<>();
        if (!full) {
            for (int i = 0; i < index; i++) {
                ret.add(values[i]);
            }
        } else {
            for (int i = 0; i < size; i++) {
                if(timestamps[(index + i) % size] > date){
                    ret.add(values[(index + i) % size]);
                }
            }
        }
        return ret;
    }

}
