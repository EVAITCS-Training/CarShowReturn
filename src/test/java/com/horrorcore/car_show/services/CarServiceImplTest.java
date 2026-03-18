package com.horrorcore.car_show.services;

import com.horrorcore.car_show.dtos.CarRequest;
import com.horrorcore.car_show.entities.Car;
import com.horrorcore.car_show.exceptions.InvalidCarIdException;
import com.horrorcore.car_show.repositories.CarRepository;
import com.horrorcore.car_show.services.testutils.TestDataFactory;
import com.horrorcore.car_show.enums.VehicleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    void shouldCreateCar_whenValidRequest_thenReturnCarResponse() {
        Car car = TestDataFactory.createCarEntity(1L);
        CarRequest req = TestDataFactory.createCarRequest(1L);

        when(carRepository.save(any(Car.class))).thenReturn(car);

        var response = carService.createCar(req);

        assertNotNull(response);
        assertEquals(car.getCarId(), response.carId());
        assertEquals(car.getMake(), response.make());
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void shouldGetAllCars_returnListOfCarResponses() {
        Car car = TestDataFactory.createCarEntity(1L);
        when(carRepository.findAll()).thenReturn(List.of(car));

        var list = carService.getAllCars();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(car.getCarId(), list.get(0).carId());
    }

    @Test
    void shouldGetCarById_whenExists_thenReturnCarResponse() {
        Car car = TestDataFactory.createCarEntity(2L);
        when(carRepository.findById(2L)).thenReturn(Optional.of(car));

        var resp = carService.getCarById(2L);

        assertNotNull(resp);
        assertEquals(2L, resp.carId());
    }

    @Test
    void shouldThrowInvalidCarIdException_whenGetCarByIdNotFound() {
        when(carRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(InvalidCarIdException.class, () -> carService.getCarById(99L));
    }

    @Test
    void shouldGetCarsByMake_returnMatchingCars() {
        Car car = TestDataFactory.createCarEntity(3L);
        when(carRepository.findAllByMake("Toyota")).thenReturn(List.of(car));

        var list = carService.getCarsByMake("Toyota");

        assertEquals(1, list.size());
        assertEquals("Toyota", list.get(0).make());
    }

    @Test
    void shouldGetCarsByModel_returnMatchingCars() {
        Car car = TestDataFactory.createCarEntity(4L);
        when(carRepository.findAllByModel("Corolla")).thenReturn(List.of(car));

        var list = carService.getCarsByModel("Corolla");

        assertEquals(1, list.size());
        assertEquals("Corolla", list.get(0).model());
    }

    @Test
    void shouldGetCarsByType_returnMatchingCars() {
        Car car = TestDataFactory.createCarEntity(5L);
        when(carRepository.findAllByVehicleTypeIgnoreCase(VehicleType.SUDAN)).thenReturn(List.of(car));

        var list = carService.getCarsByType(VehicleType.SUDAN.name());

        assertEquals(1, list.size());
        assertEquals(VehicleType.SUDAN, car.getVehicleType());
    }

    @Test
    void shouldUpdateCarInformation_whenExists_thenReturnUpdatedResponse() {
        Car newCar = TestDataFactory.createCarEntity(6L);
        when(carRepository.existsById(6L)).thenReturn(true);
        when(carRepository.save(any(Car.class))).thenReturn(newCar);

        var resp = carService.updateCarInformation(TestDataFactory.createCarRequest(6L));

        assertNotNull(resp);
        assertEquals(6L, resp.carId());
    }

    @Test
    void shouldReturnNull_whenUpdateCarInformationNotExists() {
        when(carRepository.existsById(7L)).thenReturn(false);

        var resp = carService.updateCarInformation(TestDataFactory.createCarRequest(7L));

        assertNull(resp);
    }

    @Test
    void shouldDeleteCarById_whenExists_thenDeleteCalled() {
        when(carRepository.existsById(8L)).thenReturn(true);

        carService.deleteCarById(8L);

        verify(carRepository, times(1)).deleteById(8L);
    }

    @Test
    void shouldThrowInvalidCarIdException_whenDeleteCarByIdNotExists() {
        when(carRepository.existsById(9L)).thenReturn(false);

        assertThrows(InvalidCarIdException.class, () -> carService.deleteCarById(9L));
    }
}


