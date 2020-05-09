package com.gumoises94.emailvalidatorapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<EmailValidationModel> validate(@PathVariable String email) {
		try {
			return new ResponseEntity<EmailValidationModel>(emailValidationService.validate(email), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			EmailValidationModel model = EmailValidationModel.builder()
					.email(email)
					.message("Your request could not be completed due an internal system error.")
					.build();
			return new ResponseEntity<EmailValidationModel>(model, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 
}
