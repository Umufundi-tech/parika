package com.parking.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.parking.model.Transaction;
import com.parking.model.VehiculeAccount;

import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select t from Transaction t order by t.id desc")
    Page<Transaction> findAllTransaction(Pageable pageable);

    List<Transaction> findByAccountId(VehiculeAccount account);

    @Query("select sum(t.transactionAmount) from  Transaction t where t.account.id= :accountId")
    BigDecimal accountSold(@Param("accountId") Long accountId);
    
    // Recuperer la somme de l'argent d'un agent collecte par jour
    @Query("SELECT COALESCE(SUM(t.transactionAmount), 0) "
    		+ "FROM Transaction t "
    		+ "JOIN Payment p ON t.id = p.transaction.id "
    		+ "JOIN ParkingTicket pt ON p.parkingTicket.id = pt.id "
    		+ "WHERE pt.agent.id = :agentId "
    		+ "AND t.transactionType = 'PAYMENT' "
    		+ "AND t.transactionDate = CURRENT_DATE ")
    BigDecimal findTodaysCollectionByAgent(@Param("agentId") Long agentId);
}
