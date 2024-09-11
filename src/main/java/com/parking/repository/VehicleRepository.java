package com.parking.repository;

import java.util.List;
import java.util.Optional;

import com.parking.model.VehicleType;
import com.parking.projection.VehicleProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parking.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query(value = "select v from Vehicle v where v.id = :id")
    Vehicle findVehicleById(@Param("id") Long id);

    @Query(value = "select ve from Vehicle ve order by ve.id desc")
    Page<Vehicle> findAllVehicle(Pageable pageable);

    @Query(value = "select ve from Vehicle ve where ve.registrationNumber = :number")
    Optional<Vehicle> findVehicleByRegistrationNumber(@Param("number") String number);

    @Query(value = "select ve from Vehicle ve join VehicleType vt on ve.vehicleType.id=vt.id where UPPER(ve.registrationNumber) like CONCAT('%',UPPER(?1),'%' ) OR UPPER(vt.vehiculeTypeName) like CONCAT('%',UPPER(?1),'%' ) order by ve.id desc ")
    Page<Vehicle> findByVehiculeRegistrationNumberLike(String search, Pageable pageable);
    
    @Query("SELECT v.id AS idVehicle, v.registrationNumber AS registrationNumber, v.vehicleType AS vehicleTypeDto, va.accountNumber AS accountNumber, " +
    		"COALESCE(SUM(t.transactionAmount), 0) AS solde, va.qrCodeImage AS qrCodeImage " +
    		"FROM Vehicle v " +
    		"JOIN v.vehicleType vt " +
    		"JOIN v.account va " +
    		"LEFT JOIN va.transactions t " +
    		"WHERE v.id = :idVehicle " +
    		"GROUP BY v.id, v.registrationNumber, vt, va.accountNumber, va.qrCodeImage")
    VehicleProjection findVehicleDetails(Long idVehicle);
    
    @Query("SELECT v.id AS idVehicle, v.registrationNumber AS registrationNumber, v.vehicleType AS vehicleTypeDto, va.accountNumber AS accountNumber, " +
    		"COALESCE(SUM(t.transactionAmount), 0) AS solde, va.qrCodeImage AS qrCodeImage, pp.price AS price " +
    		"FROM Vehicle v " +
    		"JOIN v.vehicleType vt " +
    		"LEFT JOIN vt.parkingPrices pp " +
    		"JOIN pp.company c " +
    		"JOIN v.account va " +
    		"LEFT JOIN va.transactions t " +
    		"WHERE v.registrationNumber = :registrationNumber " +
    		"AND c.id = :idCompany " +
    		"GROUP BY v.id, v.registrationNumber, vt, va.accountNumber, va.qrCodeImage, pp.price")
    VehicleProjection findVehicleDetailsByRegistrationNumber(String registrationNumber, Long idCompany);
    
    List<Vehicle> findAllByVehicleTypeId(Long vehicleType_id);
    
}
