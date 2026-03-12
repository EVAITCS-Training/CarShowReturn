package com.horrorcore.car_show.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CarRequest(
        Long carId,
        @NotBlank(message = "Make is required.")
        @Size(min = 2, max = 50, message = "Make must be between 2 and 50 characters.")
        @Pattern(
                regexp = "^[A-Za-z][A-Za-z0-9 .'-]{0,48}[A-Za-z0-9]$",
                message = "Make must look like a real manufacturer name."
        )
        String make,
        @NotBlank(message = "Model is required.")
        @Size(min = 2, max = 50, message = "Model must be between 2 and 50 characters.")
        @Pattern(
                regexp = "^[A-Za-z0-9][A-Za-z0-9 .'-]{0,48}[A-Za-z0-9]$",
                message = "Model must look like a real vehicle model."
        )
        String model,
        @NotBlank(message = "Engine type is required.")
        @Pattern(
                regexp = "^(V6_ENGINE|V8_ENGINE)$",
                message = "Engine type must be V6 Engine or V8 Engine."
        )
        String engineType,
        @NotBlank(message = "Vehicle type is required.")
        @Pattern(
                regexp = "^(SUDAN|TRUCK|SUV|RV)$",
                message = "Vehicle type must be Sudan, Truck, SUV, or RV."
        )
        String vehicleType,
        @Min(value = 2, message = "Door count must be at least 2.")
        @Max(value = 6, message = "Door count must be no more than 6.")
        byte doorCount
) {
    public CarRequest(String make, String model, String engineType, String vehicleType, byte doorCount) {
        this(0L, make, model, engineType, vehicleType, doorCount);
    }
}
