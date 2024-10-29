package com.parking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.parking.controller.api.SuperadminDashboardApi;
import com.parking.dto.SuperadminDashboardDto;
import com.parking.services.SuperadminDashboardService;

@RestController
public class SuperadminDashboardController implements SuperadminDashboardApi {
	
	private final SuperadminDashboardService superadminDashboardService;
	
	@Autowired
	public SuperadminDashboardController(SuperadminDashboardService superadminDashboardService) {
		this.superadminDashboardService = superadminDashboardService;
	}
	
	@Override
	public SuperadminDashboardDto findSuperadminDashboard() {
		
		return superadminDashboardService.getSuperadminDashboard();
	}

}
