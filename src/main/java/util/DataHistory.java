package util;

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
        index = -1;
        full = false;
    }

    public void add(T elem) {
        index = (index + 1) % size;
        values[index] = elem;
        timestamps[index] = new Date().getTime();
        if (index == 0) {
            full = true;
        }
    }

    public List<T> retrieveListSince(long date) {
        List<T> ret = new ArrayList<>();
        if (!full) {
            for (int i = 0; i <= index; i++) {
                ret.add(values[i]);
                System.out.println(values[i]);
            }
        } else {
            for (int i = 0; i <= size; i++) {
                if(timestamps[(index + i) % size] > date){
                    ret.add(values[(index + i) % size]);
                }
            }
        }
        return ret;
    }

}
