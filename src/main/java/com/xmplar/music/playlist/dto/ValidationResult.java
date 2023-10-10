package com.xmplar.music.playlist.dto;

import com.xmplar.training.library.dto.APIError;

public class ValidationResult {

	private Object objectForValidation;
	private Boolean isValid = false;
	private APIError apiError;
	
	public Object getObjectForValidation() {
		return objectForValidation;
	}
	public void setObjectForValidation(Object objectForValidation) {
		this.objectForValidation = objectForValidation;
	}
	public APIError getApiError() {
		return apiError;
	}
	public void setApiError(APIError apiError) {
		this.apiError = apiError;
	}
	public Boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
}