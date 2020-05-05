package com.gumoises94.emailvalidatorapi.services;

import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gumoises94.emailvalidatorapi.enumerations.ValidationStatus;
import com.gumoises94.emailvalidatorapi.models.EmailValidationModel;

/**
 * Service to validate the given data
 * 
 * @author Gustavo Moisés Saron
 * @date 05/2020
 *
 */
@Service
public class EmailValidationService {
	
	@Autowired
	private PopularDomainsService popularDomainsService;
	
	public EmailValidationModel validate(String email) {
		EmailValidationModel model = new EmailValidationModel();
		model.setEmail(email);
		model.setValidationStatus(ValidationStatus.INVALID);
		
		
		if(email != null && !"".equals(email)) {
			if(email.contains("@") && email.lastIndexOf(".") > email.lastIndexOf("@")) {
				String domain = email.substring(email.lastIndexOf("@") + 1);
				
				if(popularDomainsService.isPublicDomain(domain)) {
					model.setIsPublicDomain(true);
					model.setValidationStatus(ValidationStatus.VALID);
				}
				else if(validateDomain(domain)) {
					model.setIsCompanyDomain(true);
					model.setValidationStatus(ValidationStatus.VALID);
				}
				else {
					model.setMessage("Invalid domain: " + domain + ".");
				}
			}
			else {
				model.setMessage("Please insert a valid email. E.g. example@email.com.");
			}
		}
		else {
			model.setMessage("The email must not be empty.");
		}
		
		return model;
	}
	
	private boolean validateDomain(String domain) {
		try {
			String strUrl = "https://www." + domain;
			
			URL url = new URL(strUrl);
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			huc.setRequestMethod("HEAD");
			
			if(huc.getResponseCode() == HttpURLConnection.HTTP_OK) {
				return true;
			}
			else {
				strUrl = "http://www." + domain;
				url = new URL(strUrl);
				huc = (HttpURLConnection) url.openConnection();
				huc.setRequestMethod("HEAD");
				
				if(huc.getResponseCode() == HttpURLConnection.HTTP_OK)
					return true;
			}
			
			return false;
			
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
}
