import java.net.InetAddress;

public class Actuator extends Device {
    ResourceConnection resource;

    public Actuator(InetAddress address, int room, String type, String metric, Integer deviceId) {
        super(address, room, type, metric, deviceId);
        String observedResourceAddress = "coap://" + address.getHostAddress() + "/" + metric + "_actuator";
        this.resource = new ResourceConnection(observedResourceAddress);
    }

    public String sendGetRequest(String[] params) {
        return resource.sendGetRequest(params);
    }

    public void sendPostRequest(String jsonString) {
        resource.sendPostRequest(jsonString);
    }

    public void sendPutRequest(String jsonString) {
        resource.sendPutRequest(jsonString);
    }
}
