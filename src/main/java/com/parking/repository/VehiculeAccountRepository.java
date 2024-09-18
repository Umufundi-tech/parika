package com.parking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parking.model.VehiculeAccount;

public interface VehiculeAccountRepository extends JpaRepository<VehiculeAccount, Long> {

    @Query(value = "select a from VehiculeAccount a order by a.id desc")
    Page<VehiculeAccount> findAllAccount(Pageable pageable);
    
    List<VehiculeAccount> findVehiculeAccountByVehicleId(Long vehicle_id);
    
    VehiculeAccount findByVehicleId(Long vehicule_id);
    
    //JPQL query
    @Query(value = "select va from VehiculeAccount va where va.qrCodeString = :qrCodeString")
    Optional<VehiculeAccount> findVehicleAccountByQrCodeString(@Param("qrCodeString") String qrCodeString);
}
