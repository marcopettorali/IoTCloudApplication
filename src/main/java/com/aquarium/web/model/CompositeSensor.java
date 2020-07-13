package com.aquarium.web.model;

/**
 * This class is useful to represent devices containing two sensors
 * @param <SecondValueType> the type of the value collected from the second sensor
 */
public class CompositeSensor<SecondValueType> extends Sensor {

    SecondValueType secondValue;

    public CompositeSensor(String identifier,
                           float value1,
                           SecondValueType value2,
                           String status,
                           SensorDescriptor descriptor,
                           Actuator actuator)
    {
        super(identifier, value1, status, descriptor,actuator);
        this.secondValue = value2;
    }

    public SecondValueType getSecondValue() {
        return secondValue;
    }
}
