package com.aquarium.web.model;

import java.util.Date;
import java.util.List;

/**
 * Simple class modeling the descriptor of an actuator
 */
public class Actuator {

    public final long  DEFAULT_AGE = 3600000; //last 2 hour samples

    public enum ActuatorDescriptor {
        LIGHT, OXYGENATOR, THERMO_REGULATOR, WATER_CHANGE, NONE;

        public String getName() {
            return name();
        }

    }

    public String identifier;
    String status;
    ActuatorDescriptor classDescriptor;
    List<Double> lastValues;
    long lastValuesAge;

    public Actuator(String identifier, String status, ActuatorDescriptor descriptor) {
        this.identifier = identifier;
        this.status = status;
        this.classDescriptor = descriptor;
    }

    public Actuator(com.aquarium.lln_interface.Actuator a) {
        this.identifier = a.getRoom() + "_" + a.getMetric() + "_a_"
                + a.getDeviceId();

        long date = new Date().getTime() - DEFAULT_AGE;
        this.lastValuesAge = date;
        this.lastValues = a.getDataSince(date);
        this.status= a.getState();
        this.classDescriptor = mapType(a.getMetric());
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getStatus() {
        return status;
    }

    public ActuatorDescriptor getClassDescriptor() {
        return classDescriptor;
    }

    public List<Double> getLastValues() {
        return lastValues;
    }

    public double getCurrentValue() {
        if(lastValues.size() != 0)
            return lastValues.get(lastValues.size() - 1);
        return Double.MAX_VALUE;
    }

    public static Actuator.ActuatorDescriptor mapType(String type) {
        switch (type) {
            case "luminosity":
                return ActuatorDescriptor.LIGHT;
            case "oxygen":
                return ActuatorDescriptor.OXYGENATOR;
            case "temperature":
                return ActuatorDescriptor.THERMO_REGULATOR;
            case "water":
                return ActuatorDescriptor.WATER_CHANGE;
            default:
                return ActuatorDescriptor.NONE;
        }
    }

}
