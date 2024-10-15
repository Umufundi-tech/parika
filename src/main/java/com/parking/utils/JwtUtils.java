package com.parking.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.parking.dto.auth.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.expiration}")
	private int jwtExpirationMs;
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public boolean hasClaim(String token, String claimName) {
		final Claims claims = extractAllClaims(token);
		return claims.get(claimName) != null;
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getId());
        claims.put("userfullname", userDetails.getUserfullname());
        claims.put("useremail", userDetails.getUsername());
        claims.put("userphonenumber", userDetails.getUserphonenumber());
        claims.put("userrole", userDetails.getUserrole());

        if (userDetails.getSuperadmin() != null) {
            claims.put("superadminId", userDetails.getSuperadmin().getId());
            claims.put("superadmintype", userDetails.getSuperadmin().getSuperadminTypeEnum().toString());

        } else if (userDetails.getAdmin() != null) {
            claims.put("adminId", userDetails.getAdmin().getId());
            claims.put("admintype", userDetails.getAdmin().getAdminTypeEnum().toString());
            claims.put("company_id", userDetails.getAdmin().getCompany().getId());
        }
        else if (userDetails.getAgent() != null) {
            claims.put("agentId", userDetails.getAgent().getId());
            claims.put("company_id", userDetails.getAgent().getCompany().getId());
            claims.put("parkingSpace_id", userDetails.getAgent().getParkingSpace().getId());
        }
        return createToken(claims, userDetails);
    }
    
    public String refreshToken(String token) {
    	
    	Claims claims = null;
    	
    	 // Extract claims from the old token
        try{
        	claims = Jwts.parserBuilder()
        			.setSigningKey(getSignKey())
            		.build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
        	claims = e.getClaims();
        }
        
        if(claims == null) {
        	throw new IllegalArgumentException("Invalid token");
        }
        		
        // Validate claims if necessary
        // Add any additional validation logic here if needed

        // Create a new token with the same claims but updated expiration
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(claims.getSubject())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	
}
