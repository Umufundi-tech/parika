package com.parking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.parking.controller.api.CompanyDashboardApi;
import com.parking.dto.CompanyDashboardDto;
import com.parking.services.CompanyDashboardService;

@RestController
public class CompanyDashboardController implements CompanyDashboardApi {
	
	private final CompanyDashboardService companyDashboardService;
	
	@Autowired
	public CompanyDashboardController(CompanyDashboardService companyDashboardService) {
		this.companyDashboardService = companyDashboardService;
	}
	
	@Override
	public CompanyDashboardDto findCompanyDashboardByCompanyId(Long companyId) {
		
		return companyDashboardService.getCompanyDashboard(companyId);
	}

}
