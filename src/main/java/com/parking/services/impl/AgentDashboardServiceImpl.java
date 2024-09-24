package com.parking.services.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.dto.AgentDashboardDto;
import com.parking.repository.ParkingTicketRepository;
import com.parking.repository.TransactionRepository;
import com.parking.services.AgentDashboardService;

@Service
public class AgentDashboardServiceImpl implements AgentDashboardService {
	
	private final TransactionRepository transactionRepository;
	private final ParkingTicketRepository parkingTicketRepository;
	
	@Autowired
	public AgentDashboardServiceImpl(TransactionRepository transactionRepository, ParkingTicketRepository parkingTicketRepository) {
		this.transactionRepository = transactionRepository;
		this.parkingTicketRepository = parkingTicketRepository;
	}
	
	@Override
	public AgentDashboardDto getAgentDashboard(Long agentId, Long parkingSpaceId) {
		
		BigDecimal todaysCollection = transactionRepository.findTodaysCollectionByAgent(agentId);
		Long vehiclesInParking = parkingTicketRepository.countVehiclesInParkingByAgent(parkingSpaceId);
		Long unpaidTickets = parkingTicketRepository.countUnpaidTicketsByAgent(agentId);
		
		AgentDashboardDto agentDashboardDto = new AgentDashboardDto();
		agentDashboardDto.setTodaysCollection(todaysCollection);
		agentDashboardDto.setVehiclesInParking(vehiclesInParking);
		agentDashboardDto.setUnpaidTickets(unpaidTickets);
		
		return agentDashboardDto;
	}

}
