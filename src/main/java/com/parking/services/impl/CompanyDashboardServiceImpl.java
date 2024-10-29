package com.parking.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.dto.CompanyDashboardDto;
import com.parking.repository.AgentRepository;
import com.parking.repository.ParkingSpaceRepository;
import com.parking.services.CompanyDashboardService;

@Service
public class CompanyDashboardServiceImpl implements CompanyDashboardService {
	
	private ParkingSpaceRepository parkingSpaceRepository;
	private AgentRepository agentRepository;
	
	@Autowired
	public CompanyDashboardServiceImpl(ParkingSpaceRepository parkingSpaceRepository, AgentRepository agentRepository) {
		this.parkingSpaceRepository = parkingSpaceRepository;
		this.agentRepository = agentRepository;
	}

	@Override
	public CompanyDashboardDto getCompanyDashboard(Long companyId) {
		
		Long parkingSpaceCount = parkingSpaceRepository.countByCompanyId(companyId);
		Long agentCount = agentRepository.countByCompanyId(companyId);
		
		CompanyDashboardDto companyDashboardDto = new CompanyDashboardDto();
		companyDashboardDto.setParkingSpaceCount(parkingSpaceCount);
		companyDashboardDto.setAgentCount(agentCount);
		
		return companyDashboardDto;
	}
}
