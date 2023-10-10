package com.xmplar.music.playlist.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.xmplar.music.playlist.dto.APIEndpointResponse;
import com.xmplar.music.playlist.dto.APIError;
import com.xmplar.music.playlist.dto.APIRequest;
import com.xmplar.music.playlist.dto.ValidationResult;
import com.xmplar.music.playlist.util.APIResponseStatus;

@Component
public abstract class BaseAPIResponseHandler {

	private final Log logger = LogFactory.getLog(this.getClass());
	
	
	
	
	/**
	 * Generates a ResponseEntity for a transactionId that is not valid
	 * 
	 * @param apiRequest - the initial API Request
	 * @return ResponseEntity<APIEndpointResponse>
	 */
	public ResponseEntity<APIEndpointResponse> generateInvalidAPIEndpointErrorResponse(APIRequest apiRequest) {
		
		APIError apiError = new APIError();
		apiError.setDetailMessage("Please enter a valid transactionId.");
		apiError.setShortMessage("Invalid Request");
		apiError.setErrors(null);
		
		APIEndpointResponse invalidResponse = new APIEndpointResponse();
		invalidResponse.setApiError(apiError);
		invalidResponse.setApiRequest(apiRequest);
		invalidResponse.getApiResponse().getResponseContext().setStatus(APIResponseStatus.ERROR.getDisplayValue());
		
		return ResponseEntity.badRequest().body(invalidResponse);
	}
	
	/**
	 * Generates a ResponseEntity for a validation failure (using the validationResults object/errors)
	 * 
	 * @param apiRequest - the initial API Request
	 * @param validationResult - the result of the validation
	 * @return ResponseEntity<APIEndpointResponse>
	 */
	public ResponseEntity<APIEndpointResponse> generatValidationFailureErrorResponse(APIRequest apiRequest, ValidationResult validationResult) {
		
		APIEndpointResponse invalidResponse = new APIEndpointResponse();
		invalidResponse.setApiError(validationResult.getApiError());
		invalidResponse.setApiRequest(apiRequest);
		invalidResponse.getApiResponse().getResponseContext().setStatus(APIResponseStatus.ERROR.getDisplayValue());

		return ResponseEntity.badRequest().body(invalidResponse);
	}
	
	/**
	 * Generates a ResponseEntity for a bad APIKey/Username combination or missing token
	 * 
	 * @param apiRequest - the initial API Request
	 * @return ResponseEntity<APIEndpointResponse>
	 */
	public ResponseEntity<APIEndpointResponse> generateMissingAPIKeyOrTokenErrorResponse(APIRequest apiRequest) {
		
		APIError apiError = new APIError();
		apiError.setDetailMessage("Please include a valid token or APIKey/Username combination.");
		apiError.setShortMessage("Invalid Request");
		apiError.setErrors(null);
		
		APIEndpointResponse invalidResponse = new APIEndpointResponse();
		invalidResponse.setApiError(apiError);
		invalidResponse.setApiRequest(apiRequest);
		invalidResponse.getApiResponse().getResponseContext().setStatus(APIResponseStatus.ERROR.getDisplayValue());

		return ResponseEntity.badRequest().body(invalidResponse);
	}
}
