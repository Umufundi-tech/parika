package com.parking.services;

import com.parking.dto.CompanyDashboardDto;

public interface CompanyDashboardService {
	
	CompanyDashboardDto getCompanyDashboard(Long companyId);
}
