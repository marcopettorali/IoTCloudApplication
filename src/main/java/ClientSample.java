import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.net.*;
import java.util.Date;
import java.util.List;

public class ClientSample {

    private static void registerToServer(CoapClient devicesClient) {
        JSONObject json = new JSONObject();
        json.put("r", 2);
        json.put("t", "sensor");
        json.put("m", "light");
        devicesClient.post(json.toString(), MediaTypeRegistry.APPLICATION_JSON);
    }

    public static void main(String[] args) throws InterruptedException {
        CoapClient devicesClient = new CoapClient("coap://127.0.0.1/devices");

        //register
        registerToServer(devicesClient);
        System.out.println("Correctly registered to server");

        //retrieve devices
        ResourceConnection device = new ResourceConnection("coap://127.0.0.1/devices");

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
            Thread.sleep(5000);
            List<Integer> list = observer.retrieveDataSince(lastUpdate);
            for (int i : list) {
                System.out.print(i + ", ");
            }
            System.out.println();
            lastUpdate = new Date().getTime();
        }

    }
}
