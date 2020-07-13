package com.aquarium.web.model;

import java.util.ArrayList;

/**
 * This class models the descriptor of a Tank in the Aquarium
 */
public class Tank {

    public int identifier;
    public String name;
    public ArrayList<Sensor> sensors;

    //To be used when we have to represent only tank names and locations
    // (e.g. to show the list of tanks in the aquarium)
    public Tank(int identifier, String name) {
        this.identifier = identifier;
        this.name = name;
        this.sensors = new ArrayList<>();
    }

    public Tank(int identifier, String name, ArrayList<Sensor> sensors) {
        this.identifier = identifier;
        this.name = name;
        this.sensors = sensors;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

}
