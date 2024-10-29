package com.parking.controller.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.parking.dto.CompanyDashboardDto;
import com.parking.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("/companydashboard")
public interface CompanyDashboardApi {
	
	@Operation(summary = "Trouver un dashboard de l'entreprise", description = "Cette methode permet d'afficher un dashboard de l'entreprise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le dashboard a ete trouve"),
            @ApiResponse(responseCode = "404", description = "Aucune dashboard n'existe dans la BDD")
    })
    @GetMapping(value = Constants.APP_ROOT + "/companydashboard/{companyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    CompanyDashboardDto findCompanyDashboardByCompanyId(@PathVariable("companyId") Long companyId);
}
