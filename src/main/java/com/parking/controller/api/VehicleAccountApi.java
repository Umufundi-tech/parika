package com.parking.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.parking.dto.VehiculeAccountDto;
import com.parking.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("/vehicle_accounts")
public interface VehicleAccountApi {
	
	@Operation(summary = "Trouver un compte vehicule par son ID", description = "Cette methode permet de chercher un compte vehicule par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le compte vehicule a ete trouve dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucun compte vehicule n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/vehicle_accounts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    VehiculeAccountDto findById(@PathVariable("id") Long id);
	
	@Operation(summary = "Récupérer la liste de tous les comptes vehicules", description = "Cette methode permet de chercher et renvoyer la liste des comptes de vehicules qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des comptes vehicules / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/vehicle_accounts/list", produces = MediaType.APPLICATION_JSON_VALUE)
    List<VehiculeAccountDto> findAll();
}
