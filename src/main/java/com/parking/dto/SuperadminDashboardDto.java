package com.parking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuperadminDashboardDto {
	
	private Long companyCount;
	private Long vehicleCount;
	private Long vehicleTypeCount;
	
	public SuperadminDashboardDto() {
		
	}
	
	public SuperadminDashboardDto(Long companyCount, Long vehicleCount, Long vehicleTypeCount) {
		this.companyCount = companyCount;
		this.vehicleCount = vehicleCount;
		this.vehicleTypeCount = vehicleTypeCount;
	}
}
