package com.meraportal.epay.security.ldap.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.meraportal.epay.service.CustomAuthenticationProvider;

@Configuration
@EnableGlobalMethodSecurity
@ComponentScan(basePackages = "com.meraportal.epay.service")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	CustomAuthenticationProvider customAuthenticationProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().fullyAuthenticated().and().formLogin();
	}

	@Override
	public void configure(final AuthenticationManagerBuilder auth) throws Exception {
		/**
		 * Ldap Authencticator provide
		 * PLease add another provide to check first the presence of Portal user in FabePay Database
		 * 
		 */
		auth.authenticationProvider(customAuthenticationProvider);
		/*
		 * auth.ldapAuthentication().userDnPatterns("uid={0},ou=people").groupSearchBase
		 * ("ou=groups")
		 * .contextSource(contextSource()).passwordCompare().passwordEncoder(new
		 * LdapShaPasswordEncoder()) .passwordAttribute("userPassword");
		 */
	}

	
}
