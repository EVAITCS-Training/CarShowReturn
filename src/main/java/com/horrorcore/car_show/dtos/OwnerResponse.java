package com.horrorcore.car_show.dtos;

import com.horrorcore.car_show.enums.Gender;

import java.time.LocalDate;

public record OwnerResponse(
        long carId,
        String firstName,
        String lastName,
        Gender gender,
        LocalDate dateOfBirth
) {

}
