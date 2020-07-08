import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.net.*;

public class ClientSample {

    private static void registerToServer(CoapClient devicesClient) {
        JSONObject json = new JSONObject();
        json.put("r", 2);
        json.put("t", "sensor");
        json.put("m", "light");
        devicesClient.post(json.toString(), MediaTypeRegistry.APPLICATION_JSON);
    }

    public static void main(String[] args) {
        CoapClient devicesClient = new CoapClient("coap://127.0.0.1/devices");

        //register
        registerToServer(devicesClient);
        System.out.println("Correctly registered to server");

        //retieve devices
        Request req = new Request(CoAP.Code.GET);
        req.getOptions().setAccept(MediaTypeRegistry.APPLICATION_JSON);
        req.getOptions().addUriQuery("t=sensor");
        req.getOptions().addUriQuery("r=1");
        System.out.println("REQUEST = " + req.getOptions().getUriQueryString());
        CoapResponse resp = devicesClient.advanced(req);

        try {
            JSONObject json = (JSONObject) JSONValue.parseWithException(resp.getResponseText());
            JSONArray jsonArray = (JSONArray) json.get("devices");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject elem = (JSONObject) jsonArray.get(i);
                Device d = new Device(InetAddress.getByName((String) elem.get("a")), new Long((long) elem.get("r")).intValue(), (String) elem.get("t"), (String) elem.get("m"));
                System.out.println(d);
            }
        } catch (ParseException | UnknownHostException e) {
            e.printStackTrace();
        }

        // observing
        CoapClient observingClient = new CoapClient("coap://127.0.0.1/obs");
        CoapObserveRelation relation = observingClient.observe(
                new CoapHandler() {
                    @Override
                    public void onLoad(CoapResponse response) {
                        String content = response.getResponseText();
                        System.out.println(content);
                    }

                    @Override
                    public void onError() {
                        System.err.println("Failed");
                    }
                });
        while (true) ;

    }
}
