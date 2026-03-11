package com.horrorcore.car_show.services;

import com.horrorcore.car_show.dtos.OwnerRequest;
import com.horrorcore.car_show.dtos.OwnerResponse;
import com.horrorcore.car_show.entities.Owner;
import com.horrorcore.car_show.enums.Gender;
import com.horrorcore.car_show.exceptions.InvalidOwnerIdException;
import com.horrorcore.car_show.repositories.CarRepository;
import com.horrorcore.car_show.repositories.OwnerRepository;
import com.horrorcore.car_show.utils.OwnerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final CarRepository carRepository;

    @Override
    public OwnerResponse createOwner(OwnerRequest ownerRequest) {
        Owner owner = OwnerMapper.fromDto(ownerRequest);

        return OwnerMapper.toDto(ownerRepository.save(owner));
    }

    @Override
    public List<OwnerResponse> getAllOwners() {
        return ownerRepository.findAll().stream()
                .map(OwnerMapper :: toDto).toList();
    }

    @Override
    public OwnerResponse getOwnerById(long id) {
        return OwnerMapper.toDto(
                ownerRepository.findById(id)
                        .orElseThrow(
                                () -> new InvalidOwnerIdException("Owner id " + id + " not found")
                        )
        );
    }

    @Override
    public List<OwnerResponse> getOwersByFirstName(String firstName) {
        return ownerRepository.findAllByFirstName(firstName)
                .stream().map(OwnerMapper :: toDto)
                .toList();
    }

    @Override
    public List<OwnerResponse> getOwnersByLastName(String lastName) {
        return ownerRepository.findAllByLastName(lastName).stream()
                .map(OwnerMapper :: toDto)
                .toList();
    }

    @Override
    public List<OwnerResponse> getOwnersByDateOfBirth(String dateOfBirth) {
        return ownerRepository.findAllByDateOfBirth(LocalDate.parse(dateOfBirth)).stream()
                .map(OwnerMapper:: toDto)
                .toList();

    }

    public List<OwnerResponse> getOwnersByGender(String gender){
        return ownerRepository.findAllByGender(Gender.valueOf(gender)).stream()
                .map(OwnerMapper :: toDto)
                .toList();
    }

    @Override
    public OwnerResponse updateOwnerInformation(OwnerRequest ownerRequest) {
        Owner newOwnerInfo = OwnerMapper.fromDto(ownerRequest);
        if(carRepository.existsById(newOwnerInfo.getOwnerId())){
            return OwnerMapper.toDto(ownerRepository.save(newOwnerInfo));
        }
        return null;
    }

    @Override
    public void deleteCarById(long id) {
        if (!ownerRepository.existsById(id)) throw new InvalidOwnerIdException("Owner Id " + " not found!");
        ownerRepository.deleteById(id);

    }
}
