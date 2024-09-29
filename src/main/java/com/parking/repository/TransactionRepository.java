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
    
    // Recuperer la liste de l'argent collecté (paiements) par un agent donné aujourd'hui sans search
    @Query("SELECT t FROM Transaction t "
    		+ "JOIN Payment p ON t.id = p.transaction.id "
    		+ "JOIN ParkingTicket pt ON p.parkingTicket.id = pt.id "
    		+ "WHERE pt.agent.id = :agentId "
    		+ "AND t.transactionType = 'PAYMENT' "
    		+ "AND t.transactionDate = CURRENT_DATE "
    		+ "ORDER BY t.id DESC ")
    Page<Transaction> findTodaysTransactionsByAgentWithNoSearch(@Param("agentId") Long agentId, Pageable pageable);
    
    // Recuperer la liste de l'argent collecté (paiements) par un agent donné aujourd'hui avec search
    @Query("SELECT t FROM Transaction t "
    		+ "JOIN Payment p ON t.id = p.transaction.id "
    		+ "JOIN ParkingTicket pt ON p.parkingTicket.id = pt.id "
    		+ "WHERE pt.agent.id = ?1 "
    		+ "AND t.transactionType = 'PAYMENT' "
    		+ "AND t.transactionDate = CURRENT_DATE "
    		+ "AND UPPER(t.transactionCode) like CONCAT('%', UPPER(?2), '%') "
    		+ "OR UPPER(t.account.accountNumber) like CONCAT('%', UPPER(?2), '%') "
    		+ "OR UPPER(t.account.vehicle.registrationNumber) like CONCAT('%', UPPER(?2), '%' ) "
    		+ "ORDER BY t.id DESC ")
    Page<Transaction> findTodaysTransactionsByAgent(@Param("agentId") Long agentId, String search, Pageable pageable);
    
                           // AGENT
    // --------------------------------------------------------------//
    // Recuperer la liste des transactions dans une periode donnée
    @Query("SELECT t FROM Transaction t "
    		+ "JOIN Payment p ON t.id = p.transaction.id "
    		+ "JOIN ParkingTicket pt ON p.parkingTicket.id = pt.id "
 	        + "JOIN Agent a ON pt.agent.id = a.id "
 	        + "WHERE (:agentId IS NULL OR a.id = :agentId) "
    		+ "AND t.transactionType = 'PAYMENT' "
    		+ "AND (t.transactionDate = CURRENT_DATE "
 	        + "OR (t.transactionDate BETWEEN :startDate AND :endDate)) "
    		+ "ORDER BY t.id DESC")
    Page<Transaction> findTransactionByAgent(
		@Param("agentId") Long agentId, 
		@Param("startDate") LocalDate startDate, 
		@Param("endDate") LocalDate endDate, 
		Pageable pageable
    );
    
    // Recuperer la somme des transactions
    @Query("SELECT COALESCE(SUM(t.transactionAmount), 0) FROM Transaction t "
    	       + "JOIN Payment p ON t.id = p.transaction.id "
    	       + "JOIN ParkingTicket pt ON p.parkingTicket.id = pt.id "
    	       + "JOIN Agent a ON pt.agent.id = a.id "
    	       + "WHERE (:agentId IS NULL OR a.id = :agentId) "
    	       + "AND t.transactionType = 'PAYMENT' "
    	       + "AND (t.transactionDate = CURRENT_DATE "
    	       + "OR (t.transactionDate BETWEEN :startDate AND :endDate)) ")
    BigDecimal findTotalTransactionAmountByAgent(
	    @Param("agentId") Long agentId, 
	    @Param("startDate") LocalDate startDate, 
	    @Param("endDate") LocalDate endDate
	);
    // --------------------------------------------------------------------------//
    
                                // COMPANY
    // --------------------------------------------------------------------------//
    // Recuperer la liste des transactions des entreprises (companies)
//    @Query("SELECT t FROM Transaction t "
//    	       + "JOIN Payment p ON t.id = p.transaction.id "
//    	       + "JOIN ParkingTicket pt ON p.parkingTicket.id = pt.id "
//    	       + "WHERE pt.company.id = :companyId "
//    	       + "AND (pt.parkingSpace.id = :parkingSpaceId OR :parkingSpaceId IS NULL) "
//    	       + "AND t.transactionType = 'PAYMENT' "
//    	       + "AND t.transactionDate = CURRENT_DATE "
//    	       + "OR t.transactionDate BETWEEN :startDate AND :endDate "
//    	       + "ORDER BY t.id DESC")
//	Page<Transaction> findTransactionsByCompanyAndParkingSpace(
//	        @Param("companyId") Long companyId, 
//	        @Param("parkingSpaceId") Long parkingSpaceId, 
//	        @Param("startDate") LocalDate startDate, 
//	        @Param("endDate") LocalDate endDate, 
//	        Pageable pageable
//	);
    
    @Query("SELECT t FROM Transaction t "
    	       + "JOIN Payment p ON t.id = p.transaction.id "
    	       + "JOIN ParkingTicket pt ON p.parkingTicket.id = pt.id "
    	       + "JOIN ParkingSpace ps ON pt.parkingSpace.id = ps.id "
    	       + "JOIN Company c ON ps.company.id = c.id "
    	       + "WHERE (:companyId IS NULL OR c.id = :companyId) "
    	       + "AND (:parkingSpaceId IS NULL OR ps.id = :parkingSpaceId) "
    	       + "AND t.transactionType = 'PAYMENT' "
    	       + "AND (t.transactionDate = CURRENT_DATE "
    	       + "OR (t.transactionDate BETWEEN :startDate AND :endDate)) "
    	       + "ORDER BY t.id DESC")
    	Page<Transaction> findTransactionsByCompanyAndParkingSpace(
    	    @Param("companyId") Long companyId, 
    	    @Param("parkingSpaceId") Long parkingSpaceId, 
    	    @Param("startDate") LocalDate startDate, 
    	    @Param("endDate") LocalDate endDate, 
    	    Pageable pageable
    	);

    
    // Recuperer la somme des transactions des entreprises (companies)
    @Query("SELECT COALESCE(SUM(t.transactionAmount), 0) FROM Transaction t "
    		   + "JOIN Payment p ON t.id = p.transaction.id "
 	           + "JOIN ParkingTicket pt ON p.parkingTicket.id = pt.id "
 	           + "JOIN ParkingSpace ps ON pt.parkingSpace.id = ps.id "
 	           + "JOIN Company c ON ps.company.id = c.id "
 	           + "WHERE (:companyId IS NULL OR c.id = :companyId) "
 	           + "AND (:parkingSpaceId IS NULL OR ps.id = :parkingSpaceId) "
    	       + "AND t.transactionType = 'PAYMENT' "
    	       + "AND (t.transactionDate = CURRENT_DATE "
    	       + "OR (t.transactionDate BETWEEN :startDate AND :endDate)) ")
	BigDecimal findTotalTransactionAmountByCompanyAndParkingSpace(
	        @Param("companyId") Long companyId, 
	        @Param("parkingSpaceId") Long parkingSpaceId, 
	        @Param("startDate") LocalDate startDate, 
	        @Param("endDate") LocalDate endDate
	);

    
}
