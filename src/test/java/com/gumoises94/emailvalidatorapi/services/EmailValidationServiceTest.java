package com.gumoises94.emailvalidatorapi.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gumoises94.emailvalidatorapi.enumerations.ValidationStatus;
import com.gumoises94.emailvalidatorapi.models.EmailValidationModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;


import java.text.ParseException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@SpringBootTest
public class EmailValidationServiceTest {
	
	@Mock
	private PopularDomainsService popularDomainsService;
	
	@InjectMocks
	private EmailValidationService emailValidationService;
	
	private EmailValidationModel buildValidPublicDomainEmailModel() {
		return EmailValidationModel.builder()
				.email("example@gmail.com")
				.isPublicDomain(true)
				.isCompanyDomain(false)
				.message(null)
				.validationStatus(ValidationStatus.VALID)
				.build();
	}
	
	private EmailValidationModel buildValidCompanyDomainEmailModel() {
		return EmailValidationModel.builder()
				.email("example@ibm.com")
				.isPublicDomain(false)
				.isCompanyDomain(true)
				.message(null)
				.validationStatus(ValidationStatus.VALID)
				.build();
	}
	
	private EmailValidationModel buildInvalidEmailModel(String email, String expectedMessage) {
		return EmailValidationModel.builder()
				.email(email)
				.isPublicDomain(false)
				.isCompanyDomain(false)
				.message(expectedMessage)
				.validationStatus(ValidationStatus.INVALID)
				.build();
	}
	
	@Test
	public void whenValidateEmailPublicDomain_thenValid() throws ParseException {
		EmailValidationModel model = buildValidPublicDomainEmailModel();
		String domain = model.getEmail().substring(model.getEmail().lastIndexOf("@") + 1);
		
		when(popularDomainsService.isPublicDomain(domain)).thenReturn(true);
		
		EmailValidationModel resultModel = emailValidationService.validate(model.getEmail());
		
		assertEquals(model, resultModel);
		
		verify(popularDomainsService, times(1)).isPublicDomain(domain);
		verifyNoMoreInteractions(popularDomainsService);
	}
	
	@Test
	public void whenValidateEmailCompanyDomain_thenValid() throws ParseException {
		EmailValidationModel model = buildValidCompanyDomainEmailModel();
		String domain = model.getEmail().substring(model.getEmail().lastIndexOf("@") + 1);
		
		when(popularDomainsService.isPublicDomain(domain)).thenReturn(false);
		
		EmailValidationModel resultModel = emailValidationService.validate(model.getEmail());
		
		assertEquals(model, resultModel);
		
		verify(popularDomainsService, times(1)).isPublicDomain(domain);
		verifyNoMoreInteractions(popularDomainsService);
	}
	
	@Test
	public void whenValidateEmailWithInvalidDomain_thenInvalid() throws ParseException {
		String email = "example@somedomainthatdoesntexist.com";
		String domain = email.substring(email.lastIndexOf("@") + 1);
		EmailValidationModel model = buildInvalidEmailModel(email, "Invalid domain: " + domain + ".");
		
		
		when(popularDomainsService.isPublicDomain(domain)).thenReturn(false);
		
		EmailValidationModel resultModel = emailValidationService.validate(model.getEmail());
		
		assertEquals(model, resultModel);
		
		verify(popularDomainsService, times(1)).isPublicDomain(domain);
		verifyNoMoreInteractions(popularDomainsService);
	}
	
	@Test
	public void whenValidateEmailWithNullEmail_thenInvalid() throws ParseException {
		EmailValidationModel model = buildInvalidEmailModel(null, "The email must not be empty.");
		
		EmailValidationModel resultModel = emailValidationService.validate(null);
		
		assertEquals(model, resultModel);
	}
	
	@Test
	public void whenValidateEmailWithEmptyEmail_thenInvalid() throws ParseException {
		EmailValidationModel model = buildInvalidEmailModel("", "The email must not be empty.");
		
		EmailValidationModel resultModel = emailValidationService.validate("");
		
		assertEquals(model, resultModel);
	}
	
	@Test
	public void whenValidateEmailWithInvalidEmail_thenInvalid() throws ParseException {
		EmailValidationModel model = buildInvalidEmailModel("a.b@test", 
				"Please insert a valid email. E.g. example@email.com.");
		
		EmailValidationModel resultModel = emailValidationService.validate("a.b@test");
		
		assertEquals(model, resultModel);
	}
}
