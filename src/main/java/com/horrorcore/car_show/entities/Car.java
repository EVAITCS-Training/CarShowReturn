package com.horrorcore.car_show.entities;

import com.horrorcore.car_show.enums.EngineType;
import com.horrorcore.car_show.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long carId;
    private String make;
    private String model;
    private EngineType engineType;
    private byte doorCount;
    private VehicleType vehicleType;

    @ManyToOne
    private Owner owner;
}
