package com.parking.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.dto.SuperadminDashboardDto;
import com.parking.repository.CompanyRepository;
import com.parking.repository.VehicleRepository;
import com.parking.repository.VehicleTypeRepository;
import com.parking.services.SuperadminDashboardService;

@Service
public class SuperadminDashboardServiceImpl implements SuperadminDashboardService {
	
	private CompanyRepository companyRepository;
	private VehicleRepository vehicleRepository;
	private VehicleTypeRepository vehicleTypeRepository;
	
	@Autowired
	public SuperadminDashboardServiceImpl(CompanyRepository companyRepository, VehicleRepository vehicleRepository, VehicleTypeRepository vehicleTypeRepository) {
		this.companyRepository = companyRepository;
		this.vehicleRepository = vehicleRepository;
		this.vehicleTypeRepository = vehicleTypeRepository;
	}
	
	@Override
	public SuperadminDashboardDto getSuperadminDashboard() {
		
		Long companyCount = companyRepository.count();
		Long vehicleCount = vehicleRepository.count();
		Long vehicleTypeCount = vehicleTypeRepository.count();
		
		SuperadminDashboardDto superadminDashboardDto = new SuperadminDashboardDto();
		superadminDashboardDto.setCompanyCount(companyCount);
		superadminDashboardDto.setVehicleCount(vehicleCount);
		superadminDashboardDto.setVehicleTypeCount(vehicleTypeCount);
		
		return superadminDashboardDto;
	}

}
