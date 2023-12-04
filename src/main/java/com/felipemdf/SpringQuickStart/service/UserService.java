package com.felipemdf.SpringQuickStart.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.felipemdf.SpringQuickStart.core.security.jwt.JwtUtils;
import com.felipemdf.SpringQuickStart.core.utils.Validations;
import com.felipemdf.SpringQuickStart.dto.request.LoginRequest;
import com.felipemdf.SpringQuickStart.dto.request.SignUpRequest;
import com.felipemdf.SpringQuickStart.dto.response.JwtResponse;
import com.felipemdf.SpringQuickStart.model.Role;
import com.felipemdf.SpringQuickStart.model.User;
import com.felipemdf.SpringQuickStart.model.enums.RoleEnum;
import com.felipemdf.SpringQuickStart.repository.RoleRepository;
import com.felipemdf.SpringQuickStart.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
//	@Autowired
	private PasswordEncoder encoder;
	
//	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	/*
	 * TODO O uso dos beans PasswordEncoder e AuthenticationManager causa um problema de ciclo de dependencias
	 * pois esses recursos são encontrados no WebSecurityConfig, enquanto o mesmo utiliza UserService. Resolver
	 * futuramente esse problema de uma forma mais adequada
	 */
	public UserService(@Lazy PasswordEncoder encoder, @Lazy AuthenticationManager authenticationManager) {
		this.encoder = encoder;
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User usuario = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map((role) -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
		
		
		return org.springframework.security.core.userdetails.User.builder()
				.username(usuario.getUsername())
				.password(usuario.getPassword())
				.authorities(authorities)
				.build(); 
		
	}

	public JwtResponse authenticate(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		
		JwtResponse jwtResponse = new JwtResponse(
				jwtUtils.generateJwtToken(userPrincipal), 
				jwtUtils.getRoles(userPrincipal.getAuthorities())
		);
		
		
		return jwtResponse;
	}
	
	public void register (SignUpRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new RuntimeException("Error: Username is already taken!");
		}
		
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new RuntimeException("Error: Email is already taken!");
		}
		
		Set<Role> roles = new HashSet<>();
		
		if (Validations.isEmpty(signUpRequest.getRoles())) {
	        Role role = roleRepository.findByName(RoleEnum.ROLE_USER)
	            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	        
	        roles.add(role);
	    } else {
	    	signUpRequest.getRoles().forEach(signUpRole -> {
	    		Role role = roleRepository.findByName(signUpRole)
    	            	.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	    		
	    		roles.add(role);
	    	});
	    }
		
		User user = new User(
				signUpRequest.getUsername(), 
	            signUpRequest.getEmail(),
	            encoder.encode(signUpRequest.getPassword()),
	            roles
	    );
		
		userRepository.save(user);
	}
}
