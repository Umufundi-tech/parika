package com.parking.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.dto.VehicleTypeDto;
import com.parking.dto.VehiculeAccountDto;
import com.parking.exceptions.EntityNotFoundException;
import com.parking.exceptions.ErrorCodes;
import com.parking.repository.VehiculeAccountRepository;
import com.parking.services.VehicleAccountService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VehicleAccountServiceImpl implements VehicleAccountService {
	
	private final VehiculeAccountRepository vehiculeAccountRepository;
	
	@Autowired
	public VehicleAccountServiceImpl(VehiculeAccountRepository vehiculeAccountRepository) {
		this.vehiculeAccountRepository = vehiculeAccountRepository;
	}

	@Override
	public VehiculeAccountDto findById(Long id) {
		
		if(id == null) {
			log.error("Vehicle Account is null");
		}
		return vehiculeAccountRepository.findById(id)
				.map(VehiculeAccountDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucun compte de vehicle avec l'ID = " +id+ " n' a ete trouve dans la BDD ",
						ErrorCodes.VEHICLEACCOUNT_NOT_FOUND)
						);
	}

	@Override
	public List<VehiculeAccountDto> findAll() {
		
		return vehiculeAccountRepository.findAll().stream()
				.map(VehiculeAccountDto::fromEntity)
				.collect(Collectors.toList());
	}
	
}
