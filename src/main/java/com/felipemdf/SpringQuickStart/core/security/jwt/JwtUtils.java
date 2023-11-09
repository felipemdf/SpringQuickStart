package com.felipemdf.SpringQuickStart.core.security.jwt;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.felipemdf.SpringQuickStart.core.utils.Validations;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;


@Component
public class JwtUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${jwt.secret}")
    private String secret;
	
	@Value("${jwt.expiration}")
    private Long expiration;

	public String generateJwtToken(UserDetails userPrincipal) {
	    return Jwts.builder()
	        .setSubject((userPrincipal.getUsername()))
	        .setIssuedAt(new Date())
	        .setExpiration(new Date((new Date()).getTime() + expiration))
	        .signWith(SignatureAlgorithm.HS256, secret)
	        .compact();
	  }
	
	public boolean validateJwtToken(String authToken) {
	    try {
	      Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
	      return true;
	    } catch (MalformedJwtException e) {
	      logger.error("Invalid JWT token: {}", e.getMessage());
	    } catch (ExpiredJwtException e) {
	      logger.error("JWT token is expired: {}", e.getMessage());
	    } catch (UnsupportedJwtException e) {
	      logger.error("JWT token is unsupported: {}", e.getMessage());
	    } catch (IllegalArgumentException e) {
	      logger.error("JWT claims string is empty: {}", e.getMessage());
	    }

	    return false;
	  }
	
	public String getUserNameFromJwtToken (String authToken) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody().getSubject();
	}
	
	public String getToken(HttpServletRequest request) {
	    String headerAuth = request.getHeader("Authorization");

	    if (Validations.isNotEmpty(headerAuth) && headerAuth.startsWith("Bearer ")) {
	      return headerAuth.substring(7);
	    }

	    return null;
	  }

	public List<String> getRoles(Collection<? extends GrantedAuthority> authorities) {
		return authorities.stream()
		        .map(item -> item.getAuthority())
		        .collect(Collectors.toList());
	}
}
