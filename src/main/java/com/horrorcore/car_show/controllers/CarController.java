package com.horrorcore.car_show.controllers;

import com.horrorcore.car_show.dtos.CarRequest;
import com.horrorcore.car_show.services.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping(value = {"/", ""})
    public String carIndex(Model model) {
        log.info("l20:mjm:carIndex: Loading List of Cars into Model");
        model.addAttribute("listOfCars", carService.getAllCars());
        log.info("l22:mjm:carIndex: List of Cars Loaded");
        return "car/index";
    }

    @GetMapping(value = {"/create", "/create/"})
    public String carForm(Model model) {
        model
                .addAttribute(
                        "newCar",
                        new CarRequest(
                                "",
                                "",
                                "",
                                "",
                                (byte) 0));
        return "car/form";
    }
}
