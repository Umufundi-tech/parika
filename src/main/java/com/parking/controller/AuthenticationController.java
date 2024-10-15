package com.parking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.parking.controller.api.AuthenticationApi;
import com.parking.dto.auth.AuthenticationRequest;
import com.parking.dto.auth.AuthenticationResponse;
import com.parking.services.UserService;
import com.parking.tokenRefresh.TokenRefreshRequest;
import com.parking.tokenRefresh.TokenRefreshResponse;

@RestController
public class AuthenticationController implements AuthenticationApi {
	
	private final UserService userService;
	
	@Autowired
	public AuthenticationController(UserService userService) {
		this.userService = userService;
	}

	@Override
	public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
		
		return ResponseEntity.ok(userService.authenticate(request));
	}

	@Override
	public ResponseEntity<TokenRefreshResponse> refreshToken(String token) {
		
		TokenRefreshRequest request = new TokenRefreshRequest();
		request.setToken(token);
		
		return ResponseEntity.ok(userService.refreshToken(request));
	}
	
}
