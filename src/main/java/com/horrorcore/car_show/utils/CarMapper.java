package com.horrorcore.car_show.utils;

import com.horrorcore.car_show.dtos.CarRequest;
import com.horrorcore.car_show.dtos.CarResponse;
import com.horrorcore.car_show.entities.Car;
import com.horrorcore.car_show.enums.EngineType;
import com.horrorcore.car_show.enums.VehicleType;

public class CarMapper {
    public static Car fromDto(CarRequest request) {
        return new Car(
                request.carId() != null ? request.carId() : 0L,
                request.make(),
                request.model(),
                EngineType.valueOf(request.engineType()),
                request.doorCount(),
                VehicleType.valueOf(request.vehicleType()),
                null
        );
    }

    public static CarResponse toDto(Car car) {
        return new CarResponse(
                car.getCarId(),
                car.getMake(),
                car.getModel(),
                car.getEngineType().toString(),
                car.getVehicleType().toString(),
                car.getDoorCount()
        );
    }
}
