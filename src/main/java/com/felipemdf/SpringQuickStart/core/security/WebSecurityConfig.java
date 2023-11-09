package com.felipemdf.SpringQuickStart.core.security;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.felipemdf.SpringQuickStart.core.security.jwt.JwtFilter;
import com.felipemdf.SpringQuickStart.core.security.jwt.UnauthorizedEntryPoint;
import com.felipemdf.SpringQuickStart.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig{

    public static final String[] ALLOWED_ORIGINS = {"*"};
    public static final String[] ALLOWED_METHODS = {"GET","POST","PUT","DELETE","PATCH"};
    public static final String[] ALLOWED_HEADERS = {"Authorization", "content-type"};

    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;
   
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // AUTORIZAÇÃO DE ROTAS
        http.authorizeHttpRequests((authorizeHttpRequests) ->
        		authorizeHttpRequests
        			.requestMatchers("/api/auth/**").permitAll();
        );


        http.csrf((csrf) ->
                csrf.disable()
        );

        // SESSÃO
        http.sessionManagement((sessionManagment) ->
                sessionManagment.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // PROVEDOR DE AUTENTICAÇÃO
        http.authenticationProvider(authenticationProvider());

        http.exceptionHandling((exceptionHandler) -> 
        		exceptionHandler.authenticationEntryPoint(unauthorizedEntryPoint)
        );
        
        // FILTRO JWT
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Arrays.asList(ALLOWED_ORIGINS));
        config.setAllowedMethods(Arrays.asList(ALLOWED_METHODS));
        config.setAllowedHeaders(Arrays.asList(ALLOWED_HEADERS));

        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

}
