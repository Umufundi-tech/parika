package com.parking.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.parking.filter.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	    return httpSecurity.csrf(AbstractHttpConfigurer::disable)
	            .authorizeHttpRequests(auth -> auth
	                    .requestMatchers(
	                            new AntPathRequestMatcher("/auth/login"),
	                            new AntPathRequestMatcher("/**/authenticate"),
	                            new AntPathRequestMatcher("/**/refresh-token"),
	                            new AntPathRequestMatcher("/**/superadmins/create"),
	                            new AntPathRequestMatcher("/**/admins/create"),
	                            new AntPathRequestMatcher("/**/agents/create"),
	                            new AntPathRequestMatcher("/api/access/**"),
	                            new AntPathRequestMatcher("/h2-console/**"),
	                            // resources for swagger to work properly
	                            new AntPathRequestMatcher("/v2/api-docs"),
	                            new AntPathRequestMatcher("/v3/api-docs"),
	                            new AntPathRequestMatcher("/v3/api-docs/**"),
	                            new AntPathRequestMatcher("/swagger-resources"),
	                            new AntPathRequestMatcher("/swagger-resources/**"),
	                            new AntPathRequestMatcher("/configuration/ui"),
	                            new AntPathRequestMatcher("/configuration/security"),
	                            new AntPathRequestMatcher("/swagger-ui/**"),
	                            new AntPathRequestMatcher("/webjars/**"),
	                            new AntPathRequestMatcher("/swagger-ui.html")
	                    )
	                    .permitAll()
	                    .anyRequest().authenticated())
	            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	            //.authenticationProvider(authenticationProvider())
	            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
	            .addFilterBefore(corsFilter(), SessionManagementFilter.class)
	            .build();
	}


    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Don't do this in production, use a proper list  of allowed origins
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        // some comment here
        return new CorsFilter(source);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
}
