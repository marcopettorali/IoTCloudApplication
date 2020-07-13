package com.aquarium.web.model;

/**
 * Simple class modeling the descriptor of an actuator
 */
public class Actuator {

    public enum ActuatorDescriptor {
        LIGHT, OXYGENATOR, THERMO_REGULATOR, WATER_CHANGE, NONE
    }

    public String identifier;
    public int status; //Usually 1->ON, 0->OFF
    String statusDescription;
    ActuatorDescriptor classDescriptor;

    public Actuator(String identifier, int status, String statusDescription, ActuatorDescriptor descriptor) {
        this.identifier = identifier;
        this.statusDescription = statusDescription;
        this.status = status;
        this.classDescriptor = descriptor;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public ActuatorDescriptor getClassDescriptor() {
        return classDescriptor;
    }

}
