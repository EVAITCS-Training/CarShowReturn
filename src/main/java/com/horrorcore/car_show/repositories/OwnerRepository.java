package com.horrorcore.car_show.repositories;

import com.horrorcore.car_show.entities.Owner;
import com.horrorcore.car_show.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    List<Owner> findAllByFirstName(String firstName);
    List<Owner> findAllByLastName(String lastName);
    List<Owner> findAllByDateOfBirth(LocalDate dateOfBirth);
    List<Owner> findAllByGender(Gender gender);
}