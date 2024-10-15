package com.parking.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.parking.dto.auth.UserDetailsImpl;
import com.parking.model.User;
import com.parking.repository.AdminRepository;
import com.parking.repository.AgentRepository;
import com.parking.repository.SuperadminRepository;
import com.parking.repository.UserRepository;

@Service
public class UserInfoService implements UserDetailsService {
	
	private final UserRepository userRepository;
	private final AdminRepository adminRepository;
	private final SuperadminRepository superadminRepository;
	private final AgentRepository agentRepository;
	
	@Autowired
	public UserInfoService(UserRepository userRepository, AdminRepository adminRepository, SuperadminRepository superadminRepository, AgentRepository agentRepository) {
		this.userRepository = userRepository;
		this.adminRepository = adminRepository;
		this.superadminRepository = superadminRepository;
		this.agentRepository = agentRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = userRepository.findUserByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usernot found"));
		UserDetailsImpl userDetails = new UserDetailsImpl(user);
		
		// Fetch additional info based on user role
		switch (user.getUserRoleEnum().toString()) {
			case "SUPERADMIN":
				userDetails.setSuperadmin(superadminRepository.findByUser(user));
				break;
			case "ADMIN": 
				userDetails.setAdmin(adminRepository.findByUser(user));
				break;
			case "AGENT":
				userDetails.setAgent(agentRepository.findByUser(user));
				break;
			default:
				// No additional info for default role
				break;
		}
		return userDetails;
	}

}
