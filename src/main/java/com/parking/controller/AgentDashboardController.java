package com.parking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.parking.controller.api.AgentDashboardApi;
import com.parking.dto.AgentDashboardDto;
import com.parking.services.AgentDashboardService;

@RestController
public class AgentDashboardController implements AgentDashboardApi {
	
	private final AgentDashboardService agentDashboardService;
	
	@Autowired
	public AgentDashboardController(AgentDashboardService agentDashboardService) {
		this.agentDashboardService = agentDashboardService;
	}

	@Override
	public AgentDashboardDto findAgentDashboardByAgentIdAndParkingSpaceId(Long agentId, Long parkingSpaceId) {
		
		return agentDashboardService.getAgentDashboard(agentId, parkingSpaceId);
	}
	
}
