package com.horrorcore.car_show.config;

import com.horrorcore.car_show.entities.Car;
import com.horrorcore.car_show.entities.Owner;
import com.horrorcore.car_show.enums.EngineType;
import com.horrorcore.car_show.enums.Gender;
import com.horrorcore.car_show.enums.VehicleType;
import com.horrorcore.car_show.repositories.CarRepository;
import com.horrorcore.car_show.repositories.OwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("!prod")
public class DbInit implements CommandLineRunner {

    private final OwnerRepository ownerRepository;
    private final CarRepository carRepository;

    @Override
    public void run(String... args) {
        if (ownerRepository.count() > 0) {
            log.info("Database already seeded, skipping init.");
            return;
        }

        log.info("Seeding database...");

        // --- Owners ---
        Owner marcus = new Owner();
        marcus.setFirstName("Marcus");
        marcus.setLastName("Webb");
        marcus.setGender(Gender.MALE);
        marcus.setDateOfBirth(LocalDate.of(1985, 3, 14));

        Owner priya = new Owner();
        priya.setFirstName("Priya");
        priya.setLastName("Sharma");
        priya.setGender(Gender.FEMALE);
        priya.setDateOfBirth(LocalDate.of(1991, 7, 22));

        Owner jordan = new Owner();
        jordan.setFirstName("Jordan");
        jordan.setLastName("Ellis");
        jordan.setGender(Gender.MALE);
        jordan.setDateOfBirth(LocalDate.of(1988, 11, 5));

        ownerRepository.saveAll(List.of(marcus, priya, jordan));

        // --- Marcus: classic muscle / truck guy ---
        Car mustang = new Car();
        mustang.setMake("Ford");
        mustang.setModel("Mustang GT");
        mustang.setEngineType(EngineType.V8_ENGINE);
        mustang.setDoorCount((byte) 2);
        mustang.setVehicleType(VehicleType.SUDAN); // ⚠️ Typo in enum — should be SEDAN
        mustang.setOwner(marcus);

        Car f150 = new Car();
        f150.setMake("Ford");
        f150.setModel("F-150");
        f150.setEngineType(EngineType.V8_ENGINE);
        f150.setDoorCount((byte) 4);
        f150.setVehicleType(VehicleType.TRUCK);
        f150.setOwner(marcus);

        // --- Priya: practical daily drivers ---
        Car camry = new Car();
        camry.setMake("Toyota");
        camry.setModel("Camry");
        camry.setEngineType(EngineType.V6_ENGINE);
        camry.setDoorCount((byte) 4);
        camry.setVehicleType(VehicleType.SUDAN); // ⚠️ Typo in enum — should be SEDAN
        camry.setOwner(priya);

        Car rav4 = new Car();
        rav4.setMake("Toyota");
        rav4.setModel("RAV4");
        rav4.setEngineType(EngineType.V6_ENGINE);
        rav4.setDoorCount((byte) 4);
        rav4.setVehicleType(VehicleType.SUV);
        rav4.setOwner(priya);

        // --- Jordan: adventurer ---
        Car wrx = new Car();
        wrx.setMake("Subaru");
        wrx.setModel("WRX STI");
        wrx.setEngineType(EngineType.V6_ENGINE);
        wrx.setDoorCount((byte) 4);
        wrx.setVehicleType(VehicleType.SUDAN); // ⚠️ Typo in enum — should be SEDAN
        wrx.setOwner(jordan);

        Car rv = new Car();
        rv.setMake("Winnebago");
        rv.setModel("Minnie Winnie");
        rv.setEngineType(EngineType.V8_ENGINE);
        rv.setDoorCount((byte) 2);
        rv.setVehicleType(VehicleType.RV);
        rv.setOwner(jordan);

        carRepository.saveAll(List.of(mustang, f150, camry, rav4, wrx, rv));

        log.info("Database seeded: {} owners, {} cars",
                ownerRepository.count(), carRepository.count());
    }
}