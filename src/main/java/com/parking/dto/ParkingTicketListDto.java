package com.parking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.parking.model.ParkingTicket;
import com.parking.model.ParkingTicketPaymentStatusEnum;
import com.parking.model.ParkingTicketStatusEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParkingTicketListDto {
	
	private Long id;
    private String parkingTicketNumber;
    private ParkingSpaceDto parkingSpace;
    private AgentDto agent;
    private CompanyDto company;
    private VehicleDto vehicle;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private BigDecimal unitPrice;
    private BigDecimal fareAmount;
    private double duration;
    private ParkingTicketStatusEnum parkingTicketStatusEnum;
    private ParkingTicketPaymentStatusEnum parkingTicketPaymentStatusEnum;
	private VehiculeAccountDto account;
	
	public static ParkingTicketListDto fromEntity(ParkingTicket parkingTicket) {
        if(parkingTicket == null) {
            return null;
        }

        return ParkingTicketListDto.builder()
                .id(parkingTicket.getId())
                .parkingTicketNumber(parkingTicket.getParkingTicketNumber())
                .parkingSpace(ParkingSpaceDto.fromEntity(parkingTicket.getParkingSpace()))
                .agent(AgentDto.fromEntity(parkingTicket.getAgent()))
                .company(CompanyDto.fromEntity(parkingTicket.getCompany()))
                .vehicle(VehicleDto.fromEntity(parkingTicket.getVehicle()))
                .entryTime(parkingTicket.getEntryTime())
                .exitTime(parkingTicket.getExitTime())
                .unitPrice(parkingTicket.getUnitPrice())
                .fareAmount(parkingTicket.getFareAmount())
                .duration(parkingTicket.getDuration())
                .parkingTicketPaymentStatusEnum(parkingTicket.getParkingTicketPaymentStatusEnum())
                .parkingTicketStatusEnum(parkingTicket.getParkingTicketStatusEnum())
                .account(VehiculeAccountDto.fromEntity(parkingTicket.getVehicle().getAccount()))
                .build();
    }
	
	public static ParkingTicket toEntity(ParkingTicketListDto parkingTicketListDto) {
        if(parkingTicketListDto == null) {
            return null;
        }

        ParkingTicket parkingTicket = new ParkingTicket();
        parkingTicket.setId(parkingTicketListDto.getId());
        parkingTicket.setParkingTicketNumber(parkingTicketListDto.getParkingTicketNumber());
        parkingTicket.setParkingSpace(ParkingSpaceDto.toEntity(parkingTicketListDto.getParkingSpace()));
        parkingTicket.setAgent(AgentDto.toEntity(parkingTicketListDto.getAgent()));
        parkingTicket.setCompany(CompanyDto.toEntity(parkingTicketListDto.getCompany()));
        parkingTicket.setVehicle(VehicleDto.toEntity(parkingTicketListDto.getVehicle()));
        parkingTicket.setEntryTime(parkingTicketListDto.getEntryTime());
        parkingTicket.setExitTime(parkingTicketListDto.getExitTime());
        parkingTicket.setUnitPrice(parkingTicketListDto.getUnitPrice());
        parkingTicket.setDuration(parkingTicketListDto.getDuration());
        parkingTicket.setParkingTicketPaymentStatusEnum(parkingTicketListDto.getParkingTicketPaymentStatusEnum());
        parkingTicket.setParkingTicketStatusEnum(parkingTicketListDto.getParkingTicketStatusEnum());

        return parkingTicket;
    }
}
