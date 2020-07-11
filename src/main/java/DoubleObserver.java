import org.eclipse.californium.core.*;
import util.*;

import java.util.*;

public class DoubleObserver {

    private DataHistory<Double> dataHistory;

    public DoubleObserver(String address, int historySize) {
        dataHistory = new DataHistory(historySize);
        ObservingThread thread = new ObservingThread(address);
        thread.setDaemon(true);
        thread.start();
    }

    private void handleObservedData(String content){
        synchronized (dataHistory) {
            dataHistory.add(Double.parseDouble(content));
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
                            String content = response.getResponseText();
                            synchronized (dataHistory) {
                                handleObservedData(content);
                            }
                        }

                        @Override
                        public void onError() {
                            System.err.println("Failed");
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
}
