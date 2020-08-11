package com.ldapspring.ldap.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldapspring.ldap.service.AuthLdapServiceUtility;

@Controller
@CrossOrigin
public class LdapService {

	@Autowired
	AuthLdapServiceUtility authLdap;

	@RequestMapping(value = "/ldap/user/auth",
	        consumes = "application/json",
	        method = { RequestMethod.POST })
	public @ResponseBody Object ldapAuthenticate(
	        ServletRequest req, ServletResponse res,
	        @RequestBody String requestJson) throws Exception {
		System.out.println("ldapAuthenticate() called");
		System.out.println("requestJson=" + requestJson);
		JSONObject json = new JSONObject(requestJson);
		String userId = json.getString("userId");
		String password = json.getString("userPassword");
		authLdap.verifyCredentials(userId, password);
		return "{'response' : 'success'}"; //Proper response need to be setup ,to do by developers
		
	}

	@RequestMapping("/")
	public String index(Map<String, Object> model) {
		System.out.println("index() called");
		model = new HashMap<String, Object>();
		return "login";
	}
}
