package com.parking.services;

import java.util.List;

import com.parking.dto.VehiculeAccountDto;

public interface VehicleAccountService {
	
	VehiculeAccountDto findById(Long id);
	List<VehiculeAccountDto> findAll();
}
