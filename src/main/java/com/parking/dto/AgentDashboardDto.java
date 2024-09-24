package com.parking.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentDashboardDto {
	
	private BigDecimal todaysCollection;
	private Long vehiclesInParking;
	private Long unpaidTickets;
	
	public AgentDashboardDto() {
		
	}
	
	public AgentDashboardDto(BigDecimal todaysCollection, Long vehiclesInParking, Long unpaidTickets) {
		this.todaysCollection = todaysCollection;
		this.vehiclesInParking = vehiclesInParking;
		this.unpaidTickets = unpaidTickets;
	}
}
