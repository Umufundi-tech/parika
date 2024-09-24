package com.parking.services;

import com.parking.dto.AgentDashboardDto;

public interface AgentDashboardService {
	
	AgentDashboardDto getAgentDashboard(Long agentId, Long parkingSpaceId);
}
