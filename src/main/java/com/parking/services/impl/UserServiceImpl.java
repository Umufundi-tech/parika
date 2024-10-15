package com.parking.services.impl;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.parking.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.parking.dto.ChangerMotDePasseUtilisateurDto;
import com.parking.dto.UserDto;
import com.parking.dto.auth.AuthenticationRequest;
import com.parking.dto.auth.AuthenticationResponse;
import com.parking.exceptions.EntityNotFoundException;
import com.parking.exceptions.ErrorCodes;
import com.parking.exceptions.InvalidEntityException;
import com.parking.repository.UserRepository;
import com.parking.services.UserService;
import com.parking.tokenRefresh.TokenRefreshRequest;
import com.parking.tokenRefresh.TokenRefreshResponse;
import com.parking.utils.JwtUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDto findById(Long id) {
		
		if(id == null) {
			log.error("User ID is null");
		}

		return userRepository.findById(id)
				.map(UserDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucun utilisateur avec l'ID = " +id+ " n' a ete trouve dans la BDD ",
						ErrorCodes.USER_NOT_FOUND)
				);
	}

	@Override
	public List<UserDto> findAll() {
		
		return userRepository.findAll().stream()
				.map(UserDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void enableUser(Long userId) {

		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setIsUserActive(true);
			userRepository.save(user);
		} else {
			throw new EntityNotFoundException("Aucune utilisateur avec l'ID = " +userId+ " n' a ete trouve dans la BDD",ErrorCodes.USER_NOT_FOUND);
		}

	}

	@Override
	@Transactional
	public void desableUser(Long userId) {

		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setIsUserActive(false);
			userRepository.save(user);
		} else {
			throw new EntityNotFoundException("Aucune utilisateur avec l'ID = " +userId+ " n' a ete trouve dans la BDD",ErrorCodes.USER_NOT_FOUND);
		}

	}

	@Override
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		
		Authentication authentication =authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		final String token = jwtUtils.generateToken(authentication);
		return AuthenticationResponse.builder()
				.token(token)
				.build();
	}

	@Override
	public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
		
		final String newToken = jwtUtils.refreshToken(request.getToken());
		return TokenRefreshResponse.builder()
				.newToken(newToken)
				.build();
	}

	@Override
	public UserDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto) {
		
		validate(dto);
		Optional<User> userOptional = userRepository.findById(dto.getId());
		if(userOptional.isEmpty()) {
			log.warn("Aucun utilisateur n'a été trouvé avec l'ID " +dto.getId());
			throw new EntityNotFoundException("Aucun utilisateur n'a été trouvé avec l'ID " +dto.getId(), ErrorCodes.USER_NOT_FOUND);
		}
		
		User user = userOptional.get();
		
		if(!passwordEncoder.matches(dto.getCurrentPassword(), user.getUserPassword())) {
			throw new InvalidEntityException("Mot de passe actuel invalide",  ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
		}
		
		user.setUserPassword(passwordEncoder.encode(dto.getMotDePasse()));
		
		return UserDto.fromEntity(
				userRepository.save(user)
		);
	}
	
	private void validate(ChangerMotDePasseUtilisateurDto dto) {
		if(dto==null) {
			log.warn("Impossible de modifier le mot de passe avec l'objet NULL");
			throw new InvalidEntityException("Aucune information n'a été fourni pour pouvoir changer le mot de passe",
					ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
		}
		if(dto.getId()==null) {
			log.warn("Impossible de modifier le mot de passe avec un ID NULL");
			throw new InvalidEntityException("ID utilisateur null:: Impossible de modifier le mot de passe",
					ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
		}
		if(!StringUtils.hasLength(dto.getMotDePasse()) || !StringUtils.hasLength(dto.getConfirmMotDePasse())) {
			log.warn("Impossible de modifier le mot de passe avec un mot de passe NULL");
			throw new InvalidEntityException("Mot de passe utilisateur null:: Impossible de modifier le mot de passe",
					ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
		}
		if(!dto.getMotDePasse().equals(dto.getConfirmMotDePasse())) {
			log.warn("Impossible de modifier le mot de passe avec deux mots de passe différents");
			throw new InvalidEntityException("Mots de passe utilisateur non conformes:: Impossible de modifier le mot de passe",
					ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
		}
	}
}
