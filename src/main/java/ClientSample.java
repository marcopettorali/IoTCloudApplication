import org.json.simple.*;
import org.json.simple.parser.*;

import java.net.*;
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
                Device d = new Device(InetAddress.getByName((String) elem.get("a")), new Long((long) elem.get("r")).intValue(), (String) elem.get("t"), (String) elem.get("m"));
                System.out.println(d);
            }
        } catch (ParseException | UnknownHostException e) {
            e.printStackTrace();
        }

        SimpleIntegerObserver observer = new SimpleIntegerObserver("coap://127.0.0.1/obs", 20);
        long lastUpdate = 0;
        while (true) {
            Thread.sleep(Math.round(Math.random() * 10000));
            List<Integer> list = observer.retrieveDataSince(lastUpdate);
            for (int i : list) {
                System.out.print(i + ", ");
            }
            System.out.println();
            lastUpdate = new Date().getTime();
        }

    }
}
