package com.parking.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.parking.dto.auth.AuthenticationRequest;
import com.parking.dto.auth.AuthenticationResponse;
import com.parking.tokenRefresh.TokenRefreshResponse;
import com.parking.utils.Constants;


@RequestMapping("/authentication")
public interface AuthenticationApi {
	
	@PostMapping(value = Constants.AUTHENTICATION_ENDPOINT + "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request);
    
    @PostMapping(value = Constants.AUTHENTICATION_ENDPOINT + "/refresh-token")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestParam("token") String token);
}
