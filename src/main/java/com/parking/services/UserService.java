package com.parking.services;

import java.util.List;

import com.parking.dto.ChangerMotDePasseUtilisateurDto;
import com.parking.dto.UserDto;
import com.parking.dto.auth.AuthenticationRequest;
import com.parking.dto.auth.AuthenticationResponse;
import com.parking.tokenRefresh.TokenRefreshRequest;
import com.parking.tokenRefresh.TokenRefreshResponse;

public interface UserService {
	
	UserDto findById(Long id);
	
	List<UserDto> findAll();

	void enableUser(Long userId);

	void desableUser(Long userId);
	
	AuthenticationResponse authenticate(AuthenticationRequest request);
	
	TokenRefreshResponse refreshToken(TokenRefreshRequest request);
	
	UserDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto);
}
