package com.gumoises94.emailvalidatorapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gumoises94.emailvalidatorapi.models.EmailValidationModel;
import com.gumoises94.emailvalidatorapi.services.EmailValidationService;

@RestController
public class EmailValidationController {

	@Autowired
	private EmailValidationService emailValidationService;
	
	@GetMapping("/validation/{email}")
	public EmailValidationModel validate(@PathVariable String email) {
		try {
			return emailValidationService.validate(email);
		} catch(Exception e) {
			e.printStackTrace();
			return EmailValidationModel.builder()
					.email(email)
					.message("Your request could not be completed due an internal system error.")
					.build();
		}
	} 
}
