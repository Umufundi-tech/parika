package com.parking.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle extends AbstractEntity {

    @Column(name = "registration_number")
    private String registrationNumber;
    
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id")
    private VehicleType vehicleType;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "vehicle")
    private VehiculeAccount account;
}
