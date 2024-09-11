package com.parking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.parking.controller.api.ParkingTicketApi;
import com.parking.dto.ParkingTicketDto;
import com.parking.services.ParkingTicketService;

@RestController
public class ParkingTicketController implements ParkingTicketApi {

    private final ParkingTicketService parkingTicketService;

    @Autowired
    public ParkingTicketController(ParkingTicketService parkingTicketService) {
        this.parkingTicketService = parkingTicketService;
    }

    @Override
    public ParkingTicketDto entryParking(ParkingTicketDto dto) {
        return parkingTicketService.entryParking(dto);
    }

    @Override
    public ParkingTicketDto exitParking(Long parkingTicketId) {
        return parkingTicketService.exitParking(parkingTicketId);
    }

    @Override
    public ParkingTicketDto findById(Long id) {
    	
        return parkingTicketService.findById(id);
    }

    @Override
    public void delete(Long id) {

    }

	@Override
	public List<ParkingTicketDto> findAll() {
		
		return parkingTicketService.findAll();
	}

	@Override
	public Page<ParkingTicketDto> findActiveParkingTicketByParkingSpaceId(Long parkingSpaceId, String search, int page,
			int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		return parkingTicketService.findActiveParkingTIcketByParkingSpaceId(parkingSpaceId, search, pageable);
	}

	@Override
	public ParkingTicketDto getActiveParkingTicketByParkingSpaceAndRegistrationNumber(Long parkingSpaceId,
			String registrationNumber) {
		
		return parkingTicketService.getActiveParkingTicketByParkingSpaceAndRegistrationNumber(parkingSpaceId, registrationNumber);
	}
}
