package com.horrorcore.car_show.entities;

import com.horrorcore.car_show.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ownerId;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    private List<Car> cars;
}
