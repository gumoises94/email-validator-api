package com.gumoises94.emailvalidatorapi.models;

import com.gumoises94.emailvalidatorapi.enumerations.ValidationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@Builder
public class EmailValidationModel {
	private String email;
	private Boolean isPublicDomain;
	private Boolean isCompanyDomain;
	private ValidationStatus validationStatus;
	private String message;
	
	public EmailValidationModel() {
		this.email = null;
		this.isPublicDomain = false;
		this.isCompanyDomain = false;
		this.message = null;
		this.validationStatus = null;
	}
}
