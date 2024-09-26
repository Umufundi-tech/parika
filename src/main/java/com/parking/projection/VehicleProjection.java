package com.parking.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.parking.model.VehicleType;

public interface VehicleProjection {
	
	Long getIdVehicle();
	String getRegistrationNumber();
	LocalDate getCreationDate();
	VehicleType getVehicleTypeDto();
	String getAccountNumber();
	BigDecimal getSolde();
	byte[] getQrCodeImage();
	BigDecimal getPrice();
	
}
