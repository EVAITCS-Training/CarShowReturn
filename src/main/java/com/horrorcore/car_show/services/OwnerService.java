package com.horrorcore.car_show.services;

import com.horrorcore.car_show.dtos.OwnerRequest;
import com.horrorcore.car_show.dtos.OwnerResponse;

import java.util.List;

public interface OwnerService {
    OwnerResponse createOwner(OwnerRequest ownerRequest);
    List<OwnerResponse> getAllOwners();
    OwnerResponse getOwnerById(long id);
    List<OwnerResponse> getOwersByFirstName(String firstName);
    List<OwnerResponse> getOwnersByLastName(String lastName);
    List<OwnerResponse> getOwnersByDateOfBirth (String dateOfBirth);
    OwnerResponse updateOwnerInformation(OwnerRequest ownerRequest);
    void deleteOwnerById(long id);
}
