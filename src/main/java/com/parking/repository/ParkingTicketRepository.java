package com.parking.repository;
import com.parking.model.Company;
import com.parking.model.Vehicle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.parking.model.ParkingTicket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {

    @Query(value = "select pt from ParkingTicket pt where pt.vehicle.registrationNumber = :number AND pt.parkingTicketPaymentStatusEnum = 'UNPAID' ")
    Optional<ParkingTicket> findVehicleUnpaidTicketByRegistrationNumber(@Param("number") String number);

    @Query(value = "select pt from ParkingTicket pt where pt.id = :id AND pt.parkingTicketPaymentStatusEnum = 'PAID'")
    Optional<ParkingTicket> findVehiclePaidTicketById(@Param("id") Long id);

    @Query(value = "select pt from ParkingTicket pt where pt.id = :id AND pt.parkingTicketStatusEnum = 'CLOSED'")
    Optional<ParkingTicket> findVehicleCloseTicketById(@Param("id") Long id);
    
    ParkingTicket findParkingTicketById(Long id);
    
    @Query("SELECT pt FROM ParkingTicket pt " +
    		"WHERE pt.parkingSpace.id = ?1 AND pt.parkingTicketStatusEnum = 'ACTIVE' " +
    		"AND UPPER(pt.parkingTicketNumber) like CONCAT('%',UPPER(?2),'%') " +
    		"OR UPPER(pt.parkingSpace.name) like CONCAT('%',UPPER(?2),'%') " +
    		"OR UPPER(pt.agent.user.userFullName) like CONCAT('%',UPPER(?2),'%') " +
    		"OR UPPER(pt.company.companyName) like CONCAT('%',UPPER(?2),'%') " +
    		"OR UPPER(pt.company.companyPhoneNumber) like CONCAT('%',UPPER(?2),'%') " +
    		"OR UPPER(pt.vehicle.registrationNumber) like CONCAT('%',UPPER(?2),'%') " +
    		"OR UPPER(pt.vehicle.vehicleType.vehiculeTypeName) like CONCAT('%',UPPER(?2),'%') " +
    		"ORDER BY pt.id DESC")
    Page<ParkingTicket> findActiveParkingTicketByParkingSpaceId(Long parkingSpaceId, String search, Pageable pageable);
    
    @Query("SELECT pt FROM ParkingTicket pt " +
    		"WHERE pt.parkingSpace.id = ?1 AND pt.parkingTicketStatusEnum = 'ACTIVE' " +
    		"ORDER BY pt.id DESC")
    Page<ParkingTicket> findActiveParkingTicketByParkingSpaceIdWithNoSearch(Long parkingSpaceId, Pageable pageable);
    
    @Query("SELECT pt FROM ParkingTicket pt " +
    		"WHERE pt.parkingSpace.id = :parkingSpaceId AND pt.vehicle.registrationNumber = :registrationNumber " +
    		"AND pt.parkingTicketStatusEnum = 'ACTIVE' ")
    Optional<ParkingTicket> findActiveParkingTicketByParkingSpaceIdAndRegistrationNumber(Long parkingSpaceId, String registrationNumber);

}
