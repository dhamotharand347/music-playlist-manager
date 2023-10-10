package com.xmplar.music.playlist.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.xmplar.music.playlist.dto.APIEndpointResponse;
import com.xmplar.music.playlist.dto.APIRequest;
import com.xmplar.music.playlist.util.TransactionIDValues;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Acts as the delegate for the UserAPIEndpoint. Will handle the request and
 * send back the response
 */
@Service
public class RequestBO {
	
	@Autowired
	private APIRequestBO apiRequestBO;
	
	/**
	 * Handles the delegation request from the UserAPI. Will kick back to the API
	 * caller if the response fails basic validation
	 * 
	 * @param requestBody - the pure String version of the request body
	 */

	public ResponseEntity<APIEndpointResponse> handleAPIRequest(HttpServletRequest httpRequest,
			Map<String, Object> requestBody) {
		APIRequest apiRequest = apiRequestBO.convertHttpRequestBodyMapToAPIRequest(requestBody);

		return generateAPIResponse(apiRequest);
	}

	/**
	 * Generate the API response based on the API request
	 * 
	 * @param apiRequest - the request that was made for the User API
	 * @return - the response for the request
	 */
	private ResponseEntity<APIEndpointResponse> generateAPIResponse(APIRequest apiRequest) {

		switch (apiRequest.getContext().getTransactionId()) {

		case TransactionIDValues.GET_USER:
			return userBO.getUser(apiRequest);

		case TransactionIDValues.ADD_BOOK:
			return bookBO.addBook(apiRequest);

		case TransactionIDValues.GET_BOOK:
			return getbookBO.getBook(apiRequest);

		default:
			return generateInvalidAPIEndpointErrorResponse(apiRequest);
		}
	}

}
