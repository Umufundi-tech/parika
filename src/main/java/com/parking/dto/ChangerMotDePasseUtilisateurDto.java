package com.parking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangerMotDePasseUtilisateurDto {
	
	private Long id;
	private String currentPassword;
	private String motDePasse;
	private String confirmMotDePasse;
}
