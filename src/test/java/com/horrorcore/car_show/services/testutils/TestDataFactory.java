package com.horrorcore.car_show.services.testutils;

import com.horrorcore.car_show.dtos.CarRequest;
import com.horrorcore.car_show.dtos.OwnerRequest;
import com.horrorcore.car_show.dtos.UserRequest;
import com.horrorcore.car_show.entities.Car;
import com.horrorcore.car_show.entities.Owner;
import com.horrorcore.car_show.enums.EngineType;
import com.horrorcore.car_show.enums.Gender;
import com.horrorcore.car_show.enums.VehicleType;

import java.time.LocalDate;
import java.util.List;

public final class TestDataFactory {

    private TestDataFactory() {}

    public static CarRequest createCarRequest(long id) {
        return new CarRequest(id,
                "Toyota",
                "Corolla",
                EngineType.V6_ENGINE.name(),
                VehicleType.SUDAN.name(),
                (byte)4
        );
    }

    public static Car createCarEntity(long id) {
        Car car = new Car();
        car.setCarId(id);
        car.setMake("Toyota");
        car.setModel("Corolla");
        car.setEngineType(EngineType.V6_ENGINE);
        car.setDoorCount((byte)4);
        car.setVehicleType(VehicleType.SUDAN);
        car.setOwner(null);
        return car;
    }

    public static OwnerRequest createOwnerRequest(long id) {
        return new OwnerRequest(id, "John", "Doe", Gender.MALE, LocalDate.of(1990,1,1));
    }

    public static Owner createOwnerEntity(long id) {
        Owner owner = new Owner();
        owner.setOwnerId(id);
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setGender(Gender.MALE);
        owner.setDateOfBirth(LocalDate.of(1990,1,1));
        owner.setCars(List.of());
        return owner;
    }

    public static UserRequest createUserRequest(String email, String password) {
        return new UserRequest(email, password);
    }
}

