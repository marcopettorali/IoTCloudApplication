import java.net.*;
import java.util.*;

public class RegisteredDevices {
    private static List<Device> registeredDevices;

    static {
        registeredDevices = new ArrayList<>();
    }


    public static void insert(Device d) {
        registeredDevices.add(d);
    }

    public static void remove(Device d) {
        registeredDevices.remove(d);
    }

    public static Device findByAddress(InetAddress addr) {
        for (Device d : registeredDevices) {
            if (d.getAddress().equals(addr)) {
                return d;
            }
        }
        return null;
    }

    public static List<Device> query(Integer room, String type, String metric) {
        List<Device> ret = new ArrayList<>();
        for (Device d : registeredDevices) {
            if (((room == null) || d.getRoom() == room) && ((type == null) || d.getType().equals(type)) && ((metric == null) || d.getMetric().equals(metric))) {
                ret.add(d);
            }
        }
        return ret;
    }

    public static int countRooms(){
        List<Integer> rooms = new ArrayList<>();
        for (Device d : registeredDevices) {
            boolean found = false;
            for(Integer r : rooms){
                if(r == d.getRoom()){
                    found = true;
                    break;
                }
            }
            if(!found){
                rooms.add(d.getRoom());
            }
        }
        return rooms.size();
    }

    public static void print() {
        System.out.println("REGISTERED DEVICES\n-----------------------");
        for (Device d : registeredDevices) {
            System.out.println("|\t- " + d);
        }
        System.out.println("-----------------------\n");
    }

}
