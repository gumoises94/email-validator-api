package com.gumoises94.emailvalidatorapi.controllers;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.gumoises94.emailvalidatorapi.models.EmailValidationModel;
import com.gumoises94.emailvalidatorapi.services.EmailValidationService;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import static com.gumoises94.emailvalidatorapi.utils.TestUtils.*;


import java.text.ParseException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@SpringBootTest
public class EmailValidationControllerTest {
	
	@Mock
	private EmailValidationService emailValidationService;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	
	@Test
	public void whenValidateEmailPublicDomain_thenValid() throws ParseException {
		EmailValidationModel model = buildValidPublicDomainEmailModel();
		String email = model.getEmail();
		
		when(emailValidationService.validate(email)).thenReturn(model);
		
		mockMvc.perform(get("/tarefas/{param}", email).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.dataOperacao", notNullValue()))
		.andExpect(jsonPath("$.mensagens", nullValue()))
		.andExpect(jsonPath("$.retorno", notNullValue()))
		.andExpect(jsonPath("$.retorno.id").value(tarefaModel.getId()))
		.andExpect(jsonPath("$.retorno.titulo").value(tarefaModel.getTitulo()))
		.andExpect(jsonPath("$.retorno.descricao").value(tarefaModel.getDescricao()))
		.andExpect(jsonPath("$.retorno.finalizado").value(tarefaModel.getFinalizado()))
		.andExpect(jsonPath("$.retorno.dataCadastro").value(tarefaModel.getDataCadastro()))
		.andExpect(jsonPath("$.retorno.usuario", notNullValue()))
		.andExpect(jsonPath("$.retorno.usuario.id").value(tarefaModel.getUsuario().getId()))
		.andExpect(jsonPath("$.retorno.usuario.nome").value(tarefaModel.getUsuario().getNome()))
		.andExpect(jsonPath("$.retorno.usuario.email").value(tarefaModel.getUsuario().getEmail()))
		.andExpect(jsonPath("$.retorno.usuario.senha").value(tarefaModel.getUsuario().getSenha()))
		.andExpect(jsonPath("$.retorno.usuario.dataCadastro").value(tarefaModel.getUsuario().getDataCadastro()))
		.andExpect(jsonPath("$.retorno.usuario.idioma").value(tarefaModel.getUsuario().getIdioma()));
		
		assertEquals(model, resultModel);
		
		verify(popularDomainsService, times(1)).isPublicDomain(domain);
		verifyNoMoreInteractions(popularDomainsService);
	}
	
}
