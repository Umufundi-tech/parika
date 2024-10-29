package com.parking.controller.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.parking.dto.SuperadminDashboardDto;
import com.parking.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("/superadmindashboard")
public interface SuperadminDashboardApi {
	
	@Operation(summary = "Trouver un dashboard du superadmin", description = "Cette methode permet d'afficher un dashboard du superadmin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le dashboard a ete trouve"),
            @ApiResponse(responseCode = "404", description = "Aucune dashboard n'existe dans la BDD")
    })
    @GetMapping(value = Constants.APP_ROOT + "/superadmindashboard", produces = MediaType.APPLICATION_JSON_VALUE)
    SuperadminDashboardDto findSuperadminDashboard();
}
