import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.*;
import org.eclipse.californium.core.server.resources.*;

import java.util.*;

public class ObservableResource extends CoapResource {
    private int num;

    public ObservableResource(String name) {
        super(name);
        setObservable(true);
        Timer timer = new Timer();
        timer.schedule(new UpdateTask(), 0, 3 * 1000);
    }

    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
            num = (int) (Math.random() * 1000);
            System.out.println(num);
            changed();
        }
    }

    public void handleGET(CoapExchange exchange) {
        Response response = new Response(CoAP.ResponseCode.CONTENT);
        response.getOptions().setContentFormat(MediaTypeRegistry.TEXT_PLAIN);
        response.setPayload(Integer.toString(num));
        exchange.respond(response);
    }

    public void handlePOST(CoapExchange exchange) {
        /* your stuff */
        exchange.respond(CoAP.ResponseCode.CREATED);
    }
}