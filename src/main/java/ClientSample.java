import org.eclipse.californium.core.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientSample {

    public static void main(String[] args) throws InterruptedException {

        ResourceConnection device = new ResourceConnection("coap://127.0.0.1/devices");
        //register
        JSONObject deviceJson = new JSONObject();
        deviceJson.put("r", 2);
        deviceJson.put("t", "sensor");
        deviceJson.put("m", "light");
        device.sendPostRequest(deviceJson.toJSONString());
        System.out.println("Correctly registered to server");

        //retrieve devices
        try {
            JSONObject json = (JSONObject) JSONValue.parseWithException(device.sendGetRequest(new String[]{"r=1", "t=sensor"}));
            JSONArray jsonArray = (JSONArray) json.get("devices");
            for (Object o : jsonArray) {
                JSONObject elem = (JSONObject) o;
                Device d = new Device(InetAddress.getByName((String) elem.get("a")), new Long((long) elem.get("r")).intValue(), (String) elem.get("t"), (String) elem.get("m"), new Long((long) elem.get("n")).intValue());
                System.out.println(d);
            }
        } catch (ParseException | UnknownHostException e) {
            e.printStackTrace();
        }

        DoubleObserver doubleObserver = new DoubleObserver("coap://127.0.0.1/obs", 20);
        long lastUpdate = 0;
        while (true) {
            Thread.sleep(Math.round(Math.random() * 10000));
            List<Double> list = new ArrayList<Double>(doubleObserver.getDataSince(lastUpdate));
            for (double i : list) {
                System.out.print(i + ", ");
            }
            System.out.println();
            lastUpdate = new Date().getTime();
        }

    }
}
