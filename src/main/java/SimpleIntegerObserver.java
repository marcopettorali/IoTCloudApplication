import org.eclipse.californium.core.*;
import util.*;

import java.util.*;

public class SimpleIntegerObserver {

    private DataHistory<Integer> dataHistory;

    public SimpleIntegerObserver(String address, int historySize) {
        dataHistory = new DataHistory(historySize);
        ObservingThread thread = new ObservingThread(address);
        thread.setDaemon(true);
        thread.start();
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
                                dataHistory.add(Integer.parseInt(content));
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

    public List<Integer> retrieveDataSince(long date) {
        List<Integer> ret;
        synchronized (dataHistory) {
            ret = dataHistory.retrieveListSince(date);
        }
        return ret;
    }
}
