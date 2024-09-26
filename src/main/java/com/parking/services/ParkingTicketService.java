package com.parking.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.parking.dto.ParkingTicketDto;
import com.parking.dto.ParkingTicketListDto;

public interface ParkingTicketService {

    ParkingTicketDto entryParking(ParkingTicketDto dto);

    ParkingTicketDto exitParking(Long parkingTicketId);

    ParkingTicketListDto findById(Long id);
    
    List<ParkingTicketDto> findAll();

    Page<ParkingTicketDto> findAllParkingTicket(Pageable pageable);
    
    Page<ParkingTicketListDto> findActiveParkingTIcketByParkingSpaceId(Long parkingSpaceId, String search, Pageable pageable);
    
    ParkingTicketDto getActiveParkingTicketByParkingSpaceAndRegistrationNumber(Long parkingSpaceId, String registrationNumber);
    
    Page<ParkingTicketListDto> getVehiclesInParking(Long parkingSpaceId, Long agentId, String search, Pageable pageable);
    
    Page<ParkingTicketListDto> getUnpaidTicketsByAgent(Long agentId, Long parkingSpaceId, String search, Pageable pageable);

    void delete (Long id);
}
