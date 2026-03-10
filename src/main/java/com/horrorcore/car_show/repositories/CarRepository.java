package com.horrorcore.car_show.repositories;

import com.horrorcore.car_show.entities.Car;
import com.horrorcore.car_show.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByModel(String model);
    List<Car> findAllByMake(String make);
    List<Car> findAllByVehicleTypeIgnoreCase(VehicleType vehicleType);
}
