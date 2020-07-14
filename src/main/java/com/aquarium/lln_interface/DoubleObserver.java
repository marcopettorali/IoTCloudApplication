package com.aquarium.lln_interface;

import com.aquarium.util.DataHistory;
import org.eclipse.californium.core.*;

import java.util.*;

public class DoubleObserver {

    private DataHistory<Double> dataHistory;
    private String tag;
    private String state = "WORKING";

    public DoubleObserver(String address, int historySize, String tag) {
        this.tag = tag;
        dataHistory = new DataHistory(historySize);
        ObservingThread thread = new ObservingThread(address);
        thread.setDaemon(true);
        thread.start();
    }

    private void handleObservedData(String content){
        synchronized (dataHistory) {

            double value;
            try {
                value =Double.parseDouble(content);
            }catch(Exception e){
                value = 0;
            }
            dataHistory.add(value);
            System.out.println(tag + " observed value: " + value);
        }
    }

    class ObservingThread extends Thread {
        String address;

        public ObservingThread(String address) {
            this.address = address;
        }

        @Override
        public void run() {
            CoapClient observingClient = new CoapClient(address);
            CoapObserveRelation relation = observingClient.observe(
                    new CoapHandler() {
                        @Override
                        public void onLoad(CoapResponse response) {
                            state = "WORKING";
                            String content = response.getResponseText();
                            synchronized (dataHistory) {
                                handleObservedData(content);
                            }
                        }

                        @Override
                        public void onError() {
                            state = "ERROR";
                            System.err.println(tag + ": failed observe");
                        }
                    });
            while (true) ;
        }
    }

    public List<Double> getDataSince(long date) {
        List<Double> ret;
        synchronized (dataHistory) {
            ret = dataHistory.getDataSince(date);
        }
        return ret;
    }

    public String getState(){
        return state;
    }
}
