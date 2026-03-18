package com.horrorcore.car_show.services;

import com.horrorcore.car_show.dtos.OwnerRequest;
import com.horrorcore.car_show.entities.Owner;
import com.horrorcore.car_show.enums.Gender;
import com.horrorcore.car_show.exceptions.InvalidOwnerIdException;
import com.horrorcore.car_show.repositories.CarRepository;
import com.horrorcore.car_show.repositories.OwnerRepository;
import com.horrorcore.car_show.services.OwnerServiceImpl;
import com.horrorcore.car_show.services.testutils.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceImplTest {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @Test
    void shouldCreateOwner_whenValidRequest_thenReturnOwnerResponse() {
        Owner owner = TestDataFactory.createOwnerEntity(1L);
        OwnerRequest req = TestDataFactory.createOwnerRequest(1L);

        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        var resp = ownerService.createOwner(req);

        assertNotNull(resp);
        assertEquals(owner.getOwnerId(), resp.ownerId());
        verify(ownerRepository, times(1)).save(any(Owner.class));
    }

    @Test
    void shouldGetAllOwners_returnListOfOwnerResponses() {
        Owner owner = TestDataFactory.createOwnerEntity(2L);
        when(ownerRepository.findAll()).thenReturn(List.of(owner));

        var list = ownerService.getAllOwners();

        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    void shouldGetOwnerById_whenExists_thenReturnOwnerResponse() {
        Owner owner = TestDataFactory.createOwnerEntity(3L);
        when(ownerRepository.findById(3L)).thenReturn(Optional.of(owner));

        var resp = ownerService.getOwnerById(3L);

        assertNotNull(resp);
        assertEquals(3L, resp.ownerId());
    }

    @Test
    void shouldThrowInvalidOwnerIdException_whenGetOwnerByIdNotFound() {
        when(ownerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(InvalidOwnerIdException.class, () -> ownerService.getOwnerById(99L));
    }

    @Test
    void shouldGetOwnersByFirstName_returnMatchingOwners() {
        Owner owner = TestDataFactory.createOwnerEntity(4L);
        when(ownerRepository.findAllByFirstName("John")).thenReturn(List.of(owner));

        var list = ownerService.getOwersByFirstName("John");

        assertEquals(1, list.size());
        assertEquals("John", list.get(0).firstName());
    }

    @Test
    void shouldGetOwnersByLastName_returnMatchingOwners() {
        Owner owner = TestDataFactory.createOwnerEntity(5L);
        when(ownerRepository.findAllByLastName("Doe")).thenReturn(List.of(owner));

        var list = ownerService.getOwnersByLastName("Doe");

        assertEquals(1, list.size());
        assertEquals("Doe", list.get(0).lastName());
    }

    @Test
    void shouldGetOwnersByDateOfBirth_returnMatchingOwners() {
        Owner owner = TestDataFactory.createOwnerEntity(6L);
        LocalDate dob = LocalDate.of(1990,1,1);
        when(ownerRepository.findAllByDateOfBirth(dob)).thenReturn(List.of(owner));

        var list = ownerService.getOwnersByDateOfBirth("1990-01-01");

        assertEquals(1, list.size());
    }

    @Test
    void shouldGetOwnersByGender_returnMatchingOwners() {
        Owner owner = TestDataFactory.createOwnerEntity(7L);
        when(ownerRepository.findAllByGender(Gender.MALE)).thenReturn(List.of(owner));

        var list = ownerService.getOwnersByGender("MALE");

        assertEquals(1, list.size());
    }

    @Test
    void shouldUpdateOwnerInformation_whenCarRepoHasId_thenReturnUpdatedResponse() {
        Owner updated = TestDataFactory.createOwnerEntity(8L);
        when(carRepository.existsById(anyLong())).thenReturn(true);
        when(ownerRepository.save(any(Owner.class))).thenReturn(updated);

        var resp = ownerService.updateOwnerInformation(TestDataFactory.createOwnerRequest(8L));

        assertNotNull(resp);
        assertEquals(8L, resp.ownerId());
    }

    @Test
    void shouldReturnNull_whenUpdateOwnerInformationCarRepoDoesNotHaveId() {
        when(carRepository.existsById(anyLong())).thenReturn(false);

        var resp = ownerService.updateOwnerInformation(TestDataFactory.createOwnerRequest(9L));

        assertNull(resp);
    }

    @Test
    void shouldDeleteOwnerById_whenExists_thenDeleteCalled() {
        when(ownerRepository.existsById(10L)).thenReturn(true);

        ownerService.deleteOwnerById(10L);

        verify(ownerRepository, times(1)).deleteById(10L);
    }

    @Test
    void shouldThrowInvalidOwnerIdException_whenDeleteOwnerByIdNotExists() {
        when(ownerRepository.existsById(11L)).thenReturn(false);

        assertThrows(com.horrorcore.car_show.exceptions.InvalidOwnerIdException.class, () -> ownerService.deleteOwnerById(11L));
    }
}



