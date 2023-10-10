package com.xmplar.music.playlist.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.xmplar.music.playlist.dto.APIEndpointResponse;
import com.xmplar.music.playlist.dto.APIError;
import com.xmplar.music.playlist.dto.APIRequest;
import com.xmplar.music.playlist.dto.ValidationResult;
import com.xmplar.music.playlist.util.APIResponseStatus;

@Service
public abstract class BaseBO<T> {

	public ResponseEntity<APIEndpointResponse> buildValidResponse(APIRequest apiRequest, Object objectForResponse) {

		APIEndpointResponse response = new APIEndpointResponse();
		response.setApiRequest(apiRequest);
		response.getApiResponse().getResponseContext().setStatus(APIResponseStatus.SUCCESS.getDisplayValue());
		response.getApiResponse().setResponseData(objectForResponse);

		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<APIEndpointResponse> buildErrorResponse(APIRequest apiRequest, String detailMsg,
			String shortMsg) {

		Set<String> errors = new HashSet<String>();
		errors.add(detailMsg);
		APIError apiError = new APIError();
		apiError.setDetailMessage(detailMsg);
		apiError.setShortMessage(shortMsg);
		apiError.setErrors(errors);

		APIEndpointResponse invalidResponse = new APIEndpointResponse();
		invalidResponse.setApiError(apiError);
		invalidResponse.setApiRequest(apiRequest);
		invalidResponse.getApiResponse().getResponseContext().setStatus(APIResponseStatus.ERROR.getDisplayValue());

		return ResponseEntity.badRequest().body(invalidResponse);
	}

	public ResponseEntity<APIEndpointResponse> buildIllegalArgumentException(final APIRequest apiRequest,
			final Set<String> allInvalidArgumentErrors) {

		final APIEndpointResponse response = new APIEndpointResponse();
		response.setApiError(new APIError());
		response.getApiError().setShortMessage("Invalid Request");
		response.getApiError().setDetailMessage("Invalid parameter(s) in apiMandatoryParams.");
		response.getApiError().setErrors(allInvalidArgumentErrors);
		response.setApiRequest(apiRequest);
		response.getApiResponse().getResponseContext().setStatus(APIResponseStatus.ERROR.getDisplayValue());

		return ResponseEntity.badRequest().body(response);
	}

	public ResponseEntity<APIEndpointResponse> buildInternalSystemErrorResponse(final APIRequest apiRequest,
			final String errorCode) {

		final APIEndpointResponse response = new APIEndpointResponse();
		response.setApiError(new APIError());
		response.getApiError().setShortMessage("API Error");
		response.getApiError().setDetailMessage("Internal System Error.");
		response.getApiError().setErrorCode(errorCode);
		response.getApiError().getErrors().add("Internal system error has occurred (See error code).");
		response.setApiRequest(apiRequest);
		response.getApiResponse().getResponseContext().setStatus(APIResponseStatus.ERROR.getDisplayValue());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	/**
	 * Generates a ResponseEntity for a validation failure (using the
	 * validationResults object/errors)
	 * 
	 * @param apiRequest       - the initial API Request
	 * @param validationResult - the result of the validation
	 * @return ResponseEntity<APIEndpointResponse>
	 */
	protected ResponseEntity<APIEndpointResponse> generatValidationFailureErrorResponse(APIRequest apiRequest,
			ValidationResult validationResult) {

		APIEndpointResponse invalidResponse = new APIEndpointResponse();
		invalidResponse.setApiError(validationResult.getApiError());
		invalidResponse.setApiRequest(apiRequest);
		invalidResponse.getApiResponse().getResponseContext().setStatus(APIResponseStatus.ERROR.getDisplayValue());

		return ResponseEntity.badRequest().body(invalidResponse);
	}

}