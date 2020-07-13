package com.aquarium.web.model;

import java.util.ArrayList;

/**
 * This class models the descriptor of a Tank in the Aquarium
 */
public class Tank {

    public enum Location {
        INSIDE, OUTSIDE
    }

    public String identifier;
    public String name;
    public Location location;
    public ArrayList<Sensor> sensors;

    //To be used when we have to represent only tank names and locations
    // (e.g. to show the list of tanks in the aquarium)
    public Tank(String identifier, String name, boolean isInside) {
        this.identifier = identifier;
        this.name = name;
        if(isInside)
            this.location = Location.INSIDE;
        else
            this.location = Location.OUTSIDE;
        this.sensors = null;
    }

    public Tank(String identifier, String name, boolean isInside, ArrayList<Sensor> sensors) {
        this.identifier = identifier;
        this.name = name;
        if(isInside)
            this.location = Location.INSIDE;
        else
            this.location = Location.OUTSIDE;
        this.sensors = sensors;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

}
