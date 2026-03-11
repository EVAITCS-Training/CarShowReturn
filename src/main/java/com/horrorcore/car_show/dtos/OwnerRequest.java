package com.horrorcore.car_show.dtos;


import com.horrorcore.car_show.enums.Gender;

import java.time.LocalDate;

public record OwnerRequest (
        long carId,
        String firstName,
        String lastName,
        Gender gender,
        LocalDate dateOfBirth){

    public OwnerRequest(long carId, String firstName, String lastName, Gender gender, LocalDate dateOfBirth){
        this.carId = 0; // Force carId to 0
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }
}
