package com.meraportal.epay.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author er.ashok
 * This is customized Authenticator Provide which will call microservices
 * to get authenticated 
 * Similarly there will be another  Authenticator to check the presence of back office
 * User in FabePay Database 
 * 1. create the similar file and override the below methods
 * 2. Add all the Authication provide components to "SecurityConfig" class 
 * 
 *
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	ILDAPService ldapService;

    public CustomAuthenticationProvider() {
        super();
    }

    // API

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();
        ResponseEntity<String> response=ldapService.authenticateUser(name,password);
        
        
        
		if (response!=null && response.getBody().contains("success")) {
			
			  final List<GrantedAuthority> grantedAuths = new ArrayList<>();
			  grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER")); final UserDetails
			  principal = new User(name, password, grantedAuths); final Authentication auth
			  = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
			  return auth;
			 
			
		} else {
            return null;
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
