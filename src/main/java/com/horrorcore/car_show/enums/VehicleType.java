package com.horrorcore.car_show.enums;

public enum VehicleType {
    SUDAN("Sudan"),
    TRUCK("Truck"),
    SUV("SUV"),
    RV("RV");

    private String name;

    VehicleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
