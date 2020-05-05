package com.gumoises94.emailvalidatorapi.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import java.text.ParseException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@SpringBootTest
public class PopularDomainsServiceTest {
	
	@Autowired
	private PopularDomainsService popularDomainsService;
	
	@Test
	public void whenValidatePublicDomain_thenTrue() throws ParseException {
		assertTrue(popularDomainsService.isPublicDomain("gmail.com"));
	}
	
	@Test
	public void whenValidatePublicDomainWithInvalid_thenFalse() throws ParseException {
		assertFalse(popularDomainsService.isPublicDomain("aaa.com"));
	}
	
	@Test
	public void whenValidatePublicDomainWithNull_thenFalse() throws ParseException {
		assertFalse(popularDomainsService.isPublicDomain(null));
	}
	
	@Test
	public void whenValidatePublicDomainWithEmpty_thenFalse() throws ParseException {
		assertFalse(popularDomainsService.isPublicDomain(""));
	}
	
}
