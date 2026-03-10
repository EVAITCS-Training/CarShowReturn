package com.horrorcore.car_show.services;

import com.horrorcore.car_show.dtos.CarRequest;
import com.horrorcore.car_show.dtos.CarResponse;
import com.horrorcore.car_show.entities.Car;
import com.horrorcore.car_show.enums.EngineType;
import com.horrorcore.car_show.enums.VehicleType;
import com.horrorcore.car_show.exceptions.InvalidCarIdException;
import com.horrorcore.car_show.repositories.CarRepository;
import com.horrorcore.car_show.utils.CarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override
    public CarResponse createCar(CarRequest carRequest) {
        Car car = CarMapper.fromDto(carRequest);
        return CarMapper.toDto(carRepository.save(car));
    }

    @Override
    public List<CarResponse> getAllCars() {
        return carRepository.findAll().stream().map(CarMapper::toDto).toList();
    }

    @Override
    public CarResponse getCarById(long id) {
        return CarMapper.toDto(
                carRepository.findById(id)
                        .orElseThrow(
                                () -> new InvalidCarIdException("Car id " + id + " not found!")
                        )
        );
    }

    @Override
    public List<CarResponse> getCarsByMake(String make) {
        return carRepository.findAllByMake(make).stream().map(CarMapper::toDto).toList();
    }

    @Override
    public List<CarResponse> getCarsByModel(String model) {
        return carRepository.findAllByModel(model).stream().map(CarMapper::toDto).toList();
    }

    @Override
    public List<CarResponse> getCarsByType(String vehicleType) {
        return carRepository
                .findAllByVehicleTypeIgnoreCase(
                        VehicleType
                                .valueOf(vehicleType)
                ).stream()
                .map(CarMapper::toDto).toList();
    }

    @Override
    public CarResponse updateCarInformation(CarRequest carRequest) {
        Car newCarInfo = CarMapper.fromDto(carRequest);
        if(carRepository.existsById(newCarInfo.getCarId())) {
            return CarMapper.toDto(carRepository.save(newCarInfo));
        }
        return null;
    }

    @Override
    public void deleteCarById(long id) {
        if(!carRepository.existsById(id)) throw new InvalidCarIdException("Car id " + id + " not found!");
        carRepository.deleteById(id);
    }
}
