package com.parking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyDashboardDto {
	
	private Long parkingSpaceCount;
	private Long agentCount;
	
	public CompanyDashboardDto() {
		
	}
	
	public CompanyDashboardDto(Long parkingSpaceCount, Long agentCount) {
		this.parkingSpaceCount = parkingSpaceCount;
		this.agentCount = agentCount;
	}
}
