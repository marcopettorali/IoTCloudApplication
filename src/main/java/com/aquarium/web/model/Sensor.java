package com.aquarium.web.model;

/**
 * Simple class modeling the descriptor of a sensor
 */
public class Sensor {

    public enum SensorDescriptor {
        OXYGEN, PH_NH3, TEMPERATURE, LIGHT_INTENSITY, NONE
    }

    public String identifier;
    public float currentValue;
    public float currentValue2; //used only in PH_NH3 sensor for NH3 value
    public String status;
    public SensorDescriptor classDescriptor;
    public Actuator actuator;

    public Sensor(String identifier, float currentValue, String status, SensorDescriptor descriptor, Actuator actuator) {
        this.identifier = identifier;
        this.currentValue = currentValue;
        this.classDescriptor = descriptor;
        this.status = status;
        if(actuator.classDescriptor != getLinkedActuator())
            this.actuator = null;
        else
            this.actuator = actuator;
    }

    public Actuator.ActuatorDescriptor getLinkedActuator() {
        switch (classDescriptor) {
            case OXYGEN: return Actuator.ActuatorDescriptor.OXYGENATOR;
            case PH_NH3: return Actuator.ActuatorDescriptor.WATER_CHANGE;
            case TEMPERATURE: return Actuator.ActuatorDescriptor.THERMO_REGULATOR;
            case LIGHT_INTENSITY: return Actuator.ActuatorDescriptor.LIGHT;
            default: return Actuator.ActuatorDescriptor.NONE;
        }
    }

    public String getIdentifier() {
        return identifier;
    }

    public float getCurrentValue() {
        return currentValue;
    }

    public String getStatus() {
        return status;
    }

    public SensorDescriptor getClassDescriptor() {
        return classDescriptor;
    }

    public Actuator getActuator() {
        return actuator;
    }

    public float getCurrentValue2() {
        return currentValue2;
    }

    public void setCurrentValue2(float currentValue2) {
        this.currentValue2 = currentValue2;
    }


}
