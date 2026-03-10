package com.horrorcore.car_show.dtos;

public record CarRequest(
        long carId,
        String make,
        String model,
        String engineType,
        String vehicleType,
        byte doorCount
) {
    public CarRequest(String make, String model, String engineType, String vehicleType, byte doorCount) {
        this(0, make, model, engineType, vehicleType, doorCount);
    }
}
