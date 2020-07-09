import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.*;

public class ResourceConnection {
    String resourceAddress;
    CoapClient client;

    public ResourceConnection(String resourceAddress){
        this.resourceAddress = resourceAddress;
        client = new CoapClient(resourceAddress);
    }

    public String sendGetRequest(String[] params) {
        Request req = new Request(CoAP.Code.GET);
        req.getOptions().setAccept(MediaTypeRegistry.APPLICATION_JSON);
        for(String param : params){
            req.getOptions().addUriQuery(param);
        }
        CoapResponse resp = client.advanced(req);
        return resp.getResponseText();
    }
}
