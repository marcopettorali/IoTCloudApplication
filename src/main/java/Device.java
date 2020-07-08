import java.net.InetAddress;

public class Device {
    private InetAddress address;
    private int room;
    private String type;
    private String metric;

    public Device(InetAddress address, int room, String type, String metric) {
        this.address = address;
        this.room = room;
        this.type = type;
        this.metric = metric;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getRoom() {
        return room;
    }

    public String getType() {
        return type;
    }

    public String getMetric() {
        return metric;
    }

    @Override
    public String toString() {
        return metric + " " + type + " in room " + room + "@" + address.getHostAddress();
    }
}
