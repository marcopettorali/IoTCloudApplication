import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.*;
import org.eclipse.californium.core.server.resources.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.net.*;
import java.util.*;

public class DevicesResource extends CoapResource {
    private String metric;

    public DevicesResource() {
        super("devices");
    }

    public void handleGET(CoapExchange exchange) {
        Response response = new Response(CoAP.ResponseCode.CONTENT);
        if (exchange.getRequestOptions().getAccept() == MediaTypeRegistry.APPLICATION_JSON) {
            response.getOptions().setContentFormat(MediaTypeRegistry.APPLICATION_JSON);
            String roomStr = exchange.getQueryParameter("r");
            String type = exchange.getQueryParameter("t");
            String metric = exchange.getQueryParameter("m");
            Integer room = null;
            if (roomStr != null) {
                room = Integer.parseInt(roomStr);
            }
            List<Device> devices = RegisteredDevices.query(room, type, metric);
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            if (!devices.isEmpty()) {
                for (Device d : devices) {
                    JSONObject jsonDevice = new JSONObject();
                    jsonDevice.put("r", d.getRoom());
                    jsonDevice.put("t", d.getType());
                    jsonDevice.put("m", d.getMetric());
                    jsonDevice.put("a", d.getAddress().getHostAddress());
                    jsonArray.add(jsonDevice);
                }
            }
            jsonObject.put("devices", jsonArray);
            response.setPayload(jsonObject.toJSONString());
            System.out.println("RESPONSE = " + jsonObject.toJSONString());
        }
        exchange.respond(response);
    }

    public void handlePOST(CoapExchange exchange) {
        try {
            System.out.println("Payload = " + exchange.getRequestText() + ".");
            JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(exchange.getRequestText());
            System.out.println("Parsed json " + jsonObject.toJSONString() + ".");
            InetAddress deviceAddress = exchange.getSourceAddress();
            int room = new Long((long) jsonObject.get("r")).intValue();
            String type = (String) jsonObject.get("t");
            String metric = (String) jsonObject.get("m");
            Device device = new Device(deviceAddress, room, type, metric);
            RegisteredDevices.insert(device);
            System.out.println("A new " + device + " has just been inserted!");
            RegisteredDevices.print();
            exchange.respond(CoAP.ResponseCode.CREATED);
        } catch (ParseException e) {
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
            e.printStackTrace();
        }
    }
}
