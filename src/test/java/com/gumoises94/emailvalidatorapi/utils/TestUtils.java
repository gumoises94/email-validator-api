package com.gumoises94.emailvalidatorapi.utils;

import com.gumoises94.emailvalidatorapi.enumerations.ValidationStatus;
import com.gumoises94.emailvalidatorapi.models.EmailValidationModel;

public class TestUtils {
	
	public static EmailValidationModel buildValidPublicDomainEmailModel() {
		return EmailValidationModel.builder()
				.email("example@gmail.com")
				.isPublicDomain(true)
				.isCompanyDomain(false)
				.message(null)
				.validationStatus(ValidationStatus.VALID)
				.build();
	}
	
	public static EmailValidationModel buildValidCompanyDomainEmailModel() {
		return EmailValidationModel.builder()
				.email("example@ibm.com")
				.isPublicDomain(false)
				.isCompanyDomain(true)
				.message(null)
				.validationStatus(ValidationStatus.VALID)
				.build();
	}
	
	public static EmailValidationModel buildInvalidEmailModel(String email, String expectedMessage) {
		return EmailValidationModel.builder()
				.email(email)
				.isPublicDomain(false)
				.isCompanyDomain(false)
				.message(expectedMessage)
				.validationStatus(ValidationStatus.INVALID)
				.build();
	}

}
