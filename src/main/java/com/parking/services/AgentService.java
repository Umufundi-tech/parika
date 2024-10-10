package com.parking.services;

import java.util.List;

import com.parking.dto.AgentDto;
import com.parking.dto.ParkingSpaceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AgentService {
	
	AgentDto save(AgentDto dto);
	
	AgentDto findById(Long id);
	
	List<AgentDto> findAll();

	Page<AgentDto> findCompanyAgents(Long idCompany, String search, Pageable pageable);
	
	List<AgentDto> findAgentsByCompany(Long companyId);
	
	void delete(Long id);
}
