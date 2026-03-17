package com.horrorcore.car_show.controllers;

import com.horrorcore.car_show.dtos.CarRequest;
import com.horrorcore.car_show.dtos.CarResponse;
import com.horrorcore.car_show.services.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<CarResponse>> carIndex() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @PostMapping(value = {"/create", "/create/"})
    public ResponseEntity<CarResponse> createCar(@Valid @RequestBody CarRequest newCar) {
        return ResponseEntity.created(null).body(carService.createCar(newCar));
    }

    @PutMapping(value = {"/{id}/update"})
    public ResponseEntity<CarResponse> updateCarRequest(@Valid @RequestBody CarRequest request) {
        return ResponseEntity.ok(carService.updateCarInformation(request));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteCar(@PathVariable long id) {
        carService.deleteCarById(id);
        return ResponseEntity.ok().build();
    }
}
