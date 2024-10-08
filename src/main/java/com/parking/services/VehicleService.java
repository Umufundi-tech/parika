package com.parking.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.parking.dto.VehicleDto;
import com.parking.dto.VehicleListDto;
import com.parking.projection.VehicleProjection;

public interface VehicleService {

    VehicleDto save(VehicleDto dto);

    VehicleDto findById(Long id);

    Page<VehicleListDto> findByVehicleRegistrationNumber(String search, Pageable pageable);
    
    VehicleListDto getVehicleDetails(Long idVehicle);
    
    VehicleListDto getVehicleDetailsByRegistrationNumber(String registrationNumber, Long idCompany);

    void delete(Long id);
}
