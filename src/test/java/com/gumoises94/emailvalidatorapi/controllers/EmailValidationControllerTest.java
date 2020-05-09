package com.gumoises94.emailvalidatorapi.controllers;

import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.gumoises94.emailvalidatorapi.models.EmailValidationModel;
import com.gumoises94.emailvalidatorapi.services.EmailValidationService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import static com.gumoises94.emailvalidatorapi.utils.TestUtils.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
public class EmailValidationControllerTest {
	
	@MockBean
	private EmailValidationService emailValidationService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void whenValidateEmailPublicDomain_thenValid() throws Exception {
		EmailValidationModel model = buildValidPublicDomainEmailModel();
		String email = model.getEmail();
		
		when(emailValidationService.validate(email)).thenReturn(model);
		
		mockMvc.perform(get("/validation/{param}", email).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.email").value(model.getEmail()))
		.andExpect(jsonPath("$.isPublicDomain").value(model.getIsPublicDomain()))
		.andExpect(jsonPath("$.isCompanyDomain").value(model.getIsCompanyDomain()))
		.andExpect(jsonPath("$.validationStatus").value(model.getValidationStatus().toString()))
		.andExpect(jsonPath("$.message").value(model.getMessage()));
		
		verify(emailValidationService, times(1)).validate(email);
		verifyNoMoreInteractions(emailValidationService);
	}
	
	@Test
	public void whenValidateEmailPublicDomainErrosOccurs_thenThrow() throws Exception {
		EmailValidationModel model = buildValidPublicDomainEmailModel();
		String email = model.getEmail();
		
		when(emailValidationService.validate(email)).thenThrow(RuntimeException.class);
		
		mockMvc.perform(get("/validation/{param}", email).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isInternalServerError()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.email").value(model.getEmail()))
		.andExpect(jsonPath("$.message").value("Your request could not be completed due an internal system error."));
		
		verify(emailValidationService, times(1)).validate(email);
		verifyNoMoreInteractions(emailValidationService);
	}
	
}
