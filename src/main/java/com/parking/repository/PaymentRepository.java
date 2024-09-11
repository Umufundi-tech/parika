package com.parking.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.parking.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "select p from Payment p order by p.id desc")
    Page<Payment> findAllPayment(Pageable pageable);
    
    List<Payment> findAllByTransaction_id(Long transaction_id);
}
