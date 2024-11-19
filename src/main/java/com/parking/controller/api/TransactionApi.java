package com.parking.controller.api;

import java.time.LocalDate;
import java.util.List;

import com.parking.dto.DepositSaveDto;
import com.parking.dto.ParkingSpaceDto;
import com.parking.dto.PaymentSaveDto;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.parking.dto.TransactionDto;
import com.parking.dto.TransactionSummaryDto;
import com.parking.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("/transactions")
public interface TransactionApi {

    @Operation(summary = "Créer une paiement", description = "Cette methode permet d'enregistrer un paiement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet paiement cree / modifie"),
            @ApiResponse(responseCode = "400", description = "L'objet paiement n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/transactions/payments/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    PaymentSaveDto savePayment(@RequestBody PaymentSaveDto dto);

    @Operation(summary = "Créer un depot", description = "Cette methode permet d'enregistrer un depot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet depot cree / modifie"),
            @ApiResponse(responseCode = "400", description = "L'objet depot n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/transactions/deposits/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    DepositSaveDto saveDeposit(@RequestBody DepositSaveDto dto);
    
    @Operation(summary = "Récupérer la liste de paiements d'un agent d'aujourd'hui", description = "Cette methode permet de chercher et renvoyer la liste des paiements qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des paiements d'un agent / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/transactions/today-payments-list/{agentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<TransactionDto> findPaymentsByAgent(
            @PathVariable("agentId") Long agentId,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );
    
    @Operation(summary = "Récupérer la liste des transactions d'un agent avec la somme totale")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des transactions et la somme totale")
    })
    @GetMapping(value = "/transactions/agent-summary/{agentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    TransactionSummaryDto findTransactionsWithTotalAmountByAgent(
        @PathVariable("agentId") Long agentId,
        @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    );
    
    @Operation(summary = "Récupérer la liste des transactions d'une entreprise avec la somme totale")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des transactions et la somme totale")
    })
    @GetMapping(value = "/transactions/company-summary/{companyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    TransactionSummaryDto findTransactionsWithTotalAmountByCompany(
        @PathVariable("companyId") Long companyId,
        @RequestParam(value = "parkingSpaceId", required = false) Long parkingSpaceId,
        @RequestParam(value = "agentId", required = false) Long agentId,
        @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    );
    
    @Operation(summary = "Récupérer la liste des transactions des entreprises avec la somme totale")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des transactions et la somme totale")
    })
    @GetMapping(value = "/transactions/companies-summary", produces = MediaType.APPLICATION_JSON_VALUE)
    TransactionSummaryDto findAllTransactionsWithTotalAmount(
        @RequestParam(value = "companyId", required = false) Long companyId,
        @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    );
    
    @Operation(summary = "Supprimer une transaction par son ID", description = "Cette methode permet de supprimer une transaction par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La transaction a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/transactions/delete/{id}")
    void delete(@PathVariable("id") Long id);



}
