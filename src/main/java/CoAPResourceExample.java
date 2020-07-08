import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.*;
import org.eclipse.californium.core.server.resources.*;
import org.json.JSONObject;

public class CoAPResourceExample extends CoapResource {
    public CoAPResourceExample(String name) {
        super(name);
    }

    public void handleGET(CoapExchange exchange) {
        Response response = new Response(CoAP.ResponseCode.CONTENT);
        if (exchange.getRequestOptions().getAccept() == MediaTypeRegistry.APPLICATION_JSON) {
            response.getOptions().setContentFormat(MediaTypeRegistry.APPLICATION_JSON);

            JSONObject json = new JSONObject();
            json.put("name", "Marco");
            json.put("surname", "Pettorali");
            response.setPayload(json.toString());
        }
        exchange.respond(response);
        System.out.println(new String(response.getPayload()));

    }

    public void handlePOST(CoapExchange exchange) {
        /* your stuff */
        exchange.respond(CoAP.ResponseCode.CREATED);
    }
}