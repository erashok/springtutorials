package com.meraportal.epay.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class LDAPService implements ILDAPService{

	@Override
	public ResponseEntity<String> authenticateUser(String userName,String password) {
		
			try {
				String url = "http://localhost:8095/ldap/user/auth";
				RestTemplate rest = new RestTemplate();
				rest.getMessageConverters().add(
				        new MappingJackson2HttpMessageConverter());
				rest.getMessageConverters()
				        .add(new StringHttpMessageConverter());
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<String> entity = new HttpEntity<String>(
				        "{'userId' : "+userName+", 'userPassword' : "+password+"}",
				        headers);
				ResponseEntity<String> response = rest.exchange(url,
				        HttpMethod.POST, entity, String.class);
				return response;
			} catch (HttpStatusCodeException e) {
				e.printStackTrace();
				if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					String responseString = e
					        .getResponseBodyAsString();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
