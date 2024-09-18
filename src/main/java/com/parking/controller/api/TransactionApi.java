package com.parking.controller.api;

import java.util.List;

import com.parking.dto.DepositSaveDto;
import com.parking.dto.PaymentListDto;
import com.parking.dto.PaymentSaveDto;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.parking.dto.TransactionDto;
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


}
