package com.meraportal.epay.service;

import org.springframework.http.ResponseEntity;

public interface ILDAPService {

	ResponseEntity<String> authenticateUser(String userName, String password);

}
