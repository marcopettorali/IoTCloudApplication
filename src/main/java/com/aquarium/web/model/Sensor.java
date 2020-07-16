package com.aquarium.web.model;

import com.aquarium.lln_interface.RegisteredDevices;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Simple class modeling the descriptor of a sensor
 */
public class Sensor {

    public final long  DEFAULT_AGE = 3600000; //last 2 hour samples

    public enum SensorDescriptor {
        OXYGEN, PH, NH3, TEMPERATURE, LIGHT_INTENSITY, NONE;

        public String getName() {
            return name();
        }

    }

    public String identifier;
    public String status;
    public SensorDescriptor classDescriptor;
    public List<Actuator> actuators;

    public List<Double> lastValues;
    public long lastValuesAge;

    public Sensor(String identifier, String status, SensorDescriptor descriptor, Actuator actuator) {
        this.identifier = identifier;
        this.classDescriptor = descriptor;
        this.status = status;
        if(actuator.classDescriptor != getLinkedActuator())
            this.actuators = null;
        else {
            this.actuators = new ArrayList<>();
            actuators.add(actuator);
        }

        lastValues = null;
        lastValuesAge = -1;
    }

    public Sensor( com.aquarium.lln_interface.Sensor s) {
        this.identifier = s.getRoom() + "_" + s.getMetric() +
                "_s_" + s.getDeviceId();

        long date = new Date().getTime() - DEFAULT_AGE;
        this.lastValuesAge = date;
        this.lastValues = s.getDataSince(date);
        this.status = s.getState();
        this.classDescriptor = mapType(s.getMetric());
        this.actuators = new ArrayList<>();
    }

    public Actuator.ActuatorDescriptor getLinkedActuator() {
        switch (classDescriptor) {
            case OXYGEN: return Actuator.ActuatorDescriptor.OXYGENATOR;
            case PH: case NH3: return Actuator.ActuatorDescriptor.WATER_CHANGE;
            case TEMPERATURE: return Actuator.ActuatorDescriptor.THERMO_REGULATOR;
            case LIGHT_INTENSITY: return Actuator.ActuatorDescriptor.LIGHT;
            default: return Actuator.ActuatorDescriptor.NONE;
        }
    }

    public static SensorDescriptor mapType(String type) {
        switch (type) {
            case "luminosity":
                return SensorDescriptor.LIGHT_INTENSITY;
            case "oxygen":
                return SensorDescriptor.OXYGEN;
            case "temperature":
                return SensorDescriptor.TEMPERATURE;
            case "ph":
                return SensorDescriptor.PH;
            case "nh3":
                return SensorDescriptor.NH3;
            default:
                return SensorDescriptor.NONE;

        }
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getPlotButtonID() { return "plot_" + identifier; }

    public String getSampleButtonID() { return "sample_" + identifier; }

    public String getLiID() {return "li_" + identifier;}

    public String getStatus() {
        return status;
    }

    public SensorDescriptor getClassDescriptor() {
        return classDescriptor;
    }

    public void addActuator(Actuator a) {
        actuators.add(a);
    }

    public List<Actuator> getActuators() {
        return actuators;
    }

    public List<Double> getLastValues() {
        return lastValues;
    }

    public double getCurrentValue() {
        if(lastValues.size() != 0)
            return lastValues.get(lastValues.size() - 1);
        return Double.MAX_VALUE;
    }

    public Sensor getLinkedSensor(List<Sensor> sensors) {
        if(this.classDescriptor != SensorDescriptor.PH && this.classDescriptor != SensorDescriptor.NH3)
            return null;

        for(Sensor s: sensors) {
            if(this.classDescriptor == SensorDescriptor.PH && s.classDescriptor == SensorDescriptor.NH3)
                return s;
            if(this.classDescriptor == SensorDescriptor.NH3 && s.classDescriptor == SensorDescriptor.PH)
                return s;
        }
        return null;
    }



}
