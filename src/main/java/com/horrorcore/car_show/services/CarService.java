package com.horrorcore.car_show.services;

import com.horrorcore.car_show.dtos.CarRequest;
import com.horrorcore.car_show.dtos.CarResponse;

import java.util.List;

public interface CarService {
    CarResponse createCar(CarRequest carRequest);
    List<CarResponse> getAllCars();
    CarResponse getCarById(long id);
    List<CarResponse> getCarsByMake(String make);
    List<CarResponse> getCarsByModel(String model);
    List<CarResponse> getCarsByType(String vehicleType);
    CarResponse updateCarInformation(CarRequest carRequest);
    void deleteCarById(long id);
}
