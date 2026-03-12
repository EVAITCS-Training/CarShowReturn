package com.horrorcore.car_show.controllers;

import com.horrorcore.car_show.dtos.CarRequest;
import com.horrorcore.car_show.dtos.CarResponse;
import com.horrorcore.car_show.services.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarService carService;

    @Test
    void createCarShouldRedisplayFormWhenValidationFails() throws Exception {
        mockMvc.perform(post("/car/create")
                        .param("make", "B")
                        .param("model", "X")
                        .param("engineType", "")
                        .param("vehicleType", "")
                        .param("doorCount", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("car/form"))
                .andExpect(model().attributeHasFieldErrors(
                        "newCar",
                        "make",
                        "model",
                        "engineType",
                        "vehicleType",
                        "doorCount"
                ));

        verify(carService, never()).createCar(any(CarRequest.class));
    }

    @Test
    void createCarShouldPersistAndRedirectWhenValidationPasses() throws Exception {
        when(carService.createCar(any(CarRequest.class)))
                .thenReturn(new CarResponse(1L, "Ford", "F-150", "V8_ENGINE", "TRUCK", (byte) 4));

        mockMvc.perform(post("/car/create")
                        .param("carId", "0")
                        .param("make", "Ford")
                        .param("model", "F-150")
                        .param("engineType", "V8_ENGINE")
                        .param("vehicleType", "TRUCK")
                        .param("doorCount", "4"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car"));

        verify(carService).createCar(any(CarRequest.class));
    }
}

