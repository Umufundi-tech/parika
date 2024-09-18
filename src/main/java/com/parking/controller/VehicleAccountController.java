package com.parking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.parking.controller.api.VehicleAccountApi;
import com.parking.dto.VehiculeAccountDto;
import com.parking.services.VehicleAccountService;

@RestController
public class VehicleAccountController implements VehicleAccountApi {
	
	private final VehicleAccountService vehicleAccountService;
	
	@Autowired
	public VehicleAccountController(VehicleAccountService vehicleAccountService) {
		this.vehicleAccountService = vehicleAccountService;
	}

	@Override
	public VehiculeAccountDto findById(Long id) {
		
		return vehicleAccountService.findById(id);
	}

	@Override
	public List<VehiculeAccountDto> findAll() {
		
		return vehicleAccountService.findAll();
	}

}
