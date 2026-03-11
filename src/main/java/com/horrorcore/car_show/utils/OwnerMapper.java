package com.horrorcore.car_show.utils;


import com.horrorcore.car_show.dtos.OwnerRequest;
import com.horrorcore.car_show.dtos.OwnerResponse;
import com.horrorcore.car_show.entities.Owner;

public class OwnerMapper {

    public static Owner fromDto(OwnerRequest request){
        return new Owner(
                request.carId(),
                request.firstName(),
                request.lastName(),
                request.gender(),
                request.dateOfBirth()
                ,null
        );
    }


    public static OwnerResponse toDto(Owner owner){

        return new OwnerResponse(
                owner.getOwnerId(),
                owner.getFirstName(),
                owner.getLastName(),
                owner.getGender(),
                owner.getDateOfBirth()
        );
    }


}
